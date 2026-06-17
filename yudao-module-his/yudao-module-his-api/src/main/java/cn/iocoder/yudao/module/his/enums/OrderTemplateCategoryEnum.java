package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 医嘱模板分类枚举
 */
@Getter
@AllArgsConstructor
public enum OrderTemplateCategoryEnum implements IntArrayValuable {

    PERSONAL(1, "个人模板"),
    DEPT(2, "科室模板"),
    HOSPITAL(3, "全院模板"),
    DIAGNOSIS(4, "疾病模板");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(OrderTemplateCategoryEnum::getCategory).toArray();

    /**
     * 分类值
     */
    private final Integer category;
    /**
     * 分类名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static OrderTemplateCategoryEnum valueOf(Integer category) {
        for (OrderTemplateCategoryEnum e : values()) {
            if (e.getCategory().equals(category)) {
                return e;
            }
        }
        return null;
    }

}
