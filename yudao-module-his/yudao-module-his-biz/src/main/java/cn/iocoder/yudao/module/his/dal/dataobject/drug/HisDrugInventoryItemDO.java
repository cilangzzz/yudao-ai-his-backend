package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 盘点明细 DO
 *
 * 盘点单的药品明细信息
 */
@TableName("his_drug_inventory_item")
@KeySequence("his_drug_inventory_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugInventoryItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 盘点ID
     */
    private Long inventoryId;

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
     * 批号
     */
    private String batchNo;

    /**
     * 账面数量
     */
    private BigDecimal bookQuantity;

    /**
     * 实盘数量
     */
    private BigDecimal actualQuantity;

    /**
     * 差异数量（实盘-账面）
     */
    private BigDecimal differenceQuantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 差异金额（差异数量 * 零售价）
     */
    private BigDecimal differenceAmount;

    /**
     * 盘点结果:1-正常 2-盘盈 3-盘亏
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InventoryResultEnum}
     */
    private Integer inventoryResult;

    /**
     * 盘点状态:1-待盘点 2-已盘点
     */
    private Integer inventoryItemStatus;

    /**
     * 盘点人
     */
    private Long operatorId;

    /**
     * 盘点人姓名
     */
    private String operatorName;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否待盘点
     */
    public boolean isPending() {
        return inventoryItemStatus != null && inventoryItemStatus == 1;
    }

    /**
     * 是否已盘点
     */
    public boolean isCompleted() {
        return inventoryItemStatus != null && inventoryItemStatus == 2;
    }

    /**
     * 是否有差异
     */
    public boolean hasDifference() {
        return differenceQuantity != null && differenceQuantity.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * 是否盘盈
     */
    public boolean isProfit() {
        return differenceQuantity != null && differenceQuantity.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 是否盘亏
     */
    public boolean isLoss() {
        return differenceQuantity != null && differenceQuantity.compareTo(BigDecimal.ZERO) < 0;
    }

}