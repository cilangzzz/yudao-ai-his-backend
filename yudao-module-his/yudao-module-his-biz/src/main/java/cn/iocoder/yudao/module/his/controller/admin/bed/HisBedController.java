package cn.iocoder.yudao.module.his.controller.admin.bed;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.bed.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.bed.HisBedDO;
import cn.iocoder.yudao.module.his.enums.BedClassEnum;
import cn.iocoder.yudao.module.his.enums.BedStatusEnum;
import cn.iocoder.yudao.module.his.enums.BedTypeEnum;
import cn.iocoder.yudao.module.his.service.bed.HisBedService;
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
 * HIS 床位管理 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS床位管理")
@RestController
@RequestMapping("/his/bed")
@Validated
public class HisBedController {

    @Resource
    private HisBedService bedService;

    @PostMapping("/create")
    @Operation(summary = "创建床位")
    @PreAuthorize("@ss.hasPermission('his:bed:create')")
    public CommonResult<Long> createBed(@Valid @RequestBody HisBedSaveReqVO createReqVO) {
        return success(bedService.createBed(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新床位")
    @PreAuthorize("@ss.hasPermission('his:bed:update')")
    public CommonResult<Boolean> updateBed(@Valid @RequestBody HisBedSaveReqVO updateReqVO) {
        bedService.updateBed(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除床位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:bed:delete')")
    public CommonResult<Boolean> deleteBed(@RequestParam("id") Long id) {
        bedService.deleteBed(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得床位")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:bed:query')")
    public CommonResult<HisBedRespVO> getBed(@RequestParam("id") Long id) {
        HisBedDO bed = bedService.getBed(id);
        return success(convertToRespVO(bed));
    }

    @GetMapping("/page")
    @Operation(summary = "获得床位分页")
    @PreAuthorize("@ss.hasPermission('his:bed:query')")
    public CommonResult<PageResult<HisBedRespVO>> getBedPage(@Valid HisBedPageReqVO pageReqVO) {
        PageResult<HisBedDO> pageResult = bedService.getBedPage(pageReqVO);
        PageResult<HisBedRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToRespVOList(pageResult.getList()));
        return success(respPageResult);
    }

    @GetMapping("/list-by-ward")
    @Operation(summary = "获得病区下的床位列表")
    @Parameter(name = "wardId", description = "病区ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:bed:query')")
    public CommonResult<List<HisBedRespVO>> getBedListByWard(@RequestParam("wardId") Long wardId) {
        List<HisBedDO> list = bedService.getBedListByWardId(wardId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/empty-beds")
    @Operation(summary = "获得空床列表")
    @Parameter(name = "wardId", description = "病区ID（可选）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:bed:query')")
    public CommonResult<List<HisBedRespVO>> getEmptyBeds(
            @RequestParam(value = "wardId", required = false) Long wardId) {
        List<HisBedDO> list;
        if (wardId != null) {
            list = bedService.getEmptyBedListByWardId(wardId);
        } else {
            list = bedService.getEmptyBedList();
        }
        return success(convertToRespVOList(list));
    }

    @PostMapping("/occupy")
    @Operation(summary = "占用床位")
    @PreAuthorize("@ss.hasPermission('his:bed:occupy')")
    public CommonResult<Boolean> occupyBed(@RequestParam("id") Long id,
                                           @RequestParam("patientId") Long patientId,
                                           @RequestParam("patientName") String patientName,
                                           @RequestParam("admissionId") Long admissionId) {
        bedService.occupyBed(id, patientId, patientName, admissionId);
        return success(true);
    }

    @PostMapping("/release")
    @Operation(summary = "释放床位")
    @Parameter(name = "id", description = "床位ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:bed:release')")
    public CommonResult<Boolean> releaseBed(@RequestParam("id") Long id) {
        bedService.releaseBed(id);
        return success(true);
    }

    @PostMapping("/clean-complete")
    @Operation(summary = "清洁完成")
    @Parameter(name = "id", description = "床位ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:bed:update')")
    public CommonResult<Boolean> cleanComplete(@RequestParam("id") Long id) {
        bedService.cleanComplete(id);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新床位状态")
    @PreAuthorize("@ss.hasPermission('his:bed:update')")
    public CommonResult<Boolean> updateBedStatus(@RequestParam("id") Long id,
                                                  @RequestParam("status") Integer status) {
        bedService.updateBedStatus(id, status);
        return success(true);
    }

    @GetMapping("/overview")
    @Operation(summary = "获得床位总览统计")
    @Parameter(name = "wardId", description = "病区ID（可选，为空则统计全部）", example = "1")
    @PreAuthorize("@ss.hasPermission('his:bed:query')")
    public CommonResult<HisBedOverviewRespVO> getBedOverview(
            @RequestParam(value = "wardId", required = false) Long wardId) {
        return success(bedService.getBedOverview(wardId));
    }

    @PostMapping("/transfer")
    @Operation(summary = "转床")
    @PreAuthorize("@ss.hasPermission('his:bed:transfer')")
    public CommonResult<Boolean> transferBed(@Valid @RequestBody HisBedTransferReqVO reqVO) {
        bedService.transferBed(reqVO.getFromBedId(), reqVO.getToBedId(),
                reqVO.getPatientId(), reqVO.getPatientName(),
                reqVO.getAdmissionId(), reqVO.getReason());
        return success(true);
    }

    /**
     * 转换为响应VO
     */
    private HisBedRespVO convertToRespVO(HisBedDO bed) {
        if (bed == null) {
            return null;
        }
        HisBedRespVO respVO = BeanUtils.toBean(bed, HisBedRespVO.class);
        // 填充枚举名称
        respVO.setBedTypeName(BedTypeEnum.valueOf(bed.getBedType()) != null
                ? BedTypeEnum.valueOf(bed.getBedType()).getName() : null);
        respVO.setBedClassName(BedClassEnum.valueOf(bed.getBedClass()) != null
                ? BedClassEnum.valueOf(bed.getBedClass()).getName() : null);
        respVO.setBedStatusName(BedStatusEnum.valueOf(bed.getBedStatus()) != null
                ? BedStatusEnum.valueOf(bed.getBedStatus()).getName() : null);
        return respVO;
    }

    /**
     * 批量转换为响应VO
     */
    private List<HisBedRespVO> convertToRespVOList(List<HisBedDO> list) {
        return list.stream().map(this::convertToRespVO).collect(java.util.stream.Collectors.toList());
    }
}
