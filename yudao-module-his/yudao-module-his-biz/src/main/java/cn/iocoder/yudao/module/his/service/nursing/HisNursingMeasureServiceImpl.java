package cn.iocoder.yudao.module.his.service.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingMeasureEvaluationReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingMeasurePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingMeasureSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingMeasureDO;
import cn.iocoder.yudao.module.his.dal.mysql.nursing.HisNursingMeasureMapper;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.NURSING_MEASURE_NOT_EXISTS;

/**
 * 护理措施 Service 实现类
 */
@Service
@Validated
public class HisNursingMeasureServiceImpl implements HisNursingMeasureService {

    @Resource
    private HisNursingMeasureMapper nursingMeasureMapper;

    @Resource
    private HisAdmissionService admissionService;

    @Override
    public Long createNursingMeasure(HisNursingMeasureSaveReqVO createReqVO) {
        // 校验住院记录
        HisAdmissionDO admission = admissionService.validateAdmissionExists(createReqVO.getAdmissionId());

        // 创建护理措施
        HisNursingMeasureDO measure = BeanUtils.toBean(createReqVO, HisNursingMeasureDO.class);
        measure.setPatientId(admission.getPatientId());
        measure.setPatientName(admission.getPatientName());
        measure.setStatus(0); // 未开始
        if (measure.getStartTime() == null) {
            measure.setStartTime(LocalDateTime.now());
        }

        nursingMeasureMapper.insert(measure);
        return measure.getId();
    }

    @Override
    public void updateNursingMeasure(HisNursingMeasureSaveReqVO updateReqVO) {
        validateNursingMeasureExists(updateReqVO.getId());
        HisNursingMeasureDO updateObj = BeanUtils.toBean(updateReqVO, HisNursingMeasureDO.class);
        nursingMeasureMapper.updateById(updateObj);
    }

    @Override
    public void deleteNursingMeasure(Long id) {
        validateNursingMeasureExists(id);
        nursingMeasureMapper.deleteById(id);
    }

    @Override
    public HisNursingMeasureDO getNursingMeasure(Long id) {
        return nursingMeasureMapper.selectById(id);
    }

    @Override
    public PageResult<HisNursingMeasureDO> getNursingMeasurePage(HisNursingMeasurePageReqVO pageReqVO) {
        return nursingMeasureMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisNursingMeasureDO> getNursingMeasureListByAdmission(Long admissionId) {
        return nursingMeasureMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public List<HisNursingMeasureDO> getNursingMeasureListByAssessment(Long assessmentId) {
        return nursingMeasureMapper.selectListByAssessmentId(assessmentId);
    }

    @Override
    public void startNursingMeasure(Long id) {
        HisNursingMeasureDO measure = validateNursingMeasureExists(id);

        if (!measure.canExecute()) {
            throw new IllegalStateException("护理措施已开始执行，不能重复开始");
        }

        HisNursingMeasureDO updateObj = new HisNursingMeasureDO();
        updateObj.setId(id);
        updateObj.setStatus(1); // 执行中
        updateObj.setStartTime(LocalDateTime.now());

        nursingMeasureMapper.updateById(updateObj);
    }

    @Override
    public void completeNursingMeasure(Long id) {
        HisNursingMeasureDO measure = validateNursingMeasureExists(id);

        if (!measure.isExecuting()) {
            throw new IllegalStateException("护理措施未在执行中，不能完成");
        }

        HisNursingMeasureDO updateObj = new HisNursingMeasureDO();
        updateObj.setId(id);
        updateObj.setStatus(2); // 已完成
        updateObj.setEndTime(LocalDateTime.now());

        nursingMeasureMapper.updateById(updateObj);
    }

    @Override
    public void stopNursingMeasure(Long id, String reason) {
        HisNursingMeasureDO measure = validateNursingMeasureExists(id);

        if (measure.isCompleted() || measure.isStopped()) {
            throw new IllegalStateException("护理措施已完成或已停止，不能再次停止");
        }

        HisNursingMeasureDO updateObj = new HisNursingMeasureDO();
        updateObj.setId(id);
        updateObj.setStatus(3); // 已停止
        updateObj.setEndTime(LocalDateTime.now());
        updateObj.setRemark(reason);

        nursingMeasureMapper.updateById(updateObj);
    }

    @Override
    public void evaluateNursingMeasure(HisNursingMeasureEvaluationReqVO evaluationReqVO) {
        HisNursingMeasureDO measure = validateNursingMeasureExists(evaluationReqVO.getId());

        if (!measure.needsEvaluation()) {
            throw new IllegalStateException("护理措施未完成或已评价");
        }

        HisNursingMeasureDO updateObj = new HisNursingMeasureDO();
        updateObj.setId(evaluationReqVO.getId());
        updateObj.setEffectEvaluation(evaluationReqVO.getEffectEvaluation());
        updateObj.setEffectDescription(evaluationReqVO.getEffectDescription());
        updateObj.setEvaluationTime(LocalDateTime.now());

        nursingMeasureMapper.updateById(updateObj);
    }

    @Override
    public HisNursingMeasureDO validateNursingMeasureExists(Long id) {
        if (id == null) {
            return null;
        }
        HisNursingMeasureDO measure = nursingMeasureMapper.selectById(id);
        if (measure == null) {
            throw exception(NURSING_MEASURE_NOT_EXISTS);
        }
        return measure;
    }
}