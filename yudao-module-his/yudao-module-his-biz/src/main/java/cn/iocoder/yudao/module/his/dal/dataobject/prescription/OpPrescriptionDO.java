package cn.iocoder.yudao.module.his.dal.dataobject.prescription;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 处方主表 DO
 *
 * 门诊处方信息，包含处方开立、审核、调配、发药等全流程状态
 */
@TableName("op_prescription")
@KeySequence("op_prescription_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpPrescriptionDO extends TenantBaseDO {

    /**
     * 处方ID
     */
    @TableId
    private Long id;

    /**
     * 处方编号
     */
    private String prescriptionNo;

    /**
     * 挂号ID
     */
    private Long registerId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 处方类型：1-普通 2-急诊 3-儿科 4-麻醉 5-精神 6-中药
     */
    private Integer prescriptionType;

    /**
     * 开方科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 开方医生ID
     */
    private Long doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 诊断编码（ICD-10）
     */
    private String diagnoseCode;

    /**
     * 诊断名称
     */
    private String diagnoseName;

    /**
     * 处方总金额
     */
    private BigDecimal totalAmount;

    /**
     * 审方药师ID
     */
    private Long pharmacistId;

    /**
     * 审方药师姓名
     */
    private String pharmacistName;

    /**
     * 审方时间
     */
    private LocalDateTime auditTime;

    /**
     * 审方结果：1-通过 2-退回
     */
    private Integer auditResult;

    /**
     * 审核意见
     */
    private String auditRemark;

    /**
     * 调配药师ID
     */
    private Long dispensePharmacist;

    /**
     * 调配药师姓名
     */
    private String dispensePharmacistName;

    /**
     * 调配时间
     */
    private LocalDateTime dispenseTime;

    /**
     * 发药药师ID
     */
    private Long sendPharmacist;

    /**
     * 发药药师姓名
     */
    private String sendPharmacistName;

    /**
     * 发药时间
     */
    private LocalDateTime sendTime;

    /**
     * 处方状态：1-开立 2-审核通过 3-审核退回 4-已调配 5-已发药 6-已退药
     */
    private Integer prescriptionStatus;

    /**
     * 有效天数
     */
    private Integer validDays;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否已审核通过
     */
    public boolean isAuditPassed() {
        return prescriptionStatus != null && prescriptionStatus >= 2;
    }

    /**
     * 是否已发药
     */
    public boolean isDispensed() {
        return prescriptionStatus != null && prescriptionStatus >= 5;
    }

    /**
     * 是否已退药
     */
    public boolean isReturned() {
        return prescriptionStatus != null && prescriptionStatus == 6;
    }

    /**
     * 是否可以审核
     */
    public boolean canAudit() {
        // 开立状态才能审核
        return prescriptionStatus != null && prescriptionStatus == 1;
    }

    /**
     * 是否可以调配
     */
    public boolean canDispense() {
        // 审核通过才能调配
        return prescriptionStatus != null && prescriptionStatus == 2;
    }

    /**
     * 是否可以发药
     */
    public boolean canSend() {
        // 已调配才能发药
        return prescriptionStatus != null && prescriptionStatus == 4;
    }

    /**
     * 是否可以退药
     */
    public boolean canReturn() {
        // 已发药才能退药
        return prescriptionStatus != null && prescriptionStatus == 5;
    }

    /**
     * 是否在有效期内
     */
    public boolean isValid() {
        if (validDays == null || validDays <= 0) {
            return true;
        }
        return createTime != null &&
               LocalDateTime.now().isBefore(createTime.plusDays(validDays));
    }

    /**
     * 是否为特殊处方（麻醉/精神）
     */
    public boolean isSpecialPrescription() {
        return prescriptionType != null && (prescriptionType == 4 || prescriptionType == 5);
    }

}
