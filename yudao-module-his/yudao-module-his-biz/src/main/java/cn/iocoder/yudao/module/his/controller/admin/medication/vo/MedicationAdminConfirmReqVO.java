package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 给药执行确认 Request VO")
@Data
public class MedicationAdminConfirmReqVO {

    @Schema(description = "给药记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "给药记录ID不能为空")
    private Long adminId;

    @Schema(description = "腕带是否验证通过", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "腕带验证状态不能为空")
    private Boolean wristbandVerified;

    @Schema(description = "药品是否验证通过", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "药品验证状态不能为空")
    private Boolean drugVerified;

    @Schema(description = "执行时间")
    private LocalDateTime adminTime;

    @Schema(description = "执行护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "药品批号", example = "20260601")
    private String drugBatchNo;

    @Schema(description = "备注")
    private String remark;
}