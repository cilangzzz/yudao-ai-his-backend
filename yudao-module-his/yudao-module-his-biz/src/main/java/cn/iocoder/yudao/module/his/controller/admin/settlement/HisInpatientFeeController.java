package cn.iocoder.yudao.module.his.controller.admin.settlement;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientFeeDO;
import cn.iocoder.yudao.module.his.service.settlement.HisInpatientFeeService;
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
 * 住院费用明细 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - 住院费用明细")
@RestController
@RequestMapping("/his/inpatient-fee")
@Validated
public class HisInpatientFeeController {

    @Resource
    private HisInpatientFeeService feeService;

    @PostMapping("/create")
    @Operation(summary = "创建住院费用明细")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:create')")
    public CommonResult<Long> createFee(@Valid @RequestBody HisInpatientFeeSaveReqVO createReqVO) {
        return success(feeService.createFee(createReqVO));
    }

    @PostMapping("/batch-create")
    @Operation(summary = "批量创建住院费用明细")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:create')")
    public CommonResult<List<Long>> batchCreateFee(@Valid @RequestBody List<HisInpatientFeeSaveReqVO> createReqVOList) {
        return success(feeService.batchCreateFee(createReqVOList));
    }

    @PutMapping("/update")
    @Operation(summary = "更新住院费用明细")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:update')")
    public CommonResult<Boolean> updateFee(@Valid @RequestBody HisInpatientFeeSaveReqVO updateReqVO) {
        feeService.updateFee(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除住院费用明细")
    @Parameter(name = "id", description = "费用明细ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:delete')")
    public CommonResult<Boolean> deleteFee(@RequestParam("id") Long id) {
        feeService.deleteFee(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取住院费用明细详情")
    @Parameter(name = "id", description = "费用明细ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<HisInpatientFeeRespVO> getFee(@RequestParam("id") Long id) {
        HisInpatientFeeDO fee = feeService.getFee(id);
        return success(BeanUtils.toBean(fee, HisInpatientFeeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取住院费用明细分页")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<PageResult<HisInpatientFeeRespVO>> getFeePage(@Valid HisInpatientFeePageReqVO pageReqVO) {
        PageResult<HisInpatientFeeDO> pageResult = feeService.getFeePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisInpatientFeeRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "根据入院ID查询费用明细列表")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<List<HisInpatientFeeRespVO>> getFeeListByAdmissionId(@RequestParam("admissionId") Long admissionId) {
        List<HisInpatientFeeDO> list = feeService.getFeeListByAdmissionId(admissionId);
        return success(BeanUtils.toBean(list, HisInpatientFeeRespVO.class));
    }

    @GetMapping("/summary")
    @Operation(summary = "获取费用汇总统计")
    @Parameter(name = "admissionId", description = "入院记录ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<HisInpatientFeeRespVO> getFeeSummary(@RequestParam("admissionId") Long admissionId) {
        HisInpatientFeeDO summary = feeService.getFeeSummaryByAdmissionId(admissionId);
        return success(BeanUtils.toBean(summary, HisInpatientFeeRespVO.class));
    }

}
