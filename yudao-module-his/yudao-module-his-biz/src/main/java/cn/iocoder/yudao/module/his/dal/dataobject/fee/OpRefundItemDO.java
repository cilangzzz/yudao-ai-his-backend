package cn.iocoder.yudao.module.his.dal.dataobject.fee;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退费明细 DO
 *
 * 支持部分退费功能，每个退费明细对应一个原支付明细项
 *
 * @author yudao
 */
@TableName("op_refund_item")
@KeySequence("op_refund_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpRefundItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 退费记录ID
     */
    private Long refundId;

    /**
     * 退费单号
     */
    private String refundNo;

    /**
     * 原支付ID
     */
    private Long paymentId;

    /**
     * 原支付明细ID
     */
    private Long paymentItemId;

    /**
     * 费用ID
     */
    private Long feeId;

    /**
     * 费用明细ID
     */
    private Long feeItemId;

    /**
     * 挂号ID
     */
    private Long registerId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

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
     * 原数量
     */
    private BigDecimal originalQuantity;

    /**
     * 退费数量
     */
    private BigDecimal refundQuantity;

    /**
     * 原金额
     */
    private BigDecimal originalAmount;

    /**
     * 退费金额
     */
    private BigDecimal refundAmount;

    /**
     * 医保退费金额
     */
    private BigDecimal insuranceRefundAmount;

    /**
     * 自费退费金额
     */
    private BigDecimal selfRefundAmount;

    /**
     * 支付方式
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PayTypeEnum}
     * 1-现金 2-微信 3-支付宝 4-医保 5-银行卡
     */
    private Integer payType;

    /**
     * 医保类型
     */
    private Integer insuranceType;

    /**
     * 医保卡号
     */
    private String insuranceNo;

    /**
     * 医保编码
     */
    private String insuranceCode;

    /**
     * 医保类别
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceCategoryEnum}
     * 1-甲类 2-乙类 3-丙类
     */
    private Integer insuranceCategory;

    /**
     * 退费状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.RefundStatusEnum}
     * 1-待审核 2-已通过 3-已拒绝 4-已完成
     */
    private Integer refundStatus;

    /**
     * 退费原因
     */
    private String refundReason;

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
     * 退费完成时间
     */
    private LocalDateTime refundTime;

    /**
     * 备注
     */
    private String remark;

}