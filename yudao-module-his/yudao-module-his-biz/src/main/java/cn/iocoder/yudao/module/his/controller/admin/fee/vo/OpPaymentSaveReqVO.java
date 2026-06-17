package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 支付记录 新增 Request VO
 */
@Schema(description = "管理后台 - 支付记录新增 Request VO")
@Data
public class OpPaymentSaveReqVO {

    @Schema(description = "费用ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "费用ID不能为空")
    private Long feeId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "挂号ID不能为空")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "支付金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    @NotNull(message = "支付金额不能为空")
    private BigDecimal payAmount;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保卡号", example = "1234567890")
    private String insuranceNo;

    @Schema(description = "医保支付金额", example = "50.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "50.00")
    private BigDecimal selfAmount;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}