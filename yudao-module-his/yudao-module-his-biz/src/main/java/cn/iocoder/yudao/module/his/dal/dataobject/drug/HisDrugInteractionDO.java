package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 药物相互作用 DO
 *
 * 用于CDS合理用药校验，记录药物之间的相互作用信息
 */
@TableName("his_drug_interaction")
@KeySequence("his_drug_interaction_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugInteractionDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 药品A的ID
     */
    private Long drugIdA;

    /**
     * 药品A编码
     */
    private String drugCodeA;

    /**
     * 药品A名称
     */
    private String drugNameA;

    /**
     * 药品B的ID
     */
    private Long drugIdB;

    /**
     * 药品B编码
     */
    private String drugCodeB;

    /**
     * 药品B名称
     */
    private String drugNameB;

    /**
     * 相互作用类型
     */
    private String interactionType;

    /**
     * 严重程度: 轻度/中度/重度/禁忌
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InteractionSeverityEnum}
     */
    private String severity;

    /**
     * 作用机制
     */
    private String mechanism;

    /**
     * 相互作用描述
     */
    private String description;

    /**
     * 临床影响
     */
    private String clinicalEffect;

    /**
     * 处置建议
     */
    private String suggestion;

    /**
     * 参考文献
     */
    private String reference;

    /**
     * 状态: 0停用/1正常
     */
    private Integer status;

}