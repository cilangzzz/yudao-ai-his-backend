package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 住院费用退费状态枚举
 */
@Getter
@AllArgsConstructor
public enum InpatientFeeRefundStatusEnum {

    PENDING(0, "待审核"),
    APPROVED(1, "已通过"),
    REFUNDED(2, "已退费"),
    CANCELLED(3, "已取消"),
    REJECTED(4, "已拒绝");

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
    public static InpatientFeeRefundStatusEnum valueOf(Integer status) {
        if (status == null) {
            return null;
        }
        for (InpatientFeeRefundStatusEnum enumItem : values()) {
            if (enumItem.getStatus().equals(status)) {
                return enumItem;
            }
        }
        return null;
    }

    /**
     * 获取状态名称
     */
    public static String getName(Integer status) {
        InpatientFeeRefundStatusEnum enumItem = valueOf(status);
        return enumItem != null ? enumItem.getName() : "未知";
    }

}