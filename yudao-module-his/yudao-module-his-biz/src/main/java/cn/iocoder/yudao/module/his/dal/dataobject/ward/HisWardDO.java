package cn.iocoder.yudao.module.his.dal.dataobject.ward;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 病区 DO
 *
 * @author yudao
 */
@TableName(value = "his_ward", autoResultMap = true)
@KeySequence("his_ward_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisWardDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 病区编码
     */
    private String wardCode;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 所属科室ID
     */
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 床位总数
     */
    private Integer bedCount;

    /**
     * 已用床位数
     */
    private Integer usedBedCount;

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

    /**
     * 获取空床位数
     */
    public Integer getEmptyBedCount() {
        if (bedCount == null) {
            return 0;
        }
        if (usedBedCount == null) {
            return bedCount;
        }
        return bedCount - usedBedCount;
    }

    /**
     * 获取床位使用率
     */
    public Double getUsageRate() {
        if (bedCount == null || bedCount == 0) {
            return 0.0;
        }
        if (usedBedCount == null) {
            return 0.0;
        }
        return (double) usedBedCount / bedCount * 100;
    }

    /**
     * 是否有空床
     */
    public boolean hasEmptyBed() {
        return getEmptyBedCount() > 0;
    }
}
