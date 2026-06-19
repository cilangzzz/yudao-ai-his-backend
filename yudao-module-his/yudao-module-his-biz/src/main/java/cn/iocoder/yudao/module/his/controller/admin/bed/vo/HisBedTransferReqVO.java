package cn.iocoder.yudao.module.his.controller.admin.bed.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 转床请求 VO
 */
@Schema(description = "管理后台 - 转床请求")
@Data
public class HisBedTransferReqVO {

    @Schema(description = "原床位ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "原床位ID不能为空")
    private Long fromBedId;

    @Schema(description = "目标床位ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "目标床位ID不能为空")
    private Long toBedId;

    @Schema(description = "患者ID")
    private Long patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "住院ID")
    private Long admissionId;

    @Schema(description = "转床原因")
    private String reason;

}