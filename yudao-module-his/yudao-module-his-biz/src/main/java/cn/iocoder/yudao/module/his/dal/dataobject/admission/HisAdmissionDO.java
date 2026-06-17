package cn.iocoder.yudao.module.his.dal.dataobject.admission;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 入院记录 DO
 *
 * 对应FHIR资源: Encounter (住院场景)
 * 患者住院信息，包含入院、在院、出院等流程
 *
 * @author yudao
 */
@TableName("his_admission")
@KeySequence("his_admission_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisAdmissionDO extends TenantBaseDO {

    /**
     * 入院记录ID
     */
    @TableId
    private Long id;

    /**
     * 住院号（院内唯一）
     */
    private String admissionNo;

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
     * 关联门诊挂号ID（门诊转住院时关联）
     */
    private Long registerId;

    /**
     * 入院日期
     */
    private LocalDateTime admissionDate;

    /**
     * 入院科室ID
     */
    private Long admissionDept;

    /**
     * 科室名称
     */
    private String admissionDeptName;

    /**
     * 病区ID
     */
    private Long wardId;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 床位ID
     */
    private Long bedId;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 入院诊断
     */
    private String admissionDiagnosis;

    /**
     * 入院诊断ICD-10编码
     */
    private String diagnosisCode;

    /**
     * 主治医师ID
     */
    private Long attendingDoctor;

    /**
     * 主治医师姓名
     */
    private String attendingDoctorName;

    /**
     * 住院医师ID
     */
    private Long residentDoctor;

    /**
     * 住院医师姓名
     */
    private String residentDoctorName;

    /**
     * 责任护士ID
     */
    private Long nurseId;

    /**
     * 责任护士姓名
     */
    private String nurseName;

    /**
     * 入院方式：1-门诊 2-急诊 3-转院
     */
    private Integer admissionWay;

    /**
     * 入院情况：1-危 2-急 3-一般
     */
    private Integer admissionCondition;

    /**
     * 医保类型
     */
    private Integer insuranceType;

    /**
     * 医保号
     */
    private String insuranceNo;

    /**
     * 预交金总额
     */
    private BigDecimal depositAmount;

    /**
     * 总费用
     */
    private BigDecimal totalFee;

    /**
     * 出院日期
     */
    private LocalDateTime dischargeDate;

    /**
     * 出院科室ID
     */
    private Long dischargeDept;

    /**
     * 出院诊断
     */
    private String dischargeDiagnosis;

    /**
     * 出院诊断ICD-10编码
     */
    private String dischargeCode;

    /**
     * 出院方式：1-医嘱出院 2-自动出院 3-转院 4-死亡
     */
    private Integer dischargeWay;

    /**
     * 入院状态：1-在院 2-出院 3-转科
     */
    private Integer admissionStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否在院
     */
    public boolean isInpatient() {
        return admissionStatus != null && admissionStatus == 1;
    }

    /**
     * 是否已出院
     */
    public boolean isDischarged() {
        return admissionStatus != null && admissionStatus == 2;
    }

    /**
     * 是否可以出院
     */
    public boolean canDischarge() {
        // 在院状态才能办理出院
        return admissionStatus != null && admissionStatus == 1;
    }

}
