package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 门诊支付更新 Request VO")
@Data
public class OpPaymentUpdateReqVO {

    @Schema(description = "支付ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "支付ID不能为空")
    private Long id;

    @Schema(description = "发票号")
    private String invoiceNo;

    @Schema(description = "备注")
    private String remark;

}
