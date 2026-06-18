package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 出院申请创建/更新 Request VO")
@Data
public class HisDischargeApplySaveReqVO {

    @Schema(description = "出院申请ID（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "入院记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "入院记录ID不能为空")
    private Long admissionId;

    @Schema(description = "拟定出院日期", example = "2026-06-18T10:00:00")
    private LocalDateTime dischargeDate;

    @Schema(description = "出院方式: 1医嘱出院/2自动出院/3转院/4死亡", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "出院方式不能为空")
    private Integer dischargeWay;

    @Schema(description = "出院诊断", example = "肺炎痊愈")
    private String dischargeDiagnosis;

    @Schema(description = "出院诊断编码(ICD-10)", example = "J18.9")
    private String dischargeDiagnosisCode;

    @Schema(description = "出院诊断备注", example = "患者经过治疗后痊愈")
    private String dischargeDiagnosisRemark;

    @Schema(description = "治疗结果: 1治愈/2好转/3未愈/4死亡/5其他", example = "1")
    private Integer treatmentResult;

    @Schema(description = "转院医院(转院时填写)", example = "北京协和医院")
    private String transferHospital;

    @Schema(description = "转院原因", example = "需进一步治疗")
    private String transferReason;

    @Schema(description = "死亡原因(死亡时填写)", example = "心力衰竭")
    private String deathReason;

    @Schema(description = "死亡时间", example = "2026-06-18T10:00:00")
    private LocalDateTime deathTime;

    @Schema(description = "申请医生ID", example = "1")
    private Long applyDoctorId;

    @Schema(description = "申请医生姓名", example = "张医生")
    private String applyDoctorName;

    @Schema(description = "申请原因", example = "患者病情好转，可以出院")
    private String applyReason;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}