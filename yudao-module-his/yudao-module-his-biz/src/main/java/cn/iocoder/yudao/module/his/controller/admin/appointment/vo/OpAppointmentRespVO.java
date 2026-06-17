package cn.iocoder.yudao.module.his.controller.admin.appointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 预约挂号响应 VO
 */
@Schema(description = "管理后台 - HIS预约挂号 Response VO")
@Data
public class OpAppointmentRespVO {

    @Schema(description = "预约ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "预约编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "A202606170001")
    private String appointmentNo;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String patientName;

    @Schema(description = "排班ID", example = "1")
    private Long scheduleId;

    @Schema(description = "医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "医生姓名", example = "李医生")
    private String doctorName;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "预约日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-06-17")
    private LocalDate appointmentDate;

    @Schema(description = "时段：1-上午 2-下午 3-晚上", example = "1")
    private Integer timePeriod;

    @Schema(description = "排队序号", example = "1")
    private Integer queueNo;

    @Schema(description = "挂号类型：1-普通 2-专家 3-急诊", example = "1")
    private Integer registerType;

    @Schema(description = "来源：1-现场 2-微信 3-APP 4-网站 5-电话", example = "1")
    private Integer sourceType;

    @Schema(description = "预约状态：1-已预约 2-已取号 3-已就诊 4-已取消 5-已过期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer appointmentStatus;

    @Schema(description = "取消原因", example = "临时有事")
    private String cancelReason;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消人", example = "1")
    private Long cancelBy;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}