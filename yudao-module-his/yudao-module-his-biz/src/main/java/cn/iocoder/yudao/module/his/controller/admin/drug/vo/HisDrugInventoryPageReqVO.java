package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 盘点记录 分页 Request VO
 */
@Schema(description = "管理后台 - 盘点记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDrugInventoryPageReqVO extends PageParam {

    @Schema(description = "盘点单号，模糊匹配", example = "PD")
    private String inventoryNo;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "盘点日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inventoryDate;

    @Schema(description = "状态:1-进行中 2-已完成", example = "1")
    private Integer inventoryStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}