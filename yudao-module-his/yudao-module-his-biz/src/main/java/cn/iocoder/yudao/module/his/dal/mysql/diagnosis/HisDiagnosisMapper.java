package cn.iocoder.yudao.module.his.dal.mysql.diagnosis;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.diagnosis.vo.HisDiagnosisPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.diagnosis.HisDiagnosisDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 诊断记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisDiagnosisMapper extends BaseMapperX<HisDiagnosisDO> {

    /**
     * 分页查询诊断记录
     *
     * @param reqVO 分页查询条件
     * @return 分页结果
     */
    default PageResult<HisDiagnosisDO> selectPage(HisDiagnosisPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDiagnosisDO>()
                .eqIfPresent(HisDiagnosisDO::getEncounterId, reqVO.getEncounterId())
                .eqIfPresent(HisDiagnosisDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisDiagnosisDO::getDiagnosisType, reqVO.getDiagnosisType())
                .eqIfPresent(HisDiagnosisDO::getDiagnosisCode, reqVO.getDiagnosisCode())
                .likeIfPresent(HisDiagnosisDO::getDiagnosisName, reqVO.getDiagnosisName())
                .eqIfPresent(HisDiagnosisDO::getDiagnosisStatus, reqVO.getDiagnosisStatus())
                .betweenIfPresent(HisDiagnosisDO::getDiagnoseTime, reqVO.getDiagnoseTimeStart(), reqVO.getDiagnoseTimeEnd())
                .orderByDesc(HisDiagnosisDO::getId));
    }

    /**
     * 根据就诊ID查询诊断列表
     *
     * @param encounterId 就诊ID
     * @return 诊断列表
     */
    default List<HisDiagnosisDO> selectByEncounterId(Long encounterId) {
        return selectList(new LambdaQueryWrapperX<HisDiagnosisDO>()
                .eq(HisDiagnosisDO::getEncounterId, encounterId)
                .orderByAsc(HisDiagnosisDO::getDiagnosisSeq));
    }

    /**
     * 根据患者ID查询诊断列表
     *
     * @param patientId 患者ID
     * @return 诊断列表
     */
    default List<HisDiagnosisDO> selectByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisDiagnosisDO>()
                .eq(HisDiagnosisDO::getPatientId, patientId)
                .orderByDesc(HisDiagnosisDO::getDiagnoseTime));
    }

    /**
     * 根据就诊ID和诊断类型查询诊断列表
     *
     * @param encounterId  就诊ID
     * @param diagnosisType 诊断类型
     * @return 诊断列表
     */
    default List<HisDiagnosisDO> selectByEncounterIdAndType(Long encounterId, Integer diagnosisType) {
        return selectList(new LambdaQueryWrapperX<HisDiagnosisDO>()
                .eq(HisDiagnosisDO::getEncounterId, encounterId)
                .eq(HisDiagnosisDO::getDiagnosisType, diagnosisType)
                .orderByAsc(HisDiagnosisDO::getDiagnosisSeq));
    }

    /**
     * 查询就诊的主诊断
     *
     * @param encounterId 就诊ID
     * @return 主诊断记录
     */
    default HisDiagnosisDO selectPrimaryDiagnosis(Long encounterId) {
        return selectOne(new LambdaQueryWrapperX<HisDiagnosisDO>()
                .eq(HisDiagnosisDO::getEncounterId, encounterId)
                .eq(HisDiagnosisDO::getDiagnosisSeq, 1)
                .last("LIMIT 1"));
    }

    /**
     * 根据诊断编码查询
     *
     * @param diagnosisCode ICD-10诊断编码
     * @return 诊断列表
     */
    default List<HisDiagnosisDO> selectByDiagnosisCode(String diagnosisCode) {
        return selectList(new LambdaQueryWrapperX<HisDiagnosisDO>()
                .eq(HisDiagnosisDO::getDiagnosisCode, diagnosisCode)
                .orderByDesc(HisDiagnosisDO::getDiagnoseTime));
    }

    /**
     * 删除就诊的所有诊断记录
     *
     * @param encounterId 就诊ID
     */
    default void deleteByEncounterId(Long encounterId) {
        delete(new LambdaQueryWrapperX<HisDiagnosisDO>()
                .eq(HisDiagnosisDO::getEncounterId, encounterId));
    }

}