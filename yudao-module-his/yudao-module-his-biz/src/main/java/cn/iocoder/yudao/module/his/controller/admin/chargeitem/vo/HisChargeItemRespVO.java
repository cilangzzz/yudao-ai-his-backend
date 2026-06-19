package cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收费项目响应 VO
 */
@Schema(description = "管理后台 - HIS收费项目 Response VO")
@Data
public class HisChargeItemRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "ITEM001")
    private String itemCode;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "心电图检查")
    private String name;

    @Schema(description = "项目类型：1-挂号费 2-诊查费 3-检查费 4-治疗费 5-手术费 6-化验费 7-材料费 8-药品费 9-床位费 10-护理费 11-其他", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Integer type;

    @Schema(description = "项目类型名称", example = "检查费")
    private String typeName;

    @Schema(description = "计价单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "次")
    private String unit;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    private BigDecimal price;

    @Schema(description = "医保类型：1-甲类 2-乙类 3-丙类 4-自费", example = "1")
    private Integer insuranceType;

    @Schema(description = "医保类型名称", example = "甲类")
    private String insuranceTypeName;

    @Schema(description = "执行科室ID", example = "1")
    private Long deptId;

    @Schema(description = "执行科室名称", example = "心电图室")
    private String deptName;

    @Schema(description = "规格", example = "标准")
    private String spec;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "状态：1-在用 2-停用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}