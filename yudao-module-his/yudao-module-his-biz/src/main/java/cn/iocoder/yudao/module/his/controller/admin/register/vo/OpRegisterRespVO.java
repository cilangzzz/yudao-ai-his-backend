package cn.iocoder.yudao.module.his.controller.admin.register.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 挂号响应 VO
 */
@Schema(description = "管理后台 - HIS挂号 Response VO")
@Data
public class OpRegisterRespVO {

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "挂号编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "R202606170001")
    private String registerNo;

    @Schema(description = "预约ID", example = "1")
    private Long appointmentId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String patientName;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "身份证号", example = "320102199001011234")
    private String idCardNo;

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

    @Schema(description = "挂号日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-06-17")
    private LocalDate registerDate;

    @Schema(description = "时段：1-上午 2-下午 3-晚上", example = "1")
    private Integer timePeriod;

    @Schema(description = "排队序号", example = "1")
    private Integer queueNo;

    @Schema(description = "挂号类型：1-普通 2-专家 3-急诊", example = "1")
    private Integer registerType;

    @Schema(description = "来源：1-现场 2-微信 3-APP 4-网站 5-自助机", example = "1")
    private Integer sourceType;

    @Schema(description = "挂号费", example = "10.00")
    private BigDecimal registerFee;

    @Schema(description = "诊查费", example = "20.00")
    private BigDecimal consultationFee;

    @Schema(description = "总费用", example = "30.00")
    private BigDecimal totalFee;

    @Schema(description = "支付状态：0-未支付 1-已支付 2-已退费", example = "0")
    private Integer payStatus;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付方式：1-现金 2-微信 3-支付宝 4-医保", example = "1")
    private Integer payType;

    @Schema(description = "挂号状态：1-候诊 2-就诊中 3-已完成 4-已退号", example = "1")
    private Integer registerStatus;

    @Schema(description = "就诊时间")
    private LocalDateTime visitTime;

    @Schema(description = "就诊结束时间")
    private LocalDateTime endTime;

    @Schema(description = "退费金额", example = "30.00")
    private BigDecimal refundAmount;

    @Schema(description = "退号时间")
    private LocalDateTime refundTime;

    @Schema(description = "退号操作人", example = "1")
    private Long refundBy;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}