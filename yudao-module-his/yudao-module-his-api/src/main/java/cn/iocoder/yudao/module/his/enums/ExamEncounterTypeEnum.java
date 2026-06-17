package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 就诊类型枚举(检查申请用)
 */
@Getter
@AllArgsConstructor
public enum ExamEncounterTypeEnum {

    OUTPATIENT(1, "门诊"),
    INPATIENT(2, "住院"),
    EMERGENCY(3, "急诊");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型名称
     */
    private final String name;

    public static ExamEncounterTypeEnum valueOf(Integer code) {
        for (ExamEncounterTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

}
