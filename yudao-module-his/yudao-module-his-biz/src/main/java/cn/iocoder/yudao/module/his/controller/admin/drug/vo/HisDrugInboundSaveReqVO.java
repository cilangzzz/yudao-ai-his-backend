package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 入库记录 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 入库记录新增/修改 Request VO")
@Data
public class HisDrugInboundSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "入库类型:1-采购入库 2-退货入库 3-调拨入库", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "入库类型不能为空")
    private Integer inboundType;

    @Schema(description = "供应商ID", example = "1")
    private Long supplierId;

    @Schema(description = "供应商名称", example = "XX医药公司")
    private String supplierName;

    @Schema(description = "采购单ID", example = "1")
    private Long purchaseId;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "中心药房")
    private String warehouseName;

    @Schema(description = "入库时间", example = "2024-01-01 10:00:00")
    private String inboundTime;

    @Schema(description = "操作员ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "张三")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "入库明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "入库明细不能为空")
    @Valid
    private List<InboundItemVO> items;

    /**
     * 入库明细 VO
     */
    @Data
    public static class InboundItemVO {

        @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "药品ID不能为空")
        private Long drugId;

        @Schema(description = "批号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20240101")
        @NotNull(message = "批号不能为空")
        private String batchNo;

        @Schema(description = "生产日期")
        private LocalDate productionDate;

        @Schema(description = "有效期", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "有效期不能为空")
        private LocalDate expiryDate;

        @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        @NotNull(message = "入库数量不能为空")
        private BigDecimal quantity;

        @Schema(description = "零售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "25.50")
        @NotNull(message = "零售价不能为空")
        private BigDecimal retailPrice;

        @Schema(description = "采购价", requiredMode = Schema.RequiredMode.REQUIRED, example = "15.00")
        @NotNull(message = "采购价不能为空")
        private BigDecimal purchasePrice;

        @Schema(description = "货架位置", example = "A-01-02")
        private String storageLocation;

        @Schema(description = "备注", example = "备注信息")
        private String remark;

    }

}