package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 盘点明细 盘点 Request VO
 */
@Schema(description = "管理后台 - 盘点明细盘点 Request VO")
@Data
public class HisDrugInventoryItemSaveReqVO {

    @Schema(description = "盘点明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "盘点明细ID不能为空")
    private Long itemId;

    @Schema(description = "实盘数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "实盘数量不能为空")
    private BigDecimal actualQuantity;

    @Schema(description = "操作员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "操作员ID不能为空")
    private Long operatorId;

    @Schema(description = "操作员姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "操作员姓名不能为空")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}