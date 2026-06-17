package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.*;
import cn.iocoder.yudao.module.his.dal.mysql.fee.*;
import cn.iocoder.yudao.module.his.enums.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 退费记录 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class OpRefundServiceImpl implements OpRefundService {

    @Resource
    private OpRefundMapper refundMapper;

    @Resource
    private OpRefundItemMapper refundItemMapper;

    @Resource
    private OpPaymentMapper paymentMapper;

    @Resource
    private OpPaymentItemMapper paymentItemMapper;

    @Resource
    private OpFeeMapper feeMapper;

    @Resource
    private OpFeeItemMapper feeItemMapper;

    // ========== 退费申请 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long applyRefund(OpRefundApplyReqVO applyReqVO) {
        // 1. 校验支付记录存在且成功
        OpPaymentDO payment = validatePaymentExists(applyReqVO.getPaymentId());

        // 2. 根据退费类型处理
        if (RefundTypeEnum.FULL.getType().equals(applyReqVO.getRefundType())) {
            // 全额退费
            return doFullRefund(applyReqVO, payment);
        } else {
            // 部分退费
            return doPartialRefund(applyReqVO, payment);
        }
    }

    /**
     * 执行全额退费
     */
    private Long doFullRefund(OpRefundApplyReqVO applyReqVO, OpPaymentDO payment) {
        // 获取所有支付明细
        List<OpPaymentItemDO> paymentItems = paymentItemMapper.selectListByPaymentId(payment.getId());
        if (paymentItems.isEmpty()) {
            throw exception(PAYMENT_ITEM_NOT_EXISTS);
        }

        // 创建退费记录
        OpRefundDO refund = buildRefundDO(applyReqVO, payment, RefundTypeEnum.FULL.getType());
        refund.setRefundAmount(payment.getPayAmount());
        refund.setInsuranceRefundAmount(payment.getInsuranceAmount());
        refund.setSelfRefundAmount(payment.getSelfAmount());
        refund.setOriginalPaymentAmount(payment.getPayAmount());
        refund.setRefundItemCount(paymentItems.size());
        refundMapper.insert(refund);

        // 创建退费明细
        List<OpRefundItemDO> refundItems = new ArrayList<>();
        for (OpPaymentItemDO paymentItem : paymentItems) {
            // 检查是否可以退费
            if (PaymentItemStatusEnum.REFUNDED.getStatus().equals(paymentItem.getPayStatus())) {
                throw exception(PAYMENT_ITEM_ALREADY_REFUNDED);
            }

            OpRefundItemDO refundItem = buildRefundItemDO(refund, paymentItem);
            refundItem.setOriginalQuantity(paymentItem.getQuantity());
            refundItem.setRefundQuantity(paymentItem.getQuantity());
            refundItem.setOriginalAmount(paymentItem.getPayAmount());
            refundItem.setRefundAmount(paymentItem.getPayAmount());
            refundItem.setInsuranceRefundAmount(paymentItem.getInsuranceAmount());
            refundItem.setSelfRefundAmount(paymentItem.getSelfAmount());
            refundItem.setRefundReason(applyReqVO.getRefundReason());
            refundItems.add(refundItem);
        }
        refundItemMapper.insertBatch(refundItems);

        return refund.getId();
    }

    /**
     * 执行部分退费
     */
    private Long doPartialRefund(OpRefundApplyReqVO applyReqVO, OpPaymentDO payment) {
        // 校验退费明细不为空
        if (applyReqVO.getRefundItems() == null || applyReqVO.getRefundItems().isEmpty()) {
            throw exception(OP_REFUND_AMOUNT_INVALID);
        }

        // 计算退费金额
        RefundAmountSummary summary = calculateRefundAmount(applyReqVO.getPaymentId(), applyReqVO.getRefundItems());

        // 创建退费记录
        OpRefundDO refund = buildRefundDO(applyReqVO, payment, RefundTypeEnum.PARTIAL.getType());
        refund.setRefundAmount(summary.getTotalRefundAmount());
        refund.setInsuranceRefundAmount(summary.getInsuranceRefundAmount());
        refund.setSelfRefundAmount(summary.getSelfRefundAmount());
        refund.setOriginalPaymentAmount(payment.getPayAmount());
        refund.setRefundItemCount(applyReqVO.getRefundItems().size());
        refundMapper.insert(refund);

        // 创建退费明细
        List<OpRefundItemDO> refundItems = new ArrayList<>();
        for (OpRefundItemReqVO itemReqVO : applyReqVO.getRefundItems()) {
            // 获取支付明细
            OpPaymentItemDO paymentItem = paymentItemMapper.selectById(itemReqVO.getPaymentItemId());
            if (paymentItem == null) {
                throw exception(PAYMENT_ITEM_NOT_EXISTS);
            }

            // 检查是否可以退费
            if (PaymentItemStatusEnum.REFUNDED.getStatus().equals(paymentItem.getPayStatus())) {
                throw exception(PAYMENT_ITEM_ALREADY_REFUNDED);
            }

            // 检查退费数量是否超过可退数量
            BigDecimal refundedQuantity = getRefundedQuantity(paymentItem.getId());
            BigDecimal refundableQuantity = paymentItem.getQuantity().subtract(refundedQuantity);
            if (itemReqVO.getRefundQuantity().compareTo(refundableQuantity) > 0) {
                throw exception(REFUND_AMOUNT_EXCEED);
            }

            // 计算退费金额
            BigDecimal refundAmount = paymentItem.getUnitPrice().multiply(itemReqVO.getRefundQuantity())
                    .setScale(2, RoundingMode.HALF_UP);

            // 按比例计算医保和自费退费金额
            BigDecimal insuranceRatio = paymentItem.getInsuranceAmount().divide(paymentItem.getPayAmount(), 4, RoundingMode.HALF_UP);
            BigDecimal selfRatio = BigDecimal.ONE.subtract(insuranceRatio);
            BigDecimal insuranceRefundAmount = refundAmount.multiply(insuranceRatio).setScale(2, RoundingMode.HALF_UP);
            BigDecimal selfRefundAmount = refundAmount.multiply(selfRatio).setScale(2, RoundingMode.HALF_UP);

            OpRefundItemDO refundItem = buildRefundItemDO(refund, paymentItem);
            refundItem.setOriginalQuantity(paymentItem.getQuantity());
            refundItem.setRefundQuantity(itemReqVO.getRefundQuantity());
            refundItem.setOriginalAmount(paymentItem.getPayAmount());
            refundItem.setRefundAmount(refundAmount);
            refundItem.setInsuranceRefundAmount(insuranceRefundAmount);
            refundItem.setSelfRefundAmount(selfRefundAmount);
            refundItem.setRefundReason(itemReqVO.getRefundReason() != null ? itemReqVO.getRefundReason() : applyReqVO.getRefundReason());
            refundItems.add(refundItem);
        }
        refundItemMapper.insertBatch(refundItems);

        return refund.getId();
    }

    /**
     * 构建退费记录DO
     */
    private OpRefundDO buildRefundDO(OpRefundApplyReqVO applyReqVO, OpPaymentDO payment, Integer refundType) {
        OpRefundDO refund = new OpRefundDO();
        refund.setRefundNo(generateRefundNo());
        refund.setRefundType(refundType);
        refund.setPaymentId(applyReqVO.getPaymentId());
        refund.setFeeId(applyReqVO.getFeeId());
        refund.setRegisterId(applyReqVO.getRegisterId());
        refund.setPatientId(applyReqVO.getPatientId());
        refund.setPatientName(applyReqVO.getPatientName());
        refund.setPayType(payment.getPayType());
        refund.setInsuranceType(payment.getInsuranceType());
        refund.setInvoiceNo(payment.getInvoiceNo());
        refund.setRefundReason(applyReqVO.getRefundReason());
        refund.setRefundStatus(RefundStatusEnum.PENDING.getStatus());
        refund.setApplyTime(LocalDateTime.now());
        refund.setRemark(applyReqVO.getRemark());
        return refund;
    }

    /**
     * 构建退费明细DO
     */
    private OpRefundItemDO buildRefundItemDO(OpRefundDO refund, OpPaymentItemDO paymentItem) {
        OpRefundItemDO refundItem = new OpRefundItemDO();
        refundItem.setRefundId(refund.getId());
        refundItem.setRefundNo(refund.getRefundNo());
        refundItem.setPaymentId(paymentItem.getPaymentId());
        refundItem.setPaymentItemId(paymentItem.getId());
        refundItem.setFeeId(paymentItem.getFeeId());
        refundItem.setFeeItemId(paymentItem.getFeeItemId());
        refundItem.setRegisterId(paymentItem.getRegisterId());
        refundItem.setPatientId(paymentItem.getPatientId());
        refundItem.setPatientName(paymentItem.getPatientName());
        refundItem.setChargeItemId(paymentItem.getChargeItemId());
        refundItem.setItemCode(paymentItem.getItemCode());
        refundItem.setItemName(paymentItem.getItemName());
        refundItem.setItemCategory(paymentItem.getItemCategory());
        refundItem.setSpec(paymentItem.getSpec());
        refundItem.setUnit(paymentItem.getUnit());
        refundItem.setUnitPrice(paymentItem.getUnitPrice());
        refundItem.setPayType(paymentItem.getPayType());
        refundItem.setInsuranceType(paymentItem.getInsuranceType());
        refundItem.setInsuranceNo(paymentItem.getInsuranceNo());
        refundItem.setInsuranceCode(paymentItem.getInsuranceCode());
        refundItem.setInsuranceCategory(paymentItem.getInsuranceCategory());
        refundItem.setRefundStatus(RefundStatusEnum.PENDING.getStatus());
        refundItem.setExecutionDeptId(paymentItem.getExecutionDeptId());
        refundItem.setExecutionDeptName(paymentItem.getExecutionDeptName());
        refundItem.setDoctorId(paymentItem.getDoctorId());
        refundItem.setDoctorName(paymentItem.getDoctorName());
        return refundItem;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRefund(OpRefundSaveReqVO createReqVO) {
        // 转换为退费申请VO（全额退费）
        OpRefundApplyReqVO applyReqVO = new OpRefundApplyReqVO();
        applyReqVO.setPaymentId(createReqVO.getPaymentId());
        applyReqVO.setFeeId(createReqVO.getFeeId());
        applyReqVO.setRegisterId(createReqVO.getRegisterId());
        applyReqVO.setPatientId(createReqVO.getPatientId());
        applyReqVO.setPatientName(createReqVO.getPatientName());
        applyReqVO.setRefundType(RefundTypeEnum.FULL.getType());
        applyReqVO.setRefundAmount(createReqVO.getRefundAmount());
        applyReqVO.setRefundReason(createReqVO.getRefundReason());
        applyReqVO.setRemark(createReqVO.getRemark());

        return applyRefund(applyReqVO);
    }

    // ========== 退费审核与完成 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditRefund(Long id, Boolean approved, String auditRemark) {
        // 1. 校验退费记录存在
        OpRefundDO refund = validateRefundExists(id);

        // 2. 校验状态
        if (!RefundStatusEnum.PENDING.getStatus().equals(refund.getRefundStatus())) {
            throw exception(REFUND_STATUS_ERROR);
        }

        // 3. 更新审核状态
        OpRefundDO updateRefund = new OpRefundDO();
        updateRefund.setId(id);
        updateRefund.setAuditTime(LocalDateTime.now());
        updateRefund.setAuditRemark(auditRemark);

        if (approved) {
            updateRefund.setRefundStatus(RefundStatusEnum.APPROVED.getStatus());
        } else {
            updateRefund.setRefundStatus(RefundStatusEnum.REJECTED.getStatus());
        }

        refundMapper.updateById(updateRefund);

        // 4. 同步更新退费明细状态
        List<OpRefundItemDO> refundItems = refundItemMapper.selectListByRefundId(id);
        for (OpRefundItemDO item : refundItems) {
            OpRefundItemDO updateItem = new OpRefundItemDO();
            updateItem.setId(item.getId());
            updateItem.setRefundStatus(approved ? RefundStatusEnum.APPROVED.getStatus() : RefundStatusEnum.REJECTED.getStatus());
            refundItemMapper.updateById(updateItem);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeRefund(Long id) {
        // 1. 校验退费记录存在
        OpRefundDO refund = validateRefundExists(id);

        // 2. 校验状态（已通过才能完成）
        if (!RefundStatusEnum.APPROVED.getStatus().equals(refund.getRefundStatus())) {
            throw exception(REFUND_STATUS_ERROR);
        }

        LocalDateTime now = LocalDateTime.now();

        // 3. 更新退费状态
        OpRefundDO updateRefund = new OpRefundDO();
        updateRefund.setId(id);
        updateRefund.setRefundStatus(RefundStatusEnum.COMPLETED.getStatus());
        updateRefund.setCompleteTime(now);
        refundMapper.updateById(updateRefund);

        // 4. 更新退费明细状态
        List<OpRefundItemDO> refundItems = refundItemMapper.selectListByRefundId(id);
        for (OpRefundItemDO refundItem : refundItems) {
            OpRefundItemDO updateItem = new OpRefundItemDO();
            updateItem.setId(refundItem.getId());
            updateItem.setRefundStatus(RefundStatusEnum.COMPLETED.getStatus());
            updateItem.setRefundTime(now);
            refundItemMapper.updateById(updateItem);

            // 5. 更新支付明细退费金额
            OpPaymentItemDO paymentItem = paymentItemMapper.selectById(refundItem.getPaymentItemId());
            if (paymentItem != null) {
                BigDecimal currentRefundAmount = paymentItem.getRefundAmount() != null ? paymentItem.getRefundAmount() : BigDecimal.ZERO;
                BigDecimal newRefundAmount = currentRefundAmount.add(refundItem.getRefundAmount());

                OpPaymentItemDO updatePaymentItem = new OpPaymentItemDO();
                updatePaymentItem.setId(paymentItem.getId());
                updatePaymentItem.setRefundAmount(newRefundAmount);
                updatePaymentItem.setRefundTime(now);

                // 如果退费金额等于支付金额，更新状态为已退费
                if (newRefundAmount.compareTo(paymentItem.getPayAmount()) >= 0) {
                    updatePaymentItem.setPayStatus(PaymentItemStatusEnum.REFUNDED.getStatus());
                }
                paymentItemMapper.updateById(updatePaymentItem);
            }
        }

        // 6. 检查支付记录是否全部退费
        List<OpPaymentItemDO> paymentItems = paymentItemMapper.selectListByPaymentId(refund.getPaymentId());
        boolean allRefunded = paymentItems.stream()
                .allMatch(item -> PaymentItemStatusEnum.REFUNDED.getStatus().equals(item.getPayStatus()));

        if (allRefunded) {
            OpPaymentDO updatePayment = new OpPaymentDO();
            updatePayment.setId(refund.getPaymentId());
            updatePayment.setPayStatus(PaymentStatusEnum.REFUNDED.getStatus());
            paymentMapper.updateById(updatePayment);
        }

        // 7. 更新费用状态
        OpFeeDO fee = feeMapper.selectById(refund.getFeeId());
        if (fee != null) {
            // 计算已退费金额
            List<OpRefundDO> refunds = refundMapper.selectListByFeeId(refund.getFeeId());
            BigDecimal totalRefunded = refunds.stream()
                    .filter(r -> RefundStatusEnum.COMPLETED.getStatus().equals(r.getRefundStatus()))
                    .map(OpRefundDO::getRefundAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            OpFeeDO updateFee = new OpFeeDO();
            updateFee.setId(refund.getFeeId());

            // 如果退费金额等于总金额，更新状态为已退费
            if (totalRefunded.compareTo(fee.getTotalAmount()) >= 0) {
                updateFee.setFeeStatus(FeeStatusEnum.REFUNDED.getStatus());
                feeMapper.updateById(updateFee);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRefund(Long id) {
        OpRefundDO refund = validateRefundExists(id);

        // 只有待审核状态可以取消
        if (!RefundStatusEnum.PENDING.getStatus().equals(refund.getRefundStatus())) {
            throw exception(REFUND_STATUS_ERROR);
        }

        OpRefundDO updateRefund = new OpRefundDO();
        updateRefund.setId(id);
        updateRefund.setRefundStatus(RefundStatusEnum.REJECTED.getStatus());
        updateRefund.setAuditRemark("申请人取消");
        refundMapper.updateById(updateRefund);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectRefund(Long id, String rejectReason) {
        OpRefundDO refund = validateRefundExists(id);

        // 只有待审核状态可以拒绝
        if (!RefundStatusEnum.PENDING.getStatus().equals(refund.getRefundStatus())) {
            throw exception(REFUND_STATUS_ERROR);
        }

        OpRefundDO updateRefund = new OpRefundDO();
        updateRefund.setId(id);
        updateRefund.setRefundStatus(RefundStatusEnum.REJECTED.getStatus());
        updateRefund.setAuditTime(LocalDateTime.now());
        updateRefund.setAuditRemark(rejectReason);
        refundMapper.updateById(updateRefund);
    }

    // ========== 查询方法 ==========

    @Override
    public OpRefundDO getRefund(Long id) {
        return refundMapper.selectById(id);
    }

    @Override
    public OpRefundRespVO getRefundWithItems(Long id) {
        OpRefundDO refund = refundMapper.selectById(id);
        if (refund == null) {
            return null;
        }

        OpRefundRespVO respVO = BeanUtils.toBean(refund, OpRefundRespVO.class);

        // 设置枚举名称
        respVO.setRefundTypeName(RefundTypeEnum.FULL.getType().equals(refund.getRefundType()) ? "全额退费" : "部分退费");
        respVO.setRefundStatusName(getRefundStatusName(refund.getRefundStatus()));
        if (refund.getPayType() != null) {
            respVO.setPayTypeName(getPayTypeName(refund.getPayType()));
        }

        // 查询退费明细
        List<OpRefundItemDO> refundItems = refundItemMapper.selectListByRefundId(id);
        List<OpRefundItemRespVO> itemRespList = BeanUtils.toBean(refundItems, OpRefundItemRespVO.class);
        for (int i = 0; i < refundItems.size(); i++) {
            OpRefundItemRespVO itemResp = itemRespList.get(i);
            OpRefundItemDO item = refundItems.get(i);
            itemResp.setItemCategoryName(getItemCategoryName(item.getItemCategory()));
            itemResp.setRefundStatusName(getRefundStatusName(item.getRefundStatus()));
            if (item.getPayType() != null) {
                itemResp.setPayTypeName(getPayTypeName(item.getPayType()));
            }
            if (item.getInsuranceCategory() != null) {
                itemResp.setInsuranceCategoryName(getInsuranceCategoryName(item.getInsuranceCategory()));
            }
            if (item.getSourceType() != null) {
                itemResp.setSourceTypeName(getSourceTypeName(item.getSourceType()));
            }
        }
        respVO.setRefundItems(itemRespList);

        return respVO;
    }

    @Override
    public PageResult<OpRefundDO> getRefundPage(OpRefundPageReqVO pageReqVO) {
        return refundMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<OpRefundDO>()
                .likeIfPresent(OpRefundDO::getRefundNo, pageReqVO.getRefundNo())
                .eqIfPresent(OpRefundDO::getPaymentId, pageReqVO.getPaymentId())
                .eqIfPresent(OpRefundDO::getFeeId, pageReqVO.getFeeId())
                .eqIfPresent(OpRefundDO::getRegisterId, pageReqVO.getRegisterId())
                .eqIfPresent(OpRefundDO::getPatientId, pageReqVO.getPatientId())
                .likeIfPresent(OpRefundDO::getPatientName, pageReqVO.getPatientName())
                .eqIfPresent(OpRefundDO::getRefundStatus, pageReqVO.getRefundStatus())
                .eqIfPresent(OpRefundDO::getApplyBy, pageReqVO.getApplyBy())
                .eqIfPresent(OpRefundDO::getAuditBy, pageReqVO.getAuditBy())
                .betweenIfPresent(OpRefundDO::getApplyTime, pageReqVO.getApplyTime())
                .orderByDesc(OpRefundDO::getId));
    }

    @Override
    public List<OpRefundDO> getRefundList(List<Long> ids) {
        return refundMapper.selectBatchIds(ids);
    }

    @Override
    public OpRefundDO getRefundByRefundNo(String refundNo) {
        return refundMapper.selectByRefundNo(refundNo);
    }

    @Override
    public List<OpRefundDO> getRefundListByFeeId(Long feeId) {
        return refundMapper.selectListByFeeId(feeId);
    }

    @Override
    public List<OpRefundDO> getRefundListByPaymentId(Long paymentId) {
        return refundMapper.selectListByPaymentId(paymentId);
    }

    @Override
    public List<OpRefundDO> getPendingRefundList() {
        return refundMapper.selectPendingRefunds();
    }

    @Override
    public OpRefundDO validateRefundExists(Long id) {
        if (id == null) {
            return null;
        }
        OpRefundDO refund = refundMapper.selectById(id);
        if (refund == null) {
            throw exception(OP_REFUND_NOT_EXISTS);
        }
        return refund;
    }

    // ========== 退费明细相关方法 ==========

    @Override
    public List<OpRefundItemDO> getRefundItemList(Long refundId) {
        return refundItemMapper.selectListByRefundId(refundId);
    }

    @Override
    public BigDecimal getRefundedQuantity(Long paymentItemId) {
        return refundItemMapper.selectRefundedQuantityByPaymentItemId(paymentItemId);
    }

    @Override
    public BigDecimal getRefundedAmount(Long paymentItemId) {
        return refundItemMapper.selectRefundedAmountByPaymentItemId(paymentItemId);
    }

    // ========== 可退费查询相关方法 ==========

    @Override
    public List<RefundableItemRespVO> getRefundableItems(Long paymentId) {
        // 获取支付明细列表
        List<OpPaymentItemDO> paymentItems = paymentItemMapper.selectListByPaymentId(paymentId);
        if (paymentItems.isEmpty()) {
            return new ArrayList<>();
        }

        List<RefundableItemRespVO> result = new ArrayList<>();
        for (OpPaymentItemDO paymentItem : paymentItems) {
            RefundableItemRespVO item = new RefundableItemRespVO();
            item.setPaymentItemId(paymentItem.getId());
            item.setFeeItemId(paymentItem.getFeeItemId());
            item.setChargeItemId(paymentItem.getChargeItemId());
            item.setItemCode(paymentItem.getItemCode());
            item.setItemName(paymentItem.getItemName());
            item.setItemCategory(paymentItem.getItemCategory());
            item.setItemCategoryName(getItemCategoryName(paymentItem.getItemCategory()));
            item.setSpec(paymentItem.getSpec());
            item.setUnit(paymentItem.getUnit());
            item.setUnitPrice(paymentItem.getUnitPrice());
            item.setOriginalQuantity(paymentItem.getQuantity());
            item.setOriginalAmount(paymentItem.getPayAmount());
            item.setInsuranceAmount(paymentItem.getInsuranceAmount());
            item.setSelfAmount(paymentItem.getSelfAmount());
            item.setPayType(paymentItem.getPayType());
            item.setPayTypeName(getPayTypeName(paymentItem.getPayType()));
            item.setInsuranceType(paymentItem.getInsuranceType());
            item.setInsuranceNo(paymentItem.getInsuranceNo());
            item.setInsuranceCode(paymentItem.getInsuranceCode());
            item.setInsuranceCategory(paymentItem.getInsuranceCategory());
            item.setInsuranceCategoryName(getInsuranceCategoryName(paymentItem.getInsuranceCategory()));
            item.setExecutionDeptId(paymentItem.getExecutionDeptId());
            item.setExecutionDeptName(paymentItem.getExecutionDeptName());
            item.setDoctorId(paymentItem.getDoctorId());
            item.setDoctorName(paymentItem.getDoctorName());

            // 计算已退费数量和金额
            BigDecimal refundedQuantity = getRefundedQuantity(paymentItem.getId());
            BigDecimal refundedAmount = getRefundedAmount(paymentItem.getId());
            item.setRefundedQuantity(refundedQuantity);
            item.setRefundedAmount(refundedAmount);

            // 计算可退费数量和金额
            BigDecimal refundableQuantity = paymentItem.getQuantity().subtract(refundedQuantity);
            BigDecimal refundableAmount = paymentItem.getPayAmount().subtract(refundedAmount);
            item.setRefundableQuantity(refundableQuantity);
            item.setRefundableAmount(refundableAmount);

            // 判断是否可退费
            boolean canRefund = refundableQuantity.compareTo(BigDecimal.ZERO) > 0
                    && !PaymentItemStatusEnum.REFUNDED.getStatus().equals(paymentItem.getPayStatus());
            item.setCanRefund(canRefund);
            if (!canRefund && PaymentItemStatusEnum.REFUNDED.getStatus().equals(paymentItem.getPayStatus())) {
                item.setCannotRefundReason("已全部退费");
            }

            result.add(item);
        }

        return result;
    }

    @Override
    public RefundAmountSummary calculateRefundAmount(Long paymentId, List<OpRefundItemReqVO> refundItems) {
        RefundAmountSummary summary = new RefundAmountSummary();

        if (refundItems == null || refundItems.isEmpty()) {
            return summary;
        }

        BigDecimal totalRefundAmount = BigDecimal.ZERO;
        BigDecimal insuranceRefundAmount = BigDecimal.ZERO;
        BigDecimal selfRefundAmount = BigDecimal.ZERO;

        for (OpRefundItemReqVO item : refundItems) {
            OpPaymentItemDO paymentItem = paymentItemMapper.selectById(item.getPaymentItemId());
            if (paymentItem == null) {
                continue;
            }

            // 计算退费金额
            BigDecimal itemRefundAmount = paymentItem.getUnitPrice().multiply(item.getRefundQuantity())
                    .setScale(2, RoundingMode.HALF_UP);

            // 按比例计算医保和自费退费金额
            if (paymentItem.getPayAmount() != null && paymentItem.getPayAmount().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal insuranceRatio = paymentItem.getInsuranceAmount() != null
                        ? paymentItem.getInsuranceAmount().divide(paymentItem.getPayAmount(), 4, RoundingMode.HALF_UP)
                        : BigDecimal.ZERO;
                BigDecimal selfRatio = BigDecimal.ONE.subtract(insuranceRatio);
                insuranceRefundAmount = insuranceRefundAmount.add(itemRefundAmount.multiply(insuranceRatio).setScale(2, RoundingMode.HALF_UP));
                selfRefundAmount = selfRefundAmount.add(itemRefundAmount.multiply(selfRatio).setScale(2, RoundingMode.HALF_UP));
            }

            totalRefundAmount = totalRefundAmount.add(itemRefundAmount);
        }

        summary.setTotalRefundAmount(totalRefundAmount);
        summary.setInsuranceRefundAmount(insuranceRefundAmount);
        summary.setSelfRefundAmount(selfRefundAmount);

        return summary;
    }

    // ========== 辅助方法 ==========

    /**
     * 校验支付记录存在且成功
     */
    private OpPaymentDO validatePaymentExists(Long paymentId) {
        OpPaymentDO payment = paymentMapper.selectById(paymentId);
        if (payment == null) {
            throw exception(OP_PAYMENT_NOT_EXISTS);
        }
        if (!PaymentStatusEnum.SUCCESS.getStatus().equals(payment.getPayStatus())) {
            throw exception(OP_REFUND_AMOUNT_INVALID);
        }
        return payment;
    }

    /**
     * 生成退费单号
     * 格式: R + yyyyMMddHHmmss + 4位流水号
     */
    private String generateRefundNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("R%s%04d", dateStr, seq);
    }

    /**
     * 获取退费状态名称
     */
    private String getRefundStatusName(Integer status) {
        if (status == null) {
            return "";
        }
        for (RefundStatusEnum e : RefundStatusEnum.values()) {
            if (e.getStatus().equals(status)) {
                return e.getName();
            }
        }
        return "";
    }

    /**
     * 获取支付方式名称
     */
    private String getPayTypeName(Integer payType) {
        if (payType == null) {
            return "";
        }
        for (PayTypeEnum e : PayTypeEnum.values()) {
            if (e.getType().equals(payType)) {
                return e.getName();
            }
        }
        return "";
    }

    /**
     * 获取项目类别名称
     */
    private String getItemCategoryName(Integer itemCategory) {
        if (itemCategory == null) {
            return "";
        }
        for (ChargeItemCategoryEnum e : ChargeItemCategoryEnum.values()) {
            if (e.getCategory().equals(itemCategory)) {
                return e.getName();
            }
        }
        return "";
    }

    /**
     * 获取医保类别名称
     */
    private String getInsuranceCategoryName(Integer insuranceCategory) {
        if (insuranceCategory == null) {
            return "";
        }
        for (InsuranceCategoryEnum e : InsuranceCategoryEnum.values()) {
            if (e.getCategory().equals(insuranceCategory)) {
                return e.getName();
            }
        }
        return "";
    }

    /**
     * 获取来源类型名称
     */
    private String getSourceTypeName(Integer sourceType) {
        if (sourceType == null) {
            return "";
        }
        for (FeeSourceTypeEnum e : FeeSourceTypeEnum.values()) {
            if (e.getType().equals(sourceType)) {
                return e.getName();
            }
        }
        return "";
    }

}