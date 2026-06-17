package cn.iocoder.yudao.module.his.dal.dataobject.prepayment;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预交金退还记录 DO
 *
 * 记录预交金的退还申请和审批流程
 *
 * @author yudao
 */
@TableName(value = "his_prepayment_refund", autoResultMap = true)
@KeySequence("his_prepayment_refund_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisPrepaymentRefundDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 退还编号
     */
    private String refundNo;

    /**
     * 预交金记录ID
     */
    private Long prepaymentId;

    /**
     * 预交金编号
     */
    private String prepaymentNo;

    /**
     * 入院记录ID
     */
    private Long admissionId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 退还金额
     */
    private BigDecimal refundAmount;

    /**
     * 退还原因
     */
    private String refundReason;

    /**
     * 退还方式
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.RefundTypeEnum}
     * 1-现金 2-银行卡 3-原路退回
     */
    private Integer refundType;

    /**
     * 退还渠道
     */
    private String refundChannel;

    /**
     * 外部退还流水号
     */
    private String refundNoExternal;

    /**
     * 状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PrepaymentRefundStatusEnum}
     * 1-申请中 2-已审批 3-已退还 4-已拒绝
     */
    private Integer status;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 审批时间
     */
    private LocalDateTime approveTime;

    /**
     * 审批人ID
     */
    private Long approveUserId;

    /**
     * 审批人姓名
     */
    private String approveUserName;

    /**
     * 审批备注
     */
    private String approveRemark;

    /**
     * 退还时间
     */
    private LocalDateTime refundTime;

    /**
     * 申请人ID
     */
    private Long operatorId;

    /**
     * 申请人姓名
     */
    private String operatorName;

    /**
     * 备注
     */
    private String remark;

    // ========== 业务方法 ==========

    /**
     * 是否申请中
     */
    public boolean isApplying() {
        return status != null && status == 1;
    }

    /**
     * 是否已审批
     */
    public boolean isApproved() {
        return status != null && status == 2;
    }

    /**
     * 是否已退还
     */
    public boolean isRefunded() {
        return status != null && status == 3;
    }

    /**
     * 是否已拒绝
     */
    public boolean isRejected() {
        return status != null && status == 4;
    }

}