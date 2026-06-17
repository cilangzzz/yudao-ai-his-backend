package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扫描状态枚举
 *
 * 用于腕带扫描和药品条码扫描的状态记录
 */
@Getter
@AllArgsConstructor
public enum ScanStatusEnum {

    NOT_SCANNED(0, "未扫描"),
    MATCHED(1, "匹配"),
    NOT_MATCHED(2, "不匹配");

    /**
     * 状态值
     */
    private final Integer status;

    /**
     * 状态名称
     */
    private final String name;

    /**
     * 根据状态值获取枚举
     */
    public static ScanStatusEnum valueOf(Integer status) {
        if (status == null) {
            return null;
        }
        for (ScanStatusEnum value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 是否已扫描
     */
    public boolean isScanned() {
        return this != NOT_SCANNED;
    }

    /**
     * 是否匹配成功
     */
    public boolean isMatched() {
        return this == MATCHED;
    }
}