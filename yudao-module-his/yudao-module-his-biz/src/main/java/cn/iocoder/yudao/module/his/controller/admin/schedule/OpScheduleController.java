package cn.iocoder.yudao.module.his.controller.admin.schedule;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpSchedulePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpScheduleRespVO;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpScheduleSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.schedule.OpScheduleDO;
import cn.iocoder.yudao.module.his.service.schedule.OpScheduleService;
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
 * HIS 医生排班管理 Controller
 */
@Tag(name = "管理后台 - HIS医生排班管理")
@RestController
@RequestMapping("/his/schedule")
@Validated
public class OpScheduleController {

    @Resource
    private OpScheduleService scheduleService;

    @PostMapping("/create")
    @Operation(summary = "创建排班")
    @PreAuthorize("@ss.hasPermission('his:schedule:create')")
    public CommonResult<Long> createSchedule(@Valid @RequestBody OpScheduleSaveReqVO createReqVO) {
        return success(scheduleService.createSchedule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新排班")
    @PreAuthorize("@ss.hasPermission('his:schedule:update')")
    public CommonResult<Boolean> updateSchedule(@Valid @RequestBody OpScheduleSaveReqVO updateReqVO) {
        scheduleService.updateSchedule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除排班")
    @Parameter(name = "id", description = "排班ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:schedule:delete')")
    public CommonResult<Boolean> deleteSchedule(@RequestParam("id") Long id) {
        scheduleService.deleteSchedule(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得排班详情")
    @Parameter(name = "id", description = "排班ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:schedule:query')")
    public CommonResult<OpScheduleRespVO> getSchedule(@RequestParam("id") Long id) {
        OpScheduleDO schedule = scheduleService.getSchedule(id);
        return success(BeanUtils.toBean(schedule, OpScheduleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得排班分页")
    @PreAuthorize("@ss.hasPermission('his:schedule:query')")
    public CommonResult<PageResult<OpScheduleRespVO>> getSchedulePage(@Valid OpSchedulePageReqVO pageReqVO) {
        PageResult<OpScheduleDO> pageResult = scheduleService.getSchedulePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpScheduleRespVO.class));
    }

    @GetMapping("/list-by-date")
    @Operation(summary = "获取某日排班列表")
    @Parameter(name = "date", description = "排班日期", required = true, example = "2026-06-17")
    @PreAuthorize("@ss.hasPermission('his:schedule:query')")
    public CommonResult<List<OpScheduleRespVO>> getScheduleByDate(
            @RequestParam("date") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date) {
        List<OpScheduleDO> list = scheduleService.getScheduleByDate(date);
        return success(BeanUtils.toBean(list, OpScheduleRespVO.class));
    }

    @GetMapping("/available")
    @Operation(summary = "获取某日可用排班（挂号用）")
    @Parameter(name = "date", description = "排班日期", required = true, example = "2026-06-17")
    @PreAuthorize("@ss.hasPermission('his:schedule:query')")
    public CommonResult<List<OpScheduleRespVO>> getAvailableSchedule(
            @RequestParam("date") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date,
            @RequestParam(value = "deptId", required = false) Long deptId) {
        List<OpScheduleDO> list = scheduleService.getAvailableSchedule(date, deptId);
        return success(BeanUtils.toBean(list, OpScheduleRespVO.class));
    }

    @PostMapping("/batch-create")
    @Operation(summary = "批量创建排班")
    @PreAuthorize("@ss.hasPermission('his:schedule:create')")
    public CommonResult<Boolean> batchCreateSchedule(@Valid @RequestBody List<OpScheduleSaveReqVO> createReqVOs) {
        scheduleService.batchCreateSchedule(createReqVOs);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新排班状态（停诊/复诊）")
    @PreAuthorize("@ss.hasPermission('his:schedule:update')")
    public CommonResult<Boolean> updateStatus(@RequestParam("id") Long id,
                                               @RequestParam("status") Integer status) {
        scheduleService.updateStatus(id, status);
        return success(true);
    }

}
