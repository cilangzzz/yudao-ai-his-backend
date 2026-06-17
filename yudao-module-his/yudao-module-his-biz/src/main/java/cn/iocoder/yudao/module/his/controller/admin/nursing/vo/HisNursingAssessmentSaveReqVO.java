package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理评估新增/修改 Request VO")
@Data
public class HisNursingAssessmentSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "评估类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "评估类型不能为空")
    private Integer assessmentType;

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
}