package cn.iocoder.yudao.module.his.controller.admin.vital.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 生命体征新增/修改 Request VO")
@Data
public class HisVitalSignSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    // ==================== 生命体征数据 ====================

    @Schema(description = "体温(°C)", example = "36.5")
    private BigDecimal temperature;

    @Schema(description = "脉搏(次/分)", example = "80")
    private Integer pulse;

    @Schema(description = "呼吸(次/分)", example = "18")
    private Integer respiration;

    @Schema(description = "收缩压(mmHg)", example = "120")
    private Integer systolicBp;

    @Schema(description = "舒张压(mmHg)", example = "80")
    private Integer diastolicBp;

    @Schema(description = "血氧饱和度(%)", example = "98.5")
    private BigDecimal oxygenSaturation;

    @Schema(description = "体重(kg)", example = "65.5")
    private BigDecimal weight;

    @Schema(description = "身高(cm)", example = "170")
    private BigDecimal height;

    @Schema(description = "BMI指数", example = "22.6")
    private BigDecimal bmi;

    @Schema(description = "疼痛评分(0-10)", example = "3")
    private Integer painScore;

    @Schema(description = "意识状态", example = "清醒")
    private String consciousness;

    // ==================== 测量信息 ====================

    @Schema(description = "测量时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "测量时间不能为空")
    private LocalDateTime measureTime;

    @Schema(description = "测量护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "测量护士姓名", example = "王护士")
    private String nurseName;

    @Schema(description = "备注")
    private String remark;
}