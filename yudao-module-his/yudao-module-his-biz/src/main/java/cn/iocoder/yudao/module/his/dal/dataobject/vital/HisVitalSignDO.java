package cn.iocoder.yudao.module.his.dal.dataobject.vital;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 生命体征 DO
 *
 * 记录患者体温、脉搏、呼吸、血压、血氧等生命体征数据
 * 对应FHIR资源: Observation
 */
@TableName("his_vital_sign")
@KeySequence("his_vital_sign_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisVitalSignDO extends TenantBaseDO {

    /**
     * 生命体征ID
     */
    @TableId
    private Long id;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 住院ID
     */
    private Long admissionId;

    // ==================== 生命体征数据 ====================

    /**
     * 体温(°C)
     */
    private BigDecimal temperature;

    /**
     * 脉搏(次/分)
     */
    private Integer pulse;

    /**
     * 呼吸(次/分)
     */
    private Integer respiration;

    /**
     * 收缩压(mmHg)
     */
    private Integer systolicBp;

    /**
     * 舒张压(mmHg)
     */
    private Integer diastolicBp;

    /**
     * 血氧饱和度(%)
     */
    private BigDecimal oxygenSaturation;

    /**
     * 体重(kg)
     */
    private BigDecimal weight;

    /**
     * 身高(cm)
     */
    private BigDecimal height;

    /**
     * BMI指数
     */
    private BigDecimal bmi;

    /**
     * 疼痛评分(0-10)
     */
    private Integer painScore;

    /**
     * 意识状态
     *
     * 清醒/嗜睡/昏迷
     */
    private String consciousness;

    // ==================== 测量信息 ====================

    /**
     * 测量时间
     */
    private LocalDateTime measureTime;

    /**
     * 测量护士ID
     */
    private Long nurseId;

    /**
     * 测量护士姓名
     */
    private String nurseName;

    // ==================== 异常标识 ====================

    /**
     * 异常标识
     *
     * 0-正常 1-异常
     */
    private Integer abnormalFlag;

    /**
     * 异常项目
     */
    private String abnormalItems;

    /**
     * 备注
     */
    private String remark;

    // ==================== 业务方法 ====================

    /**
     * 是否体温异常
     */
    public boolean isTemperatureAbnormal() {
        if (temperature == null) {
            return false;
        }
        // 正常体温范围: 36.0-37.5°C
        return temperature.compareTo(new BigDecimal("36.0")) < 0
            || temperature.compareTo(new BigDecimal("37.5")) > 0;
    }

    /**
     * 是否血压异常
     */
    public boolean isBloodPressureAbnormal() {
        if (systolicBp == null || diastolicBp == null) {
            return false;
        }
        // 正常血压范围: 收缩压90-140, 舒张压60-90
        return systolicBp < 90 || systolicBp > 140
            || diastolicBp < 60 || diastolicBp > 90;
    }

    /**
     * 是否脉搏异常
     */
    public boolean isPulseAbnormal() {
        if (pulse == null) {
            return false;
        }
        // 正常脉搏范围: 60-100次/分
        return pulse < 60 || pulse > 100;
    }

    /**
     * 是否呼吸异常
     */
    public boolean isRespirationAbnormal() {
        if (respiration == null) {
            return false;
        }
        // 正常呼吸范围: 16-20次/分
        return respiration < 16 || respiration > 20;
    }

    /**
     * 是否血氧异常
     */
    public boolean isOxygenSaturationAbnormal() {
        if (oxygenSaturation == null) {
            return false;
        }
        // 正常血氧饱和度: >= 95%
        return oxygenSaturation.compareTo(new BigDecimal("95")) < 0;
    }

    /**
     * 是否有异常
     */
    public boolean hasAbnormal() {
        return isTemperatureAbnormal()
            || isBloodPressureAbnormal()
            || isPulseAbnormal()
            || isRespirationAbnormal()
            || isOxygenSaturationAbnormal();
    }

    /**
     * 计算BMI
     */
    public BigDecimal calculateBmi() {
        if (weight == null || height == null
            || weight.compareTo(BigDecimal.ZERO) <= 0
            || height.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }
        // BMI = 体重(kg) / 身高(m)^2
        BigDecimal heightInMeter = height.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP);
        return weight.divide(heightInMeter.multiply(heightInMeter), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取血压字符串
     */
    public String getBloodPressureStr() {
        if (systolicBp == null || diastolicBp == null) {
            return null;
        }
        return systolicBp + "/" + diastolicBp;
    }
}