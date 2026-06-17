package cn.iocoder.yudao.module.his.controller.admin.exam.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检查申请明细响应 VO
 */
@Schema(description = "管理后台 - HIS检查申请明细 Response VO")
@Data
public class HisExamRequestItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "申请ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long requestId;

    @Schema(description = "检查项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "CT001")
    private String itemCode;

    @Schema(description = "检查项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "胸部CT平扫")
    private String itemName;

    @Schema(description = "项目类别", example = "CT")
    private String itemCategory;

    @Schema(description = "检查方法", example = "平扫")
    private String examMethod;

    @Schema(description = "检查部位", example = "胸部")
    private String examPart;

    @Schema(description = "规格/条件", example = "层厚5mm")
    private String spec;

    @Schema(description = "单位", example = "次")
    private String unit;

    @Schema(description = "数量", example = "1")
    private BigDecimal quantity;

    @Schema(description = "单价", example = "300.00")
    private BigDecimal unitPrice;

    @Schema(description = "金额", example = "300.00")
    private BigDecimal amount;

    @Schema(description = "医保编码", example = "330100001")
    private String insuranceCode;

    @Schema(description = "医保类别:1-甲类 2-乙类 3-丙类", example = "1")
    private Integer insuranceCategory;

    @Schema(description = "执行科室ID", example = "20")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "放射科")
    private String executionDeptName;

    @Schema(description = "状态:1-待检查 2-检查中 3-已完成 4-已取消", example = "1")
    private Integer itemStatus;

    @Schema(description = "检查开始时间")
    private LocalDateTime startTime;

    @Schema(description = "检查结束时间")
    private LocalDateTime endTime;

    @Schema(description = "检查技师姓名", example = "王技师")
    private String technicianName;

    @Schema(description = "检查结果(检验)", example = "正常")
    private String resultValue;

    @Schema(description = "结果单位", example = "mg/L")
    private String resultUnit;

    @Schema(description = "参考范围", example = "0-10")
    private String referenceRange;

    @Schema(description = "异常标志:0-正常 1-偏高 2-偏低 3-阳性 4-阴性", example = "0")
    private Integer abnormalFlag;

    @Schema(description = "结果备注", example = "正常范围")
    private String resultRemark;

    @Schema(description = "影像URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "备注", example = "项目备注")
    private String remark;

}