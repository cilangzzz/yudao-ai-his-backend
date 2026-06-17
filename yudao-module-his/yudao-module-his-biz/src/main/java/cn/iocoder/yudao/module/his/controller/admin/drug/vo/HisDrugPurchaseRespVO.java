package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购计划 Response VO
 */
@Schema(description = "管理后台 - 采购计划 Response VO")
@Data
public class HisDrugPurchaseRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "采购单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "CG202401010001")
    private String purchaseNo;

    @Schema(description = "供应商ID", example = "1")
    private Long supplierId;

    @Schema(description = "供应商名称", example = "XX医药公司")
    private String supplierName;

    @Schema(description = "采购类型:1-计划采购 2-紧急采购", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer purchaseType;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "15000.00")
    private BigDecimal totalAmount;

    @Schema(description = "状态:1-草稿 2-已提交 3-已审批 4-已采购 5-已完成", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer purchaseStatus;

    @Schema(description = "申请人ID", example = "1")
    private Long applyBy;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审批人ID", example = "1")
    private Long auditBy;

    @Schema(description = "审批时间")
    private LocalDateTime auditTime;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}