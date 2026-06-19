package cn.iocoder.yudao.module.his.controller.admin.cds.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * CDS 处方校验结果 VO
 */
@Schema(description = "管理后台 - CDS处方校验结果")
@Data
public class CdsCheckRespVO {

    @Schema(description = "总体风险等级: 0-无风险 1-提示 2-警告 3-禁止")
    private Integer riskLevel;

    @Schema(description = "总体风险等级名称")
    private String riskLevelName;

    @Schema(description = "是否通过校验")
    private Boolean passed;

    @Schema(description = "药物相互作用校验结果")
    private List<InteractionResult> interactionResults;

    @Schema(description = "过敏校验结果")
    private List<AllergyResult> allergyResults;

    @Schema(description = "剂量校验结果")
    private List<DosageResult> dosageResults;

    @Schema(description = "配伍禁忌校验结果")
    private List<ContraindicationResult> contraindicationResults;

    @Schema(description = "药物相互作用结果")
    @Data
    public static class InteractionResult {

        @Schema(description = "药品A ID")
        private Long drugIdA;

        @Schema(description = "药品A名称")
        private String drugNameA;

        @Schema(description = "药品B ID")
        private Long drugIdB;

        @Schema(description = "药品B名称")
        private String drugNameB;

        @Schema(description = "相互作用类型")
        private String interactionType;

        @Schema(description = "严重程度: 轻度/中度/重度/禁忌")
        private String severity;

        @Schema(description = "风险等级: 1-提示 2-警告 3-禁止")
        private Integer riskLevel;

        @Schema(description = "相互作用描述")
        private String description;

        @Schema(description = "临床影响")
        private String clinicalEffect;

        @Schema(description = "处置建议")
        private String suggestion;
    }

    @Schema(description = "过敏校验结果")
    @Data
    public static class AllergyResult {

        @Schema(description = "药品ID")
        private Long drugId;

        @Schema(description = "药品名称")
        private String drugName;

        @Schema(description = "过敏源名称")
        private String allergenName;

        @Schema(description = "过敏反应")
        private String reaction;

        @Schema(description = "严重程度")
        private String severity;

        @Schema(description = "风险等级: 3-禁止")
        private Integer riskLevel;

        @Schema(description = "提示信息")
        private String message;
    }

    @Schema(description = "剂量校验结果")
    @Data
    public static class DosageResult {

        @Schema(description = "药品ID")
        private Long drugId;

        @Schema(description = "药品名称")
        private String drugName;

        @Schema(description = "开立剂量")
        private java.math.BigDecimal orderedDosage;

        @Schema(description = "剂量单位")
        private String dosageUnit;

        @Schema(description = "常用剂量范围")
        private String normalRange;

        @Schema(description = "单次最大剂量")
        private java.math.BigDecimal maxSingleDosage;

        @Schema(description = "日最大剂量")
        private java.math.BigDecimal maxDailyDosage;

        @Schema(description = "风险等级: 1-提示 2-警告")
        private Integer riskLevel;

        @Schema(description = "提示信息")
        private String message;
    }

    @Schema(description = "配伍禁忌结果")
    @Data
    public static class ContraindicationResult {

        @Schema(description = "药品A ID")
        private Long drugIdA;

        @Schema(description = "药品A名称")
        private String drugNameA;

        @Schema(description = "药品B ID")
        private Long drugIdB;

        @Schema(description = "药品B名称")
        private String drugNameB;

        @Schema(description = "禁忌类型")
        private String contraindicationType;

        @Schema(description = "风险等级: 3-禁止")
        private Integer riskLevel;

        @Schema(description = "禁忌原因")
        private String reason;

        @Schema(description = "处置建议")
        private String suggestion;
    }

}