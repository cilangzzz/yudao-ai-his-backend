package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理措施 Response VO")
@Data
public class HisNursingMeasureRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "措施编号", example = "NM202606170001")
    private String measureNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "关联评估ID", example = "1")
    private Long assessmentId;

    @Schema(description = "措施类型", example = "1")
    private Integer measureType;

    @Schema(description = "措施类型名称", example = "跌倒预防")
    private String measureTypeName;

    @Schema(description = "措施名称", example = "跌倒预防措施")
    private String measureName;

    @Schema(description = "措施内容")
    private String measureContent;

    @Schema(description = "执行频次", example = "每日2次")
    private String frequency;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "执行状态", example = "1")
    private Integer status;

    @Schema(description = "执行状态名称", example = "执行中")
    private String statusName;

    @Schema(description = "执行护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "执行护士姓名", example = "王护士")
    private String nurseName;

    @Schema(description = "效果评价", example = "1")
    private Integer effectEvaluation;

    @Schema(description = "效果评价名称", example = "有效")
    private String effectEvaluationName;

    @Schema(description = "效果评价说明")
    private String effectDescription;

    @Schema(description = "评价时间")
    private LocalDateTime evaluationTime;

    @Schema(description = "评价护士ID", example = "2")
    private Long evaluationNurseId;

    @Schema(description = "评价护士姓名", example = "李护士")
    private String evaluationNurseName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // ==================== 状态判断 ====================

    @Schema(description = "是否可以执行")
    private Boolean canExecute;

    @Schema(description = "是否执行中")
    private Boolean executing;

    @Schema(description = "是否已完成")
    private Boolean completed;

    @Schema(description = "是否需要评价")
    private Boolean needsEvaluation;
}