package cn.iocoder.yudao.module.his.dal.dataobject.bed;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;

/**
 * 床位 DO
 *
 * @author yudao
 */
@TableName(value = "his_bed", autoResultMap = true)
@KeySequence("his_bed_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisBedDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 床号
     */
    private String bedNo;

    /**
     * 病区ID
     */
    private Long wardId;

    /**
     * 病区名称
     */
    private String wardName;

    /**
     * 房间号
     */
    private String roomNo;

    /**
     * 床位类型
     *
     * 1-普通 2-抢救 3-ICU 4-隔离
     * @see cn.iocoder.yudao.module.his.enums.BedTypeEnum
     */
    private Integer bedType;

    /**
     * 床位等级
     *
     * 1-普通 2-双人 3-单人 4-VIP
     * @see cn.iocoder.yudao.module.his.enums.BedClassEnum
     */
    private Integer bedClass;

    /**
     * 床位日单价
     */
    private BigDecimal dailyPrice;

    /**
     * 床位状态
     *
     * 1-空床 2-占用 3-预约 4-清洁 5-维修
     * @see cn.iocoder.yudao.module.his.enums.BedStatusEnum
     */
    private Integer bedStatus;

    /**
     * 当前患者ID
     */
    private Long currentPatientId;

    /**
     * 当前患者姓名
     */
    private String currentPatientName;

    /**
     * 当前住院ID
     */
    private Long admissionId;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

    /**
     * 判断床位是否可用
     */
    public boolean isAvailable() {
        return bedStatus != null && bedStatus == 1;
    }

    /**
     * 判断床位是否被占用
     */
    public boolean isOccupied() {
        return bedStatus != null && bedStatus == 2;
    }
}
