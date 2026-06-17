package cn.iocoder.yudao.module.his.controller.admin.payment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 门诊退费审核 Request VO")
@Data
public class OpRefundAuditReqVO {

    @Schema(description = "是否通过", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审核结果不能为空")
    private Boolean approved;

    @Schema(description = "审核人ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审核人ID不能为空")
    private Long auditBy;

    @Schema(description = "审核意见")
    private String auditRemark;

}
