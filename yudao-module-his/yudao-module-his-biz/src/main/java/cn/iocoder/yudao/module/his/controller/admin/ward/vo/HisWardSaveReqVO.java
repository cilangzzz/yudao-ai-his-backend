package cn.iocoder.yudao.module.his.controller.admin.ward.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 病区创建/修改 Request VO")
@Data
public class HisWardSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "病区编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "W001")
    @NotBlank(message = "病区编码不能为空")
    private String wardCode;

    @Schema(description = "病区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "内科一病区")
    @NotBlank(message = "病区名称不能为空")
    private String wardName;

    @Schema(description = "所属科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "所属科室不能为空")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "楼层", example = "3F")
    private String floor;

    @Schema(description = "床位总数", example = "30")
    private Integer bedCount;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "备注信息")
    private String remark;
}
