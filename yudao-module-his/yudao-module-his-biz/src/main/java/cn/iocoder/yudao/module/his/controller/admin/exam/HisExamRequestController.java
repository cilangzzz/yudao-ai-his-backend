package cn.iocoder.yudao.module.his.controller.admin.exam;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestDO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestItemDO;
import cn.iocoder.yudao.module.his.service.exam.HisExamRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * HIS 检查申请管理 Controller
 */
@Tag(name = "管理后台 - HIS检查申请管理")
@RestController
@RequestMapping("/his/exam-request")
@Validated
public class HisExamRequestController {

    @Resource
    private HisExamRequestService examRequestService;

    @PostMapping("/create")
    @Operation(summary = "创建检查申请")
    @PreAuthorize("@ss.hasPermission('his:exam-request:create')")
    public CommonResult<Long> createExamRequest(@Valid @RequestBody HisExamRequestSaveReqVO createReqVO) {
        return success(examRequestService.createExamRequest(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检查申请")
    @PreAuthorize("@ss.hasPermission('his:exam-request:update')")
    public CommonResult<Boolean> updateExamRequest(@Valid @RequestBody HisExamRequestSaveReqVO updateReqVO) {
        examRequestService.updateExamRequest(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检查申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:exam-request:delete')")
    public CommonResult<Boolean> deleteExamRequest(@RequestParam("id") Long id) {
        examRequestService.deleteExamRequest(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检查申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:exam-request:query')")
    public CommonResult<HisExamRequestRespVO> getExamRequest(@RequestParam("id") Long id) {
        HisExamRequestDO examRequest = examRequestService.getExamRequest(id);
        HisExamRequestRespVO respVO = BeanUtils.toBean(examRequest, HisExamRequestRespVO.class);
        // 查询明细
        List<HisExamRequestItemDO> items = examRequestService.getExamRequestItemList(id);
        respVO.setItems(BeanUtils.toBean(items, HisExamRequestItemRespVO.class));
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得检查申请分页")
    @PreAuthorize("@ss.hasPermission('his:exam-request:query')")
    public CommonResult<PageResult<HisExamRequestRespVO>> getExamRequestPage(@Valid HisExamRequestPageReqVO pageReqVO) {
        PageResult<HisExamRequestDO> pageResult = examRequestService.getExamRequestPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisExamRequestRespVO.class));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "根据患者ID获得检查申请列表")
    @Parameter(name = "patientId", description = "患者ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:exam-request:query')")
    public CommonResult<List<HisExamRequestRespVO>> getExamRequestListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisExamRequestDO> list = examRequestService.getExamRequestListByPatientId(patientId);
        return success(BeanUtils.toBean(list, HisExamRequestRespVO.class));
    }

    @GetMapping("/list-by-encounter")
    @Operation(summary = "根据就诊ID获得检查申请列表")
    @Parameter(name = "encounterId", description = "就诊ID", required = true, example = "100")
    @PreAuthorize("@ss.hasPermission('his:exam-request:query')")
    public CommonResult<List<HisExamRequestRespVO>> getExamRequestListByEncounter(@RequestParam("encounterId") Long encounterId) {
        List<HisExamRequestDO> list = examRequestService.getExamRequestListByEncounterId(encounterId);
        return success(BeanUtils.toBean(list, HisExamRequestRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获得检查申请明细列表")
    @Parameter(name = "requestId", description = "申请ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:exam-request:query')")
    public CommonResult<List<HisExamRequestItemRespVO>> getExamRequestItemList(@RequestParam("requestId") Long requestId) {
        List<HisExamRequestItemDO> list = examRequestService.getExamRequestItemList(requestId);
        return success(BeanUtils.toBean(list, HisExamRequestItemRespVO.class));
    }

    @PutMapping("/appoint")
    @Operation(summary = "预约检查")
    @PreAuthorize("@ss.hasPermission('his:exam-request:appoint')")
    public CommonResult<Boolean> appointExamRequest(@RequestParam("id") Long id,
                                                     @RequestParam("appointmentTime") LocalDateTime appointmentTime) {
        examRequestService.appointExamRequest(id, appointmentTime);
        return success(true);
    }

    @PutMapping("/check-in")
    @Operation(summary = "签到")
    @Parameter(name = "id", description = "申请ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:exam-request:check-in')")
    public CommonResult<Boolean> checkInExamRequest(@RequestParam("id") Long id) {
        examRequestService.checkInExamRequest(id);
        return success(true);
    }

    @PutMapping("/start")
    @Operation(summary = "开始检查")
    @Parameter(name = "id", description = "申请ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:exam-request:execute')")
    public CommonResult<Boolean> startExamRequest(@RequestParam("id") Long id) {
        examRequestService.startExamRequest(id);
        return success(true);
    }

    @PutMapping("/complete")
    @Operation(summary = "完成检查")
    @Parameter(name = "id", description = "申请ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:exam-request:execute')")
    public CommonResult<Boolean> completeExamRequest(@RequestParam("id") Long id) {
        examRequestService.completeExamRequest(id);
        return success(true);
    }

    @PutMapping("/cancel")
    @Operation(summary = "取消检查申请")
    @PreAuthorize("@ss.hasPermission('his:exam-request:cancel')")
    public CommonResult<Boolean> cancelExamRequest(@RequestParam("id") Long id,
                                                    @RequestParam("cancelReason") String cancelReason) {
        examRequestService.cancelExamRequest(id, cancelReason);
        return success(true);
    }

}