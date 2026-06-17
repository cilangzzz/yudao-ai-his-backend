package cn.iocoder.yudao.module.his.service.prescription;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.prescription.OpPrescriptionItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.prescription.OpPrescriptionMapper;
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
 * 门诊处方 Service 实现类
 *
 * 提供处方的开立、审核、调配、发药、退药等全流程管理功能
 */
@Service
@Validated
public class OpPrescriptionServiceImpl implements OpPrescriptionService {

    @Resource
    private OpPrescriptionMapper prescriptionMapper;

    @Resource
    private OpPrescriptionItemMapper prescriptionItemMapper;

    // ==================== 处方基本操作 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPrescription(OpPrescriptionSaveReqVO createReqVO) {
        // 1. 生成处方编号
        String prescriptionNo = generatePrescriptionNo();

        // 2. 创建处方主表
        OpPrescriptionDO prescription = BeanUtils.toBean(createReqVO, OpPrescriptionDO.class);
        prescription.setPrescriptionNo(prescriptionNo);
        prescription.setPrescriptionStatus(1); // 开立状态
        if (prescription.getValidDays() == null) {
            prescription.setValidDays(3); // 默认有效期3天
        }

        // 3. 计算总金额
        BigDecimal totalAmount = calculateTotalAmount(createReqVO.getItems());
        prescription.setTotalAmount(totalAmount);

        // 4. 插入处方
        prescriptionMapper.insert(prescription);

        // 5. 插入处方明细
        if (createReqVO.getItems() != null && !createReqVO.getItems().isEmpty()) {
            List<OpPrescriptionItemDO> items = new ArrayList<>();
            int sortOrder = 1;
            for (OpPrescriptionSaveReqVO.PrescriptionItemVO itemVO : createReqVO.getItems()) {
                OpPrescriptionItemDO item = BeanUtils.toBean(itemVO, OpPrescriptionItemDO.class);
                item.setPrescriptionId(prescription.getId());
                if (item.getSortOrder() == null) {
                    item.setSortOrder(sortOrder++);
                }
                // 计算金额
                if (item.getQuantity() != null && item.getUnitPrice() != null) {
                    item.setAmount(item.getQuantity().multiply(item.getUnitPrice()));
                }
                // 默认皮试标志
                if (item.getSkinTest() == null) {
                    item.setSkinTest(0); // 不需要皮试
                }
                items.add(item);
            }
            prescriptionItemMapper.insertBatch(items);
        }

