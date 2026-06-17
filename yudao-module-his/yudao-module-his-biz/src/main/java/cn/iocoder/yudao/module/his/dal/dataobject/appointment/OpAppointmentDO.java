package cn.iocoder.yudao.module.his.dal.dataobject.appointment;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 预约挂号 DO
 *
 * 患者预约挂号信息，包含预约、取号、取消等流程
 */
@TableName("op_appointment")
@KeySequence("op_appointment_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpAppointmentDO extends TenantBaseDO {

    /**
     * 预约ID
     */
    @TableId
    private Long id;

    /**
     * 预约编号
     */
    private String appointmentNo;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 排班ID
     */
    private Long scheduleId;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 预约日期
     */
    private LocalDate appointmentDate;

    /**
     * 时段：1-上午 2-下午 3-晚上
     */
    private Integer timePeriod;

    /**
     * 排队序号
     */
    private Integer queueNo;

    /**
     * 挂号类型：1-普通 2-专家 3-急诊
     */
    private Integer registerType;

    /**
     * 来源：1-现场 2-微信 3-APP 4-网站 5-电话
     */
    private Integer sourceType;

    /**
     * 预约状态：1-已预约 2-已取号 3-已就诊 4-已取消 5-已过期
     */
    private Integer appointmentStatus;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 取消人
     */
    private Long cancelBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否已预约
     */
    public boolean isAppointmented() {
        return appointmentStatus != null && appointmentStatus == 1;
    }

    /**
     * 是否已取号
     */
    public boolean isTaken() {
        return appointmentStatus != null && appointmentStatus == 2;
    }

    /**
     * 是否已取消
     */
    public boolean isCancelled() {
        return appointmentStatus != null && appointmentStatus == 4;
    }

    /**
     * 是否已过期
     */
    public boolean isExpired() {
        return appointmentStatus != null && appointmentStatus == 5;
    }

    /**
     * 是否可以取消
     * 已预约状态才能取消
     */
    public boolean canCancel() {
        return appointmentStatus != null && appointmentStatus == 1;
    }

    /**
     * 是否可以取号
     * 已预约状态才能取号
     */
    public boolean canTake() {
        return appointmentStatus != null && appointmentStatus == 1;
    }

}
