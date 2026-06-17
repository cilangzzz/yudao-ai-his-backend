package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 评估类型枚举
 */
@Getter
@AllArgsConstructor
public enum AssessTypeEnum implements ArrayValuable<Integer> {

    ADMISSION(1, "入院评估"),
    FALL(2, "跌倒评估"),
    PRESSURE_ULCER(3, "压疮评估"),
    PAIN(4, "疼痛评估"),
    NUTRITION(5, "营养评估");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AssessTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(AssessTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static AssessTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
