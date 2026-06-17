package cn.iocoder.yudao.module.his.controller.admin.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 医生排班保存请求 VO
 */
@Schema(description = "管理后台 - HIS医生排班保存 Request VO")
@Data
public class OpScheduleSaveReqVO {

    @Schema(description = "编号（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医生ID不能为空")
    private Long doctorId;

    @Schema(description = "医生姓名", example = "张医生")
    private String doctorName;

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "科室ID不能为空")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "排班日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2026-06-20")
    @NotNull(message = "排班日期不能为空")
    private LocalDate scheduleDate;

    @Schema(description = "时段：1-上午 2-下午 3-晚上", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "时段不能为空")
    private Integer timePeriod;

    @Schema(description = "挂号类型：1-普通 2-专家 3-急诊", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "挂号类型不能为空")
    private Integer registerType;

    @Schema(description = "总号源数", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    @NotNull(message = "总号源数不能为空")
    private Integer totalQuota;

    @Schema(description = "已用号源数", example = "0")
    private Integer usedQuota;

    @Schema(description = "状态：1-正常 2-停诊 3-已满", example = "1")
    private Integer status;

    @Schema(description = "诊查费", example = "50.00")
    private BigDecimal consultationFee;

    @Schema(description = "挂号费", example = "10.00")
    private BigDecimal registerFee;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}
