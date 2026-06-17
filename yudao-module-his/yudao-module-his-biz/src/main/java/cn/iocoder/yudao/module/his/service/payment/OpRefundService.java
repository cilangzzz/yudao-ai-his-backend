package cn.iocoder.yudao.module.his.service.payment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpRefundDO;

import java.util.List;

/**
 * 门诊退费 Service 接口
 *
 * @author yudao
 */
public interface OpRefundService {

    /**
     * 创建退费申请
     *
     * @param createReqVO 创建信息
     * @return 退费ID
     */
    Long createRefund(OpRefundCreateReqVO createReqVO);

    /**
     * 审核退费申请
     *
     * @param id 退费ID
     * @param auditReqVO 审核信息
     */
    void auditRefund(Long id, OpRefundAuditReqVO auditReqVO);

    /**
     * 完成退费
     *
     * @param id 退费ID
     */
    void completeRefund(Long id);

    /**
     * 删除退费记录
     *
     * @param id 退费ID
     */
    void deleteRefund(Long id);

    /**
     * 获取退费记录
     *
     * @param id 退费ID
     * @return 退费记录
     */
    OpRefundDO getRefund(Long id);

    /**
     * 根据退费单号获取退费记录
     *
     * @param refundNo 退费单号
     * @return 退费记录
     */
    OpRefundDO getRefundByRefundNo(String refundNo);

    /**
     * 分页查询退费记录
     *
     * @param pageReqVO 分页查询条件
     * @return 分页结果
     */
    PageResult<OpRefundDO> getRefundPage(OpRefundPageReqVO pageReqVO);

    /**
     * 获取待审核退费列表
     *
     * @return 待审核列表
     */
    List<OpRefundDO> getPendingRefundList();

    /**
     * 根据支付ID获取退费记录列表
     *
     * @param paymentId 支付ID
     * @return 退费列表
     */
    List<OpRefundDO> getRefundListByPaymentId(Long paymentId);

    /**
     * 根据患者ID获取退费记录列表
     *
     * @param patientId 患者ID
     * @return 退费列表
     */
    List<OpRefundDO> getRefundListByPatientId(Long patientId);

}