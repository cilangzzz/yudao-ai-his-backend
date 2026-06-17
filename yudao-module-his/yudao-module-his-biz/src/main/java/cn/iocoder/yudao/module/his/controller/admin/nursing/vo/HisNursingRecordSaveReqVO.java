package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理记录新增/修改 Request VO")
@Data
public class HisNursingRecordSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "住院ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住院ID不能为空")
    private Long admissionId;

    @Schema(description = "记录类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "记录类型不能为空")
    private Integer recordType;

    @Schema(description = "标题", example = "护理记录")
    private String title;

    @Schema(description = "护理内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "护理内容不能为空")
    private String content;

    @Schema(description = "记录时间")
    private LocalDateTime recordTime;

    @Schema(description = "护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "护士姓名", example = "王护士")
    private String nurseName;

    @Schema(description = "备注")
    private String remark;
}