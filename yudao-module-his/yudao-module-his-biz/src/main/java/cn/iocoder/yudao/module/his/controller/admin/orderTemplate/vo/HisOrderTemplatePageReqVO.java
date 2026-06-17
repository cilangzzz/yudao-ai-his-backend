package cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 医嘱模板分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HisOrderTemplatePageReqVO extends PageParam {

    @Schema(description = "模板编码", example = "TPL001")
    private String templateCode;

    @Schema(description = "模板名称", example = "肺炎入院常规医嘱")
    private String templateName;

    @Schema(description = "模板分类", example = "1")
    private Integer templateCategory;

    @Schema(description = "医嘱类型", example = "1")
    private Integer orderType;

    @Schema(description = "科室ID", example = "100")
    private Long deptId;

    @Schema(description = "医生ID", example = "200")
    private Long doctorId;

    @Schema(description = "疾病名称", example = "肺炎")
    private String diagnosisName;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime[] createTime;

}