package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 床位类型枚举
 */
@Getter
@AllArgsConstructor
public enum BedTypeEnum implements ArrayValuable<Integer> {

    NORMAL(1, "普通"),
    RESCUE(2, "抢救"),
    ICU(3, "ICU"),
    ISOLATION(4, "隔离");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BedTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(BedTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static BedTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static String getNameByCode(Integer value) {
        BedTypeEnum e = valueOf(value);
        return e != null ? e.getName() : null;
    }
}
