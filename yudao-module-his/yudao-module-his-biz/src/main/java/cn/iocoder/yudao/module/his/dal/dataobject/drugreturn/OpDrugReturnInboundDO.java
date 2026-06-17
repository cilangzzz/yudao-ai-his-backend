package cn.iocoder.yudao.module.his.dal.dataobject.drugreturn;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退药入库记录表 DO
 *
 * 记录退药入库的详细信息，与药品库存表关联
 */
@TableName("op_drug_return_inbound")
@KeySequence("op_drug_return_inbound_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpDrugReturnInboundDO extends TenantBaseDO {

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
     * 退药ID
     */
    private Long returnId;

    /**
     * 退药单号
     */
    private String returnNo;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 入库总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 入库总金额
     */
    private BigDecimal totalAmount;

    /**
     * 入库时间
     */
    private LocalDateTime inboundTime;

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 审核状态：1-待审核 2-已审核 3-已拒绝
     */
    private Integer auditStatus;

    /**
     * 审核人ID
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
    public boolean isPending() {
        return auditStatus != null && auditStatus == 1;
    }

    /**
     * 是否已审核
     */
    public boolean isApproved() {
        return auditStatus != null && auditStatus == 2;
    }

    /**
     * 是否已拒绝
     */
    public boolean isRejected() {
        return auditStatus != null && auditStatus == 3;
    }

}