package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 护理记录类型枚举
 */
@Getter
@AllArgsConstructor
public enum NursingRecordTypeEnum {

    GENERAL(1, "一般护理记录"),
    CRITICAL(2, "危重护理记录"),
    SURGERY(3, "手术护理记录"),
    HANDOVER(4, "交接班记录"),
    SPECIAL(5, "特殊护理记录");

    /**
     * 类型值
     */
    private final Integer type;

    /**
     * 类型名称
     */
    private final String name;

    /**
     * 根据类型值获取枚举
     */
    public static NursingRecordTypeEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        for (NursingRecordTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}