package cn.iocoder.yudao.module.his.controller.admin.lab;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisCriticalValuePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisCriticalValueProcessReqVO;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisCriticalValueRespVO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisCriticalValueDO;
import cn.iocoder.yudao.module.his.enums.CriticalValueStatusEnum;
import cn.iocoder.yudao.module.his.service.lab.HisCriticalValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 危急值管理 Controller
 */
@Tag(name = "管理后台 - HIS危急值管理")
@RestController
@RequestMapping("/his/lab/critical-value")
@Validated
public class HisCriticalValueController {

    @Resource
    private HisCriticalValueService criticalValueService;

    @GetMapping("/page")
    @Operation(summary = "获得危急值分页列表")
    @PreAuthorize("@ss.hasPermission('his:critical-value:query')")
    public CommonResult<PageResult<HisCriticalValueRespVO>> getCriticalValuePage(@Valid HisCriticalValuePageReqVO pageReqVO) {
        PageResult<HisCriticalValueDO> pageResult = criticalValueService.getCriticalValuePage(pageReqVO);
        return success(convertToPageResult(pageResult));
    }

    @GetMapping("/get")
    @Operation(summary = "获得危急值详情")
    @Parameter(name = "id", description = "危急值ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:critical-value:query')")
    public CommonResult<HisCriticalValueRespVO> getCriticalValue(@RequestParam("id") Long id) {
        HisCriticalValueDO criticalValue = criticalValueService.getCriticalValue(id);
        return success(convertToRespVO(criticalValue));
    }

    @GetMapping("/list-unconfirmed")
    @Operation(summary = "获得未确认的危急值列表", description = "获取状态为已通知(未确认)的危急值列表，用于临床科室确认处理")
    @PreAuthorize("@ss.hasPermission('his:critical-value:query')")
    public CommonResult<List<HisCriticalValueRespVO>> getUnconfirmedList() {
        List<HisCriticalValueDO> list = criticalValueService.getUnconfirmedList();
        return success(convertToList(list));
    }

    @GetMapping("/list-detected")
    @Operation(summary = "获得待通知的危急值列表", description = "获取状态为检出(待通知)的危急值列表，用于检验科通知")
    @PreAuthorize("@ss.hasPermission('his:critical-value:query')")
    public CommonResult<List<HisCriticalValueRespVO>> getDetectedList() {
        List<HisCriticalValueDO> list = criticalValueService.getDetectedList();
        return success(convertToList(list));
    }

    @PostMapping("/notify")
    @Operation(summary = "通知危急值", description = "检验科通知临床科室危急值")
    @Parameter(name = "id", description = "危急值ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:critical-value:notify')")
    public CommonResult<Boolean> notifyCriticalValue(@RequestParam("id") Long id) {
        criticalValueService.notifyCriticalValue(id);
        return success(true);
    }

    @PostMapping("/confirm")
    @Operation(summary = "确认危急值", description = "临床科室确认接收危急值")
    @Parameter(name = "id", description = "危急值ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:critical-value:confirm')")
    public CommonResult<Boolean> confirmCriticalValue(@RequestParam("id") Long id) {
        criticalValueService.confirmCriticalValue(id);
        return success(true);
    }

    @PostMapping("/process")
    @Operation(summary = "处理危急值", description = "临床科室处理危急值，填写处理结果")
    @PreAuthorize("@ss.hasPermission('his:critical-value:process')")
    public CommonResult<Boolean> processCriticalValue(@Valid @RequestBody HisCriticalValueProcessReqVO reqVO) {
        criticalValueService.processCriticalValue(reqVO.getId(), reqVO.getProcessResult());
        return success(true);
    }

    // ==================== 转换方法 ====================

    private HisCriticalValueRespVO convertToRespVO(HisCriticalValueDO criticalValue) {
        if (criticalValue == null) {
            return null;
        }
        HisCriticalValueRespVO respVO = BeanUtils.toBean(criticalValue, HisCriticalValueRespVO.class);
        CriticalValueStatusEnum statusEnum = CriticalValueStatusEnum.valueOf(criticalValue.getStatus());
        if (statusEnum != null) {
            respVO.setStatusName(statusEnum.getName());
        }
        return respVO;
    }

    private List<HisCriticalValueRespVO> convertToList(List<HisCriticalValueDO> list) {
        return list.stream().map(this::convertToRespVO).collect(Collectors.toList());
    }

    private PageResult<HisCriticalValueRespVO> convertToPageResult(PageResult<HisCriticalValueDO> pageResult) {
        PageResult<HisCriticalValueRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToList(pageResult.getList()));
        return respPageResult;
    }

}