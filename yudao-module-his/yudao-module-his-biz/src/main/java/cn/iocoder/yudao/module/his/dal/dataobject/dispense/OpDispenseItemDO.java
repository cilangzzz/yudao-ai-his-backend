package cn.iocoder.yudao.module.his.dal.dataobject.dispense;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 发药明细 DO
 *
 * 门诊发药药品明细，记录每条发药记录中的药品批次信息
 */
@TableName("op_dispense_item")
@KeySequence("op_dispense_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpDispenseItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 发药ID
     */
    private Long dispenseId;

    /**
     * 处方明细ID
     */
    private Long prescriptionItemId;

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
     * 发药数量
     */
    private BigDecimal quantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 有效期
     */
    private LocalDate expiryDate;

    /**
     * 货架位置
     */
    private String storageLocation;

    // ==================== 业务方法 ====================

    /**
     * 计算金额
     */
    public void calculateAmount() {
        if (quantity != null && unitPrice != null) {
            this.amount = quantity.multiply(unitPrice);
        }
    }

    /**
     * 是否已过期
     */
    public boolean isExpired() {
        return expiryDate != null && LocalDate.now().isAfter(expiryDate);
    }

    /**
     * 是否即将过期（30天内）
     */
    public boolean isNearExpiry() {
        if (expiryDate == null) {
            return false;
        }
        LocalDate warningDate = LocalDate.now().plusDays(30);
        return !LocalDate.now().isAfter(expiryDate) && !expiryDate.isAfter(warningDate);
    }

}
