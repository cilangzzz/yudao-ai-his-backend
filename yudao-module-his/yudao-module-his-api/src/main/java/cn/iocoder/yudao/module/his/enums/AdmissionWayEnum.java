package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 入院方式枚举
 */
@Getter
@AllArgsConstructor
public enum AdmissionWayEnum implements ArrayValuable<Integer> {

    OUTPATIENT(1, "门诊"),
    EMERGENCY(2, "急诊"),
    TRANSFER(3, "转院");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AdmissionWayEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(AdmissionWayEnum::getValue).toArray(Integer[]::new);
    }

    public static AdmissionWayEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
