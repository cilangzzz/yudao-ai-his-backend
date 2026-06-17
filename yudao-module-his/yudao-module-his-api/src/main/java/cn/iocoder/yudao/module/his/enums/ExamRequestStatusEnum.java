package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检查申请状态枚举
 */
@Getter
@AllArgsConstructor
public enum ExamRequestStatusEnum {

    REQUESTED(1, "已申请"),
    APPOINTED(2, "已预约"),
    CHECKED_IN(3, "已签到"),
    IN_PROGRESS(4, "检查中"),
    COMPLETED(5, "已完成"),
    REPORTED(6, "已报告"),
    AUDITED(7, "已审核"),
    CANCELLED(8, "已取消"),
    REFUNDED(9, "已退费");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    public static ExamRequestStatusEnum valueOf(Integer code) {
        for (ExamRequestStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}