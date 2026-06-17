package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 入库明细 Response VO
 */
@Schema(description = "管理后台 - 入库明细 Response VO")
@Data
public class HisDrugInboundItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "入库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long inboundId;

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long drugId;

    @Schema(description = "药品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DRUG001")
    private String drugCode;

    @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
    private String drugName;

    @Schema(description = "药品规格", example = "0.5g*24粒")
    private String drugSpec;

    @Schema(description = "批号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20240101")
    private String batchNo;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate expiryDate;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private BigDecimal quantity;

    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "盒")
    private String unit;

    @Schema(description = "零售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "25.50")
    private BigDecimal retailPrice;

    @Schema(description = "采购价", requiredMode = Schema.RequiredMode.REQUIRED, example = "15.00")
    private BigDecimal purchasePrice;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "1500.00")
    private BigDecimal amount;

    @Schema(description = "货架位置", example = "A-01-02")
    private String storageLocation;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}