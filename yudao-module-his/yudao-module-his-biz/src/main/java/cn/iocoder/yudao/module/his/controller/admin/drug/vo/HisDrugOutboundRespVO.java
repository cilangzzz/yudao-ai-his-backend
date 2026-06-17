package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 出库记录 Response VO
 */
@Schema(description = "管理后台 - 出库记录 Response VO")
@Data
public class HisDrugOutboundRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "出库单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "CK202401010001")
    private String outboundNo;

    @Schema(description = "出库类型:1-药房领用 2-报损出库 3-调拨出库", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer outboundType;

    @Schema(description = "出库目标ID", example = "1")
    private Long targetId;

    @Schema(description = "出库目标名称", example = "门诊药房")
    private String targetName;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "中心药房")
    private String warehouseName;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "1500.00")
    private BigDecimal totalAmount;

    @Schema(description = "出库时间")
    private LocalDateTime outboundTime;

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