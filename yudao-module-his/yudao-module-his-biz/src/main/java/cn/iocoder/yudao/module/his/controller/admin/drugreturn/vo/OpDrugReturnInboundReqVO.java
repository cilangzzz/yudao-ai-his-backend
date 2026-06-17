package cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 退药入库 Request VO
 */
@Schema(description = "管理后台 - 退药入库 Request VO")
@Data
public class OpDrugReturnInboundReqVO {

    @Schema(description = "退药ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "退药ID不能为空")
    private Long returnId;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "门诊药房仓库")
    private String warehouseName;

    @Schema(description = "备注")
    private String remark;

}
