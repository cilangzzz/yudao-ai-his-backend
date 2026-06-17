package cn.iocoder.yudao.module.his.controller.admin.fee;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpRefundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpRefundItemDO;
import cn.iocoder.yudao.module.his.service.fee.OpRefundService;
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
 * 退费记录 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 退费管理")
@RestController
@RequestMapping("/his/op-refund")
@Validated
public class OpRefundController {

    @Resource
    private OpRefundService refundService;

    // ========== 退费申请 ==========

    @PostMapping("/apply")
    @Operation(summary = "提交退费申请（支持全额/部分退费）")
    @PreAuthorize("@ss.hasPermission('his:op-fee:refund')")
    public CommonResult<Long> applyRefund(@Valid @RequestBody OpRefundApplyReqVO applyReqVO) {
        return success(refundService.applyRefund(applyReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "创建退费申请（简化版，全额退费）")
    @PreAuthorize("@ss.hasPermission('his:op-fee:refund')")
    public CommonResult<Long> createRefund(@Valid @RequestBody OpRefundSaveReqVO createReqVO) {
        return success(refundService.createRefund(createReqVO));
    }

    // ========== 退费审核 ==========

    @PostMapping("/audit")
    @Operation(summary = "审核退费申请")
    @PreAuthorize("@ss.hasPermission('his:op-fee:refund-audit')")
    public CommonResult<Boolean> auditRefund(
            @RequestParam("id") Long id,
            @RequestParam("approved") Boolean approved,
            @RequestParam(value = "auditRemark", required = false) String auditRemark) {
        refundService.auditRefund(id, approved, auditRemark);
        return success(true);
    }

    @PostMapping("/reject")
    @Operation(summary = "拒绝退费申请")
    @PreAuthorize("@ss.hasPermission('his:op-fee:refund-audit')")
    public CommonResult<Boolean> rejectRefund(
            @RequestParam("id") Long id,
            @RequestParam("rejectReason") String rejectReason) {
        refundService.rejectRefund(id, rejectReason);
        return success(true);
    }

    // ========== 退费执行 ==========

    @PostMapping("/complete")
    @Operation(summary = "完成退费")
    @Parameter(name = "id", description = "退费记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:refund')")
    public CommonResult<Boolean> completeRefund(@RequestParam("id") Long id) {
        refundService.completeRefund(id);
        return success(true);
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消退费申请")
    @Parameter(name = "id", description = "退费记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:refund')")
    public CommonResult<Boolean> cancelRefund(@RequestParam("id") Long id) {
        refundService.cancelRefund(id);
        return success(true);
    }

    // ========== 退费查询 ==========

    @GetMapping("/get")
    @Operation(summary = "获得退费记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<OpRefundRespVO> getRefund(@RequestParam("id") Long id) {
        OpRefundDO refund = refundService.getRefund(id);
        return success(BeanUtils.toBean(refund, OpRefundRespVO.class));
    }

    @GetMapping("/get-with-items")
    @Operation(summary = "获得退费记录（包含退费明细）")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<OpRefundRespVO> getRefundWithItems(@RequestParam("id") Long id) {
        return success(refundService.getRefundWithItems(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得退费记录分页")
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<PageResult<OpRefundRespVO>> getRefundPage(@Valid OpRefundPageReqVO pageReqVO) {
        PageResult<OpRefundDO> pageResult = refundService.getRefundPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpRefundRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得退费记录列表")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpRefundRespVO>> getRefundList(@RequestParam("ids") List<Long> ids) {
        List<OpRefundDO> list = refundService.getRefundList(ids);
        return success(BeanUtils.toBean(list, OpRefundRespVO.class));
    }

    @GetMapping("/list-by-fee")
    @Operation(summary = "根据费用ID获得退费记录列表")
    @Parameter(name = "feeId", description = "费用ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpRefundRespVO>> getRefundListByFeeId(@RequestParam("feeId") Long feeId) {
        List<OpRefundDO> list = refundService.getRefundListByFeeId(feeId);
        return success(BeanUtils.toBean(list, OpRefundRespVO.class));
    }

    @GetMapping("/list-by-payment")
    @Operation(summary = "根据支付ID获得退费记录列表")
    @Parameter(name = "paymentId", description = "支付ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpRefundRespVO>> getRefundListByPaymentId(@RequestParam("paymentId") Long paymentId) {
        List<OpRefundDO> list = refundService.getRefundListByPaymentId(paymentId);
        return success(BeanUtils.toBean(list, OpRefundRespVO.class));
    }

    @GetMapping("/pending")
    @Operation(summary = "获得待审核的退费记录列表")
    @PreAuthorize("@ss.hasPermission('his:op-fee:refund-audit')")
    public CommonResult<List<OpRefundRespVO>> getPendingRefundList() {
        List<OpRefundDO> list = refundService.getPendingRefundList();
        return success(BeanUtils.toBean(list, OpRefundRespVO.class));
    }

    // ========== 退费明细查询 ==========

    @GetMapping("/items")
    @Operation(summary = "获得退费明细列表")
    @Parameter(name = "refundId", description = "退费记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpRefundItemRespVO>> getRefundItemList(@RequestParam("refundId") Long refundId) {
        List<OpRefundItemDO> list = refundService.getRefundItemList(refundId);
        return success(BeanUtils.toBean(list, OpRefundItemRespVO.class));
    }

    // ========== 可退费查询 ==========

    @GetMapping("/refundable-items")
    @Operation(summary = "获取可退费明细列表")
    @Parameter(name = "paymentId", description = "支付记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<RefundableItemRespVO>> getRefundableItems(@RequestParam("paymentId") Long paymentId) {
        return success(refundService.getRefundableItems(paymentId));
    }

    @PostMapping("/calculate-refund-amount")
    @Operation(summary = "计算退费金额")
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<OpRefundService.RefundAmountSummary> calculateRefundAmount(
            @RequestParam("paymentId") Long paymentId,
            @RequestBody List<OpRefundItemReqVO> refundItems) {
        return success(refundService.calculateRefundAmount(paymentId, refundItems));
    }

}