package cn.iocoder.yudao.module.his.controller.admin.department.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 科室保存请求 VO
 */
@Schema(description = "管理后台 - HIS科室保存 Request VO")
@Data
public class HisDepartmentSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "科室编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DEPT001")
    @NotBlank(message = "科室编码不能为空")
    private String deptCode;

    @Schema(description = "科室名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "内科")
    @NotBlank(message = "科室名称不能为空")
    private String deptName;

    @Schema(description = "科室简称", example = "内")
    private String deptShortName;

    @Schema(description = "科室类型：1-临床科室 2-医技科室 3-行政科室 4-后勤科室", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "科室类型不能为空")
    private Integer deptType;

    @Schema(description = "上级科室ID", example = "0")
    private Long parentId;

    @Schema(description = "科室层级", example = "1")
    private Integer deptLevel;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "科室电话", example = "025-12345678")
    private String phone;

    @Schema(description = "科室位置", example = "门诊楼1楼")
    private String location;

    @Schema(description = "编制床位数", example = "30")
    private Integer bedCount;

    @Schema(description = "状态：1-正常 2-停用", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "科室备注")
    private String remark;

}