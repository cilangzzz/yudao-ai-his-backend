package cn.iocoder.yudao.module.his.controller.admin.drug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import cn.iocoder.yudao.module.his.service.drug.HisDrugService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 药品目录")
@RestController
@RequestMapping("/his/drug")
@Validated
public class HisDrugController {

    @Resource
    private HisDrugService drugService;

    @PostMapping("/create")
    @Operation(summary = "创建药品目录")
    @PreAuthorize("@ss.hasPermission('his:drug:create')")
    public CommonResult<Long> createDrug(@Valid @RequestBody HisDrugSaveReqVO createReqVO) {
        return success(drugService.createDrug(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新药品目录")
    @PreAuthorize("@ss.hasPermission('his:drug:update')")
    public CommonResult<Boolean> updateDrug(@Valid @RequestBody HisDrugSaveReqVO updateReqVO) {
        drugService.updateDrug(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除药品目录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug:delete')")
    public CommonResult<Boolean> deleteDrug(@RequestParam("id") Long id) {
        drugService.deleteDrug(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得药品目录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug:query')")
    public CommonResult<HisDrugRespVO> getDrug(@RequestParam("id") Long id) {
        HisDrugDO drug = drugService.getDrug(id);
        return success(BeanUtils.toBean(drug, HisDrugRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得药品目录分页")
    @PreAuthorize("@ss.hasPermission('his:drug:query')")
    public CommonResult<PageResult<HisDrugRespVO>> getDrugPage(@Valid HisDrugPageReqVO pageReqVO) {
        PageResult<HisDrugDO> pageResult = drugService.getDrugPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDrugRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得药品目录列表")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug:query')")
    public CommonResult<List<HisDrugRespVO>> getDrugList(@RequestParam("ids") List<Long> ids) {
        List<HisDrugDO> list = drugService.getDrugList(ids);
        return success(BeanUtils.toBean(list, HisDrugRespVO.class));
    }

    @GetMapping("/list-by-name")
    @Operation(summary = "根据药品名称模糊查询列表")
    @Parameter(name = "drugName", description = "药品名称", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug:query')")
    public CommonResult<List<HisDrugRespVO>> getDrugListByName(@RequestParam("drugName") String drugName) {
        List<HisDrugDO> list = drugService.getDrugListByName(drugName);
        return success(BeanUtils.toBean(list, HisDrugRespVO.class));
    }

    @GetMapping("/list-by-type")
    @Operation(summary = "根据药品类型查询列表")
    @Parameter(name = "drugType", description = "药品类型", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug:query')")
    public CommonResult<List<HisDrugRespVO>> getDrugListByType(@RequestParam("drugType") Integer drugType) {
        List<HisDrugDO> list = drugService.getDrugListByType(drugType);
        return success(BeanUtils.toBean(list, HisDrugRespVO.class));
    }

    @GetMapping("/special-list")
    @Operation(summary = "获得特殊药品列表（麻醉/精神/毒性）")
    @PreAuthorize("@ss.hasPermission('his:drug:query')")
    public CommonResult<List<HisDrugRespVO>> getSpecialDrugList() {
        List<HisDrugDO> list = drugService.getSpecialDrugList();
        return success(BeanUtils.toBean(list, HisDrugRespVO.class));
    }

    @GetMapping("/antibiotic-list")
    @Operation(summary = "获得抗菌药物列表")
    @PreAuthorize("@ss.hasPermission('his:drug:query')")
    public CommonResult<List<HisDrugRespVO>> getAntibioticList() {
        List<HisDrugDO> list = drugService.getAntibioticList();
        return success(BeanUtils.toBean(list, HisDrugRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出药品目录 Excel")
    @PreAuthorize("@ss.hasPermission('his:drug:export')")
    @OperateLog(type = EXPORT)
    public void exportDrugExcel(@Valid HisDrugPageReqVO pageReqVO,
                                HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<HisDrugDO> list = drugService.getDrugPage(pageReqVO).getList();
        // 导出 Excel
        List<HisDrugExcelVO> excelList = BeanUtils.toBean(list, HisDrugExcelVO.class);
        ExcelUtils.write(response, "药品目录.xls", "数据", HisDrugExcelVO.class, excelList);
    }

}
