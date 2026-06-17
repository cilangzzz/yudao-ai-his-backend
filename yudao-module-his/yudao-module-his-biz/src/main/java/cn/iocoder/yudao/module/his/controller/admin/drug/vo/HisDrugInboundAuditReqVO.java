package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 入库审核 Request VO
 */
@Schema(description = "管理后台 - 入库审核 Request VO")
@Data
public class HisDrugInboundAuditReqVO {

    @Schema(description = "入库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "入库ID不能为空")
    private Long id;

    @Schema(description = "审核人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审核人ID不能为空")
    private Long auditBy;

    @Schema(description = "审核人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "审核人姓名不能为空")
    private String auditByName;

    @Schema(description = "是否通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "审核结果不能为空")
    private Boolean passed;

    @Schema(description = "审核备注", example = "审核通过")
    private String remark;

}