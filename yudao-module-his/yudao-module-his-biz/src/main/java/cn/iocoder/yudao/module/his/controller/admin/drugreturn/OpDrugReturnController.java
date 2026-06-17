package cn.iocoder.yudao.module.his.controller.admin.drugreturn;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnItemDO;
import cn.iocoder.yudao.module.his.enums.*;
import cn.iocoder.yudao.module.his.service.drugreturn.OpDrugReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

/**
 * 退药申请 Controller
 */
@Tag(name = "管理后台 - 退药申请")
@RestController
@RequestMapping("/his/drug-return")
@Validated
public class OpDrugReturnController {

    @Resource
    private OpDrugReturnService drugReturnService;

    @PostMapping("/create")
    @Operation(summary = "创建退药申请")
    @PreAuthorize("@ss.hasPermission('his:drug-return:create')")
    public CommonResult<Long> createDrugReturn(@Valid @RequestBody OpDrugReturnSaveReqVO createReqVO) {
        return success(drugReturnService.createDrugReturn(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新退药申请")
    @PreAuthorize("@ss.hasPermission('his:drug-return:update')")
    public CommonResult<Boolean> updateDrugReturn(@Valid @RequestBody OpDrugReturnSaveReqVO updateReqVO) {
        drugReturnService.updateDrugReturn(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除退药申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:delete')")
    public CommonResult<Boolean> deleteDrugReturn(@RequestParam("id") Long id) {
        drugReturnService.deleteDrugReturn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得退药申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<OpDrugReturnRespVO> getDrugReturn(@RequestParam("id") Long id) {
        OpDrugReturnDO drugReturn = drugReturnService.getDrugReturn(id);
        OpDrugReturnRespVO respVO = BeanUtils.toBean(drugReturn, OpDrugReturnRespVO.class);
        if (respVO != null) {
            // 设置枚举名称
            respVO.setReturnTypeName(DrugReturnTypeEnum.getName(respVO.getReturnType()));
            respVO.setReturnStatusName(DrugReturnStatusEnum.getName(respVO.getReturnStatus()));
            respVO.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(respVO.getReturnReasonType()));
        }
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得退药申请分页")
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<PageResult<OpDrugReturnRespVO>> getDrugReturnPage(@Valid OpDrugReturnPageReqVO pageReqVO) {
        PageResult<OpDrugReturnDO> pageResult = drugReturnService.getDrugReturnPage(pageReqVO);
        PageResult<OpDrugReturnRespVO> respPageResult = BeanUtils.toBean(pageResult, OpDrugReturnRespVO.class);
        // 设置枚举名称
        respPageResult.getList().forEach(vo -> {
            vo.setReturnTypeName(DrugReturnTypeEnum.getName(vo.getReturnType()));
            vo.setReturnStatusName(DrugReturnStatusEnum.getName(vo.getReturnStatus()));
            vo.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(vo.getReturnReasonType()));
        });
        return success(respPageResult);
    }

    @GetMapping("/list")
    @Operation(summary = "获得退药申请列表")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<List<OpDrugReturnRespVO>> getDrugReturnList(@RequestParam("ids") List<Long> ids) {
        List<OpDrugReturnDO> list = drugReturnService.getDrugReturnList(ids);
        List<OpDrugReturnRespVO> respList = BeanUtils.toBean(list, OpDrugReturnRespVO.class);
        // 设置枚举名称
        respList.forEach(vo -> {
            vo.setReturnTypeName(DrugReturnTypeEnum.getName(vo.getReturnType()));
            vo.setReturnStatusName(DrugReturnStatusEnum.getName(vo.getReturnStatus()));
            vo.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(vo.getReturnReasonType()));
        });
        return success(respList);
    }

    @GetMapping("/items")
    @Operation(summary = "获得退药明细列表")
    @Parameter(name = "returnId", description = "退药ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<List<OpDrugReturnItemRespVO>> getDrugReturnItemList(@RequestParam("returnId") Long returnId) {
        List<OpDrugReturnItemDO> list = drugReturnService.getDrugReturnItemList(returnId);
        List<OpDrugReturnItemRespVO> respList = BeanUtils.toBean(list, OpDrugReturnItemRespVO.class);
        // 设置枚举名称和扩展字段
        respList.forEach(vo -> {
            vo.setDrugConditionName(DrugConditionEnum.getName(vo.getDrugCondition()));
            vo.setItemStatusName(DrugReturnStatusEnum.getName(vo.getItemStatus()));
            // 设置是否过期/近效期
            if (vo.getExpiryDate() != null) {
                vo.setExpired(vo.getExpiryDate().isBefore(java.time.LocalDate.now()));
                vo.setNearExpiry(vo.getExpiryDate().isBefore(java.time.LocalDate.now().plusDays(30)));
            }
        });
        return success(respList);
    }

    @PostMapping("/audit")
    @Operation(summary = "审核退药申请")
    @PreAuthorize("@ss.hasPermission('his:drug-return:audit')")
    public CommonResult<Boolean> auditDrugReturn(@Valid @RequestBody OpDrugReturnAuditReqVO auditReqVO) {
        drugReturnService.auditDrugReturn(auditReqVO);
        return success(true);
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消退药申请")
    @PreAuthorize("@ss.hasPermission('his:drug-return:cancel')")
    public CommonResult<Boolean> cancelDrugReturn(@RequestParam("id") Long id,
                                                   @RequestParam(value = "cancelReason", required = false) String cancelReason) {
        drugReturnService.cancelDrugReturn(id, cancelReason);
        return success(true);
    }

    @PostMapping("/inbound")
    @Operation(summary = "退药入库")
    @PreAuthorize("@ss.hasPermission('his:drug-return:inbound')")
    public CommonResult<String> inboundDrugReturn(@Valid @RequestBody OpDrugReturnInboundReqVO inboundReqVO) {
        String inboundNo = drugReturnService.inboundDrugReturn(inboundReqVO);
        return success(inboundNo);
    }

    @PostMapping("/refund")
    @Operation(summary = "退药退款")
    @Parameter(name = "id", description = "退药ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:refund')")
    public CommonResult<Boolean> refundDrugReturn(@RequestParam("id") Long id) {
        drugReturnService.refundDrugReturn(id);
        return success(true);
    }

    @GetMapping("/pending-count")
    @Operation(summary = "获取待审核退药数量")
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<Long> getPendingCount() {
        return success(drugReturnService.getPendingCount());
    }

    @GetMapping("/pending-by-pharmacy")
    @Operation(summary = "根据药房获取待处理退药列表")
    @Parameter(name = "pharmacyId", description = "药房ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<List<OpDrugReturnRespVO>> getPendingListByPharmacy(@RequestParam("pharmacyId") Long pharmacyId) {
        List<OpDrugReturnDO> list = drugReturnService.getPendingListByPharmacy(pharmacyId);
        List<OpDrugReturnRespVO> respList = BeanUtils.toBean(list, OpDrugReturnRespVO.class);
        // 设置枚举名称
        respList.forEach(vo -> {
            vo.setReturnTypeName(DrugReturnTypeEnum.getName(vo.getReturnType()));
            vo.setReturnStatusName(DrugReturnStatusEnum.getName(vo.getReturnStatus()));
            vo.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(vo.getReturnReasonType()));
        });
        return success(respList);
    }

    @GetMapping("/list-by-prescription")
    @Operation(summary = "根据处方ID获取退药记录")
    @Parameter(name = "prescriptionId", description = "处方ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<List<OpDrugReturnRespVO>> getDrugReturnListByPrescription(@RequestParam("prescriptionId") Long prescriptionId) {
        List<OpDrugReturnDO> list = drugReturnService.getDrugReturnListByPrescriptionId(prescriptionId);
        List<OpDrugReturnRespVO> respList = BeanUtils.toBean(list, OpDrugReturnRespVO.class);
        respList.forEach(vo -> {
            vo.setReturnTypeName(DrugReturnTypeEnum.getName(vo.getReturnType()));
            vo.setReturnStatusName(DrugReturnStatusEnum.getName(vo.getReturnStatus()));
            vo.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(vo.getReturnReasonType()));
        });
        return success(respList);
    }

    @GetMapping("/list-by-dispense")
    @Operation(summary = "根据发药ID获取退药记录")
    @Parameter(name = "dispenseId", description = "发药ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<List<OpDrugReturnRespVO>> getDrugReturnListByDispense(@RequestParam("dispenseId") Long dispenseId) {
        List<OpDrugReturnDO> list = drugReturnService.getDrugReturnListByDispenseId(dispenseId);
        List<OpDrugReturnRespVO> respList = BeanUtils.toBean(list, OpDrugReturnRespVO.class);
        respList.forEach(vo -> {
            vo.setReturnTypeName(DrugReturnTypeEnum.getName(vo.getReturnType()));
            vo.setReturnStatusName(DrugReturnStatusEnum.getName(vo.getReturnStatus()));
            vo.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(vo.getReturnReasonType()));
        });
        return success(respList);
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获取退药记录")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-return:query')")
    public CommonResult<List<OpDrugReturnRespVO>> getDrugReturnListByPatient(@RequestParam("patientId") Long patientId) {
        List<OpDrugReturnDO> list = drugReturnService.getDrugReturnListByPatientId(patientId);
        List<OpDrugReturnRespVO> respList = BeanUtils.toBean(list, OpDrugReturnRespVO.class);
        respList.forEach(vo -> {
            vo.setReturnTypeName(DrugReturnTypeEnum.getName(vo.getReturnType()));
            vo.setReturnStatusName(DrugReturnStatusEnum.getName(vo.getReturnStatus()));
            vo.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(vo.getReturnReasonType()));
        });
        return success(respList);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出退药申请 Excel")
    @PreAuthorize("@ss.hasPermission('his:drug-return:export')")
    @OperateLog(type = EXPORT)
    public void exportDrugReturnExcel(@Valid OpDrugReturnPageReqVO pageReqVO,
                                      HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OpDrugReturnDO> list = drugReturnService.getDrugReturnPage(pageReqVO).getList();
        // 导出 Excel
        List<OpDrugReturnRespVO> excelList = BeanUtils.toBean(list, OpDrugReturnRespVO.class);
        // 设置枚举名称
        excelList.forEach(vo -> {
            vo.setReturnTypeName(DrugReturnTypeEnum.getName(vo.getReturnType()));
            vo.setReturnStatusName(DrugReturnStatusEnum.getName(vo.getReturnStatus()));
            vo.setReturnReasonTypeName(DrugReturnReasonTypeEnum.getName(vo.getReturnReasonType()));
        });
        ExcelUtils.write(response, "退药申请.xls", "数据", OpDrugReturnRespVO.class, excelList);
    }

}