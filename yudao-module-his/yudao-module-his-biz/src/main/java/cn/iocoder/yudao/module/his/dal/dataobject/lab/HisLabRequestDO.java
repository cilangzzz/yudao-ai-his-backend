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
 * 对应FHIR资源: ServiceRequest, LaboratoryRequest
 * 用于门诊/住院检验申请管理，支持血液、尿液、生化等检验类型
 *
 * @author yudao
 */
@TableName("his_lab_request")
@KeySequence("his_lab_request_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisLabRequestDO extends TenantBaseDO {

    /**
     * 申请ID
     */
    @TableId
    private Long id;

    /**
     * 申请单号
     */
    private String requestNo;

    /**
     * 申请来源/就诊类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ExamEncounterTypeEnum}
     * 1-门诊 2-住院 3-急诊
     */
    private Integer sourceType;

    /**
     * 关联ID/就诊ID
     *
     * 门诊来源时为挂号ID(op_register.id)
     * 住院来源时为住院ID(his_admission.id)
     */
    private Long sourceId;

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
     *
     * 1-男 2-女
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
     * 患者床号(住院)
     */
    private String bedNo;

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
     * 申请时间
     */
    private LocalDateTime requestTime;

    /**
     * 检验类别(血常规/尿常规/生化/免疫等)
     */
    private String labCategory;

    /**
     * 检验类型
     *
     * 1-常规检验 2-急诊检验 3-特检
     */
    private Integer labType;

    /**
     * 临床诊断
     */
    private String diagnosis;

    /**
     * 诊断编码(ICD-10)
     */
    private String diagnosisCode;

    /**
     * 临床症状
     */
    private String clinicalSymptom;

    /**
     * 检验目的
     */
    private String clinicalPurpose;

    /**
     * 紧急程度
     *
     * 0-普通 1-急诊 2-加急
     */
    private Integer urgency;

    /**
     * 是否急诊检验
     *
     * 0-否 1-是
     */
    private Integer isStat;

    // ========== 标本信息 ==========

    /**
     * 标本类型
     *
     * 1-血液 2-尿液 3-粪便 4-痰液 5-其他
     */
    private Integer specimenType;

    /**
     * 标本类型名称
     */
    private String sampleType;

    /**
     * 标本ID
     */
    private Long specimenId;

    /**
     * 标本条码
     */
    private String specimenBarcode;

    /**
     * 标本采集说明
     */
    private String specimenNote;

    /**
     * 标本采集时间
     */
    private LocalDateTime collectTime;

    /**
     * 标本采集人ID
     */
    private Long collectorId;

    /**
     * 标本采集人姓名
     */
    private String collectorName;

    /**
     * 标本状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.SpecimenStatusEnum}
     * 1-待采集 2-已采集 3-已运送 4-已接收 5-已拒收 6-检验中 7-已完成 8-已销毁
     */
    private Integer specimenStatus;

    /**
     * 标本接收时间
     */
    private LocalDateTime receiveTime;

    /**
     * 标本接收人ID
     */
    private Long receiverId;

    /**
     * 标本接收人姓名
     */
    private String receiverName;

    /**
     * 标本拒收原因
     */
    private String rejectReason;

    /**
     * 标本拒收时间
     */
    private LocalDateTime rejectTime;

    // ========== 执行信息 ==========

    /**
     * 执行科室ID
     */
    private Long executionDeptId;

    /**
     * 执行科室名称
     */
    private String executionDeptName;

    /**
     * 检验开始时间
     */
    private LocalDateTime startTime;

    /**
     * 检验结束时间
     */
    private LocalDateTime endTime;

    /**
     * 检验技师ID
     */
    private Long technicianId;

    /**
     * 检验技师姓名
     */
    private String technicianName;

    // ========== 报告信息 ==========

    /**
     * 报告ID
     */
    private Long reportId;

    /**
     * 报告编号
     */
    private String reportNo;

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
     * 报告状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.LabReportStatusEnum}
     * 0-草稿 1-初步报告 2-最终报告 3-已审核 4-已发布 5-已撤回
     */
    private Integer reportStatus;

    // ========== 申请状态 ==========

    /**
     * 申请状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.LabRequestStatusEnum}
     * 1-已申请 2-已采集 3-已接收 4-检验中 5-已完成 6-已报告 7-已审核 8-已取消 9-已退费
     */
    private Integer requestStatus;

    // ========== 危急值信息 ==========

    /**
     * 是否有危急值
     *
     * 0-无 1-有
     */
    private Integer criticalFlag;

    /**
     * 危急值内容
     */
    private String criticalValueContent;

    /**
     * 危急值报告时间
     */
    private LocalDateTime criticalReportTime;

    /**
     * 危急值确认人ID
     */
    private Long criticalConfirmUserId;

    /**
     * 危急值确认人姓名
     */
    private String criticalConfirmUserName;

    /**
     * 危急值确认时间
     */
    private LocalDateTime criticalConfirmTime;

    // ========== 费用信息 ==========

    /**
     * 检验项目数
     */
    private Integer itemCount;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 医保支付金额
     */
    private BigDecimal insuranceAmount;

    /**
     * 自付金额
     */
    private BigDecimal selfAmount;

    /**
     * 支付状态
     *
     * 0-未支付 1-已支付 2-已退费
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付方式
     *
     * 1-现金 2-微信 3-支付宝 4-医保
     */
    private Integer payType;

    // ========== 取消信息 ==========

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

    // ========== 其他信息 ==========

    /**
     * 备注
     */
    private String remark;

    /**
     * 临床病史
     */
    private String clinicalHistory;

    /**
     * 过敏史
     */
    private String allergyHistory;

    /**
     * 用药史
     */
    private String medicationHistory;

    /**
     * 是否空腹
     *
     * 0-否 1-是
     */
    private Integer fastingFlag;

    /**
     * 特殊说明
     */
    private String specialInstruction;

    // ========== 业务方法 ==========

    /**
     * 是否待采集
     */
    public boolean isPendingCollect() {
        return requestStatus != null && requestStatus == 1;
    }

    /**
     * 是否已采集
     */
    public boolean isCollected() {
        return requestStatus != null && requestStatus >= 2;
    }

    /**
     * 是否检验中
     */
    public boolean isInLab() {
        return requestStatus != null && requestStatus == 4;
    }

    /**
     * 是否已完成
     */
    public boolean isCompleted() {
        return requestStatus != null && requestStatus >= 5;
    }

    /**
     * 是否已发布
     */
    public boolean isPublished() {
        return requestStatus != null && requestStatus == 7;
    }

    /**
     * 是否已取消
     */
    public boolean isCancelled() {
        return requestStatus != null && requestStatus == 8;
    }

    /**
     * 是否有危急值
     */
    public boolean hasCriticalValue() {
        return criticalFlag != null && criticalFlag == 1;
    }

    /**
     * 是否紧急
     */
    public boolean isUrgent() {
        return urgency != null && urgency >= 1;
    }

    /**
     * 是否门诊来源
     */
    public boolean isOutpatientSource() {
        return sourceType != null && sourceType == 1;
    }

    /**
     * 是否住院来源
     */
    public boolean isInpatientSource() {
        return sourceType != null && sourceType == 2;
    }

}