package cn.iocoder.yudao.module.his.controller.admin.lab.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 检验申请分页请求 VO
 */
@Schema(description = "管理后台 - HIS检验申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisLabRequestPageReqVO extends PageParam {

    @Schema(description = "申请单号", example = "LB202606180001")
    private String requestNo;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "就诊类型:1-门诊 2-住院 3-急诊", example = "1")
    private Integer sourceType;

    @Schema(description = "就诊ID", example = "100")
    private Long sourceId;

    @Schema(description = "申请科室ID", example = "10")
    private Long deptId;

    @Schema(description = "申请医生ID", example = "100")
    private Long doctorId;

    @Schema(description = "检验类型:1-常规检验 2-急诊检验 3-特检", example = "1")
    private Integer labType;

    @Schema(description = "检验类别", example = "血常规")
    private String labCategory;

    @Schema(description = "标本类型:1-血液 2-尿液 3-粪便 4-痰液 5-其他", example = "1")
    private Integer specimenType;

    @Schema(description = "申请状态:1-已申请 2-已采集 3-已接收 4-检验中 5-已完成 6-已报告 7-已审核 8-已取消 9-已退费", example = "1")
    private Integer requestStatus;

    @Schema(description = "标本状态", example = "1")
    private Integer specimenStatus;

    @Schema(description = "紧急程度:0-普通 1-急诊 2-加急", example = "0")
    private Integer urgency;

    @Schema(description = "危急值标志:0-无 1-有", example = "0")
    private Integer criticalFlag;

    @Schema(description = "支付状态:0-未支付 1-已支付 2-已退费", example = "0")
    private Integer payStatus;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] requestTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
