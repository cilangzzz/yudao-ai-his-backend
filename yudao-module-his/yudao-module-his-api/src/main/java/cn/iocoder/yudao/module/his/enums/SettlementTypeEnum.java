package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 住院结算类型枚举
 */
@Getter
@AllArgsConstructor
public enum SettlementTypeEnum {

    DISCHARGE(1, "出院结算"),
    MIDWAY(2, "中途结算"),
    CREDIT(3, "挂账结算");

    /**
     * 类型值
     */
    private final Integer type;

    /**
     * 类型名称
     */
    private final String name;

    /**
     * 根据类型值获取枚举
     */
    public static SettlementTypeEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        for (SettlementTypeEnum enumItem : values()) {
            if (enumItem.getType().equals(type)) {
                return enumItem;
            }
        }
        return null;
    }

    /**
     * 获取类型名称
     */
    public static String getName(Integer type) {
        SettlementTypeEnum enumItem = valueOf(type);
        return enumItem != null ? enumItem.getName() : "未知";
    }

}