package cn.iocoder.yudao.module.his.controller.admin.admission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 入院登记创建/修改 Request VO")
@Data
public class HisAdmissionSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者不能为空")
    private Long patientId;

    @Schema(description = "关联门诊挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "入院日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "入院日期不能为空")
    private LocalDateTime admissionDate;

    @Schema(description = "入院科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "入院科室不能为空")
    private Long admissionDept;

    @Schema(description = "科室名称", example = "内科")
    private String admissionDeptName;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "床位ID", example = "1")
    private Long bedId;

    @Schema(description = "床号", example = "01")
    private String bedNo;

    @Schema(description = "入院诊断", example = "肺炎")
    private String admissionDiagnosis;

    @Schema(description = "入院诊断ICD-10编码", example = "J18.9")
    private String diagnosisCode;

    @Schema(description = "主治医师ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "主治医师不能为空")
    private Long attendingDoctor;

    @Schema(description = "主治医师姓名", example = "李医生")
    private String attendingDoctorName;

    @Schema(description = "住院医师ID", example = "2")
    private Long residentDoctor;

    @Schema(description = "住院医师姓名", example = "王医生")
    private String residentDoctorName;

    @Schema(description = "责任护士ID", example = "3")
    private Long nurseId;

    @Schema(description = "责任护士姓名", example = "张护士")
    private String nurseName;

    @Schema(description = "入院方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "入院方式不能为空")
    private Integer admissionWay;

    @Schema(description = "入院情况", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @NotNull(message = "入院情况不能为空")
    private Integer admissionCondition;

    @Schema(description = "医保类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保号", example = "123456")
    private String insuranceNo;

    @Schema(description = "预交金", example = "1000.00")
    private BigDecimal depositAmount;

    @Schema(description = "备注", example = "备注信息")
    private String remark;
}