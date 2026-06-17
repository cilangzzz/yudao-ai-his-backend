package cn.iocoder.yudao.module.his.service.drugreturn;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.*;
import cn.iocoder.yudao.module.his.dal.mysql.drugreturn.*;
import cn.iocoder.yudao.module.his.enums.DrugReturnStatusEnum;
import cn.iocoder.yudao.module.his.enums.DrugReturnTypeEnum;
import cn.iocoder.yudao.module.his.enums.DrugReturnReasonTypeEnum;
import cn.iocoder.yudao.module.his.enums.DrugReturnAuditTypeEnum;
import cn.iocoder.yudao.module.his.enums.DrugConditionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 退药申请 Service 实现类
 */
@Service
@Validated
public class OpDrugReturnServiceImpl implements OpDrugReturnService {

    @Resource
    private OpDrugReturnMapper drugReturnMapper;

    @Resource
    private OpDrugReturnItemMapper drugReturnItemMapper;

    @Resource
    private OpDrugReturnAuditMapper drugReturnAuditMapper;

    @Resource
    private OpDrugReturnInboundMapper drugReturnInboundMapper;

    @Resource
    private OpDrugReturnInboundItemMapper drugReturnInboundItemMapper;

    // TODO: 需要注入其他相关服务（处方、发药、患者、药品库存等）

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDrugReturn(OpDrugReturnSaveReqVO createReqVO) {
        // 1. 生成退药单号
        String returnNo = generateReturnNo(createReqVO.getReturnType());

        // 2. 计算退药总数量和总金额
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OpDrugReturnSaveReqVO.Item item : createReqVO.getItems()) {
            BigDecimal amount = item.getReturnQuantity().multiply(item.getUnitPrice());
            totalQuantity = totalQuantity.add(item.getReturnQuantity());
            totalAmount = totalAmount.add(amount);
        }

        // 3. 创建退药主表
        OpDrugReturnDO drugReturn = BeanUtils.toBean(createReqVO, OpDrugReturnDO.class);
        drugReturn.setReturnNo(returnNo);
        drugReturn.setTotalQuantity(totalQuantity);
        drugReturn.setTotalAmount(totalAmount);
        drugReturn.setReturnStatus(DrugReturnStatusEnum.PENDING.getValue());
        drugReturn.setApplyTime(LocalDateTime.now());
        // TODO: 设置申请人信息（从登录用户获取）
        drugReturnMapper.insert(drugReturn);

        // 4. 创建退药明细
        List<OpDrugReturnItemDO> itemDOList = BeanUtils.toBean(createReqVO.getItems(), OpDrugReturnItemDO.class);
        for (int i = 0; i < itemDOList.size(); i++) {
            OpDrugReturnItemDO itemDO = itemDOList.get(i);
            itemDO.setReturnId(drugReturn.getId());
            itemDO.setReturnNo(returnNo);
            itemDO.setAmount(itemDO.getReturnQuantity().multiply(itemDO.getUnitPrice()));
            itemDO.setReturnedQuantity(BigDecimal.ZERO);
            itemDO.setItemStatus(DrugReturnStatusEnum.PENDING.getValue());
            itemDO.setSortOrder(i + 1);
            // 默认药品状态为完好
            if (itemDO.getDrugCondition() == null) {
                itemDO.setDrugCondition(DrugConditionEnum.GOOD.getValue());
            }
            // 默认可再利用
            if (itemDO.getCanReuse() == null) {
                itemDO.setCanReuse(DrugConditionEnum.GOOD.getValue().equals(itemDO.getDrugCondition()) ? 1 : 0);
            }
            drugReturnItemMapper.insert(itemDO);
        }

        return drugReturn.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDrugReturn(OpDrugReturnSaveReqVO updateReqVO) {
        // 1. 校验存在
        OpDrugReturnDO drugReturn = validateDrugReturnExists(updateReqVO.getId());

        // 2. 校验状态（只有待审核状态可以修改）
        if (!drugReturn.canAudit()) {
            throw exception(DRUG_RETURN_STATUS_ERROR, DrugReturnStatusEnum.getName(drugReturn.getReturnStatus()));
        }

        // 3. 计算退药总数量和总金额
        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OpDrugReturnSaveReqVO.Item item : updateReqVO.getItems()) {
            BigDecimal amount = item.getReturnQuantity().multiply(item.getUnitPrice());
            totalQuantity = totalQuantity.add(item.getReturnQuantity());
            totalAmount = totalAmount.add(amount);
        }

