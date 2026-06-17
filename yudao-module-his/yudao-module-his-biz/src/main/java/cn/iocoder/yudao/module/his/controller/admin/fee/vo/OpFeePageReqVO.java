package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 门诊费用 分页 Request VO
 */
@Schema(description = "管理后台 - 门诊费用分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpFeePageReqVO extends PageParam {

    @Schema(description = "费用单号", example = "F202606180001")
    private String feeNo;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名，模糊匹配", example = "张三")
    private String patientName;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "费用状态", example = "0")
    private Integer feeStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}