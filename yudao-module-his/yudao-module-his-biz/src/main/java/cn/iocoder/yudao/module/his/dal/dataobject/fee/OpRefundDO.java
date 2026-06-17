package cn.iocoder.yudao.module.his.dal.dataobject.fee;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退费记录 DO
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
public class OpRefundDO extends TenantBaseDO {

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
     * 退费类型
     *
     * 1-全额退费 2-部分退费
     */
    private Integer refundType;

    /**
     * 原支付ID
     */
    private Long paymentId;

    /**
     * 费用ID
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
     * 医保退费金额
     */
    private BigDecimal insuranceRefundAmount;

    /**
     * 自费退费金额
     */
    private BigDecimal selfRefundAmount;

    /**
     * 原支付总金额
     */
    private BigDecimal originalPaymentAmount;

    /**
     * 退费明细数量
     */
    private Integer refundItemCount;

    /**
     * 支付方式
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PayTypeEnum}
     * 1-现金 2-微信 3-支付宝 4-医保 5-银行卡
     */
    private Integer payType;

    /**
     * 医保类型
     */
    private Integer insuranceType;

    /**
     * 原发票号
     */
    private String invoiceNo;

    /**
     * 退费发票号
     */
    private String cancelInvoiceNo;

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 退费原因
     */
    private String refundReason;

    /**
     * 退费状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.RefundStatusEnum}
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

}
