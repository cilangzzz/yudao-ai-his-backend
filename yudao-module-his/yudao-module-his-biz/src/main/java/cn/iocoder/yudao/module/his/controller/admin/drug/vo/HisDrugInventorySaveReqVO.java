package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 盘点记录 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 盘点记录新增/修改 Request VO")
@Data
public class HisDrugInventorySaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "中心药房")
    private String warehouseName;

    @Schema(description = "盘点日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "盘点日期不能为空")
    private LocalDate inventoryDate;

    @Schema(description = "盘点人ID", example = "1")
    private Long operatorId;

    @Schema(description = "盘点人姓名", example = "张三")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}