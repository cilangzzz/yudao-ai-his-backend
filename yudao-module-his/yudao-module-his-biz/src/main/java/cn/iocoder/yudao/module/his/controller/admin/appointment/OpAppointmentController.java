package cn.iocoder.yudao.module.his.controller.admin.appointment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentRespVO;
import cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.appointment.OpAppointmentDO;
import cn.iocoder.yudao.module.his.service.appointment.OpAppointmentService;
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
 * HIS 预约挂号管理 Controller
 */
@Tag(name = "管理后台 - HIS预约挂号管理")
@RestController
@RequestMapping("/his/appointment")
@Validated
public class OpAppointmentController {

    @Resource
    private OpAppointmentService appointmentService;

    @PostMapping("/create")
    @Operation(summary = "创建预约")
    @PreAuthorize("@ss.hasPermission('his:appointment:create')")
    public CommonResult<Long> createAppointment(@Valid @RequestBody OpAppointmentSaveReqVO createReqVO) {
        return success(appointmentService.createAppointment(createReqVO));
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消预约")
    @PreAuthorize("@ss.hasPermission('his:appointment:cancel')")
    public CommonResult<Boolean> cancelAppointment(@RequestParam("id") Long id,
                                                   @RequestParam(value = "reason", required = false) String reason) {
        appointmentService.cancelAppointment(id, reason);
        return success(true);
    }

    @PostMapping("/take")
    @Operation(summary = "取号（将预约转为挂号）")
    @Parameter(name = "id", description = "预约ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:appointment:take')")
    public CommonResult<Long> takeAppointment(@RequestParam("id") Long id) {
        return success(appointmentService.takeAppointment(id));
    }

    @GetMapping("/get")
    @Operation(summary = "获得预约详情")
    @Parameter(name = "id", description = "预约ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:appointment:query')")
    public CommonResult<OpAppointmentRespVO> getAppointment(@RequestParam("id") Long id) {
        OpAppointmentDO appointment = appointmentService.getAppointment(id);
        return success(BeanUtils.toBean(appointment, OpAppointmentRespVO.class));
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据预约编号获得预约详情")
    @Parameter(name = "appointmentNo", description = "预约编号", required = true, example = "A202606170001")
    @PreAuthorize("@ss.hasPermission('his:appointment:query')")
    public CommonResult<OpAppointmentRespVO> getAppointmentByNo(@RequestParam("appointmentNo") String appointmentNo) {
        OpAppointmentDO appointment = appointmentService.getAppointmentByAppointmentNo(appointmentNo);
        return success(BeanUtils.toBean(appointment, OpAppointmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得预约分页")
    @PreAuthorize("@ss.hasPermission('his:appointment:query')")
    public CommonResult<PageResult<OpAppointmentRespVO>> getAppointmentPage(@Valid OpAppointmentPageReqVO pageReqVO) {
        PageResult<OpAppointmentDO> pageResult = appointmentService.getAppointmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OpAppointmentRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "获取患者的预约列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:appointment:query')")
    public CommonResult<List<OpAppointmentRespVO>> getAppointmentListByPatientId(@RequestParam("patientId") Long patientId) {
        List<OpAppointmentDO> list = appointmentService.getAppointmentListByPatientId(patientId);
        return success(BeanUtils.toBean(list, OpAppointmentRespVO.class));
    }

    @GetMapping("/list-by-doctor")
    @Operation(summary = "获取医生某日期的预约列表")
    @PreAuthorize("@ss.hasPermission('his:appointment:query')")
    public CommonResult<List<OpAppointmentRespVO>> getAppointmentListByDoctorIdAndDate(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("date") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date) {
        List<OpAppointmentDO> list = appointmentService.getAppointmentListByDoctorIdAndDate(doctorId, date);
        return success(BeanUtils.toBean(list, OpAppointmentRespVO.class));
    }

    @GetMapping("/list-by-dept")
    @Operation(summary = "获取科室某日期的预约列表")
    @PreAuthorize("@ss.hasPermission('his:appointment:query')")
    public CommonResult<List<OpAppointmentRespVO>> getAppointmentListByDeptIdAndDate(
            @RequestParam("deptId") Long deptId,
            @RequestParam("date") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date) {
        List<OpAppointmentDO> list = appointmentService.getAppointmentListByDeptIdAndDate(deptId, date);
        return success(BeanUtils.toBean(list, OpAppointmentRespVO.class));
    }

    @GetMapping("/count-by-schedule")
    @Operation(summary = "统计某排班某日期的预约数量")
    @PreAuthorize("@ss.hasPermission('his:appointment:query')")
    public CommonResult<Long> getAppointmentCountByScheduleIdAndDate(
            @RequestParam("scheduleId") Long scheduleId,
            @RequestParam("date") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date) {
        return success(appointmentService.getAppointmentCountByScheduleIdAndDate(scheduleId, date));
    }

}