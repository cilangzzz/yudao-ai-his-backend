package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 床位状态枚举
 */
@Getter
@AllArgsConstructor
public enum BedStatusEnum implements ArrayValuable<Integer> {

    EMPTY(1, "空床"),
    OCCUPIED(2, "占用"),
    RESERVED(3, "预约"),
    CLEANING(4, "清洁"),
    MAINTENANCE(5, "维修");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BedStatusEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(BedStatusEnum::getValue).toArray(Integer[]::new);
    }

    public static BedStatusEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    /**
     * 判断床位是否可用
     */
    public boolean isAvailable() {
        return this == EMPTY;
    }
}
