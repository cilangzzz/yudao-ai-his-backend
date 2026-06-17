package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 药品目录 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 药品目录新增/修改 Request VO")
@Data
public class HisDrugSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "药品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DRUG001")
    @NotBlank(message = "药品编码不能为空")
    private String drugCode;

    @Schema(description = "药品通用名", requiredMode = Schema.RequiredMode.REQUIRED, example = "阿莫西林胶囊")
    @NotBlank(message = "药品通用名不能为空")
    private String drugName;

    @Schema(description = "药品商品名", example = "阿莫西林")
    private String drugTradeName;

    @Schema(description = "药品类型:1-西药 2-中成药 3-中草药 4-生物制品", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "药品类型不能为空")
    private Integer drugType;

    @Schema(description = "药品类别", example = "抗感染药")
    private String drugCategory;

    @Schema(description = "剂型", example = "胶囊剂")
    private String dosageForm;

    @Schema(description = "规格", example = "0.5g*24粒")
    private String spec;

    @Schema(description = "基本单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "盒")
    @NotBlank(message = "基本单位不能为空")
    private String unit;

    @Schema(description = "生产厂家", example = "哈药集团")
    private String manufacturer;

    @Schema(description = "批准文号", example = "国药准字H20123456")
    private String approvalNo;

    @Schema(description = "零售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "25.50")
    @NotNull(message = "零售价不能为空")
    private BigDecimal retailPrice;

    @Schema(description = "采购价", example = "15.00")
    private BigDecimal purchasePrice;

    @Schema(description = "医保目录编码", example = "XYZ123")
    private String insuranceCode;

    @Schema(description = "医保类别:1-甲类 2-乙类 3-丙类", example = "1")
    private Integer insuranceCategory;

    @Schema(description = "OTC标志:0-处方药 1-OTC", example = "0")
    private Integer otcFlag;

    @Schema(description = "麻醉药品标志:0-否 1-是", example = "0")
    private Integer narcoticFlag;

    @Schema(description = "精神药品标志:0-否 1-是", example = "0")
    private Integer psychotropicFlag;

    @Schema(description = "毒性药品标志:0-否 1-是", example = "0")
    private Integer toxicFlag;

    @Schema(description = "抗菌药物标志:0-否 1-是", example = "0")
    private Integer antibioticFlag;

    @Schema(description = "储存条件", example = "密封，在干燥处保存")
    private String storageCondition;

    @Schema(description = "有效期(月)", example = "24")
    private Integer shelfLife;

    @Schema(description = "条形码", example = "6901234567890")
    private String barcode;

    @Schema(description = "状态:1-在用 2-停用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}
