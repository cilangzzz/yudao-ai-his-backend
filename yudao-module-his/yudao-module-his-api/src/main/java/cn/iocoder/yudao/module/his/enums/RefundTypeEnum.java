package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退费类型枚举
 */
@Getter
@AllArgsConstructor
public enum RefundTypeEnum implements IntArrayValuable {

    FULL(1, "全额退费"),
    PARTIAL(2, "部分退费");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RefundTypeEnum::getType).toArray();

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