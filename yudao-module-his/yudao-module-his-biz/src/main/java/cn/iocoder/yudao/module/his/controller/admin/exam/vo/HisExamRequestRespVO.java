package cn.iocoder.yudao.module.his.controller.admin.exam.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 检查申请响应 VO
 */
@Schema(description = "管理后台 - HIS检查申请 Response VO")
@Data
public class HisExamRequestRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "申请单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "EX202606180001")
    private String requestNo;

    @Schema(description = "就诊类型:1-门诊 2-住院 3-急诊", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer encounterType;

    @Schema(description = "就诊ID", example = "100")
    private Long encounterId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String patientName;

    @Schema(description = "患者性别:1-男 2-女", example = "1")
    private Integer patientGender;

    @Schema(description = "患者年龄", example = "35")
    private Integer patientAge;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "申请科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long deptId;

    @Schema(description = "申请科室名称", example = "内科")
    private String deptName;

    @Schema(description = "申请医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long doctorId;

    @Schema(description = "申请医生姓名", example = "张医生")
    private String doctorName;

    @Schema(description = "检查类型:1-影像检查 2-检验检查 3-病理检查 4-功能检查", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer examType;

    @Schema(description = "检查类别", example = "CT")
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

    @Schema(description = "标本类型", example = "血液")
    private String sampleType;

    @Schema(description = "标本采集时间")
    private LocalDateTime sampleCollectTime;

    @Schema(description = "标本采集护士姓名", example = "李护士")
    private String sampleCollectNurseName;

    @Schema(description = "执行科室ID", example = "20")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "放射科")
    private String executionDeptName;

    @Schema(description = "检查室/机房", example = "CT1室")
    private String executionRoom;

    @Schema(description = "预约检查时间")
    private LocalDateTime appointmentTime;

    @Schema(description = "签到时间")
    private LocalDateTime checkInTime;

    @Schema(description = "检查开始时间")
    private LocalDateTime startTime;

    @Schema(description = "检查结束时间")
    private LocalDateTime endTime;

    @Schema(description = "检查技师姓名", example = "王技师")
    private String technicianName;

    @Schema(description = "报告医生姓名", example = "陈医生")
    private String reportDoctorName;

    @Schema(description = "审核医生姓名", example = "刘医生")
    private String auditDoctorName;

    @Schema(description = "申请状态:1-已申请 2-已预约 3-已签到 4-检查中 5-已完成 6-已报告 7-已审核 8-已取消 9-已退费", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer requestStatus;

    @Schema(description = "报告状态:0-未报告 1-初步报告 2-最终报告 3-已审核", example = "0")
    private Integer reportStatus;

    @Schema(description = "报告编号", example = "RP202606180001")
    private String reportNo;

    @Schema(description = "报告时间")
    private LocalDateTime reportTime;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "总金额", example = "300.00")
    private BigDecimal totalAmount;

    @Schema(description = "医保支付金额", example = "240.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "60.00")
    private BigDecimal selfAmount;

    @Schema(description = "支付状态:0-未支付 1-已支付 2-已退费", example = "0")
    private Integer payStatus;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付方式:1-现金 2-微信 3-支付宝 4-医保", example = "4")
    private Integer payType;

    @Schema(description = "取消原因", example = "患者取消")
    private String cancelReason;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "备注", example = "申请备注")
    private String remark;

    @Schema(description = "临床病史", example = "既往有肺结核病史")
    private String clinicalHistory;

    @Schema(description = "过敏史", example = "碘造影剂过敏")
    private String allergyHistory;

    @Schema(description = "用药史", example = "正在使用抗凝药物")
    private String medicationHistory;

    @Schema(description = "妊娠标志:0-未妊娠 1-已妊娠", example = "0")
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

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "检查项目明细列表")
    private List<HisExamRequestItemRespVO> items;

}
