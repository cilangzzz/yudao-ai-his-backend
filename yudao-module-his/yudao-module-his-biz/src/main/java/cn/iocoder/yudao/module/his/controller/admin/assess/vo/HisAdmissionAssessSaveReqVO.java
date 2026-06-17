package cn.iocoder.yudao.module.his.controller.admin.assess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 入院评估创建/修改 Request VO")
@Data
public class HisAdmissionAssessSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院记录不能为空")
    private Long admissionId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者不能为空")
    private Long patientId;

    @Schema(description = "评估类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "评估类型不能为空")
    private Integer assessType;

    @Schema(description = "评估得分", example = "85")
    private Integer assessScore;

    @Schema(description = "评估结果", example = "正常")
    private String assessResult;

    @Schema(description = "风险等级", example = "1")
    private Integer riskLevel;

    @Schema(description = "评估内容(JSON)", example = "{}")
    private String assessContent;

    @Schema(description = "评估时间", example = "2026-06-18")
    private LocalDateTime assessTime;

    @Schema(description = "评估护士ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "评估护士不能为空")
    private Long assessNurse;

    @Schema(description = "评估护士姓名", example = "张护士")
    private String assessNurseName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;
}