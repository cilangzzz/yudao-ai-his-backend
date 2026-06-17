package cn.iocoder.yudao.module.his.controller.admin.prescription.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 处方保存请求 VO
 */
@Schema(description = "管理后台 - HIS处方保存 Request VO")
@Data
public class OpPrescriptionSaveReqVO {

    @Schema(description = "编号（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "处方编号", example = "RX20260618001")
    private String prescriptionNo;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "挂号ID不能为空")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "患者姓名不能为空")
    private String patientName;

    @Schema(description = "处方类型：1-普通 2-急诊 3-儿科 4-麻醉 5-精神 6-中药", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "处方类型不能为空")
    private Integer prescriptionType;

    @Schema(description = "开方科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "开方科室ID不能为空")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "开方医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "开方医生ID不能为空")
    private Long doctorId;

    @Schema(description = "医生姓名", example = "李医生")
    private String doctorName;

    @Schema(description = "诊断编码（ICD-10）", example = "J06.9")
    private String diagnoseCode;

    @Schema(description = "诊断名称", example = "急性上呼吸道感染")
    private String diagnoseName;

    @Schema(description = "处方总金额", example = "150.00")
    private BigDecimal totalAmount;

    @Schema(description = "有效天数", example = "3")
    private Integer validDays;

    @Schema(description = "备注", example = "饭后服用")
    private String remark;

    @Schema(description = "处方明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "处方明细不能为空")
    @Valid
    private List<PrescriptionItemVO> items;

    /**
     * 处方明细 VO
     */
    @Schema(description = "处方明细")
    @Data
    public static class PrescriptionItemVO {

        @Schema(description = "编号（更新时必填）", example = "1")
        private Long id;

        @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "药品ID不能为空")
        private Long drugId;

        @Schema(description = "药品编码", example = "DRUG001")
        private String drugCode;

        @Schema(description = "药品通用名", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
        @NotEmpty(message = "药品名称不能为空")
        private String drugName;

        @Schema(description = "药品规格", example = "0.5g*24粒")
        private String drugSpec;

        @Schema(description = "生产厂家", example = "哈药集团")
        private String manufacturer;

        @Schema(description = "单次剂量", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.5")
        @NotNull(message = "单次剂量不能为空")
        private BigDecimal dosage;

        @Schema(description = "剂量单位", example = "g")
        private String dosageUnit;

        @Schema(description = "频次编码", example = "TID")
        private String frequencyCode;

        @Schema(description = "频次名称", example = "一日三次")
        private String frequencyName;

        @Schema(description = "用药途径编码", example = "PO")
        private String routeCode;

        @Schema(description = "用药途径名称", example = "口服")
        private String routeName;

        @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "24")
        @NotNull(message = "数量不能为空")
        private BigDecimal quantity;

        @Schema(description = "单位", example = "粒")
        private String unit;

        @Schema(description = "单价", example = "2.50")
        private BigDecimal unitPrice;

        @Schema(description = "金额", example = "60.00")
        private BigDecimal amount;

        @Schema(description = "用药天数", example = "3")
        private Integer days;

        @Schema(description = "皮试标志：0-不需要 1-需要 2-已做通过 3-已做未通过", example = "0")
        private Integer skinTest;

        @Schema(description = "皮试结果", example = "阴性")
        private String skinTestResult;

        @Schema(description = "皮试时间", example = "2026-06-18 10:00:00")
        private LocalDateTime skinTestTime;

        @Schema(description = "皮试护士ID", example = "1")
        private Long skinTestNurse;

        @Schema(description = "用药备注", example = "饭后半小时服用")
        private String remarks;

        @Schema(description = "排序号", example = "1")
        private Integer sortOrder;

    }

}
