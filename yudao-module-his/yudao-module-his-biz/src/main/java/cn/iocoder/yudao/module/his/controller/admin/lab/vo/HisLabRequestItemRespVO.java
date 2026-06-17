package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检验申请明细响应 VO
 */
@Schema(description = "管理后台 - HIS检验申请明细 Response VO")
@Data
public class HisLabRequestItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "申请ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long requestId;

    @Schema(description = "检验项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "WBC")
    private String itemCode;

    @Schema(description = "检验项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "白细胞计数")
    private String itemName;

    @Schema(description = "项目简称", example = "WBC")
    private String itemShortName;

    @Schema(description = "项目类别", example = "血常规")
    private String itemCategory;

    @Schema(description = "检验方法", example = "仪器法")
    private String testMethod;

    @Schema(description = "标本类型", example = "血液")
    private String sampleType;

    @Schema(description = "单位", example = "10^9/L")
    private String unit;

    @Schema(description = "数量", example = "1")
    private BigDecimal quantity;

    @Schema(description = "单价", example = "10.00")
    private BigDecimal unitPrice;

    @Schema(description = "金额", example = "10.00")
    private BigDecimal amount;

    @Schema(description = "医保编码", example = "220100001")
    private String insuranceCode;

    @Schema(description = "医保类别:1-甲类 2-乙类 3-丙类", example = "1")
    private Integer insuranceCategory;

    @Schema(description = "执行科室ID", example = "20")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "检验科")
    private String executionDeptName;

    @Schema(description = "项目状态:1-待检验 2-检验中 3-已完成 4-已取消", example = "1")
    private Integer itemStatus;

    @Schema(description = "检验开始时间")
    private LocalDateTime startTime;

    @Schema(description = "检验结束时间")
    private LocalDateTime endTime;

    @Schema(description = "检验技师姓名", example = "王技师")
    private String technicianName;

    // ========== 结果信息 ==========

    @Schema(description = "检验结果值", example = "12.5")
    private String resultValue;

    @Schema(description = "结果单位", example = "10^9/L")
    private String resultUnit;

    @Schema(description = "参考范围下限", example = "4.0")
    private BigDecimal referenceRangeLow;

    @Schema(description = "参考范围上限", example = "10.0")
    private BigDecimal referenceRangeHigh;

    @Schema(description = "参考范围文本", example = "4.0-10.0")
    private String referenceRangeText;

    @Schema(description = "异常标志:0-正常 1-偏高 2-偏低 3-阳性 4-阴性 5-弱阳性", example = "0")
    private Integer abnormalFlag;

    @Schema(description = "危急值标志:0-无 1-有", example = "0")
    private Integer criticalFlag;

    @Schema(description = "危急值下限", example = "2.0")
    private BigDecimal criticalLow;

    @Schema(description = "危急值上限", example = "30.0")
    private BigDecimal criticalHigh;

    @Schema(description = "结果状态:1-正常 2-异常 3-危急", example = "1")
    private Integer resultStatus;

    @Schema(description = "结果备注", example = "正常范围")
    private String resultRemark;

    @Schema(description = "仪器设备名称", example = "全自动血液分析仪")
    private String instrumentName;

    @Schema(description = "试剂批号", example = "202606001")
    private String reagentBatch;

    @Schema(description = "检验时间")
    private LocalDateTime testTime;

    @Schema(description = "操作员姓名", example = "王技师")
    private String operatorName;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "分组编码", example = "CBC")
    private String groupCode;

    @Schema(description = "备注", example = "项目备注")
    private String remark;

}