package cn.iocoder.yudao.module.his.controller.admin.staff.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 医护人员保存请求 VO
 */
@Schema(description = "管理后台 - HIS医护人员保存 Request VO")
@Data
public class HisStaffSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "工号", requiredMode = Schema.RequiredMode.REQUIRED, example = "STAFF001")
    @NotBlank(message = "工号不能为空")
    private String staffCode;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张医生")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(description = "性别：1-男 2-女", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "性别不能为空")
    private Integer gender;

    @Schema(description = "出生日期", example = "1980-01-01")
    private String birthDate;

    @Schema(description = "身份证号", example = "320102198001011234")
    private String idCard;

    @Schema(description = "所属科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "所属科室不能为空")
    private Long deptId;

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

    @Schema(description = "状态：1-在职 2-离职 3-退休", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}