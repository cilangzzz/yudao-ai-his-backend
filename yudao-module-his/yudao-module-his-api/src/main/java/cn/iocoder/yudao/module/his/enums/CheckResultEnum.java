package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 核对结果枚举
 *
 * 用于闭环给药的双重核对（腕带+药品条码）
 */
@Getter
@AllArgsConstructor
public enum CheckResultEnum {

    PASS(1, "通过"),
    FAIL(2, "不通过");

    /**
     * 结果值
     */
    private final Integer result;

    /**
     * 结果名称
     */
    private final String name;

    /**
     * 根据结果值获取枚举
     */
    public static CheckResultEnum valueOf(Integer result) {
        if (result == null) {
            return null;
        }
        for (CheckResultEnum value : values()) {
            if (value.getResult().equals(result)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否通过核对
     */
    public boolean isPassed() {
        return this == PASS;
    }
}