package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退药药品状态枚举
 */
@Getter
@AllArgsConstructor
public enum DrugConditionEnum implements ArrayValuable<Integer> {

    GOOD(1, "完好"),
    DAMAGED(2, "包装破损"),
    EXPIRED(3, "过期"),
    OTHER(4, "其他");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DrugConditionEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(DrugConditionEnum::getValue).toArray(Integer[]::new);
    }

    public static DrugConditionEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static String getName(Integer value) {
        DrugConditionEnum enumObj = valueOf(value);
        return enumObj != null ? enumObj.getName() : "";
    }

    /**
     * 是否可再利用
     */
    public boolean canReuse() {
        return this == GOOD;
    }

}