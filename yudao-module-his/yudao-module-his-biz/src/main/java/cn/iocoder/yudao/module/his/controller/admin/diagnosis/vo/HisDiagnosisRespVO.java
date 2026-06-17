package cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 诊断记录响应 VO
 */
@Schema(description = "管理后台 - HIS诊断记录 Response VO")
@Data
public class HisDiagnosisRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "就诊ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long encounterId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "诊断类型：1-门诊诊断 2-入院诊断 3-出院诊断 4-修正诊断 5-补充诊断", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer diagnosisType;

    @Schema(description = "ICD-10诊断编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "J00.X00")
    private String diagnosisCode;

    @Schema(description = "诊断名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "急性上呼吸道感染")
    private String diagnosisName;

    @Schema(description = "诊断序号：1=主诊断，2=第一副诊断...", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer diagnosisSeq;

    @Schema(description = "诊断状态：1-初步诊断 2-确定诊断 3-修正诊断", example = "1")
    private Integer diagnosisStatus;

    @Schema(description = "诊断医生ID", example = "1")
    private Long diagnoseDoctor;

    @Schema(description = "诊断时间", example = "2026-06-18 10:00:00")
    private LocalDateTime diagnoseTime;

    @Schema(description = "备注", example = "患者自述症状明显")
    private String remarks;

    @Schema(description = "是否为主诊断", example = "true")
    private Boolean primaryDiagnosis;

    @Schema(description = "是否为确定诊断", example = "true")
    private Boolean confirmed;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
