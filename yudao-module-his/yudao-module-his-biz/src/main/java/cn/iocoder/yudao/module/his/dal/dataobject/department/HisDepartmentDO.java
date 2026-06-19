package cn.iocoder.yudao.module.his.dal.dataobject.department;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 科室信息 DO
 *
 * 对应FHIR资源: Organization
 * 科室是医院组织架构的基本单元，用于管理临床科室、医技科室、行政科室等
 *
 * @author yudao
 */
@TableName(value = "his_department", autoResultMap = true)
@KeySequence("his_department_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisDepartmentDO extends TenantBaseDO {

    /**
     * 科室ID
     */
    @TableId
    private Long id;

    /**
     * 科室编码
     */
    private String deptCode;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 科室简称
     */
    private String deptShortName;

    /**
     * 科室类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.DepartmentTypeEnum}
     * 1-临床科室 2-医技科室 3-行政科室 4-后勤科室
     */
    private Integer deptType;

    /**
     * 上级科室ID
     */
    private Long parentId;

    /**
     * 科室层级
     */
    private Integer deptLevel;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 科室电话
     */
    private String phone;

    /**
     * 科室位置
     */
    private String location;

    /**
     * 编制床位数
     */
    private Integer bedCount;

    /**
     * 状态
     *
     * 1-正常 2-停用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}