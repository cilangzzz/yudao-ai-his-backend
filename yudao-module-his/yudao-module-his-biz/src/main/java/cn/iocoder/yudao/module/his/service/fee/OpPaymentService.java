package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 支付记录 Service 接口
 *
 * @author yudao
 */
public interface OpPaymentService {

    /**
     * 创建支付记录（收费）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPayment(@Valid OpPaymentSaveReqVO createReqVO);

    /**
     * 获得支付记录
     *
     * @param id 编号
     * @return 支付记录
     */
    OpPaymentDO getPayment(Long id);

    /**
     * 获得支付记录分页
     *
     * @param pageReqVO 分页查询
     * @return 支付记录分页
     */
    PageResult<OpPaymentDO> getPaymentPage(OpPaymentPageReqVO pageReqVO);

    /**
     * 获得支付记录列表
     *
     * @param ids 编号列表
     * @return 支付记录列表
     */
    List<OpPaymentDO> getPaymentList(List<Long> ids);

    /**
     * 根据支付单号获得支付记录
     *
     * @param paymentNo 支付单号
     * @return 支付记录
     */
    OpPaymentDO getPaymentByPaymentNo(String paymentNo);

    /**
     * 根据费用ID获得支付记录列表
     *
     * @param feeId 费用ID
     * @return 支付记录列表
     */
    List<OpPaymentDO> getPaymentListByFeeId(Long feeId);

    /**
     * 根据挂号ID获得支付记录列表
     *
     * @param registerId 挂号ID
     * @return 支付记录列表
     */
    List<OpPaymentDO> getPaymentListByRegisterId(Long registerId);

    /**
     * 根据患者ID获得支付记录列表
     *
     * @param patientId 患者ID
     * @return 支付记录列表
     */
    List<OpPaymentDO> getPaymentListByPatientId(Long patientId);

    /**
     * 根据发票号获得支付记录
     *
     * @param invoiceNo 发票号
     * @return 支付记录
     */
    OpPaymentDO getPaymentByInvoiceNo(String invoiceNo);

    /**
     * 校验支付记录是否存在
     *
     * @param id 编号
     * @return 支付记录
     */
    OpPaymentDO validatePaymentExists(Long id);

}