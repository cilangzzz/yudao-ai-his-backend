package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 药品类型枚举
 */
@Getter
@AllArgsConstructor
public enum DrugTypeEnum {

    WESTERN(1, "西药"),
    CHINESE_PATENT(2, "中成药"),
    CHINESE_HERBAL(3, "中草药"),
    BIOLOGICAL(4, "生物制品");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型名称
     */
    private final String name;

    public static DrugTypeEnum valueOf(Integer code) {
        for (DrugTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
