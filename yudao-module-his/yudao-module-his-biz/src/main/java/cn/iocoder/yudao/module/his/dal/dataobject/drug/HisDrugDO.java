package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 药品目录 DO
 *
 * 药品基础信息管理，包含药品编码、名称、规格、价格等基本信息
 */
@TableName("his_drug")
@KeySequence("his_drug_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 药品编码(唯一)
     */
    private String drugCode;

    /**
     * 药品通用名
     */
    private String drugName;

    /**
     * 药品商品名
     */
    private String drugTradeName;

    /**
     * 药品类型:1-西药 2-中成药 3-中草药 4-生物制品
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DrugTypeEnum}
     */
    private Integer drugType;

    /**
     * 药品类别
     */
    private String drugCategory;

    /**
     * 剂型(片剂、注射剂等)
     */
    private String dosageForm;

    /**
     * 规格
     */
    private String spec;

    /**
     * 基本单位
     */
    private String unit;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 批准文号
     */
    private String approvalNo;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 医保目录编码
     */
    private String insuranceCode;

    /**
     * 医保类别:1-甲类 2-乙类 3-丙类
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceCategoryEnum}
     */
    private Integer insuranceCategory;

    /**
     * OTC标志:0-处方药 1-OTC
     */
    private Integer otcFlag;

    /**
     * 麻醉药品标志:0-否 1-是
     */
    private Integer narcoticFlag;

    /**
     * 精神药品标志:0-否 1-是
     */
    private Integer psychotropicFlag;

    /**
     * 毒性药品标志:0-否 1-是
     */
    private Integer toxicFlag;

    /**
     * 抗菌药物标志:0-否 1-是
     */
    private Integer antibioticFlag;

    /**
     * 储存条件
     */
    private String storageCondition;

    /**
     * 有效期(月)
     */
    private Integer shelfLife;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 状态:1-在用 2-停用
     *
     * 枚举 {@link cn.iocoder.yudao.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否为特殊药品（麻醉/精神/毒性）
     */
    public boolean isSpecialDrug() {
        return (narcoticFlag != null && narcoticFlag == 1)
                || (psychotropicFlag != null && psychotropicFlag == 1)
                || (toxicFlag != null && toxicFlag == 1);
    }

    /**
     * 是否为处方药
     */
    public boolean isPrescriptionDrug() {
        return otcFlag == null || otcFlag == 0;
    }

    /**
     * 是否在用
     */
    public boolean isInUse() {
        return status != null && status == 1;
    }

    /**
     * 是否医保药品
     */
    public boolean isInsuranceDrug() {
        return insuranceCode != null && !insuranceCode.isEmpty();
    }

}
