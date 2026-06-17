package cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预住院创建/修改 Request VO")
@Data
public class HisPreAdmissionSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "患者不能为空")
    private Long patientId;

    @Schema(description = "关联门诊挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "预约科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "预约科室不能为空")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "预约入院日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预约入院日期不能为空")
    private LocalDateTime scheduledDate;

    @Schema(description = "预约诊断", example = "肺炎")
    private String diagnosis;

    @Schema(description = "诊断编码", example = "J18.9")
    private String diagnosisCode;

    @Schema(description = "主治医师ID", example = "1")
    private Long doctorId;

    @Schema(description = "主治医师姓名", example = "李医生")
    private String doctorName;

    @Schema(description = "预交金", example = "1000.00")
    private BigDecimal depositAmount;

    @Schema(description = "备注", example = "备注信息")
    private String remark;
}