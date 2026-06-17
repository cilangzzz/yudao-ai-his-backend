package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 腕带扫描验证 Request VO")
@Data
public class WristbandVerifyReqVO {

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "腕带编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "WB202606170001")
    @NotNull(message = "腕带编码不能为空")
    private String wristbandCode;
}