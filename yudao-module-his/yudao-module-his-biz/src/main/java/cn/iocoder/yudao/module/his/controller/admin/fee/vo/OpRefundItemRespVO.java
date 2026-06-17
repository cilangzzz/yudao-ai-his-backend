package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退费明细 Response VO
 */
@Schema(description = "管理后台 - 退费明细 Response VO")
@Data
public class OpRefundItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "退费记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long refundId;

    @Schema(description = "退费单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "R202606180001")
    private String refundNo;

    @Schema(description = "原支付ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long paymentId;

    @Schema(description = "原支付明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long paymentItemId;

    @Schema(description = "费用ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeId;

    @Schema(description = "费用明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeItemId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "收费项目ID", example = "1")
    private Long chargeItemId;

    @Schema(description = "项目编码", example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称", example = "专家挂号费")
    private String itemName;

    @Schema(description = "项目类别", example = "1")
    private Integer itemCategory;

    @Schema(description = "项目类别名称", example = "挂号费")
    private String itemCategoryName;

    @Schema(description = "规格", example = "盒")
    private String spec;

    @Schema(description = "计价单位", example = "次")
    private String unit;

    @Schema(description = "单价", example = "50.00")
    private BigDecimal unitPrice;

    @Schema(description = "原数量", example = "1")
    private BigDecimal originalQuantity;

    @Schema(description = "退费数量", example = "1")
    private BigDecimal refundQuantity;

    @Schema(description = "原金额", example = "50.00")
    private BigDecimal originalAmount;

    @Schema(description = "退费金额", example = "50.00")
    private BigDecimal refundAmount;

    @Schema(description = "医保退费金额", example = "30.00")
    private BigDecimal insuranceRefundAmount;

    @Schema(description = "自费退费金额", example = "20.00")
    private BigDecimal selfRefundAmount;

    @Schema(description = "支付方式", example = "4")
    private Integer payType;

    @Schema(description = "支付方式名称", example = "医保")
    private String payTypeName;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保卡号", example = "3301****0001")
    private String insuranceNo;

    @Schema(description = "医保编码", example = "YB001")
    private String insuranceCode;

    @Schema(description = "医保类别", example = "1")
    private Integer insuranceCategory;

    @Schema(description = "医保类别名称", example = "甲类")
    private String insuranceCategoryName;

    @Schema(description = "退费状态", example = "1")
    private Integer refundStatus;

    @Schema(description = "退费状态名称", example = "待审核")
    private String refundStatusName;

    @Schema(description = "退费原因", example = "患者要求退费")
    private String refundReason;

    @Schema(description = "执行科室ID", example = "1")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "内科")
    private String executionDeptName;

    @Schema(description = "开单医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "开单医生姓名", example = "李医生")
    private String doctorName;

    @Schema(description = "来源类型", example = "1")
    private Integer sourceType;

    @Schema(description = "来源类型名称", example = "挂号")
    private String sourceTypeName;

    @Schema(description = "来源ID", example = "1")
    private Long sourceId;

    @Schema(description = "来源明细ID", example = "1")
    private Long sourceItemId;

    @Schema(description = "退费完成时间")
    private LocalDateTime refundTime;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}