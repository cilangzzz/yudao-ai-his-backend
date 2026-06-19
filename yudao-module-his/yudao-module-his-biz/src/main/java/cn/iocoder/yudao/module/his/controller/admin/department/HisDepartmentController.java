package cn.iocoder.yudao.module.his.controller.admin.department;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentRespVO;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.department.HisDepartmentDO;
import cn.iocoder.yudao.module.his.service.department.HisDepartmentService;
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
 * HIS 科室管理 Controller
 */
@Tag(name = "管理后台 - HIS科室管理")
@RestController
@RequestMapping("/his/basic/department")
@Validated
public class HisDepartmentController {

    @Resource
    private HisDepartmentService departmentService;

    @PostMapping("/create")
    @Operation(summary = "创建科室")
    @PreAuthorize("@ss.hasPermission('his:department:create')")
    public CommonResult<Long> createDepartment(@Valid @RequestBody HisDepartmentSaveReqVO createReqVO) {
        return success(departmentService.createDepartment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新科室")
    @PreAuthorize("@ss.hasPermission('his:department:update')")
    public CommonResult<Boolean> updateDepartment(@Valid @RequestBody HisDepartmentSaveReqVO updateReqVO) {
        departmentService.updateDepartment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除科室")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:department:delete')")
    public CommonResult<Boolean> deleteDepartment(@RequestParam("id") Long id) {
        departmentService.deleteDepartment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得科室")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:department:query')")
    public CommonResult<HisDepartmentRespVO> getDepartment(@RequestParam("id") Long id) {
        HisDepartmentDO department = departmentService.getDepartment(id);
        return success(BeanUtils.toBean(department, HisDepartmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得科室分页")
    @PreAuthorize("@ss.hasPermission('his:department:query')")
    public CommonResult<PageResult<HisDepartmentRespVO>> getDepartmentPage(@Valid HisDepartmentPageReqVO pageReqVO) {
        PageResult<HisDepartmentDO> pageResult = departmentService.getDepartmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDepartmentRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得科室列表")
    @Parameter(name = "type", description = "科室类型", example = "1")
    @PreAuthorize("@ss.hasPermission('his:department:query')")
    public CommonResult<List<HisDepartmentRespVO>> getDepartmentList(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "status", required = false) Integer status) {
        List<HisDepartmentDO> list = departmentService.getDepartmentList(type, status);
        return success(BeanUtils.toBean(list, HisDepartmentRespVO.class));
    }

}