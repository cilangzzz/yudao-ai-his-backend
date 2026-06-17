package cn.iocoder.yudao.module.his.controller.admin.drug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundItemDO;
import cn.iocoder.yudao.module.his.service.drug.HisDrugOutboundService;
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

@Tag(name = "管理后台 - 出库管理")
@RestController
@RequestMapping("/his/drug-outbound")
@Validated
public class HisDrugOutboundController {

    @Resource
    private HisDrugOutboundService outboundService;

    @PostMapping("/create")
    @Operation(summary = "创建出库记录")
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:create')")
    public CommonResult<Long> createOutbound(@Valid @RequestBody HisDrugOutboundSaveReqVO createReqVO) {
        return success(outboundService.createOutbound(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新出库记录")
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:update')")
    public CommonResult<Boolean> updateOutbound(@Valid @RequestBody HisDrugOutboundSaveReqVO updateReqVO) {
        outboundService.updateOutbound(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除出库记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:delete')")
    public CommonResult<Boolean> deleteOutbound(@RequestParam("id") Long id) {
        outboundService.deleteOutbound(id);
        return success(true);
    }

    @PostMapping("/audit")
    @Operation(summary = "审核出库记录")
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:audit')")
    public CommonResult<Boolean> auditOutbound(@Valid @RequestBody HisDrugOutboundAuditReqVO auditReqVO) {
        outboundService.auditOutbound(auditReqVO.getId(), auditReqVO.getAuditBy(),
                auditReqVO.getAuditByName(), auditReqVO.getPassed(), auditReqVO.getRemark());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得出库记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:query')")
    public CommonResult<HisDrugOutboundRespVO> getOutbound(@RequestParam("id") Long id) {
        HisDrugOutboundDO outbound = outboundService.getOutbound(id);
        return success(BeanUtils.toBean(outbound, HisDrugOutboundRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得出库记录分页")
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:query')")
    public CommonResult<PageResult<HisDrugOutboundRespVO>> getOutboundPage(@Valid HisDrugOutboundPageReqVO pageReqVO) {
        PageResult<HisDrugOutboundDO> pageResult = outboundService.getOutboundPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDrugOutboundRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获得出库明细列表")
    @Parameter(name = "outboundId", description = "出库ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:query')")
    public CommonResult<List<HisDrugOutboundItemRespVO>> getOutboundItems(@RequestParam("outboundId") Long outboundId) {
        List<HisDrugOutboundItemDO> list = outboundService.getOutboundItems(outboundId);
        return success(BeanUtils.toBean(list, HisDrugOutboundItemRespVO.class));
    }

    @GetMapping("/pending-audit-list")
    @Operation(summary = "获得待审核出库列表")
    @PreAuthorize("@ss.hasPermission('his:drug-outbound:query')")
    public CommonResult<List<HisDrugOutboundRespVO>> getPendingAuditList() {
        List<HisDrugOutboundDO> list = outboundService.getPendingAuditList();
        return success(BeanUtils.toBean(list, HisDrugOutboundRespVO.class));
    }

}
