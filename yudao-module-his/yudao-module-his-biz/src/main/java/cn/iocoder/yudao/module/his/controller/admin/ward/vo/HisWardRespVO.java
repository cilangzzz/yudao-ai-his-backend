package cn.iocoder.yudao.module.his.controller.admin.ward.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 病区 Response VO")
@Data
public class HisWardRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "病区编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "W001")
    private String wardCode;

    @Schema(description = "病区名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "内科一病区")
    private String wardName;

    @Schema(description = "所属科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "楼层", example = "3F")
    private String floor;

    @Schema(description = "床位总数", example = "30")
    private Integer bedCount;

    @Schema(description = "已用床位数", example = "25")
    private Integer usedBedCount;

    @Schema(description = "空床位数", example = "5")
    private Integer emptyBedCount;

    @Schema(description = "床位使用率(%)", example = "83.33")
    private Double usageRate;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
