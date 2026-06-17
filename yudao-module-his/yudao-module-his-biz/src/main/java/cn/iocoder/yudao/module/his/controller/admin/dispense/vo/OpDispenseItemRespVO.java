package cn.iocoder.yudao.module.his.controller.admin.dispense.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 发药明细 Response VO")
@Data
public class OpDispenseItemRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "发药ID", example = "1")
    private Long dispenseId;

    @Schema(description = "处方明细ID", example = "1")
    private Long prescriptionItemId;

    @Schema(description = "药品ID", example = "1")
    private Long drugId;

    @Schema(description = "药品编码", example = "DRUG001")
    private String drugCode;

    @Schema(description = "药品名称", example = "阿莫西林胶囊")
    private String drugName;

    @Schema(description = "药品规格", example = "0.5g*24粒")
    private String drugSpec;

    @Schema(description = "批号", example = "B20260601")
    private String batchNo;

    @Schema(description = "发药数量", example = "2")
    private BigDecimal quantity;

    @Schema(description = "单位", example = "盒")
    private String unit;

    @Schema(description = "单价", example = "25.50")
    private BigDecimal unitPrice;

    @Schema(description = "金额", example = "51.00")
    private BigDecimal amount;

    @Schema(description = "有效期", example = "2028-06-01")
    private LocalDate expiryDate;

    @Schema(description = "货架位置", example = "A-01-02")
    private String storageLocation;

    @Schema(description = "是否已过期", example = "false")
    private Boolean expired;

    @Schema(description = "是否即将过期（30天内）", example = "false")
    private Boolean nearExpiry;

}
