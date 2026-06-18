package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院结算单保存请求 VO
 */
@Data
@Schema(description = "管理后台 - 住院结算单保存请求 VO")
public class HisInpatientSettlementSaveReqVO {

    @Schema(description = "结算单ID（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "入院记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "入院记录ID不能为空")
    private Long admissionId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "患者姓名不能为空")
    private String patientName;

    @Schema(description = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @Schema(description = "身份证号", example = "110101199001011234")
    private String idCardNo;

    @Schema(description = "入院日期")
    private LocalDateTime admissionDate;

    @Schema(description = "出院日期")
    private LocalDateTime dischargeDate;

    @Schema(description = "住院天数", example = "7")
    private Integer hospitalDays;

    @Schema(description = "科室ID", example = "10")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "一病区")
    private String wardName;

    @Schema(description = "床位ID", example = "1")
    private Long bedId;

    @Schema(description = "床号", example = "01")
    private String bedNo;

    @Schema(description = "主治医师ID", example = "100")
    private Long attendingDoctorId;

    @Schema(description = "主治医师姓名", example = "王医生")
    private String attendingDoctorName;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保号", example = "YB123456")
    private String insuranceNo;

    @Schema(description = "费用总额", example = "10000.00")
    private BigDecimal totalAmount;

    @Schema(description = "西药费", example = "2000.00")
    private BigDecimal westernMedicineFee;

    @Schema(description = "中药费", example = "500.00")
    private BigDecimal chineseMedicineFee;

    @Schema(description = "检查费", example = "1500.00")
    private BigDecimal examinationFee;

    @Schema(description = "检验费", example = "800.00")
    private BigDecimal laboratoryFee;

    @Schema(description = "诊疗费", example = "1000.00")
    private BigDecimal treatmentFee;

    @Schema(description = "护理费", example = "300.00")
    private BigDecimal nursingFee;

    @Schema(description = "手术费", example = "3000.00")
    private BigDecimal surgeryFee;

    @Schema(description = "床位费", example = "700.00")
    private BigDecimal bedFee;

    @Schema(description = "材料费", example = "200.00")
    private BigDecimal materialFee;

    @Schema(description = "其他费用", example = "500.00")
    private BigDecimal otherFee;

    @Schema(description = "优惠金额", example = "100.00")
    private BigDecimal discountAmount;

    @Schema(description = "预交金抵扣", example = "5000.00")
    private BigDecimal prepaymentAmount;

    @Schema(description = "医保支付", example = "3000.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "2000.00")
    private BigDecimal selfAmount;

    @Schema(description = "结算类型", example = "1")
    private Integer settlementType;

    @Schema(description = "入院诊断", example = "肺炎")
    private String admissionDiagnosis;

    @Schema(description = "出院诊断", example = "肺炎已治愈")
    private String dischargeDiagnosis;

    @Schema(description = "ICD-10编码", example = "J18.9")
    private String diagnosisCode;

    @Schema(description = "备注", example = "正常出院")
    private String remark;

}