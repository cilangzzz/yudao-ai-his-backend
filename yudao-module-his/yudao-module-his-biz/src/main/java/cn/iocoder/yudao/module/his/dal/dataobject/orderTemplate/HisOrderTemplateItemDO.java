package cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 医嘱模板明细表 DO
 *
 * 存储医嘱模板的具体医嘱项目明细
 */
@TableName("his_order_template_item")
@KeySequence("his_order_template_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisOrderTemplateItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 医嘱内容
     */
    private String orderContent;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 医嘱类型: 1-药品 2-检验 3-检查 4-护理 5-手术 6-饮食 7-其他
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderTypeEnum}
     */
    private Integer orderType;

    /**
     * 医嘱分类: 1-长期 2-临时
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderCategoryEnum}
     */
    private Integer orderCategory;

    /**
     * 剂量
     */
    private String dosage;

    /**
     * 剂量单位
     */
    private String dosageUnit;

    /**
     * 频次编码
     */
    private String frequencyCode;

    /**
     * 频次名称
     */
    private String frequencyName;

    /**
     * 途径编码
     */
    private String routeCode;

    /**
     * 途径名称
     */
    private String routeName;

    /**
     * 疗程(天)
     */
    private Integer duration;

    /**
     * 单价
     */
    private java.math.BigDecimal price;

    /**
     * 金额
     */
    private java.math.BigDecimal amount;

    /**
     * 紧急标志: 0-常规 1-紧急
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderUrgencyEnum}
     */
    private Integer urgency;

    /**
     * 皮试标志: 0-不需要 1-需要
     */
    private Integer skinTestFlag;

    /**
     * 自备标志: 0-否 1-是
     */
    private Integer selfProvideFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序号
     */
    private Integer sort;

    // ==================== 业务方法 ====================

    /**
     * 是否为长期医嘱
     */
    public boolean isLongTermOrder() {
        return orderCategory != null && orderCategory == 1;
    }

    /**
     * 是否为临时医嘱
     */
    public boolean isTemporaryOrder() {
        return orderCategory != null && orderCategory == 2;
    }

    /**
     * 是否为紧急医嘱
     */
    public boolean isUrgent() {
        return urgency != null && urgency == 1;
    }

    /**
     * 是否需要皮试
     */
    public boolean needSkinTest() {
        return skinTestFlag != null && skinTestFlag == 1;
    }

    /**
     * 是否自备药
     */
    public boolean isSelfProvide() {
        return selfProvideFlag != null && selfProvideFlag == 1;
    }

    /**
     * 是否为药品医嘱
     */
    public boolean isDrugOrder() {
        return orderType != null && orderType == 1;
    }

    /**
     * 是否为检验医嘱
     */
    public boolean isLabOrder() {
        return orderType != null && orderType == 2;
    }

    /**
     * 是否为检查医嘱
     */
    public boolean isExamOrder() {
        return orderType != null && orderType == 3;
    }

}
