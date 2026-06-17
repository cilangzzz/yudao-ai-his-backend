package cn.iocoder.yudao.module.his.service.dispense;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispensePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispenseSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseDO;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseItemDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.dispense.OpDispenseItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.dispense.OpDispenseMapper;
import cn.iocoder.yudao.module.his.dal.mysql.prescription.OpPrescriptionItemMapper;
import cn.iocoder.yudao.module.his.service.prescription.OpPrescriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 门诊发药管理 Service 实现类
 *
 * 提供发药单的创建、调配、发药、退药等全流程管理功能
 */
@Service
@Validated
public class OpDispenseServiceImpl implements OpDispenseService {

    @Resource
    private OpDispenseMapper dispenseMapper;

    @Resource
    private OpDispenseItemMapper dispenseItemMapper;

    @Resource
    private OpPrescriptionItemMapper prescriptionItemMapper;

    @Resource
    private OpPrescriptionService prescriptionService;

    // ==================== 发药单基本操作 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDispense(OpDispenseSaveReqVO createReqVO) {
        // 1. 校验处方是否存在且已审核通过
        var prescription = prescriptionService.validatePrescriptionExists(createReqVO.getPrescriptionId());
        if (prescription.getPrescriptionStatus() < 2) {
            throw exception(OP_DISPENSE_PRESCRIPTION_NOT_AUDITED);
        }

        // 2. 检查是否已存在发药单
        var existingDispense = dispenseMapper.selectByPrescriptionId(createReqVO.getPrescriptionId());
        if (existingDispense != null) {
            throw exception(OP_DISPENSE_ALREADY_COMPLETED);
        }

        // 3. 生成发药单号
        String dispenseNo = generateDispenseNo();

        // 4. 创建发药单主表
        OpDispenseDO dispense = BeanUtils.toBean(createReqVO, OpDispenseDO.class);
        dispense.setDispenseNo(dispenseNo);
        dispense.setDispenseStatus(1); // 待调配状态
        dispense.setTotalAmount(calculateTotalAmount(createReqVO.getItems()));

        // 5. 插入发药单
        dispenseMapper.insert(dispense);

        // 6. 插入发药明细
        insertDispenseItems(dispense.getId(), createReqVO.getItems());

