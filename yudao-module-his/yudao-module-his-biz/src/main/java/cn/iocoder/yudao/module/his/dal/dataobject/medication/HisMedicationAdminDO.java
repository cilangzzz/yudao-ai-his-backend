package cn.iocoder.yudao.module.his.dal.dataobject.medication;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 给药记录 DO (eMAR)
 *
 * 电子给药记录，实现闭环给药管理（HIMSS EMRAM Stage 5核心功能）
 * 对应FHIR资源: MedicationAdministration
 *
 * 支持腕带+药品条码双重核对，确保给药安全
 */
@TableName("his_medication_admin")
@KeySequence("his_medication_admin_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisMedicationAdminDO extends TenantBaseDO {

    /**
     * 给药记录ID
     */
    @TableId
    private Long id;

    /**
     * 记录编号
     */
    private String adminNo;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 住院ID
     */
    private Long admissionId;

    /**
     * 住院号
     */
    private String admissionNo;

    /**
     * 医嘱ID
     */
    private Long orderId;

    /**
     * 医嘱编号
     */
    private String orderNo;

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
     * 规格
     */
    private String spec;

    /**
     * 剂量
     */
    private BigDecimal dosage;

    /**
     * 剂量单位
     */
    private String dosageUnit;

    /**
     * 给药途径
     */
    private String route;

    /**
     * 预定执行时间
     */
    private LocalDateTime scheduledTime;

    /**
     * 实际执行时间
     */
    private LocalDateTime actualTime;

    /**
     * 执行护士ID
     */
    private Long nurseId;

    /**
     * 执行护士姓名
     */
    private String nurseName;

    // ==================== 闭环给药核对字段 ====================

    /**
     * 腕带扫描状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ScanStatusEnum}
     * 0-未扫描 1-匹配 2-不匹配
     */
    private Integer wristbandScanStatus;

    /**
     * 腕带扫描时间
     */
    private LocalDateTime wristbandScanTime;

    /**
     * 腕带扫描结果
     */
    private String wristbandScanResult;

    /**
     * 药品扫描状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ScanStatusEnum}
     * 0-未扫描 1-匹配 2-不匹配
     */
    private Integer drugScanStatus;

    /**
     * 药品扫描时间
     */
    private LocalDateTime drugScanTime;

    /**
     * 药品扫描结果
     */
    private String drugScanResult;

    /**
     * 药品批号
     */
    private String drugBatchNo;

    /**
     * 药品有效期
     */
    private LocalDate drugExpireDate;

    /**
     * 核对结果
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.CheckResultEnum}
     * 1-通过 2-不通过
     */
    private Integer checkResult;

    // ==================== 执行状态字段 ====================

    /**
     * 状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.MedicationAdminStatusEnum}
     * 1-待执行 2-已执行 3-未执行 4-延迟执行 5-患者拒绝
     */
    private Integer status;

    /**
     * 未执行/延迟原因
     */
    private String reason;

    /**
     * 原因类型
     *
     * 患者拒绝/病情变化/药品问题/其他
     */
    private String reasonType;

    /**
     * 是否通知医生
     */
    private Boolean notifyDoctor;

    // ==================== 记账字段 ====================

    /**
     * 记账状态
     *
     * 0-未记账 1-已记账
     */
    private Integer chargeStatus;

    /**
     * 记账时间
     */
    private LocalDateTime chargeTime;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否可以执行
     */
    public boolean canExecute() {
        return status != null && status == 1;
    }

    /**
     * 是否已执行
     */
    public boolean isExecuted() {
        return status != null && status == 2;
    }

    /**
     * 是否需要记录原因
     */
    public boolean needsReason() {
        return status != null && (status == 3 || status == 4 || status == 5);
    }

    /**
     * 腕带是否匹配
     */
    public boolean isWristbandMatched() {
        return wristbandScanStatus != null && wristbandScanStatus == 1;
    }

    /**
     * 药品是否匹配
     */
    public boolean isDrugMatched() {
        return drugScanStatus != null && drugScanStatus == 1;
    }

    /**
     * 双重核对是否通过
     */
    public boolean isDoubleCheckPassed() {
        return isWristbandMatched() && isDrugMatched();
    }

    /**
     * 是否已记账
     */
    public boolean isCharged() {
        return chargeStatus != null && chargeStatus == 1;
    }

    /**
     * 是否延迟给药
     */
    public boolean isDelayed() {
        if (scheduledTime == null || actualTime == null) {
            return false;
        }
        // 超过预定时间1小时视为延迟
        return actualTime.isAfter(scheduledTime.plusHours(1));
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) {
            return "未知";
        }
        switch (status) {
            case 1:
                return "待执行";
            case 2:
                return "已执行";
            case 3:
                return "未执行";
            case 4:
                return "延迟执行";
            case 5:
                return "患者拒绝";
            default:
                return "未知";
        }
    }
}