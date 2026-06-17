package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 盘点记录 Response VO
 */
@Schema(description = "管理后台 - 盘点记录 Response VO")
@Data
public class HisDrugInventoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "盘点单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "PD202401010001")
    private String inventoryNo;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "中心药房")
    private String warehouseName;

    @Schema(description = "盘点日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate inventoryDate;

    @Schema(description = "盘点品种数", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer totalItems;

    @Schema(description = "盘盈品种数", example = "5")
    private Integer profitItems;

    @Schema(description = "盘亏品种数", example = "3")
    private Integer lossItems;

    @Schema(description = "盘盈金额", example = "1500.00")
    private BigDecimal profitAmount;

    @Schema(description = "盘亏金额", example = "800.00")
    private BigDecimal lossAmount;

    @Schema(description = "状态:1-进行中 2-已完成", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer inventoryStatus;

    @Schema(description = "盘点人ID", example = "1")
    private Long operatorId;

    @Schema(description = "盘点人姓名", example = "张三")
    private String operatorName;

    @Schema(description = "完成时间")
    private LocalDateTime completeTime;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}