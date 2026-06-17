package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 给药记录新增/修改 Request VO")
@Data
public class HisMedicationAdminSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "医嘱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱ID不能为空")
    private Long orderId;

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "药品ID不能为空")
    private Long drugId;

    @Schema(description = "药品名称", example = "0.9%氯化钠注射液")
    private String drugName;

    @Schema(description = "规格", example = "250ml")
    private String spec;

    @Schema(description = "剂量", example = "250")
    private BigDecimal dosage;

    @Schema(description = "剂量单位", example = "ml")
    private String dosageUnit;

    @Schema(description = "给药途径", example = "ivgtt")
    private String route;

    @Schema(description = "预定执行时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预定执行时间不能为空")
    private LocalDateTime scheduledTime;

    @Schema(description = "实际执行时间")
    private LocalDateTime actualTime;

    @Schema(description = "执行护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "执行护士姓名", example = "王护士")
    private String nurseName;

    // ==================== 闭环给药核对 ====================

    @Schema(description = "腕带扫描状态", example = "1")
    private Integer wristbandScanStatus;

    @Schema(description = "腕带扫描时间")
    private LocalDateTime wristbandScanTime;

    @Schema(description = "腕带扫描结果")
    private String wristbandScanResult;

    @Schema(description = "药品扫描状态", example = "1")
    private Integer drugScanStatus;

    @Schema(description = "药品扫描时间")
    private LocalDateTime drugScanTime;

    @Schema(description = "药品扫描结果")
    private String drugScanResult;

    @Schema(description = "药品批号", example = "20260601")
    private String drugBatchNo;

    @Schema(description = "药品有效期")
    private LocalDate drugExpireDate;

    @Schema(description = "核对结果", example = "1")
    private Integer checkResult;

    // ==================== 执行状态 ====================

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "未执行/延迟原因", example = "患者拒绝")
    private String reason;

    @Schema(description = "原因类型", example = "患者拒绝")
    private String reasonType;

    @Schema(description = "是否通知医生", example = "false")
    private Boolean notifyDoctor;

    @Schema(description = "备注")
    private String remark;
}