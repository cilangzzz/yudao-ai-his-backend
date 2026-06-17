package cn.iocoder.yudao.module.his.controller.admin.drug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseItemDO;
import cn.iocoder.yudao.module.his.service.drug.HisDrugPurchaseService;
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

@Tag(name = "管理后台 - 采购计划管理")
@RestController
@RequestMapping("/his/drug-purchase")
@Validated
public class HisDrugPurchaseController {

    @Resource
    private HisDrugPurchaseService purchaseService;

    @PostMapping("/create")
    @Operation(summary = "创建采购计划")
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:create')")
    public CommonResult<Long> createPurchase(@Valid @RequestBody HisDrugPurchaseSaveReqVO createReqVO) {
        return success(purchaseService.createPurchase(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新采购计划")
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:update')")
    public CommonResult<Boolean> updatePurchase(@Valid @RequestBody HisDrugPurchaseSaveReqVO updateReqVO) {
        purchaseService.updatePurchase(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除采购计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:delete')")
    public CommonResult<Boolean> deletePurchase(@RequestParam("id") Long id) {
        purchaseService.deletePurchase(id);
        return success(true);
    }

    @PostMapping("/submit")
    @Operation(summary = "提交采购计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:update')")
    public CommonResult<Boolean> submitPurchase(@RequestParam("id") Long id,
                                                 @RequestParam("applyBy") Long applyBy) {
        purchaseService.submitPurchase(id, applyBy);
        return success(true);
    }

    @PostMapping("/approve")
    @Operation(summary = "审批采购计划")
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:approve')")
    public CommonResult<Boolean> approvePurchase(@Valid @RequestBody HisDrugPurchaseApproveReqVO approveReqVO) {
        purchaseService.approvePurchase(approveReqVO.getId(), approveReqVO.getAuditBy(), approveReqVO.getApproved());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得采购计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:query')")
    public CommonResult<HisDrugPurchaseRespVO> getPurchase(@RequestParam("id") Long id) {
        HisDrugPurchaseDO purchase = purchaseService.getPurchase(id);
        return success(BeanUtils.toBean(purchase, HisDrugPurchaseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得采购计划分页")
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:query')")
    public CommonResult<PageResult<HisDrugPurchaseRespVO>> getPurchasePage(@Valid HisDrugPurchasePageReqVO pageReqVO) {
        PageResult<HisDrugPurchaseDO> pageResult = purchaseService.getPurchasePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDrugPurchaseRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获得采购明细列表")
    @Parameter(name = "purchaseId", description = "采购ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:query')")
    public CommonResult<List<HisDrugPurchaseItemRespVO>> getPurchaseItems(@RequestParam("purchaseId") Long purchaseId) {
        List<HisDrugPurchaseItemDO> list = purchaseService.getPurchaseItems(purchaseId);
        return success(BeanUtils.toBean(list, HisDrugPurchaseItemRespVO.class));
    }

    @GetMapping("/pending-approval-list")
    @Operation(summary = "获得待审批采购列表")
    @PreAuthorize("@ss.hasPermission('his:drug-purchase:query')")
    public CommonResult<List<HisDrugPurchaseRespVO>> getPendingApprovalList() {
        List<HisDrugPurchaseDO> list = purchaseService.getPendingApprovalList();
        return success(BeanUtils.toBean(list, HisDrugPurchaseRespVO.class));
    }

}
