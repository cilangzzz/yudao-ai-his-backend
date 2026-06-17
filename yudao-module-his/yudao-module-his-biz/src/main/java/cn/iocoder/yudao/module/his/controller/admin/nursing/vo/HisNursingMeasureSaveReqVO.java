package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理措施新增/修改 Request VO")
@Data
public class HisNursingMeasureSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "关联评估ID", example = "1")
    private Long assessmentId;

    @Schema(description = "措施类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "措施类型不能为空")
    private Integer measureType;

    @Schema(description = "措施名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "跌倒预防措施")
    @NotBlank(message = "措施名称不能为空")
    private String measureName;

    @Schema(description = "措施内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "措施内容不能为空")
    private String measureContent;

    @Schema(description = "执行频次", example = "每日2次")
    private String frequency;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "执行护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "执行护士姓名", example = "王护士")
    private String nurseName;

    @Schema(description = "备注")
    private String remark;
}