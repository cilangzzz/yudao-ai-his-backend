package cn.iocoder.yudao.module.his.controller.admin.diagnosis;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisRespVO;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.diagnosis.HisDiagnosisDO;
import cn.iocoder.yudao.module.his.service.diagnosis.HisDiagnosisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * HIS 诊断管理 Controller
 *
 * 对应FHIR资源: Condition
 * 管理患者诊断信息，支持门诊诊断、入院诊断、出院诊断等多种类型
 */
@Tag(name = "管理后台 - HIS诊断管理")
@RestController
@RequestMapping("/his/diagnosis")
@Validated
public class HisDiagnosisController {

    @Resource
    private HisDiagnosisService diagnosisService;

    @PostMapping("/create")
    @Operation(summary = "创建诊断记录")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:create')")
    public CommonResult<Long> createDiagnosis(@Valid @RequestBody HisDiagnosisSaveReqVO createReqVO) {
        return success(diagnosisService.createDiagnosis(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新诊断记录")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:update')")
    public CommonResult<Boolean> updateDiagnosis(@Valid @RequestBody HisDiagnosisSaveReqVO updateReqVO) {
        diagnosisService.updateDiagnosis(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除诊断记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:diagnosis:delete')")
    public CommonResult<Boolean> deleteDiagnosis(@RequestParam("id") Long id) {
        diagnosisService.deleteDiagnosis(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得诊断记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:query')")
    public CommonResult<HisDiagnosisRespVO> getDiagnosis(@RequestParam("id") Long id) {
        HisDiagnosisDO diagnosis = diagnosisService.getDiagnosis(id);
        return success(BeanUtils.toBean(diagnosis, HisDiagnosisRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得诊断记录分页")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:query')")
    public CommonResult<PageResult<HisDiagnosisRespVO>> getDiagnosisPage(@Valid HisDiagnosisPageReqVO pageReqVO) {
        PageResult<HisDiagnosisDO> pageResult = diagnosisService.getDiagnosisPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDiagnosisRespVO.class));
    }

    @GetMapping("/list-by-encounter")
    @Operation(summary = "根据就诊ID获得诊断列表")
    @Parameter(name = "encounterId", description = "就诊ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:query')")
    public CommonResult<List<HisDiagnosisRespVO>> getDiagnosisListByEncounter(@RequestParam("encounterId") Long encounterId) {
        List<HisDiagnosisDO> list = diagnosisService.getDiagnosisListByEncounterId(encounterId);
        return success(BeanUtils.toBean(list, HisDiagnosisRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获得诊断列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:query')")
    public CommonResult<List<HisDiagnosisRespVO>> getDiagnosisListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisDiagnosisDO> list = diagnosisService.getDiagnosisListByPatientId(patientId);
        return success(BeanUtils.toBean(list, HisDiagnosisRespVO.class));
    }

    @GetMapping("/list-by-encounter-type")
    @Operation(summary = "根据就诊ID和诊断类型获得诊断列表")
    @Parameter(name = "encounterId", description = "就诊ID", required = true, example = "1")
    @Parameter(name = "diagnosisType", description = "诊断类型：1-门诊诊断 2-入院诊断 3-出院诊断 4-修正诊断 5-补充诊断", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:query')")
    public CommonResult<List<HisDiagnosisRespVO>> getDiagnosisListByEncounterAndType(
            @RequestParam("encounterId") Long encounterId,
            @RequestParam("diagnosisType") Integer diagnosisType) {
        List<HisDiagnosisDO> list = diagnosisService.getDiagnosisListByEncounterIdAndType(encounterId, diagnosisType);
        return success(BeanUtils.toBean(list, HisDiagnosisRespVO.class));
    }

    @GetMapping("/primary-diagnosis")
    @Operation(summary = "获得就诊的主诊断")
    @Parameter(name = "encounterId", description = "就诊ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:query')")
    public CommonResult<HisDiagnosisRespVO> getPrimaryDiagnosis(@RequestParam("encounterId") Long encounterId) {
        HisDiagnosisDO diagnosis = diagnosisService.getPrimaryDiagnosis(encounterId);
        return success(BeanUtils.toBean(diagnosis, HisDiagnosisRespVO.class));
    }

    @PostMapping("/batch-save")
    @Operation(summary = "批量保存就诊诊断")
    @Parameter(name = "encounterId", description = "就诊ID", required = true, example = "1")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:diagnosis:create')")
    public CommonResult<Boolean> batchSaveDiagnosis(
            @RequestParam("encounterId") Long encounterId,
            @RequestParam("patientId") Long patientId,
            @Valid @RequestBody List<HisDiagnosisSaveReqVO> diagnosisList) {
        diagnosisService.batchSaveDiagnosis(encounterId, patientId, diagnosisList);
        return success(true);
    }

}