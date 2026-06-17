package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 预交金使用类型枚举
 *
 * @author yudao
 */
@Getter
@AllArgsConstructor
public enum PrepaymentUseTypeEnum implements ArrayValuable<Integer> {

    SETTLE_FEE(1, "结算费用"),
    REFUND_TRANSFER(2, "退费转出"),
    OTHER_DEDUCT(3, "其他扣减");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PrepaymentUseTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(PrepaymentUseTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static PrepaymentUseTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

}
