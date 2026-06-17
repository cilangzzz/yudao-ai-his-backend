package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 门诊退费 Response VO")
@Data
public class OpRefundRespVO {

    @Schema(description = "退费ID")
    private Long id;

    @Schema(description = "退费单号")
    private String refundNo;

    @Schema(description = "原支付ID")
    private Long paymentId;

    @Schema(description = "费用ID")
    private Long feeId;

    @Schema(description = "挂号ID")
    private Long registerId;

    @Schema(description = "患者ID")
    private Long patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "退费金额")
    private BigDecimal refundAmount;

    @Schema(description = "退费原因")
    private String refundReason;

    @Schema(description = "退费状态")
    private Integer refundStatus;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "申请人ID")
    private Long applyBy;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人ID")
    private Long auditBy;

    @Schema(description = "审核意见")
    private String auditRemark;

    @Schema(description = "完成时间")
    private LocalDateTime completeTime;

    @Schema(description = "操作员ID")
    private Long operatorId;

    @Schema(description = "操作员姓名")
    private String operatorName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
