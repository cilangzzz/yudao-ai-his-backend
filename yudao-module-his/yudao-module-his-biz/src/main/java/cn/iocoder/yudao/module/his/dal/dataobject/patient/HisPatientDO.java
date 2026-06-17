package cn.iocoder.yudao.module.his.dal.dataobject.patient;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * 患者主索引 DO
 *
 * 对应FHIR资源: Patient
 * 患者基本信息，全院唯一标识患者身份（EMPI）
 *
 * @author yudao
 */
@TableName(value = "his_patient", autoResultMap = true)
@KeySequence("his_patient_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisPatientDO extends TenantBaseDO {

    /**
     * 患者ID
     */
    @TableId
    private Long id;

    /**
     * 患者编号（院内唯一）
     */
    private String patientNo;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.PatientGenderEnum}
     * 1-男 2-女 9-未知
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 年龄（根据出生日期计算）
     */
    private Integer age;

    /**
     * 年龄单位
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.AgeUnitEnum}
     * 1-岁 2-月 3-天
     */
    private Integer ageUnit;

    /**
     * 民族
     */
    private String nation;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 婚姻状况
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.MaritalStatusEnum}
     * 1-未婚 2-已婚 3-离异 4-丧偶
     */
    private Integer maritalStatus;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人关系
     */
    private String contactRelation;

    /**
     * 血型
     *
     * 1-A 2-B 3-AB 4-O 5-不详
     */
    private Integer bloodType;

    /**
     * RH血型
     *
     * 1-阳性 2-阴性
     */
    private Integer rhBlood;

    /**
     * 医保类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.InsuranceTypeEnum}
     * 1-城镇职工 2-城镇居民 3-新农合 4-自费
     */
    private Integer insuranceType;

    /**
     * 医保卡号
     */
    private String insuranceNo;

    /**
     * 过敏史
     */
    private String allergyHistory;

    /**
     * 既往病史
     */
    private String medicalHistory;

    /**
     * 家族史
     */
    private String familyHistory;

    /**
     * 照片URL
     */
    private String photoUrl;

    /**
     * 状态
     *
     * 1-正常 2-注销 3-死亡
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
