package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付明细状态枚举
 */
@Getter
@AllArgsConstructor
public enum PaymentItemStatusEnum implements ArrayValiable<Integer> {

    UNPAID(0, "待支付"),
    PAID(1, "已支付"),
    REFUNDED(2, "已退费");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(PaymentItemStatusEnum::getStatus).toArray(Integer[]::new);

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名称
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
