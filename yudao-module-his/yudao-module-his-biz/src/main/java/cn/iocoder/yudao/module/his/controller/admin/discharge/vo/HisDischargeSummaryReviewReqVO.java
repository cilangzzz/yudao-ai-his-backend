package cn.iocoder.yudao.module.his.controller.admin.discharge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 出院小结审核 Request VO
 */
@Schema(description = "管理后台 - 出院小结审核 Request VO")
@Data
public class HisDischargeSummaryReviewReqVO {

    @Schema(description = "出院小结ID", required = true, example = "1")
    @NotNull(message = "出院小结ID不能为空")
    private Long id;

    @Schema(description = "审核医生ID", required = true, example = "1")
    @NotNull(message = "审核医生不能为空")
    private Long reviewDoctor;

    @Schema(description = "审核医生姓名", example = "王主任")
    private String reviewDoctorName;
}