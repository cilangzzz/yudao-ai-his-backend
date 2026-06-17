package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付方式枚举
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum implements IntArrayValuable {

    CASH(1, "现金"),
    WECHAT(2, "微信"),
    ALIPAY(3, "支付宝"),
    INSURANCE(4, "医保"),
    BANK_CARD(5, "银行卡");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PayTypeEnum::getType).toArray();

    /**
     * 类型值
     */
    private final Integer type;
    /**
     * 类型名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
