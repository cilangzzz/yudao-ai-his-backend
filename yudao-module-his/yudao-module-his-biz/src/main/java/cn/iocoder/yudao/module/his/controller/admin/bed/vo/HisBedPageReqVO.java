package cn.iocoder.yudao.module.his.controller.admin.bed.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 床位分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HisBedPageReqVO extends PageParam {

    @Schema(description = "床号", example = "01")
    private String bedNo;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "房间号", example = "101")
    private String roomNo;

    @Schema(description = "床位状态", example = "1")
    private Integer bedStatus;

    @Schema(description = "床位类型", example = "1")
    private Integer bedType;

    @Schema(description = "床位等级", example = "1")
    private Integer bedClass;
}
