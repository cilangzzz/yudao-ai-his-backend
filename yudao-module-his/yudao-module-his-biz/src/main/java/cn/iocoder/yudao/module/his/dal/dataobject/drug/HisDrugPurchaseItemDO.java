package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 采购明细 DO
 *
 * 采购计划的药品明细信息
 */
@TableName("his_drug_purchase_item")
@KeySequence("his_drug_purchase_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugPurchaseItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 采购ID
     */
    private Long purchaseId;

    /**
     * 药品ID
     */
    private Long drugId;

    /**
     * 药品编码
     */
    private String drugCode;

    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 药品规格
     */
    private String drugSpec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 采购数量
     */
    private BigDecimal quantity;

    /**
     * 参考价格
     */
    private BigDecimal referencePrice;

    /**
     * 预算金额
     */
    private BigDecimal budgetAmount;

    /**
     * 备注
     */
    private String remark;

}