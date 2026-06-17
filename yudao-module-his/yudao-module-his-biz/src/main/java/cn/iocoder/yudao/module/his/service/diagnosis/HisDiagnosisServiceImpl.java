package cn.iocoder.yudao.module.his.service.diagnosis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.diagnosis.HisDiagnosisDO;
import cn.iocoder.yudao.module.his.dal.mysql.diagnosis.HisDiagnosisMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.DIAGNOSIS_NOT_EXISTS;

/**
 * 诊断记录 Service 实现类
 */
@Service
@Validated
public class HisDiagnosisServiceImpl implements HisDiagnosisService {

    @Resource
    private HisDiagnosisMapper diagnosisMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDiagnosis(HisDiagnosisSaveReqVO createReqVO) {
        HisDiagnosisDO diagnosis = BeanUtils.toBean(createReqVO, HisDiagnosisDO.class);
        // 设置默认诊断状态
        if (diagnosis.getDiagnosisStatus() == null) {
            diagnosis.setDiagnosisStatus(1); // 默认初步诊断
        }
        diagnosisMapper.insert(diagnosis);
        return diagnosis.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDiagnosis(HisDiagnosisSaveReqVO updateReqVO) {
        // 1. 校验存在
        validateDiagnosisExists(updateReqVO.getId());

        // 2. 更新诊断记录
        HisDiagnosisDO updateObj = BeanUtils.toBean(updateReqVO, HisDiagnosisDO.class);
        diagnosisMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDiagnosis(Long id) {
        // 1. 校验存在
        validateDiagnosisExists(id);

        // 2. 删除
        diagnosisMapper.deleteById(id);
    }

    @Override
    public HisDiagnosisDO getDiagnosis(Long id) {
        return diagnosisMapper.selectById(id);
    }

    @Override
    public PageResult<HisDiagnosisDO> getDiagnosisPage(HisDiagnosisPageReqVO pageReqVO) {
        return diagnosisMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDiagnosisDO> getDiagnosisListByEncounterId(Long encounterId) {
        return diagnosisMapper.selectByEncounterId(encounterId);
    }

    @Override
    public List<HisDiagnosisDO> getDiagnosisListByPatientId(Long patientId) {
        return diagnosisMapper.selectByPatientId(patientId);
    }

    @Override
    public List<HisDiagnosisDO> getDiagnosisListByEncounterIdAndType(Long encounterId, Integer diagnosisType) {
        return diagnosisMapper.selectByEncounterIdAndType(encounterId, diagnosisType);
    }

    @Override
    public HisDiagnosisDO getPrimaryDiagnosis(Long encounterId) {
        return diagnosisMapper.selectPrimaryDiagnosis(encounterId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveDiagnosis(Long encounterId, Long patientId, List<HisDiagnosisSaveReqVO> diagnosisList) {
        if (diagnosisList == null || diagnosisList.isEmpty()) {
            return;
        }

        // 1. 删除原有诊断记录
        diagnosisMapper.deleteByEncounterId(encounterId);

        // 2. 批量插入新诊断记录
        for (HisDiagnosisSaveReqVO reqVO : diagnosisList) {
            HisDiagnosisDO diagnosis = BeanUtils.toBean(reqVO, HisDiagnosisDO.class);
            diagnosis.setEncounterId(encounterId);
            diagnosis.setPatientId(patientId);
            // 设置默认诊断状态
            if (diagnosis.getDiagnosisStatus() == null) {
                diagnosis.setDiagnosisStatus(1); // 默认初步诊断
            }
            diagnosisMapper.insert(diagnosis);
        }
    }

    @Override
    public HisDiagnosisDO validateDiagnosisExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDiagnosisDO diagnosis = diagnosisMapper.selectById(id);
        if (diagnosis == null) {
            throw exception(DIAGNOSIS_NOT_EXISTS);
        }
        return diagnosis;
    }

}
