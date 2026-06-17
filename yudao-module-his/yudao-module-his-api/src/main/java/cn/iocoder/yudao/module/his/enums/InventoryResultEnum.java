package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 盘点结果枚举
 */
@Getter
@AllArgsConstructor
public enum InventoryResultEnum {

    NORMAL(1, "正常"),
    PROFIT(2, "盘盈"),
    LOSS(3, "盘亏");

    /**
     * 结果编码
     */
    private final Integer code;

    /**
     * 结果名称
     */
    private final String name;

    public static InventoryResultEnum valueOf(Integer code) {
        for (InventoryResultEnum result : values()) {
            if (result.getCode().equals(code)) {
                return result;
            }
        }
        return null;
    }

}
