package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 采购审批 Request VO
 */
@Schema(description = "管理后台 - 采购审批 Request VO")
@Data
public class HisDrugPurchaseApproveReqVO {

    @Schema(description = "采购ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "采购ID不能为空")
    private Long id;

    @Schema(description = "审批人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审批人ID不能为空")
    private Long auditBy;

    @Schema(description = "是否通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "审批结果不能为空")
    private Boolean approved;

}