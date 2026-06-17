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
 * 对应FHIR资源: ServiceRequest, ImagingRequest, ProcedureRequest
 * 用于门诊/住院检查申请管理，支持影像检查、检验检查、病理检查、功能检查
 *
 * @author yudao
 */
@TableName(value = "his_exam_request", autoResultMap = true)
@KeySequence("his_exam_request_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisExamRequestDO extends TenantBaseDO {

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
     * 就诊类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ExamEncounterTypeEnum}
     * 1-门诊 2-住院 3-急诊
     */
    private Integer encounterType;

    /**
     * 就诊ID(挂号ID/住院ID)
     */
    private Long encounterId;

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
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ExamTypeEnum}
     * 1-影像检查 2-检验检查 3-病理检查 4-功能检查
     */
    private Integer examType;

    /**
     * 检查类别(CT/MRI/X光/B超/血常规等)
     */
    private String examCategory;

    /**
     * 临床诊断
     */
    private String clinicalDiagnosis;

    /**
     * 临床症状
     */
    private String clinicalSymptom;

    /**
     * 检查目的
     */
    private String examPurpose;

    /**
     * 检查部位
     */
    private String examPart;

    /**
     * 紧急程度
     *
     * 0-普通 1-急诊 2-加急
     */
    private Integer urgency;

    /**
     * 是否急诊检查
     *
     * 0-否 1-是
     */
    private Integer isStat;

    /**
     * 标本类型(检验检查用)
     */
    private String sampleType;

    /**
     * 标本采集时间
     */
    private LocalDateTime sampleCollectTime;

    /**
     * 标本采集护士
     */
    private Long sampleCollectNurse;

    /**
     * 标本采集护士姓名
     */
    private String sampleCollectNurseName;

    /**
     * 执行科室ID
     */
    private Long executionDeptId;

    /**
     * 执行科室名称
     */
    private String executionDeptName;

    /**
     * 检查室/机房
     */
    private String executionRoom;

    /**
     * 预约检查时间
     */
    private LocalDateTime appointmentTime;

    /**
     * 安排检查时间
     */
    private LocalDateTime scheduledTime;

    /**
     * 签到时间
     */
    private LocalDateTime checkInTime;

    /**
     * 检查开始时间
     */
    private LocalDateTime startTime;

    /**
     * 检查结束时间
     */
    private LocalDateTime endTime;

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
     * 审核医生ID
     */
    private Long auditDoctorId;

    /**
     * 审核医生姓名
     */
    private String auditDoctorName;

    /**
     * 申请状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ExamRequestStatusEnum}
     * 1-已申请 2-已预约 3-已签到 4-检查中 5-已完成 6-已报告 7-已审核 8-已取消 9-已退费
     */
    private Integer requestStatus;

    /**
     * 报告状态
     *
     * 0-未报告 1-初步报告 2-最终报告 3-已审核
     */
    private Integer reportStatus;

    /**
     * 报告编号
     */
    private String reportNo;

    /**
     * 报告时间
     */
    private LocalDateTime reportTime;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

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

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 取消人
     */
    private Long cancelBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 相关临床病史
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
     * 妊娠标志
     *
     * 0-未妊娠 1-已妊娠(女性患者必填)
     */
    private Integer pregnancyFlag;

    /**
     * 是否需要造影
     *
     * 0-否 1-是
     */
    private Integer contrastFlag;

    /**
     * 造影剂类型
     */
    private String contrastType;

    /**
     * 造影剂用量
     */
    private BigDecimal contrastAmount;

    /**
     * 是否需要空腹
     *
     * 0-否 1-是
     */
    private Integer fastingFlag;

    /**
     * 检查准备说明
     */
    private String preparationInstruction;

    /**
     * 特殊说明
     */
    private String specialInstruction;

}
