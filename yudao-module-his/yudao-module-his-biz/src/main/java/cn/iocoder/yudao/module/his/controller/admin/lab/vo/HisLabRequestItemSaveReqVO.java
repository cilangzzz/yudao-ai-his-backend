package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 检验申请明细保存请求 VO
 */
@Schema(description = "管理后台 - HIS检验申请明细保存 Request VO")
@Data
public class HisLabRequestItemSaveReqVO {

    @Schema(description = "编号(更新时传递)", example = "1")
    private Long id;

    @Schema(description = "检验项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "WBC")
    @NotBlank(message = "检验项目编码不能为空")
    private String itemCode;

    @Schema(description = "检验项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "白细胞计数")
    @NotBlank(message = "检验项目名称不能为空")
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

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "分组编码", example = "CBC")
    private String groupCode;

    @Schema(description = "备注", example = "项目备注")
    private String remark;

}
