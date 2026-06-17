package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 可退费金额查询 Response VO
 *
 * 用于展示某个支付记录中各明细项的可退费情况
 */
@Schema(description = "管理后台 - 可退费金额查询 Response VO")
@Data
public class RefundableItemRespVO {

    @Schema(description = "支付明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long paymentItemId;

    @Schema(description = "费用明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeItemId;

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

    @Schema(description = "原支付数量", example = "1")
    private BigDecimal originalQuantity;

    @Schema(description = "已退费数量", example = "0")
    private BigDecimal refundedQuantity;

    @Schema(description = "可退费数量", example = "1")
    private BigDecimal refundableQuantity;

    @Schema(description = "原支付金额", example = "50.00")
    private BigDecimal originalAmount;

    @Schema(description = "已退费金额", example = "0.00")
    private BigDecimal refundedAmount;

    @Schema(description = "可退费金额", example = "50.00")
    private BigDecimal refundableAmount;

    @Schema(description = "医保支付金额", example = "30.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "20.00")
    private BigDecimal selfAmount;

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

    @Schema(description = "是否可退费", example = "true")
    private Boolean canRefund;

    @Schema(description = "不可退费原因", example = "药品已使用")
    private String cannotRefundReason;

}