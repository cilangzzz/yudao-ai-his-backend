package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 采购计划 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 采购计划新增/修改 Request VO")
@Data
public class HisDrugPurchaseSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "供应商ID", example = "1")
    private Long supplierId;

    @Schema(description = "供应商名称", example = "XX医药公司")
    private String supplierName;

    @Schema(description = "采购类型:1-计划采购 2-紧急采购", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "采购类型不能为空")
    private Integer purchaseType;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "采购明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "采购明细不能为空")
    @Valid
    private List<PurchaseItemVO> items;

    /**
     * 采购明细 VO
     */
    @Data
    public static class PurchaseItemVO {

        @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "药品ID不能为空")
        private Long drugId;

        @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        @NotNull(message = "采购数量不能为空")
        private BigDecimal quantity;

        @Schema(description = "参考价格", example = "15.00")
        private BigDecimal referencePrice;

        @Schema(description = "备注", example = "备注信息")
        private String remark;

    }

}