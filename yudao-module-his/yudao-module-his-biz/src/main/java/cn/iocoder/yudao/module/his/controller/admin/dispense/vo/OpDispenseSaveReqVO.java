package cn.iocoder.yudao.module.his.controller.admin.dispense.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "管理后台 - 发药记录新增/修改 Request VO")
@Data
public class OpDispenseSaveReqVO {

    @Schema(description = "主键ID（修改时需要）", example = "1")
    private Long id;

    @Schema(description = "处方ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "处方ID不能为空")
    private Long prescriptionId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "挂号ID不能为空")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "药房ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "药房ID不能为空")
    private Long pharmacyId;

    @Schema(description = "药房名称", example = "门诊西药房")
    private String pharmacyName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "发药明细列表")
    @Valid
    private List<DispenseItemVO> items;

    @Schema(description = "发药明细项")
    @Data
    public static class DispenseItemVO {

        @Schema(description = "处方明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "处方明细ID不能为空")
        private Long prescriptionItemId;

        @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "药品ID不能为空")
        private Long drugId;

        @Schema(description = "药品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DRUG001")
        @NotEmpty(message = "药品编码不能为空")
        private String drugCode;

        @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
        @NotEmpty(message = "药品名称不能为空")
        private String drugName;

        @Schema(description = "药品规格", example = "0.5g*24粒")
        private String drugSpec;

        @Schema(description = "批号", example = "B20260601")
        private String batchNo;

        @Schema(description = "发药数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
        @NotNull(message = "发药数量不能为空")
        private BigDecimal quantity;

        @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "盒")
        @NotEmpty(message = "单位不能为空")
        private String unit;

        @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "25.50")
        @NotNull(message = "单价不能为空")
        private BigDecimal unitPrice;

        @Schema(description = "有效期", example = "2028-06-01")
        private LocalDate expiryDate;

        @Schema(description = "货架位置", example = "A-01-02")
        private String storageLocation;

    }

}
