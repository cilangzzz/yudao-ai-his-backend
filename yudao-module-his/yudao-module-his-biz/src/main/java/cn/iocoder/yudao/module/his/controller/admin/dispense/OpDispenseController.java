package cn.iocoder.yudao.module.his.controller.admin.dispense;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispenseItemRespVO;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispensePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispenseRespVO;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispenseSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseDO;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseItemDO;
import cn.iocoder.yudao.module.his.service.dispense.OpDispenseService;
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
 * HIS 门诊发药管理 Controller
 *
 * 提供发药单的创建、调配、发药、退药等全流程管理接口
 */
@Tag(name = "管理后台 - HIS门诊发药管理")
@RestController
@RequestMapping("/his/dispense")
@Validated
public class OpDispenseController {

    @Resource
    private OpDispenseService dispenseService;

    // ==================== 发药单基本操作 ====================

    @PostMapping("/create")
    @Operation(summary = "创建发药单")
    @PreAuthorize("@ss.hasPermission('his:dispense:create')")
    public CommonResult<Long> createDispense(@Valid @RequestBody OpDispenseSaveReqVO createReqVO) {
        return success(dispenseService.createDispense(createReqVO));
    }

    @PostMapping("/create-by-prescription")
    @Operation(summary = "根据处方创建发药单")
    @PreAuthorize("@ss.hasPermission('his:dispense:create')")
    public CommonResult<Long> createDispenseByPrescription(
            @RequestParam("prescriptionId") Long prescriptionId,
            @RequestParam("pharmacyId") Long pharmacyId,
            @RequestParam(value = "pharmacyName", required = false) String pharmacyName) {
        return success(dispenseService.createDispenseByPrescription(prescriptionId, pharmacyId, pharmacyName));
    }

