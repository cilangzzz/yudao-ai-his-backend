package cn.iocoder.yudao.module.his.controller.admin.exam.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 检查申请保存请求 VO
 */
@Schema(description = "管理后台 - HIS检查申请保存 Request VO")
@Data
public class HisExamRequestSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "就诊类型:1-门诊 2-住院 3-急诊", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "就诊类型不能为空")
    private Integer encounterType;

    @Schema(description = "就诊ID(挂号ID/住院ID)", example = "100")
    private Long encounterId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "申请科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "申请科室ID不能为空")
    private Long deptId;

    @Schema(description = "申请科室名称", example = "内科")
    private String deptName;

    @Schema(description = "申请医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "申请医生ID不能为空")
    private Long doctorId;

    @Schema(description = "申请医生姓名", example = "张医生")
    private String doctorName;

    @Schema(description = "检查类型:1-影像检查 2-检验检查 3-病理检查 4-功能检查", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "检查类型不能为空")
    private Integer examType;

    @Schema(description = "检查类别(CT/MRI/X光/B超/血常规等)", example = "CT")
    private String examCategory;

    @Schema(description = "临床诊断", example = "肺炎")
    private String clinicalDiagnosis;

    @Schema(description = "临床症状", example = "发热、咳嗽")
    private String clinicalSymptom;

    @Schema(description = "检查目的", example = "排查肺部感染")
    private String examPurpose;

    @Schema(description = "检查部位", example = "胸部")
    private String examPart;

    @Schema(description = "紧急程度:0-普通 1-急诊 2-加急", example = "0")
    private Integer urgency;

    @Schema(description = "是否急诊检查:0-否 1-是", example = "0")
    private Integer isStat;

    @Schema(description = "标本类型(检验检查用)", example = "血液")
    private String sampleType;

    @Schema(description = "执行科室ID", example = "20")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "放射科")
    private String executionDeptName;

    @Schema(description = "预约检查时间", example = "2026-06-20 10:00:00")
    private LocalDateTime appointmentTime;

    @Schema(description = "备注", example = "申请备注")
    private String remark;

    @Schema(description = "相关临床病史", example = "既往有肺结核病史")
    private String clinicalHistory;

    @Schema(description = "过敏史", example = "碘造影剂过敏")
    private String allergyHistory;

    @Schema(description = "用药史", example = "正在使用抗凝药物")
    private String medicationHistory;

    @Schema(description = "妊娠标志:0-未妊娠 1-已妊娠(女性患者必填)", example = "0")
    private Integer pregnancyFlag;

    @Schema(description = "是否需要造影:0-否 1-是", example = "0")
    private Integer contrastFlag;

    @Schema(description = "造影剂类型", example = "碘造影剂")
    private String contrastType;

    @Schema(description = "造影剂用量", example = "100")
    private BigDecimal contrastAmount;

    @Schema(description = "是否需要空腹:0-否 1-是", example = "0")
    private Integer fastingFlag;

    @Schema(description = "检查准备说明", example = "检查前禁食4小时")
    private String preparationInstruction;

    @Schema(description = "特殊说明", example = "携带既往影像资料")
    private String specialInstruction;

    @Schema(description = "检查项目明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检查项目不能为空")
    @Valid
    private List<HisExamRequestItemSaveReqVO> items;

}