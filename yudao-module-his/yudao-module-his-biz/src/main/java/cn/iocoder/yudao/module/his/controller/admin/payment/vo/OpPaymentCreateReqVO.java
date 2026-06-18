package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "管理后台 - 门诊支付创建 Request VO")
@Data
public class OpPaymentCreateReqVO {

    @Schema(description = "费用ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "费用ID不能为空")
    private Long feeId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "挂号ID不能为空")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "支付金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "支付金额不能为空")
    private BigDecimal payAmount;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

    @Schema(description = "医保类型")
    private Integer insuranceType;

    @Schema(description = "医保卡号")
    private String insuranceNo;

    @Schema(description = "医保支付金额")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额")
    private BigDecimal selfAmount;

    @Schema(description = "收费员ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收费员ID不能为空")
    private Long cashierId;

    @Schema(description = "收费员姓名")
    private String cashierName;

    @Schema(description = "发票号")
    private String invoiceNo;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "支付明细列表")
    private List<OpPaymentItemCreateReqVO> items;

}
