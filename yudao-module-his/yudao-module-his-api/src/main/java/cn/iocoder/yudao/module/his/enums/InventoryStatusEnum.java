package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 盘点状态枚举
 */
@Getter
@AllArgsConstructor
public enum InventoryStatusEnum {

    IN_PROGRESS(1, "进行中"),
    COMPLETED(2, "已完成");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    public static InventoryStatusEnum valueOf(Integer code) {
        for (InventoryStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
