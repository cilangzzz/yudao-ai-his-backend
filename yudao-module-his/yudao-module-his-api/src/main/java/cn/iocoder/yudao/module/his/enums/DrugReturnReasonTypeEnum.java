package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退药原因类型枚举
 */
@Getter
@AllArgsConstructor
public enum DrugReturnReasonTypeEnum implements ArrayValuable<Integer> {

    ADVERSE_REACTION(1, "药品不良反应"),
    ORDER_CHANGE(2, "医嘱变更"),
    PATIENT_REFUSAL(3, "患者拒服"),
    QUALITY_ISSUE(4, "药品质量问题"),
    DUPLICATE_PRESCRIPTION(5, "重复开药"),
    OTHER(6, "其他");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DrugReturnReasonTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(DrugReturnReasonTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static DrugReturnReasonTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static String getName(Integer value) {
        DrugReturnReasonTypeEnum enumObj = valueOf(value);
        return enumObj != null ? enumObj.getName() : "";
    }

}