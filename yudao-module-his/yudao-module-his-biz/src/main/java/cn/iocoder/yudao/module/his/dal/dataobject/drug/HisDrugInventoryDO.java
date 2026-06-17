package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 盘点记录 DO
 *
 * 药品库存盘点管理，记录盘点过程和结果
 */
@TableName("his_drug_inventory")
@KeySequence("his_drug_inventory_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugInventoryDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 盘点单号
     */
    private String inventoryNo;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 盘点日期
     */
    private LocalDate inventoryDate;

    /**
     * 盘点品种数
     */
    private Integer totalItems;

    /**
     * 盘盈品种数
     */
    private Integer profitItems;

    /**
     * 盘亏品种数
     */
    private Integer lossItems;

    /**
     * 盘盈金额
     */
    private BigDecimal profitAmount;

    /**
     * 盘亏金额
     */
    private BigDecimal lossAmount;

    /**
     * 状态:1-进行中 2-已完成
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InventoryStatusEnum}
     */
    private Integer inventoryStatus;

    /**
     * 盘点人
     */
    private Long operatorId;

    /**
     * 盘点人姓名
     */
    private String operatorName;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否进行中
     */
    public boolean isInProgress() {
        return inventoryStatus != null && inventoryStatus == 1;
    }

    /**
     * 是否已完成
     */
    public boolean isCompleted() {
        return inventoryStatus != null && inventoryStatus == 2;
    }

    /**
     * 是否可以完成盘点
     */
    public boolean canComplete() {
        return isInProgress();
    }

    /**
     * 是否有盘盈盘亏
     */
    public boolean hasDifference() {
        return (profitItems != null && profitItems > 0) || (lossItems != null && lossItems > 0);
    }

}