package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 退费明细 Request VO
 *
 * 用于部分退费时指定退费的明细项和数量
 */
@Schema(description = "管理后台 - 退费明细 Request VO")
@Data
public class OpRefundItemReqVO {

    @Schema(description = "原支付明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "原支付明细ID不能为空")
    private Long paymentItemId;

    @Schema(description = "费用明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "费用明细ID不能为空")
    private Long feeItemId;

    @Schema(description = "退费数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "退费数量不能为空")
    private BigDecimal refundQuantity;

    @Schema(description = "退费金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    @NotNull(message = "退费金额不能为空")
    private BigDecimal refundAmount;

    @Schema(description = "退费原因", example = "患者要求退费")
    private String refundReason;

}