package cn.iocoder.yudao.module.his.controller.admin.preAdmission;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.preAdmission.HisPreAdmissionDO;
import cn.iocoder.yudao.module.his.service.preAdmission.HisPreAdmissionService;
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
 * HIS 预住院管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS预住院管理")
@RestController
@RequestMapping("/his/pre-admission")
@Validated
public class HisPreAdmissionController {

    @Resource
    private HisPreAdmissionService preAdmissionService;

    @PostMapping("/create")
    @Operation(summary = "创建预住院记录")
    @PreAuthorize("@ss.hasPermission('his:pre-admission:create')")
    public CommonResult<Long> createPreAdmission(@Valid @RequestBody HisPreAdmissionSaveReqVO createReqVO) {
        return success(preAdmissionService.createPreAdmission(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新预住院记录")
    @PreAuthorize("@ss.hasPermission('his:pre-admission:update')")
    public CommonResult<Boolean> updatePreAdmission(@Valid @RequestBody HisPreAdmissionSaveReqVO updateReqVO) {
        preAdmissionService.updatePreAdmission(updateReqVO);
        return success(true);
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消预住院")
    @PreAuthorize("@ss.hasPermission('his:pre-admission:cancel')")
    public CommonResult<Boolean> cancelPreAdmission(@RequestParam("id") Long id,
                                                     @RequestParam(value = "reason", required = false) String reason) {
        preAdmissionService.cancelPreAdmission(id, reason);
        return success(true);
    }

    @PostMapping("/convert")
    @Operation(summary = "转为正式入院")
    @Parameter(name = "id", description = "预住院ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:pre-admission:convert')")
    public CommonResult<Long> convertToAdmission(@RequestParam("id") Long id) {
        return success(preAdmissionService.convertToAdmission(id));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除预住院记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:pre-admission:delete')")
    public CommonResult<Boolean> deletePreAdmission(@RequestParam("id") Long id) {
        preAdmissionService.deletePreAdmission(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得预住院记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:pre-admission:query')")
    public CommonResult<HisPreAdmissionRespVO> getPreAdmission(@RequestParam("id") Long id) {
        HisPreAdmissionDO preAdmission = preAdmissionService.getPreAdmission(id);
        return success(convertToRespVO(preAdmission));
    }

    @GetMapping("/page")
    @Operation(summary = "获得预住院分页")
    @PreAuthorize("@ss.hasPermission('his:pre-admission:query')")
    public CommonResult<PageResult<HisPreAdmissionRespVO>> getPreAdmissionPage(@Valid HisPreAdmissionPageReqVO pageReqVO) {
        PageResult<HisPreAdmissionDO> pageResult = preAdmissionService.getPreAdmissionPage(pageReqVO);
        return success(convertToRespVOPageResult(pageResult));
    }

    @GetMapping("/pending-list")
    @Operation(summary = "获得待入院列表")
    @PreAuthorize("@ss.hasPermission('his:pre-admission:query')")
    public CommonResult<List<HisPreAdmissionRespVO>> getPendingList() {
        List<HisPreAdmissionDO> list = preAdmissionService.getPendingList();
        return success(convertToRespVOList(list));
    }

    @GetMapping("/pending-by-dept")
    @Operation(summary = "获得科室待入院列表")
    @Parameter(name = "deptId", description = "科室ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:pre-admission:query')")
    public CommonResult<List<HisPreAdmissionRespVO>> getPendingListByDept(@RequestParam("deptId") Long deptId) {
        List<HisPreAdmissionDO> list = preAdmissionService.getPendingListByDept(deptId);
        return success(convertToRespVOList(list));
    }

    private HisPreAdmissionRespVO convertToRespVO(HisPreAdmissionDO preAdmission) {
        if (preAdmission == null) {
            return null;
        }
        HisPreAdmissionRespVO respVO = BeanUtils.toBean(preAdmission, HisPreAdmissionRespVO.class);
        if (preAdmission.getStatus() != null) {
            String statusName;
            switch (preAdmission.getStatus()) {
                case 1: statusName = "待入院"; break;
                case 2: statusName = "已入院"; break;
                case 3: statusName = "已取消"; break;
                default: statusName = "未知";
            }
            respVO.setStatusName(statusName);
        }
        return respVO;
    }

    private List<HisPreAdmissionRespVO> convertToRespVOList(List<HisPreAdmissionDO> list) {
        return list.stream().map(this::convertToRespVO).collect(java.util.stream.Collectors.toList());
    }

    private PageResult<HisPreAdmissionRespVO> convertToRespVOPageResult(PageResult<HisPreAdmissionDO> pageResult) {
        PageResult<HisPreAdmissionRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToRespVOList(pageResult.getList()));
        return respPageResult;
    }
}