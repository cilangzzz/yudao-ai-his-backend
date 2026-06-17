package cn.iocoder.yudao.module.his.controller.admin.assess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 入院评估 Response VO")
@Data
public class HisAdmissionAssessRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "评估类型", example = "1")
    private Integer assessType;

    @Schema(description = "评估类型名称", example = "入院评估")
    private String assessTypeName;

    @Schema(description = "评估得分", example = "85")
    private Integer assessScore;

    @Schema(description = "评估结果", example = "正常")
    private String assessResult;

    @Schema(description = "风险等级", example = "1")
    private Integer riskLevel;

    @Schema(description = "风险等级名称", example = "低")
    private String riskLevelName;

    @Schema(description = "评估内容(JSON)", example = "{}")
    private String assessContent;

    @Schema(description = "评估时间")
    private LocalDateTime assessTime;

    @Schema(description = "评估护士ID", example = "1")
    private Long assessNurse;

    @Schema(description = "评估护士姓名", example = "张护士")
    private String assessNurseName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}