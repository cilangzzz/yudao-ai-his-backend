package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 入院状态枚举
 */
@Getter
@AllArgsConstructor
public enum AdmissionStatusEnum implements ArrayValuable<Integer> {

    IN_HOSPITAL(1, "在院"),
    DISCHARGED(2, "出院"),
    TRANSFERRED(3, "转科");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AdmissionStatusEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(AdmissionStatusEnum::getValue).toArray(Integer[]::new);
    }

    public static AdmissionStatusEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
