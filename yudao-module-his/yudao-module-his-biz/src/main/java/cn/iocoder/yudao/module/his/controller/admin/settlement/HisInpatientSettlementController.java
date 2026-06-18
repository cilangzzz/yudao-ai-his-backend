package cn.iocoder.yudao.module.his.controller.admin.settlement;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientSettlementDO;
import cn.iocoder.yudao.module.his.service.settlement.HisInpatientSettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
 * 住院结算 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 住院结算")
@RestController
@RequestMapping("/his/settlement")
@Validated
public class HisInpatientSettlementController {

    @Resource
    private HisInpatientSettlementService settlementService;

    @PostMapping("/create")
    @Operation(summary = "创建住院结算单")
    @PreAuthorize("@ss.hasPermission('his:settlement:create')")
    public CommonResult<Long> createSettlement(@Valid @RequestBody HisInpatientSettlementSaveReqVO createReqVO) {
        return success(settlementService.createSettlement(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新住院结算单")
    @PreAuthorize("@ss.hasPermission('his:settlement:update')")
    public CommonResult<Boolean> updateSettlement(@Valid @RequestBody HisInpatientSettlementSaveReqVO updateReqVO) {
        settlementService.updateSettlement(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除住院结算单")
    @Parameter(name = "id", description = "结算单ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:settlement:delete')")
    public CommonResult<Boolean> deleteSettlement(@RequestParam("id") Long id) {
        settlementService.deleteSettlement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取住院结算单详情")
    @Parameter(name = "id", description = "结算单ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:settlement:query')")
    public CommonResult<HisInpatientSettlementRespVO> getSettlement(@RequestParam("id") Long id) {
        HisInpatientSettlementDO settlement = settlementService.getSettlement(id);
        return success(BeanUtils.toBean(settlement, HisInpatientSettlementRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取住院结算单分页")
    @PreAuthorize("@ss.hasPermission('his:settlement:query')")
    public CommonResult<PageResult<HisInpatientSettlementRespVO>> getSettlementPage(@Valid HisInpatientSettlementPageReqVO pageReqVO) {
        PageResult<HisInpatientSettlementDO> pageResult = settlementService.getSettlementPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisInpatientSettlementRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "根据入院ID查询结算单列表")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:settlement:query')")
    public CommonResult<List<HisInpatientSettlementRespVO>> getSettlementListByAdmissionId(@RequestParam("admissionId") Long admissionId) {
        List<HisInpatientSettlementDO> list = settlementService.getSettlementListByAdmissionId(admissionId);
        return success(BeanUtils.toBean(list, HisInpatientSettlementRespVO.class));
    }

    @PostMapping("/confirm")
    @Operation(summary = "结算确认")
    @Parameters({
            @Parameter(name = "id", description = "结算单ID", required = true),
            @Parameter(name = "paymentType", description = "支付方式", required = true)
    })
    @PreAuthorize("@ss.hasPermission('his:settlement:confirm')")
    public CommonResult<Boolean> confirmSettlement(
            @RequestParam("id") Long id,
            @RequestParam("paymentType") Integer paymentType) {
        // TODO: 从登录用户获取操作员信息
        settlementService.confirmSettlement(id, paymentType, 1L, "操作员");
        return success(true);
    }

    @PostMapping("/refund")
    @Operation(summary = "退费处理")
    @Parameters({
            @Parameter(name = "id", description = "结算单ID", required = true),
            @Parameter(name = "refundAmount", description = "退费金额", required = true),
            @Parameter(name = "remark", description = "备注")
    })
    @PreAuthorize("@ss.hasPermission('his:settlement:refund')")
    public CommonResult<Boolean> refundSettlement(
            @RequestParam("id") Long id,
            @RequestParam("refundAmount") BigDecimal refundAmount,
            @RequestParam(value = "remark", required = false) String remark) {
        settlementService.refundSettlement(id, refundAmount, remark);
        return success(true);
    }

    @PostMapping("/cancel")
    @Operation(summary = "作废处理")
    @Parameters({
            @Parameter(name = "id", description = "结算单ID", required = true),
            @Parameter(name = "remark", description = "备注")
    })
    @PreAuthorize("@ss.hasPermission('his:settlement:cancel')")
    public CommonResult<Boolean> cancelSettlement(
            @RequestParam("id") Long id,
            @RequestParam(value = "remark", required = false) String remark) {
        settlementService.cancelSettlement(id, remark);
        return success(true);
    }

    @GetMapping("/fee-summary")
    @Operation(summary = "获取费用汇总")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:settlement:query')")
    public CommonResult<HisInpatientSettlementRespVO> getFeeSummary(@RequestParam("admissionId") Long admissionId) {
        HisInpatientSettlementDO summary = settlementService.calculateFeeSummary(admissionId);
        return success(BeanUtils.toBean(summary, HisInpatientSettlementRespVO.class));
    }

}