package cn.iocoder.yudao.module.his.controller.admin.drug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryItemDO;
import cn.iocoder.yudao.module.his.service.drug.HisDrugInventoryService;
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

@Tag(name = "管理后台 - 盘点管理")
@RestController
@RequestMapping("/his/drug-inventory")
@Validated
public class HisDrugInventoryController {

    @Resource
    private HisDrugInventoryService inventoryService;

    @PostMapping("/create")
    @Operation(summary = "创建盘点记录")
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:create')")
    public CommonResult<Long> createInventory(@Valid @RequestBody HisDrugInventorySaveReqVO createReqVO) {
        return success(inventoryService.createInventory(createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除盘点记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:delete')")
    public CommonResult<Boolean> deleteInventory(@RequestParam("id") Long id) {
        inventoryService.deleteInventory(id);
        return success(true);
    }

    @PostMapping("/complete")
    @Operation(summary = "完成盘点")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:update')")
    public CommonResult<Boolean> completeInventory(@RequestParam("id") Long id) {
        inventoryService.completeInventory(id);
        return success(true);
    }

    @PostMapping("/inventory-item")
    @Operation(summary = "盘点单个药品")
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:update')")
    public CommonResult<Boolean> inventoryItem(@Valid @RequestBody HisDrugInventoryItemSaveReqVO itemReqVO) {
        inventoryService.inventoryItem(itemReqVO.getItemId(), itemReqVO.getActualQuantity(),
                itemReqVO.getOperatorId(), itemReqVO.getOperatorName(), itemReqVO.getRemark());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得盘点记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:query')")
    public CommonResult<HisDrugInventoryRespVO> getInventory(@RequestParam("id") Long id) {
        HisDrugInventoryDO inventory = inventoryService.getInventory(id);
        return success(BeanUtils.toBean(inventory, HisDrugInventoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得盘点记录分页")
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:query')")
    public CommonResult<PageResult<HisDrugInventoryRespVO>> getInventoryPage(@Valid HisDrugInventoryPageReqVO pageReqVO) {
        PageResult<HisDrugInventoryDO> pageResult = inventoryService.getInventoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDrugInventoryRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获得盘点明细列表")
    @Parameter(name = "inventoryId", description = "盘点ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:query')")
    public CommonResult<List<HisDrugInventoryItemRespVO>> getInventoryItems(@RequestParam("inventoryId") Long inventoryId) {
        List<HisDrugInventoryItemDO> list = inventoryService.getInventoryItems(inventoryId);
        return success(BeanUtils.toBean(list, HisDrugInventoryItemRespVO.class));
    }

    @GetMapping("/in-progress-list")
    @Operation(summary = "获得进行中的盘点列表")
    @PreAuthorize("@ss.hasPermission('his:drug-inventory:query')")
    public CommonResult<List<HisDrugInventoryRespVO>> getInProgressList() {
        List<HisDrugInventoryDO> list = inventoryService.getInProgressList();
        return success(BeanUtils.toBean(list, HisDrugInventoryRespVO.class));
    }

}
