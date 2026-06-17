package cn.iocoder.yudao.module.his.controller.admin.medication.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 给药执行 Request VO
 *
 * 用于护士执行给药操作，包含闭环核对信息
 */
@Schema(description = "管理后台 - 给药执行 Request VO")
@Data
public class HisMedicationAdminExecuteReqVO {

    @Schema(description = "给药记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "给药记录ID不能为空")
    private Long id;

    // ==================== 腕带扫描信息 ====================

    @Schema(description = "腕带扫描状态: 0未扫描/1匹配/2不匹配", example = "1")
    private Integer wristbandScanStatus;

    @Schema(description = "腕带扫描结果", example = "腕带匹配成功")
    private String wristbandScanResult;

    // ==================== 药品扫描信息 ====================

    @Schema(description = "药品扫描状态: 0未扫描/1匹配/2不匹配", example = "1")
    private Integer drugScanStatus;

    @Schema(description = "药品扫描结果", example = "药品条码匹配成功")
    private String drugScanResult;

    @Schema(description = "药品批号", example = "B20260101")
    private String drugBatchNo;

    @Schema(description = "药品有效期", example = "2027-12-31")
    private String drugExpireDate;

    // ==================== 核对结果 ====================

    @Schema(description = "核对结果: 1通过/2不通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "核对结果不能为空")
    private Integer checkResult;

    // ==================== 执行信息 ====================

    @Schema(description = "实际执行时间，不填则使用当前时间")
    private LocalDateTime actualTime;

    @Schema(description = "备注")
    private String remark;

}
