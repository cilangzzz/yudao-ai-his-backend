package cn.iocoder.yudao.module.his.controller.admin.drug.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 供应商 新增/修改 Request VO
 */
@Schema(description = "管理后台 - 供应商新增/修改 Request VO")
@Data
public class HisSupplierSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "供应商编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "SUP001")
    @NotBlank(message = "供应商编码不能为空")
    private String supplierCode;

    @Schema(description = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "XX医药公司")
    @NotBlank(message = "供应商名称不能为空")
    private String supplierName;

    @Schema(description = "简称", example = "XX医药")
    private String shortName;

    @Schema(description = "联系人", example = "张三")
    private String contactName;

    @Schema(description = "联系电话", example = "13800138000")
    private String contactPhone;

    @Schema(description = "地址", example = "北京市朝阳区XX路XX号")
    private String address;

    @Schema(description = "营业执照号", example = "91110105MA01XXXXX")
    private String licenseNo;

    @Schema(description = "GSP证书号", example = "GSP2019001")
    private String gspNo;

    @Schema(description = "开户银行", example = "中国工商银行")
    private String bankName;

    @Schema(description = "银行账号", example = "6222021234567890123")
    private String bankAccount;

    @Schema(description = "状态:1-正常 2-停用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}