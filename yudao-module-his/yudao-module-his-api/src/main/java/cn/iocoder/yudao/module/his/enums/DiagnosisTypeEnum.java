package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 诊断类型枚举
 *
 * @author yudao
 */
@Getter
@AllArgsConstructor
public enum DiagnosisTypeEnum implements IntArrayValuable {

    OUTPATIENT(1, "门诊诊断"),
    ADMISSION(2, "入院诊断"),
    DISCHARGE(3, "出院诊断"),
    CORRECTION(4, "修正诊断"),
    SUPPLEMENT(5, "补充诊断");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DiagnosisTypeEnum::getType).toArray();

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
