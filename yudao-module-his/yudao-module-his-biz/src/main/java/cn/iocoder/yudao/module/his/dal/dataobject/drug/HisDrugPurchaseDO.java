package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购计划 DO
 *
 * 药品采购计划管理
 */
@TableName("his_drug_purchase")
@KeySequence("his_drug_purchase_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugPurchaseDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 采购单号
     */
    private String purchaseNo;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购类型:1-计划采购 2-紧急采购
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PurchaseTypeEnum}
     */
    private Integer purchaseType;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 状态:1-草稿 2-已提交 3-已审批 4-已采购 5-已完成
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PurchaseStatusEnum}
     */
    private Integer purchaseStatus;

    /**
     * 申请人
     */
    private Long applyBy;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 审核人
     */
    private Long auditBy;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否草稿
     */
    public boolean isDraft() {
        return purchaseStatus != null && purchaseStatus == 1;
    }

    /**
     * 是否已提交
     */
    public boolean isSubmitted() {
        return purchaseStatus != null && purchaseStatus >= 2;
    }

    /**
     * 是否已审批
     */
    public boolean isApproved() {
        return purchaseStatus != null && purchaseStatus >= 3;
    }

    /**
     * 是否可以修改
     */
    public boolean canUpdate() {
        return isDraft();
    }

    /**
     * 是否可以提交
     */
    public boolean canSubmit() {
        return isDraft();
    }

    /**
     * 是否可以审批
     */
    public boolean canApprove() {
        return purchaseStatus != null && purchaseStatus == 2;
    }

}