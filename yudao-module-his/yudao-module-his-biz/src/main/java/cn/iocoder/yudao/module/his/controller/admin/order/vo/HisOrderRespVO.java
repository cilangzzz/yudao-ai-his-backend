package cn.iocoder.yudao.module.his.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 医嘱 Response VO")
@Data
public class HisOrderRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "医嘱编号", example = "O202606170001")
    private String orderNo;

    @Schema(description = "住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "医嘱类型", example = "1")
    private Integer orderType;

    @Schema(description = "医嘱类型名称", example = "药品")
    private String orderTypeName;

    @Schema(description = "医嘱分类", example = "1")
    private Integer orderCategory;

    @Schema(description = "医嘱分类名称", example = "长期")
    private String orderCategoryName;

    @Schema(description = "医嘱内容", example = "0.9%NS 250ml ivgtt qd")
    private String orderContent;

    @Schema(description = "项目编码", example = "DR001")
    private String itemCode;

    @Schema(description = "项目名称", example = "0.9%氯化钠注射液")
    private String itemName;

    @Schema(description = "剂量", example = "250")
    private String dosage;

    @Schema(description = "剂量单位", example = "ml")
    private String dosageUnit;

    @Schema(description = "频次编码", example = "qd")
    private String frequencyCode;

    @Schema(description = "频次名称", example = "每日一次")
    private String frequencyName;

    @Schema(description = "途径编码", example = "ivgtt")
    private String routeCode;

    @Schema(description = "途径名称", example = "静脉滴注")
    private String routeName;

    @Schema(description = "疗程(天)", example = "7")
    private Integer duration;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "停止时间")
    private LocalDateTime stopTime;

    @Schema(description = "开立医生ID", example = "1")
    private Long prescribingDoctor;

    @Schema(description = "开立医生姓名", example = "李医生")
    private String prescribingDoctorName;

    @Schema(description = "审核护士ID", example = "1")
    private Long auditNurse;

    @Schema(description = "审核护士姓名", example = "王护士")
    private String auditNurseName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "执行护士ID", example = "1")
    private Long executeNurse;

    @Schema(description = "执行护士姓名", example = "王护士")
    private String executeNurseName;

    @Schema(description = "执行时间")
    private LocalDateTime executeTime;

    @Schema(description = "医嘱状态", example = "1")
    private Integer orderStatus;

    @Schema(description = "医嘱状态名称", example = "开立")
    private String orderStatusName;

    @Schema(description = "停止医生ID", example = "1")
    private Long stopDoctor;

    @Schema(description = "停止医生姓名", example = "李医生")
    private String stopDoctorName;

    @Schema(description = "停止原因", example = "病情好转")
    private String stopReason;

    @Schema(description = "紧急标志", example = "0")
    private Integer urgency;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // ==================== 业务状态判断 ====================

    @Schema(description = "是否为长期医嘱")
    private Boolean longTermOrder;

    @Schema(description = "是否为临时医嘱")
    private Boolean temporaryOrder;

    @Schema(description = "是否为紧急医嘱")
    private Boolean urgent;

    @Schema(description = "是否可以审核")
    private Boolean canAudit;

    @Schema(description = "是否可以执行")
    private Boolean canExecute;

    @Schema(description = "是否执行中")
    private Boolean executing;

    @Schema(description = "是否已完成")
    private Boolean completed;

    @Schema(description = "是否已停止")
    private Boolean stopped;

    @Schema(description = "是否已作废")
    private Boolean cancelled;

    @Schema(description = "是否可以停止")
    private Boolean canStop;

    @Schema(description = "是否可以作废")
    private Boolean canCancel;

    @Schema(description = "是否有效医嘱")
    private Boolean validOrder;
}