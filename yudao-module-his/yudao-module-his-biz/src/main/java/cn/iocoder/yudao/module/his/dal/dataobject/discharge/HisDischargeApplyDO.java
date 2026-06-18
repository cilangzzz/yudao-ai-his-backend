package cn.iocoder.yudao.module.his.dal.dataobject.discharge;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 出院申请 DO
 *
 * 对应FHIR资源: Encounter (出院场景)
 * 出院申请信息，包含申请、审批、出院等流程
 *
 * @author yudao
 */
@TableName("his_discharge_apply")
@KeySequence("his_discharge_apply_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDischargeApplyDO extends TenantBaseDO {

    /**
     * 出院申请ID
     */
    @TableId
    private Long id;

    /**
     * 申请单号
     */
    private String applyNo;

    /**
     * 入院记录ID
     */
    private Long admissionId;

    /**
     * 住院号
     */
    private String admissionNo;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 病区ID
     */
    private Long wardId;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 床位ID
     */
    private Long bedId;

    /**
     * 床位号
     */
    private String bedNo;

    /**
     * 主治医生ID
     */
    private Long attendingDoctorId;

    /**
     * 主治医生姓名
     */
    private String attendingDoctorName;

    /**
     * 入院日期
     */
    private LocalDateTime admissionDate;

    /**
     * 入院诊断
     */
    private String admissionDiagnosis;

    /**
     * 入院诊断编码(ICD-10)
     */
    private String admissionDiagnosisCode;

    /**
     * 拟定出院日期
     */
    private LocalDateTime dischargeDate;

    /**
     * 出院方式: 1医嘱出院/2自动出院/3转院/4死亡
     */
    private Integer dischargeWay;

    /**
     * 出院诊断
     */
    private String dischargeDiagnosis;

    /**
     * 出院诊断编码(ICD-10)
     */
    private String dischargeDiagnosisCode;

    /**
     * 出院诊断备注
     */
    private String dischargeDiagnosisRemark;

    /**
     * 治疗结果: 1治愈/2好转/3未愈/4死亡/5其他
     */
    private Integer treatmentResult;

    /**
     * 转院医院(转院时填写)
     */
    private String transferHospital;

    /**
     * 转院原因
     */
    private String transferReason;

    /**
     * 死亡原因(死亡时填写)
     */
    private String deathReason;

    /**
     * 死亡时间
     */
    private LocalDateTime deathTime;

    /**
     * 申请医生ID
     */
    private Long applyDoctorId;

    /**
     * 申请医生姓名
     */
    private String applyDoctorName;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 申请原因
     */
    private String applyReason;

    /**
     * 状态: 1待审批/2已审批/3已驳回/4已出院/5已取消
     */
    private Integer status;

    /**
     * 审批医生ID
     */
    private Long auditDoctorId;

    /**
     * 审批医生姓名
     */
    private String auditDoctorName;

    /**
     * 审批时间
     */
    private LocalDateTime auditTime;

    /**
     * 审批意见
     */
    private String auditRemark;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 是否停止医嘱: 0否/1是
     */
    private Integer orderStopped;

    /**
     * 医嘱停止时间
     */
    private LocalDateTime orderStopTime;

    /**
     * 是否费用结算: 0否/1是
     */
    private Integer feeSettled;

    /**
     * 费用结算时间
     */
    private LocalDateTime feeSettleTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否待审批
     */
    public boolean isPending() {
        return status != null && status == 1;
    }

    /**
     * 是否已审批
     */
    public boolean isApproved() {
        return status != null && status == 2;
    }

    /**
     * 是否已驳回
     */
    public boolean isRejected() {
        return status != null && status == 3;
    }

    /**
     * 是否已出院
     */
    public boolean isDischarged() {
        return status != null && status == 4;
    }

    /**
     * 是否已取消
     */
    public boolean isCancelled() {
        return status != null && status == 5;
    }

    /**
     * 是否可以审批
     */
    public boolean canApprove() {
        return isPending();
    }

    /**
     * 是否可以驳回
     */
    public boolean canReject() {
        return isPending();
    }

    /**
     * 是否可以取消
     */
    public boolean canCancel() {
        return isPending() || isRejected();
    }

    /**
     * 是否可以出院
     */
    public boolean canDischarge() {
        return isApproved() && orderStopped == 1;
    }

}