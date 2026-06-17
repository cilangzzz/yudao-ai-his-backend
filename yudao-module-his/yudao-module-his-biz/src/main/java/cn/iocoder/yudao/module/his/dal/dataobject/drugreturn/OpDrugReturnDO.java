package cn.iocoder.yudao.module.his.dal.dataobject.drugreturn;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退药申请主表 DO
 *
 * 用于门诊/住院退药申请管理，包含申请、审核、入库、退款全流程
 */
@TableName("op_drug_return")
@KeySequence("op_drug_return_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpDrugReturnDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 退药单号
     */
    private String returnNo;

    /**
     * 退药类型：1-门诊退药 2-住院退药
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DrugReturnTypeEnum}
     */
    private Integer returnType;

    /**
     * 原处方ID
     */
    private Long prescriptionId;

    /**
     * 原处方编号
     */
    private String prescriptionNo;

    /**
     * 原发药ID
     */
    private Long dispenseId;

    /**
     * 原发药单号
     */
    private String dispenseNo;

    /**
     * 挂号ID（门诊）
     */
    private Long registerId;

    /**
     * 住院ID（住院）
     */
    private Long admissionId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者电话
     */
    private String patientPhone;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 开单医生ID
     */
    private Long doctorId;

    /**
     * 开单医生姓名
     */
    private String doctorName;

    /**
     * 药房ID
     */
    private Long pharmacyId;

    /**
     * 药房名称
     */
    private String pharmacyName;

    /**
     * 退药总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 退药总金额
     */
    private BigDecimal totalAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退药原因
     */
    private String returnReason;

    /**
     * 退药原因类型：1-药品不良反应 2-医嘱变更 3-患者拒服 4-药品质量问题 5-重复开药 6-其他
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DrugReturnReasonTypeEnum}
     */
    private Integer returnReasonType;

    /**
     * 退药状态：1-待审核 2-审核通过 3-审核拒绝 4-已入库 5-已退款 6-已取消
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DrugReturnStatusEnum}
     */
    private Integer returnStatus;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 申请人ID
     */
    private Long applyBy;

    /**
     * 申请人姓名
     */
    private String applyByName;

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
     * 审核意见
     */
    private String auditRemark;

    /**
     * 入库时间
     */
    private LocalDateTime inboundTime;

    /**
     * 入库人ID
     */
    private Long inboundBy;

    /**
     * 入库人姓名
     */
    private String inboundByName;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 退款人ID
     */
    private Long refundBy;

    /**
     * 退款人姓名
     */
    private String refundByName;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 取消人ID
     */
    private Long cancelBy;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 附件URL（药品照片等）
     */
    private String attachmentUrl;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否待审核
     */
    public boolean isPending() {
        return returnStatus != null && returnStatus == 1;
    }

    /**
     * 是否审核通过
     */
    public boolean isApproved() {
        return returnStatus != null && returnStatus == 2;
    }

    /**
     * 是否审核拒绝
     */
    public boolean isRejected() {
        return returnStatus != null && returnStatus == 3;
    }

    /**
     * 是否已入库
     */
    public boolean isInbounded() {
        return returnStatus != null && returnStatus == 4;
    }

    /**
     * 是否已退款
     */
    public boolean isRefunded() {
        return returnStatus != null && returnStatus == 5;
    }

    /**
     * 是否已取消
     */
    public boolean isCancelled() {
        return returnStatus != null && returnStatus == 6;
    }

    /**
     * 是否可以审核
     */
    public boolean canAudit() {
        return isPending();
    }

    /**
     * 是否可以取消
     */
    public boolean canCancel() {
        return isPending();
    }

    /**
     * 是否可以入库
     */
    public boolean canInbound() {
        return isApproved();
    }

    /**
     * 是否可以退款
     */
    public boolean canRefund() {
        return isInbounded();
    }

    /**
     * 是否门诊退药
     */
    public boolean isOutpatient() {
        return returnType != null && returnType == 1;
    }

    /**
     * 是否住院退药
     */
    public boolean isInpatient() {
        return returnType != null && returnType == 2;
    }

}
