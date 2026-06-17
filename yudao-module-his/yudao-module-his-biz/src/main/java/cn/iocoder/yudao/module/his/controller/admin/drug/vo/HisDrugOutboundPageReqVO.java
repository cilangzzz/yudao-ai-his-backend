package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 出库记录 分页 Request VO
 */
@Schema(description = "管理后台 - 出库记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDrugOutboundPageReqVO extends PageParam {

    @Schema(description = "出库单号，模糊匹配", example = "CK")
    private String outboundNo;

    @Schema(description = "出库类型:1-药房领用 2-报损出库 3-调拨出库", example = "1")
    private Integer outboundType;

    @Schema(description = "出库目标ID", example = "1")
    private Long targetId;

    @Schema(description = "出库目标名称，模糊匹配", example = "药房")
    private String targetName;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "审核状态:1-待审核 2-已审核 3-已拒绝", example = "1")
    private Integer auditStatus;

    @Schema(description = "出库时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] outboundTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}