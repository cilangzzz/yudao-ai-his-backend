package cn.iocoder.yudao.module.his.controller.admin.prescription.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 处方主表响应 VO
 */
@Schema(description = "管理后台 - HIS门诊处方 Response VO")
@Data
public class OpPrescriptionRespVO {

    @Schema(description = "处方ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "处方编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "RX202606180001")
    private String prescriptionNo;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String patientName;

    @Schema(description = "处方类型：1-普通 2-急诊 3-儿科 4-麻醉 5-精神 6-中药", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer prescriptionType;

    @Schema(description = "开方科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "开方医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "医生姓名", example = "李医生")
    private String doctorName;

    @Schema(description = "诊断编码（ICD-10）", example = "J06.9")
    private String diagnoseCode;

    @Schema(description = "诊断名称", example = "急性上呼吸道感染")
    private String diagnoseName;

    @Schema(description = "处方总金额", example = "150.50")
    private BigDecimal totalAmount;

    @Schema(description = "审方药师ID", example = "1")
    private Long pharmacistId;

    @Schema(description = "审方药师姓名", example = "王药师")
    private String pharmacistName;

    @Schema(description = "审方时间")
    private LocalDateTime auditTime;

    @Schema(description = "审方结果：1-通过 2-退回", example = "1")
    private Integer auditResult;

    @Schema(description = "审核意见", example = "处方合理")
    private String auditRemark;

    @Schema(description = "调配药师ID", example = "1")
    private Long dispensePharmacist;

    @Schema(description = "调配药师姓名", example = "赵药师")
    private String dispensePharmacistName;

    @Schema(description = "调配时间")
    private LocalDateTime dispenseTime;

    @Schema(description = "发药药师ID", example = "1")
    private Long sendPharmacist;

    @Schema(description = "发药药师姓名", example = "钱药师")
    private String sendPharmacistName;

    @Schema(description = "发药时间")
    private LocalDateTime sendTime;

    @Schema(description = "处方状态：1-开立 2-审核通过 3-审核退回 4-已调配 5-已发药 6-已退药", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer prescriptionStatus;

    @Schema(description = "有效天数", example = "3")
    private Integer validDays;

    @Schema(description = "备注", example = "饭后服用")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "处方明细列表")
    private List<OpPrescriptionItemRespVO> items;

}
