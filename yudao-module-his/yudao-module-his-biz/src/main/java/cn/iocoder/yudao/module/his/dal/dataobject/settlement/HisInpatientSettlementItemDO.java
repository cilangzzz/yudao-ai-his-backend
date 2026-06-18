package cn.iocoder.yudao.module.his.dal.dataobject.settlement;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 住院结算明细 DO
 *
 * 结算单关联的费用明细记录
 *
 * @author yudao
 */
@TableName("his_inpatient_settlement_item")
@KeySequence("his_inpatient_settlement_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisInpatientSettlementItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 结算单ID
     */
    private Long settlementId;

    /**
     * 结算单号
     */
    private String settlementNo;

    /**
     * 费用明细ID
     */
    private Long feeId;

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
     */
    private Integer itemType;

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

    /**
     * 费用日期
     */
    private LocalDate feeDate;

    /**
     * 备注
     */
    private String remark;

}
