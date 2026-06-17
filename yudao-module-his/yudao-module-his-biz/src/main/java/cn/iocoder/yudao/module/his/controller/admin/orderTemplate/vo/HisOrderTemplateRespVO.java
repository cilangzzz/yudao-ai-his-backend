package cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 医嘱模板 Response VO")
@Data
public class HisOrderTemplateRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "TPL001")
    private String templateCode;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "肺炎入院常规医嘱")
    private String templateName;

    @Schema(description = "模板分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer templateCategory;

    @Schema(description = "模板分类名称", example = "个人模板")
    private String templateCategoryName;

    @Schema(description = "医嘱类型", example = "1")
    private Integer orderType;

    @Schema(description = "医嘱类型名称", example = "药品")
    private String orderTypeName;

    @Schema(description = "科室ID", example = "100")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "创建医生ID", example = "200")
    private Long doctorId;

    @Schema(description = "创建医生姓名", example = "张医生")
    private String doctorName;

    @Schema(description = "适用科室ID（多个用逗号分隔）", example = "100,101,102")
    private String applicableDeptIds;

    @Schema(description = "疾病编码（ICD-10）", example = "J18.9")
    private String diagnosisCode;

    @Schema(description = "疾病名称", example = "肺炎")
    private String diagnosisName;

    @Schema(description = "模板内容摘要", example = "包含抗感染、止咳化痰等医嘱")
    private String contentSummary;

    @Schema(description = "备注", example = "适用于普通肺炎患者")
    private String remark;

    @Schema(description = "排序号", example = "1")
    private Integer sort;

    @Schema(description = "使用次数", example = "100")
    private Integer useCount;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "医嘱模板明细列表")
    private java.util.List<HisOrderTemplateItemRespVO> items;

}
