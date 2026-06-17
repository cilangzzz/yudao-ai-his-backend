package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 退费记录 Response VO
 */
@Schema(description = "管理后台 - 退费记录 Response VO")
@Data
public class OpRefundRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "退费单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "R202606180001")
    private String refundNo;

    @Schema(description = "退费类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer refundType;

    @Schema(description = "退费类型名称", example = "全额退费")
    private String refundTypeName;

    @Schema(description = "原支付ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long paymentId;

    @Schema(description = "费用ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "退费金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    private BigDecimal refundAmount;

    @Schema(description = "医保退费金额", example = "60.00")
    private BigDecimal insuranceRefundAmount;

    @Schema(description = "自费退费金额", example = "40.00")
    private BigDecimal selfRefundAmount;

    @Schema(description = "原支付总金额", example = "167.00")
    private BigDecimal originalPaymentAmount;

    @Schema(description = "退费明细数量", example = "2")
    private Integer refundItemCount;

    @Schema(description = "支付方式", example = "4")
    private Integer payType;

    @Schema(description = "支付方式名称", example = "医保")
    private String payTypeName;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "原发票号", example = "FP202606170001")
    private String invoiceNo;

    @Schema(description = "退费发票号", example = "TF202606180001")
    private String cancelInvoiceNo;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "退费原因", example = "患者要求退费")
    private String refundReason;

    @Schema(description = "退费状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer refundStatus;

    @Schema(description = "退费状态名称", example = "待审核")
    private String refundStatusName;

    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime applyTime;

    @Schema(description = "申请人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long applyBy;

    @Schema(description = "申请人姓名", example = "王收费员")
    private String applyByName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人ID", example = "1")
    private Long auditBy;

    @Schema(description = "审核人姓名", example = "张管理员")
    private String auditByName;

    @Schema(description = "审核意见", example = "同意退费")
    private String auditRemark;

    @Schema(description = "完成时间")
    private LocalDateTime completeTime;

    @Schema(description = "操作员ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "王操作员")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "退费明细列表")
    private List<OpRefundItemRespVO> refundItems;

}