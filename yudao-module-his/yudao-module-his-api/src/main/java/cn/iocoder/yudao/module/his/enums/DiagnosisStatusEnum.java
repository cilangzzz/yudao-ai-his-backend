package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 诊断状态枚举
 *
 * @author yudao
 */
@Getter
@AllArgsConstructor
public enum DiagnosisStatusEnum implements IntArrayValuable {

    PRELIMINARY(1, "初步诊断"),
    CONFIRMED(2, "确定诊断"),
    CORRECTED(3, "修正诊断");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DiagnosisStatusEnum::getStatus).toArray();

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
