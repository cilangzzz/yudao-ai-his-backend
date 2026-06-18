package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 住院结算分页查询 Request VO
 */
@Schema(description = "管理后台 - 住院结算分页查询 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisInpatientSettlementPageReqVO extends PageParam {

    @Schema(description = "结算单号", example = "JS20240101001")
    private String settlementNo;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院号", example = "ZY20240101001")
    private String inpatientNo;

    @Schema(description = "入院记录ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "结算状态", example = "1")
    private Integer settlementStatus;

    @Schema(description = "结算类型", example = "1")
    private Integer settlementType;

    @Schema(description = "结算时间-开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime settlementTimeStart;

    @Schema(description = "结算时间-结束")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime settlementTimeEnd;

    @Schema(description = "入院日期-开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime admissionDateStart;

    @Schema(description = "入院日期-结束")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime admissionDateEnd;

    @Schema(description = "出院日期-开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime dischargeDateStart;

    @Schema(description = "出院日期-结束")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime dischargeDateEnd;

}
