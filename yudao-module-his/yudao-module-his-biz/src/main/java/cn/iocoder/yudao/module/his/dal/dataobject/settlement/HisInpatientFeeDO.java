package cn.iocoder.yudao.module.his.dal.dataobject.settlement;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 住院费用明细 DO
 *
 * 用于住院患者的费用明细管理，支持各类费用项目的记录和结算
 *
 * @author yudao
 */
@TableName("his_inpatient_fee")
@KeySequence("his_inpatient_fee_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisInpatientFeeDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 费用单号
     */
    private String feeNo;

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

    // ========== 医嘱关联 ==========

    /**
     * 医嘱ID
     */
    private Long orderId;

    /**
     * 医嘱编号
     */
    private String orderNo;

    // ========== 费用项目信息 ==========

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InpatientFeeItemTypeEnum}
     * 1-药品 2-检查 3-检验 4-诊疗 5-护理 6-手术 7-床位 8-其他
     */
    private Integer itemType;

    /**
     * 项目分类
     */
    private String itemCategory;

    // ========== 费用明细 ==========

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 应付金额
     */
    private BigDecimal payAmount;

    // ========== 医保信息 ==========

    /**
     * 医保编码
     */
    private String insuranceCode;

    /**
     * 医保类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceTypeEnum}
     * 1-甲类 2-乙类 3-自费
     */
    private Integer insuranceType;

    /**
     * 医保报销比例
     */
    private BigDecimal insuranceRatio;

    /**
     * 医保支付金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 自付金额
     */
    private BigDecimal selfAmount;

    // ========== 执行信息 ==========

    /**
     * 执行科室ID
     */
    private Long deptId;

    /**
     * 执行科室名称
     */
    private String deptName;

    /**
     * 开单医生ID
     */
    private Long doctorId;

    /**
     * 开单医生姓名
     */
    private String doctorName;

    /**
     * 执行时间
     */
    private LocalDateTime executeTime;

    // ========== 费用状态 ==========

    /**
     * 费用状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InpatientFeeStatusEnum}
     * 0-未结算 1-已结算 2-已退费
     */
    private Integer feeStatus;

    /**
     * 结算单ID
     */
    private Long settlementId;

    /**
     * 费用日期
     */
    private LocalDate feeDate;

    /**
     * 备注
     */
    private String remark;

    // ========== 业务方法 ==========

    /**
     * 是否未结算
     */
    public boolean isUnsettled() {
        return feeStatus != null && feeStatus == 0;
    }

    /**
     * 是否已结算
     */
    public boolean isSettled() {
        return feeStatus != null && feeStatus == 1;
    }

    /**
     * 是否已退费
     */
    public boolean isRefunded() {
        return feeStatus != null && feeStatus == 2;
    }

    /**
     * 计算应付金额
     */
    public void calculatePayAmount() {
        if (totalAmount == null) {
            payAmount = BigDecimal.ZERO;
            return;
        }
        BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        payAmount = totalAmount.subtract(discount);
    }

    /**
     * 计算医保和自付金额
     */
    public void calculateInsuranceAmount() {
        if (payAmount == null) {
            insuranceAmount = BigDecimal.ZERO;
            selfAmount = BigDecimal.ZERO;
            return;
        }
        if (insuranceRatio != null && insuranceRatio.compareTo(BigDecimal.ZERO) > 0) {
            insuranceAmount = payAmount.multiply(insuranceRatio).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            selfAmount = payAmount.subtract(insuranceAmount);
        } else {
            insuranceAmount = BigDecimal.ZERO;
            selfAmount = payAmount;
        }
    }

}
