package cn.iocoder.yudao.module.his.controller.admin.fee;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentDO;
import cn.iocoder.yudao.module.his.service.fee.OpPaymentService;
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
 * 支付记录 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 支付记录")
@RestController
@RequestMapping("/his/op-payment")
@Validated
public class OpPaymentController {

    @Resource
    private OpPaymentService paymentService;

    @PostMapping("/create")
    @Operation(summary = "创建支付记录（收费）")
    @PreAuthorize("@ss.hasPermission('his:op-fee:pay')")
    public CommonResult<Long> createPayment(@Valid @RequestBody OpPaymentSaveReqVO createReqVO) {
        return success(paymentService.createPayment(createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得支付记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<OpPaymentRespVO> getPayment(@RequestParam("id") Long id) {
        OpPaymentDO payment = paymentService.getPayment(id);
        return success(BeanUtils.toBean(payment, OpPaymentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得支付记录分页")
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<PageResult<OpPaymentRespVO>> getPaymentPage(@Valid OpPaymentPageReqVO pageReqVO) {
        PageResult<OpPaymentDO> pageResult = paymentService.getPaymentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpPaymentRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得支付记录列表")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpPaymentRespVO>> getPaymentList(@RequestParam("ids") List<Long> ids) {
        List<OpPaymentDO> list = paymentService.getPaymentList(ids);
        return success(BeanUtils.toBean(list, OpPaymentRespVO.class));
    }

    @GetMapping("/list-by-fee")
    @Operation(summary = "根据费用ID获得支付记录列表")
    @Parameter(name = "feeId", description = "费用ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpPaymentRespVO>> getPaymentListByFeeId(@RequestParam("feeId") Long feeId) {
        List<OpPaymentDO> list = paymentService.getPaymentListByFeeId(feeId);
        return success(BeanUtils.toBean(list, OpPaymentRespVO.class));
    }

    @GetMapping("/list-by-register")
    @Operation(summary = "根据挂号ID获得支付记录列表")
    @Parameter(name = "registerId", description = "挂号ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpPaymentRespVO>> getPaymentListByRegisterId(@RequestParam("registerId") Long registerId) {
        List<OpPaymentDO> list = paymentService.getPaymentListByRegisterId(registerId);
        return success(BeanUtils.toBean(list, OpPaymentRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获得支付记录列表")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:op-fee:query')")
    public CommonResult<List<OpPaymentRespVO>> getPaymentListByPatientId(@RequestParam("patientId") Long patientId) {
        List<OpPaymentDO> list = paymentService.getPaymentListByPatientId(patientId);
        return success(BeanUtils.toBean(list, OpPaymentRespVO.class));
    }

}