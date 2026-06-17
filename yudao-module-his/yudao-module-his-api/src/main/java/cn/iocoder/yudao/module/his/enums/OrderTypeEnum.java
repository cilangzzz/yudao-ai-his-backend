package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 医嘱类型枚举
 *
 * 支持药品、检验、检查、护理、手术、饮食等多种医嘱类型
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    DRUG(1, "药品"),
    LAB(2, "检验"),
    EXAM(3, "检查"),
    NURSING(4, "护理"),
    SURGERY(5, "手术"),
    DIET(6, "饮食"),
    OTHER(7, "其他");

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
    public static OrderTypeEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        for (OrderTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否需要药品库存
     */
    public boolean needsDrugStock() {
        return this == DRUG;
    }

    /**
     * 是否需要标本采集
     */
    public boolean needsSpecimenCollection() {
        return this == LAB;
    }

    /**
     * 是否需要设备预约
     */
    public boolean needsEquipmentReservation() {
        return this == EXAM;
    }

    /**
     * 是否需要给药执行
     */
    public boolean needsMedicationAdmin() {
        return this == DRUG;
    }
}