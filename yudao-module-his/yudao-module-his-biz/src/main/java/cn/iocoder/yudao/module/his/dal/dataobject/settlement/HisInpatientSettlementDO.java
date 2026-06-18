package cn.iocoder.yudao.module.his.dal.dataobject.settlement;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院结算主表 DO
 *
 * 用于住院患者的出院结算管理，记录费用汇总和结算信息
 *
 * @author yudao
 */
@TableName("his_inpatient_settlement")
@KeySequence("his_inpatient_settlement_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisInpatientSettlementDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 结算单号
     */
    private String settlementNo;

    /**
     * 入院记录ID
     */
    private Long admissionId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 住院号
     */
    private String inpatientNo;

    /**
     * 身份证号
     */
    private String idCardNo;

    // ========== 住院时间 ==========

    /**
     * 入院日期
     */
    private LocalDateTime admissionDate;

    /**
     * 出院日期
     */
    private LocalDateTime dischargeDate;

    /**
     * 住院天数
     */
    private Integer hospitalDays;

    // ========== 科室信息 ==========

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 病区ID
     */
    private Long wardId;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 床位ID
     */
    private Long bedId;

    /**
     * 床号
     */
    private String bedNo;

    // ========== 医生信息 ==========

    /**
     * 主治医师ID
     */
    private Long attendingDoctorId;

    /**
     * 主治医师姓名
     */
    private String attendingDoctorName;

    // ========== 医保信息 ==========

    /**
     * 医保类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceCategoryEnum}
     * 1-城镇职工 2-城镇居民 3-新农合 4-自费
     */
    private Integer insuranceType;

    /**
     * 医保号
     */
    private String insuranceNo;

    // ========== 费用汇总 ==========

    /**
     * 费用总额
     */
    private BigDecimal totalAmount;

    /**
     * 西药费
     */
    private BigDecimal westernMedicineFee;

    /**
     * 中药费
     */
    private BigDecimal chineseMedicineFee;

    /**
     * 检查费
     */
    private BigDecimal examinationFee;

    /**
     * 检验费
     */
    private BigDecimal laboratoryFee;

    /**
     * 诊疗费
     */
    private BigDecimal treatmentFee;

    /**
     * 护理费
     */
    private BigDecimal nursingFee;

    /**
     * 手术费
     */
    private BigDecimal surgeryFee;

    /**
     * 床位费
     */
    private BigDecimal bedFee;

    /**
     * 材料费
     */
    private BigDecimal materialFee;

    /**
     * 其他费用
     */
    private BigDecimal otherFee;

    // ========== 结算金额 ==========

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 应付金额
     */
    private BigDecimal payableAmount;

    /**
     * 预交金抵扣
     */
    private BigDecimal prepaymentAmount;

    /**
     * 医保支付
     */
    private BigDecimal insuranceAmount;

    /**
     * 自付金额
     */
    private BigDecimal selfAmount;

    /**
     * 退费金额
     */
    private BigDecimal refundAmount;

    /**
     * 实收/应退金额
     */
    private BigDecimal actualAmount;

    // ========== 支付信息 ==========

    /**
     * 支付方式
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PaymentTypeEnum}
     * 1-现金 2-银行卡 3-医保卡 4-微信 5-支付宝 6-混合支付
     */
    private Integer paymentType;

    /**
     * 支付流水号
     */
    private String paymentNo;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    // ========== 诊断信息 ==========

    /**
     * 入院诊断
     */
    private String admissionDiagnosis;

    /**
     * 出院诊断
     */
    private String dischargeDiagnosis;

    /**
     * ICD-10编码
     */
    private String diagnosisCode;

    // ========== 结算状态 ==========

    /**
     * 结算状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.SettlementStatusEnum}
     * 0-未结算 1-已结算 2-已退费 3-已作废
     */
    private Integer settlementStatus;

    /**
     * 结算类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.SettlementTypeEnum}
     * 1-出院结算 2-中途结算 3-挂账结算
     */
    private Integer settlementType;

    /**
     * 发票号码
     */
    private String invoiceNo;

    // ========== 操作信息 ==========

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;

    /**
     * 备注
     */
    private String remark;

    // ========== 业务方法 ==========

    /**
     * 是否未结算
     */
    public boolean isUnsettled() {
        return settlementStatus != null && settlementStatus == 0;
    }

    /**
     * 是否已结算
     */
    public boolean isSettled() {
        return settlementStatus != null && settlementStatus == 1;
    }

    /**
     * 是否已退费
     */
    public boolean isRefunded() {
        return settlementStatus != null && settlementStatus == 2;
    }

    /**
     * 是否已作废
     */
    public boolean isCancelled() {
        return settlementStatus != null && settlementStatus == 3;
    }

    /**
     * 是否出院结算
     */
    public boolean isDischargeSettlement() {
        return settlementType != null && settlementType == 1;
    }

    /**
     * 是否中途结算
     */
    public boolean isMidwaySettlement() {
        return settlementType != null && settlementType == 2;
    }

    /**
     * 计算应付金额
     */
    public void calculatePayableAmount() {
        if (totalAmount == null) {
            payableAmount = BigDecimal.ZERO;
            return;
        }
        BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        payableAmount = totalAmount.subtract(discount);
    }

    /**
     * 计算实收/应退金额
     */
    public void calculateActualAmount() {
        if (payableAmount == null) {
            actualAmount = BigDecimal.ZERO;
            return;
        }
        BigDecimal prepayment = prepaymentAmount != null ? prepaymentAmount : BigDecimal.ZERO;
        BigDecimal insurance = insuranceAmount != null ? insuranceAmount : BigDecimal.ZERO;
        BigDecimal selfPay = selfAmount != null ? selfAmount : BigDecimal.ZERO;
        BigDecimal refund = refundAmount != null ? refundAmount : BigDecimal.ZERO;

        // 实收 = 自付金额 - 预交金抵扣
        // 如果预交金大于自付金额，则为负数表示应退
        actualAmount = selfPay.subtract(prepayment).subtract(refund);
    }

    /**
     * 是否需要补缴费用
     */
    public boolean needPay() {
        return actualAmount != null && actualAmount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 是否需要退还费用
     */
    public boolean needRefund() {
        return actualAmount != null && actualAmount.compareTo(BigDecimal.ZERO) < 0;
    }

}