    @PutMapping("/update")
    @Operation(summary = "更新发药单")
    @PreAuthorize("@ss.hasPermission('his:dispense:update')")
    public CommonResult<Boolean> updateDispense(@Valid @RequestBody OpDispenseSaveReqVO updateReqVO) {
        dispenseService.updateDispense(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除发药单")
    @Parameter(name = "id", description = "发药单ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:delete')")
    public CommonResult<Boolean> deleteDispense(@RequestParam("id") Long id) {
        dispenseService.deleteDispense(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取发药单详情")
    @Parameter(name = "id", description = "发药单ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<OpDispenseRespVO> getDispense(@RequestParam("id") Long id) {
        OpDispenseDO dispense = dispenseService.getDispense(id);
        OpDispenseRespVO respVO = BeanUtils.toBean(dispense, OpDispenseRespVO.class);
        // 获取发药明细
        List<OpDispenseItemDO> items = dispenseService.getDispenseItems(id);
        respVO.setItems(BeanUtils.toBean(items, OpDispenseItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据发药单号获取发药单")
    @Parameter(name = "dispenseNo", description = "发药单号", required = true, example = "DS202606180001")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<OpDispenseRespVO> getDispenseByNo(@RequestParam("dispenseNo") String dispenseNo) {
        OpDispenseDO dispense = dispenseService.getDispenseByNo(dispenseNo);
        OpDispenseRespVO respVO = BeanUtils.toBean(dispense, OpDispenseRespVO.class);
        // 获取发药明细
        List<OpDispenseItemDO> items = dispenseService.getDispenseItems(dispense.getId());
        respVO.setItems(BeanUtils.toBean(items, OpDispenseItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/get-by-prescription")
    @Operation(summary = "根据处方ID获取发药单")
    @Parameter(name = "prescriptionId", description = "处方ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<OpDispenseRespVO> getDispenseByPrescriptionId(@RequestParam("prescriptionId") Long prescriptionId) {
        OpDispenseDO dispense = dispenseService.getDispenseByPrescriptionId(prescriptionId);
        if (dispense == null) {
            return success(null);
        }
        OpDispenseRespVO respVO = BeanUtils.toBean(dispense, OpDispenseRespVO.class);
        // 获取发药明细
        List<OpDispenseItemDO> items = dispenseService.getDispenseItems(dispense.getId());
        respVO.setItems(BeanUtils.toBean(items, OpDispenseItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获取发药单分页")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<PageResult<OpDispenseRespVO>> getDispensePage(@Valid OpDispensePageReqVO pageReqVO) {
        PageResult<OpDispenseDO> pageResult = dispenseService.getDispensePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpDispenseRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获取发药明细列表")
    @Parameter(name = "dispenseId", description = "发药单ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<List<OpDispenseItemRespVO>> getDispenseItems(@RequestParam("dispenseId") Long dispenseId) {
        List<OpDispenseItemDO> items = dispenseService.getDispenseItems(dispenseId);
        return success(BeanUtils.toBean(items, OpDispenseItemRespVO.class));
    }

    @GetMapping("/list-by-register")
    @Operation(summary = "根据挂号ID获取发药记录列表")
    @Parameter(name = "registerId", description = "挂号ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<List<OpDispenseRespVO>> getDispensesByRegisterId(@RequestParam("registerId") Long registerId) {
        List<OpDispenseDO> list = dispenseService.getDispensesByRegisterId(registerId);
        return success(BeanUtils.toBean(list, OpDispenseRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获取发药记录列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<List<OpDispenseRespVO>> getDispensesByPatientId(@RequestParam("patientId") Long patientId) {
        List<OpDispenseDO> list = dispenseService.getDispensesByPatientId(patientId);
        return success(BeanUtils.toBean(list, OpDispenseRespVO.class));
    }

    // ==================== 调配发药流程 ====================

    @PostMapping("/dispense")
    @Operation(summary = "调配发药单")
    @PreAuthorize("@ss.hasPermission('his:dispense:dispense')")
    public CommonResult<Boolean> dispense(
            @RequestParam("id") Long id,
            @RequestParam("pharmacistId") Long pharmacistId,
            @RequestParam("pharmacistName") String pharmacistName) {
        dispenseService.dispense(id, pharmacistId, pharmacistName);
        return success(true);
    }

    @PostMapping("/send")
    @Operation(summary = "发药")
    @PreAuthorize("@ss.hasPermission('his:dispense:send')")
    public CommonResult<Boolean> send(
            @RequestParam("id") Long id,
            @RequestParam("pharmacistId") Long pharmacistId,
            @RequestParam("pharmacistName") String pharmacistName) {
        dispenseService.send(id, pharmacistId, pharmacistName);
        return success(true);
    }

    @PostMapping("/batch-send")
    @Operation(summary = "批量发药")
    @PreAuthorize("@ss.hasPermission('his:dispense:send')")
    public CommonResult<Boolean> batchSend(
            @RequestBody List<Long> dispenseIds,
            @RequestParam("pharmacistId") Long pharmacistId,
            @RequestParam("pharmacistName") String pharmacistName) {
        dispenseService.batchSend(dispenseIds, pharmacistId, pharmacistName);
        return success(true);
    }

    // ==================== 退药 ====================

    @PostMapping("/return")
    @Operation(summary = "退药")
    @PreAuthorize("@ss.hasPermission('his:dispense:return')")
    public CommonResult<Boolean> returnDrug(
            @RequestParam("id") Long id,
            @RequestParam(value = "pharmacistId", required = false) Long pharmacistId,
            @RequestParam(value = "pharmacistName", required = false) String pharmacistName,
            @RequestParam("reason") String reason) {
        if (pharmacistId != null && pharmacistName != null) {
            dispenseService.returnDrug(id, pharmacistId, pharmacistName, reason);
        } else {
            dispenseService.returnDrug(id, reason);
        }
        return success(true);
    }

    // ==================== 查询统计 ====================

    @GetMapping("/pending-dispense-list")
    @Operation(summary = "获取待调配的发药记录列表")
    @Parameter(name = "pharmacyId", description = "药房ID（可选）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<List<OpDispenseRespVO>> getPendingDispenseList(
            @RequestParam(value = "pharmacyId", required = false) Long pharmacyId) {
        List<OpDispenseDO> list = dispenseService.getPendingDispenseList(pharmacyId);
        return success(BeanUtils.toBean(list, OpDispenseRespVO.class));
    }

    @GetMapping("/pending-send-list")
    @Operation(summary = "获取待发药的记录列表")
    @Parameter(name = "pharmacyId", description = "药房ID（可选）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<List<OpDispenseRespVO>> getPendingSendList(
            @RequestParam(value = "pharmacyId", required = false) Long pharmacyId) {
        List<OpDispenseDO> list = dispenseService.getPendingSendList(pharmacyId);
        return success(BeanUtils.toBean(list, OpDispenseRespVO.class));
    }

    @GetMapping("/count-by-status")
    @Operation(summary = "统计发药数量（按药房和状态）")
    @PreAuthorize("@ss.hasPermission('his:dispense:query')")
    public CommonResult<Long> countByPharmacyIdAndStatus(
            @RequestParam(value = "pharmacyId", required = false) Long pharmacyId,
            @RequestParam("dispenseStatus") Integer dispenseStatus) {
        return success(dispenseService.countByPharmacyIdAndStatus(pharmacyId, dispenseStatus));
    }

}