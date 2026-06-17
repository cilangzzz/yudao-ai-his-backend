package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 采购计划 分页 Request VO
 */
@Schema(description = "管理后台 - 采购计划分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDrugPurchasePageReqVO extends PageParam {

    @Schema(description = "采购单号，模糊匹配", example = "CG")
    private String purchaseNo;

    @Schema(description = "供应商ID", example = "1")
    private Long supplierId;

    @Schema(description = "供应商名称，模糊匹配", example = "医药")
    private String supplierName;

    @Schema(description = "采购类型:1-计划采购 2-紧急采购", example = "1")
    private Integer purchaseType;

    @Schema(description = "状态:1-草稿 2-已提交 3-已审批 4-已采购 5-已完成", example = "1")
    private Integer purchaseStatus;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}