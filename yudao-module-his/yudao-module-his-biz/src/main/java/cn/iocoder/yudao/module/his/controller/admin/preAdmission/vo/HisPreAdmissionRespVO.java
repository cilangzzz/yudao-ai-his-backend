package cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预住院记录 Response VO")
@Data
public class HisPreAdmissionRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "预住院编号", example = "P001")
    private String preAdmissionNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "身份证号", example = "123456789012345678")
    private String idCardNo;

    @Schema(description = "关联门诊挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "预约科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "预约入院日期")
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

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "状态名称", example = "待入院")
    private String statusName;

    @Schema(description = "入院时间")
    private LocalDateTime admissionTime;

    @Schema(description = "入院记录ID", example = "1")
    private Long admissionId;

    @Schema(description = "取消原因", example = "患者主动取消")
    private String cancelReason;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}