package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 出院申请驳回 Request VO")
@Data
public class HisDischargeApplyRejectReqVO {

    @Schema(description = "出院申请ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "出院申请ID不能为空")
    private Long id;

    @Schema(description = "审批医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审批医生ID不能为空")
    private Long auditDoctorId;

    @Schema(description = "审批医生姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李医生")
    @NotNull(message = "审批医生姓名不能为空")
    private String auditDoctorName;

    @Schema(description = "驳回原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "需进一步观察")
    @NotNull(message = "驳回原因不能为空")
    private String rejectReason;

}