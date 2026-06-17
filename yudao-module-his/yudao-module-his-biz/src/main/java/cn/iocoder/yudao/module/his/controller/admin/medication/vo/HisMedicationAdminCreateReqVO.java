package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 给药记录创建 Request VO
 */
@Schema(description = "管理后台 - 给药记录创建 Request VO")
@Data
public class HisMedicationAdminCreateReqVO {

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotBlank(message = "患者姓名不能为空")
    private String patientName;

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "住院号", example = "ZY202606180001")
    private String admissionNo;

    @Schema(description = "医嘱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱ID不能为空")
    private Long orderId;

    @Schema(description = "医嘱编号", example = "O202606180001")
    private String orderNo;

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "药品ID不能为空")
    private Long drugId;

    @Schema(description = "药品编码", example = "DRUG001")
    private String drugCode;

    @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
    @NotBlank(message = "药品名称不能为空")
    private String drugName;

    @Schema(description = "规格", example = "0.5g*24粒")
    private String spec;

    @Schema(description = "剂量", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.5")
    @NotNull(message = "剂量不能为空")
    private BigDecimal dosage;

    @Schema(description = "剂量单位", example = "g")
    private String dosageUnit;

    @Schema(description = "给药途径", example = "口服")
    private String route;

    @Schema(description = "预定执行时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预定执行时间不能为空")
    private LocalDateTime scheduledTime;

    @Schema(description = "执行护士ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "执行护士ID不能为空")
    private Long nurseId;

    @Schema(description = "执行护士姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李护士")
    @NotBlank(message = "执行护士姓名不能为空")
    private String nurseName;

    @Schema(description = "备注")
    private String remark;

}
