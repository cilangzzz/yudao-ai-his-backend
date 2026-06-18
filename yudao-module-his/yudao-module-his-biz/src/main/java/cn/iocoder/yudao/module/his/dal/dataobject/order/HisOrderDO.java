package cn.iocoder.yudao.module.his.dal.dataobject.order;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 医嘱主表 DO
 *
 * 住院医嘱信息，支持长期医嘱和临时医嘱的管理
 */
@TableName("his_order")
@KeySequence("his_order_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisOrderDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 医嘱编号
     */
    private String orderNo;

    /**
     * 住院ID
     */
    private Long admissionId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 医嘱类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderTypeEnum}
     * 1-药品 2-检验 3-检查 4-护理 5-手术 6-饮食 7-其他
     */
    private Integer orderType;

    /**
     * 医嘱分类
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderCategoryEnum}
     * 1-长期 2-临时
     */
    private Integer orderCategory;

    /**
     * 医嘱内容
     */
    private String orderContent;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 剂量
     */
    private String dosage;

    /**
     * 剂量单位
     */
    private String dosageUnit;

    /**
     * 频次编码
     */
    private String frequencyCode;

    /**
     * 频次名称
     */
    private String frequencyName;

    /**
     * 途径编码
     */
    private String routeCode;

    /**
     * 途径名称
     */
    private String routeName;

    /**
     * 疗程(天)
     */
    private Integer duration;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 停止时间
     */
    private LocalDateTime stopTime;

    /**
     * 开立医生ID
     */
    private Long prescribingDoctor;

    /**
     * 开立医生姓名
     */
    private String prescribingDoctorName;

    /**
     * 审核护士ID
     */
    private Long auditNurse;

    /**
     * 审核护士姓名
     */
    private String auditNurseName;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 执行护士ID
     */
    private Long executeNurse;

    /**
     * 执行护士姓名
     */
    private String executeNurseName;

    /**
     * 执行时间
     */
    private LocalDateTime executeTime;

    /**
     * 医嘱状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderStatusEnum}
     * 1-开立 2-审核 3-执行中 4-已完成 5-已停止 6-已作废
     */
    private Integer orderStatus;

    /**
     * 停止医生ID
     */
    private Long stopDoctor;

    /**
     * 停止医生姓名
     */
    private String stopDoctorName;

    /**
     * 停止原因
     */
    private String stopReason;

    /**
     * 紧急标志
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.OrderUrgencyEnum}
     * 0-常规 1-紧急
     */
    private Integer urgency;

    /**
     * 备注
     */
    private String remarks;

    // ==================== 业务方法 ====================

    /**
     * 是否为长期医嘱
     */
    public boolean isLongTermOrder() {
        return orderCategory != null && orderCategory == 1;
    }

    /**
     * 是否为临时医嘱
     */
    public boolean isTemporaryOrder() {
        return orderCategory != null && orderCategory == 2;
    }

    /**
     * 是否为紧急医嘱
     */
    public boolean isUrgent() {
        return urgency != null && urgency == 1;
    }

    /**
     * 是否可以审核
     */
    public boolean canAudit() {
        // 开立状态才能审核
        return orderStatus != null && orderStatus == 1;
    }

    /**
     * 是否可以执行
     */
    public boolean canExecute() {
        // 审核通过才能执行
        return orderStatus != null && orderStatus == 2;
    }

    /**
     * 是否执行中
     */
    public boolean isExecuting() {
        return orderStatus != null && orderStatus == 3;
    }

    /**
     * 是否已完成
     */
    public boolean isCompleted() {
        return orderStatus != null && orderStatus == 4;
    }

    /**
     * 是否已停止
     */
    public boolean isStopped() {
        return orderStatus != null && orderStatus == 5;
    }

    /**
     * 是否已作废
     */
    public boolean isCancelled() {
        return orderStatus != null && orderStatus == 6;
    }

    /**
     * 是否可以停止
     */
    public boolean canStop() {
        // 执行中才能停止
        return orderStatus != null && orderStatus == 3;
    }

    /**
     * 是否可以作废
     */
    public boolean canCancel() {
        // 开立或审核状态才能作废
        return orderStatus != null && (orderStatus == 1 || orderStatus == 2);
    }

    /**
     * 是否为药品医嘱
     */
    public boolean isDrugOrder() {
        return orderType != null && orderType == 1;
    }

    /**
     * 是否为检验医嘱
     */
    public boolean isLabOrder() {
        return orderType != null && orderType == 2;
    }

    /**
     * 是否为检查医嘱
     */
    public boolean isExamOrder() {
        return orderType != null && orderType == 3;
    }

    /**
     * 是否为护理医嘱
     */
    public boolean isNursingOrder() {
        return orderType != null && orderType == 4;
    }

    /**
     * 是否为手术医嘱
     */
    public boolean isSurgeryOrder() {
        return orderType != null && orderType == 5;
    }

    /**
     * 是否为饮食医嘱
     */
    public boolean isDietOrder() {
        return orderType != null && orderType == 6;
    }

    /**
     * 是否已审核
     */
    public boolean isAudited() {
        return orderStatus != null && orderStatus >= 2;
    }

    /**
     * 是否已执行
     */
    public boolean isExecuted() {
        return orderStatus != null && orderStatus >= 3;
    }

    /**
     * 是否有效医嘱
     */
    public boolean isValidOrder() {
        // 非停止、非作废状态
        return orderStatus != null && orderStatus >= 1 && orderStatus <= 4;
    }

    /**
     * 获取医嘱状态名称
     */
    public String getStatusName() {
        if (orderStatus == null) {
            return "未知";
        }
        switch (orderStatus) {
            case 1:
                return "开立";
            case 2:
                return "审核";
            case 3:
                return "执行中";
            case 4:
                return "已完成";
            case 5:
                return "已停止";
            case 6:
                return "已作废";
            default:
                return "未知";
        }
    }

    /**
     * 获取医嘱类型名称
     */
    public String getTypeName() {
        if (orderType == null) {
            return "未知";
        }
        switch (orderType) {
            case 1:
                return "药品";
            case 2:
                return "检验";
            case 3:
                return "检查";
            case 4:
                return "护理";
            case 5:
                return "手术";
            case 6:
                return "饮食";
            case 7:
                return "其他";
            default:
                return "未知";
        }
    }

    /**
     * 获取医嘱分类名称
     */
    public String getCategoryName() {
        if (orderCategory == null) {
            return "未知";
        }
        return orderCategory == 1 ? "长期" : "临时";
    }

}
