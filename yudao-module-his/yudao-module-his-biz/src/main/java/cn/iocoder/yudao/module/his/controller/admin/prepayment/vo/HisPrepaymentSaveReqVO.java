package cn.iocoder.yudao.module.his.controller.admin.prepayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 预交金新增/修改 Request VO
 */
@Schema(description = "管理后台 - 预交金新增/修改 Request VO")
@Data
public class HisPrepaymentSaveReqVO {

    @Schema(description = "主键ID（修改时需要）", example = "1")
    private Long id;

    @Schema(description = "入院记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "入院记录不能为空")
    private Long admissionId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院号", example = "IN202606180001")
    private String inpatientNo;

    @Schema(description = "预交金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000.00")
    @NotNull(message = "预交金额不能为空")
    @DecimalMin(value = "0.01", message = "预交金额必须大于0")
    private BigDecimal amount;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "支付方式不能为空")
    private Integer paymentType;

    @Schema(description = "支付渠道编码", example = "WECHAT")
    private String paymentChannel;

    @Schema(description = "支付流水号", example = "WX202606180001")
    private String paymentNo;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "操作员ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "李收费")
    private String operatorName;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}
