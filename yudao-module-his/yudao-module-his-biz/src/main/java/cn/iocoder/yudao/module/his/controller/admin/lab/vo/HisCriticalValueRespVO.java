package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 危急值响应 VO
 */
@Schema(description = "管理后台 - 危急值信息")
@Data
public class HisCriticalValueRespVO {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "危急值编号")
    private String criticalValueNo;

    @Schema(description = "检验申请ID")
    private Long labRequestId;

    @Schema(description = "检验申请单号")
    private String requestNo;

    @Schema(description = "患者ID")
    private Long patientId;

    @Schema(description = "患者姓名")
    private String patientName;

    @Schema(description = "就诊ID")
    private Long visitId;

    @Schema(description = "就诊类型: 1门诊/2住院")
    private Integer visitType;

    @Schema(description = "科室ID")
    private Long deptId;

    @Schema(description = "科室名称")
    private String deptName;

    @Schema(description = "检验项目名称")
    private String itemName;

    @Schema(description = "检验结果")
    private String resultValue;

    @Schema(description = "结果单位")
    private String resultUnit;

    @Schema(description = "危急值下限")
    private String lowerLimit;

    @Schema(description = "危急值上限")
    private String upperLimit;

    @Schema(description = "危急值类型: 高/低")
    private String criticalType;

    @Schema(description = "检出时间")
    private LocalDateTime detectedTime;

    @Schema(description = "检出人姓名")
    private String detectorName;

    @Schema(description = "通知时间")
    private LocalDateTime notifyTime;

    @Schema(description = "通知人姓名")
    private String notifierName;

    @Schema(description = "接收人姓名")
    private String receiverName;

    @Schema(description = "确认时间")
    private LocalDateTime confirmTime;

    @Schema(description = "确认人姓名")
    private String confirmerName;

    @Schema(description = "处理时间")
    private LocalDateTime processTime;

    @Schema(description = "处理人姓名")
    private String processorName;

    @Schema(description = "处理结果")
    private String processResult;

    @Schema(description = "状态: 1检出 2已通知 3已确认 4已处理 5超时升级")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "是否超时")
    private Boolean timeoutFlag;

    @Schema(description = "超时时间(分钟)")
    private Integer timeoutMinutes;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}