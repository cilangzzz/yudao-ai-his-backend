package cn.iocoder.yudao.module.his.controller.admin.orderTemplate;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateDO;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateItemDO;
import cn.iocoder.yudao.module.his.enums.OrderCategoryEnum;
import cn.iocoder.yudao.module.his.enums.OrderTemplateCategoryEnum;
import cn.iocoder.yudao.module.his.enums.OrderTypeEnum;
import cn.iocoder.yudao.module.his.enums.OrderUrgencyEnum;
import cn.iocoder.yudao.module.his.service.orderTemplate.HisOrderTemplateService;
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

@Tag(name = "管理后台 - 医嘱模板")
@RestController
@RequestMapping("/his/order-template")
@Validated
public class HisOrderTemplateController {

    @Resource
    private HisOrderTemplateService orderTemplateService;

    @PostMapping("/create")
    @Operation(summary = "创建医嘱模板")
    @PreAuthorize("@ss.hasPermission('his:order-template:create')")
    public CommonResult<Long> createOrderTemplate(@Valid @RequestBody HisOrderTemplateSaveReqVO createReqVO) {
        return success(orderTemplateService.createOrderTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新医嘱模板")
    @PreAuthorize("@ss.hasPermission('his:order-template:update')")
    public CommonResult<Boolean> updateOrderTemplate(@Valid @RequestBody HisOrderTemplateSaveReqVO updateReqVO) {
        orderTemplateService.updateOrderTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除医嘱模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:delete')")
    public CommonResult<Boolean> deleteOrderTemplate(@RequestParam("id") Long id) {
        orderTemplateService.deleteOrderTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得医嘱模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<HisOrderTemplateRespVO> getOrderTemplate(@RequestParam("id") Long id) {
        HisOrderTemplateDO template = orderTemplateService.getOrderTemplate(id);
        HisOrderTemplateRespVO respVO = BeanUtils.toBean(template, HisOrderTemplateRespVO.class);

        // 获取模板明细
        if (template != null) {
            List<HisOrderTemplateItemDO> items = orderTemplateService.getOrderTemplateItemList(id);
            respVO.setItems(BeanUtils.toBean(items, HisOrderTemplateItemRespVO.class));

            // 设置分类名称
            respVO.setTemplateCategoryName(getTemplateCategoryName(template.getTemplateCategory()));
            respVO.setOrderTypeName(getOrderTypeName(template.getOrderType()));
        }

        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得医嘱模板分页")
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<PageResult<HisOrderTemplateRespVO>> getOrderTemplatePage(@Valid HisOrderTemplatePageReqVO pageReqVO) {
        PageResult<HisOrderTemplateDO> pageResult = orderTemplateService.getOrderTemplatePage(pageReqVO);
        PageResult<HisOrderTemplateRespVO> respPageResult = BeanUtils.toBean(pageResult, HisOrderTemplateRespVO.class);

        // 设置分类名称
        respPageResult.getList().forEach(vo -> {
            vo.setTemplateCategoryName(getTemplateCategoryName(vo.getTemplateCategory()));
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
        });

        return success(respPageResult);
    }

    @GetMapping("/list-by-category")
    @Operation(summary = "根据模板分类查询列表")
    @Parameter(name = "templateCategory", description = "模板分类", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<List<HisOrderTemplateRespVO>> getOrderTemplateListByCategory(@RequestParam("templateCategory") Integer templateCategory) {
        List<HisOrderTemplateDO> list = orderTemplateService.getOrderTemplateListByCategory(templateCategory);
        List<HisOrderTemplateRespVO> respList = BeanUtils.toBean(list, HisOrderTemplateRespVO.class);

        // 设置分类名称
        respList.forEach(vo -> {
            vo.setTemplateCategoryName(getTemplateCategoryName(vo.getTemplateCategory()));
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
        });

        return success(respList);
    }

    @GetMapping("/list-by-dept")
    @Operation(summary = "根据科室ID查询模板列表")
    @Parameter(name = "deptId", description = "科室ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<List<HisOrderTemplateRespVO>> getOrderTemplateListByDeptId(@RequestParam("deptId") Long deptId) {
        List<HisOrderTemplateDO> list = orderTemplateService.getOrderTemplateListByDeptId(deptId);
        List<HisOrderTemplateRespVO> respList = BeanUtils.toBean(list, HisOrderTemplateRespVO.class);

        // 设置分类名称
        respList.forEach(vo -> {
            vo.setTemplateCategoryName(getTemplateCategoryName(vo.getTemplateCategory()));
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
        });

        return success(respList);
    }

    @GetMapping("/list-by-doctor")
    @Operation(summary = "根据医生ID查询模板列表")
    @Parameter(name = "doctorId", description = "医生ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<List<HisOrderTemplateRespVO>> getOrderTemplateListByDoctorId(@RequestParam("doctorId") Long doctorId) {
        List<HisOrderTemplateDO> list = orderTemplateService.getOrderTemplateListByDoctorId(doctorId);
        List<HisOrderTemplateRespVO> respList = BeanUtils.toBean(list, HisOrderTemplateRespVO.class);

        // 设置分类名称
        respList.forEach(vo -> {
            vo.setTemplateCategoryName(getTemplateCategoryName(vo.getTemplateCategory()));
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
        });

        return success(respList);
    }

    @GetMapping("/list-by-order-type")
    @Operation(summary = "根据医嘱类型查询模板列表")
    @Parameter(name = "orderType", description = "医嘱类型", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<List<HisOrderTemplateRespVO>> getOrderTemplateListByOrderType(@RequestParam("orderType") Integer orderType) {
        List<HisOrderTemplateDO> list = orderTemplateService.getOrderTemplateListByOrderType(orderType);
        List<HisOrderTemplateRespVO> respList = BeanUtils.toBean(list, HisOrderTemplateRespVO.class);

        // 设置分类名称
        respList.forEach(vo -> {
            vo.setTemplateCategoryName(getTemplateCategoryName(vo.getTemplateCategory()));
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
        });

        return success(respList);
    }

    @GetMapping("/list-by-diagnosis")
    @Operation(summary = "根据疾病编码查询模板列表")
    @Parameter(name = "diagnosisCode", description = "疾病编码", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<List<HisOrderTemplateRespVO>> getOrderTemplateListByDiagnosisCode(@RequestParam("diagnosisCode") String diagnosisCode) {
        List<HisOrderTemplateDO> list = orderTemplateService.getOrderTemplateListByDiagnosisCode(diagnosisCode);
        List<HisOrderTemplateRespVO> respList = BeanUtils.toBean(list, HisOrderTemplateRespVO.class);

        // 设置分类名称
        respList.forEach(vo -> {
            vo.setTemplateCategoryName(getTemplateCategoryName(vo.getTemplateCategory()));
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
        });

        return success(respList);
    }

    @GetMapping("/list-by-name")
    @Operation(summary = "根据名称模糊查询模板列表")
    @Parameter(name = "templateName", description = "模板名称", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<List<HisOrderTemplateRespVO>> getOrderTemplateListByName(@RequestParam("templateName") String templateName) {
        List<HisOrderTemplateDO> list = orderTemplateService.getOrderTemplateListByName(templateName);
        List<HisOrderTemplateRespVO> respList = BeanUtils.toBean(list, HisOrderTemplateRespVO.class);

        // 设置分类名称
        respList.forEach(vo -> {
            vo.setTemplateCategoryName(getTemplateCategoryName(vo.getTemplateCategory()));
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
        });

        return success(respList);
    }

    @PostMapping("/copy")
    @Operation(summary = "复制医嘱模板")
    @Parameter(name = "id", description = "模板ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:create')")
    public CommonResult<Long> copyOrderTemplate(@RequestParam("id") Long id) {
        return success(orderTemplateService.copyOrderTemplate(id));
    }

    @PutMapping("/update-status")
    @Operation(summary = "启用/禁用医嘱模板")
    @PreAuthorize("@ss.hasPermission('his:order-template:update')")
    public CommonResult<Boolean> updateOrderTemplateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        orderTemplateService.updateOrderTemplateStatus(id, status);
        return success(true);
    }

    @PostMapping("/record-usage")
    @Operation(summary = "记录模板使用")
    @Parameter(name = "id", description = "模板ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<Boolean> recordTemplateUsage(@RequestParam("id") Long id) {
        orderTemplateService.recordTemplateUsage(id);
        return success(true);
    }

    @GetMapping("/items")
    @Operation(summary = "获得医嘱模板明细列表")
    @Parameter(name = "templateId", description = "模板ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:order-template:query')")
    public CommonResult<List<HisOrderTemplateItemRespVO>> getOrderTemplateItemList(@RequestParam("templateId") Long templateId) {
        List<HisOrderTemplateItemDO> list = orderTemplateService.getOrderTemplateItemList(templateId);
        List<HisOrderTemplateItemRespVO> respList = BeanUtils.toBean(list, HisOrderTemplateItemRespVO.class);

        // 设置分类名称
        respList.forEach(vo -> {
            vo.setOrderTypeName(getOrderTypeName(vo.getOrderType()));
            vo.setOrderCategoryName(getOrderCategoryName(vo.getOrderCategory()));
            vo.setUrgencyName(getUrgencyName(vo.getUrgency()));
        });

        return success(respList);
    }

    // ==================== 辅助方法 ====================

    /**
     * 获取模板分类名称
     */
    private String getTemplateCategoryName(Integer templateCategory) {
        if (templateCategory == null) {
            return null;
        }
        OrderTemplateCategoryEnum e = OrderTemplateCategoryEnum.valueOf(templateCategory);
        return e != null ? e.getName() : null;
    }

    /**
     * 获取医嘱类型名称
     */
    private String getOrderTypeName(Integer orderType) {
        if (orderType == null) {
            return null;
        }
        OrderTypeEnum e = OrderTypeEnum.valueOf(orderType);
        return e != null ? e.getName() : null;
    }

    /**
     * 获取医嘱分类名称
     */
    private String getOrderCategoryName(Integer orderCategory) {
        if (orderCategory == null) {
            return null;
        }
        OrderCategoryEnum e = OrderCategoryEnum.valueOf(orderCategory);
        return e != null ? e.getName() : null;
    }

    /**
     * 获取紧急程度名称
     */
    private String getUrgencyName(Integer urgency) {
        if (urgency == null) {
            return null;
        }
        OrderUrgencyEnum e = OrderUrgencyEnum.valueOf(urgency);
        return e != null ? e.getName() : null;
    }

}