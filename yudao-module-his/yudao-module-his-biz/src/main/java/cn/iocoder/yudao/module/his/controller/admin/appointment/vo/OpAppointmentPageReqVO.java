package cn.iocoder.yudao.module.his.controller.admin.appointment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 预约挂号分页请求 VO
 */
@Schema(description = "管理后台 - HIS预约挂号分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpAppointmentPageReqVO extends PageParam {

    @Schema(description = "预约编号", example = "AP202606170001")
    private String appointmentNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名",  example = "张三")
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

    @Schema(description = "预约日期", example = "2026-06-17")
    private LocalDate appointmentDate;

    @Schema(description = "时段：1-上午 2-下午 3-晚上", example = "1")
    private Integer timePeriod;

    @Schema(description = "挂号类型：1-普通 2-专家 3-急诊", example = "1")
    private Integer registerType;

    @Schema(description = "来源：1-现场 2-微信 3-APP 4-网站 5-电话", example = "1")
    private Integer sourceType;

    @Schema(description = "预约状态：1-已预约 2-已取号 3-已就诊 4-已取消 5-已过期", example = "1")
    private Integer appointmentStatus;

}
