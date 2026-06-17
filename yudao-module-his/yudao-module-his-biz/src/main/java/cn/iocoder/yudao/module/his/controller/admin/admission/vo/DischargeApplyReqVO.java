package cn.iocoder.yudao.module.his.controller.admin.admission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 申请出院 Request VO")
@Data
public class DischargeApplyReqVO {

    @Schema(description = "入院记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long admissionId;

    @Schema(description = "出院方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer dischargeWay;

    @Schema(description = "出院诊断", example = "肺炎痊愈")
    private String dischargeDiagnosis;

    @Schema(description = "出院诊断ICD-10编码", example = "J18.9")
    private String dischargeCode;

    @Schema(description = "出院日期", example = "2026-06-18")
    private LocalDateTime dischargeDate;

    @Schema(description = "备注", example = "备注信息")
    private String remark;
}