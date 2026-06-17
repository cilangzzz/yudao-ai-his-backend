package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付方式枚举
 *
 * @author yudao
 */
@Getter
@AllArgsConstructor
public enum PaymentTypeEnum implements ArrayValuable<Integer> {

    CASH(1, "现金"),
    BANK_CARD(2, "银行卡"),
    MEDICAL_CARD(3, "医保卡"),
    WECHAT(4, "微信"),
    ALIPAY(5, "支付宝"),
    MIXED(6, "混合支付");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PaymentTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(PaymentTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static PaymentTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

}
