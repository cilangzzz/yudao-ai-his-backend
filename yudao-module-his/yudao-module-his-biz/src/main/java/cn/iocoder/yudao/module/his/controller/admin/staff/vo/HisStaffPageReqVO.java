package cn.iocoder.yudao.module.his.controller.admin.staff.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 医护人员分页请求 VO
 */
@Schema(description = "管理后台 - HIS医护人员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisStaffPageReqVO extends PageParam {

    @Schema(description = "姓名", example = "张医生")
    private String name;

    @Schema(description = "工号", example = "STAFF001")
    private String staffCode;

    @Schema(description = "人员类型：1-医生 2-护士 3-技师 4-药剂师 5-行政人员", example = "1")
    private Integer type;

    @Schema(description = "所属科室ID", example = "1")
    private Long deptId;

    @Schema(description = "状态：1-在职 2-离职 3-退休", example = "1")
    private Integer status;

}