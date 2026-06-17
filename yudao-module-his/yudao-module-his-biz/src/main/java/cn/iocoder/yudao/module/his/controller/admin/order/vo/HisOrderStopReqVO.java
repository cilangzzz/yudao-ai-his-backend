package cn.iocoder.yudao.module.his.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 医嘱停止 Request VO")
@Data
public class HisOrderStopReqVO {

    @Schema(description = "医嘱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱ID不能为空")
    private Long orderId;

    @Schema(description = "停止医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "停止医生ID不能为空")
    private Long stopDoctorId;

    @Schema(description = "停止医生姓名", example = "李医生")
    private String stopDoctorName;

    @Schema(description = "停止时间")
    private LocalDateTime stopTime;

    @Schema(description = "停止原因", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "停止原因不能为空")
    private String stopReason;
}