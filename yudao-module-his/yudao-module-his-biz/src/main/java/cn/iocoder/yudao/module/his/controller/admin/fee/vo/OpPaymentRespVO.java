package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录 Response VO
 */
@Schema(description = "管理后台 - 支付记录 Response VO")
@Data
public class OpPaymentRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "支付单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "P202606180001")
    private String paymentNo;

    @Schema(description = "费用ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "支付金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    private BigDecimal payAmount;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer payType;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保卡号", example = "1234567890")
    private String insuranceNo;

    @Schema(description = "医保支付金额", example = "50.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "50.00")
    private BigDecimal selfAmount;

    @Schema(description = "支付状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer payStatus;

    @Schema(description = "支付时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime payTime;

    @Schema(description = "收费员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long cashierId;

    @Schema(description = "收费员姓名", example = "王收费员")
    private String cashierName;

    @Schema(description = "发票号", example = "INV001")
    private String invoiceNo;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}