package cn.iocoder.yudao.module.his.controller.admin.prepayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预交金 Response VO
 */
@Schema(description = "管理后台 - 预交金 Response VO")
@Data
public class HisPrepaymentRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

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

    @Schema(description = "预交金额", example = "1000.00")
    private BigDecimal amount;

    @Schema(description = "剩余金额", example = "800.00")
    private BigDecimal balanceAmount;

    @Schema(description = "支付方式", example = "1")
    private Integer paymentType;

    @Schema(description = "支付方式名称", example = "现金")
    private String paymentTypeName;

    @Schema(description = "支付渠道编码", example = "WECHAT")
    private String paymentChannel;

    @Schema(description = "支付流水号", example = "WX202606180001")
    private String paymentNo;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "状态：1-已缴纳 2-已使用 3-已退还 4-已结算", example = "1")
    private Integer status;

    @Schema(description = "状态名称", example = "已缴纳")
    private String statusName;

    @Schema(description = "已使用金额", example = "200.00")
    private BigDecimal usedAmount;

    @Schema(description = "已退还金额", example = "0.00")
    private BigDecimal refundAmount;

    @Schema(description = "可用余额", example = "800.00")
    private BigDecimal availableBalance;

    @Schema(description = "操作员ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "李收费")
    private String operatorName;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
