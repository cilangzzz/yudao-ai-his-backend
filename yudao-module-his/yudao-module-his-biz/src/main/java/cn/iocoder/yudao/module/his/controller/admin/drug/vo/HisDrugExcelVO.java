package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 药品目录 Excel VO
 */
@Data
public class HisDrugExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("药品编码")
    private String drugCode;

    @ExcelProperty("药品通用名")
    private String drugName;

    @ExcelProperty("药品商品名")
    private String drugTradeName;

    @ExcelProperty("药品类型")
    @DictFormat("his_drug_type")
    private Integer drugType;

    @ExcelProperty("剂型")
    private String dosageForm;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("基本单位")
    private String unit;

    @ExcelProperty("生产厂家")
    private String manufacturer;

    @ExcelProperty("零售价")
    private BigDecimal retailPrice;

    @ExcelProperty("采购价")
    private BigDecimal purchasePrice;

    @ExcelProperty("医保类别")
    @DictFormat("his_insurance_category")
    private Integer insuranceCategory;

    @ExcelProperty("OTC标志")
    private Integer otcFlag;

    @ExcelProperty("状态")
    @DictFormat("common_status")
    private Integer status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
