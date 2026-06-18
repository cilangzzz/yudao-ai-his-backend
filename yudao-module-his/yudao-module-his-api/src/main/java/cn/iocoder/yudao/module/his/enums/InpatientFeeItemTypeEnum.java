package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 住院费用项目类型枚举
 */
@Getter
@AllArgsConstructor
public enum InpatientFeeItemTypeEnum {

    DRUG(1, "药品"),
    EXAMINATION(2, "检查"),
    LABORATORY(3, "检验"),
    TREATMENT(4, "诊疗"),
    NURSING(5, "护理"),
    SURGERY(6, "手术"),
    BED(7, "床位"),
    OTHER(8, "其他");

    /**
     * 类型值
     */
    private final Integer type;

    /**
     * 类型名称
     */
    private final String name;

    /**
     * 根据类型值获取枚举
     */
    public static InpatientFeeItemTypeEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        for (InpatientFeeItemTypeEnum enumItem : values()) {
            if (enumItem.getType().equals(type)) {
                return enumItem;
            }
        }
        return null;
    }

    /**
     * 获取类型名称
     */
    public static String getName(Integer type) {
        InpatientFeeItemTypeEnum enumItem = valueOf(type);
        return enumItem != null ? enumItem.getName() : "未知";
    }

}