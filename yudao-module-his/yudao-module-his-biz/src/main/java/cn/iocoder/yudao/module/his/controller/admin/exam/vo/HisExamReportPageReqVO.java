package cn.iocoder.yudao.module.his.controller.admin.exam.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 检查报告分页请求 VO
 */
@Schema(description = "管理后台 - HIS检查报告分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisExamReportPageReqVO extends PageParam {

    @Schema(description = "报告编号", example = "RP202606180001")
    private String reportNo;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "申请ID", example = "1")
    private Long requestId;

    @Schema(description = "检查类型:1-影像检查 2-检验检查 3-病理检查 4-功能检查", example = "1")
    private Integer examType;

    @Schema(description = "检查类别", example = "CT")
    private String examCategory;

    @Schema(description = "报告状态:1-待审核 2-已审核 3-已发布 4-已撤回", example = "2")
    private Integer reportStatus;

    @Schema(description = "报告时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] reportTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}