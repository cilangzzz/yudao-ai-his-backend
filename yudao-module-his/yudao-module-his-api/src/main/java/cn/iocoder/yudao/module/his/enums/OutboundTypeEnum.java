package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutboundTypeEnum {

    PHARMACY(1, "药房领用"),
    LOSS(2, "报损出库"),
    TRANSFER(3, "调拨出库");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型名称
     */
    private final String name;

    public static OutboundTypeEnum valueOf(Integer code) {
        for (OutboundTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
