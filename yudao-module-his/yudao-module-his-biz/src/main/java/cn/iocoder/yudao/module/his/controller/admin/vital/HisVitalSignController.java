package cn.iocoder.yudao.module.his.controller.admin.vital;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignRespVO;
import cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.vital.HisVitalSignDO;
import cn.iocoder.yudao.module.his.service.vital.HisVitalSignService;
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

@Tag(name = "管理后台 - 生命体征")
@RestController
@RequestMapping("/his/vital-sign")
@Validated
public class HisVitalSignController {

    @Resource
    private HisVitalSignService vitalSignService;

    @PostMapping("/create")
    @Operation(summary = "创建生命体征记录")
    @PreAuthorize("@ss.hasPermission('his:vital-sign:create')")
    public CommonResult<Long> createVitalSign(@Valid @RequestBody HisVitalSignSaveReqVO createReqVO) {
        return success(vitalSignService.createVitalSign(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新生命体征记录")
    @PreAuthorize("@ss.hasPermission('his:vital-sign:update')")
    public CommonResult<Boolean> updateVitalSign(@Valid @RequestBody HisVitalSignSaveReqVO updateReqVO) {
        vitalSignService.updateVitalSign(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生命体征记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:vital-sign:delete')")
    public CommonResult<Boolean> deleteVitalSign(@RequestParam("id") Long id) {
        vitalSignService.deleteVitalSign(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取生命体征记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:vital-sign:query')")
    public CommonResult<HisVitalSignRespVO> getVitalSign(@RequestParam("id") Long id) {
        HisVitalSignDO vitalSign = vitalSignService.getVitalSign(id);
        return success(BeanUtils.toBean(vitalSign, HisVitalSignRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取生命体征分页")
    @PreAuthorize("@ss.hasPermission('his:vital-sign:query')")
    public CommonResult<PageResult<HisVitalSignRespVO>> getVitalSignPage(@Valid HisVitalSignPageReqVO pageReqVO) {
        PageResult<HisVitalSignDO> pageResult = vitalSignService.getVitalSignPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisVitalSignRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "获取住院的生命体征列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:vital-sign:query')")
    public CommonResult<List<HisVitalSignRespVO>> getVitalSignListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisVitalSignDO> list = vitalSignService.getVitalSignListByAdmission(admissionId);
        return success(BeanUtils.toBean(list, HisVitalSignRespVO.class));
    }

    @GetMapping("/latest")
    @Operation(summary = "获取最近一次生命体征")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:vital-sign:query')")
    public CommonResult<HisVitalSignRespVO> getLatestVitalSign(@RequestParam("admissionId") Long admissionId) {
        HisVitalSignDO vitalSign = vitalSignService.getLatestVitalSign(admissionId);
        return success(BeanUtils.toBean(vitalSign, HisVitalSignRespVO.class));
    }

    @GetMapping("/abnormal-list")
    @Operation(summary = "获取异常生命体征列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:vital-sign:query')")
    public CommonResult<List<HisVitalSignRespVO>> getAbnormalVitalSignList(@RequestParam("admissionId") Long admissionId) {
        List<HisVitalSignDO> list = vitalSignService.getAbnormalVitalSignList(admissionId);
        return success(BeanUtils.toBean(list, HisVitalSignRespVO.class));
    }
}