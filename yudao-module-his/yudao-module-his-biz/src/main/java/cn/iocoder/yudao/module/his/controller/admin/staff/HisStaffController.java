package cn.iocoder.yudao.module.his.controller.admin.staff;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffRespVO;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.staff.HisStaffDO;
import cn.iocoder.yudao.module.his.service.staff.HisStaffService;
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
 * HIS 医护人员管理 Controller
 */
@Tag(name = "管理后台 - HIS医护人员管理")
@RestController
@RequestMapping("/his/basic/staff")
@Validated
public class HisStaffController {

    @Resource
    private HisStaffService staffService;

    @PostMapping("/create")
    @Operation(summary = "创建医护人员")
    @PreAuthorize("@ss.hasPermission('his:staff:create')")
    public CommonResult<Long> createStaff(@Valid @RequestBody HisStaffSaveReqVO createReqVO) {
        return success(staffService.createStaff(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新医护人员")
    @PreAuthorize("@ss.hasPermission('his:staff:update')")
    public CommonResult<Boolean> updateStaff(@Valid @RequestBody HisStaffSaveReqVO updateReqVO) {
        staffService.updateStaff(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除医护人员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:staff:delete')")
    public CommonResult<Boolean> deleteStaff(@RequestParam("id") Long id) {
        staffService.deleteStaff(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得医护人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:staff:query')")
    public CommonResult<HisStaffRespVO> getStaff(@RequestParam("id") Long id) {
        HisStaffDO staff = staffService.getStaff(id);
        return success(BeanUtils.toBean(staff, HisStaffRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得医护人员分页")
    @PreAuthorize("@ss.hasPermission('his:staff:query')")
    public CommonResult<PageResult<HisStaffRespVO>> getStaffPage(@Valid HisStaffPageReqVO pageReqVO) {
        PageResult<HisStaffDO> pageResult = staffService.getStaffPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisStaffRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得医护人员列表")
    @PreAuthorize("@ss.hasPermission('his:staff:query')")
    public CommonResult<List<HisStaffRespVO>> getStaffList(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "deptId", required = false) Long deptId,
            @RequestParam(value = "status", required = false) Integer status) {
        List<HisStaffDO> list = staffService.getStaffList(type, deptId, status);
        return success(BeanUtils.toBean(list, HisStaffRespVO.class));
    }

}