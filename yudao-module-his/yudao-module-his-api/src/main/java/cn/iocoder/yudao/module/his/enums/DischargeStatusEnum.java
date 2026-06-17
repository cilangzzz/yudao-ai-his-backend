package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 出院状态枚举
 */
@Getter
@AllArgsConstructor
public enum DischargeStatusEnum implements ArrayValuable<Integer> {

    PENDING_SETTLE(1, "待结算"),
    SETTLED(2, "已结算"),
    DISCHARGED(3, "已出院");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DischargeStatusEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(DischargeStatusEnum::getValue).toArray(Integer[]::new);
    }

    public static DischargeStatusEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
