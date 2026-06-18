package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 医嘱紧急程度枚举
 */
@Getter
@AllArgsConstructor
public enum OrderUrgencyEnum implements ArrayValiable<Integer> {

    NORMAL(0, "常规"),
    URGENT(1, "紧急");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(OrderUrgencyEnum::getUrgency).toArray(Integer[]::new);

    /**
     * 紧急程度值
     */
    private final Integer urgency;
    /**
     * 紧急程度名称
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    public static OrderUrgencyEnum valueOf(Integer urgency) {
        for (OrderUrgencyEnum e : values()) {
            if (e.getUrgency().equals(urgency)) {
                return e;
            }
        }
        return null;
    }

}
