package cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 医嘱模板主表 DO
 *
 * 医嘱模板用于存储常用的医嘱组合，便于医生快速开立医嘱
 * 支持按科室、医生、疾病等维度分类管理
 */
@TableName("his_order_template")
@KeySequence("his_order_template_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisOrderTemplateDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板分类: 1-个人模板 2-科室模板 3-全院模板 4-疾病模板
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderTemplateCategoryEnum}
     */
    private Integer templateCategory;

    /**
     * 医嘱类型: 1-药品 2-检验 3-检查 4-护理 5-手术 6-饮食 7-其他
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderTypeEnum}
     */
    private Integer orderType;

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 创建医生ID
     */
    private Long doctorId;

    /**
     * 创建医生姓名
     */
    private String doctorName;

    /**
     * 适用科室ID（多个用逗号分隔）
     */
    private String applicableDeptIds;

    /**
     * 疾病编码（ICD-10）
     */
    private String diagnosisCode;

    /**
     * 疾病名称
     */
    private String diagnosisName;

    /**
     * 模板内容摘要
     */
    private String contentSummary;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 使用次数
     */
    private Integer useCount;

    /**
     * 状态: 0-禁用 1-启用
     *
     * 枚举 {@link cn.iocoder.yudao.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;

    // ==================== 业务方法 ====================

    /**
     * 是否为个人模板
     */
    public boolean isPersonalTemplate() {
        return templateCategory != null && templateCategory == 1;
    }

    /**
     * 是否为科室模板
     */
    public boolean isDeptTemplate() {
        return templateCategory != null && templateCategory == 2;
    }

    /**
     * 是否为全院模板
     */
    public boolean isHospitalTemplate() {
        return templateCategory != null && templateCategory == 3;
    }

    /**
     * 是否为疾病模板
     */
    public boolean isDiagnosisTemplate() {
        return templateCategory != null && templateCategory == 4;
    }

    /**
     * 是否启用
     */
    public boolean isEnabled() {
        return status != null && status == 1;
    }

}
