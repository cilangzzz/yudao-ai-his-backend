package cn.iocoder.yudao.module.his.dal.dataobject.schedule;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 医生排班 DO
 *
 * 医生门诊排班信息，用于挂号号源管理
 */
@TableName("op_schedule")
@KeySequence("op_schedule_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpScheduleDO extends TenantBaseDO {

    /**
     * 排班ID
     */
    @TableId
    private Long id;

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
     * 排班日期
     */
    private LocalDate scheduleDate;

    /**
     * 时段：1-上午 2-下午 3-晚上
     */
    private Integer timePeriod;

    /**
     * 挂号类型：1-普通 2-专家 3-急诊
     */
    private Integer registerType;

    /**
     * 总号源数
     */
    private Integer totalQuota;

    /**
     * 已用号源数
     */
    private Integer usedQuota;

    /**
     * 状态：1-正常 2-停诊 3-已满
     */
    private Integer status;

    /**
     * 诊查费
     */
    private BigDecimal consultationFee;

    /**
     * 挂号费
     */
    private BigDecimal registerFee;

    /**
     * 备注
     */
    private String remark;

    /**
     * 获取剩余号源数
     */
    public Integer getRemainingQuota() {
        if (totalQuota == null || usedQuota == null) {
            return 0;
        }
        return Math.max(0, totalQuota - usedQuota);
    }

    /**
     * 是否有剩余号源
     */
    public boolean hasRemainingQuota() {
        return getRemainingQuota() > 0;
    }

}
