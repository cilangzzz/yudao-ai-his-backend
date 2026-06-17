package cn.iocoder.yudao.module.his.controller.admin.patient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 患者响应 VO
 */
@Schema(description = "管理后台 - HIS患者 Response VO")
@Data
public class HisPatientRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "患者编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "P202606170001")
    private String patientNo;

    @Schema(description = "身份证号", example = "320102199001011234")
    private String idCardNo;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "性别：1-男 2-女 9-未知", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer gender;

    @Schema(description = "出生日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1990-01-01")
    private LocalDate birthDate;

    @Schema(description = "年龄", example = "36")
    private Integer age;

    @Schema(description = "年龄单位：1-岁 2-月 3-天", example = "1")
    private Integer ageUnit;

    @Schema(description = "民族", example = "汉族")
    private String nation;

    @Schema(description = "国籍", example = "中国")
    private String nationality;

    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(description = "家庭住址", example = "江苏省南京市")
    private String address;

    @Schema(description = "婚姻状况：1-未婚 2-已婚 3-离异 4-丧偶", example = "2")
    private Integer maritalStatus;

    @Schema(description = "职业", example = "工程师")
    private String occupation;

    @Schema(description = "联系人姓名", example = "李四")
    private String contactName;

    @Schema(description = "联系人电话", example = "13900139000")
    private String contactPhone;

    @Schema(description = "联系人关系", example = "配偶")
    private String contactRelation;

    @Schema(description = "血型：1-A 2-B 3-AB 4-O 5-不详", example = "1")
    private Integer bloodType;

    @Schema(description = "RH血型：1-阳性 2-阴性", example = "1")
    private Integer rhBlood;

    @Schema(description = "医保类型：1-城镇职工 2-城镇居民 3-新农合 4-自费", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保卡号", example = "3201001234567890")
    private String insuranceNo;

    @Schema(description = "过敏史", example = "青霉素过敏")
    private String allergyHistory;

    @Schema(description = "既往病史", example = "高血压")
    private String medicalHistory;

    @Schema(description = "家族史", example = "糖尿病家族史")
    private String familyHistory;

    @Schema(description = "照片URL", example = "https://example.com/photo.jpg")
    private String photoUrl;

    @Schema(description = "状态：1-正常 2-注销 3-死亡", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "备注", example = "建档备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}