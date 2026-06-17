package cn.iocoder.yudao.module.his.controller.admin.nursing;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingMeasureDO;
import cn.iocoder.yudao.module.his.service.nursing.HisNursingMeasureService;
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

@Tag(name = "管理后台 - 护理措施")
@RestController
@RequestMapping("/his/nursing-measure")
@Validated
public class HisNursingMeasureController {

    @Resource
    private HisNursingMeasureService nursingMeasureService;

    @PostMapping("/create")
    @Operation(summary = "创建护理措施")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:create')")
    public CommonResult<Long> createNursingMeasure(@Valid @RequestBody HisNursingMeasureSaveReqVO createReqVO) {
        return success(nursingMeasureService.createNursingMeasure(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护理措施")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:update')")
    public CommonResult<Boolean> updateNursingMeasure(@Valid @RequestBody HisNursingMeasureSaveReqVO updateReqVO) {
        nursingMeasureService.updateNursingMeasure(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护理措施")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:delete')")
    public CommonResult<Boolean> deleteNursingMeasure(@RequestParam("id") Long id) {
        nursingMeasureService.deleteNursingMeasure(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取护理措施")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:query')")
    public CommonResult<HisNursingMeasureRespVO> getNursingMeasure(@RequestParam("id") Long id) {
        HisNursingMeasureDO measure = nursingMeasureService.getNursingMeasure(id);
        return success(BeanUtils.toBean(measure, HisNursingMeasureRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取护理措施分页")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:query')")
    public CommonResult<PageResult<HisNursingMeasureRespVO>> getNursingMeasurePage(@Valid HisNursingMeasurePageReqVO pageReqVO) {
        PageResult<HisNursingMeasureDO> pageResult = nursingMeasureService.getNursingMeasurePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisNursingMeasureRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "获取住院的护理措施列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:query')")
    public CommonResult<List<HisNursingMeasureRespVO>> getNursingMeasureListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisNursingMeasureDO> list = nursingMeasureService.getNursingMeasureListByAdmission(admissionId);
        return success(BeanUtils.toBean(list, HisNursingMeasureRespVO.class));
    }

    @GetMapping("/list-by-assessment")
    @Operation(summary = "获取评估关联的护理措施列表")
    @Parameter(name = "assessmentId", description = "评估ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:query')")
    public CommonResult<List<HisNursingMeasureRespVO>> getNursingMeasureListByAssessment(@RequestParam("assessmentId") Long assessmentId) {
        List<HisNursingMeasureDO> list = nursingMeasureService.getNursingMeasureListByAssessment(assessmentId);
        return success(BeanUtils.toBean(list, HisNursingMeasureRespVO.class));
    }

    @PostMapping("/start")
    @Operation(summary = "开始执行护理措施")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:execute')")
    public CommonResult<Boolean> startNursingMeasure(@RequestParam("id") Long id) {
        nursingMeasureService.startNursingMeasure(id);
        return success(true);
    }

    @PostMapping("/complete")
    @Operation(summary = "完成护理措施")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:execute')")
    public CommonResult<Boolean> completeNursingMeasure(@RequestParam("id") Long id) {
        nursingMeasureService.completeNursingMeasure(id);
        return success(true);
    }

    @PostMapping("/stop")
    @Operation(summary = "停止护理措施")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:execute')")
    public CommonResult<Boolean> stopNursingMeasure(
            @RequestParam("id") Long id,
            @RequestParam(value = "reason", required = false) String reason) {
        nursingMeasureService.stopNursingMeasure(id, reason);
        return success(true);
    }

    @PostMapping("/evaluate")
    @Operation(summary = "护理措施效果评价")
    @PreAuthorize("@ss.hasPermission('his:nursing-measure:evaluate')")
    public CommonResult<Boolean> evaluateNursingMeasure(@Valid @RequestBody HisNursingMeasureEvaluationReqVO evaluationReqVO) {
        nursingMeasureService.evaluateNursingMeasure(evaluationReqVO);
        return success(true);
    }
}