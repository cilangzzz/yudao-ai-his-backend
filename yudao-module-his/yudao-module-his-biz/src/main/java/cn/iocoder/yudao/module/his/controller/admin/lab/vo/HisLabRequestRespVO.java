package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 检验申请响应 VO
 */
@Schema(description = "管理后台 - HIS检验申请 Response VO")
@Data
public class HisLabRequestRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "申请单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "LB202606180001")
    private String requestNo;

    @Schema(description = "就诊类型:1-门诊 2-住院 3-急诊", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sourceType;

    @Schema(description = "就诊ID", example = "100")
    private Long sourceId;

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

    @Schema(description = "患者床号(住院)", example = "A区101床")
    private String bedNo;

    @Schema(description = "申请科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long deptId;

    @Schema(description = "申请科室名称", example = "内科")
    private String deptName;

    @Schema(description = "申请医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long doctorId;

    @Schema(description = "申请医生姓名", example = "张医生")
    private String doctorName;

    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime requestTime;

    @Schema(description = "检验类别", example = "血常规")
    private String labCategory;

    @Schema(description = "检验类型:1-常规检验 2-急诊检验 3-特检", example = "1")
    private Integer labType;

    @Schema(description = "临床诊断", example = "上呼吸道感染")
    private String diagnosis;

    @Schema(description = "诊断编码", example = "J06.9")
    private String diagnosisCode;

    @Schema(description = "临床症状", example = "发热、咳嗽")
    private String clinicalSymptom;

    @Schema(description = "检验目的", example = "排查感染")
    private String clinicalPurpose;

    @Schema(description = "紧急程度:0-普通 1-急诊 2-加急", example = "0")
    private Integer urgency;

    @Schema(description = "是否急诊检验:0-否 1-是", example = "0")
    private Integer isStat;

    // ========== 标本信息 ==========

    @Schema(description = "标本类型", example = "1")
    private Integer specimenType;

    @Schema(description = "标本类型名称", example = "血液")
    private String sampleType;

    @Schema(description = "标本ID", example = "1000")
    private Long specimenId;

    @Schema(description = "标本条码", example = "SP202606180001")
    private String specimenBarcode;

    @Schema(description = "标本采集说明", example = "空腹采血")
    private String specimenNote;

    @Schema(description = "标本采集时间")
    private LocalDateTime collectTime;

    @Schema(description = "标本采集人ID", example = "200")
    private Long collectorId;

    @Schema(description = "标本采集人姓名", example = "李护士")
    private String collectorName;

    @Schema(description = "标本状态", example = "2")
    private Integer specimenStatus;

    @Schema(description = "标本接收时间")
    private LocalDateTime receiveTime;

    @Schema(description = "标本接收人姓名", example = "王技师")
    private String receiverName;

    @Schema(description = "标本拒收原因", example = "标本溶血")
    private String rejectReason;

    @Schema(description = "标本拒收时间")
    private LocalDateTime rejectTime;

    // ========== 执行信息 ==========

    @Schema(description = "执行科室ID", example = "20")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "检验科")
    private String executionDeptName;

    @Schema(description = "检验开始时间")
    private LocalDateTime startTime;

    @Schema(description = "检验结束时间")
    private LocalDateTime endTime;

    @Schema(description = "检验技师姓名", example = "王技师")
    private String technicianName;

    // ========== 报告信息 ==========

    @Schema(description = "报告ID", example = "100")
    private Long reportId;

    @Schema(description = "报告编号", example = "RP202606180001")
    private String reportNo;

    @Schema(description = "报告医生姓名", example = "陈医生")
    private String reportDoctorName;

    @Schema(description = "报告时间")
    private LocalDateTime reportTime;

    @Schema(description = "审核医生姓名", example = "刘医生")
    private String auditDoctorName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "报告状态", example = "3")
    private Integer reportStatus;

    // ========== 申请状态 ==========

    @Schema(description = "申请状态:1-已申请 2-已采集 3-已接收 4-检验中 5-已完成 6-已报告 7-已审核 8-已取消 9-已退费", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer requestStatus;

    // ========== 危急值信息 ==========

    @Schema(description = "是否有危急值:0-无 1-有", example = "0")
    private Integer criticalFlag;

    @Schema(description = "危急值内容", example = "白细胞计数异常偏高")
    private String criticalValueContent;

    @Schema(description = "危急值报告时间")
    private LocalDateTime criticalReportTime;

    @Schema(description = "危急值确认人姓名", example = "张医生")
    private String criticalConfirmUserName;

    @Schema(description = "危急值确认时间")
    private LocalDateTime criticalConfirmTime;

    // ========== 费用信息 ==========

    @Schema(description = "检验项目数", example = "5")
    private Integer itemCount;

    @Schema(description = "总金额", example = "100.00")
    private BigDecimal totalAmount;

    @Schema(description = "医保支付金额", example = "80.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "20.00")
    private BigDecimal selfAmount;

    @Schema(description = "支付状态:0-未支付 1-已支付 2-已退费", example = "0")
    private Integer payStatus;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付方式:1-现金 2-微信 3-支付宝 4-医保", example = "4")
    private Integer payType;

    // ========== 取消信息 ==========

    @Schema(description = "取消原因", example = "患者取消")
    private String cancelReason;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    // ========== 其他信息 ==========

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

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "检验项目明细列表")
    private List<HisLabRequestItemRespVO> items;

}