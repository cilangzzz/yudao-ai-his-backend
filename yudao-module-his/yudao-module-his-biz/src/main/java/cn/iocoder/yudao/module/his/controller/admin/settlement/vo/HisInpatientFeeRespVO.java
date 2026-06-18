package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院费用明细响应 VO
 */
@Data
@Schema(description = "管理后台 - 住院费用明细响应 VO")
public class HisInpatientFeeRespVO {

    @Schema(description = "费用明细ID", example = "1")
    private Long id;

    @Schema(description = "费用单号", example = "FEE202406180001")
    private String feeNo;

    @Schema(description = "入院记录ID", example = "100")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @Schema(description = "医嘱ID", example = "1")
    private Long orderId;

    @Schema(description = "医嘱编号", example = "ORD20240001")
    private String orderNo;

    @Schema(description = "项目编码", example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称", example = "检查费")
    private String itemName;

    @Schema(description = "项目类型", example = "1")
    private Integer itemType;

    @Schema(description = "项目分类", example = "检查")
    private String itemCategory;

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

    @Schema(description = "执行科室ID", example = "10")
    private Long deptId;

    @Schema(description = "执行科室名称", example = "内科")
    private String deptName;

    @Schema(description = "开单医生ID", example = "100")
    private Long doctorId;

    @Schema(description = "开单医生姓名", example = "王医生")
    private String doctorName;

    @Schema(description = "执行时间")
    private LocalDateTime executeTime;

    @Schema(description = "费用状态", example = "0")
    private Integer feeStatus;

    @Schema(description = "结算单ID", example = "1")
    private Long settlementId;

    @Schema(description = "费用日期")
    private LocalDateTime feeDate;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}