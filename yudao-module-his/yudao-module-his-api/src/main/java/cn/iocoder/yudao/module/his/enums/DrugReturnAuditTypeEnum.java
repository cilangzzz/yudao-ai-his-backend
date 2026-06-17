package cn.iocoder.yudao.module.his.enums;

import cn.iocoder.yudao.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 退药审核类型枚举
 */
@Getter
@AllArgsConstructor
public enum DrugReturnAuditTypeEnum implements ArrayValuable<Integer> {

    PHARMACIST(1, "药师审核"),
    DEPARTMENT(2, "科室审核"),
    FINANCE(3, "财务审核");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DrugReturnAuditTypeEnum::getValue).toArray();

    private final Integer value;
    private final String name;

    @Override
    public Integer[] array() {
        return Arrays.stream(values()).map(DrugReturnAuditTypeEnum::getValue).toArray(Integer[]::new);
    }

    public static DrugReturnAuditTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static String getName(Integer value) {
        DrugReturnAuditTypeEnum enumObj = valueOf(value);
        return enumObj != null ? enumObj.getName() : "";
    }

}