package cn.iocoder.yudao.module.his.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 医嘱审核 Request VO")
@Data
public class HisOrderAuditReqVO {

    @Schema(description = "医嘱ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱ID不能为空")
    private Long orderId;

    @Schema(description = "审核护士ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审核护士ID不能为空")
    private Long auditNurseId;

    @Schema(description = "审核护士姓名", example = "王护士")
    private String auditNurseName;

    @Schema(description = "审核备注")
    private String remark;
}