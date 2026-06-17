package cn.iocoder.yudao.module.his.dal.dataobject.nursing;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 护理措施 DO
 *
 * 记录针对评估结果采取的护理措施
 * 与护理评估关联，实现评估-干预闭环管理
 */
@TableName("his_nursing_measure")
@KeySequence("his_nursing_measure_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisNursingMeasureDO extends TenantBaseDO {

    /**
     * 措施ID
     */
    @TableId
    private Long id;

    /**
     * 措施编号
     */
    private String measureNo;

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
     * 关联的评估ID
     */
    private Long assessmentId;

    /**
     * 措施类型
     *
     * 1-跌倒预防 2-压疮预防 3-疼痛管理 4-营养支持 5-安全护理 6-其他
     */
    private Integer measureType;

    /**
     * 措施名称
     */
    private String measureName;

    /**
     * 措施内容
     */
    private String measureContent;

    /**
     * 执行频次
     *
     * 如: 每日2次、每4小时1次等
     */
    private String frequency;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 执行状态
     *
     * 0-未开始 1-执行中 2-已完成 3-已停止
     */
    private Integer status;

    /**
     * 执行护士ID
     */
    private Long nurseId;

    /**
     * 执行护士姓名
     */
    private String nurseName;

    /**
     * 效果评价
     *
     * 1-有效 2-部分有效 3-无效
     */
    private Integer effectEvaluation;

    /**
     * 效果评价说明
     */
    private String effectDescription;

    /**
     * 评价时间
     */
    private LocalDateTime evaluationTime;

    /**
     * 评价护士ID
     */
    private Long evaluationNurseId;

    /**
     * 评价护士姓名
     */
    private String evaluationNurseName;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否可以执行
     */
    public boolean canExecute() {
        return status != null && status == 0;
    }

    /**
     * 是否执行中
     */
    public boolean isExecuting() {
        return status != null && status == 1;
    }

    /**
     * 是否已完成
     */
    public boolean isCompleted() {
        return status != null && status == 2;
    }

    /**
     * 是否已停止
     */
    public boolean isStopped() {
        return status != null && status == 3;
    }

    /**
     * 是否需要评价
     */
    public boolean needsEvaluation() {
        return isCompleted() && effectEvaluation == null;
    }

    /**
     * 是否有效
     */
    public boolean isEffective() {
        return effectEvaluation != null && effectEvaluation == 1;
    }

    /**
     * 获取措施类型名称
     */
    public String getMeasureTypeName() {
        if (measureType == null) {
            return "未知";
        }
        return switch (measureType) {
            case 1 -> "跌倒预防";
            case 2 -> "压疮预防";
            case 3 -> "疼痛管理";
            case 4 -> "营养支持";
            case 5 -> "安全护理";
            case 6 -> "其他";
            default -> "未知";
        };
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) {
            return "未知";
        }
        return switch (status) {
            case 0 -> "未开始";
            case 1 -> "执行中";
            case 2 -> "已完成";
            case 3 -> "已停止";
            default -> "未知";
        };
    }

    /**
     * 获取效果评价名称
     */
    public String getEffectEvaluationName() {
        if (effectEvaluation == null) {
            return null;
        }
        return switch (effectEvaluation) {
            case 1 -> "有效";
            case 2 -> "部分有效";
            case 3 -> "无效";
            default -> null;
        };
    }
}
