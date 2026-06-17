package cn.iocoder.yudao.module.his.dal.dataobject.diagnosis;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 诊断记录 DO
 *
 * 对应FHIR资源: Condition
 * 患者诊断信息，支持门诊诊断、入院诊断、出院诊断等多种类型
 *
 * @author yudao
 */
@TableName("his_diagnosis")
@KeySequence("his_diagnosis_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDiagnosisDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 就诊ID
     *
     * 关联挂号记录（门诊）或入院记录（住院）
     */
    private Long encounterId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 诊断类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DiagnosisTypeEnum}
     * 1-门诊诊断 2-入院诊断 3-出院诊断 4-修正诊断 5-补充诊断
     */
    private Integer diagnosisType;

    /**
     * ICD-10诊断编码
     */
    private String diagnosisCode;

    /**
     * 诊断名称
     */
    private String diagnosisName;

    /**
     * 诊断序号
     *
     * 1=主诊断，2=第一副诊断，3=第二副诊断...
     */
    private Integer diagnosisSeq;

    /**
     * 诊断状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DiagnosisStatusEnum}
     * 1-初步诊断 2-确定诊断 3-修正诊断
     */
    private Integer diagnosisStatus;

    /**
     * 诊断医生ID
     */
    private Long diagnoseDoctor;

    /**
     * 诊断时间
     */
    private LocalDateTime diagnoseTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否为主诊断
     */
    public boolean isPrimaryDiagnosis() {
        return diagnosisSeq != null && diagnosisSeq == 1;
    }

    /**
     * 是否为确定诊断
     */
    public boolean isConfirmed() {
        return diagnosisStatus != null && diagnosisStatus == 2;
    }

}
