package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 检验申请保存请求 VO
 */
@Schema(description = "管理后台 - HIS检验申请保存 Request VO")
@Data
public class HisLabRequestSaveReqVO {

    @Schema(description = "编号(更新时传递)", example = "1")
    private Long id;

    @Schema(description = "就诊类型:1-门诊 2-住院 3-急诊", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "就诊类型不能为空")
    private Integer sourceType;

    @Schema(description = "就诊ID(挂号ID/住院ID)", example = "100")
    private Long sourceId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者性别:1-男 2-女", example = "1")
    private Integer patientGender;

    @Schema(description = "患者年龄", example = "35")
    private Integer patientAge;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "患者床号(住院)", example = "A区101床")
    private String bedNo;

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

    @Schema(description = "检验类别(血常规/尿常规/生化/免疫等)", example = "血常规")
    private String labCategory;

    @Schema(description = "检验类型:1-常规检验 2-急诊检验 3-特检", example = "1")
    private Integer labType;

    @Schema(description = "临床诊断", example = "上呼吸道感染")
    private String diagnosis;

    @Schema(description = "诊断编码(ICD-10)", example = "J06.9")
    private String diagnosisCode;

    @Schema(description = "临床症状", example = "发热、咳嗽")
    private String clinicalSymptom;

    @Schema(description = "检验目的", example = "排查感染")
    private String clinicalPurpose;

    @Schema(description = "紧急程度:0-普通 1-急诊 2-加急", example = "0")
    private Integer urgency;

    @Schema(description = "是否急诊检验:0-否 1-是", example = "0")
    private Integer isStat;

    @Schema(description = "标本类型:1-血液 2-尿液 3-粪便 4-痰液 5-其他", example = "1")
    private Integer specimenType;

    @Schema(description = "标本类型名称", example = "血液")
    private String sampleType;

    @Schema(description = "标本采集说明", example = "空腹采血")
    private String specimenNote;

    @Schema(description = "执行科室ID", example = "20")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "检验科")
    private String executionDeptName;

    @Schema(description = "备注", example = "申请备注")
    private String remark;

    @Schema(description = "临床病史", example = "既往有糖尿病病史")
    private String clinicalHistory;

    @Schema(description = "过敏史", example = "青霉素过敏")
    private String allergyHistory;

    @Schema(description = "用药史", example = "正在使用抗生素")
    private String medicationHistory;

    @Schema(description = "是否空腹:0-否 1-是", example = "1")
    private Integer fastingFlag;

    @Schema(description = "特殊说明", example = "需采集静脉血")
    private String specialInstruction;

    @Schema(description = "检验项目明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检验项目不能为空")
    @Valid
    private List<HisLabRequestItemSaveReqVO> items;

}
