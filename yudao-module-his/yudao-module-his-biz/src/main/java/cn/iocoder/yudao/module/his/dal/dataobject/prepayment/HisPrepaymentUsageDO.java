package cn.iocoder.yudao.module.his.dal.dataobject.prepayment;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预交金使用记录 DO
 *
 * 记录预交金的使用情况，包括结算费用、退费转出等
 *
 * @author yudao
 */
@TableName(value = "his_prepayment_usage", autoResultMap = true)
@KeySequence("his_prepayment_usage_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisPrepaymentUsageDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 使用记录编号
     */
    private String usageNo;

    /**
     * 预交金记录ID
     */
    private Long prepaymentId;

    /**
     * 预交金编号
     */
    private String prepaymentNo;

    /**
     * 入院记录ID
     */
    private Long admissionId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 使用金额
     */
    private BigDecimal useAmount;

    /**
     * 使用类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PrepaymentUseTypeEnum}
     * 1-结算费用 2-退费转出 3-其他扣减
     */
    private Integer useType;

    /**
     * 费用明细ID
     */
    private Long feeItemId;

    /**
     * 费用项目名称
     */
    private String feeItemName;

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 使用时间
     */
    private LocalDateTime useTime;

    /**
     * 备注
     */
    private String remark;

}