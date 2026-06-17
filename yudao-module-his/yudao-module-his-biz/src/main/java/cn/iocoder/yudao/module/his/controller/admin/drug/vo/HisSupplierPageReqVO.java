package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 供应商 分页 Request VO
 */
@Schema(description = "管理后台 - 供应商分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisSupplierPageReqVO extends PageParam {

    @Schema(description = "供应商编码，模糊匹配", example = "SUP")
    private String supplierCode;

    @Schema(description = "供应商名称，模糊匹配", example = "医药")
    private String supplierName;

    @Schema(description = "联系人，模糊匹配", example = "张")
    private String contactName;

    @Schema(description = "状态:1-正常 2-停用", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}