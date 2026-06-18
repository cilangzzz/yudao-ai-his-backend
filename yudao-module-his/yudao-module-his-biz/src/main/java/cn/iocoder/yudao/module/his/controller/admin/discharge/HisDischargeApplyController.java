package cn.iocoder.yudao.module.his.controller.admin.discharge;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeApplyDO;
import cn.iocoder.yudao.module.his.enums.DischargeWayEnum;
import cn.iocoder.yudao.module.his.service.discharge.HisDischargeApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * HIS 出院申请 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS出院申请")
@RestController
@RequestMapping("/his/discharge-apply")
@Validated
public class HisDischargeApplyController {

    @Resource
    private HisDischargeApplyService dischargeApplyService;

    @PostMapping("/create")
    @Operation(summary = "创建出院申请")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:create')")
    public CommonResult<Long> createDischargeApply(@Valid @RequestBody HisDischargeApplySaveReqVO createReqVO) {
        return success(dischargeApplyService.createDischargeApply(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新出院申请")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:update')")
    public CommonResult<Boolean> updateDischargeApply(@Valid @RequestBody HisDischargeApplySaveReqVO updateReqVO) {
        dischargeApplyService.updateDischargeApply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除出院申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:delete')")
    public CommonResult<Boolean> deleteDischargeApply(@RequestParam("id") Long id) {
        dischargeApplyService.deleteDischargeApply(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得出院申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<HisDischargeApplyRespVO> getDischargeApply(@RequestParam("id") Long id) {
        HisDischargeApplyDO dischargeApply = dischargeApplyService.getDischargeApply(id);
        return success(convertToRespVO(dischargeApply));
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据申请单号获得出院申请")
    @Parameter(name = "applyNo", description = "申请单号", required = true, example = "D202606180001")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<HisDischargeApplyRespVO> getDischargeApplyByNo(@RequestParam("applyNo") String applyNo) {
        HisDischargeApplyDO dischargeApply = dischargeApplyService.getDischargeApplyByNo(applyNo);
        return success(convertToRespVO(dischargeApply));
    }

    @GetMapping("/get-by-admission")
    @Operation(summary = "根据入院记录ID获得出院申请")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<HisDischargeApplyRespVO> getDischargeApplyByAdmission(@RequestParam("admissionId") Long admissionId) {
        HisDischargeApplyDO dischargeApply = dischargeApplyService.getDischargeApplyByAdmissionId(admissionId);
        return success(convertToRespVO(dischargeApply));
    }

    @GetMapping("/page")
    @Operation(summary = "获得出院申请分页")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<PageResult<HisDischargeApplyRespVO>> getDischargeApplyPage(@Valid HisDischargeApplyPageReqVO pageReqVO) {
        PageResult<HisDischargeApplyDO> pageResult = dischargeApplyService.getDischargeApplyPage(pageReqVO);
        return success(convertToRespVOPageResult(pageResult));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "获得患者的出院申请列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<List<HisDischargeApplyRespVO>> getDischargeApplyListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisDischargeApplyDO> list = dischargeApplyService.getDischargeApplyListByPatientId(patientId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/pending-list")
    @Operation(summary = "获得待审批出院申请列表")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<List<HisDischargeApplyRespVO>> getPendingDischargeApplyList() {
        List<HisDischargeApplyDO> list = dischargeApplyService.getPendingDischargeApplyList();
        return success(convertToRespVOList(list));
    }

    @GetMapping("/pending-list-by-dept")
    @Operation(summary = "获得科室待审批出院申请列表")
    @Parameter(name = "deptId", description = "科室ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<List<HisDischargeApplyRespVO>> getPendingDischargeApplyListByDept(@RequestParam("deptId") Long deptId) {
        List<HisDischargeApplyDO> list = dischargeApplyService.getPendingDischargeApplyListByDept(deptId);
        return success(convertToRespVOList(list));
    }

    @PostMapping("/approve")
    @Operation(summary = "审批出院申请")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:approve')")
    public CommonResult<Boolean> approveDischargeApply(@Valid @RequestBody HisDischargeApplyApproveReqVO reqVO) {
        dischargeApplyService.approveDischargeApply(reqVO);
        return success(true);
    }

    @PostMapping("/reject")
    @Operation(summary = "驳回出院申请")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:approve')")
    public CommonResult<Boolean> rejectDischargeApply(@Valid @RequestBody HisDischargeApplyRejectReqVO reqVO) {
        dischargeApplyService.rejectDischargeApply(reqVO);
        return success(true);
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消出院申请")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:cancel')")
    public CommonResult<Boolean> cancelDischargeApply(@RequestParam("id") Long id,
                                                       @RequestParam(value = "cancelReason", required = false) String cancelReason) {
        dischargeApplyService.cancelDischargeApply(id, cancelReason);
        return success(true);
    }

    @PostMapping("/stop-orders")
    @Operation(summary = "停止医嘱")
    @Parameter(name = "id", description = "出院申请ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:discharge')")
    public CommonResult<Boolean> stopOrders(@RequestParam("id") Long id) {
        dischargeApplyService.stopOrders(id);
        return success(true);
    }

    @PostMapping("/confirm-discharge")
    @Operation(summary = "确认出院")
    @Parameter(name = "id", description = "出院申请ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:discharge')")
    public CommonResult<Boolean> confirmDischarge(@RequestParam("id") Long id) {
        dischargeApplyService.confirmDischarge(id);
        return success(true);
    }

    @GetMapping("/statistics")
    @Operation(summary = "获取出院申请统计数据")
    @PreAuthorize("@ss.hasPermission('his:discharge-apply:query')")
    public CommonResult<DischargeApplyStatisticsRespVO> getStatistics() {
        DischargeApplyStatisticsRespVO statistics = new DischargeApplyStatisticsRespVO();
        statistics.setTodayApply(dischargeApplyService.countTodayApply());
        statistics.setPending(dischargeApplyService.countPending());
        return success(statistics);
    }

    /**
     * 转换为响应VO
     */
    private HisDischargeApplyRespVO convertToRespVO(HisDischargeApplyDO dischargeApply) {
        if (dischargeApply == null) {
            return null;
        }
        HisDischargeApplyRespVO respVO = BeanUtils.toBean(dischargeApply, HisDischargeApplyRespVO.class);

        // 填充枚举名称
        if (dischargeApply.getDischargeWay() != null) {
            DischargeWayEnum dischargeWayEnum = DischargeWayEnum.valueOf(dischargeApply.getDischargeWay());
            respVO.setDischargeWayName(dischargeWayEnum != null ? dischargeWayEnum.getName() : null);
        }

        // 填充状态名称
        respVO.setStatusName(getStatusName(dischargeApply.getStatus()));

        // 填充治疗结果名称
        respVO.setTreatmentResultName(getTreatmentResultName(dischargeApply.getTreatmentResult()));

        // 计算住院天数
        if (dischargeApply.getAdmissionDate() != null) {
            java.time.LocalDateTime endTime = dischargeApply.getDischargeDate() != null
                    ? dischargeApply.getDischargeDate()
                    : java.time.LocalDateTime.now();
            respVO.setHospitalDays((int) ChronoUnit.DAYS.between(dischargeApply.getAdmissionDate(), endTime) + 1);
        }

        return respVO;
    }

    /**
     * 批量转换为响应VO
     */
    private List<HisDischargeApplyRespVO> convertToRespVOList(List<HisDischargeApplyDO> list) {
        return list.stream().map(this::convertToRespVO).collect(java.util.stream.Collectors.toList());
    }

    /**
     * 转换为分页响应VO
     */
    private PageResult<HisDischargeApplyRespVO> convertToRespVOPageResult(PageResult<HisDischargeApplyDO> pageResult) {
        PageResult<HisDischargeApplyRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToRespVOList(pageResult.getList()));
        return respPageResult;
    }

    /**
     * 获取状态名称
     */
    private String getStatusName(Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case 1:
                return "待审批";
            case 2:
                return "已审批";
            case 3:
                return "已驳回";
            case 4:
                return "已出院";
            case 5:
                return "已取消";
            default:
                return null;
        }
    }

    /**
     * 获取治疗结果名称
     */
    private String getTreatmentResultName(Integer treatmentResult) {
        if (treatmentResult == null) {
            return null;
        }
        switch (treatmentResult) {
            case 1:
                return "治愈";
            case 2:
                return "好转";
            case 3:
                return "未愈";
            case 4:
                return "死亡";
            case 5:
                return "其他";
            default:
                return null;
        }
    }

    /**
     * 出院申请统计数据 VO
     */
    @Data
    public static class DischargeApplyStatisticsRespVO {
        private Integer todayApply;
        private Integer pending;
    }

}