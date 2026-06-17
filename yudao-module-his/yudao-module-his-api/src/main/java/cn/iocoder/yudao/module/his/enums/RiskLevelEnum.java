package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 风险等级枚举
 */
@Getter
@AllArgsConstructor
public enum RiskLevelEnum implements ArrayValuable<Integer> {

    LOW(1, "低"),
    MEDIUM(2, "中"),
    HIGH(3, "高");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RiskLevelEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(RiskLevelEnum::getValue).toArray(Integer[]::new);
    }

    public static RiskLevelEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
