package cn.iocoder.yudao.module.his.controller.admin.bed.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 床位创建/修改 Request VO")
@Data
public class HisBedSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "床号", requiredMode = Schema.RequiredMode.REQUIRED, example = "01")
    @NotBlank(message = "床号不能为空")
    private String bedNo;

    @Schema(description = "病区ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "病区不能为空")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "房间号", example = "101")
    private String roomNo;

    @Schema(description = "床位类型", example = "1")
    private Integer bedType;

    @Schema(description = "床位等级", example = "1")
    private Integer bedClass;

    @Schema(description = "床位日单价", example = "50.00")
    private BigDecimal dailyPrice;

    @Schema(description = "床位状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "床位状态不能为空")
    private Integer bedStatus;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "备注", example = "备注信息")
    private String remark;
}
