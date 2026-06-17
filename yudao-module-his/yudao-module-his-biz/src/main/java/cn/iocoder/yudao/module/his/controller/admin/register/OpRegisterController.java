package cn.iocoder.yudao.module.his.controller.admin.register;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterRespVO;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.register.OpRegisterDO;
import cn.iocoder.yudao.module.his.service.register.OpRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * HIS 挂号管理 Controller
 */
@Tag(name = "管理后台 - HIS挂号管理")
@RestController
@RequestMapping("/his/register")
@Validated
public class OpRegisterController {

    @Resource
    private OpRegisterService registerService;

    @PostMapping("/create")
    @Operation(summary = "现场挂号")
    @PreAuthorize("@ss.hasPermission('his:register:create')")
    public CommonResult<Long> createRegister(@Valid @RequestBody OpRegisterSaveReqVO createReqVO) {
        return success(registerService.createRegister(createReqVO));
    }

    @PostMapping("/refund")
    @Operation(summary = "退号")
    @PreAuthorize("@ss.hasPermission('his:register:refund')")
    public CommonResult<Boolean> refundRegister(@RequestParam("id") Long id,
                                                @RequestParam(value = "reason", required = false) String reason) {
        registerService.refundRegister(id, reason);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得挂号详情")
    @Parameter(name = "id", description = "挂号ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:register:query')")
    public CommonResult<OpRegisterRespVO> getRegister(@RequestParam("id") Long id) {
        OpRegisterDO register = registerService.getRegister(id);
        return success(BeanUtils.toBean(register, OpRegisterRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得挂号分页")
    @PreAuthorize("@ss.hasPermission('his:register:query')")
    public CommonResult<PageResult<OpRegisterRespVO>> getRegisterPage(@Valid OpRegisterPageReqVO pageReqVO) {
        PageResult<OpRegisterDO> pageResult = registerService.getRegisterPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpRegisterRespVO.class));
    }

    @PostMapping("/start-visit")
    @Operation(summary = "开始就诊")
    @Parameter(name = "id", description = "挂号ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:register:visit')")
    public CommonResult<Boolean> startVisit(@RequestParam("id") Long id) {
        registerService.startVisit(id);
        return success(true);
    }

    @PostMapping("/end-visit")
    @Operation(summary = "结束就诊")
    @Parameter(name = "id", description = "挂号ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:register:visit')")
    public CommonResult<Boolean> endVisit(@RequestParam("id") Long id) {
        registerService.endVisit(id);
        return success(true);
    }

    @GetMapping("/waiting-list")
    @Operation(summary = "获取候诊列表")
    @Parameter(name = "doctorId", description = "医生ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:register:query')")
    public CommonResult<List<OpRegisterRespVO>> getWaitingList(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date) {
        List<OpRegisterDO> list = registerService.getWaitingList(doctorId, date);
        return success(BeanUtils.toBean(list, OpRegisterRespVO.class));
    }

    @PostMapping("/call-next")
    @Operation(summary = "叫号（获取下一个候诊患者）")
    @Parameter(name = "doctorId", description = "医生ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:register:visit')")
    public CommonResult<OpRegisterRespVO> callNext(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date) {
        OpRegisterDO next = registerService.callNext(doctorId, date);
        return success(BeanUtils.toBean(next, OpRegisterRespVO.class));
    }

}
