package cn.iocoder.yudao.module.his.dal.dataobject.prescription;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 处方明细 DO
 *
 * 门诊处方药品明细，记录每条处方中的药品信息
 */
@TableName("op_prescription_item")
@KeySequence("op_prescription_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpPrescriptionItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 处方ID
     */
    private Long prescriptionId;

    /**
     * 药品ID
     */
    private Long drugId;

    /**
     * 药品编码
     */
    private String drugCode;

    /**
     * 药品通用名
     */
    private String drugName;

    /**
     * 药品规格
     */
    private String drugSpec;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 单次剂量
     */
    private BigDecimal dosage;

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
     * 用药途径编码
     */
    private String routeCode;

    /**
     * 用药途径名称
     */
    private String routeName;

    /**
     * 数量
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
     * 用药天数
     */
    private Integer days;

    /**
     * 皮试标志：0-不需要 1-需要 2-已做通过 3-已做未通过
     */
    private Integer skinTest;

    /**
     * 皮试结果
     */
    private String skinTestResult;

    /**
     * 皮试时间
     */
    private LocalDateTime skinTestTime;

    /**
     * 皮试护士ID
     */
    private Long skinTestNurse;

    /**
     * 用药备注
     */
    private String remarks;

    /**
     * 排序号
     */
    private Integer sortOrder;

    // ==================== 业务方法 ====================

    /**
     * 是否需要皮试
     */
    public boolean needSkinTest() {
        return skinTest != null && skinTest >= 1;
    }

    /**
     * 皮试是否通过
     */
    public boolean isSkinTestPassed() {
        return skinTest != null && skinTest == 2;
    }

    /**
     * 皮试是否未通过
     */
    public boolean isSkinTestFailed() {
        return skinTest != null && skinTest == 3;
    }

    /**
     * 是否已完成皮试
     */
    public boolean isSkinTestDone() {
        return skinTest != null && (skinTest == 2 || skinTest == 3);
    }

    /**
     * 计算金额
     */
    public void calculateAmount() {
        if (quantity != null && unitPrice != null) {
            this.amount = quantity.multiply(unitPrice);
        }
    }

    /**
     * 获取每日剂量
     */
    public BigDecimal getDailyDosage() {
        if (dosage == null) {
            return BigDecimal.ZERO;
        }
        // 根据频次计算每日剂量
        int timesPerDay = getFrequencyTimes();
        return dosage.multiply(BigDecimal.valueOf(timesPerDay));
    }

    /**
     * 获取每日用药次数
     */
    public int getFrequencyTimes() {
        if (frequencyCode == null) {
            return 1;
        }
        return switch (frequencyCode) {
            case "QD", "QN" -> 1;
            case "BID", "Q12H" -> 2;
            case "TID", "Q8H" -> 3;
            case "QID", "Q6H" -> 4;
            default -> 1;
        };
    }

    /**
     * 是否为注射给药
     */
    public boolean isInjection() {
        if (routeCode == null) {
            return false;
        }
        return routeCode.equals("IV") || routeCode.equals("IVGTT")
            || routeCode.equals("IM") || routeCode.equals("IH") || routeCode.equals("ID");
    }

    /**
     * 是否为静脉给药
     */
    public boolean isIntravenous() {
        return "IV".equals(routeCode) || "IVGTT".equals(routeCode);
    }

}
