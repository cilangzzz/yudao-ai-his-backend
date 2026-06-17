package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 出库记录 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 出库记录新增/修改 Request VO")
@Data
public class HisDrugOutboundSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "出库类型:1-药房领用 2-报损出库 3-调拨出库", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "出库类型不能为空")
    private Integer outboundType;

    @Schema(description = "出库目标ID", example = "1")
    private Long targetId;

    @Schema(description = "出库目标名称", example = "门诊药房")
    private String targetName;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "中心药房")
    private String warehouseName;

    @Schema(description = "出库时间", example = "2024-01-01 10:00:00")
    private String outboundTime;

    @Schema(description = "操作员ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "张三")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "出库明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "出库明细不能为空")
    @Valid
    private List<OutboundItemVO> items;

    /**
     * 出库明细 VO
     */
    @Data
    public static class OutboundItemVO {

        @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "药品ID不能为空")
        private Long drugId;

        @Schema(description = "批号", example = "20240101")
        private String batchNo;

        @Schema(description = "出库数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        @NotNull(message = "出库数量不能为空")
        private BigDecimal quantity;

        @Schema(description = "零售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "25.50")
        @NotNull(message = "零售价不能为空")
        private BigDecimal retailPrice;

        @Schema(description = "备注", example = "备注信息")
        private String remark;

    }

}