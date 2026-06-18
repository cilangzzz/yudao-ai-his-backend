package cn.iocoder.yudao.module.his.controller.admin.settlement;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientFeeDO;
import cn.iocoder.yudao.module.his.service.settlement.HisInpatientFeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "管理后台 - 住院费用明细")
@RestController
@RequestMapping("/his/inpatient-fee")
@Validated
public class HisInpatientFeeController {

    @Resource
    private HisInpatientFeeService feeService;

    @PostMapping("/create")
    @ApiOperation("创建住院费用明细")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:create')")
    public CommonResult<Long> createFee(@Valid @RequestBody HisInpatientFeeSaveReqVO createReqVO) {
        return success(feeService.createFee(createReqVO));
    }

    @PostMapping("/batch-create")
    @ApiOperation("批量创建住院费用明细")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:create')")
    public CommonResult<List<Long>> batchCreateFee(@Valid @RequestBody List<HisInpatientFeeSaveReqVO> createReqVOList) {
        return success(feeService.batchCreateFee(createReqVOList));
    }

    @PutMapping("/update")
    @ApiOperation("更新住院费用明细")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:update')")
    public CommonResult<Boolean> updateFee(@Valid @RequestBody HisInpatientFeeSaveReqVO updateReqVO) {
        feeService.updateFee(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除住院费用明细")
    @ApiImplicitParam(name = "id", value = "费用明细ID", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:delete')")
    public CommonResult<Boolean> deleteFee(@RequestParam("id") Long id) {
        feeService.deleteFee(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获取住院费用明细详情")
    @ApiImplicitParam(name = "id", value = "费用明细ID", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<HisInpatientFeeRespVO> getFee(@RequestParam("id") Long id) {
        HisInpatientFeeDO fee = feeService.getFee(id);
        return success(BeanUtils.toBean(fee, HisInpatientFeeRespVO.class));
    }

    @GetMapping("/page")
    @ApiOperation("获取住院费用明细分页")
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<PageResult<HisInpatientFeeRespVO>> getFeePage(@Valid HisInpatientFeePageReqVO pageReqVO) {
        PageResult<HisInpatientFeeDO> pageResult = feeService.getFeePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisInpatientFeeRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @ApiOperation("根据入院ID查询费用明细列表")
    @ApiImplicitParam(name = "admissionId", value = "入院记录ID", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<List<HisInpatientFeeRespVO>> getFeeListByAdmissionId(@RequestParam("admissionId") Long admissionId) {
        List<HisInpatientFeeDO> list = feeService.getFeeListByAdmissionId(admissionId);
        return success(BeanUtils.toBean(list, HisInpatientFeeRespVO.class));
    }

    @GetMapping("/summary")
    @ApiOperation("获取费用汇总统计")
    @ApiImplicitParam(name = "admissionId", value = "入院记录ID", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('his:inpatient-fee:query')")
    public CommonResult<HisInpatientFeeRespVO> getFeeSummary(@RequestParam("admissionId") Long admissionId) {
        HisInpatientFeeDO summary = feeService.getFeeSummaryByAdmissionId(admissionId);
        return success(BeanUtils.toBean(summary, HisInpatientFeeRespVO.class));
    }

}
