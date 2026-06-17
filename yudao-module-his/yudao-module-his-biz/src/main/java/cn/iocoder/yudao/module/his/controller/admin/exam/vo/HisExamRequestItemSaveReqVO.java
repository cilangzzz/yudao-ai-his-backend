package cn.iocoder.yudao.module.his.controller.admin.exam.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 检查申请明细保存请求 VO
 */
@Schema(description = "管理后台 - HIS检查申请明细保存 Request VO")
@Data
public class HisExamRequestItemSaveReqVO {

    @Schema(description = "编号(更新时传递)", example = "1")
    private Long id;

    @Schema(description = "检查项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "CT001")
    @NotBlank(message = "检查项目编码不能为空")
    private String itemCode;

    @Schema(description = "检查项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "胸部CT平扫")
    @NotBlank(message = "检查项目名称不能为空")
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

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "备注", example = "项目备注")
    private String remark;

}
