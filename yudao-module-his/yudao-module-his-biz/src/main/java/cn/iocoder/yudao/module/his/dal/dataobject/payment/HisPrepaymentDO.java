package cn.iocoder.yudao.module.his.dal.dataobject.payment;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.module.his.enums.PayTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预交金记录 DO（住院）
 *
 * @author yudao
 */
@TableName("his_prepayment")
@KeySequence("his_prepayment_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisPrepaymentDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 预交金单号
     */
    private String prepayNo;

    /**
     * 住院ID
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
     * 金额
     */
    private BigDecimal amount;

    /**
     * 支付方式
     *
     * 枚举 {@link PayTypeEnum}
     * 1-现金 2-微信 3-支付宝 4-银行卡
     */
    private Integer payType;

    /**
     * 支付状态
     *
     * 1-正常 2-已退
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 退款人ID
     */
    private Long refundBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户编号
     */
    private Long tenantId;

}