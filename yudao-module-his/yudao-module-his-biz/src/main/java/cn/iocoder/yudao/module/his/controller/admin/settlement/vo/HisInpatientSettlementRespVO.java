package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院结算单响应 VO
 */
@Data
@ApiModel("住院结算单响应 VO")
public class HisInpatientSettlementRespVO {

    @ApiModelProperty(value = "结算单ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "结算单号", example = "JS202406180001")
    private String settlementNo;

    @ApiModelProperty(value = "入院记录ID", example = "100")
    private Long admissionId;

    @ApiModelProperty(value = "患者ID", example = "1")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名", example = "张三")
    private String patientName;

    @ApiModelProperty(value = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @ApiModelProperty(value = "身份证号", example = "110101199001011234")
    private String idCardNo;

    @ApiModelProperty(value = "入院日期")
    private LocalDateTime admissionDate;

    @ApiModelProperty(value = "出院日期")
    private LocalDateTime dischargeDate;

    @ApiModelProperty(value = "住院天数", example = "7")
    private Integer hospitalDays;

    @ApiModelProperty(value = "科室ID", example = "10")
    private Long deptId;

    @ApiModelProperty(value = "科室名称", example = "内科")
    private String deptName;

    @ApiModelProperty(value = "病区ID", example = "1")
    private Long wardId;

    @ApiModelProperty(value = "病区名称", example = "一病区")
    private String wardName;

    @ApiModelProperty(value = "床位ID", example = "1")
    private Long bedId;

    @ApiModelProperty(value = "床号", example = "01")
    private String bedNo;

    @ApiModelProperty(value = "主治医师ID", example = "100")
    private Long attendingDoctorId;

    @ApiModelProperty(value = "主治医师姓名", example = "王医生")
    private String attendingDoctorName;

    @ApiModelProperty(value = "医保类型", example = "1")
    private Integer insuranceType;

    @ApiModelProperty(value = "医保号", example = "YB123456")
    private String insuranceNo;

    // ========== 费用汇总 ==========

    @ApiModelProperty(value = "费用总额", example = "10000.00")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "西药费", example = "2000.00")
    private BigDecimal westernMedicineFee;

    @ApiModelProperty(value = "中药费", example = "500.00")
    private BigDecimal chineseMedicineFee;

    @ApiModelProperty(value = "检查费", example = "1500.00")
    private BigDecimal examinationFee;

    @ApiModelProperty(value = "检验费", example = "800.00")
    private BigDecimal laboratoryFee;

    @ApiModelProperty(value = "诊疗费", example = "1000.00")
    private BigDecimal treatmentFee;

    @ApiModelProperty(value = "护理费", example = "300.00")
    private BigDecimal nursingFee;

    @ApiModelProperty(value = "手术费", example = "3000.00")
    private BigDecimal surgeryFee;

    @ApiModelProperty(value = "床位费", example = "700.00")
    private BigDecimal bedFee;

    @ApiModelProperty(value = "材料费", example = "200.00")
    private BigDecimal materialFee;

    @ApiModelProperty(value = "其他费用", example = "500.00")
    private BigDecimal otherFee;

    // ========== 结算金额 ==========

    @ApiModelProperty(value = "优惠金额", example = "100.00")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "应付金额", example = "9900.00")
    private BigDecimal payableAmount;

    @ApiModelProperty(value = "预交金抵扣", example = "5000.00")
    private BigDecimal prepaymentAmount;

    @ApiModelProperty(value = "医保支付", example = "3000.00")
    private BigDecimal insuranceAmount;

    @ApiModelProperty(value = "自付金额", example = "2000.00")
    private BigDecimal selfAmount;

    @ApiModelProperty(value = "退费金额", example = "0.00")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "实收/应退金额", example = "2000.00")
    private BigDecimal actualAmount;

    // ========== 支付信息 ==========

    @ApiModelProperty(value = "支付方式", example = "1")
    private Integer paymentType;

    @ApiModelProperty(value = "支付流水号", example = "PAY202406180001")
    private String paymentNo;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentTime;

    // ========== 诊断信息 ==========

    @ApiModelProperty(value = "入院诊断", example = "肺炎")
    private String admissionDiagnosis;

    @ApiModelProperty(value = "出院诊断", example = "肺炎已治愈")
    private String dischargeDiagnosis;

    @ApiModelProperty(value = "ICD-10编码", example = "J18.9")
    private String diagnosisCode;

    // ========== 结算状态 ==========

    @ApiModelProperty(value = "结算状态", example = "0")
    private Integer settlementStatus;

    @ApiModelProperty(value = "结算类型", example = "1")
    private Integer settlementType;

    @ApiModelProperty(value = "发票号码", example = "FP202406180001")
    private String invoiceNo;

    // ========== 操作信息 ==========

    @ApiModelProperty(value = "操作员ID", example = "1")
    private Long operatorId;

    @ApiModelProperty(value = "操作员姓名", example = "操作员")
    private String operatorName;

    @ApiModelProperty(value = "结算时间")
    private LocalDateTime settlementTime;

    @ApiModelProperty(value = "备注", example = "正常出院")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}