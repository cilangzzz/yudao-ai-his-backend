package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 给药记录 Response VO")
@Data
public class HisMedicationAdminRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "记录编号", example = "MA202606170001")
    private String adminNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "住院号", example = "ZY202606170001")
    private String admissionNo;

    @Schema(description = "医嘱ID", example = "1")
    private Long orderId;

    @Schema(description = "医嘱编号", example = "O202606170001")
    private String orderNo;

    @Schema(description = "药品ID", example = "1")
    private Long drugId;

    @Schema(description = "药品编码", example = "DR001")
    private String drugCode;

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

    @Schema(description = "预定执行时间")
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

    @Schema(description = "腕带扫描状态名称", example = "匹配")
    private String wristbandScanStatusName;

    @Schema(description = "腕带扫描时间")
    private LocalDateTime wristbandScanTime;

    @Schema(description = "腕带扫描结果")
    private String wristbandScanResult;

    @Schema(description = "药品扫描状态", example = "1")
    private Integer drugScanStatus;

    @Schema(description = "药品扫描状态名称", example = "匹配")
    private String drugScanStatusName;

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

    @Schema(description = "核对结果名称", example = "通过")
    private String checkResultName;

    // ==================== 执行状态 ====================

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "状态名称", example = "待执行")
    private String statusName;

    @Schema(description = "未执行/延迟原因")
    private String reason;

    @Schema(description = "原因类型")
    private String reasonType;

    @Schema(description = "是否通知医生")
    private Boolean notifyDoctor;

    // ==================== 记账状态 ====================

    @Schema(description = "记账状态", example = "0")
    private Integer chargeStatus;

    @Schema(description = "记账时间")
    private LocalDateTime chargeTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否延迟给药")
    private Boolean delayed;

    @Schema(description = "双重核对是否通过")
    private Boolean doubleCheckPassed;
}