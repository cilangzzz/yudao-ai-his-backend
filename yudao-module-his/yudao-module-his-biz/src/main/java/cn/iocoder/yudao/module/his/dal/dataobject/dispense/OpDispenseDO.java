package cn.iocoder.yudao.module.his.dal.dataobject.dispense;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发药记录主表 DO
 *
 * 门诊发药信息，记录处方的调配、发药、退药等全流程
 */
@TableName("op_dispense")
@KeySequence("op_dispense_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpDispenseDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 发药单号
     */
    private String dispenseNo;

    /**
     * 处方ID
     */
    private Long prescriptionId;

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
     * 药房ID
     */
    private Long pharmacyId;

    /**
     * 药房名称
     */
    private String pharmacyName;

    /**
     * 调配药师ID
     */
    private Long dispensePharmacist;

    /**
     * 调配药师姓名
     */
    private String dispensePharmacistName;

    /**
     * 调配时间
     */
    private LocalDateTime dispenseTime;

    /**
     * 发药药师ID
     */
    private Long sendPharmacist;

    /**
     * 发药药师姓名
     */
    private String sendPharmacistName;

    /**
     * 发药时间
     */
    private LocalDateTime sendTime;

    /**
     * 发药状态：1-待调配 2-已调配 3-已发药 4-已退药
     */
    private Integer dispenseStatus;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否待调配
     */
    public boolean isPendingDispense() {
        return dispenseStatus != null && dispenseStatus == 1;
    }

    /**
     * 是否已调配
     */
    public boolean isDispensed() {
        return dispenseStatus != null && dispenseStatus >= 2;
    }

    /**
     * 是否已发药
     */
    public boolean isSent() {
        return dispenseStatus != null && dispenseStatus >= 3;
    }

    /**
     * 是否已退药
     */
    public boolean isReturned() {
        return dispenseStatus != null && dispenseStatus == 4;
    }

    /**
     * 是否可以调配
     */
    public boolean canDispense() {
        return dispenseStatus != null && dispenseStatus == 1;
    }

    /**
     * 是否可以发药
     */
    public boolean canSend() {
        return dispenseStatus != null && dispenseStatus == 2;
    }

    /**
     * 是否可以退药
     */
    public boolean canReturn() {
        return dispenseStatus != null && dispenseStatus == 3;
    }

}
