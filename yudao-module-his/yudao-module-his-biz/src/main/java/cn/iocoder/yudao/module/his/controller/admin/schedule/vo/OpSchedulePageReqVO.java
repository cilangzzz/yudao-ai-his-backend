package cn.iocoder.yudao.module.his.controller.admin.schedule.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 医生排班分页请求 VO
 */
@Schema(description = "管理后台 - HIS医生排班分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpSchedulePageReqVO extends PageParam {

    @Schema(description = "医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "排班日期", example = "2026-06-17")
    private LocalDate scheduleDate;

    @Schema(description = "状态：1-正常 2-停诊 3-已满", example = "1")
    private Integer status;

}
