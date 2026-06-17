package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退费状态枚举
 */
@Getter
@AllArgsConstructor
public enum RefundStatusEnum implements IntArrayValuable {

    PENDING(1, "待审核"),
    APPROVED(2, "已通过"),
    REJECTED(3, "已拒绝"),
    COMPLETED(4, "已完成");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(RefundStatusEnum::getStatus).toArray();

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
