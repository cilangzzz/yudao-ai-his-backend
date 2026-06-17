package cn.iocoder.yudao.module.his.controller.admin.assess;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.assess.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.assess.HisAdmissionAssessDO;
import cn.iocoder.yudao.module.his.enums.AssessTypeEnum;
import cn.iocoder.yudao.module.his.enums.RiskLevelEnum;
import cn.iocoder.yudao.module.his.service.assess.HisAdmissionAssessService;
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
 * HIS 入院评估 Controller
 *
 * @author yudao
 */
@Tag(name = "管理后台 - HIS入院评估")
@RestController
@RequestMapping("/his/assess")
@Validated
public class HisAdmissionAssessController {

    @Resource
    private HisAdmissionAssessService assessService;

    @PostMapping("/create")
    @Operation(summary = "创建入院评估")
    @PreAuthorize("@ss.hasPermission('his:assess:create')")
    public CommonResult<Long> createAssess(@Valid @RequestBody HisAdmissionAssessSaveReqVO createReqVO) {
        return success(assessService.createAssess(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新入院评估")
    @PreAuthorize("@ss.hasPermission('his:assess:update')")
    public CommonResult<Boolean> updateAssess(@Valid @RequestBody HisAdmissionAssessSaveReqVO updateReqVO) {
        assessService.updateAssess(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除入院评估")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:assess:delete')")
    public CommonResult<Boolean> deleteAssess(@RequestParam("id") Long id) {
        assessService.deleteAssess(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得入院评估")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('his:assess:query')")
    public CommonResult<HisAdmissionAssessRespVO> getAssess(@RequestParam("id") Long id) {
        HisAdmissionAssessDO assess = assessService.getAssess(id);
        return success(convertToRespVO(assess));
    }

    @GetMapping("/page")
    @Operation(summary = "获得入院评估分页")
    @PreAuthorize("@ss.hasPermission('his:assess:query')")
    public CommonResult<PageResult<HisAdmissionAssessRespVO>> getAssessPage(@Valid HisAdmissionAssessPageReqVO pageReqVO) {
        PageResult<HisAdmissionAssessDO> pageResult = assessService.getAssessPage(pageReqVO);
        return success(convertToRespVOPageResult(pageResult));
    }

    @GetMapping("/list-by-admission")
    @Operation(summary = "获得住院记录的所有评估")
    @Parameter(name = "admissionId", description = "住院ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:assess:query')")
    public CommonResult<List<HisAdmissionAssessRespVO>> getAssessListByAdmission(@RequestParam("admissionId") Long admissionId) {
        List<HisAdmissionAssessDO> list = assessService.getAssessListByAdmission(admissionId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/list-by-patient")
    @Operation(summary = "获得患者的所有评估")
    @Parameter(name = "patientId", description = "患者ID", required = true)
    @PreAuthorize("@ss.hasPermission('his:assess:query')")
    public CommonResult<List<HisAdmissionAssessRespVO>> getAssessListByPatient(@RequestParam("patientId") Long patientId) {
        List<HisAdmissionAssessDO> list = assessService.getAssessListByPatient(patientId);
        return success(convertToRespVOList(list));
    }

    @GetMapping("/latest")
    @Operation(summary = "获得住院记录的最新评估")
    @PreAuthorize("@ss.hasPermission('his:assess:query')")
    public CommonResult<HisAdmissionAssessRespVO> getLatestAssess(@RequestParam("admissionId") Long admissionId,
                                                                  @RequestParam("assessType") Integer assessType) {
        HisAdmissionAssessDO assess = assessService.getLatestAssess(admissionId, assessType);
        return success(convertToRespVO(assess));
    }

    private HisAdmissionAssessRespVO convertToRespVO(HisAdmissionAssessDO assess) {
        if (assess == null) {
            return null;
        }
        HisAdmissionAssessRespVO respVO = BeanUtils.toBean(assess, HisAdmissionAssessRespVO.class);
        respVO.setAssessTypeName(AssessTypeEnum.valueOf(assess.getAssessType()) != null
                ? AssessTypeEnum.valueOf(assess.getAssessType()).getName() : null);
        respVO.setRiskLevelName(RiskLevelEnum.valueOf(assess.getRiskLevel()) != null
                ? RiskLevelEnum.valueOf(assess.getRiskLevel()).getName() : null);
        return respVO;
    }

    private List<HisAdmissionAssessRespVO> convertToRespVOList(List<HisAdmissionAssessDO> list) {
        return list.stream().map(this::convertToRespVO).collect(java.util.stream.Collectors.toList());
    }

    private PageResult<HisAdmissionAssessRespVO> convertToRespVOPageResult(PageResult<HisAdmissionAssessDO> pageResult) {
        PageResult<HisAdmissionAssessRespVO> respPageResult = new PageResult<>();
        respPageResult.setTotal(pageResult.getTotal());
        respPageResult.setList(convertToRespVOList(pageResult.getList()));
        return respPageResult;
    }
}