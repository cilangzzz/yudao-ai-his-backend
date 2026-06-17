package cn.iocoder.yudao.module.his.service.payment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpRefundDO;
import cn.iocoder.yudao.module.his.dal.mysql.payment.OpPaymentMapper;
import cn.iocoder.yudao.module.his.dal.mysql.payment.OpRefundMapper;
import cn.iocoder.yudao.module.his.enums.PaymentStatusEnum;
import cn.iocoder.yudao.module.his.enums.RefundStatusEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 门诊退费 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class OpRefundServiceImpl implements OpRefundService {

    @Resource
    private OpRefundMapper refundMapper;

    @Resource
    private OpPaymentMapper paymentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRefund(OpRefundCreateReqVO createReqVO) {
        // 1. 校验原支付记录
        OpPaymentDO payment = paymentMapper.selectById(createReqVO.getPaymentId());
        if (payment == null) {
            throw exception(PAYMENT_NOT_EXISTS);
        }

        // 2. 校验退费金额
        if (createReqVO.getRefundAmount().compareTo(payment.getPayAmount()) > 0) {
            throw exception(REFUND_AMOUNT_EXCEED);
        }

        // 3. 生成退费单号
        String refundNo = generateRefundNo();

        // 4. 创建退费记录
        OpRefundDO refund = BeanUtils.toBean(createReqVO, OpRefundDO.class);
        refund.setRefundNo(refundNo);
        refund.setRefundStatus(RefundStatusEnum.PENDING.getStatus());
        refund.setApplyTime(LocalDateTime.now());
        refund.setFeeId(payment.getFeeId());
        refund.setRegisterId(payment.getRegisterId());
        refund.setPatientId(payment.getPatientId());
        refund.setPatientName(payment.getPatientName());
        refundMapper.insert(refund);

        return refund.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditRefund(Long id, OpRefundAuditReqVO auditReqVO) {
        // 1. 校验退费记录
        OpRefundDO refund = refundMapper.selectById(id);
        if (refund == null) {
            throw exception(REFUND_NOT_EXISTS);
        }

        // 2. 校验状态
        if (!RefundStatusEnum.PENDING.getStatus().equals(refund.getRefundStatus())) {
            throw exception(REFUND_STATUS_ERROR);
        }

        // 3. 更新审核结果
        OpRefundDO updateObj = new OpRefundDO();
        updateObj.setId(id);
        updateObj.setRefundStatus(auditReqVO.getApproved() ? RefundStatusEnum.APPROVED.getStatus() : RefundStatusEnum.REJECTED.getStatus());
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setAuditBy(auditReqVO.getAuditBy());
        updateObj.setAuditRemark(auditReqVO.getAuditRemark());
        refundMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeRefund(Long id) {
        // 1. 校验退费记录
        OpRefundDO refund = refundMapper.selectById(id);
        if (refund == null) {
            throw exception(REFUND_NOT_EXISTS);
        }

        // 2. 校验状态
        if (!RefundStatusEnum.APPROVED.getStatus().equals(refund.getRefundStatus())) {
            throw exception(REFUND_STATUS_ERROR);
        }

        // 3. 更新退费状态
        OpRefundDO updateObj = new OpRefundDO();
        updateObj.setId(id);
        updateObj.setRefundStatus(RefundStatusEnum.COMPLETED.getStatus());
        updateObj.setCompleteTime(LocalDateTime.now());
        refundMapper.updateById(updateObj);

        // 4. 更新原支付状态
        OpPaymentDO paymentUpdate = new OpPaymentDO();
        paymentUpdate.setId(refund.getPaymentId());
        paymentUpdate.setPayStatus(PaymentStatusEnum.REFUNDED.getStatus());
        paymentMapper.updateById(paymentUpdate);
    }

    @Override
    public void deleteRefund(Long id) {
        // 校验存在
        validateRefundExists(id);
        // 删除
        refundMapper.deleteById(id);
    }

    private void validateRefundExists(Long id) {
        if (refundMapper.selectById(id) == null) {
            throw exception(REFUND_NOT_EXISTS);
        }
    }

    @Override
    public OpRefundDO getRefund(Long id) {
        return refundMapper.selectById(id);
    }

    @Override
    public OpRefundDO getRefundByRefundNo(String refundNo) {
        return refundMapper.selectByRefundNo(refundNo);
    }

    @Override
    public PageResult<OpRefundDO> getRefundPage(OpRefundPageReqVO pageReqVO) {
        return refundMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<OpRefundDO>()
                .eqIfPresent(OpRefundDO::getRefundNo, pageReqVO.getRefundNo())
                .eqIfPresent(OpRefundDO::getPaymentId, pageReqVO.getPaymentId())
                .eqIfPresent(OpRefundDO::getPatientId, pageReqVO.getPatientId())
                .likeIfPresent(OpRefundDO::getPatientName, pageReqVO.getPatientName())
                .eqIfPresent(OpRefundDO::getRefundStatus, pageReqVO.getRefundStatus())
                .betweenIfPresent(OpRefundDO::getApplyTime, pageReqVO.getApplyTime())
                .orderByDesc(OpRefundDO::getId));
    }

    @Override
    public List<OpRefundDO> getPendingRefundList() {
        return refundMapper.selectPendingList();
    }

    @Override
    public List<OpRefundDO> getRefundListByPaymentId(Long paymentId) {
        return refundMapper.selectListByPaymentId(paymentId);
    }

    @Override
    public List<OpRefundDO> getRefundListByPatientId(Long patientId) {
        return refundMapper.selectListByPatientId(patientId);
    }

    /**
     * 生成退费单号
     */
    private String generateRefundNo() {
        return "REF" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

}