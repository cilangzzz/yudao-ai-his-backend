package cn.iocoder.yudao.module.his.controller.admin.admission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 入院记录 Response VO")
@Data
public class HisAdmissionRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "住院号", requiredMode = Schema.RequiredMode.REQUIRED, example = "A001")
    private String admissionNo;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "身份证号", example = "123456789012345678")
    private String idCardNo;

    @Schema(description = "关联门诊挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "入院日期")
    private LocalDateTime admissionDate;

    @Schema(description = "入院科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long admissionDept;

    @Schema(description = "科室名称", example = "内科")
    private String admissionDeptName;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "床位ID", example = "1")
    private Long bedId;

    @Schema(description = "床号", example = "01")
    private String bedNo;

    @Schema(description = "入院诊断", example = "肺炎")
    private String admissionDiagnosis;

    @Schema(description = "入院诊断ICD-10编码", example = "J18.9")
    private String diagnosisCode;

    @Schema(description = "主治医师ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long attendingDoctor;

    @Schema(description = "主治医师姓名", example = "李医生")
    private String attendingDoctorName;

    @Schema(description = "住院医师ID", example = "2")
    private Long residentDoctor;

    @Schema(description = "住院医师姓名", example = "王医生")
    private String residentDoctorName;

    @Schema(description = "责任护士ID", example = "3")
    private Long nurseId;

    @Schema(description = "责任护士姓名", example = "张护士")
    private String nurseName;

    @Schema(description = "入院方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer admissionWay;

    @Schema(description = "入院方式名称", example = "门诊")
    private String admissionWayName;

    @Schema(description = "入院情况", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Integer admissionCondition;

    @Schema(description = "入院情况名称", example = "一般")
    private String admissionConditionName;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保号", example = "123456")
    private String insuranceNo;

    @Schema(description = "预交金总额", example = "1000.00")
    private BigDecimal depositAmount;

    @Schema(description = "总费用", example = "5000.00")
    private BigDecimal totalFee;

    @Schema(description = "出院日期")
    private LocalDateTime dischargeDate;

    @Schema(description = "出院科室ID", example = "1")
    private Long dischargeDept;

    @Schema(description = "出院诊断", example = "肺炎痊愈")
    private String dischargeDiagnosis;

    @Schema(description = "出院诊断ICD-10编码", example = "J18.9")
    private String dischargeCode;

    @Schema(description = "出院方式", example = "1")
    private Integer dischargeWay;

    @Schema(description = "出院方式名称", example = "医嘱出院")
    private String dischargeWayName;

    @Schema(description = "入院状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer admissionStatus;

    @Schema(description = "入院状态名称", example = "在院")
    private String admissionStatusName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "住院天数", example = "5")
    private Integer hospitalDays;
}