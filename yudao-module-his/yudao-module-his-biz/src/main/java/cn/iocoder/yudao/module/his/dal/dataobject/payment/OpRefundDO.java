package cn.iocoder.yudao.module.his.dal.dataobject.payment;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.his.enums.RefundStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 门诊退费记录 DO
 *
 * @author yudao
 */
@TableName("op_refund")
@KeySequence("op_refund_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpRefundDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 退费单号
     */
    private String refundNo;

    /**
     * 原支付ID
     */
    private Long paymentId;

    /**
     * 费费ID
     */
    private Long feeId;

    /**
     * 挂号ID
     */
    private Long registerId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 退费金额
     */
    private BigDecimal refundAmount;

    /**
     * 退费原因
     */
    private String refundReason;

    /**
     * 退费状态
     *
     * 枚举 {@link RefundStatusEnum}
     * 1-待审核 2-已通过 3-已拒绝 4-已完成
     */
    private Integer refundStatus;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 申请人ID
     */
    private Long applyBy;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核人ID
     */
    private Long auditBy;

    /**
     * 审核意见
     */
    private String auditRemark;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户编号
     */
    private Long tenantId;

}