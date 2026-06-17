package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 出库记录 DO
 *
 * 药品出库管理，支持药房领用、报损出库、调拨出库等类型
 */
@TableName("his_drug_outbound")
@KeySequence("his_drug_outbound_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugOutboundDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 出库单号
     */
    private String outboundNo;

    /**
     * 出库类型:1-药房领用 2-报损出库 3-调拨出库
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OutboundTypeEnum}
     */
    private Integer outboundType;

    /**
     * 出库目标ID
     */
    private Long targetId;

    /**
     * 出库目标名称
     */
    private String targetName;

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
     * 出库时间
     */
    private LocalDateTime outboundTime;

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
     * 是否药房领用
     */
    public boolean isPharmacyOutbound() {
        return outboundType != null && outboundType == 1;
    }

    /**
     * 是否报损出库
     */
    public boolean isLossOutbound() {
        return outboundType != null && outboundType == 2;
    }

    /**
     * 是否调拨出库
     */
    public boolean isTransferOutbound() {
        return outboundType != null && outboundType == 3;
    }

}