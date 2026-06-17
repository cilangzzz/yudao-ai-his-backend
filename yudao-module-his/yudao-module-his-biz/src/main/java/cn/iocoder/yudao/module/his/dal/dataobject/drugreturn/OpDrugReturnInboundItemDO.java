package cn.iocoder.yudao.module.his.dal.dataobject.drugreturn;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 退药入库明细表 DO
 *
 * 记录退药入库的明细信息
 */
@TableName("op_drug_return_inbound_item")
@KeySequence("op_drug_return_inbound_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpDrugReturnInboundItemDO extends TenantBaseDO {

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
     * 入库单号
     */
    private String inboundNo;

    /**
     * 退药明细ID
     */
    private Long returnItemId;

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
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 货架位置
     */
    private String storageLocation;

    /**
     * 入库后库存ID
     */
    private Long stockId;

}
