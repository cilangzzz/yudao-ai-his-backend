package cn.iocoder.yudao.module.his.controller.admin.admission;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.enums.AdmissionConditionEnum;
import cn.iocoder.yudao.module.his.enums.AdmissionStatusEnum;
import cn.iocoder.yudao.module.his.enums.AdmissionWayEnum;
import cn.iocoder.yudao.module.his.enums.DischargeWayEnum;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.time.temporal.ChronoUnit;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * HIS 入院管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS入院管理")
@RestController
@RequestMapping("/his/admission")
@Validated
public class HisAdmissionController {

    @Resource
    private HisAdmissionService admissionService;

    @PostMapping("/create")
    @Operation(summary = "入院登记")
    @PreAuthorize("@ss.hasPermission('his:admission:create')")
    public CommonResult<Long> createAdmission(@Valid @RequestBody HisAdmissionSaveReqVO createReqVO) {
        return success(admissionService.createAdmission(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新入院记录")
    @PreAuthorize("@ss.hasPermission('his:admission:update')")
    public CommonResult<Boolean> updateAdmission(@Valid @RequestBody HisAdmissionSaveReqVO updateReqVO) {
        admissionService.updateAdmission(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除入院记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:admission:delete')")
    public CommonResult<Boolean> deleteAdmission(@RequestParam("id") Long id) {
        admissionService.deleteAdmission(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得入院记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<HisAdmissionRespVO> getAdmission(@RequestParam("id") Long id) {
        HisAdmissionDO admission = admissionService.getAdmission(id);
        return success(convertToRespVO(admission));
    }

    @GetMapping("/get-by-no")
    @Operation(summary = "根据住院号获得入院记录")
    @Parameter(name = "admissionNo", description = "住院号", required = true, example = "A001")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<HisAdmissionRespVO> getAdmissionByNo(@RequestParam("admissionNo") String admissionNo) {
        HisAdmissionDO admission = admissionService.getAdmissionByNo(admissionNo);
        return success(convertToRespVO(admission));
    }

    @GetMapping("/page")
    @Operation(summary = "获得入院记录分页")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<PageResult<HisAdmissionRespVO>> getAdmissionPage(@Valid HisAdmissionPageReqVO pageReqVO) {
        PageResult<HisAdmissionDO> pageResult = admissionService.getAdmissionPage(pageReqVO);
        return success(convertToRespVOPageResult(pageResult));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "获得患者历史入院记录")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<List<HisAdmissionRespVO>> getAdmissionListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisAdmissionDO> list = admissionService.getAdmissionListByPatientId(patientId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/in-hospital-list")
    @Operation(summary = "获得在院患者列表")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<List<HisAdmissionRespVO>> getInHospitalList() {
        List<HisAdmissionDO> list = admissionService.getInHospitalList();
        return success(convertToRespVOList(list));
    }

    @GetMapping("/in-hospital-by-dept")
    @Operation(summary = "获得科室在院患者列表")
    @Parameter(name = "deptId", description = "科室ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<List<HisAdmissionRespVO>> getInHospitalListByDept(@RequestParam("deptId") Long deptId) {
        List<HisAdmissionDO> list = admissionService.getInHospitalListByDept(deptId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/in-hospital-by-ward")
    @Operation(summary = "获得病区在院患者列表")
    @Parameter(name = "wardId", description = "病区ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<List<HisAdmissionRespVO>> getInHospitalListByWard(@RequestParam("wardId") Long wardId) {
        List<HisAdmissionDO> list = admissionService.getInHospitalListByWard(wardId);
        return success(convertToRespVOList(list));
    }

    @PostMapping("/apply-discharge")
    @Operation(summary = "申请出院")
    @PreAuthorize("@ss.hasPermission('his:admission:discharge')")
    public CommonResult<Boolean> applyDischarge(@Valid @RequestBody DischargeApplyReqVO reqVO) {
        admissionService.applyDischarge(reqVO);
        return success(true);
    }

    @PostMapping("/confirm-discharge")
    @Operation(summary = "确认出院")
    @Parameter(name = "id", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:admission:discharge')")
    public CommonResult<Boolean> confirmDischarge(@RequestParam("id") Long id) {
        admissionService.confirmDischarge(id);
        return success(true);
    }

    @PostMapping("/transfer-dept")
    @Operation(summary = "转科")
    @PreAuthorize("@ss.hasPermission('his:admission:transfer')")
    public CommonResult<Boolean> transferDept(@RequestParam("id") Long id,
                                               @RequestParam("newDeptId") Long newDeptId,
                                               @RequestParam("newWardId") Long newWardId,
                                               @RequestParam(value = "newBedId", required = false) Long newBedId) {
        admissionService.transferDept(id, newDeptId, newWardId, newBedId);
        return success(true);
    }

    @PostMapping("/update-bed")
    @Operation(summary = "更换床位")
    @PreAuthorize("@ss.hasPermission('his:admission:update')")
    public CommonResult<Boolean> updateBed(@RequestParam("id") Long id,
                                           @RequestParam("newBedId") Long newBedId) {
        admissionService.updateBed(id, newBedId);
        return success(true);
    }

    @PostMapping("/emergency-admission")
    @Operation(summary = "紧急入院")
    @PreAuthorize("@ss.hasPermission('his:admission:create')")
    public CommonResult<Long> emergencyAdmission(@RequestParam("patientId") Long patientId,
                                                  @RequestParam("deptId") Long deptId) {
        return success(admissionService.emergencyAdmission(patientId, deptId));
    }

    @PostMapping("/add-deposit")
    @Operation(summary = "增加预交金")
    @PreAuthorize("@ss.hasPermission('his:admission:update')")
    public CommonResult<Boolean> addDeposit(@RequestParam("id") Long id,
                                            @RequestParam("amount") BigDecimal amount) {
        admissionService.addDeposit(id, amount);
        return success(true);
    }

    @GetMapping("/statistics")
    @Operation(summary = "获取住院统计数据")
    @PreAuthorize("@ss.hasPermission('his:admission:query')")
    public CommonResult<AdmissionStatisticsRespVO> getStatistics() {
        AdmissionStatisticsRespVO statistics = new AdmissionStatisticsRespVO();
        statistics.setTodayAdmission(admissionService.countTodayAdmission());
        statistics.setTodayDischarge(admissionService.countTodayDischarge());
        statistics.setInHospital(admissionService.countInHospital());
        return success(statistics);
    }

    /**
     * 转换为响应VO
     */
    private HisAdmissionRespVO convertToRespVO(HisAdmissionDO admission) {
        if (admission == null) {
            return null;
        }
        HisAdmissionRespVO respVO = BeanUtils.toBean(admission, HisAdmissionRespVO.class);
        // 填充枚举名称
        respVO.setAdmissionWayName(AdmissionWayEnum.valueOf(admission.getAdmissionWay()) != null
                ? AdmissionWayEnum.valueOf(admission.getAdmissionWay()).getName() : null);
        respVO.setAdmissionConditionName(AdmissionConditionEnum.valueOf(admission.getAdmissionCondition()) != null
                ? AdmissionConditionEnum.valueOf(admission.getAdmissionCondition()).getName() : null);
        respVO.setAdmissionStatusName(AdmissionStatusEnum.valueOf(admission.getAdmissionStatus()) != null
                ? AdmissionStatusEnum.valueOf(admission.getAdmissionStatus()).getName() : null);
        if (admission.getDischargeWay() != null) {
            respVO.setDischargeWayName(DischargeWayEnum.valueOf(admission.getDischargeWay()) != null
                    ? DischargeWayEnum.valueOf(admission.getDischargeWay()).getName() : null);
        }
        // 计算住院天数
        if (admission.getAdmissionDate() != null) {
            LocalDateTime endTime = admission.getDischargeDate() != null ? admission.getDischargeDate() : LocalDateTime.now();
            respVO.setHospitalDays((int) ChronoUnit.DAYS.between(admission.getAdmissionDate(), endTime) + 1);
        }
        return respVO;
    }

    /**
     * 批量转换为响应VO
     */
    private List<HisAdmissionRespVO> convertToRespVOList(List<HisAdmissionDO> list) {
        return list.stream().map(this::convertToRespVO).collect(java.util.stream.Collectors.toList());
    }

    /**
     * 转换为分页响应VO
     */
    private PageResult<HisAdmissionRespVO> convertToRespVOPageResult(PageResult<HisAdmissionDO> pageResult) {
        PageResult<HisAdmissionRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToRespVOList(pageResult.getList()));
        return respPageResult;
    }

    /**
     * 住院统计数据 VO
     */
    @Data
    public static class AdmissionStatisticsRespVO {
        private Integer todayAdmission;
        private Integer todayDischarge;
        private Integer inHospital;
    }
}