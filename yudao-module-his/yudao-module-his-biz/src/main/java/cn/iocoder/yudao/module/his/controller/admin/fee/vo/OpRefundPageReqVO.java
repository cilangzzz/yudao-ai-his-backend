package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 退费记录 分页 Request VO
 */
@Schema(description = "管理后台 - 退费记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpRefundPageReqVO extends PageParam {

    @Schema(description = "退费单号", example = "R202606180001")
    private String refundNo;

    @Schema(description = "原支付ID", example = "1")
    private Long paymentId;

    @Schema(description = "费用ID", example = "1")
    private Long feeId;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名，模糊匹配", example = "张三")
    private String patientName;

    @Schema(description = "退费状态", example = "1")
    private Integer refundStatus;

    @Schema(description = "申请人ID", example = "1")
    private Long applyBy;

    @Schema(description = "审核人ID", example = "1")
    private Long auditBy;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

}