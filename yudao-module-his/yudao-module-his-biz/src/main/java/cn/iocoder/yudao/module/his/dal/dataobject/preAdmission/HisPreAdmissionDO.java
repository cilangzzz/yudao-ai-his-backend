package cn.iocoder.yudao.module.his.dal.dataobject.preAdmission;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预住院记录 DO
 *
 * 预住院是指患者已预约住院但尚未正式入院的状态
 *
 * @author yudao
 */
@TableName("his_pre_admission")
@KeySequence("his_pre_admission_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisPreAdmissionDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 预住院编号
     */
    private String preAdmissionNo;

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
     * 关联门诊挂号ID
     */
    private Long registerId;

    /**
     * 预约科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 预约入院日期
     */
    private LocalDateTime scheduledDate;

    /**
     * 预约诊断
     */
    private String diagnosis;

    /**
     * 诊断编码
     */
    private String diagnosisCode;

    /**
     * 主治医师ID
     */
    private Long doctorId;

    /**
     * 主治医师姓名
     */
    private String doctorName;

    /**
     * 预交金
     */
    private BigDecimal depositAmount;

    /**
     * 预住院状态
     *
     * 1-待入院 2-已入院 3-已取消
     */
    private Integer status;

    /**
     * 入院时间（正式入院后）
     */
    private LocalDateTime admissionTime;

    /**
     * 入院记录ID（正式入院后）
     */
    private Long admissionId;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否待入院
     */
    public boolean isPending() {
        return status != null && status == 1;
    }

    /**
     * 是否已入院
     */
    public boolean isAdmitted() {
        return status != null && status == 2;
    }

    /**
     * 是否已取消
     */
    public boolean isCancelled() {
        return status != null && status == 3;
    }
}