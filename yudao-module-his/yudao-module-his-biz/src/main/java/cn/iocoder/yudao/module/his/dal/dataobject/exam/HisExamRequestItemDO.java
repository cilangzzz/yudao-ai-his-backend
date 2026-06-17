package cn.iocoder.yudao.module.his.dal.dataobject.exam;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检查申请明细 DO
 *
 * 检查申请的子项目，支持一个申请包含多个检查项目
 *
 * @author yudao
 */
@TableName(value = "his_exam_request_item", autoResultMap = true)
@KeySequence("his_exam_request_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisExamRequestItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 申请ID
     */
    private Long requestId;

    /**
     * 检查项目编码
     */
    private String itemCode;

    /**
     * 检查项目名称
     */
    private String itemName;

    /**
     * 项目类别
     */
    private String itemCategory;

    /**
     * 检查方法
     */
    private String examMethod;

    /**
     * 检查部位
     */
    private String examPart;

    /**
     * 规格/条件
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 医保编码
     */
    private String insuranceCode;

    /**
     * 医保类别
     *
     * 1-甲类 2-乙类 3-丙类
     */
    private Integer insuranceCategory;

    /**
     * 执行科室ID
     */
    private Long executionDeptId;

    /**
     * 执行科室名称
     */
    private String executionDeptName;

    /**
     * 状态
     *
     * 1-待检查 2-检查中 3-已完成 4-已取消
     */
    private Integer itemStatus;

    /**
     * 检查开始时间
     */
    private LocalDateTime startTime;

    /**
     * 检查结束时间
     */
    private LocalDateTime endTime;

    /**
     * 检查技师
     */
    private Long technicianId;

    /**
     * 检查技师姓名
     */
    private String technicianName;

    /**
     * 检查结果(检验)
     */
    private String resultValue;

    /**
     * 结果单位
     */
    private String resultUnit;

    /**
     * 参考范围
     */
    private String referenceRange;

    /**
     * 异常标志
     *
     * 0-正常 1-偏高 2-偏低 3-阳性 4-阴性
     */
    private Integer abnormalFlag;

    /**
     * 结果备注
     */
    private String resultRemark;

    /**
     * 影像URL
     */
    private String imageUrl;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

}
