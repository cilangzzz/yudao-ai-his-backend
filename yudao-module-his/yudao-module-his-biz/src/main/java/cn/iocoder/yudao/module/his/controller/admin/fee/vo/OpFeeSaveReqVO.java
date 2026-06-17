package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 门诊费用 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 门诊费用新增/修改 Request VO")
@Data
public class OpFeeSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "费用单号", example = "F202606180001")
    private String feeNo;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "挂号ID不能为空")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "科室ID不能为空")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "总金额", example = "100.00")
    private BigDecimal totalAmount;

    @Schema(description = "优惠金额", example = "10.00")
    private BigDecimal discountAmount;

    @Schema(description = "应付金额", example = "90.00")
    private BigDecimal payAmount;

    @Schema(description = "医保支付金额", example = "50.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "40.00")
    private BigDecimal selfAmount;

    @Schema(description = "费用状态", example = "0")
    private Integer feeStatus;

}