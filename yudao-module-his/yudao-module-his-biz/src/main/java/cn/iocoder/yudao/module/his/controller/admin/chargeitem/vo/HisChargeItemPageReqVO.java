package cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收费项目分页请求 VO
 */
@Schema(description = "管理后台 - HIS收费项目分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisChargeItemPageReqVO extends PageParam {

    @Schema(description = "项目名称", example = "心电图检查")
    private String name;

    @Schema(description = "项目编码", example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目类型：1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他", example = "3")
    private Integer type;

    @Schema(description = "医保类型：1-甲类 2-乙类 3-丙类", example = "1")
    private Integer insuranceType;

    @Schema(description = "执行科室ID", example = "1")
    private Long deptId;

    @Schema(description = "状态：1-在用 2-停用", example = "1")
    private Integer status;

}