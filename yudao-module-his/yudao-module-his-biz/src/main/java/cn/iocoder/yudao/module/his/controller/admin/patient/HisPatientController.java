package cn.iocoder.yudao.module.his.controller.admin.patient;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientRespVO;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import cn.iocoder.yudao.module.his.service.patient.HisPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * HIS 患者管理 Controller
 */
@Tag(name = "管理后台 - HIS患者管理")
@RestController
@RequestMapping("/his/patient")
@Validated
public class HisPatientController {

    @Resource
    private HisPatientService patientService;

    @PostMapping("/create")
    @Operation(summary = "创建患者")
    @PreAuthorize("@ss.hasPermission('his:patient:create')")
    public CommonResult<Long> createPatient(@Valid @RequestBody HisPatientSaveReqVO createReqVO) {
        return success(patientService.createPatient(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新患者")
    @PreAuthorize("@ss.hasPermission('his:patient:update')")
    public CommonResult<Boolean> updatePatient(@Valid @RequestBody HisPatientSaveReqVO updateReqVO) {
        patientService.updatePatient(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除患者")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:patient:delete')")
    public CommonResult<Boolean> deletePatient(@RequestParam("id") Long id) {
        patientService.deletePatient(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得患者")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:patient:query')")
    public CommonResult<HisPatientRespVO> getPatient(@RequestParam("id") Long id) {
        HisPatientDO patient = patientService.getPatient(id);
        return success(BeanUtils.toBean(patient, HisPatientRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得患者分页")
    @PreAuthorize("@ss.hasPermission('his:patient:query')")
    public CommonResult<PageResult<HisPatientRespVO>> getPatientPage(@Valid HisPatientPageReqVO pageReqVO) {
        PageResult<HisPatientDO> pageResult = patientService.getPatientPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisPatientRespVO.class));
    }

    @GetMapping("/get-by-id-card")
    @Operation(summary = "根据身份证号获得患者")
    @Parameter(name = "idCardNo", description = "身份证号", required = true, example = "320102199001011234")
    @PreAuthorize("@ss.hasPermission('his:patient:query')")
    public CommonResult<HisPatientRespVO> getPatientByIdCard(@RequestParam("idCardNo") String idCardNo) {
        HisPatientDO patient = patientService.getPatientByIdCardNo(idCardNo);
        return success(BeanUtils.toBean(patient, HisPatientRespVO.class));
    }

}
