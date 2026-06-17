package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 入库记录 分页 Request VO
 */
@Schema(description = "管理后台 - 入库记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDrugInboundPageReqVO extends PageParam {

    @Schema(description = "入库单号，模糊匹配", example = "RK")
    private String inboundNo;

    @Schema(description = "入库类型:1-采购入库 2-退货入库 3-调拨入库", example = "1")
    private Integer inboundType;

    @Schema(description = "供应商ID", example = "1")
    private Long supplierId;

    @Schema(description = "供应商名称，模糊匹配", example = "医药")
    private String supplierName;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "审核状态:1-待审核 2-已审核 3-已拒绝", example = "1")
    private Integer auditStatus;

    @Schema(description = "入库时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] inboundTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}