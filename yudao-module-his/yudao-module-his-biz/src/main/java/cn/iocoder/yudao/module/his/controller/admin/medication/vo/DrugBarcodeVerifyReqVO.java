package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 药品条码扫描验证 Request VO")
@Data
public class DrugBarcodeVerifyReqVO {

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "医嘱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱ID不能为空")
    private Long orderId;

    @Schema(description = "药品条码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DR20260601001")
    @NotNull(message = "药品条码不能为空")
    private String drugBarcode;
}