package cn.iocoder.yudao.module.his.dal.dataobject.lab;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检验申请明细 DO
 *
 * 检验申请的子项目，支持一个申请包含多个检验项目
 *
 * @author yudao
 */
@TableName(value = "his_lab_request_item", autoResultMap = true)
@KeySequence("his_lab_request_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisLabRequestItemDO extends TenantBaseDO {

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
     * 检验项目编码
     */
    private String itemCode;

    /**
     * 检验项目名称
     */
    private String itemName;

    /**
     * 项目简称
     */
    private String itemShortName;

    /**
     * 项目类别
     */
    private String itemCategory;

    /**
     * 检验方法
     */
    private String testMethod;

    /**
     * 标本类型
     */
    private String sampleType;

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
     * 项目状态
     *
     * 1-待检验 2-检验中 3-已完成 4-已取消
     */
    private Integer itemStatus;

    /**
     * 检验开始时间
     */
    private LocalDateTime startTime;

    /**
     * 检验结束时间
     */
    private LocalDateTime endTime;

    /**
     * 检验技师ID
     */
    private Long technicianId;

    /**
     * 检验技师姓名
     */
    private String technicianName;

    /**
     * 检验结果值
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

    /**
     * 备注
     */
    private String remark;

}
