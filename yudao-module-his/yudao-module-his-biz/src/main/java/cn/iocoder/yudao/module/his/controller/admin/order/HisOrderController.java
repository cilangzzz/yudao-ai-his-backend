package cn.iocoder.yudao.module.his.controller.admin.order;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.order.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.order.HisOrderDO;
import cn.iocoder.yudao.module.his.service.order.HisOrderService;
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

@Tag(name = "管理后台 - 医嘱管理")
@RestController
@RequestMapping("/his/order")
@Validated
public class HisOrderController {

    @Resource
    private HisOrderService orderService;

    @GetMapping("/get")
    @Operation(summary = "获取医嘱")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:order:query')")
    public CommonResult<HisOrderRespVO> getOrder(@RequestParam("id") Long id) {
        HisOrderDO order = orderService.getOrder(id);
        return success(BeanUtils.toBean(order, HisOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取医嘱分页")
    @PreAuthorize("@ss.hasPermission('his:order:query')")
    public CommonResult<PageResult<HisOrderRespVO>> getOrderPage(@Valid HisOrderPageReqVO pageReqVO) {
        PageResult<HisOrderDO> pageResult = orderService.getOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisOrderRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "获取住院的医嘱列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:order:query')")
    public CommonResult<List<HisOrderRespVO>> getOrderListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisOrderDO> list = orderService.getOrderListByAdmission(admissionId);
        return success(BeanUtils.toBean(list, HisOrderRespVO.class));
    }

    @GetMapping("/pending-audit-list")
    @Operation(summary = "获取待审核的医嘱列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:order:query')")
    public CommonResult<List<HisOrderRespVO>> getPendingAuditOrderList(@RequestParam("admissionId") Long admissionId) {
        List<HisOrderDO> list = orderService.getPendingAuditOrderList(admissionId);
        return success(BeanUtils.toBean(list, HisOrderRespVO.class));
    }

    @GetMapping("/executing-list")
    @Operation(summary = "获取执行中的医嘱列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:order:query')")
    public CommonResult<List<HisOrderRespVO>> getExecutingOrderList(@RequestParam("admissionId") Long admissionId) {
        List<HisOrderDO> list = orderService.getExecutingOrderList(admissionId);
        return success(BeanUtils.toBean(list, HisOrderRespVO.class));
    }

    @GetMapping("/long-term-list")
    @Operation(summary = "获取长期医嘱列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:order:query')")
    public CommonResult<List<HisOrderRespVO>> getLongTermOrderList(@RequestParam("admissionId") Long admissionId) {
        List<HisOrderDO> list = orderService.getLongTermOrderList(admissionId);
        return success(BeanUtils.toBean(list, HisOrderRespVO.class));
    }

    @GetMapping("/drug-order-list")
    @Operation(summary = "获取药品医嘱列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:order:query')")
    public CommonResult<List<HisOrderRespVO>> getDrugOrderList(@RequestParam("admissionId") Long admissionId) {
        List<HisOrderDO> list = orderService.getDrugOrderList(admissionId);
        return success(BeanUtils.toBean(list, HisOrderRespVO.class));
    }

    // ==================== 医嘱操作接口 ====================

    @PostMapping("/audit")
    @Operation(summary = "审核医嘱")
    @PreAuthorize("@ss.hasPermission('his:order:audit')")
    public CommonResult<Boolean> auditOrder(@Valid @RequestBody HisOrderAuditReqVO reqVO) {
        orderService.auditOrder(reqVO);
        return success(true);
    }

    @PostMapping("/execute")
    @Operation(summary = "执行医嘱")
    @PreAuthorize("@ss.hasPermission('his:order:execute')")
    public CommonResult<Boolean> executeOrder(@Valid @RequestBody HisOrderExecuteReqVO reqVO) {
        orderService.executeOrder(reqVO);
        return success(true);
    }

    @PostMapping("/stop")
    @Operation(summary = "停止医嘱")
    @PreAuthorize("@ss.hasPermission('his:order:stop')")
    public CommonResult<Boolean> stopOrder(@Valid @RequestBody HisOrderStopReqVO reqVO) {
        orderService.stopOrder(reqVO);
        return success(true);
    }

    @PostMapping("/cancel")
    @Operation(summary = "作废医嘱")
    @PreAuthorize("@ss.hasPermission('his:order:cancel')")
    public CommonResult<Boolean> cancelOrder(
            @RequestParam("orderId") Long orderId,
            @RequestParam("cancelDoctorId") Long cancelDoctorId,
            @RequestParam(value = "cancelReason", required = false) String cancelReason) {
        orderService.cancelOrder(orderId, cancelDoctorId, cancelReason);
        return success(true);
    }
}