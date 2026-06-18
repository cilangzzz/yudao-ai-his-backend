package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付状态枚举
 */
@Getter
@AllArgsConstructor
public enum PaymentStatusEnum implements ArrayValuable<Integer> {

    SUCCESS(1, "成功"),
    FAILED(2, "失败"),
    REFUNDED(3, "已退费");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(PaymentStatusEnum::getStatus).toArray(Integer[]::new);

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
