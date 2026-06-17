package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 入库记录 DO
 *
 * 药品入库管理，支持采购入库、退货入库、调拨入库等类型
 */
@TableName("his_drug_inbound")
@KeySequence("his_drug_inbound_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugInboundDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 入库单号
     */
    private String inboundNo;

    /**
     * 入库类型:1-采购入库 2-退货入库 3-调拨入库
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InboundTypeEnum}
     */
    private Integer inboundType;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购单ID
     */
    private Long purchaseId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 入库时间
     */
    private LocalDateTime inboundTime;

    /**
     * 操作员
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 审核状态:1-待审核 2-已审核 3-已拒绝
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.AuditStatusEnum}
     */
    private Integer auditStatus;

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
     * 是否待审核
     */
    public boolean isPendingAudit() {
        return auditStatus != null && auditStatus == 1;
    }

    /**
     * 是否已审核
     */
    public boolean isAudited() {
        return auditStatus != null && auditStatus == 2;
    }

    /**
     * 是否已拒绝
     */
    public boolean isRejected() {
        return auditStatus != null && auditStatus == 3;
    }

    /**
     * 是否可以审核
     */
    public boolean canAudit() {
        return isPendingAudit();
    }

    /**
     * 是否采购入库
     */
    public boolean isPurchaseInbound() {
        return inboundType != null && inboundType == 1;
    }

    /**
     * 是否退货入库
     */
    public boolean isReturnInbound() {
        return inboundType != null && inboundType == 2;
    }

    /**
     * 是否调拨入库
     */
    public boolean isTransferInbound() {
        return inboundType != null && inboundType == 3;
    }

}