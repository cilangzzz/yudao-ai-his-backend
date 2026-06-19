package cn.iocoder.yudao.module.his.controller.admin.cds.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * CDS 处方校验请求 VO
 */
@Schema(description = "管理后台 - CDS处方校验请求")
@Data
public class CdsCheckReqVO {

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long patientId;

    @Schema(description = "挂号ID")
    private Long registerId;

    @Schema(description = "住院ID")
    private Long admissionId;

    @Schema(description = "待校验药品列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "待校验药品列表不能为空")
    private List<DrugItem> drugItems;

    @Schema(description = "药品项")
    @Data
    public static class DrugItem {

        @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long drugId;

        @Schema(description = "药品编码")
        private String drugCode;

        @Schema(description = "药品名称")
        private String drugName;

        @Schema(description = "单次剂量")
        private java.math.BigDecimal dosage;

        @Schema(description = "剂量单位")
        private String dosageUnit;

        @Schema(description = "用药频次")
        private String frequency;

        @Schema(description = "用药天数")
        private Integer days;
    }

}