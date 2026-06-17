package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 门诊费用 Response VO
 */
@Schema(description = "管理后台 - 门诊费用 Response VO")
@Data
public class OpFeeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "费用单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "F202606180001")
    private String feeNo;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    private BigDecimal totalAmount;

    @Schema(description = "优惠金额", example = "10.00")
    private BigDecimal discountAmount;

    @Schema(description = "应付金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "90.00")
    private BigDecimal payAmount;

    @Schema(description = "医保支付金额", example = "50.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "40.00")
    private BigDecimal selfAmount;

    @Schema(description = "费用状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer feeStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "费用明细列表")
    private List<OpFeeItemRespVO> items;

}