package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpRefundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpRefundItemDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 退费记录 Service 接口
 *
 * @author yudao
 */
public interface OpRefundService {

    /**
     * 创建退费申请（支持全额退费和部分退费）
     *
     * @param applyReqVO 退费申请信息
     * @return 退费记录ID
     */
    Long applyRefund(@Valid OpRefundApplyReqVO applyReqVO);

    /**
     * 创建退费申请（简化版，全额退费）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRefund(@Valid OpRefundSaveReqVO createReqVO);

    /**
     * 审核退费申请
     *
     * @param id 退费记录ID
     * @param approved 是否通过
     * @param auditRemark 审核意见
     */
    void auditRefund(Long id, Boolean approved, String auditRemark);

    /**
     * 完成退费
     *
     * @param id 退费记录ID
     */
    void completeRefund(Long id);

    /**
     * 取消退费申请
     *
     * @param id 退费记录ID
     */
    void cancelRefund(Long id);

    /**
     * 拒绝退费申请
     *
     * @param id 退费记录ID
     * @param rejectReason 拒绝原因
     */
    void rejectRefund(Long id, String rejectReason);

    /**
     * 获得退费记录
     *
     * @param id 编号
     * @return 退费记录
     */
    OpRefundDO getRefund(Long id);

    /**
     * 获得退费记录（包含退费明细）
     *
     * @param id 编号
     * @return 退费记录
     */
    OpRefundRespVO getRefundWithItems(Long id);

    /**
     * 获得退费记录分页
     *
     * @param pageReqVO 分页查询
     * @return 退费记录分页
     */
    PageResult<OpRefundDO> getRefundPage(OpRefundPageReqVO pageReqVO);

    /**
     * 获得退费记录列表
     *
     * @param ids 编号列表
     * @return 退费记录列表
     */
    List<OpRefundDO> getRefundList(List<Long> ids);

    /**
     * 根据退费单号获得退费记录
     *
     * @param refundNo 退费单号
     * @return 退费记录
     */
    OpRefundDO getRefundByRefundNo(String refundNo);

    /**
     * 根据费用ID获得退费记录列表
     *
     * @param feeId 费用ID
     * @return 退费记录列表
     */
    List<OpRefundDO> getRefundListByFeeId(Long feeId);

    /**
     * 根据支付ID获得退费记录列表
     *
     * @param paymentId 支付ID
     * @return 退费记录列表
     */
    List<OpRefundDO> getRefundListByPaymentId(Long paymentId);

    /**
     * 获得待审核的退费记录列表
     *
     * @return 待审核的退费记录列表
     */
    List<OpRefundDO> getPendingRefundList();

    /**
     * 校验退费记录是否存在
     *
     * @param id 编号
     * @return 退费记录
     */
    OpRefundDO validateRefundExists(Long id);

    // ========== 退费明细相关方法 ==========

    /**
     * 获得退费明细列表
     *
     * @param refundId 退费记录ID
     * @return 退费明细列表
     */
    List<OpRefundItemDO> getRefundItemList(Long refundId);

    /**
     * 根据支付明细ID获得已退费数量
     *
     * @param paymentItemId 支付明细ID
     * @return 已退费数量
     */
    BigDecimal getRefundedQuantity(Long paymentItemId);

    /**
     * 根据支付明细ID获得已退费金额
     *
     * @param paymentItemId 支付明细ID
     * @return 已退费金额
     */
    BigDecimal getRefundedAmount(Long paymentItemId);

    // ========== 可退费查询相关方法 ==========

    /**
     * 获取可退费明细列表
     *
     * @param paymentId 支付记录ID
     * @return 可退费明细列表
     */
    List<RefundableItemRespVO> getRefundableItems(Long paymentId);

    /**
     * 计算退费金额
     *
     * @param paymentId 支付记录ID
     * @param refundItems 退费明细列表
     * @return 退费金额汇总
     */
    RefundAmountSummary calculateRefundAmount(Long paymentId, List<OpRefundItemReqVO> refundItems);

    /**
     * 退费金额汇总
     */
    class RefundAmountSummary {
        private BigDecimal totalRefundAmount;
        private BigDecimal insuranceRefundAmount;
        private BigDecimal selfRefundAmount;

        public RefundAmountSummary() {
            this.totalRefundAmount = BigDecimal.ZERO;
            this.insuranceRefundAmount = BigDecimal.ZERO;
            this.selfRefundAmount = BigDecimal.ZERO;
        }

        public RefundAmountSummary(BigDecimal totalRefundAmount, BigDecimal insuranceRefundAmount, BigDecimal selfRefundAmount) {
            this.totalRefundAmount = totalRefundAmount;
            this.insuranceRefundAmount = insuranceRefundAmount;
            this.selfRefundAmount = selfRefundAmount;
        }

        public BigDecimal getTotalRefundAmount() {
            return totalRefundAmount;
        }

        public void setTotalRefundAmount(BigDecimal totalRefundAmount) {
            this.totalRefundAmount = totalRefundAmount;
        }

        public BigDecimal getInsuranceRefundAmount() {
            return insuranceRefundAmount;
        }

        public void setInsuranceRefundAmount(BigDecimal insuranceRefundAmount) {
            this.insuranceRefundAmount = insuranceRefundAmount;
        }

        public BigDecimal getSelfRefundAmount() {
            return selfRefundAmount;
        }

        public void setSelfRefundAmount(BigDecimal selfRefundAmount) {
            this.selfRefundAmount = selfRefundAmount;
        }
    }

}