package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 费用来源类型枚举
 */
@Getter
@AllArgsConstructor
public enum FeeSourceTypeEnum {

    REGISTER(1, "挂号"),
    PRESCRIPTION(2, "处方"),
    EXAMINATION(3, "检查"),
    LAB(4, "检验"),
    TREATMENT(5, "治疗"),
    SURGERY(6, "手术"),
    OTHER(7, "其他");

    /**
     * 类型值
     */
    private final Integer type;

    /**
     * 类型名称
     */
    private final String name;

}