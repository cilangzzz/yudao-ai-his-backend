package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 出院小结响应 VO
 */
@Schema(description = "管理后台 - 出院小结响应 VO")
@Data
public class HisDischargeSummaryRespVO {

    @Schema(description = "主键ID", required = true, example = "1")
    private Long id;

    @Schema(description = "出院ID", required = true, example = "1")
    private Long dischargeId;

    @Schema(description = "住院ID", required = true, example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", required = true, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "入院诊断", example = "肺炎")
    private String admissionDiagnosis;

    @Schema(description = "出院诊断", example = "肺炎治愈")
    private String dischargeDiagnosis;

    @Schema(description = "入院情况")
    private String admissionCondition;

    @Schema(description = "诊疗经过")
    private String treatmentProcess;

    @Schema(description = "出院情况")
    private String dischargeCondition;

    @Schema(description = "出院医嘱")
    private String dischargeAdvice;

    @Schema(description = "随访建议")
    private String followUp;

    @Schema(description = "用药指导")
    private String medicationAdvice;

    @Schema(description = "书写医生ID", example = "1")
    private Long authorDoctor;

    @Schema(description = "书写医生姓名", example = "李医生")
    private String authorDoctorName;

    @Schema(description = "书写时间")
    private LocalDateTime authorTime;

    @Schema(description = "审核医生ID", example = "1")
    private Long reviewDoctor;

    @Schema(description = "审核医生姓名", example = "王主任")
    private String reviewDoctorName;

    @Schema(description = "审核时间")
    private LocalDateTime reviewTime;

    @Schema(description = "状态：1-待审核 2-已审核", example = "1")
    private Integer summaryStatus;

    @Schema(description = "状态名称", example = "待审核")
    private String summaryStatusName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 获取状态名称
     */
    public String getSummaryStatusName() {
        if (summaryStatus == null) {
            return null;
        }
        switch (summaryStatus) {
            case 1:
                return "待审核";
            case 2:
                return "已审核";
            default:
                return "未知";
        }
    }
}