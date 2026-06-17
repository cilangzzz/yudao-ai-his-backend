package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理评估 Response VO")
@Data
public class HisNursingAssessmentRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "评估编号", example = "NA202606170001")
    private String assessmentNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "评估类型", example = "1")
    private Integer assessmentType;

    @Schema(description = "评估类型名称", example = "跌倒评估")
    private String assessmentTypeName;

    @Schema(description = "评估名称", example = "跌倒风险评估")
    private String assessmentName;

    @Schema(description = "总分", example = "15")
    private Integer totalScore;

    @Schema(description = "风险等级", example = "高风险")
    private String riskLevel;

    @Schema(description = "评估详情(JSON格式)")
    private String assessmentDetail;

    @Schema(description = "评估项目明细(JSON格式)")
    private String items;

    @Schema(description = "评估护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "评估护士姓名", example = "王护士")
    private String nurseName;

    @Schema(description = "评估时间")
    private LocalDateTime assessmentTime;

    @Schema(description = "下次评估时间")
    private LocalDateTime nextAssessmentTime;

    @Schema(description = "护理措施建议")
    private String measureSuggestion;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // ==================== 状态判断 ====================

    @Schema(description = "是否高风险")
    private Boolean highRisk;

    @Schema(description = "是否中风险")
    private Boolean mediumRisk;

    @Schema(description = "是否需要干预")
    private Boolean needsIntervention;

    @Schema(description = "是否需要再次评估")
    private Boolean needsReassessment;
}