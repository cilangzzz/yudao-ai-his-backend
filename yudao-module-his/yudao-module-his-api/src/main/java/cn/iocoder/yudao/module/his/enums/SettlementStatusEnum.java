package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 住院结算状态枚举
 */
@Getter
@AllArgsConstructor
public enum SettlementStatusEnum {

    UNSETTLED(0, "未结算"),
    SETTLED(1, "已结算"),
    REFUNDED(2, "已退费"),
    CANCELLED(3, "已作废");

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
    public static SettlementStatusEnum valueOf(Integer status) {
        if (status == null) {
            return null;
        }
        for (SettlementStatusEnum enumItem : values()) {
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
        SettlementStatusEnum enumItem = valueOf(status);
        return enumItem != null ? enumItem.getName() : "未知";
    }

}