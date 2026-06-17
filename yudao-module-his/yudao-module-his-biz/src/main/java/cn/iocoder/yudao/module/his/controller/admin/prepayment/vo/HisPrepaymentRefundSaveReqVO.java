package cn.iocoder.yudao.module.his.controller.admin.prepayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 预交金退还新增/修改 Request VO
 */
@Schema(description = "管理后台 - 预交金退还新增/修改 Request VO")
@Data
public class HisPrepaymentRefundSaveReqVO {

    @Schema(description = "主键ID（修改时需要）", example = "1")
    private Long id;

    @Schema(description = "预交金记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "预交金记录不能为空")
    private Long prepaymentId;

    @Schema(description = "入院记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "入院记录不能为空")
    private Long admissionId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "退还金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "500.00")
    @NotNull(message = "退还金额不能为空")
    @DecimalMin(value = "0.01", message = "退还金额必须大于0")
    private BigDecimal refundAmount;

    @Schema(description = "退还原因", example = "出院结算退余额")
    private String refundReason;

    @Schema(description = "退还方式：1-现金 2-银行卡 3-原路退回", example = "1")
    private Integer refundType;

    @Schema(description = "退还渠道", example = "WECHAT")
    private String refundChannel;

    @Schema(description = "申请人ID", example = "1")
    private Long operatorId;

    @Schema(description = "申请人姓名", example = "李收费")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}
