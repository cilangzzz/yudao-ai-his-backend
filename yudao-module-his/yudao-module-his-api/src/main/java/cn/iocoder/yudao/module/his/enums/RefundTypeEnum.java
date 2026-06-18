package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退费类型枚举
 *
 * @author yudao
 */
@Getter
@AllArgsConstructor
public enum RefundTypeEnum implements ArrayValuable<Integer> {

    FULL(1, "全额退费"),
    PARTIAL(2, "部分退费");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RefundTypeEnum::getType).toArray();

    private final Integer type;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(RefundTypeEnum::getType).toArray(Integer[]::new);
    }

    public static RefundTypeEnum valueOf(Integer type) {
        return Arrays.stream(values())
                .filter(e -> e.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

}
