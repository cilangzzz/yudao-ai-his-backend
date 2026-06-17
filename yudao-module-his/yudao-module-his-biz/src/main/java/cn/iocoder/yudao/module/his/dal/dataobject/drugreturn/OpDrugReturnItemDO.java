package cn.iocoder.yudao.module.his.dal.dataobject.drugreturn;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 退药明细表 DO
 *
 * 退药申请的明细记录，记录每个退药药品的详细信息
 */
@TableName("op_drug_return_item")
@KeySequence("op_drug_return_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpDrugReturnItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 退药ID
     */
    private Long returnId;

    /**
     * 退药单号
     */
    private String returnNo;

    /**
     * 原处方明细ID
     */
    private Long prescriptionItemId;

    /**
     * 原发药明细ID
     */
    private Long dispenseItemId;

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
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 批号
     */
    private String batchNo;

    /**
     * 有效期
     */
    private LocalDate expiryDate;

    /**
     * 原发药数量
     */
    private BigDecimal originalQuantity;

    /**
     * 退药数量
     */
    private BigDecimal returnQuantity;

    /**
     * 已退数量
     */
    private BigDecimal returnedQuantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 退药金额
     */
    private BigDecimal amount;

    /**
     * 药品状态：1-完好 2-包装破损 3-过期 4-其他
     */
    private Integer drugCondition;

    /**
     * 药品状态说明
     */
    private String drugConditionRemark;

    /**
     * 是否可再利用：0-否 1-是
     */
    private Integer canReuse;

    /**
     * 存放位置
     */
    private String storageLocation;

    /**
     * 明细状态：1-待审核 2-审核通过 3-审核拒绝 4-已入库
     */
    private Integer itemStatus;

    /**
     * 退药原因
     */
    private String returnReason;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否完好
     */
    public boolean isGoodCondition() {
        return drugCondition != null && drugCondition == 1;
    }

    /**
     * 是否可再利用
     */
    public boolean canReuse() {
        return canReuse != null && canReuse == 1;
    }

    /**
     * 是否还有剩余可退数量
     */
    public boolean hasRemainingToReturn() {
        if (returnQuantity == null || returnedQuantity == null) {
            return true;
        }
        return returnQuantity.compareTo(returnedQuantity) > 0;
    }

    /**
     * 计算剩余可退数量
     */
    public BigDecimal getRemainingQuantity() {
        if (returnQuantity == null) {
            return BigDecimal.ZERO;
        }
        if (returnedQuantity == null) {
            return returnQuantity;
        }
        return returnQuantity.subtract(returnedQuantity);
    }

    /**
     * 是否待审核
     */
    public boolean isPending() {
        return itemStatus != null && itemStatus == 1;
    }

    /**
     * 是否审核通过
     */
    public boolean isApproved() {
        return itemStatus != null && itemStatus == 2;
    }

    /**
     * 是否已入库
     */
    public boolean isInbounded() {
        return itemStatus != null && itemStatus == 4;
    }

    /**
     * 是否过期
     */
    public boolean isExpired() {
        if (expiryDate == null) {
            return false;
        }
        return expiryDate.isBefore(LocalDate.now());
    }

    /**
     * 是否近效期（30天内）
     */
    public boolean isNearExpiry() {
        if (expiryDate == null) {
            return false;
        }
        return expiryDate.isBefore(LocalDate.now().plusDays(30));
    }

}