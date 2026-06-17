package cn.iocoder.yudao.module.his.controller.admin.prescription;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionItemRespVO;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionRespVO;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionItemDO;
import cn.iocoder.yudao.module.his.service.prescription.OpPrescriptionService;
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
 * HIS 门诊处方管理 Controller
 *
 * 提供处方的开立、审核、调配、发药、退药等全流程管理接口
 */
@Tag(name = "管理后台 - HIS门诊处方管理")
@RestController
@RequestMapping("/his/prescription")
@Validated
public class OpPrescriptionController {

    @Resource
    private OpPrescriptionService prescriptionService;

    // ==================== 处方基本操作 ====================

    @PostMapping("/create")
    @Operation(summary = "开立处方")
    @PreAuthorize("@ss.hasPermission('his:prescription:create')")
    public CommonResult<Long> createPrescription(@Valid @RequestBody OpPrescriptionSaveReqVO createReqVO) {
        return success(prescriptionService.createPrescription(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新处方")
    @PreAuthorize("@ss.hasPermission('his:prescription:update')")
    public CommonResult<Boolean> updatePrescription(@Valid @RequestBody OpPrescriptionSaveReqVO updateReqVO) {
        prescriptionService.updatePrescription(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除处方")
    @Parameter(name = "id", description = "处方ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:delete')")
    public CommonResult<Boolean> deletePrescription(@RequestParam("id") Long id) {
        prescriptionService.deletePrescription(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取处方详情")
    @Parameter(name = "id", description = "处方ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<OpPrescriptionRespVO> getPrescription(@RequestParam("id") Long id) {
        OpPrescriptionDO prescription = prescriptionService.getPrescription(id);
        OpPrescriptionRespVO respVO = BeanUtils.toBean(prescription, OpPrescriptionRespVO.class);
        // 获取处方明细
        List<OpPrescriptionItemDO> items = prescriptionService.getPrescriptionItems(id);
        respVO.setItems(BeanUtils.toBean(items, OpPrescriptionItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据处方编号获取处方")
    @Parameter(name = "prescriptionNo", description = "处方编号", required = true, example = "RX202606180001")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<OpPrescriptionRespVO> getPrescriptionByNo(@RequestParam("prescriptionNo") String prescriptionNo) {
        OpPrescriptionDO prescription = prescriptionService.getPrescriptionByNo(prescriptionNo);
        OpPrescriptionRespVO respVO = BeanUtils.toBean(prescription, OpPrescriptionRespVO.class);
        // 获取处方明细
        List<OpPrescriptionItemDO> items = prescriptionService.getPrescriptionItems(prescription.getId());
        respVO.setItems(BeanUtils.toBean(items, OpPrescriptionItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获取处方分页")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<PageResult<OpPrescriptionRespVO>> getPrescriptionPage(@Valid OpPrescriptionPageReqVO pageReqVO) {
        PageResult<OpPrescriptionDO> pageResult = prescriptionService.getPrescriptionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpPrescriptionRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获取处方明细列表")
    @Parameter(name = "prescriptionId", description = "处方ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<List<OpPrescriptionItemRespVO>> getPrescriptionItems(@RequestParam("prescriptionId") Long prescriptionId) {
        List<OpPrescriptionItemDO> items = prescriptionService.getPrescriptionItems(prescriptionId);
        return success(BeanUtils.toBean(items, OpPrescriptionItemRespVO.class));
    }

    @GetMapping("/list-by-register")
    @Operation(summary = "根据挂号ID获取处方列表")
    @Parameter(name = "registerId", description = "挂号ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<List<OpPrescriptionRespVO>> getPrescriptionsByRegisterId(@RequestParam("registerId") Long registerId) {
        List<OpPrescriptionDO> list = prescriptionService.getPrescriptionsByRegisterId(registerId);
        return success(BeanUtils.toBean(list, OpPrescriptionRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获取处方列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<List<OpPrescriptionRespVO>> getPrescriptionsByPatientId(@RequestParam("patientId") Long patientId) {
        List<OpPrescriptionDO> list = prescriptionService.getPrescriptionsByPatientId(patientId);
        return success(BeanUtils.toBean(list, OpPrescriptionRespVO.class));
    }

    // ==================== 处方审核 ====================

    @PostMapping("/audit-pass")
    @Operation(summary = "审核处方（通过）")
    @PreAuthorize("@ss.hasPermission('his:prescription:audit')")
    public CommonResult<Boolean> auditPass(@RequestParam("id") Long id,
                                           @RequestParam("pharmacistId") Long pharmacistId,
                                           @RequestParam("pharmacistName") String pharmacistName,
                                           @RequestParam(value = "remark", required = false) String remark) {
        prescriptionService.auditPass(id, pharmacistId, pharmacistName, remark);
        return success(true);
    }

    @PostMapping("/audit-reject")
    @Operation(summary = "审核处方（退回）")
    @PreAuthorize("@ss.hasPermission('his:prescription:audit')")
    public CommonResult<Boolean> auditReject(@RequestParam("id") Long id,
                                             @RequestParam("pharmacistId") Long pharmacistId,
                                             @RequestParam("pharmacistName") String pharmacistName,
                                             @RequestParam("reason") String reason) {
        prescriptionService.auditReject(id, pharmacistId, pharmacistName, reason);
        return success(true);
    }

    // ==================== 调配发药 ====================

    @PostMapping("/dispense")
    @Operation(summary = "调配处方")
    @PreAuthorize("@ss.hasPermission('his:prescription:dispense')")
    public CommonResult<Boolean> dispense(@RequestParam("id") Long id,
                                          @RequestParam("pharmacistId") Long pharmacistId,
                                          @RequestParam("pharmacistName") String pharmacistName) {
        prescriptionService.dispense(id, pharmacistId, pharmacistName);
        return success(true);
    }

    @PostMapping("/send")
    @Operation(summary = "发药")
    @PreAuthorize("@ss.hasPermission('his:prescription:send')")
    public CommonResult<Boolean> send(@RequestParam("id") Long id,
                                      @RequestParam("pharmacistId") Long pharmacistId,
                                      @RequestParam("pharmacistName") String pharmacistName) {
        prescriptionService.send(id, pharmacistId, pharmacistName);
        return success(true);
    }

    @PostMapping("/batch-send")
    @Operation(summary = "批量发药")
    @PreAuthorize("@ss.hasPermission('his:prescription:send')")
    public CommonResult<Boolean> batchSend(@RequestBody List<Long> prescriptionIds,
                                           @RequestParam("pharmacistId") Long pharmacistId,
                                           @RequestParam("pharmacistName") String pharmacistName) {
        prescriptionService.batchSend(prescriptionIds, pharmacistId, pharmacistName);
        return success(true);
    }

    // ==================== 退药 ====================

    @PostMapping("/return")
    @Operation(summary = "退药")
    @PreAuthorize("@ss.hasPermission('his:prescription:return')")
    public CommonResult<Boolean> returnDrug(@RequestParam("id") Long id,
                                            @RequestParam(value = "pharmacistId", required = false) Long pharmacistId,
                                            @RequestParam(value = "pharmacistName", required = false) String pharmacistName,
                                            @RequestParam("reason") String reason) {
        if (pharmacistId != null && pharmacistName != null) {
            prescriptionService.returnDrug(id, pharmacistId, pharmacistName, reason);
        } else {
            prescriptionService.returnDrug(id, reason);
        }
        return success(true);
    }

    // ==================== 皮试管理 ====================

    @PostMapping("/record-skin-test")
    @Operation(summary = "记录皮试结果")
    @PreAuthorize("@ss.hasPermission('his:prescription:skin-test')")
    public CommonResult<Boolean> recordSkinTest(@RequestParam("itemId") Long itemId,
                                                @RequestParam("skinTestResult") String skinTestResult,
                                                @RequestParam("nurseId") Long nurseId) {
        prescriptionService.recordSkinTest(itemId, skinTestResult, nurseId);
        return success(true);
    }

    @GetMapping("/pending-skin-test-items")
    @Operation(summary = "获取待皮试的处方明细列表")
    @Parameter(name = "prescriptionId", description = "处方ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<List<OpPrescriptionItemRespVO>> getPendingSkinTestItems(@RequestParam("prescriptionId") Long prescriptionId) {
        List<OpPrescriptionItemDO> items = prescriptionService.getPendingSkinTestItems(prescriptionId);
        return success(BeanUtils.toBean(items, OpPrescriptionItemRespVO.class));
    }

    // ==================== 查询统计 ====================

    @GetMapping("/pending-audit-list")
    @Operation(summary = "获取待审核处方列表")
    @Parameter(name = "deptId", description = "科室ID（可选）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<List<OpPrescriptionRespVO>> getPendingAuditList(@RequestParam(value = "deptId", required = false) Long deptId) {
        List<OpPrescriptionDO> list = prescriptionService.getPendingAuditList(deptId);
        return success(BeanUtils.toBean(list, OpPrescriptionRespVO.class));
    }

    @GetMapping("/pending-dispense-list")
    @Operation(summary = "获取待调配处方列表")
    @Parameter(name = "deptId", description = "科室ID（可选）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<List<OpPrescriptionRespVO>> getPendingDispenseList(@RequestParam(value = "deptId", required = false) Long deptId) {
        List<OpPrescriptionDO> list = prescriptionService.getPendingDispenseList(deptId);
        return success(BeanUtils.toBean(list, OpPrescriptionRespVO.class));
    }

    @GetMapping("/pending-send-list")
    @Operation(summary = "获取待发药处方列表")
    @Parameter(name = "deptId", description = "科室ID（可选）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<List<OpPrescriptionRespVO>> getPendingSendList(@RequestParam(value = "deptId", required = false) Long deptId) {
        List<OpPrescriptionDO> list = prescriptionService.getPendingSendList(deptId);
        return success(BeanUtils.toBean(list, OpPrescriptionRespVO.class));
    }

    @GetMapping("/count-by-status")
    @Operation(summary = "统计处方数量（按状态）")
    @PreAuthorize("@ss.hasPermission('his:prescription:query')")
    public CommonResult<Long> countByStatus(@RequestParam(value = "deptId", required = false) Long deptId,
                                            @RequestParam("status") Integer status) {
        return success(prescriptionService.countByStatus(deptId, status));
    }

}
