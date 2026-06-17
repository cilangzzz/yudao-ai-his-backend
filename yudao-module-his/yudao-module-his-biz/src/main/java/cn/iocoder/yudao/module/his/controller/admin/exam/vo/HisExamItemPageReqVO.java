package cn.iocoder.yudao.module.his.controller.admin.exam.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 检查项目字典分页请求 VO
 */
@Schema(description = "管理后台 - HIS检查项目字典分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisExamItemPageReqVO extends PageParam {

    @Schema(description = "项目编码", example = "CT001")
    private String itemCode;

    @Schema(description = "项目名称", example = "胸部CT")
    private String itemName;

    @Schema(description = "检查类型:1-影像检查 2-检验检查 3-病理检查 4-功能检查", example = "1")
    private Integer examType;

    @Schema(description = "项目类别", example = "CT")
    private String itemCategory;

    @Schema(description = "执行科室ID", example = "20")
    private Long executionDept;

    @Schema(description = "状态:1-在用 2-停用", example = "1")
    private Integer status;

}
