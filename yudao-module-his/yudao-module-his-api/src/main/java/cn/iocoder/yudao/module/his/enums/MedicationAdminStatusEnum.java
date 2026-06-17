package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 给药记录状态枚举 (eMAR)
 *
 * 用于闭环给药管理，记录每次给药执行状态
 */
@Getter
@AllArgsConstructor
public enum MedicationAdminStatusEnum {

    PENDING(1, "待执行"),
    EXECUTED(2, "已执行"),
    NOT_EXECUTED(3, "未执行"),
    DELAYED(4, "延迟执行"),
    REFUSED(5, "患者拒绝");

    /**
     * 状态值
     */
    private final Integer status;

    /**
     * 状态名称
     */
    private final String name;

    /**
     * 根据状态值获取枚举
     */
    public static MedicationAdminStatusEnum valueOf(Integer status) {
        if (status == null) {
            return null;
        }
        for (MedicationAdminStatusEnum value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否可以执行
     */
    public boolean canExecute() {
        return this == PENDING;
    }

    /**
     * 是否已完成执行
     */
    public boolean isCompleted() {
        return this == EXECUTED;
    }

    /**
     * 是否需要记录原因
     */
    public boolean needsReason() {
        return this == NOT_EXECUTED || this == DELAYED || this == REFUSED;
    }
}