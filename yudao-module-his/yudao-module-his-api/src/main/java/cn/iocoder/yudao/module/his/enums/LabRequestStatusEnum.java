package cn.iocoder.yudao.module.his.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检验申请状态枚举
 *
 * 检验申请流程：已申请 -> 已采集 -> 已接收 -> 检验中 -> 已完成 -> 已报告 -> 已审核
 */
@Getter
@AllArgsConstructor
public enum LabRequestStatusEnum {

    REQUESTED(1, "已申请"),
    COLLECTED(2, "已采集"),
    RECEIVED(3, "已接收"),
    IN_PROGRESS(4, "检验中"),
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

    public static LabRequestStatusEnum valueOf(Integer code) {
        for (LabRequestStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
