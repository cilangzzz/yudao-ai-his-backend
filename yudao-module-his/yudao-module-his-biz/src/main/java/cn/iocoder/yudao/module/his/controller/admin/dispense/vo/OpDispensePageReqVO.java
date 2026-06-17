package cn.iocoder.yudao.module.his.controller.admin.dispense.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 发药记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OpDispensePageReqVO extends PageParam {

    @Schema(description = "发药单号", example = "DS202606180001")
    private String dispenseNo;

    @Schema(description = "处方ID", example = "1")
    private Long prescriptionId;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名（模糊）", example = "张三")
    private String patientName;

    @Schema(description = "药房ID", example = "1")
    private Long pharmacyId;

    @Schema(description = "发药状态：1-待调配 2-已调配 3-已发药 4-已退药", example = "1")
    private Integer dispenseStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
