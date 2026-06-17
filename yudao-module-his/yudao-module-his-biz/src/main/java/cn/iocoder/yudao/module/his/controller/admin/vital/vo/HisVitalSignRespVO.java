package cn.iocoder.yudao.module.his.controller.admin.vital.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 生命体征 Response VO")
@Data
public class HisVitalSignRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院ID", example = "1")
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

    @Schema(description = "血压字符串", example = "120/80")
    private String bloodPressureStr;

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

    @Schema(description = "测量时间")
    private LocalDateTime measureTime;

    @Schema(description = "测量护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "测量护士姓名", example = "王护士")
    private String nurseName;

    // ==================== 异常标识 ====================

    @Schema(description = "异常标识", example = "0")
    private Integer abnormalFlag;

    @Schema(description = "异常项目")
    private String abnormalItems;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // ==================== 异常判断 ====================

    @Schema(description = "是否体温异常")
    private Boolean temperatureAbnormal;

    @Schema(description = "是否血压异常")
    private Boolean bloodPressureAbnormal;

    @Schema(description = "是否脉搏异常")
    private Boolean pulseAbnormal;

    @Schema(description = "是否呼吸异常")
    private Boolean respirationAbnormal;

    @Schema(description = "是否血氧异常")
    private Boolean oxygenSaturationAbnormal;

    @Schema(description = "是否有异常")
    private Boolean hasAbnormal;
}