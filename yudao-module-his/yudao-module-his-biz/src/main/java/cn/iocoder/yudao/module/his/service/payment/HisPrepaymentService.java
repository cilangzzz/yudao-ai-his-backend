package cn.iocoder.yudao.module.his.service.payment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.HisPrepaymentDO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预交金 Service 接口
 *
 * @author yudao
 */
public interface HisPrepaymentService {

    /**
     * 创建预交金记录
     *
     * @param createReqVO 创建信息
     * @return 预交金ID
     */
    Long createPrepayment(HisPrepaymentCreateReqVO createReqVO);

    /**
     * 退预交金
     *
     * @param id 预交金ID
     * @param refundAmount 退款金额
     * @param refundBy 退款人ID
     */
    void refundPrepayment(Long id, BigDecimal refundAmount, Long refundBy);

    /**
     * 删除预交金记录
     *
     * @param id 预交金ID
     */
    void deletePrepayment(Long id);

    /**
     * 获取预交金记录
     *
     * @param id 预交金ID
     * @return 预交金记录
     */
    HisPrepaymentDO getPrepayment(Long id);

    /**
     * 根据预交金单号获取预交金记录
     *
     * @param prepayNo 预交金单号
     * @return 预交金记录
     */
    HisPrepaymentDO getPrepaymentByPrepayNo(String prepayNo);

    /**
     * 分页查询预交金记录
     *
     * @param pageReqVO 分页查询条件
     * @return 分页结果
     */
    PageResult<HisPrepaymentDO> getPrepaymentPage(HisPrepaymentPageReqVO pageReqVO);

    /**
     * 根据住院ID获取预交金列表
     *
     * @param admissionId 住院ID
     * @return 预交金列表
     */
    List<HisPrepaymentDO> getPrepaymentListByAdmissionId(Long admissionId);

    /**
     * 根据患者ID获取预交金列表
     *
     * @param patientId 患者ID
     * @return 预交金列表
     */
    List<HisPrepaymentDO> getPrepaymentListByPatientId(Long patientId);

    /**
     * 获取住院预交金总额
     *
     * @param admissionId 住院ID
     * @return 预交金总额
     */
    BigDecimal getTotalPrepaymentByAdmissionId(Long admissionId);

}