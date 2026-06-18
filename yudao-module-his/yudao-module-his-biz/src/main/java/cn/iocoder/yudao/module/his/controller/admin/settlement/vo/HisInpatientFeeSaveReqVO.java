package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住院费用明细保存请求 VO
 */
@Data
@Schema(description = "管理后台 - 住院费用明细保存请求 VO")
public class HisInpatientFeeSaveReqVO {

    @Schema(description = "费用明细ID（更新时必填）", example = "1")
    private Long id;

    @Schema(description = "入院记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "入院记录ID不能为空")
    private Long admissionId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @Schema(description = "医嘱ID", example = "1")
    private Long orderId;

    @Schema(description = "医嘱编号", example = "ORD20240001")
    private String orderNo;

    @Schema(description = "项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "ITEM001")
    @NotNull(message = "项目编码不能为空")
    private String itemCode;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "检查费")
    @NotNull(message = "项目名称不能为空")
    private String itemName;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "项目类型不能为空")
    private Integer itemType;

    @Schema(description = "项目分类", example = "检查")
    private String itemCategory;

    @Schema(description = "规格", example = "500mg")
    private String spec;

    @Schema(description = "单位", example = "次")
    private String unit;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;

    @Schema(description = "优惠金额", example = "10.00")
    private BigDecimal discountAmount;

    @Schema(description = "医保编码", example = "YB001")
    private String insuranceCode;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保报销比例", example = "80")
    private BigDecimal insuranceRatio;

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

    @Schema(description = "费用日期")
    private LocalDateTime feeDate;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}