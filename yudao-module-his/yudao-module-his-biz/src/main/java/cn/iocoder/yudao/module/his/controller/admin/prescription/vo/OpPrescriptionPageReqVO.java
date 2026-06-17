package cn.iocoder.yudao.module.his.controller.admin.prescription.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 处方分页请求 VO
 */
@Schema(description = "管理后台 - HIS处方分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpPrescriptionPageReqVO extends PageParam {

    @Schema(description = "处方编号", example = "RX202606180001")
    private String prescriptionNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "挂号ID", example = "1")
    private Long registerId;

    @Schema(description = "处方类型：1-普通 2-急诊 3-儿科 4-麻醉 5-精神 6-中药", example = "1")
    private Integer prescriptionType;

    @Schema(description = "开方科室ID", example = "1")
    private Long deptId;

    @Schema(description = "开方医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "处方状态：1-开立 2-审核通过 3-审核退回 4-已调配 5-已发药 6-已退药", example = "1")
    private Integer prescriptionStatus;

    @Schema(description = "开始日期", example = "2026-06-01")
    private LocalDate startDate;

    @Schema(description = "结束日期", example = "2026-06-18")
    private LocalDate endDate;

}
