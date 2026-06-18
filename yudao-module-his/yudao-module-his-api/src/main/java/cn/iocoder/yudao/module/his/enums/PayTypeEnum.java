package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付方式枚举
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum implements ArrayValuable<Integer> {

    CASH(1, "现金"),
    WECHAT(2, "微信"),
    ALIPAY(3, "支付宝"),
    INSURANCE(4, "医保"),
    BANK_CARD(5, "银行卡");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(PayTypeEnum::getType).toArray(Integer[]::new);

    /**
     * 类型值
     */
    private final Integer type;
    /**
     * 类型名称
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
