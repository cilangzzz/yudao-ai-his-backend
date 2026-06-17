package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 药品库存状态枚举
 */
@Getter
@AllArgsConstructor
public enum DrugStockStatusEnum {

    NORMAL(1, "正常"),
    NEAR_EXPIRY(2, "近效期"),
    EXPIRED(3, "过期");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    public static DrugStockStatusEnum valueOf(Integer code) {
        for (DrugStockStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
