package cn.iocoder.yudao.module.his.controller.admin.prepayment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentRefundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentUsageDO;
import cn.iocoder.yudao.module.his.service.prepayment.HisPrepaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 住院预交金管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 住院预交金管理")
@RestController
@RequestMapping("/his/prepayment")
@Validated
public class HisPrepaymentController {

    @Resource
    private HisPrepaymentService prepaymentService;

    // ========== 预交金管理 ==========

    @PostMapping("/create")
    @Operation(summary = "创建预交金（交费）")
    @PreAuthorize("@ss.hasPermission('his:prepayment:create')")
    public CommonResult<Long> createPrepayment(@Valid @RequestBody HisPrepaymentSaveReqVO createReqVO) {
        return success(prepaymentService.createPrepayment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新预交金")
    @PreAuthorize("@ss.hasPermission('his:prepayment:update')")
    public CommonResult<Boolean> updatePrepayment(@Valid @RequestBody HisPrepaymentSaveReqVO updateReqVO) {
        prepaymentService.updatePrepayment(updateReqVO);
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
    @Operation(summary = "获取预交金")
    @Parameter(name = "id", description = "预交金ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<HisPrepaymentRespVO> getPrepayment(@RequestParam("id") Long id) {
        HisPrepaymentDO prepayment = prepaymentService.getPrepayment(id);
        return success(convertToRespVO(prepayment));
    }

    @GetMapping("/page")
    @Operation(summary = "获取预交金分页")
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<PageResult<HisPrepaymentRespVO>> getPrepaymentPage(@Valid HisPrepaymentPageReqVO pageReqVO) {
        PageResult<HisPrepaymentDO> pageResult = prepaymentService.getPrepaymentPage(pageReqVO);
        return success(convertToRespVOPageResult(pageResult));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "获取入院记录的预交金列表")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentRespVO>> getPrepaymentListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisPrepaymentDO> list = prepaymentService.getPrepaymentListByAdmission(admissionId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "获取患者的预交金列表")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentRespVO>> getPrepaymentListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisPrepaymentDO> list = prepaymentService.getPrepaymentListByPatient(patientId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/sum-amount")
    @Operation(summary = "统计入院记录的预交金总额")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<BigDecimal> sumAmountByAdmission(@RequestParam("admissionId") Long admissionId) {
        return success(prepaymentService.sumAmountByAdmission(admissionId));
    }

    @GetMapping("/sum-available-balance")
    @Operation(summary = "统计入院记录的可用余额")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<BigDecimal> sumAvailableBalanceByAdmission(@RequestParam("admissionId") Long admissionId) {
        return success(prepaymentService.sumAvailableBalanceByAdmission(admissionId));
    }

    @GetMapping("/sum-used-amount")
    @Operation(summary = "统计入院记录的已使用金额")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<BigDecimal> sumUsedAmountByAdmission(@RequestParam("admissionId") Long admissionId) {
        return success(prepaymentService.sumUsedAmountByAdmission(admissionId));
    }

    @GetMapping("/sum-refund-amount")
    @Operation(summary = "统计入院记录的已退还金额")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<BigDecimal> sumRefundAmountByAdmission(@RequestParam("admissionId") Long admissionId) {
        return success(prepaymentService.sumRefundAmountByAdmission(admissionId));
    }

    // ========== 预交金使用记录 ==========

    @GetMapping("/usage/list-by-prepayment")
    @Operation(summary = "获取预交金使用记录列表")
    @Parameter(name = "prepaymentId", description = "预交金ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentUsageDO>> getUsageListByPrepayment(@RequestParam("prepaymentId") Long prepaymentId) {
        return success(prepaymentService.getUsageListByPrepayment(prepaymentId));
    }

    @GetMapping("/usage/list-by-admission")
    @Operation(summary = "获取入院记录的预交金使用记录列表")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentUsageDO>> getUsageListByAdmission(@RequestParam("admissionId") Long admissionId) {
        return success(prepaymentService.getUsageListByAdmission(admissionId));
    }

    // ========== 预交金退还管理 ==========

    @PostMapping("/refund/apply")
    @Operation(summary = "申请退还预交金")
    @PreAuthorize("@ss.hasPermission('his:prepayment:refund')")
    public CommonResult<Long> createRefundApply(@Valid @RequestBody HisPrepaymentRefundSaveReqVO createReqVO) {
        return success(prepaymentService.createRefundApply(createReqVO));
    }

    @PostMapping("/refund/approve")
    @Operation(summary = "审批退还申请")
    @PreAuthorize("@ss.hasPermission('his:prepayment:refund-approve')")
    public CommonResult<Boolean> approveRefund(
            @RequestParam("id") Long id,
            @RequestParam("approved") Boolean approved,
            @RequestParam(value = "approveRemark", required = false) String approveRemark) {
        // TODO: 从登录用户获取审批人信息
        prepaymentService.approveRefund(id, approved, approveRemark, 1L, "管理员");
        return success(true);
    }

    @PostMapping("/refund/execute")
    @Operation(summary = "执行退还（财务退款）")
    @PreAuthorize("@ss.hasPermission('his:prepayment:refund-execute')")
    public CommonResult<Boolean> executeRefund(
            @RequestParam("id") Long id,
            @RequestParam(value = "refundNoExternal", required = false) String refundNoExternal) {
        // TODO: 从登录用户获取操作员信息
        prepaymentService.executeRefund(id, refundNoExternal, 1L, "财务人员");
        return success(true);
    }

    @PostMapping("/refund/cancel")
    @Operation(summary = "取消退还申请")
    @Parameter(name = "id", description = "退还记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:refund')")
    public CommonResult<Boolean> cancelRefund(@RequestParam("id") Long id) {
        prepaymentService.cancelRefund(id);
        return success(true);
    }

    @GetMapping("/refund/get")
    @Operation(summary = "获取退还记录")
    @Parameter(name = "id", description = "退还记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<HisPrepaymentRefundRespVO> getRefund(@RequestParam("id") Long id) {
        HisPrepaymentRefundDO refund = prepaymentService.getRefund(id);
        return success(convertRefundToRespVO(refund));
    }

    @GetMapping("/refund/page")
    @Operation(summary = "获取退还记录分页")
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<PageResult<HisPrepaymentRefundRespVO>> getRefundPage(@Valid HisPrepaymentRefundPageReqVO pageReqVO) {
        PageResult<HisPrepaymentRefundDO> pageResult = prepaymentService.getRefundPage(pageReqVO);
        return success(convertRefundToRespVOPageResult(pageResult));
    }

    @GetMapping("/refund/list-by-prepayment")
    @Operation(summary = "获取预交金的退还记录列表")
    @Parameter(name = "prepaymentId", description = "预交金ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentRefundRespVO>> getRefundListByPrepayment(@RequestParam("prepaymentId") Long prepaymentId) {
        List<HisPrepaymentRefundDO> list = prepaymentService.getRefundListByPrepayment(prepaymentId);
        return success(convertRefundToRespVOList(list));
    }

    @GetMapping("/refund/list-by-admission")
    @Operation(summary = "获取入院记录的退还记录列表")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:prepayment:query')")
    public CommonResult<List<HisPrepaymentRefundRespVO>> getRefundListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisPrepaymentRefundDO> list = prepaymentService.getRefundListByAdmission(admissionId);
        return success(convertRefundToRespVOList(list));
    }

    // ========== 转换方法 ==========

    private HisPrepaymentRespVO convertToRespVO(HisPrepaymentDO prepayment) {
        if (prepayment == null) {
            return null;
        }
        HisPrepaymentRespVO respVO = BeanUtils.toBean(prepayment, HisPrepaymentRespVO.class);
        // 支付方式名称
        respVO.setPaymentTypeName(getPaymentTypeName(prepayment.getPaymentType()));
        // 状态名称
        respVO.setStatusName(getPrepaymentStatusName(prepayment.getStatus()));
        // 计算可用余额
        respVO.setAvailableBalance(prepayment.getAvailableBalance());
        return respVO;
    }

    private List<HisPrepaymentRespVO> convertToRespVOList(List<HisPrepaymentDO> list) {
        return list.stream().map(this::convertToRespVO).collect(java.util.stream.Collectors.toList());
    }

    private PageResult<HisPrepaymentRespVO> convertToRespVOPageResult(PageResult<HisPrepaymentDO> pageResult) {
        PageResult<HisPrepaymentRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToRespVOList(pageResult.getList()));
        return respPageResult;
    }

    private HisPrepaymentRefundRespVO convertRefundToRespVO(HisPrepaymentRefundDO refund) {
        if (refund == null) {
            return null;
        }
        HisPrepaymentRefundRespVO respVO = BeanUtils.toBean(refund, HisPrepaymentRefundRespVO.class);
        // 退还方式名称
        respVO.setRefundTypeName(getRefundTypeName(refund.getRefundType()));
        // 状态名称
        respVO.setStatusName(getRefundStatusName(refund.getStatus()));
        return respVO;
    }

    private List<HisPrepaymentRefundRespVO> convertRefundToRespVOList(List<HisPrepaymentRefundDO> list) {
        return list.stream().map(this::convertRefundToRespVO).collect(java.util.stream.Collectors.toList());
    }

    private PageResult<HisPrepaymentRefundRespVO> convertRefundToRespVOPageResult(PageResult<HisPrepaymentRefundDO> pageResult) {
        PageResult<HisPrepaymentRefundRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertRefundToRespVOList(pageResult.getList()));
        return respPageResult;
    }

    private String getPaymentTypeName(Integer paymentType) {
        if (paymentType == null) {
            return null;
        }
        switch (paymentType) {
            case 1: return "现金";
            case 2: return "银行卡";
            case 3: return "医保卡";
            case 4: return "微信";
            case 5: return "支付宝";
            case 6: return "混合支付";
            default: return "未知";
        }
    }

    private String getPrepaymentStatusName(Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case 1: return "已缴纳";
            case 2: return "已使用";
            case 3: return "已退还";
            case 4: return "已结算";
            default: return "未知";
        }
    }

    private String getRefundTypeName(Integer refundType) {
        if (refundType == null) {
            return null;
        }
        switch (refundType) {
            case 1: return "现金";
            case 2: return "银行卡";
            case 3: return "原路退回";
            default: return "未知";
        }
    }

    private String getRefundStatusName(Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case 1: return "申请中";
            case 2: return "已审批";
            case 3: return "已退还";
            case 4: return "已拒绝";
            default: return "未知";
        }
    }

}
