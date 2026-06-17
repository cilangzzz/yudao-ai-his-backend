package cn.iocoder.yudao.module.his.dal.dataobject.assess;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 入院评估 DO
 *
 * @author yudao
 */
@TableName("his_admission_assess")
@KeySequence("his_admission_assess_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisAdmissionAssessDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 住院ID
     */
    private Long admissionId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 评估类型
     *
     * 1-入院评估 2-跌倒评估 3-压疮评估 4-疼痛评估 5-营养评估
     * @see cn.iocoder.yudao.module.his.enums.AssessTypeEnum
     */
    private Integer assessType;

    /**
     * 评估得分
     */
    private Integer assessScore;

    /**
     * 评估结果
     */
    private String assessResult;

    /**
     * 风险等级
     *
     * 1-低 2-中 3-高
     * @see cn.iocoder.yudao.module.his.enums.RiskLevelEnum
     */
    private Integer riskLevel;

    /**
     * 评估内容(JSON)
     */
    private String assessContent;

    /**
     * 评估时间
     */
    private LocalDateTime assessTime;

    /**
     * 评估护士ID
     */
    private Long assessNurse;

    /**
     * 评估护士姓名
     */
    private String assessNurseName;

    /**
     * 备注
     */
    private String remark;
}