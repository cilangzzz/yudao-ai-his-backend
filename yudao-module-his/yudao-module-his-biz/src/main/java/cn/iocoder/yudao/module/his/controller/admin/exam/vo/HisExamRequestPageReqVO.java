package cn.iocoder.yudao.module.his.controller.admin.exam.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 检查申请分页请求 VO
 */
@Schema(description = "管理后台 - HIS检查申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisExamRequestPageReqVO extends PageParam {

    @Schema(description = "申请单号", example = "EX202606180001")
    private String requestNo;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "就诊类型:1-门诊 2-住院 3-急诊", example = "1")
    private Integer encounterType;

    @Schema(description = "就诊ID", example = "100")
    private Long encounterId;

    @Schema(description = "申请科室ID", example = "10")
    private Long deptId;

    @Schema(description = "申请医生ID", example = "100")
    private Long doctorId;

    @Schema(description = "检查类型:1-影像检查 2-检验检查 3-病理检查 4-功能检查", example = "1")
    private Integer examType;

    @Schema(description = "检查类别", example = "CT")
    private String examCategory;

    @Schema(description = "申请状态", example = "1")
    private Integer requestStatus;

    @Schema(description = "紧急程度:0-普通 1-急诊 2-加急", example = "0")
    private Integer urgency;

    @Schema(description = "支付状态:0-未支付 1-已支付 2-已退费", example = "0")
    private Integer payStatus;

    @Schema(description = "预约检查时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] appointmentTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
