package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付状态枚举
 */
@Getter
@AllArgsConstructor
public enum PaymentStatusEnum implements IntArrayValuable {

    SUCCESS(1, "成功"),
    FAILED(2, "失败"),
    REFUNDED(3, "已退费");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PaymentStatusEnum::getStatus).toArray();

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
