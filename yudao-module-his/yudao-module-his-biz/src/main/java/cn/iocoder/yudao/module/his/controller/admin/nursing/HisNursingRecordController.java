package cn.iocoder.yudao.module.his.controller.admin.nursing;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordRespVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingRecordDO;
import cn.iocoder.yudao.module.his.service.nursing.HisNursingRecordService;
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

@Tag(name = "管理后台 - 护理记录")
@RestController
@RequestMapping("/his/nursing-record")
@Validated
public class HisNursingRecordController {

    @Resource
    private HisNursingRecordService nursingRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建护理记录")
    @PreAuthorize("@ss.hasPermission('his:nursing-record:create')")
    public CommonResult<Long> createNursingRecord(@Valid @RequestBody HisNursingRecordSaveReqVO createReqVO) {
        return success(nursingRecordService.createNursingRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新护理记录")
    @PreAuthorize("@ss.hasPermission('his:nursing-record:update')")
    public CommonResult<Boolean> updateNursingRecord(@Valid @RequestBody HisNursingRecordSaveReqVO updateReqVO) {
        nursingRecordService.updateNursingRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除护理记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:nursing-record:delete')")
    public CommonResult<Boolean> deleteNursingRecord(@RequestParam("id") Long id) {
        nursingRecordService.deleteNursingRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取护理记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-record:query')")
    public CommonResult<HisNursingRecordRespVO> getNursingRecord(@RequestParam("id") Long id) {
        HisNursingRecordDO record = nursingRecordService.getNursingRecord(id);
        return success(BeanUtils.toBean(record, HisNursingRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取护理记录分页")
    @PreAuthorize("@ss.hasPermission('his:nursing-record:query')")
    public CommonResult<PageResult<HisNursingRecordRespVO>> getNursingRecordPage(@Valid HisNursingRecordPageReqVO pageReqVO) {
        PageResult<HisNursingRecordDO> pageResult = nursingRecordService.getNursingRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, HisNursingRecordRespVO.class));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "获取住院的护理记录列表")
    @Parameter(name = "admissionId", description = "住院ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('his:nursing-record:query')")
    public CommonResult<List<HisNursingRecordRespVO>> getNursingRecordListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisNursingRecordDO> list = nursingRecordService.getNursingRecordListByAdmission(admissionId);
        return success(BeanUtils.toBean(list, HisNursingRecordRespVO.class));
    }

    @PostMapping("/sign")
    @Operation(summary = "签名护理记录")
    @PreAuthorize("@ss.hasPermission('his:nursing-record:sign')")
    public CommonResult<Boolean> signNursingRecord(@RequestParam("id") Long id) {
        nursingRecordService.signNursingRecord(id);
        return success(true);
    }

    @PostMapping("/audit")
    @Operation(summary = "审核护理记录")
    @PreAuthorize("@ss.hasPermission('his:nursing-record:audit')")
    public CommonResult<Boolean> auditNursingRecord(
            @RequestParam("id") Long id,
            @RequestParam("auditNurseId") Long auditNurseId) {
        nursingRecordService.auditNursingRecord(id, auditNurseId);
        return success(true);
    }
}