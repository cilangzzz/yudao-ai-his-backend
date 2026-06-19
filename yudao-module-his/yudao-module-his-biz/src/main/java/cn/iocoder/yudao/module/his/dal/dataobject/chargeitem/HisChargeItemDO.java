package cn.iocoder.yudao.module.his.dal.dataobject.chargeitem;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 收费项目字典 DO
 *
 * 医院收费项目字典，包括挂号费、诊查费、检查费、治疗费等
 *
 * @author yudao
 */
@TableName(value = "his_charge_item", autoResultMap = true)
@KeySequence("his_charge_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisChargeItemDO extends TenantBaseDO {

    /**
     * 项目ID
     */
    @TableId
    private Long id;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 项目类别
     *
     * 1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他
     */
    private Integer itemCategory;

    /**
     * 子类别
     */
    private String itemSubcategory;

    /**
     * 规格
     */
    private String spec;

    /**
     * 计价单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 医保编码
     */
    private String insuranceCode;

    /**
     * 医保定价
     */
    private BigDecimal insurancePrice;

    /**
     * 医保类别
     *
     * 1-甲类 2-乙类 3-丙类
     */
    private Integer insuranceCategory;

    /**
     * 执行科室
     */
    private Long executionDept;

    /**
     * 状态
     *
     * 1-在用 2-停用
     */
    private Integer status;

    /**
     * 生效日期
     */
    private LocalDate effectiveDate;

    /**
     * 失效日期
     */
    private LocalDate expireDate;

    /**
     * 备注
     */
    private String remark;

}