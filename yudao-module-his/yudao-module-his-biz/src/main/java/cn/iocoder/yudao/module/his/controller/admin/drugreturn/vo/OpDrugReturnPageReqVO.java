package cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 退药申请 分页 Request VO
 */
@Schema(description = "管理后台 - 退药申请分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class OpDrugReturnPageReqVO extends PageParam {

    @Schema(description = "退药单号", example = "TY202606180001")
    private String returnNo;

    @Schema(description = "退药类型：1-门诊退药 2-住院退药", example = "1")
    private Integer returnType;

    @Schema(description = "原处方编号", example = "CF202606180001")
    private String prescriptionNo;

    @Schema(description = "原发药单号", example = "FY202606180001")
    private String dispenseNo;

    @Schema(description = "患者ID", example = "1")
    private Long patientId;

    @Schema(description = "患者姓名", example = "张三")
    private String patientName;

    @Schema(description = "患者电话", example = "13800138000")
    private String patientPhone;

    @Schema(description = "身份证号", example = "110101199001011234")
    private String idCardNo;

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "药房ID", example = "1")
    private Long pharmacyId;

    @Schema(description = "退药状态：1-待审核 2-审核通过 3-审核拒绝 4-已入库 5-已退款 6-已取消", example = "1")
    private Integer returnStatus;

    @Schema(description = "退药原因类型：1-药品不良反应 2-医嘱变更 3-患者拒服 4-药品质量问题 5-重复开药 6-其他", example = "1")
    private Integer returnReasonType;

    @Schema(description = "申请时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] applyTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
