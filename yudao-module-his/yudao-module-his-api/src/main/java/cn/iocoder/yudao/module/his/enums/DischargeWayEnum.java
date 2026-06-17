package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 出院方式枚举
 */
@Getter
@AllArgsConstructor
public enum DischargeWayEnum implements ArrayValuable<Integer> {

    DOCTOR_ORDER(1, "医嘱出院"),
    VOLUNTARY(2, "自动出院"),
    TRANSFER(3, "转院"),
    DEATH(4, "死亡");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DischargeWayEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(DischargeWayEnum::getValue).toArray(Integer[]::new);
    }

    public static DischargeWayEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
