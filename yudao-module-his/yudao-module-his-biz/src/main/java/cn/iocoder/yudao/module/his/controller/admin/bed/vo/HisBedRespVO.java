package cn.iocoder.yudao.module.his.controller.admin.bed.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 床位 Response VO")
@Data
public class HisBedRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "床号", requiredMode = Schema.RequiredMode.REQUIRED, example = "01")
    private String bedNo;

    @Schema(description = "病区ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long wardId;

    @Schema(description = "病区名称", example = "内科一病区")
    private String wardName;

    @Schema(description = "房间号", example = "101")
    private String roomNo;

    @Schema(description = "床位类型", example = "1")
    private Integer bedType;

    @Schema(description = "床位类型名称", example = "普通")
    private String bedTypeName;

    @Schema(description = "床位等级", example = "1")
    private Integer bedClass;

    @Schema(description = "床位等级名称", example = "普通")
    private String bedClassName;

    @Schema(description = "床位日单价", example = "50.00")
    private BigDecimal dailyPrice;

    @Schema(description = "床位状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer bedStatus;

    @Schema(description = "床位状态名称", example = "空床")
    private String bedStatusName;

    @Schema(description = "当前患者ID", example = "1")
    private Long currentPatientId;

    @Schema(description = "当前患者姓名", example = "张三")
    private String currentPatientName;

    @Schema(description = "当前住院ID", example = "1")
    private Long admissionId;

    @Schema(description = "排序号", example = "1")
    private Integer sortOrder;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
