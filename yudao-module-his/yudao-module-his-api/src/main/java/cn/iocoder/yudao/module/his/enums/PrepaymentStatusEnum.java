package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 预交金状态枚举
 */
@Getter
@AllArgsConstructor
public enum PrepaymentStatusEnum implements ArrayValuable<Integer> {

    NORMAL(1, "正常"),
    REFUNDED(2, "已退");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PrepaymentStatusEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(PrepaymentStatusEnum::getValue).toArray(Integer[]::new);
    }

    public static PrepaymentStatusEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
