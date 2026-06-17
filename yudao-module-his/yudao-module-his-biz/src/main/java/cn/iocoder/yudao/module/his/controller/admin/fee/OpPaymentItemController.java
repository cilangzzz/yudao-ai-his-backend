package cn.iocoder.yudao.module.his.controller.admin.fee;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemRespVO;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentItemDO;
import cn.iocoder.yudao.module.his.service.fee.OpPaymentItemService;
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
 * 支付明细 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 支付明细")
@RestController
@RequestMapping("/his/payment-item")
@Validated
public class OpPaymentItemController {

    @Resource
    private OpPaymentItemService paymentItemService;

    @PostMapping("/create")
    @Operation(summary = "创建支付明细")
    @PreAuthorize("@ss.hasPermission('his:payment-item:create')")
    public CommonResult<Long> createPaymentItem(@Valid @RequestBody OpPaymentItemSaveReqVO createReqVO) {
        return success(paymentItemService.createPaymentItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新支付明细")
    @PreAuthorize("@ss.hasPermission('his:payment-item:update')")
    public CommonResult<Boolean> updatePaymentItem(@Valid @RequestBody OpPaymentItemSaveReqVO updateReqVO) {
        paymentItemService.updatePaymentItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除支付明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment-item:delete')")
    public CommonResult<Boolean> deletePaymentItem(@RequestParam("id") Long id) {
        paymentItemService.deletePaymentItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得支付明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:payment-item:query')")
    public CommonResult<OpPaymentItemRespVO> getPaymentItem(@RequestParam("id") Long id) {
        OpPaymentItemDO paymentItem = paymentItemService.getPaymentItem(id);
        return success(BeanUtils.toBean(paymentItem, OpPaymentItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得支付明细分页")
    @PreAuthorize("@ss.hasPermission('his:payment-item:query')")
    public CommonResult<PageResult<OpPaymentItemRespVO>> getPaymentItemPage(@Valid OpPaymentItemPageReqVO pageReqVO) {
        PageResult<OpPaymentItemDO> pageResult = paymentItemService.getPaymentItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpPaymentItemRespVO.class));
    }

    @GetMapping("/list-by-payment")
    @Operation(summary = "根据支付记录ID获取支付明细列表")
    @Parameter(name = "paymentId", description = "支付记录ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:payment-item:query')")
    public CommonResult<List<OpPaymentItemRespVO>> getPaymentItemsByPaymentId(@RequestParam("paymentId") Long paymentId) {
        List<OpPaymentItemDO> list = paymentItemService.getPaymentItemsByPaymentId(paymentId);
        return success(BeanUtils.toBean(list, OpPaymentItemRespVO.class));
    }

    @GetMapping("/list-by-fee")
    @Operation(summary = "根据费用ID获取支付明细列表")
    @Parameter(name = "feeId", description = "费用ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:payment-item:query')")
    public CommonResult<List<OpPaymentItemRespVO>> getPaymentItemsByFeeId(@RequestParam("feeId") Long feeId) {
        List<OpPaymentItemDO> list = paymentItemService.getPaymentItemsByFeeId(feeId);
        return success(BeanUtils.toBean(list, OpPaymentItemRespVO.class));
    }

    @GetMapping("/list-by-register")
    @Operation(summary = "根据挂号ID获取支付明细列表")
    @Parameter(name = "registerId", description = "挂号ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:payment-item:query')")
    public CommonResult<List<OpPaymentItemRespVO>> getPaymentItemsByRegisterId(@RequestParam("registerId") Long registerId) {
        List<OpPaymentItemDO> list = paymentItemService.getPaymentItemsByRegisterId(registerId);
        return success(BeanUtils.toBean(list, OpPaymentItemRespVO.class));
    }

}
