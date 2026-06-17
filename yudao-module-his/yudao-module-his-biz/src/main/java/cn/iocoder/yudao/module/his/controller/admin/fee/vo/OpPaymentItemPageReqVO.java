package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 支付明细分页 Request VO
 *
 * @author yudao
 */
@Schema(description = "管理后台 - 支付明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OpPaymentItemPageReqVO extends PageParam {

    @Schema(description = "支付记录ID", example = "1")
    private Long paymentId;

    @Schema(description = "费用ID", example = "1")
    private Long feeId;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "项目名称", example = "挂号费")
    private String itemName;

    @Schema(description = "项目类别", example = "1")
    private Integer itemCategory;

    @Schema(description = "支付方式", example = "1")
    private Integer payType;

    @Schema(description = "支付状态", example = "1")
    private Integer payStatus;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

}
