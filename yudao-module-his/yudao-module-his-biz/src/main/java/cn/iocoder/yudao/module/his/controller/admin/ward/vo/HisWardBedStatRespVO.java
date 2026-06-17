package cn.iocoder.yudao.module.his.controller.admin.ward.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 病区床位统计 Response VO")
@Data
public class HisWardBedStatRespVO {

    @Schema(description = "病区ID", example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "床位总数", example = "30")
    private Integer totalBeds;

    @Schema(description = "已用床位数", example = "25")
    private Integer usedBeds;

    @Schema(description = "空床位数", example = "5")
    private Integer emptyBeds;

    @Schema(description = "清洁中床位数", example = "2")
    private Integer cleaningBeds;

    @Schema(description = "维修中床位数", example = "1")
    private Integer maintenanceBeds;

    @Schema(description = "预约中床位数", example = "0")
    private Integer reservedBeds;

    @Schema(description = "床位使用率(%)", example = "83.33")
    private Double usageRate;
}
