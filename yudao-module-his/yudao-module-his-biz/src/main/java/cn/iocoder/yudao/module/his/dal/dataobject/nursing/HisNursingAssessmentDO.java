package cn.iocoder.yudao.module.his.dal.dataobject.nursing;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 护理评估 DO
 *
 * 记录跌倒评估、压疮评估、疼痛评估、营养评估等
 */
@TableName("his_nursing_assessment")
@KeySequence("his_nursing_assessment_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisNursingAssessmentDO extends TenantBaseDO {

    /**
     * 评估ID
     */
    @TableId
    private Long id;

    /**
     * 评估编号
     */
    private String assessmentNo;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 住院ID
     */
    private Long admissionId;

    /**
     * 评估类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.AssessTypeEnum}
     * 1-跌倒评估 2-压疮评估 3-疼痛评估 4-自理能力 5-营养评估
     */
    private Integer assessmentType;

    /**
     * 评估名称
     */
    private String assessmentName;

    /**
     * 总分
     */
    private Integer totalScore;

    /**
     * 风险等级
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.RiskLevelEnum}
     * 无风险/低风险/中风险/高风险
     */
    private String riskLevel;

    /**
     * 评估详情(JSON格式)
     */
    private String assessmentDetail;

    /**
     * 评估项目明细(JSON格式)
     */
    private String items;

    /**
     * 评估护士ID
     */
    private Long nurseId;

    /**
     * 评估护士姓名
     */
    private String nurseName;

    /**
     * 评估时间
     */
    private LocalDateTime assessmentTime;

    /**
     * 下次评估时间
     */
    private LocalDateTime nextAssessmentTime;

    /**
     * 护理措施建议
     */
    private String measureSuggestion;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否高风险
     */
    public boolean isHighRisk() {
        return "高风险".equals(riskLevel);
    }

    /**
     * 是否中风险
     */
    public boolean isMediumRisk() {
        return "中风险".equals(riskLevel);
    }

    /**
     * 是否需要干预
     */
    public boolean needsIntervention() {
        return isHighRisk() || isMediumRisk();
    }

    /**
     * 是否需要再次评估
     */
    public boolean needsReassessment() {
        if (nextAssessmentTime == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(nextAssessmentTime);
    }

    /**
     * 获取评估类型名称
     */
    public String getAssessmentTypeName() {
        if (assessmentType == null) {
            return "未知";
        }
        switch (assessmentType) {
            case 1:
                return "跌倒评估";
            case 2:
                return "压疮评估";
            case 3:
                return "疼痛评估";
            case 4:
                return "自理能力评估";
            case 5:
                return "营养评估";
            default:
                return "未知";
        }
    }
}