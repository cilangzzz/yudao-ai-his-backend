package cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 预住院记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisPreAdmissionPageReqVO extends PageParam {

    @Schema(description = "预住院编号", example = "P001")
    private String preAdmissionNo;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "预约科室ID", example = "1")
    private Long deptId;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "预约入院日期开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime scheduledDateStart;

    @Schema(description = "预约入院日期结束")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime scheduledDateEnd;
}