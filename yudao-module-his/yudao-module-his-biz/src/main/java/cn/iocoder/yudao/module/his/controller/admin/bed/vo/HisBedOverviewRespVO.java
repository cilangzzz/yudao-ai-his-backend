package cn.iocoder.yudao.module.his.controller.admin.bed.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 床位总览统计 VO
 */
@Schema(description = "管理后台 - 床位总览统计")
@Data
public class HisBedOverviewRespVO {

    @Schema(description = "病区ID")
    private Long wardId;

    @Schema(description = "病区名称")
    private String wardName;

    @Schema(description = "总床位数")
    private Integer totalBeds;

    @Schema(description = "空闲床位数")
    private Integer emptyBeds;

    @Schema(description = "占用床位数")
    private Integer occupiedBeds;

    @Schema(description = "清洁中床位数")
    private Integer cleaningBeds;

    @Schema(description = "维修中床位数")
    private Integer maintenanceBeds;

    @Schema(description = "预留床位数")
    private Integer reservedBeds;

    @Schema(description = "床位使用率(%)")
    private Double usageRate;

    @Schema(description = "各类型床位统计")
    private List<BedTypeStats> bedTypeStats;

    @Schema(description = "床位类型统计")
    @Data
    public static class BedTypeStats {

        @Schema(description = "床位类型")
        private Integer bedType;

        @Schema(description = "床位类型名称")
        private String bedTypeName;

        @Schema(description = "总数")
        private Integer total;

        @Schema(description = "空闲数")
        private Integer empty;

        @Schema(description = "占用数")
        private Integer occupied;
    }

}