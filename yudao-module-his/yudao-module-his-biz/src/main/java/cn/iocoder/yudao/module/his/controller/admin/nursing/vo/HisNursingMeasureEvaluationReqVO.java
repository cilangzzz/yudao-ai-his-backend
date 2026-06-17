package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 护理措施效果评价 Request VO")
@Data
public class HisNursingMeasureEvaluationReqVO {

    @Schema(description = "措施ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "措施ID不能为空")
    private Long id;

    @Schema(description = "效果评价", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "效果评价不能为空")
    private Integer effectEvaluation;

    @Schema(description = "效果评价说明")
    private String effectDescription;
}