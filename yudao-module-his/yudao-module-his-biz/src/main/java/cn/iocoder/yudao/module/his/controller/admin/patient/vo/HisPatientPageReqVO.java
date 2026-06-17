package cn.iocoder.yudao.module.his.controller.admin.patient.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 患者分页请求 VO
 */
@Schema(description = "管理后台 - HIS患者分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HisPatientPageReqVO extends PageParam {

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "身份证号", example = "320102199001011234")
    private String idCardNo;

    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(description = "状态：1-正常 2-注销 3-死亡", example = "1")
    private Integer status;

}
