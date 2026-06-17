package cn.iocoder.yudao.module.his.service.diagnosis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.diagnosis.HisDiagnosisDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 诊断记录 Service 接口
 */
public interface HisDiagnosisService {

    /**
     * 创建诊断记录
     *
     * @param createReqVO 创建信息
     * @return 诊断记录编号
     */
    Long createDiagnosis(@Valid HisDiagnosisSaveReqVO createReqVO);

    /**
     * 更新诊断记录
     *
     * @param updateReqVO 更新信息
     */
    void updateDiagnosis(@Valid HisDiagnosisSaveReqVO updateReqVO);

    /**
     * 删除诊断记录
     *
     * @param id 编号
     */
    void deleteDiagnosis(Long id);

    /**
     * 获取诊断记录
     *
     * @param id 编号
     * @return 诊断记录
     */
    HisDiagnosisDO getDiagnosis(Long id);

    /**
     * 获取诊断记录分页
     *
     * @param pageReqVO 分页查询
     * @return 诊断记录分页
     */
    PageResult<HisDiagnosisDO> getDiagnosisPage(HisDiagnosisPageReqVO pageReqVO);

    /**
     * 根据就诊ID获取诊断列表
     *
     * @param encounterId 就诊ID
     * @return 诊断列表
     */
    List<HisDiagnosisDO> getDiagnosisListByEncounterId(Long encounterId);

    /**
     * 根据患者ID获取诊断列表
     *
     * @param patientId 患者ID
     * @return 诊断列表
     */
    List<HisDiagnosisDO> getDiagnosisListByPatientId(Long patientId);

    /**
     * 根据就诊ID和诊断类型获取诊断列表
     *
     * @param encounterId  就诊ID
     * @param diagnosisType 诊断类型
     * @return 诊断列表
     */
    List<HisDiagnosisDO> getDiagnosisListByEncounterIdAndType(Long encounterId, Integer diagnosisType);

    /**
     * 获取就诊的主诊断
     *
     * @param encounterId 就诊ID
     * @return 主诊断记录
     */
    HisDiagnosisDO getPrimaryDiagnosis(Long encounterId);

    /**
     * 批量保存就诊诊断
     *
     * @param encounterId 就诊ID
     * @param patientId   患者ID
     * @param diagnosisList 诊断列表
     */
    void batchSaveDiagnosis(Long encounterId, Long patientId, List<HisDiagnosisSaveReqVO> diagnosisList);

    /**
     * 校验诊断记录是否存在
     *
     * @param id 编号
     * @return 诊断记录
     */
    HisDiagnosisDO validateDiagnosisExists(Long id);

}
