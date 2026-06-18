package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院费用明细保存请求 VO
 */
@Data
@ApiModel("住院费用明细保存请求 VO")
public class HisInpatientFeeSaveReqVO {

    @ApiModelProperty(value = "费用明细ID（更新时必填）", example = "1")
    private Long id;

    @ApiModelProperty(value = "入院记录ID", required = true, example = "100")
    @NotNull(message = "入院记录ID不能为空")
    private Long admissionId;

    @ApiModelProperty(value = "患者ID", required = true, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名", example = "张三")
    private String patientName;

    @ApiModelProperty(value = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @ApiModelProperty(value = "医嘱ID", example = "1")
    private Long orderId;

    @ApiModelProperty(value = "医嘱编号", example = "ORD20240001")
    private String orderNo;

    @ApiModelProperty(value = "项目编码", required = true, example = "ITEM001")
    @NotNull(message = "项目编码不能为空")
    private String itemCode;

    @ApiModelProperty(value = "项目名称", required = true, example = "检查费")
    @NotNull(message = "项目名称不能为空")
    private String itemName;

    @ApiModelProperty(value = "项目类型", required = true, example = "1")
    @NotNull(message = "项目类型不能为空")
    private Integer itemType;

    @ApiModelProperty(value = "项目分类", example = "检查")
    private String itemCategory;

    @ApiModelProperty(value = "规格", example = "500mg")
    private String spec;

    @ApiModelProperty(value = "单位", example = "次")
    private String unit;

    @ApiModelProperty(value = "数量", required = true, example = "1")
    @NotNull(message = "数量不能为空")
    private BigDecimal quantity;

    @ApiModelProperty(value = "单价", required = true, example = "100.00")
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "优惠金额", example = "10.00")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "医保编码", example = "YB001")
    private String insuranceCode;

    @ApiModelProperty(value = "医保类型", example = "1")
    private Integer insuranceType;

    @ApiModelProperty(value = "医保报销比例", example = "80")
    private BigDecimal insuranceRatio;

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

    @ApiModelProperty(value = "费用日期")
    private LocalDateTime feeDate;

    @ApiModelProperty(value = "备注", example = "备注信息")
    private String remark;

}