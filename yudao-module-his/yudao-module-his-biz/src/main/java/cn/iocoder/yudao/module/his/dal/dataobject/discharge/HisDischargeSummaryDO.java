package cn.iocoder.yudao.module.his.dal.dataobject.discharge;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 出院小结 DO
 *
 * 出院小结是住院病历的重要组成部分，记录患者住院期间的诊疗过程和出院指导
 *
 * @author yudao
 */
@TableName("his_discharge_summary")
@KeySequence("his_discharge_summary_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDischargeSummaryDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 出院ID
     */
    private Long dischargeId;

    /**
     * 住院ID
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
     * 入院诊断
     */
    private String admissionDiagnosis;

    /**
     * 出院诊断
     */
    private String dischargeDiagnosis;

    /**
     * 入院情况
     */
    private String admissionCondition;

    /**
     * 诊疗经过
     */
    private String treatmentProcess;

    /**
     * 出院情况
     */
    private String dischargeCondition;

    /**
     * 出院医嘱
     */
    private String dischargeAdvice;

    /**
     * 随访建议
     */
    private String followUp;

    /**
     * 用药指导
     */
    private String medicationAdvice;

    /**
     * 书写医生ID
     */
    private Long authorDoctor;

    /**
     * 书写医生姓名
     */
    private String authorDoctorName;

    /**
     * 书写时间
     */
    private LocalDateTime authorTime;

    /**
     * 审核医生ID
     */
    private Long reviewDoctor;

    /**
     * 审核医生姓名
     */
    private String reviewDoctorName;

    /**
     * 审核时间
     */
    private LocalDateTime reviewTime;

    /**
     * 状态：1-待审核 2-已审核
     */
    private Integer summaryStatus;

    /**
     * 是否待审核
     */
    public boolean isPendingReview() {
        return summaryStatus != null && summaryStatus == 1;
    }

    /**
     * 是否已审核
     */
    public boolean isReviewed() {
        return summaryStatus != null && summaryStatus == 2;
    }

    /**
     * 是否可以修改
     */
    public boolean canModify() {
        // 待审核状态可以修改
        return isPendingReview();
    }

    /**
     * 是否可以审核
     */
    public boolean canReview() {
        // 待审核状态可以审核
        return isPendingReview();
    }
}