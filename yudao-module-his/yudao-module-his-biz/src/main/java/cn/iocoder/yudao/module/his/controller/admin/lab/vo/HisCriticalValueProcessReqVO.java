package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 危急值处理请求 VO
 */
@Schema(description = "管理后台 - 危急值处理请求")
@Data
public class HisCriticalValueProcessReqVO {

    @Schema(description = "危急值ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "危急值ID不能为空")
    private Long id;

    @Schema(description = "处理结果", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "处理结果不能为空")
    private String processResult;

}