package cn.iocoder.yudao.module.his.service.payment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentItemDO;

import java.util.List;

/**
 * 门诊支付 Service 接口
 *
 * @author yudao
 */
public interface OpPaymentService {

    /**
     * 创建支付记录
     *
     * @param createReqVO 创建信息
     * @return 支付ID
     */
    Long createPayment(OpPaymentCreateReqVO createReqVO);

    /**
     * 更新支付记录
     *
     * @param updateReqVO 更新信息
     */
    void updatePayment(OpPaymentUpdateReqVO updateReqVO);

    /**
     * 删除支付记录
     *
     * @param id 支付ID
     */
    void deletePayment(Long id);

    /**
     * 获取支付记录
     *
     * @param id 支付ID
     * @return 支付记录
     */
    OpPaymentDO getPayment(Long id);

    /**
     * 根据支付单号获取支付记录
     *
     * @param paymentNo 支付单号
     * @return 支付记录
     */
    OpPaymentDO getPaymentByPaymentNo(String paymentNo);

    /**
     * 分页查询支付记录
     *
     * @param pageReqVO 分页查询条件
     * @return 分页结果
     */
    PageResult<OpPaymentDO> getPaymentPage(OpPaymentPageReqVO pageReqVO);

    /**
     * 获取支付明细列表
     *
     * @param paymentId 支付ID
     * @return 明细列表
     */
    List<OpPaymentItemDO> getPaymentItemList(Long paymentId);

    /**
     * 根据挂号ID获取支付列表
     *
     * @param registerId 挂号ID
     * @return 支付列表
     */
    List<OpPaymentDO> getPaymentListByRegisterId(Long registerId);

    /**
     * 根据患者ID获取支付列表
     *
     * @param patientId 患者ID
     * @return 支付列表
     */
    List<OpPaymentDO> getPaymentListByPatientId(Long patientId);

    /**
     * 支付确认（更新支付状态为成功）
     *
     * @param id 支付ID
     * @param invoiceNo 发票号
     */
    void confirmPayment(Long id, String invoiceNo);

    /**
     * 取消支付（更新支付状态为失败）
     *
     * @param id 支付ID
     */
    void cancelPayment(Long id);

}