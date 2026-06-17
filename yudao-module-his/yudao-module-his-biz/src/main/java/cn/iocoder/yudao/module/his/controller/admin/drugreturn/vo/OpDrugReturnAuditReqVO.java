package cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 退药审核 Request VO
 */
@Schema(description = "管理后台 - 退药审核 Request VO")
@Data
public class OpDrugReturnAuditReqVO {

    @Schema(description = "退药ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "退药ID不能为空")
    private Long returnId;

    @Schema(description = "审核结果：1-通过 2-拒绝", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审核结果不能为空")
    private Integer auditResult;

    @Schema(description = "审核意见", example = "同意退药")
    private String auditRemark;

    @Schema(description = "审核类型：1-药师审核 2-科室审核 3-财务审核", example = "1")
    private Integer auditType;

}
