package cn.iocoder.yudao.module.his.service.prepayment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentRefundPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentRefundSaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentRefundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentUsageDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 住院预交金 Service 接口
 *
 * @author yudao
 */
public interface HisPrepaymentService {

    // ========== 预交金管理 ==========

    /**
     * 创建预交金（交费）
     *
     * @param createReqVO 创建信息
     * @return 预交金ID
     */
    Long createPrepayment(@Valid HisPrepaymentSaveReqVO createReqVO);

    /**
     * 更新预交金
     *
     * @param updateReqVO 更新信息
     */
    void updatePrepayment(@Valid HisPrepaymentSaveReqVO updateReqVO);

    /**
     * 删除预交金记录（仅支持已缴纳状态且未使用）
     *
     * @param id 预交金ID
     */
    void deletePrepayment(Long id);

    /**
     * 获取预交金
     *
     * @param id 预交金ID
     * @return 预交金
     */
    HisPrepaymentDO getPrepayment(Long id);

    /**
     * 获取预交金分页
     *
     * @param pageReqVO 分页查询条件
     * @return 预交金分页
     */
    PageResult<HisPrepaymentDO> getPrepaymentPage(HisPrepaymentPageReqVO pageReqVO);

    /**
     * 获取入院记录的预交金列表
     *
     * @param admissionId 入院记录ID
     * @return 预交金列表
     */
    List<HisPrepaymentDO> getPrepaymentListByAdmission(Long admissionId);

    /**
     * 获取患者的预交金列表
     *
     * @param patientId 患者ID
     * @return 预交金列表
     */
    List<HisPrepaymentDO> getPrepaymentListByPatient(Long patientId);

    /**
     * 统计入院记录的预交金总额
     *
     * @param admissionId 入院记录ID
     * @return 预交金总额
     */
    BigDecimal sumAmountByAdmission(Long admissionId);

    /**
     * 统计入院记录的可用余额
     *
     * @param admissionId 入院记录ID
     * @return 可用余额
     */
    BigDecimal sumAvailableBalanceByAdmission(Long admissionId);

    /**
     * 统计入院记录的已使用金额
     *
     * @param admissionId 入院记录ID
     * @return 已使用金额
     */
    BigDecimal sumUsedAmountByAdmission(Long admissionId);

    /**
     * 统计入院记录的已退还金额
     *
     * @param admissionId 入院记录ID
     * @return 已退还金额
     */
    BigDecimal sumRefundAmountByAdmission(Long admissionId);

    /**
     * 校验预交金是否存在
     *
     * @param id 预交金ID
     * @return 预交金
     */
    HisPrepaymentDO validatePrepaymentExists(Long id);

    /**
     * 校验预交金是否可退还
     *
     * @param id 预交金ID
     * @return 预交金
     */
    HisPrepaymentDO validatePrepaymentCanRefund(Long id);

    // ========== 预交金使用 ==========

    /**
     * 记录预交金使用
     *
     * @param prepaymentId 预交金ID
     * @param admissionId 入院记录ID
     * @param patientId 患者ID
     * @param useAmount 使用金额
     * @param useType 使用类型
     * @param feeItemId 费用明细ID
     * @param feeItemName 费用项目名称
     * @param operatorId 操作员ID
     * @param operatorName 操作员姓名
     * @param remark 备注
     */
    void recordUsage(Long prepaymentId, Long admissionId, Long patientId,
                     BigDecimal useAmount, Integer useType,
                     Long feeItemId, String feeItemName,
                     Long operatorId, String operatorName, String remark);

    /**
     * 获取预交金使用记录列表
     *
     * @param prepaymentId 预交金ID
     * @return 使用记录列表
     */
    List<HisPrepaymentUsageDO> getUsageListByPrepayment(Long prepaymentId);

    /**
     * 获取入院记录的预交金使用记录列表
     *
     * @param admissionId 入院记录ID
     * @return 使用记录列表
     */
    List<HisPrepaymentUsageDO> getUsageListByAdmission(Long admissionId);

    // ========== 预交金退还 ==========

    /**
     * 创建退还申请
     *
     * @param createReqVO 创建信息
     * @return 退还记录ID
     */
    Long createRefundApply(@Valid HisPrepaymentRefundSaveReqVO createReqVO);

    /**
     * 审批退还申请
     *
     * @param id 退还记录ID
     * @param approved 是否批准
     * @param approveRemark 审批备注
     * @param approveUserId 审批人ID
     * @param approveUserName 审批人姓名
     */
    void approveRefund(Long id, Boolean approved, String approveRemark,
                       Long approveUserId, String approveUserName);

    /**
     * 执行退还（财务退款）
     *
     * @param id 退还记录ID
     * @param refundNoExternal 外部退还流水号
     * @param operatorId 操作员ID
     * @param operatorName 操作员姓名
     */
    void executeRefund(Long id, String refundNoExternal,
                       Long operatorId, String operatorName);

    /**
     * 取消退还申请
     *
     * @param id 退还记录ID
     */
    void cancelRefund(Long id);

    /**
     * 获取退还记录
     *
     * @param id 退还记录ID
     * @return 退还记录
     */
    HisPrepaymentRefundDO getRefund(Long id);

    /**
     * 获取退还记录分页
     *
     * @param pageReqVO 分页查询条件
     * @return 退还记录分页
     */
    PageResult<HisPrepaymentRefundDO> getRefundPage(HisPrepaymentRefundPageReqVO pageReqVO);

    /**
     * 获取预交金的退还记录列表
     *
     * @param prepaymentId 预交金ID
     * @return 退还记录列表
     */
    List<HisPrepaymentRefundDO> getRefundListByPrepayment(Long prepaymentId);

    /**
     * 获取入院记录的退还记录列表
     *
     * @param admissionId 入院记录ID
     * @return 退还记录列表
     */
    List<HisPrepaymentRefundDO> getRefundListByAdmission(Long admissionId);

}