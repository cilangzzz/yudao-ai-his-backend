package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 入库记录 Response VO
 */
@Schema(description = "管理后台 - 入库记录 Response VO")
@Data
public class HisDrugInboundRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "入库单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "RK202401010001")
    private String inboundNo;

    @Schema(description = "入库类型:1-采购入库 2-退货入库 3-调拨入库", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer inboundType;

    @Schema(description = "供应商ID", example = "1")
    private Long supplierId;

    @Schema(description = "供应商名称", example = "XX医药公司")
    private String supplierName;

    @Schema(description = "采购单ID", example = "1")
    private Long purchaseId;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "中心药房")
    private String warehouseName;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "1500.00")
    private BigDecimal totalAmount;

    @Schema(description = "入库时间")
    private LocalDateTime inboundTime;

    @Schema(description = "操作员ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "张三")
    private String operatorName;

    @Schema(description = "审核状态:1-待审核 2-已审核 3-已拒绝", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer auditStatus;

    @Schema(description = "审核人ID", example = "1")
    private Long auditBy;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}