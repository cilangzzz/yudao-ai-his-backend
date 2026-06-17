package cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 退药申请 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 退药申请新增/修改 Request VO")
@Data
public class OpDrugReturnSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "退药类型：1-门诊退药 2-住院退药", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "退药类型不能为空")
    private Integer returnType;

    @Schema(description = "原处方ID")
    private Long prescriptionId;

    @Schema(description = "原处方编号")
    private String prescriptionNo;

    @Schema(description = "原发药ID")
    private Long dispenseId;

    @Schema(description = "原发药单号")
    private String dispenseNo;

    @Schema(description = "挂号ID（门诊）")
    private Long registerId;

    @Schema(description = "住院ID（住院）")
    private Long admissionId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "身份证号", example = "110101199001011234")
    private String idCardNo;

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "科室ID不能为空")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "开单医生ID")
    private Long doctorId;

    @Schema(description = "开单医生姓名")
    private String doctorName;

    @Schema(description = "药房ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "药房ID不能为空")
    private Long pharmacyId;

    @Schema(description = "药房名称", example = "门诊药房")
    private String pharmacyName;

    @Schema(description = "退药原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "药品不良反应，需要停药")
    @NotEmpty(message = "退药原因不能为空")
    private String returnReason;

    @Schema(description = "退药原因类型：1-药品不良反应 2-医嘱变更 3-患者拒服 4-药品质量问题 5-重复开药 6-其他", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "退药原因类型不能为空")
    private Integer returnReasonType;

    @Schema(description = "附件URL（药品照片等）")
    private String attachmentUrl;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "退药明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "退药明细不能为空")
    @Valid
    private List<Item> items;

    /**
     * 退药明细项
     */
    @Schema(description = "退药明细项")
    @Data
    public static class Item {

        @Schema(description = "原处方明细ID")
        private Long prescriptionItemId;

        @Schema(description = "原发药明细ID")
        private Long dispenseItemId;

        @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "药品ID不能为空")
        private Long drugId;

        @Schema(description = "药品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "YP001")
        @NotEmpty(message = "药品编码不能为空")
        private String drugCode;

        @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
        @NotEmpty(message = "药品名称不能为空")
        private String drugName;

        @Schema(description = "药品规格", example = "0.5g*24粒")
        private String drugSpec;

        @Schema(description = "生产厂家", example = "XX制药")
        private String manufacturer;

        @Schema(description = "批号")
        private String batchNo;

        @Schema(description = "有效期")
        private LocalDate expiryDate;

        @Schema(description = "原发药数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
        @NotNull(message = "原发药数量不能为空")
        private BigDecimal originalQuantity;

        @Schema(description = "退药数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
        @NotNull(message = "退药数量不能为空")
        private BigDecimal returnQuantity;

        @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "盒")
        @NotEmpty(message = "单位不能为空")
        private String unit;

        @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "25.50")
        @NotNull(message = "单价不能为空")
        private BigDecimal unitPrice;

        @Schema(description = "药品状态：1-完好 2-包装破损 3-过期 4-其他", example = "1")
        private Integer drugCondition;

        @Schema(description = "药品状态说明")
        private String drugConditionRemark;

        @Schema(description = "是否可再利用：0-否 1-是", example = "1")
        private Integer canReuse;

        @Schema(description = "退药原因")
        private String returnReason;

        @Schema(description = "排序号", example = "1")
        private Integer sortOrder;

        @Schema(description = "备注")
        private String remark;

    }

}
