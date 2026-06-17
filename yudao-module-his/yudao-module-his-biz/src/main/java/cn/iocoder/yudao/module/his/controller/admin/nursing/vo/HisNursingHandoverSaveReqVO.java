package cn.iocoder.yudao.module.his.controller.admin.nursing.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 护理交接班新增/修改 Request VO")
@Data
public class HisNursingHandoverSaveReqVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "病区ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "病区ID不能为空")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "交班护士ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "交班护士不能为空")
    private Long handoverNurseId;

    @Schema(description = "交班护士姓名", example = "王护士")
    private String handoverNurseName;

    @Schema(description = "接班护士ID", example = "2")
    private Long takeoverNurseId;

    @Schema(description = "接班护士姓名", example = "李护士")
    private String takeoverNurseName;

    @Schema(description = "交班时间")
    private LocalDateTime handoverTime;

    @Schema(description = "班次类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "班次类型不能为空")
    private Integer shiftType;

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
}