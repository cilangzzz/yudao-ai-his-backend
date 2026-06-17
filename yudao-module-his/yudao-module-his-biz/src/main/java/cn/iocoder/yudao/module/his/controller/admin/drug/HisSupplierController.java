package cn.iocoder.yudao.module.his.controller.admin.drug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisSupplierDO;
import cn.iocoder.yudao.module.his.service.drug.HisSupplierService;
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

@Tag(name = "管理后台 - 供应商管理")
@RestController
@RequestMapping("/his/supplier")
@Validated
public class HisSupplierController {

    @Resource
    private HisSupplierService supplierService;

    @PostMapping("/create")
    @Operation(summary = "创建供应商")
    @PreAuthorize("@ss.hasPermission('his:supplier:create')")
    public CommonResult<Long> createSupplier(@Valid @RequestBody HisSupplierSaveReqVO createReqVO) {
        return success(supplierService.createSupplier(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新供应商")
    @PreAuthorize("@ss.hasPermission('his:supplier:update')")
    public CommonResult<Boolean> updateSupplier(@Valid @RequestBody HisSupplierSaveReqVO updateReqVO) {
        supplierService.updateSupplier(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除供应商")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:supplier:delete')")
    public CommonResult<Boolean> deleteSupplier(@RequestParam("id") Long id) {
        supplierService.deleteSupplier(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得供应商")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:supplier:query')")
    public CommonResult<HisSupplierRespVO> getSupplier(@RequestParam("id") Long id) {
        HisSupplierDO supplier = supplierService.getSupplier(id);
        return success(BeanUtils.toBean(supplier, HisSupplierRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得供应商分页")
    @PreAuthorize("@ss.hasPermission('his:supplier:query')")
    public CommonResult<PageResult<HisSupplierRespVO>> getSupplierPage(@Valid HisSupplierPageReqVO pageReqVO) {
        PageResult<HisSupplierDO> pageResult = supplierService.getSupplierPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisSupplierRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得供应商列表")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('his:supplier:query')")
    public CommonResult<List<HisSupplierRespVO>> getSupplierList(@RequestParam("ids") List<Long> ids) {
        List<HisSupplierDO> list = supplierService.getSupplierList(ids);
        return success(BeanUtils.toBean(list, HisSupplierRespVO.class));
    }

    @GetMapping("/normal-list")
    @Operation(summary = "获得正常供应商列表")
    @PreAuthorize("@ss.hasPermission('his:supplier:query')")
    public CommonResult<List<HisSupplierRespVO>> getNormalSupplierList() {
        List<HisSupplierDO> list = supplierService.getNormalSupplierList();
        return success(BeanUtils.toBean(list, HisSupplierRespVO.class));
    }

}
