package cn.iocoder.yudao.module.his.controller.admin.ward;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.ward.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.ward.HisWardDO;
import cn.iocoder.yudao.module.his.service.ward.HisWardService;
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
 * HIS 病区管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS病区管理")
@RestController
@RequestMapping("/his/ward")
@Validated
public class HisWardController {

    @Resource
    private HisWardService wardService;

    @PostMapping("/create")
    @Operation(summary = "创建病区")
    @PreAuthorize("@ss.hasPermission('his:ward:create')")
    public CommonResult<Long> createWard(@Valid @RequestBody HisWardSaveReqVO createReqVO) {
        return success(wardService.createWard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新病区")
    @PreAuthorize("@ss.hasPermission('his:ward:update')")
    public CommonResult<Boolean> updateWard(@Valid @RequestBody HisWardSaveReqVO updateReqVO) {
        wardService.updateWard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除病区")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:ward:delete')")
    public CommonResult<Boolean> deleteWard(@RequestParam("id") Long id) {
        wardService.deleteWard(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得病区")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:ward:query')")
    public CommonResult<HisWardRespVO> getWard(@RequestParam("id") Long id) {
        HisWardDO ward = wardService.getWard(id);
        HisWardRespVO respVO = BeanUtils.toBean(ward, HisWardRespVO.class);
        if (ward != null) {
            respVO.setEmptyBedCount(ward.getEmptyBedCount());
            respVO.setUsageRate(ward.getUsageRate());
        }
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得病区分页")
    @PreAuthorize("@ss.hasPermission('his:ward:query')")
    public CommonResult<PageResult<HisWardRespVO>> getWardPage(@Valid HisWardPageReqVO pageReqVO) {
        PageResult<HisWardDO> pageResult = wardService.getWardPage(pageReqVO);
        PageResult<HisWardRespVO> respPageResult = BeanUtils.toBean(pageResult, HisWardRespVO.class);
        // 填充计算字段
        respPageResult.getList().forEach(vo -> {
            HisWardDO ward = pageResult.getList().stream()
                    .filter(w -> w.getId().equals(vo.getId()))
                    .findFirst().orElse(null);
            if (ward != null) {
                vo.setEmptyBedCount(ward.getEmptyBedCount());
                vo.setUsageRate(ward.getUsageRate());
            }
        });
        return success(respPageResult);
    }

    @GetMapping("/list")
    @Operation(summary = "获得病区列表")
    @PreAuthorize("@ss.hasPermission('his:ward:query')")
    public CommonResult<List<HisWardRespVO>> getWardList() {
        List<HisWardDO> list = wardService.getWardList();
        return success(BeanUtils.toBean(list, HisWardRespVO.class));
    }

    @GetMapping("/list-by-dept")
    @Operation(summary = "获得科室下的病区列表")
    @Parameter(name = "deptId", description = "科室ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:ward:query')")
    public CommonResult<List<HisWardRespVO>> getWardListByDept(@RequestParam("deptId") Long deptId) {
        List<HisWardDO> list = wardService.getWardListByDeptId(deptId);
        return success(BeanUtils.toBean(list, HisWardRespVO.class));
    }
}
