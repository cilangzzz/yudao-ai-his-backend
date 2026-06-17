package cn.iocoder.yudao.module.his.controller.admin.prepayment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 预交金分页 Request VO
 */
@Schema(description = "管理后台 - 预交金分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisPrepaymentPageReqVO extends PageParam {

    @Schema(description = "预交金编号", example = "PP202606180001")
    private String prepaymentNo;

    @Schema(description = "入院记录ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院号", example = "IN202606180001")
    private String inpatientNo;

    @Schema(description = "状态：1-已缴纳 2-已使用 3-已退还 4-已结算", example = "1")
    private Integer status;

    @Schema(description = "支付方式：1-现金 2-银行卡 3-医保卡 4-微信 5-支付宝 6-混合支付", example = "1")
    private Integer paymentType;

    @Schema(description = "支付时间开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime payTimeStart;

    @Schema(description = "支付时间结束")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime payTimeEnd;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
