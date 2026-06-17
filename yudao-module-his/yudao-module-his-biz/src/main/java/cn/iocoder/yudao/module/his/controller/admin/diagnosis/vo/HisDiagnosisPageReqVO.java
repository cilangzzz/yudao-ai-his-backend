package cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 诊断记录分页请求 VO
 */
@Schema(description = "管理后台 - HIS诊断记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisDiagnosisPageReqVO extends PageParam {

    @Schema(description = "就诊ID", example = "1")
    private Long encounterId;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "诊断类型：1-门诊诊断 2-入院诊断 3-出院诊断 4-修正诊断 5-补充诊断", example = "1")
    private Integer diagnosisType;

    @Schema(description = "ICD-10诊断编码", example = "J00.X00")
    private String diagnosisCode;

    @Schema(description = "诊断名称，模糊查询", example = "上呼吸道")
    private String diagnosisName;

    @Schema(description = "诊断状态：1-初步诊断 2-确定诊断 3-修正诊断", example = "1")
    private Integer diagnosisStatus;

    @Schema(description = "诊断时间-开始", example = "2026-06-01 00:00:00")
    private LocalDateTime diagnoseTimeStart;

    @Schema(description = "诊断时间-结束", example = "2026-06-18 23:59:59")
    private LocalDateTime diagnoseTimeEnd;

}