        return dispense.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDispenseByPrescription(Long prescriptionId, Long pharmacyId, String pharmacyName) {
        // 1. 获取处方信息
        var prescription = prescriptionService.validatePrescriptionExists(prescriptionId);

        // 2. 获取处方明细
        List<OpPrescriptionItemDO> prescriptionItems = prescriptionItemMapper.selectListByPrescriptionId(prescriptionId);
        if (prescriptionItems == null || prescriptionItems.isEmpty()) {
            throw exception(PRESCRIPTION_ITEM_NOT_EXISTS);
        }

        // 3. 构建发药单VO
        OpDispenseSaveReqVO createReqVO = new OpDispenseSaveReqVO();
        createReqVO.setPrescriptionId(prescriptionId);
        createReqVO.setRegisterId(prescription.getRegisterId());
        createReqVO.setPatientId(prescription.getPatientId());
        createReqVO.setPatientName(prescription.getPatientName());
        createReqVO.setPharmacyId(pharmacyId);
        createReqVO.setPharmacyName(pharmacyName);

        // 4. 构建发药明细
        List<OpDispenseSaveReqVO.DispenseItemVO> items = new ArrayList<>();
        for (OpPrescriptionItemDO prescriptionItem : prescriptionItems) {
            OpDispenseSaveReqVO.DispenseItemVO item = new OpDispenseSaveReqVO.DispenseItemVO();
            item.setPrescriptionItemId(prescriptionItem.getId());
            item.setDrugId(prescriptionItem.getDrugId());
            item.setDrugCode(prescriptionItem.getDrugCode());
            item.setDrugName(prescriptionItem.getDrugName());
            item.setDrugSpec(prescriptionItem.getDrugSpec());
            item.setQuantity(prescriptionItem.getQuantity());
            item.setUnit(prescriptionItem.getUnit());
            item.setUnitPrice(prescriptionItem.getUnitPrice());
            items.add(item);
        }
        createReqVO.setItems(items);

        return createDispense(createReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDispense(OpDispenseSaveReqVO updateReqVO) {
        // 1. 校验发药单存在且可修改
        validateDispenseCanUpdate(updateReqVO.getId());

        // 2. 更新发药单主表
        OpDispenseDO updateObj = BeanUtils.toBean(updateReqVO, OpDispenseDO.class);
        updateObj.setTotalAmount(calculateTotalAmount(updateReqVO.getItems()));
        dispenseMapper.updateById(updateObj);

        // 3. 删除原有明细并插入新的明细
        dispenseItemMapper.deleteByDispenseId(updateReqVO.getId());
        insertDispenseItems(updateReqVO.getId(), updateReqVO.getItems());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDispense(Long id) {
        // 1. 校验发药单存在且可删除
        validateDispenseCanUpdate(id);

        // 2. 删除发药明细和主表
        dispenseItemMapper.deleteByDispenseId(id);
        dispenseMapper.deleteById(id);
    }

    @Override
    public OpDispenseDO getDispense(Long id) {
        return dispenseMapper.selectById(id);
    }

    @Override
    public OpDispenseDO getDispenseByNo(String dispenseNo) {
        return dispenseMapper.selectByDispenseNo(dispenseNo);
    }

    @Override
    public OpDispenseDO getDispenseByPrescriptionId(Long prescriptionId) {
        return dispenseMapper.selectByPrescriptionId(prescriptionId);
    }

    @Override
    public PageResult<OpDispenseDO> getDispensePage(OpDispensePageReqVO pageReqVO) {
        return dispenseMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OpDispenseItemDO> getDispenseItems(Long dispenseId) {
        return dispenseItemMapper.selectListByDispenseId(dispenseId);
    }

    @Override
    public List<OpDispenseDO> getDispensesByRegisterId(Long registerId) {
        return dispenseMapper.selectListByRegisterId(registerId);
    }

    @Override
    public List<OpDispenseDO> getDispensesByPatientId(Long patientId) {
        return dispenseMapper.selectListByPatientId(patientId);
    }

    // ==================== 调配发药流程 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dispense(Long id, Long pharmacistId, String pharmacistName) {
        // 1. 校验发药单可以调配
        var dispense = validateDispenseCanDispense(id);

        // 2. 更新发药单状态
        OpDispenseDO updateObj = new OpDispenseDO();
        updateObj.setId(id);
        updateObj.setDispenseStatus(2); // 已调配
        updateObj.setDispensePharmacist(pharmacistId);
        updateObj.setDispensePharmacistName(pharmacistName);
        updateObj.setDispenseTime(LocalDateTime.now());
        dispenseMapper.updateById(updateObj);

        // 3. 同步更新处方状态
        prescriptionService.dispense(dispense.getPrescriptionId(), pharmacistId, pharmacistName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(Long id, Long pharmacistId, String pharmacistName) {
        // 1. 校验发药单可以发药
        var dispense = validateDispenseCanSend(id);

        // 2. 更新发药单状态
        OpDispenseDO updateObj = new OpDispenseDO();
        updateObj.setId(id);
        updateObj.setDispenseStatus(3); // 已发药
        updateObj.setSendPharmacist(pharmacistId);
        updateObj.setSendPharmacistName(pharmacistName);
        updateObj.setSendTime(LocalDateTime.now());
        dispenseMapper.updateById(updateObj);

        // 3. 同步更新处方状态
        prescriptionService.send(dispense.getPrescriptionId(), pharmacistId, pharmacistName);

        // TODO: 4. 调用库存服务，扣减库存
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSend(List<Long> dispenseIds, Long pharmacistId, String pharmacistName) {
        for (Long dispenseId : dispenseIds) {
            try {
                send(dispenseId, pharmacistId, pharmacistName);
            } catch (Exception ignored) {
                // 记录失败但继续处理其他发药单
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnDrug(Long id, Long pharmacistId, String pharmacistName, String reason) {
        // 1. 校验发药单可以退药
        var dispense = validateDispenseCanReturn(id);

        // 2. 更新发药单状态
        OpDispenseDO updateObj = new OpDispenseDO();
        updateObj.setId(id);
        updateObj.setDispenseStatus(4); // 已退药
        updateObj.setRemark(reason);
        dispenseMapper.updateById(updateObj);

        // 3. 同步更新处方状态
        if (pharmacistId != null && pharmacistName != null) {
            prescriptionService.returnDrug(dispense.getPrescriptionId(), pharmacistId, pharmacistName, reason);
        } else {
            prescriptionService.returnDrug(dispense.getPrescriptionId(), reason);
        }

        // TODO: 4. 调用库存服务，退还库存
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnDrug(Long id, String reason) {
        returnDrug(id, null, null, reason);
    }

    // ==================== 查询统计 ====================

    @Override
    public List<OpDispenseDO> getPendingDispenseList(Long pharmacyId) {
        return dispenseMapper.selectPendingDispenseList(pharmacyId);
    }

    @Override
    public List<OpDispenseDO> getPendingSendList(Long pharmacyId) {
        return dispenseMapper.selectPendingSendList(pharmacyId);
    }

    @Override
    public Long countByPharmacyIdAndStatus(Long pharmacyId, Integer dispenseStatus) {
        return dispenseMapper.selectCountByPharmacyIdAndStatus(pharmacyId, dispenseStatus);
    }

    // ==================== 校验方法 ====================

    @Override
    public OpDispenseDO validateDispenseExists(Long id) {
        if (id == null) {
            return null;
        }
        OpDispenseDO dispense = dispenseMapper.selectById(id);
        if (dispense == null) {
            throw exception(OP_DISPENSE_NOT_EXISTS);
        }
        return dispense;
    }

    @Override
    public OpDispenseDO validateDispenseCanDispense(Long id) {
        var dispense = validateDispenseExists(id);
        if (!dispense.canDispense()) {
            throw exception(OP_DISPENSE_ALREADY_COMPLETED);
        }
        return dispense;
    }

    @Override
    public OpDispenseDO validateDispenseCanSend(Long id) {
        var dispense = validateDispenseExists(id);
        if (!dispense.canSend()) {
            throw exception(OP_DISPENSE_ALREADY_COMPLETED);
        }
        return dispense;
    }

    @Override
    public OpDispenseDO validateDispenseCanReturn(Long id) {
        var dispense = validateDispenseExists(id);
        if (!dispense.canReturn()) {
            throw exception(OP_DISPENSE_ALREADY_COMPLETED);
        }
        return dispense;
    }

    /**
     * 校验发药单是否可以修改/删除（仅待调配状态可修改）
     */
    private OpDispenseDO validateDispenseCanUpdate(Long id) {
        var dispense = validateDispenseExists(id);
        if (dispense.getDispenseStatus() != 1) {
            throw exception(OP_DISPENSE_ALREADY_COMPLETED);
        }
        return dispense;
    }

    // ==================== 私有方法 ====================

    /**
     * 生成发药单号
     * 格式: DS + yyyyMMdd + 4位流水号
     */
    private String generateDispenseNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("DS%s%04d", dateStr, seq);
    }

    /**
     * 计算发药单总金额
     */
    private BigDecimal calculateTotalAmount(List<OpDispenseSaveReqVO.DispenseItemVO> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> {
                    if (item.getQuantity() != null && item.getUnitPrice() != null) {
                        return item.getQuantity().multiply(item.getUnitPrice());
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 插入发药明细
     */
    private void insertDispenseItems(Long dispenseId, List<OpDispenseSaveReqVO.DispenseItemVO> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        List<OpDispenseItemDO> itemList = new ArrayList<>();
        for (var itemVO : items) {
            OpDispenseItemDO item = BeanUtils.toBean(itemVO, OpDispenseItemDO.class);
            item.setDispenseId(dispenseId);
            if (item.getQuantity() != null && item.getUnitPrice() != null) {
                item.setAmount(item.getQuantity().multiply(item.getUnitPrice()));
            }
            itemList.add(item);
        }
        dispenseItemMapper.insertBatch(itemList);
    }

}