package cn.iocoder.yudao.module.his.controller.admin.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 医生排班响应 VO
 */
@Schema(description = "管理后台 - HIS医生排班 Response VO")
@Data
public class OpScheduleRespVO {

    @Schema(description = "排班ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long doctorId;

    @Schema(description = "医生姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李医生")
    private String doctorName;

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long deptId;

    @Schema(description = "科室名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "内科")
    private String deptName;

    @Schema(description = "排班日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-06-17")
    private LocalDate scheduleDate;

    @Schema(description = "时段：1-上午 2-下午 3-晚上", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer timePeriod;

    @Schema(description = "挂号类型：1-普通 2-专家 3-急诊", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer registerType;

    @Schema(description = "总号源数", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer totalQuota;

    @Schema(description = "已用号源数", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer usedQuota;

    @Schema(description = "剩余号源数", example = "15")
    private Integer remainingQuota;

    @Schema(description = "状态：1-正常 2-停诊 3-已满", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "诊查费", example = "20.00")
    private BigDecimal consultationFee;

    @Schema(description = "挂号费", example = "10.00")
    private BigDecimal registerFee;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}