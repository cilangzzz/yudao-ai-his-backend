package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检查报告状态枚举
 */
@Getter
@AllArgsConstructor
public enum ExamReportStatusEnum {

    NOT_REPORTED(0, "未报告"),
    PRELIMINARY(1, "初步报告"),
    FINAL(2, "最终报告"),
    AUDITED(3, "已审核");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    public static ExamReportStatusEnum valueOf(Integer code) {
        for (ExamReportStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
