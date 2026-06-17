package cn.iocoder.yudao.module.his.controller.admin.lab;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestDO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestItemDO;
import cn.iocoder.yudao.module.his.service.lab.HisLabRequestService;
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
 * HIS 检验申请管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS检验申请管理")
@RestController
@RequestMapping("/his/lab-request")
@Validated
public class HisLabRequestController {

    @Resource
    private HisLabRequestService labRequestService;

    // ========== 基础CRUD ==========

    @PostMapping("/create")
    @Operation(summary = "创建检验申请")
    @PreAuthorize("@ss.hasPermission('his:lab-request:create')")
    public CommonResult<Long> createLabRequest(@Valid @RequestBody HisLabRequestSaveReqVO createReqVO) {
        return success(labRequestService.createLabRequest(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检验申请")
    @PreAuthorize("@ss.hasPermission('his:lab-request:update')")
    public CommonResult<Boolean> updateLabRequest(@Valid @RequestBody HisLabRequestSaveReqVO updateReqVO) {
        labRequestService.updateLabRequest(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检验申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:lab-request:delete')")
    public CommonResult<Boolean> deleteLabRequest(@RequestParam("id") Long id) {
        labRequestService.deleteLabRequest(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检验申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<HisLabRequestRespVO> getLabRequest(@RequestParam("id") Long id) {
        HisLabRequestDO labRequest = labRequestService.getLabRequest(id);
        HisLabRequestRespVO respVO = BeanUtils.toBean(labRequest, HisLabRequestRespVO.class);
        // 查询明细
        List<HisLabRequestItemDO> items = labRequestService.getLabRequestItemList(id);
        respVO.setItems(BeanUtils.toBean(items, HisLabRequestItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得检验申请分页")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<PageResult<HisLabRequestRespVO>> getLabRequestPage(@Valid HisLabRequestPageReqVO pageReqVO) {
        PageResult<HisLabRequestDO> pageResult = labRequestService.getLabRequestPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisLabRequestRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获得检验申请列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<List<HisLabRequestRespVO>> getLabRequestListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisLabRequestDO> list = labRequestService.getLabRequestListByPatientId(patientId);
        return success(BeanUtils.toBean(list, HisLabRequestRespVO.class));
    }

    @GetMapping("/list-by-source")
    @Operation(summary = "根据就诊ID获得检验申请列表")
    @Parameter(name = "sourceId", description = "就诊ID", required = true, example = "100")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<List<HisLabRequestRespVO>> getLabRequestListBySource(@RequestParam("sourceId") Long sourceId) {
        List<HisLabRequestDO> list = labRequestService.getLabRequestListBySourceId(sourceId);
        return success(BeanUtils.toBean(list, HisLabRequestRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获得检验申请明细列表")
    @Parameter(name = "requestId", description = "申请ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<List<HisLabRequestItemRespVO>> getLabRequestItemList(@RequestParam("requestId") Long requestId) {
        List<HisLabRequestItemDO> list = labRequestService.getLabRequestItemList(requestId);
        return success(BeanUtils.toBean(list, HisLabRequestItemRespVO.class));
    }

    @GetMapping("/by-specimen-barcode")
    @Operation(summary = "根据标本条码查询申请")
    @Parameter(name = "specimenBarcode", description = "标本条码", required = true, example = "SP202606180001")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<HisLabRequestRespVO> getLabRequestBySpecimenBarcode(@RequestParam("specimenBarcode") String specimenBarcode) {
        HisLabRequestDO labRequest = labRequestService.getLabRequestBySpecimenBarcode(specimenBarcode);
        HisLabRequestRespVO respVO = BeanUtils.toBean(labRequest, HisLabRequestRespVO.class);
        if (labRequest != null) {
            List<HisLabRequestItemDO> items = labRequestService.getLabRequestItemList(labRequest.getId());
            respVO.setItems(BeanUtils.toBean(items, HisLabRequestItemRespVO.class));
        }
        return success(respVO);
    }

    // ========== 标本采集 ==========

    @PutMapping("/collect-specimen")
    @Operation(summary = "采集标本")
    @PreAuthorize("@ss.hasPermission('his:lab-request:collect')")
    public CommonResult<Boolean> collectSpecimen(@RequestParam("id") Long id,
                                                  @RequestParam("specimenBarcode") String specimenBarcode,
                                                  @RequestParam("collectorId") Long collectorId,
                                                  @RequestParam("collectorName") String collectorName) {
        labRequestService.collectSpecimen(id, specimenBarcode, collectorId, collectorName);
        return success(true);
    }

    @PutMapping("/receive-specimen")
    @Operation(summary = "接收标本")
    @PreAuthorize("@ss.hasPermission('his:lab-request:receive')")
    public CommonResult<Boolean> receiveSpecimen(@RequestParam("id") Long id,
                                                  @RequestParam("receiverId") Long receiverId,
                                                  @RequestParam("receiverName") String receiverName) {
        labRequestService.receiveSpecimen(id, receiverId, receiverName);
        return success(true);
    }

    @PutMapping("/reject-specimen")
    @Operation(summary = "拒收标本")
    @PreAuthorize("@ss.hasPermission('his:lab-request:receive')")
    public CommonResult<Boolean> rejectSpecimen(@RequestParam("id") Long id,
                                                 @RequestParam("rejectReason") String rejectReason) {
        labRequestService.rejectSpecimen(id, rejectReason);
        return success(true);
    }

    // ========== 检验执行 ==========

    @PutMapping("/start")
    @Operation(summary = "开始检验")
    @PreAuthorize("@ss.hasPermission('his:lab-request:execute')")
    public CommonResult<Boolean> startLabRequest(@RequestParam("id") Long id,
                                                  @RequestParam("technicianId") Long technicianId,
                                                  @RequestParam("technicianName") String technicianName) {
        labRequestService.startLabRequest(id, technicianId, technicianName);
        return success(true);
    }

    @PutMapping("/complete")
    @Operation(summary = "完成检验")
    @Parameter(name = "id", description = "申请ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:lab-request:execute')")
    public CommonResult<Boolean> completeLabRequest(@RequestParam("id") Long id) {
        labRequestService.completeLabRequest(id);
        return success(true);
    }

    // ========== 报告管理 ==========

    @PutMapping("/generate-report")
    @Operation(summary = "生成报告")
    @PreAuthorize("@ss.hasPermission('his:lab-request:report')")
    public CommonResult<String> generateReport(@RequestParam("id") Long id,
                                                @RequestParam("reportDoctorId") Long reportDoctorId,
                                                @RequestParam("reportDoctorName") String reportDoctorName) {
        return success(labRequestService.generateReport(id, reportDoctorId, reportDoctorName));
    }

    @PutMapping("/audit-report")
    @Operation(summary = "审核报告")
    @PreAuthorize("@ss.hasPermission('his:lab-request:audit')")
    public CommonResult<Boolean> auditReport(@RequestParam("id") Long id,
                                              @RequestParam("auditDoctorId") Long auditDoctorId,
                                              @RequestParam("auditDoctorName") String auditDoctorName) {
        labRequestService.auditReport(id, auditDoctorId, auditDoctorName);
        return success(true);
    }

    @PutMapping("/publish-report")
    @Operation(summary = "发布报告")
    @Parameter(name = "id", description = "申请ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:lab-request:publish')")
    public CommonResult<Boolean> publishReport(@RequestParam("id") Long id) {
        labRequestService.publishReport(id);
        return success(true);
    }

    // ========== 危急值管理 ==========

    @PutMapping("/report-critical-value")
    @Operation(summary = "报告危急值")
    @PreAuthorize("@ss.hasPermission('his:lab-request:report')")
    public CommonResult<Boolean> reportCriticalValue(@RequestParam("id") Long id,
                                                      @RequestParam("criticalValueContent") String criticalValueContent) {
        labRequestService.reportCriticalValue(id, criticalValueContent);
        return success(true);
    }

    @PutMapping("/confirm-critical-value")
    @Operation(summary = "确认危急值")
    @PreAuthorize("@ss.hasPermission('his:lab-request:confirm-critical')")
    public CommonResult<Boolean> confirmCriticalValue(@RequestParam("id") Long id,
                                                       @RequestParam("confirmUserId") Long confirmUserId,
                                                       @RequestParam("confirmUserName") String confirmUserName) {
        labRequestService.confirmCriticalValue(id, confirmUserId, confirmUserName);
        return success(true);
    }

    // ========== 取消申请 ==========

    @PutMapping("/cancel")
    @Operation(summary = "取消检验申请")
    @PreAuthorize("@ss.hasPermission('his:lab-request:cancel')")
    public CommonResult<Boolean> cancelLabRequest(@RequestParam("id") Long id,
                                                   @RequestParam("cancelReason") String cancelReason) {
        labRequestService.cancelLabRequest(id, cancelReason);
        return success(true);
    }

    // ========== 工作列表 ==========

    @GetMapping("/pending-collect-list")
    @Operation(summary = "获取待采集的申请列表")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<List<HisLabRequestRespVO>> getPendingCollectList() {
        List<HisLabRequestDO> list = labRequestService.getPendingCollectList();
        return success(BeanUtils.toBean(list, HisLabRequestRespVO.class));
    }

    @GetMapping("/in-progress-list")
    @Operation(summary = "获取检验中的申请列表")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<List<HisLabRequestRespVO>> getInProgressList() {
        List<HisLabRequestDO> list = labRequestService.getInProgressList();
        return success(BeanUtils.toBean(list, HisLabRequestRespVO.class));
    }

    @GetMapping("/critical-value-unconfirmed-list")
    @Operation(summary = "获取危急值未确认的申请列表")
    @PreAuthorize("@ss.hasPermission('his:lab-request:query')")
    public CommonResult<List<HisLabRequestRespVO>> getCriticalValueUnconfirmedList() {
        List<HisLabRequestDO> list = labRequestService.getCriticalValueUnconfirmedList();
        return success(BeanUtils.toBean(list, HisLabRequestRespVO.class));
    }

}