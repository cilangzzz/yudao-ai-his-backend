package cn.iocoder.yudao.module.his.controller.admin.department.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 科室响应 VO
 */
@Schema(description = "管理后台 - HIS科室 Response VO")
@Data
public class HisDepartmentRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "科室编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DEPT001")
    private String deptCode;

    @Schema(description = "科室名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "内科")
    private String deptName;

    @Schema(description = "科室简称", example = "内")
    private String deptShortName;

    @Schema(description = "科室类型：1-临床科室 2-医技科室 3-行政科室 4-后勤科室", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer deptType;

    @Schema(description = "科室类型名称", example = "临床科室")
    private String deptTypeName;

    @Schema(description = "上级科室ID", example = "0")
    private Long parentId;

    @Schema(description = "上级科室名称", example = "医院")
    private String parentName;

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

    @Schema(description = "状态：1-正常 2-停用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "备注", example = "科室备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}