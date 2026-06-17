package cn.iocoder.yudao.module.his.controller.admin.payment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentItemDO;
import cn.iocoder.yudao.module.his.service.payment.OpPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 门诊支付管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 门诊支付管理")
@RestController
@RequestMapping("/his/payment")
@Validated
public class OpPaymentController {

    @Resource
    private OpPaymentService paymentService;

    @PostMapping("/create")
    @Operation(summary = "创建支付记录")
    @PreAuthorize("@ss.hasPermission('his:payment:create')")
    public CommonResult<Long> createPayment(@Valid @RequestBody OpPaymentCreateReqVO createReqVO) {
        return success(paymentService.createPayment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新支付记录")
    @PreAuthorize("@ss.hasPermission('his:payment:update')")
    public CommonResult<Boolean> updatePayment(@Valid @RequestBody OpPaymentUpdateReqVO updateReqVO) {
        paymentService.updatePayment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除支付记录")
    @Parameter(name = "id", description = "支付ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment:delete')")
    public CommonResult<Boolean> deletePayment(@RequestParam("id") Long id) {
        paymentService.deletePayment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得支付记录")
    @Parameter(name = "id", description = "支付ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment:query')")
    public CommonResult<OpPaymentRespVO> getPayment(@RequestParam("id") Long id) {
        OpPaymentDO payment = paymentService.getPayment(id);
        return success(BeanUtils.toBean(payment, OpPaymentRespVO.class));
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据支付单号获得支付记录")
    @Parameter(name = "paymentNo", description = "支付单号", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment:query')")
    public CommonResult<OpPaymentRespVO> getPaymentByNo(@RequestParam("paymentNo") String paymentNo) {
        OpPaymentDO payment = paymentService.getPaymentByPaymentNo(paymentNo);
        return success(BeanUtils.toBean(payment, OpPaymentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得支付记录分页")
    @PreAuthorize("@ss.hasPermission('his:payment:query')")
    public CommonResult<PageResult<OpPaymentRespVO>> getPaymentPage(@Valid OpPaymentPageReqVO pageReqVO) {
        PageResult<OpPaymentDO> pageResult = paymentService.getPaymentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpPaymentRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获得支付明细列表")
    @Parameter(name = "paymentId", description = "支付ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment:query')")
    public CommonResult<List<OpPaymentItemRespVO>> getPaymentItems(@RequestParam("paymentId") Long paymentId) {
        List<OpPaymentItemDO> items = paymentService.getPaymentItemList(paymentId);
        return success(BeanUtils.toBean(items, OpPaymentItemRespVO.class));
    }

    @GetMapping("/list-by-register")
    @Operation(summary = "根据挂号ID获得支付列表")
    @Parameter(name = "registerId", description = "挂号ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment:query')")
    public CommonResult<List<OpPaymentRespVO>> getPaymentListByRegister(@RequestParam("registerId") Long registerId) {
        List<OpPaymentDO> list = paymentService.getPaymentListByRegisterId(registerId);
        return success(BeanUtils.toBean(list, OpPaymentRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获得支付列表")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment:query')")
    public CommonResult<List<OpPaymentRespVO>> getPaymentListByPatient(@RequestParam("patientId") Long patientId) {
        List<OpPaymentDO> list = paymentService.getPaymentListByPatientId(patientId);
        return success(BeanUtils.toBean(list, OpPaymentRespVO.class));
    }

    @PutMapping("/confirm")
    @Operation(summary = "确认支付成功")
    @PreAuthorize("@ss.hasPermission('his:payment:update')")
    public CommonResult<Boolean> confirmPayment(@RequestParam("id") Long id,
                                                 @RequestParam(value = "invoiceNo", required = false) String invoiceNo) {
        paymentService.confirmPayment(id, invoiceNo);
        return success(true);
    }

    @PutMapping("/cancel")
    @Operation(summary = "取消支付")
    @Parameter(name = "id", description = "支付ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:payment:update')")
    public CommonResult<Boolean> cancelPayment(@RequestParam("id") Long id) {
        paymentService.cancelPayment(id);
        return success(true);
    }

}
