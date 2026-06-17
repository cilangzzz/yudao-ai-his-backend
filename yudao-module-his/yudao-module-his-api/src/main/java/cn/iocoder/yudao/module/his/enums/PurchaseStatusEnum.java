package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 采购状态枚举
 */
@Getter
@AllArgsConstructor
public enum PurchaseStatusEnum {

    DRAFT(1, "草稿"),
    SUBMITTED(2, "已提交"),
    APPROVED(3, "已审批"),
    PURCHASED(4, "已采购"),
    COMPLETED(5, "已完成");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    public static PurchaseStatusEnum valueOf(Integer code) {
        for (PurchaseStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
