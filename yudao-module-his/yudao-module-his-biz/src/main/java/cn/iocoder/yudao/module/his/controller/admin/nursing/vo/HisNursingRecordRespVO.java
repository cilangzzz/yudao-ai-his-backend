package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理记录 Response VO")
@Data
public class HisNursingRecordRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "记录编号", example = "NR202606170001")
    private String recordNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "护士ID", example = "1")
    private Long nurseId;

    @Schema(description = "护士姓名", example = "王护士")
    private String nurseName;

    @Schema(description = "记录类型", example = "1")
    private Integer recordType;

    @Schema(description = "记录类型名称", example = "一般护理记录")
    private String recordTypeName;

    @Schema(description = "标题", example = "护理记录")
    private String title;

    @Schema(description = "护理内容")
    private String content;

    @Schema(description = "记录时间")
    private LocalDateTime recordTime;

    // ==================== 签名信息 ====================

    @Schema(description = "签名状态", example = "0")
    private Integer signatureStatus;

    @Schema(description = "签名时间")
    private LocalDateTime signatureTime;

    @Schema(description = "电子签名")
    private String signature;

    // ==================== 审核信息 ====================

    @Schema(description = "审核状态", example = "0")
    private Integer auditStatus;

    @Schema(description = "审核护士ID", example = "2")
    private Long auditNurseId;

    @Schema(description = "审核护士姓名", example = "李护士长")
    private String auditNurseName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // ==================== 状态判断 ====================

    @Schema(description = "是否已签名")
    private Boolean signed;

    @Schema(description = "是否已审核")
    private Boolean audited;

    @Schema(description = "是否可以签名")
    private Boolean canSign;

    @Schema(description = "是否可以审核")
    private Boolean canAudit;
}