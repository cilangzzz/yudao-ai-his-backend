package cn.iocoder.yudao.module.his.dal.dataobject.register;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 挂号记录 DO
 *
 * 患者挂号信息，包含挂号、就诊、退号等流程
 */
@TableName("op_register")
@KeySequence("op_register_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpRegisterDO extends TenantBaseDO {

    /**
     * 挂号ID
     */
    @TableId
    private Long id;

    /**
     * 挂号编号
     */
    private String registerNo;

    /**
     * 预约ID（预约转挂号时关联）
     */
    private Long appointmentId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者电话
     */
    private String patientPhone;

    /**
     * 身份证号
     */
    private String idCardNo;

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
     * 挂号日期
     */
    private LocalDate registerDate;

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
     * 来源：1-现场 2-微信 3-APP 4-网站 5-自助机
     */
    private Integer sourceType;

    /**
     * 挂号费
     */
    private BigDecimal registerFee;

    /**
     * 诊查费
     */
    private BigDecimal consultationFee;

    /**
     * 总费用
     */
    private BigDecimal totalFee;

    /**
     * 支付状态：0-未支付 1-已支付 2-已退费
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付方式：1-现金 2-微信 3-支付宝 4-医保
     */
    private Integer payType;

    /**
     * 挂号状态：1-候诊 2-就诊中 3-已完成 4-已退号
     */
    private Integer registerStatus;

    /**
     * 就诊时间
     */
    private LocalDateTime visitTime;

    /**
     * 就诊结束时间
     */
    private LocalDateTime endTime;

    /**
     * 退费金额
     */
    private BigDecimal refundAmount;

    /**
     * 退号时间
     */
    private LocalDateTime refundTime;

    /**
     * 退号操作人
     */
    private Long refundBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否已支付
     */
    public boolean isPaid() {
        return payStatus != null && payStatus == 1;
    }

    /**
     * 是否已退号
     */
    public boolean isRefunded() {
        return registerStatus != null && registerStatus == 4;
    }

    /**
     * 是否可以退号
     */
    public boolean canRefund() {
        // 未就诊且未支付才能退号
        return registerStatus == 1 && payStatus == 0;
    }

}