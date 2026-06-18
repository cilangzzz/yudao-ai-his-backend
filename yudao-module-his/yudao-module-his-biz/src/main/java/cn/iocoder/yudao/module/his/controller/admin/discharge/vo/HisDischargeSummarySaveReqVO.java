package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 出院小结保存 Request VO
 */
@Schema(description = "管理后台 - 出院小结保存 Request VO")
@Data
public class HisDischargeSummarySaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "出院ID", required = true, example = "1")
    @NotNull(message = "出院ID不能为空")
    private Long dischargeId;

    @Schema(description = "住院ID", required = true, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "患者ID", required = true, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "入院诊断", example = "肺炎")
    private String admissionDiagnosis;

    @Schema(description = "出院诊断", required = true, example = "肺炎治愈")
    @NotEmpty(message = "出院诊断不能为空")
    private String dischargeDiagnosis;

    @Schema(description = "入院情况")
    private String admissionCondition;

    @Schema(description = "诊疗经过")
    private String treatmentProcess;

    @Schema(description = "出院情况", required = true)
    @NotEmpty(message = "出院情况不能为空")
    private String dischargeCondition;

    @Schema(description = "出院医嘱")
    private String dischargeAdvice;

    @Schema(description = "随访建议")
    private String followUp;

    @Schema(description = "用药指导")
    private String medicationAdvice;

    @Schema(description = "书写医生ID", required = true, example = "1")
    @NotNull(message = "书写医生不能为空")
    private Long authorDoctor;

    @Schema(description = "书写医生姓名", example = "李医生")
    private String authorDoctorName;

    @Schema(description = "书写时间")
    private LocalDateTime authorTime;
}