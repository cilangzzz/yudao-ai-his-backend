package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 采购类型枚举
 */
@Getter
@AllArgsConstructor
public enum PurchaseTypeEnum {

    PLANNED(1, "计划采购"),
    URGENT(2, "紧急采购");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型名称
     */
    private final String name;

    public static PurchaseTypeEnum valueOf(Integer code) {
        for (PurchaseTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
