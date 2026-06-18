package cn.iocoder.yudao.module.his.controller.admin.settlement.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 住院费用明细分页请求 VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("住院费用明细分页请求 VO")
public class HisInpatientFeePageReqVO extends PageParam {

    @ApiModelProperty(value = "入院记录ID", example = "100")
    private Long admissionId;

    @ApiModelProperty(value = "患者ID", example = "1")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名", example = "张三")
    private String patientName;

    @ApiModelProperty(value = "住院号", example = "ZY20240001")
    private String inpatientNo;

    @ApiModelProperty(value = "费用单号", example = "FEE202406180001")
    private String feeNo;

    @ApiModelProperty(value = "项目编码", example = "ITEM001")
    private String itemCode;

    @ApiModelProperty(value = "项目名称", example = "检查费")
    private String itemName;

    @ApiModelProperty(value = "项目类型", example = "1")
    private Integer itemType;

    @ApiModelProperty(value = "费用状态", example = "0")
    private Integer feeStatus;

    @ApiModelProperty(value = "科室ID", example = "10")
    private Long deptId;

    @ApiModelProperty(value = "费用日期-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime feeDateStart;

    @ApiModelProperty(value = "费用日期-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime feeDateEnd;

}