        // 4. 更新退药主表
        OpDrugReturnDO updateObj = BeanUtils.toBean(updateReqVO, OpDrugReturnDO.class);
        updateObj.setTotalQuantity(totalQuantity);
        updateObj.setTotalAmount(totalAmount);
        drugReturnMapper.updateById(updateObj);

        // 5. 删除旧明细，重新创建
        drugReturnItemMapper.deleteByReturnId(updateReqVO.getId());
        List<OpDrugReturnItemDO> itemDOList = BeanUtils.toBean(updateReqVO.getItems(), OpDrugReturnItemDO.class);
        for (int i = 0; i < itemDOList.size(); i++) {
            OpDrugReturnItemDO itemDO = itemDOList.get(i);
            itemDO.setReturnId(drugReturn.getId());
            itemDO.setReturnNo(drugReturn.getReturnNo());
            itemDO.setAmount(itemDO.getReturnQuantity().multiply(itemDO.getUnitPrice()));
            itemDO.setReturnedQuantity(BigDecimal.ZERO);
            itemDO.setItemStatus(DrugReturnStatusEnum.PENDING.getValue());
            itemDO.setSortOrder(i + 1);
            drugReturnItemMapper.insert(itemDO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDrugReturn(Long id) {
        // 1. 校验存在
        OpDrugReturnDO drugReturn = validateDrugReturnExists(id);

        // 2. 校验状态（只有待审核状态可以删除）
        if (!drugReturn.canCancel()) {
            throw exception(DRUG_RETURN_STATUS_ERROR, DrugReturnStatusEnum.getName(drugReturn.getReturnStatus()));
        }

        // 3. 删除明细
        drugReturnItemMapper.deleteByReturnId(id);

        // 4. 删除主表
        drugReturnMapper.deleteById(id);
    }

    @Override
    public OpDrugReturnDO getDrugReturn(Long id) {
        return drugReturnMapper.selectById(id);
    }

    @Override
    public PageResult<OpDrugReturnDO> getDrugReturnPage(OpDrugReturnPageReqVO pageReqVO) {
        return drugReturnMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OpDrugReturnDO> getDrugReturnList(List<Long> ids) {
        return drugReturnMapper.selectBatchIds(ids);
    }

    @Override
    public List<OpDrugReturnItemDO> getDrugReturnItemList(Long returnId) {
        return drugReturnItemMapper.selectListByReturnId(returnId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditDrugReturn(OpDrugReturnAuditReqVO auditReqVO) {
        // 1. 校验存在
        OpDrugReturnDO drugReturn = validateDrugReturnExists(auditReqVO.getReturnId());

        // 2. 校验状态（只有待审核状态可以审核）
        if (!drugReturn.canAudit()) {
            throw exception(DRUG_RETURN_STATUS_ERROR, DrugReturnStatusEnum.getName(drugReturn.getReturnStatus()));
        }

        // 3. 默认药师审核
        Integer auditType = auditReqVO.getAuditType();
        if (auditType == null) {
            auditType = DrugReturnAuditTypeEnum.PHARMACIST.getValue();
        }

        // 4. 记录审核历史
        OpDrugReturnAuditDO auditDO = new OpDrugReturnAuditDO();
        auditDO.setReturnId(drugReturn.getId());
        auditDO.setReturnNo(drugReturn.getReturnNo());
        auditDO.setAuditType(auditType);
        auditDO.setAuditResult(auditReqVO.getAuditResult());
        auditDO.setAuditRemark(auditReqVO.getAuditRemark());
        auditDO.setAuditTime(LocalDateTime.now());
        // TODO: 设置审核人信息（从登录用户获取）
        drugReturnAuditMapper.insert(auditDO);

        // 5. 更新退药状态
        OpDrugReturnDO updateObj = new OpDrugReturnDO();
        updateObj.setId(drugReturn.getId());
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setAuditRemark(auditReqVO.getAuditRemark());
        // TODO: 设置审核人信息（从登录用户获取）

        if (auditReqVO.getAuditResult() == 1) {
            // 审核通过
            updateObj.setReturnStatus(DrugReturnStatusEnum.APPROVED.getValue());
        } else {
            // 审核拒绝
            updateObj.setReturnStatus(DrugReturnStatusEnum.REJECTED.getValue());
        }
        drugReturnMapper.updateById(updateObj);

        // 6. 更新明细状态
        List<OpDrugReturnItemDO> items = drugReturnItemMapper.selectListByReturnId(drugReturn.getId());
        for (OpDrugReturnItemDO item : items) {
            OpDrugReturnItemDO updateItem = new OpDrugReturnItemDO();
            updateItem.setId(item.getId());
            updateItem.setItemStatus(auditReqVO.getAuditResult() == 1 ?
                    DrugReturnStatusEnum.APPROVED.getValue() : DrugReturnStatusEnum.REJECTED.getValue());
            drugReturnItemMapper.updateById(updateItem);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelDrugReturn(Long id, String cancelReason) {
        // 1. 校验存在
        OpDrugReturnDO drugReturn = validateDrugReturnExists(id);

        // 2. 校验状态（只有待审核状态可以取消）
        if (!drugReturn.canCancel()) {
            throw exception(DRUG_RETURN_STATUS_ERROR, DrugReturnStatusEnum.getName(drugReturn.getReturnStatus()));
        }

        // 3. 更新状态
        OpDrugReturnDO updateObj = new OpDrugReturnDO();
        updateObj.setId(id);
        updateObj.setReturnStatus(DrugReturnStatusEnum.CANCELLED.getValue());
        updateObj.setCancelTime(LocalDateTime.now());
        updateObj.setCancelReason(cancelReason);
        // TODO: 设置取消人信息（从登录用户获取）
        drugReturnMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String inboundDrugReturn(OpDrugReturnInboundReqVO inboundReqVO) {
        // 1. 校验存在
        OpDrugReturnDO drugReturn = validateDrugReturnExists(inboundReqVO.getReturnId());

        // 2. 校验状态（只有审核通过状态可以入库）
        if (!drugReturn.canInbound()) {
            throw exception(DRUG_RETURN_STATUS_ERROR, DrugReturnStatusEnum.getName(drugReturn.getReturnStatus()));
        }

        // 3. 生成入库单号
        String inboundNo = generateInboundNo();

        // 4. 创建入库记录
        OpDrugReturnInboundDO inboundDO = new OpDrugReturnInboundDO();
        inboundDO.setInboundNo(inboundNo);
        inboundDO.setReturnId(drugReturn.getId());
        inboundDO.setReturnNo(drugReturn.getReturnNo());
        inboundDO.setWarehouseId(inboundReqVO.getWarehouseId());
        inboundDO.setWarehouseName(inboundReqVO.getWarehouseName());
        inboundDO.setTotalQuantity(drugReturn.getTotalQuantity());
        inboundDO.setTotalAmount(drugReturn.getTotalAmount());
        inboundDO.setInboundTime(LocalDateTime.now());
        inboundDO.setAuditStatus(1); // 待审核
        // TODO: 设置操作员信息（从登录用户获取）
        inboundDO.setRemark(inboundReqVO.getRemark());
        drugReturnInboundMapper.insert(inboundDO);

        // 5. 创建入库明细
        List<OpDrugReturnItemDO> returnItems = drugReturnItemMapper.selectListByReturnId(drugReturn.getId());
        for (OpDrugReturnItemDO returnItem : returnItems) {
            // 只有可再利用的药品才入库
            if (returnItem.canReuse()) {
                OpDrugReturnInboundItemDO inboundItem = new OpDrugReturnInboundItemDO();
                inboundItem.setInboundId(inboundDO.getId());
                inboundItem.setInboundNo(inboundNo);
                inboundItem.setReturnItemId(returnItem.getId());
                inboundItem.setDrugId(returnItem.getDrugId());
                inboundItem.setDrugCode(returnItem.getDrugCode());
                inboundItem.setDrugName(returnItem.getDrugName());
                inboundItem.setDrugSpec(returnItem.getDrugSpec());
                inboundItem.setBatchNo(returnItem.getBatchNo());
                inboundItem.setExpiryDate(returnItem.getExpiryDate());
                inboundItem.setQuantity(returnItem.getReturnQuantity());
                inboundItem.setUnit(returnItem.getUnit());
                inboundItem.setUnitPrice(returnItem.getUnitPrice());
                inboundItem.setAmount(returnItem.getAmount());
                drugReturnInboundItemMapper.insert(inboundItem);

                // TODO: 更新药品库存（需要注入药品库存服务）
            }
        }

        // 6. 更新退药状态
        OpDrugReturnDO updateObj = new OpDrugReturnDO();
        updateObj.setId(drugReturn.getId());
        updateObj.setReturnStatus(DrugReturnStatusEnum.INBOUNDED.getValue());
        updateObj.setInboundTime(LocalDateTime.now());
        // TODO: 设置入库人信息（从登录用户获取）
        drugReturnMapper.updateById(updateObj);

        // 7. 更新明细状态
        for (OpDrugReturnItemDO returnItem : returnItems) {
            OpDrugReturnItemDO updateItem = new OpDrugReturnItemDO();
            updateItem.setId(returnItem.getId());
            updateItem.setItemStatus(DrugReturnStatusEnum.INBOUNDED.getValue());
            updateItem.setReturnedQuantity(returnItem.getReturnQuantity());
            drugReturnItemMapper.updateById(updateItem);
        }

        return inboundNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundDrugReturn(Long id) {
        // 1. 校验存在
        OpDrugReturnDO drugReturn = validateDrugReturnExists(id);

        // 2. 校验状态（只有已入库状态可以退款）
        if (!drugReturn.canRefund()) {
            throw exception(DRUG_RETURN_STATUS_ERROR, DrugReturnStatusEnum.getName(drugReturn.getReturnStatus()));
        }

        // 3. 更新状态
        OpDrugReturnDO updateObj = new OpDrugReturnDO();
        updateObj.setId(id);
        updateObj.setReturnStatus(DrugReturnStatusEnum.REFUNDED.getValue());
        updateObj.setRefundTime(LocalDateTime.now());
        updateObj.setRefundAmount(drugReturn.getTotalAmount());
        // TODO: 设置退款人信息（从登录用户获取）
        // TODO: 调用退款服务（需要注入支付/退款服务）
        drugReturnMapper.updateById(updateObj);
    }

    @Override
    public List<OpDrugReturnDO> getDrugReturnListByPrescriptionId(Long prescriptionId) {
        return drugReturnMapper.selectListByPrescriptionId(prescriptionId);
    }

    @Override
    public List<OpDrugReturnDO> getDrugReturnListByDispenseId(Long dispenseId) {
        return drugReturnMapper.selectListByDispenseId(dispenseId);
    }

    @Override
    public List<OpDrugReturnDO> getDrugReturnListByPatientId(Long patientId) {
        return drugReturnMapper.selectListByPatientId(patientId);
    }

    @Override
    public Long getPendingCount() {
        return drugReturnMapper.countByStatus(DrugReturnStatusEnum.PENDING.getValue());
    }

    @Override
    public List<OpDrugReturnDO> getPendingListByPharmacy(Long pharmacyId) {
        return drugReturnMapper.selectListByPharmacyIdAndStatus(pharmacyId, DrugReturnStatusEnum.PENDING.getValue());
    }

    // ==================== 私有方法 ====================

    /**
     * 校验退药申请是否存在
     */
    private OpDrugReturnDO validateDrugReturnExists(Long id) {
        if (id == null) {
            return null;
        }
        OpDrugReturnDO drugReturn = drugReturnMapper.selectById(id);
        if (drugReturn == null) {
            throw exception(DRUG_RETURN_NOT_EXISTS);
        }
        return drugReturn;
    }

    /**
     * 生成退药单号
     * 格式：TY + 年月日 + 4位序号
     * 例如：TY202606180001
     */
    private String generateReturnNo(Integer returnType) {
        String prefix = returnType == DrugReturnTypeEnum.OUTPATIENT.getValue() ? "TY" : "ZY";
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // TODO: 使用分布式序号生成器
        Long count = drugReturnMapper.selectCount() + 1;
        return prefix + dateStr + String.format("%04d", count % 10000);
    }

    /**
     * 生成入库单号
     * 格式：RTIN + 年月日 + 4位序号
     * 例如：RTIN202606180001
     */
    private String generateInboundNo() {
        String prefix = "RTIN";
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // TODO: 使用分布式序号生成器
        Long count = drugReturnInboundMapper.selectCount() + 1;
        return prefix + dateStr + String.format("%04d", count % 10000);
    }

}