package cn.iocoder.yudao.module.his.dal.dataobject.nursing;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 护理交接班 DO
 *
 * 记录护理人员的交接班信息，包括交班人、接班人、交接内容等
 */
@TableName("his_nursing_handover")
@KeySequence("his_nursing_handover_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisNursingHandoverDO extends TenantBaseDO {

    /**
     * 交接班ID
     */
    @TableId
    private Long id;

    /**
     * 交接班编号
     */
    private String handoverNo;

    /**
     * 病区ID
     */
    private Long wardId;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 交班护士ID
     */
    private Long handoverNurseId;

    /**
     * 交班护士姓名
     */
    private String handoverNurseName;

    /**
     * 接班护士ID
     */
    private Long takeoverNurseId;

    /**
     * 接班护士姓名
     */
    private String takeoverNurseName;

    /**
     * 交班时间
     */
    private LocalDateTime handoverTime;

    /**
     * 接班时间
     */
    private LocalDateTime takeoverTime;

    /**
     * 班次类型
     *
     * 1-白班 2-小夜班 3-大夜班
     */
    private Integer shiftType;

    /**
     * 交接班状态
     *
     * 0-待接班 1-已接班 2-已作废
     */
    private Integer status;

    // ==================== 患者概况 ====================

    /**
     * 原有患者数
     */
    private Integer originalPatientCount;

    /**
     * 新入院患者数
     */
    private Integer newAdmissionCount;

    /**
     * 出院患者数
     */
    private Integer dischargeCount;

    /**
     * 转入患者数
     */
    private Integer transferInCount;

    /**
     * 转出患者数
     */
    private Integer transferOutCount;

    /**
     * 现有患者数
     */
    private Integer currentPatientCount;

    /**
     * 特护患者数
     */
    private Integer specialCareCount;

    /**
     * 一级护理患者数
     */
    private Integer primaryCareCount;

    /**
     * 二级护理患者数
     */
    private Integer secondaryCareCount;

    /**
     * 三级护理患者数
     */
    private Integer tertiaryCareCount;

    // ==================== 交接内容 ====================

    /**
     * 重点患者情况
     *
     * 记录危重、手术、特殊治疗等患者情况
     */
    private String keyPatientSituation;

    /**
     * 待办事项
     *
     * 需要接班护士继续跟进的工作
     */
    private String todoItems;

    /**
     * 特殊情况记录
     *
     * 突发事件、异常情况等
     */
    private String specialSituation;

    /**
     * 物品交接情况
     *
     * 药品、器械、物品等的交接情况
     */
    private String goodsHandover;

    /**
     * 备注
     */
    private String remark;

    // ==================== 签名信息 ====================

    /**
     * 交班签名状态
     *
     * 0-未签名 1-已签名
     */
    private Integer handoverSignatureStatus;

    /**
     * 交班签名时间
     */
    private LocalDateTime handoverSignatureTime;

    /**
     * 交班电子签名
     */
    private String handoverSignature;

    /**
     * 接班签名状态
     *
     * 0-未签名 1-已签名
     */
    private Integer takeoverSignatureStatus;

    /**
     * 接班签名时间
     */
    private LocalDateTime takeoverSignatureTime;

    /**
     * 接班电子签名
     */
    private String takeoverSignature;

    // ==================== 业务方法 ====================

    /**
     * 是否已交班签名
     */
    public boolean isHandoverSigned() {
        return handoverSignatureStatus != null && handoverSignatureStatus == 1;
    }

    /**
     * 是否已接班签名
     */
    public boolean isTakeoverSigned() {
        return takeoverSignatureStatus != null && takeoverSignatureStatus == 1;
    }

    /**
     * 是否已完成交接
     */
    public boolean isCompleted() {
        return status != null && status == 1;
    }

    /**
     * 是否可以接班
     */
    public boolean canTakeover() {
        return status != null && status == 0 && isHandoverSigned();
    }

    /**
     * 是否可以作废
     */
    public boolean canCancel() {
        return status != null && status == 0 && !isTakeoverSigned();
    }

    /**
     * 获取班次类型名称
     */
    public String getShiftTypeName() {
        if (shiftType == null) {
            return "未知";
        }
        return switch (shiftType) {
            case 1 -> "白班";
            case 2 -> "小夜班";
            case 3 -> "大夜班";
            default -> "未知";
        };
    }

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (status == null) {
            return "未知";
        }
        return switch (status) {
            case 0 -> "待接班";
            case 1 -> "已接班";
            case 2 -> "已作废";
            default -> "未知";
        };
    }
}
