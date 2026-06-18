package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 住院结算明细响应 VO
 */
@Data
@ApiModel("住院结算明细响应 VO")
public class HisInpatientSettlementItemRespVO {

    @ApiModelProperty(value = "结算明细ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "结算单ID", example = "1")
    private Long settlementId;

    @ApiModelProperty(value = "结算单号", example = "JS202406180001")
    private String settlementNo;

    @ApiModelProperty(value = "费用明细ID", example = "1")
    private Long feeId;

    @ApiModelProperty(value = "费用单号", example = "FEE202406180001")
    private String feeNo;

    @ApiModelProperty(value = "入院记录ID", example = "100")
    private Long admissionId;

    @ApiModelProperty(value = "患者ID", example = "1")
    private Long patientId;

    @ApiModelProperty(value = "项目编码", example = "ITEM001")
    private String itemCode;

    @ApiModelProperty(value = "项目名称", example = "检查费")
    private String itemName;

    @ApiModelProperty(value = "项目类型", example = "1")
    private Integer itemType;

    @ApiModelProperty(value = "规格", example = "500mg")
    private String spec;

    @ApiModelProperty(value = "单位", example = "次")
    private String unit;

    @ApiModelProperty(value = "数量", example = "1")
    private BigDecimal quantity;

    @ApiModelProperty(value = "单价", example = "100.00")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "总金额", example = "100.00")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "优惠金额", example = "10.00")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "应付金额", example = "90.00")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "医保编码", example = "YB001")
    private String insuranceCode;

    @ApiModelProperty(value = "医保类型", example = "1")
    private Integer insuranceType;

    @ApiModelProperty(value = "医保报销比例", example = "80")
    private BigDecimal insuranceRatio;

    @ApiModelProperty(value = "医保支付金额", example = "72.00")
    private BigDecimal insuranceAmount;

    @ApiModelProperty(value = "自付金额", example = "18.00")
    private BigDecimal selfAmount;

    @ApiModelProperty(value = "费用日期", example = "2024-06-18")
    private LocalDate feeDate;

    @ApiModelProperty(value = "备注", example = "备注信息")
    private String remark;

}