package cn.iocoder.yudao.module.his.dal.dataobject.exam;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检查申请 DO
 *
 * 检查申请单，用于门诊/住院医生开立影像、超声、内镜、心电等检查申请
 * 对应医嘱类型: his_order.order_type = 3 (检查医嘱)
 *
 * @author yudao
 */
@TableName("op_exam_request")
@KeySequence("op_exam_request_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpExamRequestDO extends TenantBaseDO {

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
     * 1-门诊检查 2-住院检查
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
     * 检查类型
     *
     * 1-CT 2-MRI 3-X光/DR 4-超声 5-内镜 6-心电 7-核医学 8-其他
     */
    private Integer examType;

    /**
     * 检查类型名称
     */
    private String examTypeName;

    /**
     * 检查项目ID
     */
    private Long examItemId;

    /**
     * 检查项目编码
     */
    private String examItemCode;

    /**
     * 检查项目名称
     */
    private String examItemName;

    /**
     * 检查部位
     */
    private String examSite;

    /**
     * 临床诊断
     */
    private String diagnosis;

    /**
     * ICD-10诊断编码
     */
    private String diagnosisCode;

    /**
     * 检查目的/适应症
     */
    private String examPurpose;

    /**
     * 临床症状描述
     */
    private String clinicalSymptoms;

    /**
     * 病史摘要
     */
    private String medicalHistory;

    /**
     * 检查要求/备注
     */
    private String examRequirement;

    /**
     * 紧急标志
     *
     * 0-普通 1-急 2-特急
     */
    private Integer urgency;

    /**
     * 是否需要预约
     *
     * 0-否 1-是
     */
    private Integer needAppointment;

    /**
     * 预约时间
     */
    private LocalDateTime appointmentTime;

    /**
     * 执行科室ID
     */
    private Long execDeptId;

    /**
     * 执行科室名称
     */
    private String execDeptName;

    /**
     * 检查费用
     */
    private BigDecimal examFee;

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
     * 1-已开立 2-已收费 3-已预约 4-已登记 5-检查中 6-已完成 7-已报告 8-已取消
     */
    private Integer requestStatus;

    /**
     * 登记时间
     */
    private LocalDateTime registerTime;

    /**
     * 登记人ID
     */
    private Long registerBy;

    /**
     * 登记人姓名
     */
    private String registerByName;

    /**
     * 检查开始时间
     */
    private LocalDateTime examStartTime;

    /**
     * 检查结束时间
     */
    private LocalDateTime examEndTime;

    /**
     * 检查技师ID
     */
    private Long technicianId;

    /**
     * 检查技师姓名
     */
    private String technicianName;

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
     * 检查所见
     */
    private String examFinding;

    /**
     * 诊断结论
     */
    private String examConclusion;

    /**
     * PACS影像URL
     */
    private String pacsUrl;

    /**
     * 影像数量
     */
    private Integer imageCount;

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
     * 是否为门诊检查
     */
    public boolean isOutpatient() {
        return requestType != null && requestType == 1;
    }

    /**
     * 是否为住院检查
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
     * 是否已预约
     */
    public boolean isAppointed() {
        return requestStatus != null && requestStatus >= 3;
    }

    /**
     * 是否已完成检查
     */
    public boolean isExamCompleted() {
        return requestStatus != null && requestStatus >= 6;
    }

    /**
     * 是否已出报告
     */
    public boolean isReported() {
        return requestStatus != null && requestStatus >= 7;
    }

    /**
     * 是否已取消
     */
    public boolean isCancelled() {
        return requestStatus != null && requestStatus == 8;
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

}
