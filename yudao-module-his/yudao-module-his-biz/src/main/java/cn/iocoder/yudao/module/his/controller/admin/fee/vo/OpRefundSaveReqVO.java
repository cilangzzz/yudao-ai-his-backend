package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 退费记录 新增 Request VO
 */
@Schema(description = "管理后台 - 退费记录新增 Request VO")
@Data
public class OpRefundSaveReqVO {

    @Schema(description = "原支付ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "原支付ID不能为空")
    private Long paymentId;

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

    @Schema(description = "退费金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    @NotNull(message = "退费金额不能为空")
    private BigDecimal refundAmount;

    @Schema(description = "退费原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "患者要求退费")
    @NotNull(message = "退费原因不能为空")
    private String refundReason;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}