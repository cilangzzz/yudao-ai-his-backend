package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 给药未执行/延迟执行 Request VO
 *
 * 用于记录未执行、延迟执行或患者拒绝的情况
 */
@Schema(description = "管理后台 - 给药未执行 Request VO")
@Data
public class HisMedicationAdminNotExecuteReqVO {

    @Schema(description = "给药记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "给药记录ID不能为空")
    private Long id;

    @Schema(description = "状态: 3未执行/4延迟执行/5患者拒绝", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "原因类型: 患者拒绝/病情变化/药品问题/其他", requiredMode = Schema.RequiredMode.REQUIRED, example = "其他")
    @NotBlank(message = "原因类型不能为空")
    private String reasonType;

    @Schema(description = "详细原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "患者外出检查")
    @NotBlank(message = "原因不能为空")
    private String reason;

    @Schema(description = "是否通知医生: 0否/1是", example = "0")
    private Integer notifyDoctor;

    @Schema(description = "备注")
    private String remark;

}
