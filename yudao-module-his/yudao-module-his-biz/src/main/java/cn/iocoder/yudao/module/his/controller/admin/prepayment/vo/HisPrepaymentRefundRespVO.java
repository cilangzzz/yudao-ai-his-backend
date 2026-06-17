package cn.iocoder.yudao.module.his.controller.admin.prepayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 预交金退还 Response VO
 */
@Schema(description = "管理后台 - 预交金退还 Response VO")
@Data
public class HisPrepaymentRefundRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "退还编号", example = "RF202606180001")
    private String refundNo;

    @Schema(description = "预交金记录ID", example = "1")
    private Long prepaymentId;

    @Schema(description = "预交金编号", example = "PP202606180001")
    private String prepaymentNo;

    @Schema(description = "入院记录ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "退还金额", example = "500.00")
    private BigDecimal refundAmount;

    @Schema(description = "退还原因", example = "出院结算退余额")
    private String refundReason;

    @Schema(description = "退还方式：1-现金 2-银行卡 3-原路退回", example = "1")
    private Integer refundType;

    @Schema(description = "退还方式名称", example = "现金")
    private String refundTypeName;

    @Schema(description = "退还渠道", example = "WECHAT")
    private String refundChannel;

    @Schema(description = "外部退还流水号", example = "WX202606180001")
    private String refundNoExternal;

    @Schema(description = "状态：1-申请中 2-已审批 3-已退还 4-已拒绝", example = "1")
    private Integer status;

    @Schema(description = "状态名称", example = "申请中")
    private String statusName;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "审批人ID", example = "1")
    private Long approveUserId;

    @Schema(description = "审批人姓名", example = "王主任")
    private String approveUserName;

    @Schema(description = "审批备注", example = "同意退还")
    private String approveRemark;

    @Schema(description = "退还时间")
    private LocalDateTime refundTime;

    @Schema(description = "申请人ID", example = "1")
    private Long operatorId;

    @Schema(description = "申请人姓名", example = "李收费")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
