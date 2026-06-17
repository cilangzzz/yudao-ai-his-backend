package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 门诊费用明细 Response VO
 */
@Schema(description = "管理后台 - 门诊费用明细 Response VO")
@Data
public class OpFeeItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "费用ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "收费项目ID", example = "1")
    private Long chargeItemId;

    @Schema(description = "项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "挂号费")
    private String itemName;

    @Schema(description = "项目类别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer itemCategory;

    @Schema(description = "规格", example = "10mg")
    private String spec;

    @Schema(description = "计价单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "次")
    private String unit;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "50.00")
    private BigDecimal unitPrice;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private BigDecimal quantity;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    private BigDecimal amount;

    @Schema(description = "优惠金额", example = "10.00")
    private BigDecimal discountAmount;

    @Schema(description = "实收金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "90.00")
    private BigDecimal payAmount;

    @Schema(description = "医保编码", example = "YB001")
    private String insuranceCode;

    @Schema(description = "医保定价", example = "45.00")
    private BigDecimal insurancePrice;

    @Schema(description = "医保类别", example = "1")
    private Integer insuranceCategory;

    @Schema(description = "执行科室ID", example = "1")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "检验科")
    private String executionDeptName;

    @Schema(description = "开单医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "开单医生姓名", example = "李医生")
    private String doctorName;

    @Schema(description = "来源类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sourceType;

    @Schema(description = "来源ID", example = "1")
    private Long sourceId;

    @Schema(description = "来源明细ID", example = "1")
    private Long sourceItemId;

    @Schema(description = "收费状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer feeItemStatus;

    @Schema(description = "收费时间")
    private LocalDateTime feeTime;

    @Schema(description = "退费时间")
    private LocalDateTime refundTime;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}