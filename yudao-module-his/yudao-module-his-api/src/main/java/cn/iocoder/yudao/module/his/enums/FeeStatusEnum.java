package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 费用状态枚举
 */
@Getter
@AllArgsConstructor
public enum FeeStatusEnum {

    NOT_PAID(0, "未收费"),
    PAID(1, "已收费"),
    REFUNDED(2, "已退费");

    /**
     * 状态值
     */
    private final Integer status;

    /**
     * 状态名称
     */
    private final String name;

}