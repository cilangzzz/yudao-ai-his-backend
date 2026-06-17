package cn.iocoder.yudao.module.his.dal.dataobject.exam;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检查报告明细 DO
 *
 * 检验报告的具体项目结果
 *
 * @author yudao
 */
@TableName(value = "his_exam_report_item", autoResultMap = true)
@KeySequence("his_exam_report_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisExamReportItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 报告ID
     */
    private Long reportId;

    /**
     * 申请明细ID
     */
    private Long requestItemId;

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
     * 检验方法
     */
    private String testMethod;

    /**
     * 结果值
     */
    private String resultValue;

    /**
     * 结果单位
     */
    private String resultUnit;

    /**
     * 参考范围下限
     */
    private BigDecimal referenceRangeLow;

    /**
     * 参考范围上限
     */
    private BigDecimal referenceRangeHigh;

    /**
     * 参考范围文本
     */
    private String referenceRangeText;

    /**
     * 异常标志
     *
     * 0-正常 1-偏高 2-偏低 3-阳性 4-阴性 5-弱阳性
     */
    private Integer abnormalFlag;

    /**
     * 危急值标志
     *
     * 0-无 1-有
     */
    private Integer criticalFlag;

    /**
     * 危急值下限
     */
    private BigDecimal criticalLow;

    /**
     * 危急值上限
     */
    private BigDecimal criticalHigh;

    /**
     * 结果状态
     *
     * 1-正常 2-异常 3-危急
     */
    private Integer resultStatus;

    /**
     * 结果备注
     */
    private String resultRemark;

    /**
     * 标本编号
     */
    private String specimenId;

    /**
     * 标本状态
     */
    private Integer specimenStatus;

    /**
     * 仪器设备ID
     */
    private Long instrumentId;

    /**
     * 仪器设备名称
     */
    private String instrumentName;

    /**
     * 试剂批号
     */
    private String reagentBatch;

    /**
     * 检验时间
     */
    private LocalDateTime testTime;

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作员姓名
     */
    private String operatorName;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 分组编码
     */
    private String groupCode;

}
