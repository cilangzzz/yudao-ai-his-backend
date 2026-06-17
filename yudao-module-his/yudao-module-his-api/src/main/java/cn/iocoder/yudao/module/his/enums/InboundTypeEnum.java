package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库类型枚举
 */
@Getter
@AllArgsConstructor
public enum InboundTypeEnum {

    PURCHASE(1, "采购入库"),
    RETURN(2, "退货入库"),
    TRANSFER(3, "调拨入库");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型名称
     */
    private final String name;

    public static InboundTypeEnum valueOf(Integer code) {
        for (InboundTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
