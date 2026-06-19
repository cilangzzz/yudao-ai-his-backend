package cn.iocoder.yudao.module.his.controller.admin.department.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 科室分页请求 VO
 */
@Schema(description = "管理后台 - HIS科室分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDepartmentPageReqVO extends PageParam {

    @Schema(description = "科室名称", example = "内科")
    private String name;

    @Schema(description = "科室编码", example = "DEPT001")
    private String deptCode;

    @Schema(description = "科室类型：1-临床科室 2-医技科室 3-行政科室 4-后勤科室", example = "1")
    private Integer type;

    @Schema(description = "状态：1-正常 2-停用", example = "1")
    private Integer status;

}