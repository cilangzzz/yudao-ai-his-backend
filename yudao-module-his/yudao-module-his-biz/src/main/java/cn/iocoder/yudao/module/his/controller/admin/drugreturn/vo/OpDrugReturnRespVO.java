package cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退药申请 Response VO
 */
@Schema(description = "管理后台 - 退药申请 Response VO")
@Data
public class OpDrugReturnRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "退药单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "TY202606180001")
    private String returnNo;

    @Schema(description = "退药类型：1-门诊退药 2-住院退药", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer returnType;

    @Schema(description = "退药类型名称", example = "门诊退药")
    private String returnTypeName;

    @Schema(description = "原处方ID")
    private Long prescriptionId;

    @Schema(description = "原处方编号")
    private String prescriptionNo;

    @Schema(description = "原发药ID")
    private Long dispenseId;

    @Schema(description = "原发药单号")
    private String dispenseNo;

    @Schema(description = "挂号ID（门诊）")
    private Long registerId;

    @Schema(description = "住院ID（住院）")
    private Long admissionId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "身份证号", example = "110101199001011234")
    private String idCardNo;

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "开单医生ID")
    private Long doctorId;

    @Schema(description = "开单医生姓名")
    private String doctorName;

    @Schema(description = "药房ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long pharmacyId;

    @Schema(description = "药房名称", example = "门诊药房")
    private String pharmacyName;

    @Schema(description = "退药总数量", example = "10")
    private BigDecimal totalQuantity;

    @Schema(description = "退药总金额", example = "255.00")
    private BigDecimal totalAmount;

    @Schema(description = "退款金额", example = "255.00")
    private BigDecimal refundAmount;

    @Schema(description = "退药原因", example = "药品不良反应，需要停药")
    private String returnReason;

    @Schema(description = "退药原因类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer returnReasonType;

    @Schema(description = "退药原因类型名称", example = "药品不良反应")
    private String returnReasonTypeName;

    @Schema(description = "退药状态：1-待审核 2-审核通过 3-审核拒绝 4-已入库 5-已退款 6-已取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer returnStatus;

    @Schema(description = "退药状态名称", example = "待审核")
    private String returnStatusName;

    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime applyTime;

    @Schema(description = "申请人ID")
    private Long applyBy;

    @Schema(description = "申请人姓名")
    private String applyByName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人ID")
    private Long auditBy;

    @Schema(description = "审核人姓名")
    private String auditByName;

    @Schema(description = "审核意见")
    private String auditRemark;

    @Schema(description = "入库时间")
    private LocalDateTime inboundTime;

    @Schema(description = "入库人ID")
    private Long inboundBy;

    @Schema(description = "入库人姓名")
    private String inboundByName;

    @Schema(description = "退款时间")
    private LocalDateTime refundTime;

    @Schema(description = "退款人ID")
    private Long refundBy;

    @Schema(description = "退款人姓名")
    private String refundByName;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消人ID")
    private Long cancelBy;

    @Schema(description = "取消原因")
    private String cancelReason;

    @Schema(description = "附件URL")
    private String attachmentUrl;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
