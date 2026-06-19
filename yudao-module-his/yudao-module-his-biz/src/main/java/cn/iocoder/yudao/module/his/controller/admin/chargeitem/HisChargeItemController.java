package cn.iocoder.yudao.module.his.controller.admin.chargeitem;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemRespVO;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.chargeitem.HisChargeItemDO;
import cn.iocoder.yudao.module.his.service.chargeitem.HisChargeItemService;
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
 * HIS 收费项目管理 Controller
 */
@Tag(name = "管理后台 - HIS收费项目管理")
@RestController
@RequestMapping("/his/basic/charge-item")
@Validated
public class HisChargeItemController {

    @Resource
    private HisChargeItemService chargeItemService;

    @PostMapping("/create")
    @Operation(summary = "创建收费项目")
    @PreAuthorize("@ss.hasPermission('his:charge-item:create')")
    public CommonResult<Long> createChargeItem(@Valid @RequestBody HisChargeItemSaveReqVO createReqVO) {
        return success(chargeItemService.createChargeItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收费项目")
    @PreAuthorize("@ss.hasPermission('his:charge-item:update')")
    public CommonResult<Boolean> updateChargeItem(@Valid @RequestBody HisChargeItemSaveReqVO updateReqVO) {
        chargeItemService.updateChargeItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收费项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:charge-item:delete')")
    public CommonResult<Boolean> deleteChargeItem(@RequestParam("id") Long id) {
        chargeItemService.deleteChargeItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收费项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:charge-item:query')")
    public CommonResult<HisChargeItemRespVO> getChargeItem(@RequestParam("id") Long id) {
        HisChargeItemDO chargeItem = chargeItemService.getChargeItem(id);
        return success(BeanUtils.toBean(chargeItem, HisChargeItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收费项目分页")
    @PreAuthorize("@ss.hasPermission('his:charge-item:query')")
    public CommonResult<PageResult<HisChargeItemRespVO>> getChargeItemPage(@Valid HisChargeItemPageReqVO pageReqVO) {
        PageResult<HisChargeItemDO> pageResult = chargeItemService.getChargeItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisChargeItemRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得收费项目列表")
    @PreAuthorize("@ss.hasPermission('his:charge-item:query')")
    public CommonResult<List<HisChargeItemRespVO>> getChargeItemList(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "status", required = false) Integer status) {
        List<HisChargeItemDO> list = chargeItemService.getChargeItemList(type, status);
        return success(BeanUtils.toBean(list, HisChargeItemRespVO.class));
    }

}