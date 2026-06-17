package cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 退药明细 Response VO
 */
@Schema(description = "管理后台 - 退药明细 Response VO")
@Data
public class OpDrugReturnItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "退药ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long returnId;

    @Schema(description = "退药单号", example = "TY202606180001")
    private String returnNo;

    @Schema(description = "原处方明细ID")
    private Long prescriptionItemId;

    @Schema(description = "原发药明细ID")
    private Long dispenseItemId;

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long drugId;

    @Schema(description = "药品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "YP001")
    private String drugCode;

    @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
    private String drugName;

    @Schema(description = "药品规格", example = "0.5g*24粒")
    private String drugSpec;

    @Schema(description = "生产厂家", example = "XX制药")
    private String manufacturer;

    @Schema(description = "批号")
    private String batchNo;

    @Schema(description = "有效期")
    private LocalDate expiryDate;

    @Schema(description = "原发药数量", example = "10")
    private BigDecimal originalQuantity;

    @Schema(description = "退药数量", example = "5")
    private BigDecimal returnQuantity;

    @Schema(description = "已退数量", example = "5")
    private BigDecimal returnedQuantity;

    @Schema(description = "剩余可退数量", example = "0")
    private BigDecimal remainingQuantity;

    @Schema(description = "单位", example = "盒")
    private String unit;

    @Schema(description = "单价", example = "25.50")
    private BigDecimal unitPrice;

    @Schema(description = "退药金额", example = "127.50")
    private BigDecimal amount;

    @Schema(description = "药品状态：1-完好 2-包装破损 3-过期 4-其他", example = "1")
    private Integer drugCondition;

    @Schema(description = "药品状态名称", example = "完好")
    private String drugConditionName;

    @Schema(description = "药品状态说明")
    private String drugConditionRemark;

    @Schema(description = "是否可再利用：0-否 1-是", example = "1")
    private Integer canReuse;

    @Schema(description = "存放位置")
    private String storageLocation;

    @Schema(description = "明细状态：1-待审核 2-审核通过 3-审核拒绝 4-已入库", example = "1")
    private Integer itemStatus;

    @Schema(description = "明细状态名称", example = "待审核")
    private String itemStatusName;

    @Schema(description = "退药原因")
    private String returnReason;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate createTime;

    // ==================== 扩展字段 ====================

    @Schema(description = "是否过期", example = "false")
    private Boolean expired;

    @Schema(description = "是否近效期", example = "false")
    private Boolean nearExpiry;

}
