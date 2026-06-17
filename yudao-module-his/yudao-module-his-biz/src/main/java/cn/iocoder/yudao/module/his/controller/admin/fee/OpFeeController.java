package cn.iocoder.yudao.module.his.controller.admin.fee;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeItemDO;
import cn.iocoder.yudao.module.his.service.fee.OpFeeService;
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
 * 门诊费用 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 门诊费用")
@RestController
@RequestMapping("/his/op-fee")
@Validated
public class OpFeeController {

    @Resource
    private OpFeeService feeService;

    // ========== 门诊费用 ==========

    @PostMapping("/create")
    @Operation(summary = "创建门诊费用")
    @PreAuthorize("@ss.hasPermission('his:op-fee:create')")
    public CommonResult<Long> createFee(@Valid @RequestBody OpFeeSaveReqVO createReqVO) {
        return success(feeService.createFee(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新门诊费用")
    @PreAuthorize("@ss.hasPermission('his:op-fee:update')")
    public CommonResult<Boolean> updateFee(@Valid @RequestBody OpFeeSaveReqVO updateReqVO) {
        feeService.updateFee(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除门诊费用")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:delete')")
    public CommonResult<Boolean> deleteFee(@RequestParam("id") Long id) {
        feeService.deleteFee(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得门诊费用")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<OpFeeRespVO> getFee(@RequestParam("id") Long id) {
        OpFeeDO fee = feeService.getFee(id);
        OpFeeRespVO respVO = BeanUtils.toBean(fee, OpFeeRespVO.class);
        // 查询费用明细
        List<OpFeeItemDO> items = feeService.getFeeItemListByFeeId(id);
        respVO.setItems(BeanUtils.toBean(items, OpFeeItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得门诊费用分页")
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<PageResult<OpFeeRespVO>> getFeePage(@Valid OpFeePageReqVO pageReqVO) {
        PageResult<OpFeeDO> pageResult = feeService.getFeePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpFeeRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得门诊费用列表")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpFeeRespVO>> getFeeList(@RequestParam("ids") List<Long> ids) {
        List<OpFeeDO> list = feeService.getFeeList(ids);
        return success(BeanUtils.toBean(list, OpFeeRespVO.class));
    }

    @GetMapping("/get-by-register")
    @Operation(summary = "根据挂号ID获得门诊费用")
    @Parameter(name = "registerId", description = "挂号ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<OpFeeRespVO> getFeeByRegisterId(@RequestParam("registerId") Long registerId) {
        OpFeeDO fee = feeService.getFeeByRegisterId(registerId);
        if (fee == null) {
            return success(null);
        }
        OpFeeRespVO respVO = BeanUtils.toBean(fee, OpFeeRespVO.class);
        // 查询费用明细
        List<OpFeeItemDO> items = feeService.getFeeItemListByFeeId(fee.getId());
        respVO.setItems(BeanUtils.toBean(items, OpFeeItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/unpaid-by-patient")
    @Operation(summary = "获得患者的未收费费用列表")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpFeeRespVO>> getUnpaidFeeByPatient(@RequestParam("patientId") Long patientId) {
        List<OpFeeDO> list = feeService.getUnpaidFeeListByPatientId(patientId);
        return success(BeanUtils.toBean(list, OpFeeRespVO.class));
    }

    // ========== 门诊费用明细 ==========

    @PostMapping("/item/create")
    @Operation(summary = "创建费用明细")
    @PreAuthorize("@ss.hasPermission('his:op-fee:create')")
    public CommonResult<Long> createFeeItem(@Valid @RequestBody OpFeeItemSaveReqVO createReqVO) {
        return success(feeService.createFeeItem(createReqVO));
    }

    @PutMapping("/item/update")
    @Operation(summary = "更新费用明细")
    @PreAuthorize("@ss.hasPermission('his:op-fee:update')")
    public CommonResult<Boolean> updateFeeItem(@Valid @RequestBody OpFeeItemSaveReqVO updateReqVO) {
        feeService.updateFeeItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/item/delete")
    @Operation(summary = "删除费用明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:delete')")
    public CommonResult<Boolean> deleteFeeItem(@RequestParam("id") Long id) {
        feeService.deleteFeeItem(id);
        return success(true);
    }

    @GetMapping("/item/get")
    @Operation(summary = "获得费用明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<OpFeeItemRespVO> getFeeItem(@RequestParam("id") Long id) {
        OpFeeItemDO feeItem = feeService.getFeeItem(id);
        return success(BeanUtils.toBean(feeItem, OpFeeItemRespVO.class));
    }

    @GetMapping("/item/page")
    @Operation(summary = "获得费用明细分页")
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<PageResult<OpFeeItemRespVO>> getFeeItemPage(@Valid OpFeeItemPageReqVO pageReqVO) {
        PageResult<OpFeeItemDO> pageResult = feeService.getFeeItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpFeeItemRespVO.class));
    }

    @GetMapping("/item/list-by-fee")
    @Operation(summary = "根据费用ID获得费用明细列表")
    @Parameter(name = "feeId", description = "费用ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpFeeItemRespVO>> getFeeItemListByFeeId(@RequestParam("feeId") Long feeId) {
        List<OpFeeItemDO> list = feeService.getFeeItemListByFeeId(feeId);
        return success(BeanUtils.toBean(list, OpFeeItemRespVO.class));
    }

    @GetMapping("/item/list-by-source")
    @Operation(summary = "根据来源获得费用明细列表")
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpFeeItemRespVO>> getFeeItemListBySource(
            @RequestParam("sourceType") Integer sourceType,
            @RequestParam("sourceId") Long sourceId) {
        List<OpFeeItemDO> list = feeService.getFeeItemListBySource(sourceType, sourceId);
        return success(BeanUtils.toBean(list, OpFeeItemRespVO.class));
    }

}