package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 医嘱分类枚举
 *
 * 长期医嘱: 按频次重复执行，如每日用药
 * 临时医嘱: 仅执行一次，如临时检查
 */
@Getter
@AllArgsConstructor
public enum OrderCategoryEnum {

    LONG_TERM(1, "长期医嘱"),
    TEMPORARY(2, "临时医嘱");

    /**
     * 分类值
     */
    private final Integer category;

    /**
     * 分类名称
     */
    private final String name;

    /**
     * 根据分类值获取枚举
     */
    public static OrderCategoryEnum valueOf(Integer category) {
        if (category == null) {
            return null;
        }
        for (OrderCategoryEnum value : values()) {
            if (value.getCategory().equals(category)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否为长期医嘱
     */
    public boolean isLongTerm() {
        return this == LONG_TERM;
    }

    /**
     * 是否为临时医嘱
     */
    public boolean isTemporary() {
        return this == TEMPORARY;
    }

    /**
     * 是否需要停止操作
     */
    public boolean needsStopOperation() {
        return this == LONG_TERM;
    }
}