package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 预交金退还状态枚举
 *
 * @author yudao
 */
@Getter
@AllArgsConstructor
public enum PrepaymentRefundStatusEnum implements ArrayValuable<Integer> {

    APPLYING(1, "申请中"),
    APPROVED(2, "已审批"),
    REFUNDED(3, "已退还"),
    REJECTED(4, "已拒绝");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PrepaymentRefundStatusEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(PrepaymentRefundStatusEnum::getValue).toArray(Integer[]::new);
    }

    public static PrepaymentRefundStatusEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

}
