package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 采购明细 Response VO
 */
@Schema(description = "管理后台 - 采购明细 Response VO")
@Data
public class HisDrugPurchaseItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "采购ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long purchaseId;

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long drugId;

    @Schema(description = "药品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DRUG001")
    private String drugCode;

    @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
    private String drugName;

    @Schema(description = "药品规格", example = "0.5g*24粒")
    private String drugSpec;

    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "盒")
    private String unit;

    @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private BigDecimal quantity;

    @Schema(description = "参考价格", example = "15.00")
    private BigDecimal referencePrice;

    @Schema(description = "预算金额", example = "1500.00")
    private BigDecimal budgetAmount;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}