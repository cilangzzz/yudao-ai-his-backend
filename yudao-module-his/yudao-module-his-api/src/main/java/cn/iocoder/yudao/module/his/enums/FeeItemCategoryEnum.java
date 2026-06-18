package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 费用项目类别枚举
 */
@Getter
@AllArgsConstructor
public enum FeeItemCategoryEnum implements ArrayValiable<Integer> {

    REGISTRATION(1, "挂号费"),
    CONSULTATION(2, "诊查费"),
    EXAMINATION(3, "检查费"),
    TREATMENT(4, "治疗费"),
    SURGERY(5, "手术费"),
    LAB(6, "化验费"),
    MATERIAL(7, "材料费"),
    DRUG(8, "药品费"),
    BED(9, "床位费"),
    NURSING(10, "护理费"),
    OTHER(11, "其他");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(FeeItemCategoryEnum::getCategory).toArray(Integer[]::new);

    /**
     * 类别值
     */
    private final Integer category;
    /**
     * 类别名称
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
