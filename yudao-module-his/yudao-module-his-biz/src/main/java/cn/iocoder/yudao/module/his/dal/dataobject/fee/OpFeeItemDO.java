package cn.iocoder.yudao.module.his.dal.dataobject.fee;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 门诊费用明细 DO
 *
 * 患者门诊费用明细记录，关联收费项目、处方、检查等
 *
 * @author yudao
 */
@TableName("op_fee_item")
@KeySequence("op_fee_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpFeeItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 费用ID（关联 op_fee）
     */
    private Long feeId;

    /**
     * 挂号ID
     */
    private Long registerId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 收费项目ID
     */
    private Long chargeItemId;

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
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ChargeItemCategoryEnum}
     * 1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他
     */
    private Integer itemCategory;

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
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 实收金额
     */
    private BigDecimal payAmount;

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
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceCategoryEnum}
     * 1-甲类 2-乙类 3-丙类
     */
    private Integer insuranceCategory;

    /**
     * 执行科室ID
     */
    private Long executionDeptId;

    /**
     * 执行科室名称
     */
    private String executionDeptName;

    /**
     * 开单医生ID
     */
    private Long doctorId;

    /**
     * 开单医生姓名
     */
    private String doctorName;

    /**
     * 来源类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.FeeSourceTypeEnum}
     * 1-挂号 2-处方 3-检查 4-检验 5-治疗 6-手术 7-其他
     */
    private Integer sourceType;

    /**
     * 来源ID（处方ID、检查申请ID等）
     */
    private Long sourceId;

    /**
     * 来源明细ID（处方明细ID、检查项目ID等）
     */
    private Long sourceItemId;

    /**
     * 收费状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.FeeItemStatusEnum}
     * 0-未收费 1-已收费 2-已退费
     */
    private Integer feeItemStatus;

    /**
     * 收费时间
     */
    private java.time.LocalDateTime feeTime;

    /**
     * 退费时间
     */
    private java.time.LocalDateTime refundTime;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

}