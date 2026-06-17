package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 门诊支付 Response VO")
@Data
public class OpPaymentRespVO {

    @Schema(description = "支付ID")
    private Long id;

    @Schema(description = "支付单号")
    private String paymentNo;

    @Schema(description = "费用ID")
    private Long feeId;

    @Schema(description = "挂号ID")
    private Long registerId;

    @Schema(description = "患者ID")
    private Long patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付方式")
    private Integer payType;

    @Schema(description = "医保类型")
    private Integer insuranceType;

    @Schema(description = "医保卡号")
    private String insuranceNo;

    @Schema(description = "医保支付金额")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额")
    private BigDecimal selfAmount;

    @Schema(description = "支付状态")
    private Integer payStatus;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "收费员ID")
    private Long cashierId;

    @Schema(description = "收费员姓名")
    private String cashierName;

    @Schema(description = "发票号")
    private String invoiceNo;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
