package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 危急值处理状态枚举
 */
@Getter
@AllArgsConstructor
public enum CriticalValueStatusEnum implements ArrayValuable<Integer> {

    DETECTED(1, "检出", "检验结果检出危急值"),
    NOTIFIED(2, "已通知", "已通知临床科室"),
    CONFIRMED(3, "已确认", "临床已确认接收"),
    PROCESSED(4, "已处理", "临床已处理"),
    TIMEOUT_ESCALATED(5, "超时升级", "超时未确认，已升级");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CriticalValueStatusEnum::getValue).toArray();

    private final Integer value;
    private final String name;
    private final String description;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(CriticalValueStatusEnum::getValue).toArray(Integer[]::new);
    }

    public static CriticalValueStatusEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    /**
     * 判断是否为终态
     */
    public boolean isFinal() {
        return this == PROCESSED;
    }

    /**
     * 判断是否需要升级
     */
    public boolean needEscalate() {
        return this == NOTIFIED;
    }

}