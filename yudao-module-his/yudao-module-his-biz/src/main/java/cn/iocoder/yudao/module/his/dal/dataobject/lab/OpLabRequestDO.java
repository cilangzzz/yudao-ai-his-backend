package cn.iocoder.yudao.module.his.dal.dataobject.lab;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检验申请 DO
 *
 * 检验申请单，用于门诊/住院医生开立实验室检验申请（血常规、生化、免疫等）
 * 对应医嘱类型: his_order.order_type = 2 (检验医嘱)
 * FHIR映射: ServiceRequest Resource
 *
 * @author yudao
 */
@TableName("op_lab_request")
@KeySequence("op_lab_request_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpLabRequestDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 申请单号
     */
    private String requestNo;

    /**
     * 申请类型
     *
     * 1-门诊检验 2-住院检验
     */
    private Integer requestType;

    /**
     * 关联挂号ID（门诊）
     */
    private Long registerId;

    /**
     * 关联住院ID（住院）
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
     * 患者性别
     */
    private Integer patientGender;

    /**
     * 患者年龄
     */
    private Integer patientAge;

    /**
     * 患者电话
     */
    private String patientPhone;

    /**
     * 申请科室ID
     */
    private Long deptId;

    /**
     * 申请科室名称
     */
    private String deptName;

    /**
     * 申请医生ID
     */
    private Long doctorId;

    /**
     * 申请医生姓名
     */
    private String doctorName;

    /**
     * 检验类型
     *
     * 1-血常规 2-生化 3-免疫 4-微生物 5-尿液 6-粪便 7-凝血 8-血气 9-其他
     */
    private Integer labType;

    /**
     * 检验类型名称
     */
    private String labTypeName;

    /**
     * 临床诊断
     */
    private String diagnosis;

    /**
     * ICD-10诊断编码
     */
    private String diagnosisCode;

    /**
     * 检验目的
     */
    private String labPurpose;

    /**
     * 临床症状描述
     */
    private String clinicalSymptoms;

    /**
     * 病史摘要
     */
    private String medicalHistory;

    /**
     * 标本类型
     *
     * 1-全血 2-血清 3-血浆 4-尿液 5-粪便 6-脑脊液 7-痰液 8-胸腹水 9-拭子 10-其他
     */
    private Integer specimenType;

    /**
     * 标本类型名称
     */
    private String specimenTypeName;

    /**
     * 特殊要求
     */
    private String specialRequirement;

    /**
     * 紧急标志
     *
     * 0-普通 1-急 2-特急
     */
    private Integer urgency;

    /**
     * 检验费用
     */
    private BigDecimal labFee;

    /**
     * 收费状态
     *
     * 0-未收费 1-已收费 2-已退费
     */
    private Integer feeStatus;

    /**
     * 收费时间
     */
    private LocalDateTime feeTime;

    /**
     * 申请状态
     *
     * 1-已开立 2-已收费 3-待采集 4-已采集 5-已运送 6-已接收 7-检验中 8-已完成 9-已审核 10-已发布 11-已取消
     */
    private Integer requestStatus;

    /**
     * 采集护士ID
     */
    private Long collectNurseId;

    /**
     * 采集护士姓名
     */
    private String collectNurseName;

    /**
     * 采集时间
     */
    private LocalDateTime collectTime;

    /**
     * 标本编号
     */
    private String specimenNo;

    /**
     * 标本条码
     */
    private String specimenBarcode;

    /**
     * 运送人员ID
     */
    private Long transportPersonId;

    /**
     * 运送人员姓名
     */
    private String transportPersonName;

    /**
     * 运送时间
     */
    private LocalDateTime transportTime;

    /**
     * 接收人员ID
     */
    private Long receivePersonId;

    /**
     * 接收人员姓名
     */
    private String receivePersonName;

    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;

    /**
     * 检验技师ID
     */
    private Long technicianId;

    /**
     * 检验技师姓名
     */
    private String technicianName;

    /**
     * 检验开始时间
     */
    private LocalDateTime labStartTime;

    /**
     * 检验结束时间
     */
    private LocalDateTime labEndTime;

    /**
     * 报告医生ID
     */
    private Long reportDoctorId;

    /**
     * 报告医生姓名
     */
    private String reportDoctorName;

    /**
     * 报告时间
     */
    private LocalDateTime reportTime;

    /**
     * 审核医生ID
     */
    private Long auditDoctorId;

    /**
     * 审核医生姓名
     */
    private String auditDoctorName;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 是否有危急值
     *
     * 0-无 1-有
     */
    private Integer hasCriticalValue;

    /**
     * 危急值通知时间
     */
    private LocalDateTime criticalNotifyTime;

    /**
     * 危急值确认时间
     */
    private LocalDateTime criticalConfirmTime;

    /**
     * 危急值确认医生ID
     */
    private Long criticalConfirmDoctorId;

    /**
     * 危急值确认医生姓名
     */
    private String criticalConfirmDoctorName;

    /**
     * 检验结果概要
     */
    private String labResultSummary;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 取消人ID
     */
    private Long cancelBy;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否为门诊检验
     */
    public boolean isOutpatient() {
        return requestType != null && requestType == 1;
    }

    /**
     * 是否为住院检验
     */
    public boolean isInpatient() {
        return requestType != null && requestType == 2;
    }

    /**
     * 是否为急诊
     */
    public boolean isEmergency() {
        return urgency != null && urgency >= 1;
    }

    /**
     * 是否已收费
     */
    public boolean isPaid() {
        return feeStatus != null && feeStatus >= 1;
    }

    /**
     * 是否已采集标本
     */
    public boolean isCollected() {
        return requestStatus != null && requestStatus >= 4;
    }

    /**
     * 是否已完成检验
     */
    public boolean isLabCompleted() {
        return requestStatus != null && requestStatus >= 8;
    }

    /**
     * 是否已发布报告
     */
    public boolean isPublished() {
        return requestStatus != null && requestStatus >= 10;
    }

    /**
     * 是否已取消
     */
    public boolean isCancelled() {
        return requestStatus != null && requestStatus == 11;
    }

    /**
     * 是否可以取消
     */
    public boolean canCancel() {
        // 已开立或已收费状态才能取消
        return requestStatus != null && (requestStatus == 1 || requestStatus == 2);
    }

    /**
     * 是否可以修改
     */
    public boolean canModify() {
        // 已开立状态才能修改
        return requestStatus != null && requestStatus == 1;
    }

    /**
     * 是否需要收费
     */
    public boolean needPay() {
        return feeStatus != null && feeStatus == 0;
    }

    /**
     * 是否有危急值未确认
     */
    public boolean hasUnconfirmedCriticalValue() {
        return hasCriticalValue != null && hasCriticalValue == 1
               && criticalConfirmTime == null;
    }

    /**
     * 是否危急值已确认
     */
    public boolean criticalValueConfirmed() {
        return hasCriticalValue != null && hasCriticalValue == 1
               && criticalConfirmTime != null;
    }

}