package cn.iocoder.yudao.module.his.dal.dataobject.fee;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 支付明细 DO
 *
 * 记录每笔支付中各费用明细项的支付情况，建立支付记录与费用明细的关联关系
 *
 * @author yudao
 */
@TableName("op_payment_item")
@KeySequence("op_payment_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpPaymentItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 支付记录ID
     *
     * 关联 {@link OpPaymentDO#getId()}
     */
    private Long paymentId;

    /**
     * 费用ID
     *
     * 关联 {@link OpFeeDO#getId()}
     */
    private Long feeId;

    /**
     * 费用明细ID
     *
     * 关联 {@link OpFeeItemDO#getId()}
     */
    private Long feeItemId;

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
     * 收费项目ID
     */
    private Long chargeItemId;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目类别
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ChargeItemCategoryEnum}
     * 1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他
     */
    private Integer itemCategory;

    /**
     * 规格
     */
    private String spec;

    /**
     * 计价单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 费用明细金额
     */
    private BigDecimal feeAmount;

    /**
     * 本次支付金额
     */
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 医保支付金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 自付金额
     */
    private BigDecimal selfAmount;

    /**
     * 支付方式
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PayTypeEnum}
     * 1-现金 2-微信 3-支付宝 4-医保 5-银行卡
     */
    private Integer payType;

    /**
     * 医保类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceTypeEnum}
     * 1-城镇职工 2-城镇居民 3-新农合 4-自费
     */
    private Integer insuranceType;

    /**
     * 医保卡号
     */
    private String insuranceNo;

    /**
     * 医保编码
     */
    private String insuranceCode;

    /**
     * 医保类别
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceCategoryEnum}
     * 1-甲类 2-乙类 3-丙类
     */
    private Integer insuranceCategory;

    /**
     * 支付状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PaymentItemStatusEnum}
     * 0-待支付 1-已支付 2-已退费
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private java.time.LocalDateTime payTime;

    /**
     * 退费时间
     */
    private java.time.LocalDateTime refundTime;

    /**
     * 退费金额
     */
    private BigDecimal refundAmount;

    /**
     * 执行科室ID
     */
    private Long executionDeptId;

    /**
     * 执行科室名称
     */
    private String executionDeptName;

    /**
     * 开单医生ID
     */
    private Long doctorId;

    /**
     * 开单医生姓名
     */
    private String doctorName;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

}
