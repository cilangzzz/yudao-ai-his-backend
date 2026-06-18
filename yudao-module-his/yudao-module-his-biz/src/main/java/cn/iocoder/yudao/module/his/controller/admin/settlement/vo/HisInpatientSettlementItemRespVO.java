package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 住院结算明细响应 VO
 */
@Data
@Schema(description = "管理后台 - 住院结算明细响应 VO")
public class HisInpatientSettlementItemRespVO {

    @Schema(description = "结算明细ID", example = "1")
    private Long id;

    @Schema(description = "结算单ID", example = "1")
    private Long settlementId;

    @Schema(description = "结算单号", example = "JS202406180001")
    private String settlementNo;

    @Schema(description = "费用明细ID", example = "1")
    private Long feeId;

    @Schema(description = "费用单号", example = "FEE202406180001")
    private String feeNo;

    @Schema(description = "入院记录ID", example = "100")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "项目编码", example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称", example = "检查费")
    private String itemName;

    @Schema(description = "项目类型", example = "1")
    private Integer itemType;

    @Schema(description = "规格", example = "500mg")
    private String spec;

    @Schema(description = "单位", example = "次")
    private String unit;

    @Schema(description = "数量", example = "1")
    private BigDecimal quantity;

    @Schema(description = "单价", example = "100.00")
    private BigDecimal unitPrice;

    @Schema(description = "总金额", example = "100.00")
    private BigDecimal totalAmount;

    @Schema(description = "优惠金额", example = "10.00")
    private BigDecimal discountAmount;

    @Schema(description = "应付金额", example = "90.00")
    private BigDecimal payAmount;

    @Schema(description = "医保编码", example = "YB001")
    private String insuranceCode;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保报销比例", example = "80")
    private BigDecimal insuranceRatio;

    @Schema(description = "医保支付金额", example = "72.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "18.00")
    private BigDecimal selfAmount;

    @Schema(description = "费用日期", example = "2024-06-18")
    private LocalDate feeDate;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}