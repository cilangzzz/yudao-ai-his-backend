package cn.iocoder.yudao.module.his.controller.admin.register.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 挂号分页请求 VO
 */
@Schema(description = "管理后台 - HIS挂号分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpRegisterPageReqVO extends PageParam {

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "挂号日期", example = "2026-06-17")
    private LocalDate registerDate;

    @Schema(description = "挂号状态：1-候诊 2-就诊中 3-已完成 4-已退号", example = "1")
    private Integer registerStatus;

}