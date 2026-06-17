package cn.iocoder.yudao.module.his.service.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingAssessmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingAssessmentSaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingAssessmentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingRecordDO;
import cn.iocoder.yudao.module.his.dal.mysql.nursing.HisNursingAssessmentMapper;
import cn.iocoder.yudao.module.his.dal.mysql.nursing.HisNursingRecordMapper;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.NURSING_RECORD_NOT_EXISTS;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.NURSING_ASSESSMENT_NOT_EXISTS;

/**
 * 护理记录 Service 实现类
 */
@Service
@Validated
public class HisNursingRecordServiceImpl implements HisNursingRecordService {

    @Resource
    private HisNursingRecordMapper nursingRecordMapper;

    @Resource
    private HisNursingAssessmentMapper nursingAssessmentMapper;

    @Resource
    private HisAdmissionService admissionService;

    @Override
    public Long createNursingRecord(HisNursingRecordSaveReqVO createReqVO) {
        // 校验住院记录
        HisAdmissionDO admission = admissionService.validateAdmissionExists(createReqVO.getAdmissionId());

        // 创建护理记录
        HisNursingRecordDO record = BeanUtils.toBean(createReqVO, HisNursingRecordDO.class);
        record.setPatientId(admission.getPatientId());
        record.setPatientName(admission.getPatientName());
        if (record.getRecordTime() == null) {
            record.setRecordTime(LocalDateTime.now());
        }
        record.setSignatureStatus(0); // 未签名
        record.setAuditStatus(0); // 未审核

        nursingRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public void updateNursingRecord(HisNursingRecordSaveReqVO updateReqVO) {
        validateNursingRecordExists(updateReqVO.getId());
        HisNursingRecordDO updateObj = BeanUtils.toBean(updateReqVO, HisNursingRecordDO.class);
        nursingRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteNursingRecord(Long id) {
        validateNursingRecordExists(id);
        nursingRecordMapper.deleteById(id);
    }

    @Override
    public HisNursingRecordDO getNursingRecord(Long id) {
        return nursingRecordMapper.selectById(id);
    }

    @Override
    public PageResult<HisNursingRecordDO> getNursingRecordPage(HisNursingRecordPageReqVO pageReqVO) {
        return nursingRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisNursingRecordDO> getNursingRecordListByAdmission(Long admissionId) {
        return nursingRecordMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public void signNursingRecord(Long id) {
        HisNursingRecordDO record = validateNursingRecordExists(id);

        if (!record.canSign()) {
            throw new IllegalStateException("护理记录已签名，不能重复签名");
        }

        HisNursingRecordDO updateObj = new HisNursingRecordDO();
        updateObj.setId(id);
        updateObj.setSignatureStatus(1);
        updateObj.setSignatureTime(LocalDateTime.now());

        nursingRecordMapper.updateById(updateObj);
    }

    @Override
    public void auditNursingRecord(Long id, Long auditNurseId) {
        HisNursingRecordDO record = validateNursingRecordExists(id);

        if (!record.canAudit()) {
            throw new IllegalStateException("护理记录未签名或已审核");
        }

        HisNursingRecordDO updateObj = new HisNursingRecordDO();
        updateObj.setId(id);
        updateObj.setAuditStatus(1);
        updateObj.setAuditNurseId(auditNurseId);
        updateObj.setAuditTime(LocalDateTime.now());

        nursingRecordMapper.updateById(updateObj);
    }

    @Override
    public HisNursingRecordDO validateNursingRecordExists(Long id) {
        if (id == null) {
            return null;
        }
        HisNursingRecordDO record = nursingRecordMapper.selectById(id);
        if (record == null) {
            throw exception(NURSING_RECORD_NOT_EXISTS);
        }
        return record;
    }

    // ==================== 护理评估相关 ====================

    @Override
    public PageResult<HisNursingAssessmentDO> getNursingAssessmentPage(HisNursingAssessmentPageReqVO pageReqVO) {
        return nursingAssessmentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisNursingAssessmentDO> getNursingAssessmentListByAdmission(Long admissionId) {
        return nursingAssessmentMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public HisNursingAssessmentDO getLatestNursingAssessment(Long admissionId, Integer assessmentType) {
        return nursingAssessmentMapper.selectLatest(admissionId, assessmentType);
    }
}