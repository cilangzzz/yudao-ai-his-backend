package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 支付记录 分页 Request VO
 */
@Schema(description = "管理后台 - 支付记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpPaymentPageReqVO extends PageParam {

    @Schema(description = "支付单号", example = "P202606180001")
    private String paymentNo;

    @Schema(description = "费用ID", example = "1")
    private Long feeId;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名，模糊匹配", example = "张三")
    private String patientName;

    @Schema(description = "支付方式", example = "1")
    private Integer payType;

    @Schema(description = "支付状态", example = "1")
    private Integer payStatus;

    @Schema(description = "收费员ID", example = "1")
    private Long cashierId;

    @Schema(description = "发票号", example = "INV001")
    private String invoiceNo;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

}