package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退药状态枚举
 */
@Getter
@AllArgsConstructor
public enum DrugReturnStatusEnum implements ArrayValuable<Integer> {

    PENDING(1, "待审核"),
    APPROVED(2, "审核通过"),
    REJECTED(3, "审核拒绝"),
    INBOUNDED(4, "已入库"),
    REFUNDED(5, "已退款"),
    CANCELLED(6, "已取消");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DrugReturnStatusEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(DrugReturnStatusEnum::getValue).toArray(Integer[]::new);
    }

    public static DrugReturnStatusEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static String getName(Integer value) {
        DrugReturnStatusEnum enumObj = valueOf(value);
        return enumObj != null ? enumObj.getName() : "";
    }

    /**
     * 是否可以审核
     */
    public static boolean canAudit(Integer value) {
        return PENDING.getValue().equals(value);
    }

    /**
     * 是否可以取消
     */
    public static boolean canCancel(Integer value) {
        return PENDING.getValue().equals(value);
    }

    /**
     * 是否可以入库
     */
    public static boolean canInbound(Integer value) {
        return APPROVED.getValue().equals(value);
    }

    /**
     * 是否可以退款
     */
    public static boolean canRefund(Integer value) {
        return INBOUNDED.getValue().equals(value);
    }

}