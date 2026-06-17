package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 医嘱状态枚举
 *
 * 医嘱状态流转: 开立 → 审核 → 执行中 → 已完成/已停止/已作废
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    CREATED(1, "开立"),
    AUDITED(2, "审核"),
    EXECUTING(3, "执行中"),
    COMPLETED(4, "已完成"),
    STOPPED(5, "已停止"),
    CANCELLED(6, "已作废");

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
    public static OrderStatusEnum valueOf(Integer status) {
        if (status == null) {
            return null;
        }
        for (OrderStatusEnum value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否可以审核
     */
    public boolean canAudit() {
        return this == CREATED;
    }

    /**
     * 是否可以执行
     */
    public boolean canExecute() {
        return this == AUDITED;
    }

    /**
     * 是否可以停止
     */
    public boolean canStop() {
        return this == EXECUTING;
    }

    /**
     * 是否可以作废
     */
    public boolean canCancel() {
        return this == CREATED || this == AUDITED;
    }

    /**
     * 是否已审核
     */
    public boolean isAudited() {
        return this != CREATED && this != CANCELLED;
    }

    /**
     * 是否已执行
     */
    public boolean isExecuted() {
        return this == EXECUTING || this == COMPLETED;
    }

    /**
     * 是否有效医嘱
     */
    public boolean isValid() {
        return this != STOPPED && this != CANCELLED;
    }
}