        return prescription.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePrescription(OpPrescriptionSaveReqVO updateReqVO) {
        // 1. 校验处方存在且可修改
        OpPrescriptionDO prescription = validatePrescriptionCanUpdate(updateReqVO.getId());

        // 2. 更新处方主表
        OpPrescriptionDO updateObj = BeanUtils.toBean(updateReqVO, OpPrescriptionDO.class);
        // 计算总金额
        BigDecimal totalAmount = calculateTotalAmount(updateReqVO.getItems());
        updateObj.setTotalAmount(totalAmount);
        prescriptionMapper.updateById(updateObj);

        // 3. 删除原有明细
        prescriptionItemMapper.deleteByPrescriptionId(updateReqVO.getId());

        // 4. 插入新的明细
        if (updateReqVO.getItems() != null && !updateReqVO.getItems().isEmpty()) {
            List<OpPrescriptionItemDO> items = new ArrayList<>();
            int sortOrder = 1;
            for (OpPrescriptionSaveReqVO.PrescriptionItemVO itemVO : updateReqVO.getItems()) {
                OpPrescriptionItemDO item = BeanUtils.toBean(itemVO, OpPrescriptionItemDO.class);
                item.setId(null); // 清除ID，作为新记录插入
                item.setPrescriptionId(updateReqVO.getId());
                if (item.getSortOrder() == null) {
                    item.setSortOrder(sortOrder++);
                }
                // 计算金额
                if (item.getQuantity() != null && item.getUnitPrice() != null) {
                    item.setAmount(item.getQuantity().multiply(item.getUnitPrice()));
                }
                items.add(item);
            }
            prescriptionItemMapper.insertBatch(items);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePrescription(Long id) {
        // 1. 校验处方存在且可删除（仅开立状态可删除）
        OpPrescriptionDO prescription = validatePrescriptionCanUpdate(id);

        // 2. 删除处方明细
        prescriptionItemMapper.deleteByPrescriptionId(id);

        // 3. 删除处方主表
        prescriptionMapper.deleteById(id);
    }

    @Override
    public OpPrescriptionDO getPrescription(Long id) {
        return prescriptionMapper.selectById(id);
    }

    @Override
    public OpPrescriptionDO getPrescriptionByNo(String prescriptionNo) {
        return prescriptionMapper.selectByPrescriptionNo(prescriptionNo);
    }

    @Override
    public PageResult<OpPrescriptionDO> getPrescriptionPage(OpPrescriptionPageReqVO pageReqVO) {
        return prescriptionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OpPrescriptionItemDO> getPrescriptionItems(Long prescriptionId) {
        return prescriptionItemMapper.selectListByPrescriptionId(prescriptionId);
    }

    @Override
    public List<OpPrescriptionDO> getPrescriptionsByRegisterId(Long registerId) {
        return prescriptionMapper.selectListByRegisterId(registerId);
    }

    @Override
    public List<OpPrescriptionDO> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionMapper.selectListByPatientId(patientId);
    }

    // ==================== 处方审核 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditPass(Long id, Long pharmacistId, String pharmacistName, String remark) {
        // 1. 校验处方可以审核
        OpPrescriptionDO prescription = validatePrescriptionCanAudit(id);

        // 2. 校验皮试药品（如果有需要皮试的药品，必须完成皮试且通过）
        List<OpPrescriptionItemDO> items = prescriptionItemMapper.selectSkinTestList(id);
        for (OpPrescriptionItemDO item : items) {
            if (item.needSkinTest() && !item.isSkinTestPassed()) {
                throw exception(PRESCRIPTION_SKIN_TEST_REQUIRED);
            }
        }

        // 3. 更新处方状态
        OpPrescriptionDO updateObj = new OpPrescriptionDO();
        updateObj.setId(id);
        updateObj.setPrescriptionStatus(2); // 审核通过
        updateObj.setPharmacistId(pharmacistId);
        updateObj.setPharmacistName(pharmacistName);
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setAuditResult(1); // 通过
        updateObj.setAuditRemark(remark);
        prescriptionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditReject(Long id, Long pharmacistId, String pharmacistName, String reason) {
        // 1. 校验处方可以审核
        OpPrescriptionDO prescription = validatePrescriptionCanAudit(id);

        // 2. 更新处方状态
        OpPrescriptionDO updateObj = new OpPrescriptionDO();
        updateObj.setId(id);
        updateObj.setPrescriptionStatus(3); // 审核退回
        updateObj.setPharmacistId(pharmacistId);
        updateObj.setPharmacistName(pharmacistName);
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setAuditResult(2); // 退回
        updateObj.setAuditRemark(reason);
        prescriptionMapper.updateById(updateObj);
    }

    // ==================== 调配发药 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dispense(Long id, Long pharmacistId, String pharmacistName) {
        // 1. 校验处方可以调配
        OpPrescriptionDO prescription = validatePrescriptionCanDispense(id);

        // 2. 更新处方状态
        OpPrescriptionDO updateObj = new OpPrescriptionDO();
        updateObj.setId(id);
        updateObj.setPrescriptionStatus(4); // 已调配
        updateObj.setDispensePharmacist(pharmacistId);
        updateObj.setDispensePharmacistName(pharmacistName);
        updateObj.setDispenseTime(LocalDateTime.now());
        prescriptionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(Long id, Long pharmacistId, String pharmacistName) {
        // 1. 校验处方可以发药
        OpPrescriptionDO prescription = validatePrescriptionCanSend(id);

        // 2. 更新处方状态
        OpPrescriptionDO updateObj = new OpPrescriptionDO();
        updateObj.setId(id);
        updateObj.setPrescriptionStatus(5); // 已发药
        updateObj.setSendPharmacist(pharmacistId);
        updateObj.setSendPharmacistName(pharmacistName);
        updateObj.setSendTime(LocalDateTime.now());
        prescriptionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSend(List<Long> prescriptionIds, Long pharmacistId, String pharmacistName) {
        for (Long prescriptionId : prescriptionIds) {
            try {
                send(prescriptionId, pharmacistId, pharmacistName);
            } catch (Exception e) {
                // 记录失败但继续处理其他处方
                // TODO: 可以记录日志
            }
        }
    }

    // ==================== 退药 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnDrug(Long id, String reason) {
        returnDrug(id, null, null, reason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnDrug(Long id, Long pharmacistId, String pharmacistName, String reason) {
        // 1. 校验处方可以退药
        OpPrescriptionDO prescription = validatePrescriptionCanReturn(id);

        // 2. 更新处方状态
        OpPrescriptionDO updateObj = new OpPrescriptionDO();
        updateObj.setId(id);
        updateObj.setPrescriptionStatus(6); // 已退药
        updateObj.setRemark(reason);
        prescriptionMapper.updateById(updateObj);

        // TODO: 调用库存服务，退还库存
    }

    // ==================== 皮试管理 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordSkinTest(Long itemId, String skinTestResult, Long nurseId) {
        // 1. 校验明细存在
        OpPrescriptionItemDO item = validatePrescriptionItemExists(itemId);

        // 2. 校验是否需要皮试
        if (!item.needSkinTest()) {
            throw exception(PRESCRIPTION_SKIN_TEST_REQUIRED);
        }

        // 3. 更新皮试结果
        OpPrescriptionItemDO updateObj = new OpPrescriptionItemDO();
        updateObj.setId(itemId);
        // 根据皮试结果设置状态：阴性(通过)=2，阳性(未通过)=3
        updateObj.setSkinTest("阴性".equals(skinTestResult) || "通过".equals(skinTestResult) ? 2 : 3);
        updateObj.setSkinTestResult(skinTestResult);
        updateObj.setSkinTestTime(LocalDateTime.now());
        updateObj.setSkinTestNurse(nurseId);
        prescriptionItemMapper.updateById(updateObj);

        // 4. 如果皮试未通过，需要特殊处理（可以抛出异常或记录日志）
        if ("阳性".equals(skinTestResult) || "未通过".equals(skinTestResult)) {
            // 记录日志或发送通知
        }
    }

    @Override
    public List<OpPrescriptionItemDO> getPendingSkinTestItems(Long prescriptionId) {
        return prescriptionItemMapper.selectPendingSkinTestList(prescriptionId);
    }

    // ==================== 查询统计 ====================

    @Override
    public List<OpPrescriptionDO> getPendingAuditList(Long deptId) {
        List<OpPrescriptionDO> list = prescriptionMapper.selectPendingAuditList();
        if (deptId != null) {
            list.removeIf(p -> !deptId.equals(p.getDeptId()));
        }
        return list;
    }

    @Override
    public List<OpPrescriptionDO> getPendingDispenseList(Long deptId) {
        List<OpPrescriptionDO> list = prescriptionMapper.selectPendingDispenseList();
        if (deptId != null) {
            list.removeIf(p -> !deptId.equals(p.getDeptId()));
        }
        return list;
    }

    @Override
    public List<OpPrescriptionDO> getPendingSendList(Long deptId) {
        List<OpPrescriptionDO> list = prescriptionMapper.selectPendingSendList();
        if (deptId != null) {
            list.removeIf(p -> !deptId.equals(p.getDeptId()));
        }
        return list;
    }

    @Override
    public Long countByStatus(Long deptId, Integer status) {
        List<OpPrescriptionDO> list = prescriptionMapper.selectListByStatus(status);
        if (deptId != null) {
            return list.stream().filter(p -> deptId.equals(p.getDeptId())).count();
        }
        return (long) list.size();
    }

    // ==================== 校验方法 ====================

    @Override
    public OpPrescriptionDO validatePrescriptionExists(Long id) {
        if (id == null) {
            return null;
        }
        OpPrescriptionDO prescription = prescriptionMapper.selectById(id);
        if (prescription == null) {
            throw exception(PRESCRIPTION_NOT_EXISTS);
        }
        return prescription;
    }

    @Override
    public OpPrescriptionDO validatePrescriptionCanUpdate(Long id) {
        OpPrescriptionDO prescription = validatePrescriptionExists(id);
        // 仅开立状态可以修改/删除
        if (prescription.getPrescriptionStatus() != 1) {
            throw exception(PRESCRIPTION_ALREADY_AUDITED);
        }
        return prescription;
    }

    @Override
    public OpPrescriptionDO validatePrescriptionCanAudit(Long id) {
        OpPrescriptionDO prescription = validatePrescriptionExists(id);
        if (!prescription.canAudit()) {
            throw exception(PRESCRIPTION_ALREADY_AUDITED);
        }
        return prescription;
    }

    @Override
    public OpPrescriptionDO validatePrescriptionCanDispense(Long id) {
        OpPrescriptionDO prescription = validatePrescriptionExists(id);
        if (!prescription.canDispense()) {
            throw exception(PRESCRIPTION_ALREADY_DISPENSED);
        }
        return prescription;
    }

    @Override
    public OpPrescriptionDO validatePrescriptionCanSend(Long id) {
        OpPrescriptionDO prescription = validatePrescriptionExists(id);
        if (!prescription.canSend()) {
            throw exception(PRESCRIPTION_ALREADY_DISPENSED);
        }
        return prescription;
    }

    @Override
    public OpPrescriptionDO validatePrescriptionCanReturn(Long id) {
        OpPrescriptionDO prescription = validatePrescriptionExists(id);
        if (!prescription.canReturn()) {
            throw exception(PRESCRIPTION_ALREADY_DISPENSED);
        }
        return prescription;
    }

    /**
     * 校验处方明细是否存在
     */
    private OpPrescriptionItemDO validatePrescriptionItemExists(Long itemId) {
        if (itemId == null) {
            return null;
        }
        OpPrescriptionItemDO item = prescriptionItemMapper.selectById(itemId);
        if (item == null) {
            throw exception(PRESCRIPTION_ITEM_NOT_EXISTS);
        }
        return item;
    }

    // ==================== 私有方法 ====================

    /**
     * 生成处方编号
     * 格式: RX + yyyyMMdd + 4位流水号
     */
    private String generatePrescriptionNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 简化处理，实际应该用分布式ID或数据库序列
        long seq = System.currentTimeMillis() % 10000;
        return String.format("RX%s%04d", dateStr, seq);
    }

    /**
     * 计算处方总金额
     */
    private BigDecimal calculateTotalAmount(List<OpPrescriptionSaveReqVO.PrescriptionItemVO> items) {
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

}