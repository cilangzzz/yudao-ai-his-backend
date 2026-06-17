package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 护理交接班分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HisNursingHandoverPageReqVO extends PageParam {

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "交班护士ID", example = "1")
    private Long handoverNurseId;

    @Schema(description = "接班护士ID", example = "2")
    private Long takeoverNurseId;

    @Schema(description = "班次类型", example = "1")
    private Integer shiftType;

    @Schema(description = "交接班状态", example = "0")
    private Integer status;

    @Schema(description = "交班时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] handoverTime;
}