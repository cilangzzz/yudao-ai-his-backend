package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预交金 Response VO")
@Data
public class HisPrepaymentRespVO {

    @Schema(description = "预交金ID")
    private Long id;

    @Schema(description = "预交金单号")
    private String prepayNo;

    @Schema(description = "住院ID")
    private Long admissionId;

    @Schema(description = "患者ID")
    private Long patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "支付方式")
    private Integer payType;

    @Schema(description = "支付状态")
    private Integer payStatus;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "操作员ID")
    private Long operatorId;

    @Schema(description = "操作员姓名")
    private String operatorName;

    @Schema(description = "退款金额")
    private BigDecimal refundAmount;

    @Schema(description = "退款时间")
    private LocalDateTime refundTime;

    @Schema(description = "退款人ID")
    private Long refundBy;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
