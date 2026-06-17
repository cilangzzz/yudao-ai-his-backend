package cn.iocoder.yudao.module.his.controller.admin.medication;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.medication.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.medication.HisMedicationAdminDO;
import cn.iocoder.yudao.module.his.service.medication.HisMedicationAdminService;
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

@Tag(name = "管理后台 - 给药记录（eMAR）")
@RestController
@RequestMapping("/his/medication-admin")
@Validated
public class HisMedicationAdminController {

    @Resource
    private HisMedicationAdminService medicationAdminService;

    @PostMapping("/create")
    @Operation(summary = "创建给药记录")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:create')")
    public CommonResult<Long> createMedicationAdmin(@Valid @RequestBody HisMedicationAdminSaveReqVO createReqVO) {
        return success(medicationAdminService.createMedicationAdmin(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新给药记录")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:update')")
    public CommonResult<Boolean> updateMedicationAdmin(@Valid @RequestBody HisMedicationAdminSaveReqVO updateReqVO) {
        medicationAdminService.updateMedicationAdmin(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除给药记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:medication-admin:delete')")
    public CommonResult<Boolean> deleteMedicationAdmin(@RequestParam("id") Long id) {
        medicationAdminService.deleteMedicationAdmin(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取给药记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:query')")
    public CommonResult<HisMedicationAdminRespVO> getMedicationAdmin(@RequestParam("id") Long id) {
        HisMedicationAdminDO admin = medicationAdminService.getMedicationAdmin(id);
        return success(BeanUtils.toBean(admin, HisMedicationAdminRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取给药记录分页")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:query')")
    public CommonResult<PageResult<HisMedicationAdminRespVO>> getMedicationAdminPage(@Valid HisMedicationAdminPageReqVO pageReqVO) {
        PageResult<HisMedicationAdminDO> pageResult = medicationAdminService.getMedicationAdminPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisMedicationAdminRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "获取住院的给药记录列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:query')")
    public CommonResult<List<HisMedicationAdminRespVO>> getMedicationAdminListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisMedicationAdminDO> list = medicationAdminService.getMedicationAdminListByAdmission(admissionId);
        return success(BeanUtils.toBean(list, HisMedicationAdminRespVO.class));
    }

    @GetMapping("/pending-list")
    @Operation(summary = "获取待执行的给药记录列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:query')")
    public CommonResult<List<HisMedicationAdminRespVO>> getPendingMedicationAdminList(@RequestParam("admissionId") Long admissionId) {
        List<HisMedicationAdminDO> list = medicationAdminService.getPendingMedicationAdminList(admissionId);
        return success(BeanUtils.toBean(list, HisMedicationAdminRespVO.class));
    }

    // ==================== 闭环给药核对接口 ====================

    @PostMapping("/verify-wristband")
    @Operation(summary = "腕带扫描验证")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:execute')")
    public CommonResult<HisMedicationAdminService.WristbandVerifyResult> verifyWristband(
            @Valid @RequestBody WristbandVerifyReqVO reqVO) {
        return success(medicationAdminService.verifyWristband(reqVO));
    }

    @PostMapping("/verify-drug")
    @Operation(summary = "药品条码扫描验证")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:execute')")
    public CommonResult<HisMedicationAdminService.DrugBarcodeVerifyResult> verifyDrugBarcode(
            @Valid @RequestBody DrugBarcodeVerifyReqVO reqVO) {
        return success(medicationAdminService.verifyDrugBarcode(reqVO));
    }

    @PostMapping("/confirm")
    @Operation(summary = "给药执行确认（闭环给药）")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:execute')")
    public CommonResult<Long> confirmMedicationAdmin(@Valid @RequestBody MedicationAdminConfirmReqVO reqVO) {
        return success(medicationAdminService.confirmMedicationAdmin(reqVO));
    }

    @PostMapping("/record-not-executed")
    @Operation(summary = "记录未执行原因")
    @PreAuthorize("@ss.hasPermission('his:medication-admin:execute')")
    public CommonResult<Boolean> recordNotExecutedReason(
            @RequestParam("adminId") Long adminId,
            @RequestParam("reason") String reason,
            @RequestParam("reasonType") String reasonType) {
        medicationAdminService.recordNotExecutedReason(adminId, reason, reasonType);
        return success(true);
    }
}