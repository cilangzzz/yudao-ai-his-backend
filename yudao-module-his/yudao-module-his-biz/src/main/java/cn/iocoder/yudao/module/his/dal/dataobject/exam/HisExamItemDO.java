package cn.iocoder.yudao.module.his.dal.dataobject.exam;

import cn.iocoder.yudao.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检查项目字典 DO
 *
 * 定义医院可开展的检查项目，包括影像检查、检验检查、病理检查、功能检查
 *
 * @author yudao
 */
@TableName(value = "his_exam_item", autoResultMap = true)
@KeySequence("his_exam_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HisExamItemDO extends TenantBaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 项目编码
     */
    private String itemCode;

    /**
     * 项目名称
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
     * 检查类型
     *
     * 枚举 {@link cn.iocoder.yudao.module.his.enums.ExamTypeEnum}
     * 1-影像检查 2-检验检查 3-病理检查 4-功能检查
     */
    private Integer examType;

    /**
     * 检查方法
     */
    private String examMethod;

    /**
     * 规格/条件
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 默认检查部位
     */
    private String defaultPart;

    /**
     * 执行科室ID
     */
    private Long executionDept;

    /**
     * 执行科室名称
     */
    private String executionDeptName;

    /**
     * 标准价格
     */
    private BigDecimal standardPrice;

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
     * 医保定价
     */
    private BigDecimal insurancePrice;

    /**
     * 检查准备说明
     */
    private String preparationInstruction;

    /**
     * 是否需要空腹
     *
     * 0-否 1-是
     */
    private Integer fastingFlag;

    /**
     * 是否需要造影
     *
     * 0-否 1-是
     */
    private Integer contrastFlag;

    /**
     * 造影剂类型
     */
    private String contrastType;

    /**
     * 是否需要预约
     *
     * 0-否 1-是
     */
    private Integer appointmentRequired;

    /**
     * 默认检查时长(分钟)
     */
    private Integer defaultDuration;

    /**
     * 报告周转时间(小时)
     */
    private Integer reportTat;

    /**
     * 急诊报告周转时间(分钟)
     */
    private Integer statReportTat;

    /**
     * 参考范围下限(检验)
     */
    private BigDecimal referenceRangeLow;

    /**
     * 参考范围上限(检验)
     */
    private BigDecimal referenceRangeHigh;

    /**
     * 参考范围文本
     */
    private String referenceRangeText;

    /**
     * 危急值下限(检验)
     */
    private BigDecimal criticalLow;

    /**
     * 危急值上限(检验)
     */
    private BigDecimal criticalHigh;

    /**
     * 结果单位(检验)
     */
    private String resultUnit;

    /**
     * 标本类型(检验)
     */
    private String sampleType;

    /**
     * 标本量(检验)
     */
    private String sampleVolume;

    /**
     * 状态
     *
     * 1-在用 2-停用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
