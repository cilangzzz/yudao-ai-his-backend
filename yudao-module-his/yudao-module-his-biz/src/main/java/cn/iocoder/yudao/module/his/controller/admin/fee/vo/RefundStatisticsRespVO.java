package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 退费统计 Response VO
 */
@Schema(description = "管理后台 - 退费统计 Response VO")
@Data
public class RefundStatisticsRespVO {

    @Schema(description = "退费笔数", example = "8")
    private Integer refundCount;

    @Schema(description = "退费总金额", example = "1230.00")
    private BigDecimal totalRefundAmount;

    @Schema(description = "医保退费金额", example = "800.00")
    private BigDecimal insuranceRefundAmount;

    @Schema(description = "自费退费金额", example = "430.00")
    private BigDecimal selfRefundAmount;

    @Schema(description = "待审核退费笔数", example = "2")
    private Integer pendingCount;

    @Schema(description = "待审核退费金额", example = "200.00")
    private BigDecimal pendingAmount;

    @Schema(description = "已完成退费笔数", example = "6")
    private Integer completedCount;

    @Schema(description = "已完成退费金额", example = "1030.00")
    private BigDecimal completedAmount;

    @Schema(description = "已拒绝退费笔数", example = "1")
    private Integer rejectedCount;

    @Schema(description = "全额退费笔数", example = "4")
    private Integer fullRefundCount;

    @Schema(description = "部分退费笔数", example = "4")
    private Integer partialRefundCount;

}