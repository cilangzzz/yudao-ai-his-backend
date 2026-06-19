package cn.iocoder.yudao.module.his.dal.dataobject.staff;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;

/**
 * 医护人员 DO
 *
 * 对应FHIR资源: Practitioner
 * 医院内部的医生、护士、技师、药师等工作人员信息
 *
 * @author yudao
 */
@TableName(value = "his_staff", autoResultMap = true)
@KeySequence("his_staff_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisStaffDO extends TenantBaseDO {

    /**
     * 人员ID
     */
    @TableId
    private Long id;

    /**
     * 人员编码
     */
    private String staffCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     *
     * 1-男 2-女
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthDate;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 所属科室ID
     */
    private Long deptId;

    /**
     * 职务
     */
    private String position;

    /**
     * 职称
     */
    private String title;

    /**
     * 专业特长
     */
    private String specialty;

    /**
     * 是否医生
     *
     * 0-否 1-是
     */
    private Integer doctorFlag;

    /**
     * 是否护士
     *
     * 0-否 1-是
     */
    private Integer nurseFlag;

    /**
     * 是否药师
     *
     * 0-否 1-是
     */
    private Integer pharmacistFlag;

    /**
     * 是否检验技师
     *
     * 0-否 1-是
     */
    private Integer labTechFlag;

    /**
     * 是否放射技师
     *
     * 0-否 1-是
     */
    private Integer radTechFlag;

    /**
     * 状态
     *
     * 1-在职 2-离职 3-退休
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}