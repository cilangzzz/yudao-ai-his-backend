package cn.iocoder.yudao.module.his.controller.admin.prescription.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 处方明细响应 VO
 */
@Schema(description = "管理后台 - HIS门诊处方明细 Response VO")
@Data
public class OpPrescriptionItemRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "处方ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long prescriptionId;

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long drugId;

    @Schema(description = "药品编码", example = "DRUG001")
    private String drugCode;

    @Schema(description = "药品通用名", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
    private String drugName;

    @Schema(description = "药品规格", example = "0.5g*24粒")
    private String drugSpec;

    @Schema(description = "生产厂家", example = "某某制药")
    private String manufacturer;

    @Schema(description = "单次剂量", example = "0.5")
    private BigDecimal dosage;

    @Schema(description = "剂量单位", example = "g")
    private String dosageUnit;

    @Schema(description = "频次编码", example = "TID")
    private String frequencyCode;

    @Schema(description = "频次名称", example = "每日三次")
    private String frequencyName;

    @Schema(description = "用药途径编码", example = "PO")
    private String routeCode;

    @Schema(description = "用药途径名称", example = "口服")
    private String routeName;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "24")
    private BigDecimal quantity;

    @Schema(description = "单位", example = "粒")
    private String unit;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "2.50")
    private BigDecimal unitPrice;

    @Schema(description = "金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "60.00")
    private BigDecimal amount;

    @Schema(description = "用药天数", example = "3")
    private Integer days;

    @Schema(description = "皮试标志：0-不需要 1-需要 2-已做通过 3-已做未通过", example = "0")
    private Integer skinTest;

    @Schema(description = "皮试结果", example = "阴性")
    private String skinTestResult;

    @Schema(description = "皮试时间")
    private LocalDateTime skinTestTime;

    @Schema(description = "皮试护士ID", example = "1")
    private Long skinTestNurse;

    @Schema(description = "用药备注", example = "饭后半小时服用")
    private String remarks;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
