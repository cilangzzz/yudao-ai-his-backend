package cn.iocoder.yudao.module.his.controller.admin.dispense.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 发药记录 Response VO")
@Data
public class OpDispenseRespVO {

    @Schema(description = "主键ID", example = "1")
    private Long id;

    @Schema(description = "发药单号", example = "DS202606180001")
    private String dispenseNo;

    @Schema(description = "处方ID", example = "1")
    private Long prescriptionId;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "药房ID", example = "1")
    private Long pharmacyId;

    @Schema(description = "药房名称", example = "门诊西药房")
    private String pharmacyName;

    @Schema(description = "调配药师ID", example = "1")
    private Long dispensePharmacist;

    @Schema(description = "调配药师姓名", example = "李药师")
    private String dispensePharmacistName;

    @Schema(description = "调配时间")
    private LocalDateTime dispenseTime;

    @Schema(description = "发药药师ID", example = "1")
    private Long sendPharmacist;

    @Schema(description = "发药药师姓名", example = "王药师")
    private String sendPharmacistName;

    @Schema(description = "发药时间")
    private LocalDateTime sendTime;

    @Schema(description = "发药状态：1-待调配 2-已调配 3-已发药 4-已退药", example = "1")
    private Integer dispenseStatus;

    @Schema(description = "总金额", example = "125.00")
    private BigDecimal totalAmount;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "发药明细列表")
    private List<OpDispenseItemRespVO> items;

}
