package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 医保类型枚举
 */
@Getter
@AllArgsConstructor
public enum InsuranceTypeEnum {

    URBAN_EMPLOYEE(1, "城镇职工医保"),
    URBAN_RESIDENT(2, "城镇居民医保"),
    NEW_RURAL(3, "新农合"),
    SELF_PAY(4, "自费");

    /**
     * 编码
     */
    private final Integer code;

    /**
     * 名称
     */
    private final String name;

    public static InsuranceTypeEnum valueOf(Integer code) {
        for (InsuranceTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
