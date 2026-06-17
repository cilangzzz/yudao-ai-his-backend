package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退还方式枚举
 *
 * @author yudao
 */
@Getter
@AllArgsConstructor
public enum RefundTypeEnum implements ArrayValuable<Integer> {

    CASH(1, "现金"),
    BANK_CARD(2, "银行卡"),
    ORIGINAL(3, "原路退回");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RefundTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(RefundTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static RefundTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

}
