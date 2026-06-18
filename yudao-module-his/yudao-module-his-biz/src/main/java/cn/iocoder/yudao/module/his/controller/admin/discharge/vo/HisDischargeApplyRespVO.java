package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 出院申请响应 VO")
@Data
public class HisDischargeApplyRespVO {

    @Schema(description = "出院申请ID", example = "1")
    private Long id;

    @Schema(description = "申请单号", example = "D202606180001")
    private String applyNo;

    @Schema(description = "入院记录ID", example = "1")
    private Long admissionId;

    @Schema(description = "住院号", example = "A202606170001")
    private String admissionNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "性别", example = "1")
    private String gender;

    @Schema(description = "年龄", example = "30")
    private Integer age;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "床位ID", example = "1")
    private Long bedId;

    @Schema(description = "床位号", example = "101")
    private String bedNo;

    @Schema(description = "主治医生ID", example = "1")
    private Long attendingDoctorId;

    @Schema(description = "主治医生姓名", example = "张医生")
    private String attendingDoctorName;

    @Schema(description = "入院日期", example = "2026-06-15T10:00:00")
    private LocalDateTime admissionDate;

    @Schema(description = "入院诊断", example = "肺炎")
    private String admissionDiagnosis;

    @Schema(description = "入院诊断编码", example = "J18.9")
    private String admissionDiagnosisCode;

    @Schema(description = "拟定出院日期", example = "2026-06-18T10:00:00")
    private LocalDateTime dischargeDate;

    @Schema(description = "出院方式", example = "1")
    private Integer dischargeWay;

    @Schema(description = "出院方式名称", example = "医嘱出院")
    private String dischargeWayName;

    @Schema(description = "出院诊断", example = "肺炎痊愈")
    private String dischargeDiagnosis;

    @Schema(description = "出院诊断编码", example = "J18.9")
    private String dischargeDiagnosisCode;

    @Schema(description = "出院诊断备注", example = "患者经过治疗后痊愈")
    private String dischargeDiagnosisRemark;

    @Schema(description = "治疗结果", example = "1")
    private Integer treatmentResult;

    @Schema(description = "治疗结果名称", example = "治愈")
    private String treatmentResultName;

    @Schema(description = "转院医院", example = "北京协和医院")
    private String transferHospital;

    @Schema(description = "转院原因", example = "需进一步治疗")
    private String transferReason;

    @Schema(description = "死亡原因", example = "心力衰竭")
    private String deathReason;

    @Schema(description = "死亡时间", example = "2026-06-18T10:00:00")
    private LocalDateTime deathTime;

    @Schema(description = "申请医生ID", example = "1")
    private Long applyDoctorId;

    @Schema(description = "申请医生姓名", example = "张医生")
    private String applyDoctorName;

    @Schema(description = "申请时间", example = "2026-06-18T10:00:00")
    private LocalDateTime applyTime;

    @Schema(description = "申请原因", example = "患者病情好转，可以出院")
    private String applyReason;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "状态名称", example = "待审批")
    private String statusName;

    @Schema(description = "审批医生ID", example = "1")
    private Long auditDoctorId;

    @Schema(description = "审批医生姓名", example = "李医生")
    private String auditDoctorName;

    @Schema(description = "审批时间", example = "2026-06-18T11:00:00")
    private LocalDateTime auditTime;

    @Schema(description = "审批意见", example = "同意出院")
    private String auditRemark;

    @Schema(description = "驳回原因", example = "需进一步观察")
    private String rejectReason;

    @Schema(description = "取消原因", example = "患者要求继续住院")
    private String cancelReason;

    @Schema(description = "取消时间", example = "2026-06-18T12:00:00")
    private LocalDateTime cancelTime;

    @Schema(description = "是否停止医嘱", example = "1")
    private Integer orderStopped;

    @Schema(description = "医嘱停止时间", example = "2026-06-18T11:30:00")
    private LocalDateTime orderStopTime;

    @Schema(description = "是否费用结算", example = "0")
    private Integer feeSettled;

    @Schema(description = "费用结算时间", example = "2026-06-18T12:00:00")
    private LocalDateTime feeSettleTime;

    @Schema(description = "住院天数", example = "4")
    private Integer hospitalDays;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间", example = "2026-06-18T10:00:00")
    private LocalDateTime createTime;

}