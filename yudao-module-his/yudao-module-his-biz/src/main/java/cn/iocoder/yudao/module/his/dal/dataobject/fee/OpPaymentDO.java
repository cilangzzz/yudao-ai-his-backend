package cn.iocoder.yudao.module.his.dal.dataobject.fee;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录 DO
 *
 * @author yudao
 */
@TableName("op_payment")
@KeySequence("op_payment_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpPaymentDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 支付单号
     */
    private String paymentNo;

    /**
     * 费用ID
     */
    private Long feeId;

    /**
     * 挂号ID
     */
    private Long registerId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付方式
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PayTypeEnum}
     * 1-现金 2-微信 3-支付宝 4-医保 5-银行卡
     */
    private Integer payType;

    /**
     * 医保类型
     */
    private Integer insuranceType;

    /**
     * 医保卡号
     */
    private String insuranceNo;

    /**
     * 医保支付金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 自付金额
     */
    private BigDecimal selfAmount;

    /**
     * 支付状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PaymentStatusEnum}
     * 1-成功 2-失败 3-已退费
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 收费员ID
     */
    private Long cashierId;

    /**
     * 收费员姓名
     */
    private String cashierName;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 备注
     */
    private String remark;

}
