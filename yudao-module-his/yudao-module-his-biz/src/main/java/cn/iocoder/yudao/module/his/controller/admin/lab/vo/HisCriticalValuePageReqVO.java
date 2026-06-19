package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 危急值分页查询 VO
 */
@Schema(description = "管理后台 - 危急值分页查询")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisCriticalValuePageReqVO extends PageParam {

    @Schema(description = "患者ID")
    private Long patientId;

    @Schema(description = "科室ID")
    private Long deptId;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "检出时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] detectedTime;

}