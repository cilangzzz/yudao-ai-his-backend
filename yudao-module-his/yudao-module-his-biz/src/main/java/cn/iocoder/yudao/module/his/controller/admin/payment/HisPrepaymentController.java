package cn.iocoder.yudao.module.his.controller.admin.payment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.HisPrepaymentDO;
import cn.iocoder.yudao.module.his.service.payment.HisPrepaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 预交金管理 Controller（住院）
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 预交金管理")
@RestController
@RequestMapping("/his/prepayment")
@Validated
public class HisPrepaymentController {

    @Resource
    private HisPrepaymentService prepaymentService;

    @PostMapping("/create")
    @Operation(summary = "创建预交金记录")
    @PreAuthorize("@ss.hasPermission('his:prepayment:create')")
    public CommonResult<Long> createPrepayment(@Valid @RequestBody HisPrepaymentCreateReqVO createReqVO) {
        return success(prepaymentService.createPrepayment(createReqVO));
    }

    @PutMapping("/refund")
    @Operation(summary = "退预交金")
    @PreAuthorize("@ss.hasPermission('his:prepayment:refund')")
    public CommonResult<Boolean> refundPrepayment(@RequestParam("id") Long id,
                                                   @RequestParam("refundAmount") BigDecimal refundAmount,
                                                   @RequestParam("refundBy") Long refundBy) {
        prepaymentService.refundPrepayment(id, refundAmount, refundBy);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除预交金记录")
    @Parameter(name = "id", description = "预交金ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:delete')")
    public CommonResult<Boolean> deletePrepayment(@RequestParam("id") Long id) {
        prepaymentService.deletePrepayment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得预交金记录")
    @Parameter(name = "id", description = "预交金ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<HisPrepaymentRespVO> getPrepayment(@RequestParam("id") Long id) {
        HisPrepaymentDO prepayment = prepaymentService.getPrepayment(id);
        return success(BeanUtils.toBean(prepayment, HisPrepaymentRespVO.class));
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据预交金单号获得预交金记录")
    @Parameter(name = "prepayNo", description = "预交金单号", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<HisPrepaymentRespVO> getPrepaymentByNo(@RequestParam("prepayNo") String prepayNo) {
        HisPrepaymentDO prepayment = prepaymentService.getPrepaymentByPrepayNo(prepayNo);
        return success(BeanUtils.toBean(prepayment, HisPrepaymentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得预交金记录分页")
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<PageResult<HisPrepaymentRespVO>> getPrepaymentPage(@Valid HisPrepaymentPageReqVO pageReqVO) {
        PageResult<HisPrepaymentDO> pageResult = prepaymentService.getPrepaymentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisPrepaymentRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "根据住院ID获得预交金列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentRespVO>> getPrepaymentListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisPrepaymentDO> list = prepaymentService.getPrepaymentListByAdmissionId(admissionId);
        return success(BeanUtils.toBean(list, HisPrepaymentRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获得预交金列表")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentRespVO>> getPrepaymentListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisPrepaymentDO> list = prepaymentService.getPrepaymentListByPatientId(patientId);
        return success(BeanUtils.toBean(list, HisPrepaymentRespVO.class));
    }

    @GetMapping("/total")
    @Operation(summary = "获得住院预交金总额")
    @Parameter(name = "admissionId", description = "住院ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<BigDecimal> getTotalPrepayment(@RequestParam("admissionId") Long admissionId) {
        return success(prepaymentService.getTotalPrepaymentByAdmissionId(admissionId));
    }

}
