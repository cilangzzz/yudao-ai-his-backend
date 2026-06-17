package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeItemDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentDO;
import cn.iocoder.yudao.module.his.dal.mysql.fee.OpFeeMapper;
import cn.iocoder.yudao.module.his.dal.mysql.fee.OpFeeItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.fee.OpPaymentMapper;
import cn.iocoder.yudao.module.his.enums.FeeStatusEnum;
import cn.iocoder.yudao.module.his.enums.PaymentStatusEnum;
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
 * 支付记录 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class OpPaymentServiceImpl implements OpPaymentService {

    @Resource
    private OpPaymentMapper paymentMapper;

    @Resource
    private OpFeeMapper feeMapper;

    @Resource
    private OpFeeItemMapper feeItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPayment(OpPaymentSaveReqVO createReqVO) {
        // 1. 校验费用存在且未收费
        OpFeeDO fee = feeMapper.selectById(createReqVO.getFeeId());
        if (fee == null) {
            throw exception(OP_FEE_NOT_EXISTS);
        }
        if (FeeStatusEnum.PAID.getStatus().equals(fee.getFeeStatus())) {
            throw exception(OP_FEE_ALREADY_PAID);
        }

        // 2. 创建支付记录
        OpPaymentDO payment = BeanUtils.toBean(createReqVO, OpPaymentDO.class);
        payment.setPaymentNo(generatePaymentNo());
        payment.setPayStatus(PaymentStatusEnum.SUCCESS.getStatus());
        payment.setPayTime(LocalDateTime.now());
        // TODO: 设置收费员信息，从当前登录用户获取
        // payment.setCashierId(LoginUserUtil.getUserId());
        // payment.setCashierName(LoginUserUtil.getNickname());
        paymentMapper.insert(payment);

        // 3. 更新费用状态
        OpFeeDO updateFee = new OpFeeDO();
        updateFee.setId(createReqVO.getFeeId());
        updateFee.setFeeStatus(FeeStatusEnum.PAID.getStatus());
        if (createReqVO.getInsuranceAmount() != null) {
            updateFee.setInsuranceAmount(createReqVO.getInsuranceAmount());
        }
        if (createReqVO.getSelfAmount() != null) {
            updateFee.setSelfAmount(createReqVO.getSelfAmount());
        }
        feeMapper.updateById(updateFee);

        // 4. 更新费用明细状态
        List<OpFeeItemDO> feeItems = feeItemMapper.selectListByFeeId(createReqVO.getFeeId());
        for (OpFeeItemDO feeItem : feeItems) {
            OpFeeItemDO updateItem = new OpFeeItemDO();
            updateItem.setId(feeItem.getId());
            updateItem.setFeeItemStatus(1); // 已收费
            updateItem.setFeeTime(LocalDateTime.now());
            feeItemMapper.updateById(updateItem);
        }

        return payment.getId();
    }

    @Override
    public OpPaymentDO getPayment(Long id) {
        return paymentMapper.selectById(id);
    }

    @Override
    public PageResult<OpPaymentDO> getPaymentPage(OpPaymentPageReqVO pageReqVO) {
        return paymentMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<OpPaymentDO>()
                .likeIfPresent(OpPaymentDO::getPaymentNo, pageReqVO.getPaymentNo())
                .eqIfPresent(OpPaymentDO::getFeeId, pageReqVO.getFeeId())
                .eqIfPresent(OpPaymentDO::getRegisterId, pageReqVO.getRegisterId())
                .eqIfPresent(OpPaymentDO::getPatientId, pageReqVO.getPatientId())
                .likeIfPresent(OpPaymentDO::getPatientName, pageReqVO.getPatientName())
                .eqIfPresent(OpPaymentDO::getPayType, pageReqVO.getPayType())
                .eqIfPresent(OpPaymentDO::getPayStatus, pageReqVO.getPayStatus())
                .eqIfPresent(OpPaymentDO::getCashierId, pageReqVO.getCashierId())
                .likeIfPresent(OpPaymentDO::getInvoiceNo, pageReqVO.getInvoiceNo())
                .betweenIfPresent(OpPaymentDO::getPayTime, pageReqVO.getPayTime())
                .orderByDesc(OpPaymentDO::getId));
    }

    @Override
    public List<OpPaymentDO> getPaymentList(List<Long> ids) {
        return paymentMapper.selectBatchIds(ids);
    }

    @Override
    public OpPaymentDO getPaymentByPaymentNo(String paymentNo) {
        return paymentMapper.selectByPaymentNo(paymentNo);
    }

    @Override
    public List<OpPaymentDO> getPaymentListByFeeId(Long feeId) {
        return paymentMapper.selectListByFeeId(feeId);
    }

    @Override
    public List<OpPaymentDO> getPaymentListByRegisterId(Long registerId) {
        return paymentMapper.selectListByRegisterId(registerId);
    }

    @Override
    public List<OpPaymentDO> getPaymentListByPatientId(Long patientId) {
        return paymentMapper.selectListByPatientId(patientId);
    }

    @Override
    public OpPaymentDO getPaymentByInvoiceNo(String invoiceNo) {
        return paymentMapper.selectByInvoiceNo(invoiceNo);
    }

    @Override
    public OpPaymentDO validatePaymentExists(Long id) {
        if (id == null) {
            return null;
        }
        OpPaymentDO payment = paymentMapper.selectById(id);
        if (payment == null) {
            throw exception(OP_PAYMENT_NOT_EXISTS);
        }
        return payment;
    }

    /**
     * 生成支付单号
     * 格式: P + yyyyMMddHHmmss + 4位流水号
     */
    private String generatePaymentNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("P%s%04d", dateStr, seq);
    }

}