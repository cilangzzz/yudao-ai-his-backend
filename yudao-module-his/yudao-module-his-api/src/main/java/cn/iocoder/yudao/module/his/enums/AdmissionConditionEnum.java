package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 入院情况枚举
 */
@Getter
@AllArgsConstructor
public enum AdmissionConditionEnum implements ArrayValuable<Integer> {

    CRITICAL(1, "危"),
    URGENT(2, "急"),
    GENERAL(3, "一般");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AdmissionConditionEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(AdmissionConditionEnum::getValue).toArray(Integer[]::new);
    }

    public static AdmissionConditionEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
