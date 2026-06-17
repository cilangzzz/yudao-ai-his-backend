package cn.iocoder.yudao.module.his.controller.admin.drug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundItemDO;
import cn.iocoder.yudao.module.his.service.drug.HisDrugInboundService;
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

@Tag(name = "管理后台 - 入库管理")
@RestController
@RequestMapping("/his/drug-inbound")
@Validated
public class HisDrugInboundController {

    @Resource
    private HisDrugInboundService inboundService;

    @PostMapping("/create")
    @Operation(summary = "创建入库记录")
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:create')")
    public CommonResult<Long> createInbound(@Valid @RequestBody HisDrugInboundSaveReqVO createReqVO) {
        return success(inboundService.createInbound(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新入库记录")
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:update')")
    public CommonResult<Boolean> updateInbound(@Valid @RequestBody HisDrugInboundSaveReqVO updateReqVO) {
        inboundService.updateInbound(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除入库记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:delete')")
    public CommonResult<Boolean> deleteInbound(@RequestParam("id") Long id) {
        inboundService.deleteInbound(id);
        return success(true);
    }

    @PostMapping("/audit")
    @Operation(summary = "审核入库记录")
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:audit')")
    public CommonResult<Boolean> auditInbound(@Valid @RequestBody HisDrugInboundAuditReqVO auditReqVO) {
        inboundService.auditInbound(auditReqVO.getId(), auditReqVO.getAuditBy(),
                auditReqVO.getAuditByName(), auditReqVO.getPassed(), auditReqVO.getRemark());
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得入库记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:query')")
    public CommonResult<HisDrugInboundRespVO> getInbound(@RequestParam("id") Long id) {
        HisDrugInboundDO inbound = inboundService.getInbound(id);
        return success(BeanUtils.toBean(inbound, HisDrugInboundRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得入库记录分页")
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:query')")
    public CommonResult<PageResult<HisDrugInboundRespVO>> getInboundPage(@Valid HisDrugInboundPageReqVO pageReqVO) {
        PageResult<HisDrugInboundDO> pageResult = inboundService.getInboundPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisDrugInboundRespVO.class));
    }

    @GetMapping("/items")
    @Operation(summary = "获得入库明细列表")
    @Parameter(name = "inboundId", description = "入库ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:query')")
    public CommonResult<List<HisDrugInboundItemRespVO>> getInboundItems(@RequestParam("inboundId") Long inboundId) {
        List<HisDrugInboundItemDO> list = inboundService.getInboundItems(inboundId);
        return success(BeanUtils.toBean(list, HisDrugInboundItemRespVO.class));
    }

    @GetMapping("/pending-audit-list")
    @Operation(summary = "获得待审核入库列表")
    @PreAuthorize("@ss.hasPermission('his:drug-inbound:query')")
    public CommonResult<List<HisDrugInboundRespVO>> getPendingAuditList() {
        List<HisDrugInboundDO> list = inboundService.getPendingAuditList();
        return success(BeanUtils.toBean(list, HisDrugInboundRespVO.class));
    }

}
