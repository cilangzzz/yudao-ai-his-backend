package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理交接班 Response VO")
@Data
public class HisNursingHandoverRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "交接班编号", example = "HO202606170001")
    private String handoverNo;

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    // ==================== 交班信息 ====================

    @Schema(description = "交班护士ID", example = "1")
    private Long handoverNurseId;

    @Schema(description = "交班护士姓名", example = "王护士")
    private String handoverNurseName;

    @Schema(description = "交班时间")
    private LocalDateTime handoverTime;

    // ==================== 接班信息 ====================

    @Schema(description = "接班护士ID", example = "2")
    private Long takeoverNurseId;

    @Schema(description = "接班护士姓名", example = "李护士")
    private String takeoverNurseName;

    @Schema(description = "接班时间")
    private LocalDateTime takeoverTime;

    // ==================== 班次信息 ====================

    @Schema(description = "班次类型", example = "1")
    private Integer shiftType;

    @Schema(description = "班次类型名称", example = "白班")
    private String shiftTypeName;

    @Schema(description = "交接班状态", example = "0")
    private Integer status;

    @Schema(description = "状态名称", example = "待接班")
    private String statusName;

    // ==================== 患者概况 ====================

    @Schema(description = "原有患者数", example = "20")
    private Integer originalPatientCount;

    @Schema(description = "新入院患者数", example = "2")
    private Integer newAdmissionCount;

    @Schema(description = "出院患者数", example = "1")
    private Integer dischargeCount;

    @Schema(description = "转入患者数", example = "0")
    private Integer transferInCount;

    @Schema(description = "转出患者数", example = "0")
    private Integer transferOutCount;

    @Schema(description = "现有患者数", example = "21")
    private Integer currentPatientCount;

    @Schema(description = "特护患者数", example = "1")
    private Integer specialCareCount;

    @Schema(description = "一级护理患者数", example = "3")
    private Integer primaryCareCount;

    @Schema(description = "二级护理患者数", example = "10")
    private Integer secondaryCareCount;

    @Schema(description = "三级护理患者数", example = "7")
    private Integer tertiaryCareCount;

    // ==================== 交接内容 ====================

    @Schema(description = "重点患者情况")
    private String keyPatientSituation;

    @Schema(description = "待办事项")
    private String todoItems;

    @Schema(description = "特殊情况记录")
    private String specialSituation;

    @Schema(description = "物品交接情况")
    private String goodsHandover;

    @Schema(description = "备注")
    private String remark;

    // ==================== 交班签名信息 ====================

    @Schema(description = "交班签名状态", example = "0")
    private Integer handoverSignatureStatus;

    @Schema(description = "交班签名时间")
    private LocalDateTime handoverSignatureTime;

    @Schema(description = "交班电子签名")
    private String handoverSignature;

    // ==================== 接班签名信息 ====================

    @Schema(description = "接班签名状态", example = "0")
    private Integer takeoverSignatureStatus;

    @Schema(description = "接班签名时间")
    private LocalDateTime takeoverSignatureTime;

    @Schema(description = "接班电子签名")
    private String takeoverSignature;

    // ==================== 时间信息 ====================

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // ==================== 状态判断 ====================

    @Schema(description = "是否已交班签名")
    private Boolean handoverSigned;

    @Schema(description = "是否已接班签名")
    private Boolean takeoverSigned;

    @Schema(description = "是否已完成交接")
    private Boolean completed;

    @Schema(description = "是否可以接班")
    private Boolean canTakeover;

    @Schema(description = "是否可以作废")
    private Boolean canCancel;
}