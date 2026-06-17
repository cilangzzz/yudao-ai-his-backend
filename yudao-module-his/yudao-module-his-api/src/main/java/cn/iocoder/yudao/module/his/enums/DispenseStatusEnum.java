package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发药状态枚举
 *
 * 用于标识发药单的处理状态
 */
@Getter
@AllArgsConstructor
public enum DispenseStatusEnum {

    /**
     * 待调配
     */
    PENDING_DISPENSE(1, "待调配"),

    /**
     * 已调配
     */
    DISPENSED(2, "已调配"),

    /**
     * 已发药
     */
    SENT(3, "已发药"),

    /**
     * 已退药
     */
    RETURNED(4, "已退药");

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
     *
     * @param status 状态值
     * @return 枚举对象，未找到返回 null
     */
    public static DispenseStatusEnum getByStatus(Integer status) {
        if (status == null) {
            return null;
        }
        for (DispenseStatusEnum value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 判断是否为有效状态值
     *
     * @param status 状态值
     * @return 是否有效
     */
    public static boolean isValidStatus(Integer status) {
        return getByStatus(status) != null;
    }

    /**
     * 获取状态名称
     *
     * @param status 状态值
     * @return 状态名称，未找到返回空字符串
     */
    public static String getNameByStatus(Integer status) {
        DispenseStatusEnum enumValue = getByStatus(status);
        return enumValue != null ? enumValue.getName() : "";
    }

}