package cn.iocoder.yudao.module.his.dal.dataobject.drug;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 入库明细 DO
 *
 * 入库单的药品明细信息
 */
@TableName("his_drug_inbound_item")
@KeySequence("his_drug_inbound_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDrugInboundItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 入库ID
     */
    private Long inboundId;

    /**
     * 药品ID
     */
    private Long drugId;

    /**
     * 药品编码
     */
    private String drugCode;

    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 药品规格
     */
    private String drugSpec;

    /**
     * 批号
     */
    private String batchNo;

    /**
     * 生产日期
     */
    private LocalDate productionDate;

    /**
     * 有效期
     */
    private LocalDate expiryDate;

    /**
     * 入库数量
     */
    private BigDecimal quantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 货架位置
     */
    private String storageLocation;

    /**
     * 备注
     */
    private String remark;

}