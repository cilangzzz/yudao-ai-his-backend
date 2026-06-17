package cn.iocoder.yudao.module.his.controller.admin.exam;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamItemPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamItemDO;
import cn.iocoder.yudao.module.his.service.exam.HisExamItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * HIS 检查项目字典管理 Controller
 */
@Tag(name = "管理后台 - HIS检查项目字典管理")
@RestController
@RequestMapping("/his/exam-item")
@Validated
public class HisExamItemController {

    @Resource
    private HisExamItemService examItemService;

    @PostMapping("/create")
    @Operation(summary = "创建检查项目")
    @PreAuthorize("@ss.hasPermission('his:exam-item:create')")
    public CommonResult<Long> createExamItem(@RequestBody HisExamItemDO examItem) {
        return success(examItemService.createExamItem(examItem));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检查项目")
    @PreAuthorize("@ss.hasPermission('his:exam-item:update')")
    public CommonResult<Boolean> updateExamItem(@RequestBody HisExamItemDO examItem) {
        examItemService.updateExamItem(examItem);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检查项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:exam-item:delete')")
    public CommonResult<Boolean> deleteExamItem(@RequestParam("id") Long id) {
        examItemService.deleteExamItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检查项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:exam-item:query')")
    public CommonResult<HisExamItemDO> getExamItem(@RequestParam("id") Long id) {
        return success(examItemService.getExamItem(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检查项目分页")
    @PreAuthorize("@ss.hasPermission('his:exam-item:query')")
    public CommonResult<PageResult<HisExamItemDO>> getExamItemPage(@Valid HisExamItemPageReqVO pageReqVO) {
        return success(examItemService.getExamItemPage(pageReqVO));
    }

    @GetMapping("/list-by-type")
    @Operation(summary = "根据检查类型获得项目列表")
    @Parameter(name = "examType", description = "检查类型", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:exam-item:query')")
    public CommonResult<List<HisExamItemDO>> getExamItemListByType(@RequestParam("examType") Integer examType) {
        return success(examItemService.getExamItemListByType(examType));
    }

    @GetMapping("/list-by-category")
    @Operation(summary = "根据项目类别获得项目列表")
    @Parameter(name = "itemCategory", description = "项目类别", required = true, example = "CT")
    @PreAuthorize("@ss.hasPermission('his:exam-item:query')")
    public CommonResult<List<HisExamItemDO>> getExamItemListByCategory(@RequestParam("itemCategory") String itemCategory) {
        return success(examItemService.getExamItemListByCategory(itemCategory));
    }

    @GetMapping("/list-active")
    @Operation(summary = "获得所有在用项目")
    @PreAuthorize("@ss.hasPermission('his:exam-item:query')")
    public CommonResult<List<HisExamItemDO>> getActiveExamItemList() {
        return success(examItemService.getActiveExamItemList());
    }

}
