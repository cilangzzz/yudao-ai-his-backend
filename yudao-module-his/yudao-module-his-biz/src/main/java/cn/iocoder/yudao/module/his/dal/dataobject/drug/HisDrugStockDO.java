package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 药品库存 DO
 *
 * 药品库存按批次管理，记录每个批次的库存数量、有效期等信息
 */
@TableName("his_drug_stock")
@KeySequence("his_drug_stock_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugStockDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

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
     * 批号
     */
    private String batchNo;

    /**
     * 有效期
     */
    private LocalDate expiryDate;

    /**
     * 生产日期
     */
    private LocalDate productionDate;

    /**
     * 库存数量
     */
    private BigDecimal quantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 货架位置
     */
    private String storageLocation;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 状态:1-正常 2-近效期 3-过期
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DrugStockStatusEnum}
     */
    private Integer status;

    // ==================== 业务方法 ====================

    /**
     * 是否库存充足
     */
    public boolean hasStock(BigDecimal requiredQuantity) {
        return quantity != null && quantity.compareTo(requiredQuantity) >= 0;
    }

    /**
     * 是否正常状态
     */
    public boolean isNormal() {
        return status != null && status == 1;
    }

    /**
     * 是否近效期
     */
    public boolean isNearExpiry() {
        return status != null && status == 2;
    }

    /**
     * 是否过期
     */
    public boolean isExpired() {
        return status != null && status == 3;
    }

    /**
     * 是否可用（库存充足且状态正常或近效期）
     */
    public boolean isUsable(BigDecimal requiredQuantity) {
        return hasStock(requiredQuantity) && !isExpired();
    }

    /**
     * 计算库存金额（零售价）
     */
    public BigDecimal calculateRetailAmount() {
        if (quantity == null || retailPrice == null) {
            return BigDecimal.ZERO;
        }
        return quantity.multiply(retailPrice);
    }

    /**
     * 计算库存金额（采购价）
     */
    public BigDecimal calculatePurchaseAmount() {
        if (quantity == null || purchasePrice == null) {
            return BigDecimal.ZERO;
        }
        return quantity.multiply(purchasePrice);
    }

}