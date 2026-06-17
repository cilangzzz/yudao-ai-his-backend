package cn.iocoder.yudao.module.his.service.assess;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.assess.vo.HisAdmissionAssessPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.assess.vo.HisAdmissionAssessSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.assess.HisAdmissionAssessDO;
import cn.iocoder.yudao.module.his.dal.mysql.assess.HisAdmissionAssessMapper;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 入院评估 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisAdmissionAssessServiceImpl implements HisAdmissionAssessService {

    @Resource
    private HisAdmissionAssessMapper assessMapper;

    @Resource
    private HisAdmissionService admissionService;

    @Override
    public Long createAssess(HisAdmissionAssessSaveReqVO createReqVO) {
        // 校验住院记录
        HisAdmissionDO admission = admissionService.validateAdmissionExists(createReqVO.getAdmissionId());

        // 创建评估记录
        HisAdmissionAssessDO assess = BeanUtils.toBean(createReqVO, HisAdmissionAssessDO.class);
        if (assess.getAssessTime() == null) {
            assess.setAssessTime(java.time.LocalDateTime.now());
        }
        // 设置患者ID
        assess.setPatientId(admission.getPatientId());

        assessMapper.insert(assess);
        return assess.getId();
    }

    @Override
    public void updateAssess(HisAdmissionAssessSaveReqVO updateReqVO) {
        validateAssessExists(updateReqVO.getId());
        HisAdmissionAssessDO updateObj = BeanUtils.toBean(updateReqVO, HisAdmissionAssessDO.class);
        assessMapper.updateById(updateObj);
    }

    @Override
    public void deleteAssess(Long id) {
        validateAssessExists(id);
        assessMapper.deleteById(id);
    }

    @Override
    public HisAdmissionAssessDO getAssess(Long id) {
        return assessMapper.selectById(id);
    }

    @Override
    public PageResult<HisAdmissionAssessDO> getAssessPage(HisAdmissionAssessPageReqVO pageReqVO) {
        return assessMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisAdmissionAssessDO> getAssessListByAdmission(Long admissionId) {
        return assessMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public List<HisAdmissionAssessDO> getAssessListByPatient(Long patientId) {
        return assessMapper.selectListByPatientId(patientId);
    }

    @Override
    public HisAdmissionAssessDO getLatestAssess(Long admissionId, Integer assessType) {
        return assessMapper.selectLatestByAdmissionAndType(admissionId, assessType);
    }

    @Override
    public HisAdmissionAssessDO validateAssessExists(Long id) {
        if (id == null) {
            return null;
        }
        HisAdmissionAssessDO assess = assessMapper.selectById(id);
        if (assess == null) {
            throw exception(NURSING_RECORD_NOT_EXISTS);
        }
        return assess;
    }
}