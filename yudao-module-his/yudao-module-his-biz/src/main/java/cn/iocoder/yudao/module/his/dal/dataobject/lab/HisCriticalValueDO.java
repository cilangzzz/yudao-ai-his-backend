package cn.iocoder.yudao.module.his.dal.dataobject.lab;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 危急值记录 DO
 *
 * 用于记录检验危急值的检出、通知、确认、处理全过程
 */
@TableName("his_critical_value")
@KeySequence("his_critical_value_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisCriticalValueDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 危急值编号
     */
    private String criticalValueNo;

    /**
     * 检验申请ID
     */
    private Long labRequestId;

    /**
     * 检验申请单号
     */
    private String requestNo;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 就诊ID（挂号ID/住院ID）
     */
    private Long visitId;

    /**
     * 就诊类型: 1门诊/2住院
     */
    private Integer visitType;

    /**
     * 科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 检验项目名称
     */
    private String itemName;

    /**
     * 检验结果
     */
    private String resultValue;

    /**
     * 结果单位
     */
    private String resultUnit;

    /**
     * 危急值下限
     */
    private String lowerLimit;

    /**
     * 危急值上限
     */
    private String upperLimit;

    /**
     * 危急值类型: 高/低
     */
    private String criticalType;

    /**
     * 检出时间
     */
    private LocalDateTime detectedTime;

    /**
     * 检出人ID
     */
    private Long detectorId;

    /**
     * 检出人姓名
     */
    private String detectorName;

    /**
     * 通知时间
     */
    private LocalDateTime notifyTime;

    /**
     * 通知人ID
     */
    private Long notifierId;

    /**
     * 通知人姓名
     */
    private String notifierName;

    /**
     * 接收人ID
     */
    private Long receiverId;

    /**
     * 接收人姓名
     */
    private String receiverName;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;

    /**
     * 确认人ID
     */
    private Long confirmerId;

    /**
     * 确认人姓名
     */
    private String confirmerName;

    /**
     * 处理时间
     */
    private LocalDateTime processTime;

    /**
     * 处理人ID
     */
    private Long processorId;

    /**
     * 处理人姓名
     */
    private String processorName;

    /**
     * 处理结果
     */
    private String processResult;

    /**
     * 状态: 1检出 2已通知 3已确认 4已处理 5超时升级
     *
     * @see cn.iocoder.yudao.module.his.enums.CriticalValueStatusEnum
     */
    private Integer status;

    /**
     * 是否超时
     */
    private Boolean timeoutFlag;

    /**
     * 超时时间(分钟)
     */
    private Integer timeoutMinutes;

    /**
     * 备注
     */
    private String remark;

}