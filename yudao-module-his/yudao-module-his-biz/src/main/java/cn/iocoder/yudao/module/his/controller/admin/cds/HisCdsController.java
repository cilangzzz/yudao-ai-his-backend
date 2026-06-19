package cn.iocoder.yudao.module.his.controller.admin.cds;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.his.controller.admin.cds.vo.CdsCheckReqVO;
import cn.iocoder.yudao.module.his.controller.admin.cds.vo.CdsCheckRespVO;
import cn.iocoder.yudao.module.his.service.cds.HisCdsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * CDS 合理用药校验 Controller
 */
@Tag(name = "管理后台 - CDS合理用药校验")
@RestController
@RequestMapping("/his/cds")
@Validated
public class HisCdsController {

    @Resource
    private HisCdsService cdsService;

    @PostMapping("/check")
    @Operation(summary = "处方综合校验")
    @PreAuthorize("@ss.hasPermission('his:cds:check')")
    public CommonResult<CdsCheckRespVO> checkPrescription(@Valid @RequestBody CdsCheckReqVO reqVO) {
        return success(cdsService.checkPrescription(reqVO));
    }

    @PostMapping("/check-interaction")
    @Operation(summary = "药物相互作用校验")
    @PreAuthorize("@ss.hasPermission('his:cds:check')")
    public CommonResult<CdsCheckRespVO> checkInteraction(@Valid @RequestBody CdsCheckReqVO reqVO) {
        return success(cdsService.checkInteraction(reqVO));
    }

    @PostMapping("/check-allergy")
    @Operation(summary = "过敏校验", description = "检查处方药品是否与患者过敏史冲突")
    @PreAuthorize("@ss.hasPermission('his:cds:check')")
    public CommonResult<CdsCheckRespVO> checkAllergy(
            @RequestParam("patientId") Long patientId,
            @RequestBody CdsCheckReqVO.DrugItem[] drugItems) {
        return success(cdsService.checkAllergy(patientId, drugItems));
    }

    @PostMapping("/check-dosage")
    @Operation(summary = "剂量校验", description = "检查药品剂量是否超出安全范围")
    @PreAuthorize("@ss.hasPermission('his:cds:check')")
    public CommonResult<CdsCheckRespVO> checkDosage(@Valid @RequestBody CdsCheckReqVO reqVO) {
        return success(cdsService.checkDosage(reqVO));
    }

    @PostMapping("/check-contraindication")
    @Operation(summary = "配伍禁忌校验", description = "检查药品间是否存在配伍禁忌")
    @PreAuthorize("@ss.hasPermission('his:cds:check')")
    public CommonResult<CdsCheckRespVO> checkContraindication(@Valid @RequestBody CdsCheckReqVO reqVO) {
        return success(cdsService.checkContraindication(reqVO));
    }

}