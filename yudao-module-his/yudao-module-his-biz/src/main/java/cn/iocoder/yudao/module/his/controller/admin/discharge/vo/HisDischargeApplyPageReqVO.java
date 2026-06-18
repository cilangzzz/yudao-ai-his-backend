package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 出院申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDischargeApplyPageReqVO extends PageParam {

    @Schema(description = "申请单号", example = "D202606180001")
    private String applyNo;

    @Schema(description = "住院号", example = "A202606170001")
    private String admissionNo;

    @Schema(description = "患者姓名（模糊查询）", example = "张三")
    private String patientName;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "状态: 1待审批/2已审批/3已驳回/4已出院/5已取消", example = "1")
    private Integer status;

    @Schema(description = "出院方式: 1医嘱出院/2自动出院/3转院/4死亡", example = "1")
    private Integer dischargeWay;

    @Schema(description = "出院日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] dischargeDate;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

}