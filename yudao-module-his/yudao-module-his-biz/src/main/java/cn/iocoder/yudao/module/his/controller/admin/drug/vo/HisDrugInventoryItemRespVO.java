package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 盘点明细 Response VO
 */
@Schema(description = "管理后台 - 盘点明细 Response VO")
@Data
public class HisDrugInventoryItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "盘点ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long inventoryId;

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long drugId;

    @Schema(description = "药品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DRUG001")
    private String drugCode;

    @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
    private String drugName;

    @Schema(description = "药品规格", example = "0.5g*24粒")
    private String drugSpec;

    @Schema(description = "批号", example = "20240101")
    private String batchNo;

    @Schema(description = "账面数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private BigDecimal bookQuantity;

    @Schema(description = "实盘数量", example = "98")
    private BigDecimal actualQuantity;

    @Schema(description = "差异数量", example = "-2")
    private BigDecimal differenceQuantity;

    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "盒")
    private String unit;

    @Schema(description = "零售价", example = "25.50")
    private BigDecimal retailPrice;

    @Schema(description = "差异金额", example = "-51.00")
    private BigDecimal differenceAmount;

    @Schema(description = "盘点结果:1-正常 2-盘盈 3-盘亏", example = "1")
    private Integer inventoryResult;

    @Schema(description = "盘点状态:1-待盘点 2-已盘点", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer inventoryItemStatus;

    @Schema(description = "盘点人ID", example = "1")
    private Long operatorId;

    @Schema(description = "盘点人姓名", example = "张三")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}