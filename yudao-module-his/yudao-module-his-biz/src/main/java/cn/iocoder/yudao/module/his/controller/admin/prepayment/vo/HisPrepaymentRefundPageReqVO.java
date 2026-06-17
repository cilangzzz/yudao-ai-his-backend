package cn.iocoder.yudao.module.his.controller.admin.prepayment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 预交金退还分页 Request VO
 */
@Schema(description = "管理后台 - 预交金退还分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisPrepaymentRefundPageReqVO extends PageParam {

    @Schema(description = "退还编号", example = "RF202606180001")
    private String refundNo;

    @Schema(description = "预交金记录ID", example = "1")
    private Long prepaymentId;

    @Schema(description = "入院记录ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "状态：1-申请中 2-已审批 3-已退还 4-已拒绝", example = "1")
    private Integer status;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

}
