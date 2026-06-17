package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退药类型枚举
 */
@Getter
@AllArgsConstructor
public enum DrugReturnTypeEnum implements ArrayValuable<Integer> {

    OUTPATIENT(1, "门诊退药"),
    INPATIENT(2, "住院退药");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DrugReturnTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(DrugReturnTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static DrugReturnTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static String getName(Integer value) {
        DrugReturnTypeEnum enumObj = valueOf(value);
        return enumObj != null ? enumObj.getName() : "";
    }

}