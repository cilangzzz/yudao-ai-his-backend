package cn.iocoder.yudao.module.his.controller.admin.fee.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 门诊费用明细 分页 Request VO
 */
@Schema(description = "管理后台 - 门诊费用明细分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpFeeItemPageReqVO extends PageParam {

    @Schema(description = "费用ID", example = "1")
    private Long feeId;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "收费项目ID", example = "1")
    private Long chargeItemId;

    @Schema(description = "项目编码", example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称，模糊匹配", example = "挂号费")
    private String itemName;

    @Schema(description = "项目类别", example = "1")
    private Integer itemCategory;

    @Schema(description = "来源类型", example = "1")
    private Integer sourceType;

    @Schema(description = "收费状态", example = "0")
    private Integer feeItemStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}