package cn.iocoder.yudao.module.his.controller.admin.drug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugStockPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugStockRespVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugStockDO;
import cn.iocoder.yudao.module.his.service.drug.HisDrugStockService;
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

@Tag(name = "管理后台 - 药品库存")
@RestController
@RequestMapping("/his/drug-stock")
@Validated
public class HisDrugStockController {

    @Resource
    private HisDrugStockService drugStockService;

    @GetMapping("/get")
    @Operation(summary = "获得药品库存")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<HisDrugStockRespVO> getDrugStock(@RequestParam("id") Long id) {
        HisDrugStockDO stock = drugStockService.getDrugStock(id);
        return success(BeanUtils.toBean(stock, HisDrugStockRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得药品库存分页")
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<PageResult<HisDrugStockRespVO>> getDrugStockPage(@Valid HisDrugStockPageReqVO pageReqVO) {
        PageResult<HisDrugStockDO> pageResult = drugStockService.getDrugStockPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDrugStockRespVO.class));
    }

    @GetMapping("/list-by-drug")
    @Operation(summary = "根据药品ID查询库存列表")
    @Parameter(name = "drugId", description = "药品ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<List<HisDrugStockRespVO>> getDrugStockListByDrugId(@RequestParam("drugId") Long drugId) {
        List<HisDrugStockDO> list = drugStockService.getDrugStockListByDrugId(drugId);
        return success(BeanUtils.toBean(list, HisDrugStockRespVO.class));
    }

    @GetMapping("/total-quantity")
    @Operation(summary = "查询药品总库存数量")
    @Parameter(name = "drugId", description = "药品ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<BigDecimal> getTotalQuantityByDrugId(@RequestParam("drugId") Long drugId) {
        return success(drugStockService.getTotalQuantityByDrugId(drugId));
    }

    @GetMapping("/near-expiry-list")
    @Operation(summary = "获得近效期库存列表")
    @Parameter(name = "warningDays", description = "预警天数", example = "90")
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<List<HisDrugStockRespVO>> getNearExpiryStockList(
            @RequestParam(value = "warningDays", required = false, defaultValue = "90") Integer warningDays) {
        List<HisDrugStockDO> list = drugStockService.getNearExpiryStockList(warningDays);
        return success(BeanUtils.toBean(list, HisDrugStockRespVO.class));
    }

    @GetMapping("/expired-list")
    @Operation(summary = "获得过期库存列表")
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<List<HisDrugStockRespVO>> getExpiredStockList() {
        List<HisDrugStockDO> list = drugStockService.getExpiredStockList();
        return success(BeanUtils.toBean(list, HisDrugStockRespVO.class));
    }

    @GetMapping("/low-stock-list")
    @Operation(summary = "获得低库存列表")
    @Parameter(name = "threshold", description = "阈值", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<List<HisDrugStockRespVO>> getLowStockList(@RequestParam("threshold") BigDecimal threshold) {
        List<HisDrugStockDO> list = drugStockService.getLowStockList(threshold);
        return success(BeanUtils.toBean(list, HisDrugStockRespVO.class));
    }

    @PostMapping("/update-status")
    @Operation(summary = "更新库存状态（自动检测近效期和过期）")
    @PreAuthorize("@ss.hasPermission('his:drug-stock:update')")
    public CommonResult<Boolean> updateStockStatus() {
        drugStockService.updateStockStatus();
        return success(true);
    }

    @GetMapping("/validate-sufficient")
    @Operation(summary = "校验库存是否充足")
    @PreAuthorize("@ss.hasPermission('his:drug-stock:query')")
    public CommonResult<Boolean> validateStockSufficient(
            @RequestParam("drugId") Long drugId,
            @RequestParam("quantity") BigDecimal quantity,
            @RequestParam(value = "warehouseId", required = false) Long warehouseId) {
        return success(drugStockService.validateStockSufficient(drugId, quantity, warehouseId));
    }

}
