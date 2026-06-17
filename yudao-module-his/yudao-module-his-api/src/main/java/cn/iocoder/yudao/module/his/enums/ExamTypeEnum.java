package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检查类型枚举
 */
@Getter
@AllArgsConstructor
public enum ExamTypeEnum {

    IMAGING(1, "影像检查"),
    LABORATORY(2, "检验检查"),
    PATHOLOGY(3, "病理检查"),
    FUNCTIONAL(4, "功能检查");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型名称
     */
    private final String name;

    public static ExamTypeEnum valueOf(Integer code) {
        for (ExamTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
