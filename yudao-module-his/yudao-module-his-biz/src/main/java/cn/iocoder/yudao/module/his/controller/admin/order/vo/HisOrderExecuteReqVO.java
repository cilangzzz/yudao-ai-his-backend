package cn.iocoder.yudao.module.his.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 医嘱执行 Request VO")
@Data
public class HisOrderExecuteReqVO {

    @Schema(description = "医嘱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱ID不能为空")
    private Long orderId;

    @Schema(description = "执行护士ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "执行护士ID不能为空")
    private Long executeNurseId;

    @Schema(description = "执行护士姓名", example = "王护士")
    private String executeNurseName;

    @Schema(description = "执行时间")
    private LocalDateTime executeTime;

    @Schema(description = "执行备注")
    private String remark;
}