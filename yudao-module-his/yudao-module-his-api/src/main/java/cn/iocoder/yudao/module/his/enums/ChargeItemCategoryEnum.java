package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收费项目类别枚举
 */
@Getter
@AllArgsConstructor
public enum ChargeItemCategoryEnum {

    REGISTER(1, "挂号费"),
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

    /**
     * 类别值
     */
    private final Integer category;

    /**
     * 类别名称
     */
    private final String name;

}