package cn.iocoder.yudao.module.his.controller.admin.payment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpRefundDO;
import cn.iocoder.yudao.module.his.service.payment.OpRefundService;
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
 * 门诊退费管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 门诊退费管理")
@RestController
@RequestMapping("/his/refund")
@Validated
public class OpRefundController {

    @Resource
    private OpRefundService refundService;

    @PostMapping("/create")
    @Operation(summary = "创建退费申请")
    @PreAuthorize("@ss.hasPermission('his:refund:create')")
    public CommonResult<Long> createRefund(@Valid @RequestBody OpRefundCreateReqVO createReqVO) {
        return success(refundService.createRefund(createReqVO));
    }

    @PutMapping("/audit")
    @Operation(summary = "审核退费申请")
    @Parameter(name = "id", description = "退费ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:refund:audit')")
    public CommonResult<Boolean> auditRefund(@RequestParam("id") Long id,
                                              @Valid @RequestBody OpRefundAuditReqVO auditReqVO) {
        refundService.auditRefund(id, auditReqVO);
        return success(true);
    }

    @PutMapping("/complete")
    @Operation(summary = "完成退费")
    @Parameter(name = "id", description = "退费ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:refund:complete')")
    public CommonResult<Boolean> completeRefund(@RequestParam("id") Long id) {
        refundService.completeRefund(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除退费记录")
    @Parameter(name = "id", description = "退费ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:refund:delete')")
    public CommonResult<Boolean> deleteRefund(@RequestParam("id") Long id) {
        refundService.deleteRefund(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得退费记录")
    @Parameter(name = "id", description = "退费ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:refund:query')")
    public CommonResult<OpRefundRespVO> getRefund(@RequestParam("id") Long id) {
        OpRefundDO refund = refundService.getRefund(id);
        return success(BeanUtils.toBean(refund, OpRefundRespVO.class));
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据退费单号获得退费记录")
    @Parameter(name = "refundNo", description = "退费单号", required = true)
    @PreAuthorize("@ss.hasPermission('his:refund:query')")
    public CommonResult<OpRefundRespVO> getRefundByNo(@RequestParam("refundNo") String refundNo) {
        OpRefundDO refund = refundService.getRefundByRefundNo(refundNo);
        return success(BeanUtils.toBean(refund, OpRefundRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得退费记录分页")
    @PreAuthorize("@ss.hasPermission('his:refund:query')")
    public CommonResult<PageResult<OpRefundRespVO>> getRefundPage(@Valid OpRefundPageReqVO pageReqVO) {
        PageResult<OpRefundDO> pageResult = refundService.getRefundPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpRefundRespVO.class));
    }

    @GetMapping("/pending-list")
    @Operation(summary = "获得待审核退费列表")
    @PreAuthorize("@ss.hasPermission('his:refund:query')")
    public CommonResult<List<OpRefundRespVO>> getPendingRefundList() {
        List<OpRefundDO> list = refundService.getPendingRefundList();
        return success(BeanUtils.toBean(list, OpRefundRespVO.class));
    }

    @GetMapping("/list-by-payment")
    @Operation(summary = "根据支付ID获得退费列表")
    @Parameter(name = "paymentId", description = "支付ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:refund:query')")
    public CommonResult<List<OpRefundRespVO>> getRefundListByPayment(@RequestParam("paymentId") Long paymentId) {
        List<OpRefundDO> list = refundService.getRefundListByPaymentId(paymentId);
        return success(BeanUtils.toBean(list, OpRefundRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获得退费列表")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:refund:query')")
    public CommonResult<List<OpRefundRespVO>> getRefundListByPatient(@RequestParam("patientId") Long patientId) {
        List<OpRefundDO> list = refundService.getRefundListByPatientId(patientId);
        return success(BeanUtils.toBean(list, OpRefundRespVO.class));
    }

}
