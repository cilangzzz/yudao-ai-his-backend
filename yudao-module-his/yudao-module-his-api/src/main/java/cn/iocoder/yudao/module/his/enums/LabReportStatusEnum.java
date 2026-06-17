package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检验报告状态枚举
 */
@Getter
@AllArgsConstructor
public enum LabReportStatusEnum {

    DRAFT(0, "草稿"),
    PRELIMINARY(1, "初步报告"),
    FINAL(2, "最终报告"),
    AUDITED(3, "已审核"),
    PUBLISHED(4, "已发布"),
    WITHDRAWN(5, "已撤回");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    public static LabReportStatusEnum valueOf(Integer code) {
        for (LabReportStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
