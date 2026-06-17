package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 支付明细 Response VO")
@Data
public class OpPaymentItemRespVO {

    @Schema(description = "明细ID")
    private Long id;

    @Schema(description = "支付记录ID")
    private Long paymentId;

    @Schema(description = "项目编码")
    private String itemCode;

    @Schema(description = "项目名称")
    private String itemName;

    @Schema(description = "项目类别")
    private Integer itemCategory;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "计价单位")
    private String unit;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "数量")
    private BigDecimal quantity;

    @Schema(description = "费用明细金额")
    private BigDecimal feeAmount;

    @Schema(description = "本次支付金额")
    private BigDecimal payAmount;

    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "医保支付金额")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额")
    private BigDecimal selfAmount;

    @Schema(description = "支付方式")
    private Integer payType;

    @Schema(description = "支付状态")
    private Integer payStatus;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "执行科室名称")
    private String executionDeptName;

    @Schema(description = "开单医生姓名")
    private String doctorName;

}
