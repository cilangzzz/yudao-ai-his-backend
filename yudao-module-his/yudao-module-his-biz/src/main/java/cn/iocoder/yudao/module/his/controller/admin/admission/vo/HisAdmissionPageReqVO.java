package cn.iocoder.yudao.module.his.controller.admin.admission.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 入院记录分页请求 VO
 */
@Schema(description = "管理后台 - 入院记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisAdmissionPageReqVO extends PageParam {

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "入院科室ID", example = "1")
    private Long admissionDept;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "床位ID", example = "1")
    private Long bedId;

    @Schema(description = "入院状态：1-在院 2-出院 3-转科", example = "1")
    private Integer admissionStatus;

    @Schema(description = "入院日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] admissionDate;

}
