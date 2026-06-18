package cn.iocoder.yudao.module.his.service.payment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.payment.OpPaymentItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.payment.OpPaymentMapper;
import cn.iocoder.yudao.module.his.enums.PaymentStatusEnum;
import cn.iocoder.yudao.module.his.service.fee.OpFeeService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 门诊支付 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class OpPaymentServiceImpl implements OpPaymentService {

    @Resource
    private OpPaymentMapper paymentMapper;

    @Resource
    private OpPaymentItemMapper paymentItemMapper;

    @Resource
    private OpFeeService feeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPayment(OpPaymentCreateReqVO createReqVO) {
        // 1. 生成支付单号
        String paymentNo = generatePaymentNo();

        // 2. 创建支付记录
        OpPaymentDO payment = BeanUtils.toBean(createReqVO, OpPaymentDO.class);
        payment.setPaymentNo(paymentNo);
        payment.setPayStatus(PaymentStatusEnum.SUCCESS.getStatus());
        payment.setPayTime(LocalDateTime.now());
        paymentMapper.insert(payment);

        // 3. 创建支付明细
        if (createReqVO.getItems() != null && !createReqVO.getItems().isEmpty()) {
            for (OpPaymentItemCreateReqVO itemVO : createReqVO.getItems()) {
                OpPaymentItemDO item = BeanUtils.toBean(itemVO, OpPaymentItemDO.class);
                item.setPaymentId(payment.getId());
                item.setPayStatus(1); // 已支付
                item.setPayTime(LocalDateTime.now());
                paymentItemMapper.insert(item);
            }
        }

        // 4. 更新费用状态
        if (createReqVO.getFeeId() != null) {
            feeService.updateFeeStatus(createReqVO.getFeeId(), 1); // 已收费
        }

        return payment.getId();
    }

    @Override
    public void updatePayment(OpPaymentUpdateReqVO updateReqVO) {
        // 校验存在
        validatePaymentExists(updateReqVO.getId());
        // 更新
        OpPaymentDO updateObj = BeanUtils.toBean(updateReqVO, OpPaymentDO.class);
        paymentMapper.updateById(updateObj);
    }

    @Override
    public void deletePayment(Long id) {
        // 校验存在
        validatePaymentExists(id);
        // 删除
        paymentMapper.deleteById(id);
    }

    private void validatePaymentExists(Long id) {
        if (paymentMapper.selectById(id) == null) {
            throw exception(PAYMENT_NOT_EXISTS);
        }
    }

    @Override
    public OpPaymentDO getPayment(Long id) {
        return paymentMapper.selectById(id);
    }

    @Override
    public OpPaymentDO getPaymentByPaymentNo(String paymentNo) {
        return paymentMapper.selectByPaymentNo(paymentNo);
    }

    @Override
    public PageResult<OpPaymentDO> getPaymentPage(OpPaymentPageReqVO pageReqVO) {
        return paymentMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<OpPaymentDO>()
                .eqIfPresent(OpPaymentDO::getPaymentNo, pageReqVO.getPaymentNo())
                .eqIfPresent(OpPaymentDO::getFeeId, pageReqVO.getFeeId())
                .eqIfPresent(OpPaymentDO::getRegisterId, pageReqVO.getRegisterId())
                .eqIfPresent(OpPaymentDO::getPatientId, pageReqVO.getPatientId())
                .likeIfPresent(OpPaymentDO::getPatientName, pageReqVO.getPatientName())
                .eqIfPresent(OpPaymentDO::getPayType, pageReqVO.getPayType())
                .eqIfPresent(OpPaymentDO::getPayStatus, pageReqVO.getPayStatus())
                .betweenIfPresent(OpPaymentDO::getPayTime, pageReqVO.getPayTime())
                .orderByDesc(OpPaymentDO::getId));
    }

    @Override
    public List<OpPaymentItemDO> getPaymentItemList(Long paymentId) {
        return paymentItemMapper.selectListByPaymentId(paymentId);
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
    @Transactional(rollbackFor = Exception.class)
    public void confirmPayment(Long id, String invoiceNo) {
        OpPaymentDO payment = paymentMapper.selectById(id);
        if (payment == null) {
            throw exception(PAYMENT_NOT_EXISTS);
        }
        // 更新支付状态
        OpPaymentDO updateObj = new OpPaymentDO();
        updateObj.setId(id);
        updateObj.setPayStatus(PaymentStatusEnum.SUCCESS.getStatus());
        updateObj.setPayTime(LocalDateTime.now());
        updateObj.setInvoiceNo(invoiceNo);
        paymentMapper.updateById(updateObj);
    }

    @Override
    public void cancelPayment(Long id) {
        OpPaymentDO payment = paymentMapper.selectById(id);
        if (payment == null) {
            throw exception(PAYMENT_NOT_EXISTS);
        }
        // 更新支付状态为失败
        OpPaymentDO updateObj = new OpPaymentDO();
        updateObj.setId(id);
        updateObj.setPayStatus(PaymentStatusEnum.FAILED.getStatus());
        paymentMapper.updateById(updateObj);
    }

    /**
     * 生成支付单号
     */
    private String generatePaymentNo() {
        return "PAY" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

}