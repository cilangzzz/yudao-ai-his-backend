package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 住院费用明细分页请求 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 住院费用明细分页请求 VO")
public class HisInpatientFeePageReqVO extends PageParam {

    @Schema(description = "入院记录ID", example = "100")
    private Long admissionId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @Schema(description = "费用单号", example = "FEE202406180001")
    private String feeNo;

    @Schema(description = "项目编码", example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称", example = "检查费")
    private String itemName;

    @Schema(description = "项目类型", example = "1")
    private Integer itemType;

    @Schema(description = "费用状态", example = "0")
    private Integer feeStatus;

    @Schema(description = "科室ID", example = "10")
    private Long deptId;

    @Schema(description = "费用日期-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime feeDateStart;

    @Schema(description = "费用日期-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime feeDateEnd;

}