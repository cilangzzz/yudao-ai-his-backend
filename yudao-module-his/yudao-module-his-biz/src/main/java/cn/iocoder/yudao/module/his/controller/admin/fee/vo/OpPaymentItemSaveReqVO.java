package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付明细保存 Request VO
 *
 * @author yudao
 */
@Schema(description = "管理后台 - 支付明细保存 Request VO")
@Data
public class OpPaymentItemSaveReqVO {

    @Schema(description = "主键ID（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "支付记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long paymentId;

    @Schema(description = "费用ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeId;

    @Schema(description = "费用明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long feeItemId;

    @Schema(description = "挂号ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "收费项目ID", example = "1")
    private Long chargeItemId;

    @Schema(description = "项目编码", example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称", example = "挂号费")
    private String itemName;

    @Schema(description = "项目类别", example = "1")
    private Integer itemCategory;

    @Schema(description = "规格", example = "盒")
    private String spec;

    @Schema(description = "计价单位", example = "次")
    private String unit;

    @Schema(description = "单价", example = "50.00")
    private BigDecimal unitPrice;

    @Schema(description = "数量", example = "1")
    private BigDecimal quantity;

    @Schema(description = "费用明细金额", example = "50.00")
    private BigDecimal feeAmount;

    @Schema(description = "本次支付金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "50.00")
    private BigDecimal payAmount;

    @Schema(description = "优惠金额", example = "0.00")
    private BigDecimal discountAmount;

    @Schema(description = "医保支付金额", example = "0.00")
    private BigDecimal insuranceAmount;

    @Schema(description = "自付金额", example = "50.00")
    private BigDecimal selfAmount;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer payType;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保卡号", example = "1234567890")
    private String insuranceNo;

    @Schema(description = "医保编码", example = "YB001")
    private String insuranceCode;

    @Schema(description = "医保类别", example = "1")
    private Integer insuranceCategory;

    @Schema(description = "支付状态", example = "1")
    private Integer payStatus;

    @Schema(description = "执行科室ID", example = "1")
    private Long executionDeptId;

    @Schema(description = "执行科室名称", example = "内科")
    private String executionDeptName;

    @Schema(description = "开单医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "开单医生姓名", example = "李医生")
    private String doctorName;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}
