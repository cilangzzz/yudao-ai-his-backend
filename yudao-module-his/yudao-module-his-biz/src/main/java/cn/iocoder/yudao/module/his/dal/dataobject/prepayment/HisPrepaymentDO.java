package cn.iocoder.yudao.module.his.dal.dataobject.prepayment;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院预交金记录 DO
 *
 * 用于住院患者的预缴费用管理，支持多种支付方式
 *
 * @author yudao
 */
@TableName(value = "his_prepayment", autoResultMap = true)
@KeySequence("his_prepayment_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisPrepaymentDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 预交金编号
     */
    private String prepaymentNo;

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
     * 预交金额
     */
    private BigDecimal amount;

    /**
     * 剩余金额
     */
    private BigDecimal balanceAmount;

    /**
     * 支付方式
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PaymentTypeEnum}
     * 1-现金 2-银行卡 3-医保卡 4-微信 5-支付宝 6-混合支付
     */
    private Integer paymentType;

    /**
     * 支付渠道编码
     */
    private String paymentChannel;

    /**
     * 支付流水号
     */
    private String paymentNo;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PrepaymentStatusEnum}
     * 1-已缴纳 2-已使用 3-已退还 4-已结算
     */
    private Integer status;

    /**
     * 已使用金额
     */
    private BigDecimal usedAmount;

    /**
     * 已退还金额
     */
    private BigDecimal refundAmount;

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

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
     * 备注
     */
    private String remark;

    // ========== 业务方法 ==========

    /**
     * 是否已缴纳
     */
    public boolean isPaid() {
        return status != null && status == 1;
    }

    /**
     * 是否已退还
     */
    public boolean isRefunded() {
        return status != null && status == 3;
    }

    /**
     * 是否已结算
     */
    public boolean isSettled() {
        return status != null && status == 4;
    }

    /**
     * 获取可用余额
     */
    public BigDecimal getAvailableBalance() {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal used = usedAmount != null ? usedAmount : BigDecimal.ZERO;
        BigDecimal refunded = refundAmount != null ? refundAmount : BigDecimal.ZERO;
        return amount.subtract(used).subtract(refunded);
    }

}
