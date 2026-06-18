package cn.iocoder.yudao.module.his.controller.admin.discharge;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeSummaryDO;
import cn.iocoder.yudao.module.his.service.discharge.HisDischargeSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * HIS 出院小结 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS出院小结")
@RestController
@RequestMapping("/his/discharge-summary")
@Validated
public class HisDischargeSummaryController {

    @Resource
    private HisDischargeSummaryService dischargeSummaryService;

    @PostMapping("/create")
    @Operation(summary = "创建出院小结")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:create')")
    public CommonResult<Long> createDischargeSummary(@Valid @RequestBody HisDischargeSummarySaveReqVO createReqVO) {
        return success(dischargeSummaryService.createDischargeSummary(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新出院小结")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:update')")
    public CommonResult<Boolean> updateDischargeSummary(@Valid @RequestBody HisDischargeSummarySaveReqVO updateReqVO) {
        dischargeSummaryService.updateDischargeSummary(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除出院小结")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:delete')")
    public CommonResult<Boolean> deleteDischargeSummary(@RequestParam("id") Long id) {
        dischargeSummaryService.deleteDischargeSummary(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得出院小结")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:query')")
    public CommonResult<HisDischargeSummaryRespVO> getDischargeSummary(@RequestParam("id") Long id) {
        HisDischargeSummaryDO dischargeSummary = dischargeSummaryService.getDischargeSummary(id);
        return success(convertToRespVO(dischargeSummary));
    }

    @GetMapping("/get-by-discharge")
    @Operation(summary = "根据出院ID获得出院小结")
    @Parameter(name = "dischargeId", description = "出院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:query')")
    public CommonResult<HisDischargeSummaryRespVO> getDischargeSummaryByDischargeId(@RequestParam("dischargeId") Long dischargeId) {
        HisDischargeSummaryDO dischargeSummary = dischargeSummaryService.getDischargeSummaryByDischargeId(dischargeId);
        return success(convertToRespVO(dischargeSummary));
    }

    @GetMapping("/get-by-admission")
    @Operation(summary = "根据住院ID获得出院小结")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:query')")
    public CommonResult<HisDischargeSummaryRespVO> getDischargeSummaryByAdmissionId(@RequestParam("admissionId") Long admissionId) {
        HisDischargeSummaryDO dischargeSummary = dischargeSummaryService.getDischargeSummaryByAdmissionId(admissionId);
        return success(convertToRespVO(dischargeSummary));
    }

    @GetMapping("/page")
    @Operation(summary = "获得出院小结分页")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:query')")
    public CommonResult<PageResult<HisDischargeSummaryRespVO>> getDischargeSummaryPage(@Valid HisDischargeSummaryPageReqVO pageReqVO) {
        PageResult<HisDischargeSummaryDO> pageResult = dischargeSummaryService.getDischargeSummaryPage(pageReqVO);
        return success(convertToRespVOPageResult(pageResult));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "获得患者出院小结列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:query')")
    public CommonResult<List<HisDischargeSummaryRespVO>> getDischargeSummaryListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisDischargeSummaryDO> list = dischargeSummaryService.getDischargeSummaryListByPatientId(patientId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/pending-review-list")
    @Operation(summary = "获得待审核出院小结列表")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:query')")
    public CommonResult<List<HisDischargeSummaryRespVO>> getPendingReviewList() {
        List<HisDischargeSummaryDO> list = dischargeSummaryService.getPendingReviewList();
        return success(convertToRespVOList(list));
    }

    @PostMapping("/review")
    @Operation(summary = "审核出院小结")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:review')")
    public CommonResult<Boolean> reviewDischargeSummary(@Valid @RequestBody HisDischargeSummaryReviewReqVO reqVO) {
        dischargeSummaryService.reviewDischargeSummary(reqVO);
        return success(true);
    }

    @PostMapping("/withdraw-review")
    @Operation(summary = "撤回审核")
    @Parameter(name = "id", description = "出院小结ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:review')")
    public CommonResult<Boolean> withdrawReview(@RequestParam("id") Long id) {
        dischargeSummaryService.withdrawReview(id);
        return success(true);
    }

    @GetMapping("/statistics")
    @Operation(summary = "获取出院小结统计数据")
    @PreAuthorize("@ss.hasPermission('his:discharge-summary:query')")
    public CommonResult<DischargeSummaryStatisticsRespVO> getStatistics() {
        DischargeSummaryStatisticsRespVO statistics = new DischargeSummaryStatisticsRespVO();
        statistics.setPendingReview(dischargeSummaryService.countPendingReview());
        return success(statistics);
    }

    /**
     * 转换为响应VO
     */
    private HisDischargeSummaryRespVO convertToRespVO(HisDischargeSummaryDO dischargeSummary) {
        if (dischargeSummary == null) {
            return null;
        }
        HisDischargeSummaryRespVO respVO = BeanUtils.toBean(dischargeSummary, HisDischargeSummaryRespVO.class);
        // 状态名称由RespVO内部方法获取
        return respVO;
    }

    /**
     * 批量转换为响应VO
     */
    private List<HisDischargeSummaryRespVO> convertToRespVOList(List<HisDischargeSummaryDO> list) {
        return list.stream().map(this::convertToRespVO).collect(java.util.stream.Collectors.toList());
    }

    /**
     * 转换为分页响应VO
     */
    private PageResult<HisDischargeSummaryRespVO> convertToRespVOPageResult(PageResult<HisDischargeSummaryDO> pageResult) {
        PageResult<HisDischargeSummaryRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToRespVOList(pageResult.getList()));
        return respPageResult;
    }

    /**
     * 出院小结统计数据 VO
     */
    @Data
    public static class DischargeSummaryStatisticsRespVO {
        private Integer pendingReview;
    }
}