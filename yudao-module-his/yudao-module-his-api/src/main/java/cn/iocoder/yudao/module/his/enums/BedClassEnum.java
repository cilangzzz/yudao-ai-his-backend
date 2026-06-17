package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 床位等级枚举
 */
@Getter
@AllArgsConstructor
public enum BedClassEnum implements ArrayValuable<Integer> {

    NORMAL(1, "普通"),
    DOUBLE(2, "双人"),
    SINGLE(3, "单人"),
    VIP(4, "VIP");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BedClassEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(BedClassEnum::getValue).toArray(Integer[]::new);
    }

    public static BedClassEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
