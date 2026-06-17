package cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 医嘱模板明细保存 Request VO")
@Data
public class HisOrderTemplateItemSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "医嘱内容", example = "阿莫西林胶囊 0.5g tid")
    private String orderContent;

    @Schema(description = "项目编码", example = "DRUG001")
    private String itemCode;

    @Schema(description = "项目名称", example = "阿莫西林胶囊")
    private String itemName;

    @Schema(description = "医嘱类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱类型不能为空")
    private Integer orderType;

    @Schema(description = "医嘱分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "医嘱分类不能为空")
    private Integer orderCategory;

    @Schema(description = "剂量", example = "0.5")
    private String dosage;

    @Schema(description = "剂量单位", example = "g")
    private String dosageUnit;

    @Schema(description = "频次编码", example = "FREQ001")
    private String frequencyCode;

    @Schema(description = "频次名称", example = "每日三次")
    private String frequencyName;

    @Schema(description = "途径编码", example = "ROUTE001")
    private String routeCode;

    @Schema(description = "途径名称", example = "口服")
    private String routeName;

    @Schema(description = "疗程(天)", example = "7")
    private Integer duration;

    @Schema(description = "单价", example = "10.00")
    private BigDecimal price;

    @Schema(description = "金额", example = "210.00")
    private BigDecimal amount;

    @Schema(description = "紧急标志", example = "0")
    private Integer urgency;

    @Schema(description = "皮试标志", example = "0")
    private Integer skinTestFlag;

    @Schema(description = "自备标志", example = "0")
    private Integer selfProvideFlag;

    @Schema(description = "备注", example = "饭后服用")
    private String remark;

    @Schema(description = "排序号", example = "1")
    private Integer sort;

}
