package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 门诊退费创建 Request VO")
@Data
public class OpRefundCreateReqVO {

    @Schema(description = "原支付ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "原支付ID不能为空")
    private Long paymentId;

    @Schema(description = "退费金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退费金额不能为空")
    private BigDecimal refundAmount;

    @Schema(description = "退费原因", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "退费原因不能为空")
    private String refundReason;

    @Schema(description = "申请人ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "申请人ID不能为空")
    private Long applyBy;

    @Schema(description = "备注")
    private String remark;

}
