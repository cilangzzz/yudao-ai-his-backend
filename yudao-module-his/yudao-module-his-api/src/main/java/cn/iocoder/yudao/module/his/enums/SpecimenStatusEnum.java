package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标本状态枚举
 */
@Getter
@AllArgsConstructor
public enum SpecimenStatusEnum {

    PENDING(1, "待采集"),
    COLLECTED(2, "已采集"),
    SENT(3, "已运送"),
    RECEIVED(4, "已接收"),
    REJECTED(5, "已拒收"),
    TESTING(6, "检验中"),
    COMPLETED(7, "已完成"),
    DISCARDED(8, "已销毁");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    public static SpecimenStatusEnum valueOf(Integer code) {
        for (SpecimenStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
