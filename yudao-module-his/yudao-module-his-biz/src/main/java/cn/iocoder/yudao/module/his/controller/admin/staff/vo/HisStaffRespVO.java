package cn.iocoder.yudao.module.his.controller.admin.staff.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 医护人员响应 VO
 */
@Schema(description = "管理后台 - HIS医护人员 Response VO")
@Data
public class HisStaffRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "工号", requiredMode = Schema.RequiredMode.REQUIRED, example = "STAFF001")
    private String staffCode;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张医生")
    private String name;

    @Schema(description = "性别：1-男 2-女", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer gender;

    @Schema(description = "性别名称", example = "男")
    private String genderName;

    @Schema(description = "出生日期", example = "1980-01-01")
    private String birthDate;

    @Schema(description = "身份证号", example = "320102198001011234")
    private String idCard;

    @Schema(description = "所属科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long deptId;

    @Schema(description = "所属科室名称", example = "内科")
    private String deptName;

    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(description = "职务", example = "主任")
    private String position;

    @Schema(description = "职称", example = "主任医师")
    private String title;

    @Schema(description = "专业特长", example = "心血管内科")
    private String specialty;

    @Schema(description = "人员类型：1-医生 2-护士 3-技师 4-药剂师 5-行政人员", example = "1")
    private Integer type;

    @Schema(description = "人员类型名称", example = "医生")
    private String typeName;

    @Schema(description = "状态：1-在职 2-离职 3-退休", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}