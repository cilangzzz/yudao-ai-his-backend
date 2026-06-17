package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 药品库存 分页 Request VO
 */
@Schema(description = "管理后台 - 药品库存分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDrugStockPageReqVO extends PageParam {

    @Schema(description = "药品ID", example = "1")
    private Long drugId;

    @Schema(description = "药品编码，模糊匹配", example = "DRUG")
    private String drugCode;

    @Schema(description = "药品名称，模糊匹配", example = "阿莫西林")
    private String drugName;

    @Schema(description = "批号", example = "20240101")
    private String batchNo;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "状态:1-正常 2-近效期 3-过期", example = "1")
    private Integer status;

    @Schema(description = "有效期开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDateStart;

    @Schema(description = "有效期结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDateEnd;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}