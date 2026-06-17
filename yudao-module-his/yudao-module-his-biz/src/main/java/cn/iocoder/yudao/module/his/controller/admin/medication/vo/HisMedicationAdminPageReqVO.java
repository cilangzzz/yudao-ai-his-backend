package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 给药记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HisMedicationAdminPageReqVO extends PageParam {

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "医嘱ID", example = "1")
    private Long orderId;

    @Schema(description = "执行护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "药品ID", example = "1")
    private Long drugId;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "预定执行时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] scheduledTime;
}