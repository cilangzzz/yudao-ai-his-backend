package cn.iocoder.yudao.module.his.controller.admin.nursing;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverRespVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingHandoverDO;
import cn.iocoder.yudao.module.his.service.nursing.HisNursingHandoverService;
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

@Tag(name = "管理后台 - 护理交接班")
@RestController
@RequestMapping("/his/nursing-handover")
@Validated
public class HisNursingHandoverController {

    @Resource
    private HisNursingHandoverService nursingHandoverService;

    @PostMapping("/create")
    @Operation(summary = "创建护理交接班（发起交接）")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:create')")
    public CommonResult<Long> createNursingHandover(@Valid @RequestBody HisNursingHandoverSaveReqVO createReqVO) {
        return success(nursingHandoverService.createNursingHandover(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护理交接班")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:update')")
    public CommonResult<Boolean> updateNursingHandover(@Valid @RequestBody HisNursingHandoverSaveReqVO updateReqVO) {
        nursingHandoverService.updateNursingHandover(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护理交接班")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:delete')")
    public CommonResult<Boolean> deleteNursingHandover(@RequestParam("id") Long id) {
        nursingHandoverService.deleteNursingHandover(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取护理交接班")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:query')")
    public CommonResult<HisNursingHandoverRespVO> getNursingHandover(@RequestParam("id") Long id) {
        HisNursingHandoverDO handover = nursingHandoverService.getNursingHandover(id);
        return success(BeanUtils.toBean(handover, HisNursingHandoverRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取护理交接班分页")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:query')")
    public CommonResult<PageResult<HisNursingHandoverRespVO>> getNursingHandoverPage(@Valid HisNursingHandoverPageReqVO pageReqVO) {
        PageResult<HisNursingHandoverDO> pageResult = nursingHandoverService.getNursingHandoverPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisNursingHandoverRespVO.class));
    }

    @GetMapping("/list-by-ward")
    @Operation(summary = "获取病区的交接班列表")
    @Parameter(name = "wardId", description = "病区ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:query')")
    public CommonResult<List<HisNursingHandoverRespVO>> getNursingHandoverListByWard(@RequestParam("wardId") Long wardId) {
        List<HisNursingHandoverDO> list = nursingHandoverService.getNursingHandoverListByWard(wardId);
        return success(BeanUtils.toBean(list, HisNursingHandoverRespVO.class));
    }

    @GetMapping("/pending-takeover")
    @Operation(summary = "获取待接班列表")
    @Parameter(name = "wardId", description = "病区ID（可选）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:query')")
    public CommonResult<List<HisNursingHandoverRespVO>> getPendingTakeoverList(
            @RequestParam(value = "wardId", required = false) Long wardId) {
        List<HisNursingHandoverDO> list = nursingHandoverService.getPendingTakeoverList(wardId);
        return success(BeanUtils.toBean(list, HisNursingHandoverRespVO.class));
    }

    @GetMapping("/latest")
    @Operation(summary = "获取最新的交接班记录")
    @Parameter(name = "wardId", description = "病区ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:query')")
    public CommonResult<HisNursingHandoverRespVO> getLatestNursingHandover(@RequestParam("wardId") Long wardId) {
        HisNursingHandoverDO handover = nursingHandoverService.getLatestNursingHandover(wardId);
        return success(BeanUtils.toBean(handover, HisNursingHandoverRespVO.class));
    }

    @PostMapping("/sign")
    @Operation(summary = "交班签名")
    @Parameter(name = "id", description = "交接班ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:sign')")
    public CommonResult<Boolean> signHandover(@RequestParam("id") Long id) {
        nursingHandoverService.signHandover(id);
        return success(true);
    }

    @PostMapping("/takeover")
    @Operation(summary = "接班（接班签名）")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:takeover')")
    public CommonResult<Boolean> takeover(
            @RequestParam("id") Long id,
            @RequestParam("takeoverNurseId") Long takeoverNurseId) {
        nursingHandoverService.takeover(id, takeoverNurseId);
        return success(true);
    }

    @PostMapping("/cancel")
    @Operation(summary = "作废交接班记录")
    @PreAuthorize("@ss.hasPermission('his:nursing-handover:cancel')")
    public CommonResult<Boolean> cancelNursingHandover(
            @RequestParam("id") Long id,
            @RequestParam(value = "reason", required = false) String reason) {
        nursingHandoverService.cancelNursingHandover(id, reason);
        return success(true);
    }
}