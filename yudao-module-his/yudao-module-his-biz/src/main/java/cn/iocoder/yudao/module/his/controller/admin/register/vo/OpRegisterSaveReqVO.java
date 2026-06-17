package cn.iocoder.yudao.module.his.controller.admin.register.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 挂号保存请求 VO
 */
@Schema(description = "管理后台 - HIS挂号保存 Request VO")
@Data
public class OpRegisterSaveReqVO {

    @Schema(description = "编号（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "排班ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "排班ID不能为空")
    private Long scheduleId;

    @Schema(description = "预约ID（预约转挂号时传入）", example = "1")
    private Long appointmentId;

    @Schema(description = "来源：1-现场 2-微信 3-APP 4-网站 5-自助机", example = "1")
    private Integer sourceType;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}