package cn.iocoder.yudao.module.his.dal.dataobject.drugreturn;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 退药审核记录表 DO
 *
 * 记录退药审核的历史记录，支持多级审核
 */
@TableName("op_drug_return_audit")
@KeySequence("op_drug_return_audit_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpDrugReturnAuditDO extends TenantBaseDO {

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
     * 审核类型：1-药师审核 2-科室审核 3-财务审核
     */
    private Integer auditType;

    /**
     * 审核结果：1-通过 2-拒绝
     */
    private Integer auditResult;

    /**
     * 审核意见
     */
    private String auditRemark;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核人ID
     */
    private Long auditBy;

    /**
     * 审核人姓名
     */
    private String auditByName;

    /**
     * 附件URL
     */
    private String attachmentUrl;

    // ==================== 业务方法 ====================

    /**
     * 是否审核通过
     */
    public boolean isApproved() {
        return auditResult != null && auditResult == 1;
    }

    /**
     * 是否审核拒绝
     */
    public boolean isRejected() {
        return auditResult != null && auditResult == 2;
    }

    /**
     * 是否药师审核
     */
    public boolean isPharmacistAudit() {
        return auditType != null && auditType == 1;
    }

    /**
     * 是否科室审核
     */
    public boolean isDeptAudit() {
        return auditType != null && auditType == 2;
    }

    /**
     * 是否财务审核
     */
    public boolean isFinanceAudit() {
        return auditType != null && auditType == 3;
    }

}