package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 医保类别枚举
 */
@Getter
@AllArgsConstructor
public enum InsuranceCategoryEnum {

    CLASS_A(1, "甲类"),
    CLASS_B(2, "乙类"),
    CLASS_C(3, "丙类");

    /**
     * 类别值
     */
    private final Integer category;

    /**
     * 类别名称
     */
    private final String name;

}