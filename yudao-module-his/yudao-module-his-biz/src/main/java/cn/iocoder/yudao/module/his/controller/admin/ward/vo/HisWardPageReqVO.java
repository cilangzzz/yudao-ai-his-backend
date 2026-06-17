package cn.iocoder.yudao.module.his.controller.admin.ward.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 病区分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HisWardPageReqVO extends PageParam {

    @Schema(description = "病区编码", example = "W001")
    private String wardCode;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "所属科室ID", example = "1")
    private Long deptId;

    @Schema(description = "状态", example = "1")
    private Integer status;
}
