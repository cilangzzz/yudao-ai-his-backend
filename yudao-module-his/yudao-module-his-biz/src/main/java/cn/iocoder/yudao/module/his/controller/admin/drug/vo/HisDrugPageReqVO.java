package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 药品目录 分页 Request VO
 */
@Schema(description = "管理后台 - 药品目录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDrugPageReqVO extends PageParam {

    @Schema(description = "药品编码，模糊匹配", example = "DRUG")
    private String drugCode;

    @Schema(description = "药品通用名，模糊匹配", example = "阿莫西林")
    private String drugName;

    @Schema(description = "药品商品名，模糊匹配", example = "阿莫西林")
    private String drugTradeName;

    @Schema(description = "药品类型:1-西药 2-中成药 3-中草药 4-生物制品", example = "1")
    private Integer drugType;

    @Schema(description = "剂型", example = "胶囊剂")
    private String dosageForm;

    @Schema(description = "生产厂家，模糊匹配", example = "哈药")
    private String manufacturer;

    @Schema(description = "OTC标志:0-处方药 1-OTC", example = "0")
    private Integer otcFlag;

    @Schema(description = "麻醉药品标志:0-否 1-是", example = "0")
    private Integer narcoticFlag;

    @Schema(description = "精神药品标志:0-否 1-是", example = "0")
    private Integer psychotropicFlag;

    @Schema(description = "抗菌药物标志:0-否 1-是", example = "0")
    private Integer antibioticFlag;

    @Schema(description = "医保类别:1-甲类 2-乙类 3-丙类", example = "1")
    private Integer insuranceCategory;

    @Schema(description = "状态:1-在用 2-停用", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
