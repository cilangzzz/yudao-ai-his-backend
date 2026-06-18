package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 出院小结分页查询 Request VO
 */
@Schema(description = "管理后台 - 出院小结分页查询 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HisDischargeSummaryPageReqVO extends PageParam {

    @Schema(description = "出院ID", example = "1")
    private Long dischargeId;

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "出院诊断", example = "肺炎")
    private String dischargeDiagnosis;

    @Schema(description = "书写医生ID", example = "1")
    private Long authorDoctor;

    @Schema(description = "审核医生ID", example = "1")
    private Long reviewDoctor;

    @Schema(description = "状态：1-待审核 2-已审核", example = "1")
    private Integer summaryStatus;

    @Schema(description = "书写时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime authorTimeBegin;

    @Schema(description = "书写时间-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime authorTimeEnd;

    @Schema(description = "审核时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewTimeBegin;

    @Schema(description = "审核时间-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewTimeEnd;

    @Schema(description = "创建时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeBegin;

    @Schema(description = "创建时间-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;
}