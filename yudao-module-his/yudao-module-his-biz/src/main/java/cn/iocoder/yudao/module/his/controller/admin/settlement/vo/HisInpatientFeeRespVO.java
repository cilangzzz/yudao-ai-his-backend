package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院费用明细响应 VO
 */
@Data
@ApiModel("住院费用明细响应 VO")
public class HisInpatientFeeRespVO {

    @ApiModelProperty(value = "费用明细ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "费用单号", example = "FEE202406180001")
    private String feeNo;

    @ApiModelProperty(value = "入院记录ID", example = "100")
    private Long admissionId;

    @ApiModelProperty(value = "患者ID", example = "1")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名", example = "张三")
    private String patientName;

    @ApiModelProperty(value = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @ApiModelProperty(value = "医嘱ID", example = "1")
    private Long orderId;

    @ApiModelProperty(value = "医嘱编号", example = "ORD20240001")
    private String orderNo;

    @ApiModelProperty(value = "项目编码", example = "ITEM001")
    private String itemCode;

    @ApiModelProperty(value = "项目名称", example = "检查费")
    private String itemName;

    @ApiModelProperty(value = "项目类型", example = "1")
    private Integer itemType;

    @ApiModelProperty(value = "项目分类", example = "检查")
    private String itemCategory;

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

    @ApiModelProperty(value = "执行科室ID", example = "10")
    private Long deptId;

    @ApiModelProperty(value = "执行科室名称", example = "内科")
    private String deptName;

    @ApiModelProperty(value = "开单医生ID", example = "100")
    private Long doctorId;

    @ApiModelProperty(value = "开单医生姓名", example = "王医生")
    private String doctorName;

    @ApiModelProperty(value = "执行时间")
    private LocalDateTime executeTime;

    @ApiModelProperty(value = "费用状态", example = "0")
    private Integer feeStatus;

    @ApiModelProperty(value = "结算单ID", example = "1")
    private Long settlementId;

    @ApiModelProperty(value = "费用日期")
    private LocalDateTime feeDate;

    @ApiModelProperty(value = "备注", example = "备注信息")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}