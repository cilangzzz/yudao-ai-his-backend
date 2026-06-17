package cn.iocoder.yudao.module.his.controller.admin.appointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 预约挂号保存请求 VO
 */
@Schema(description = "管理后台 - HIS预约挂号保存 Request VO")
@Data
public class OpAppointmentSaveReqVO {

    @Schema(description = "编号（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "排班ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "排班ID不能为空")
    private Long scheduleId;

    @Schema(description = "预约日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-06-20")
    @NotNull(message = "预约日期不能为空")
    private LocalDate appointmentDate;

    @Schema(description = "时段：1-上午 2-下午 3-晚上", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "时段不能为空")
    private Integer timePeriod;

    @Schema(description = "挂号类型：1-普通 2-专家 3-急诊", example = "1")
    private Integer registerType;

    @Schema(description = "来源：1-现场 2-微信 3-APP 4-网站 5-电话", example = "1")
    private Integer sourceType;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}
