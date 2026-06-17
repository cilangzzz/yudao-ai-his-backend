package cn.iocoder.yudao.module.his.dal.mysql.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingAssessmentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 护理评估 Mapper
 */
@Mapper
public interface HisNursingAssessmentMapper extends BaseMapperX<HisNursingAssessmentDO> {

    /**
     * 分页查询护理评估
     */
    default PageResult<HisNursingAssessmentDO> selectPage(
            cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingAssessmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisNursingAssessmentDO>()
                .eqIfPresent(HisNursingAssessmentDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisNursingAssessmentDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisNursingAssessmentDO::getAssessmentType, reqVO.getAssessmentType())
                .eqIfPresent(HisNursingAssessmentDO::getRiskLevel, reqVO.getRiskLevel())
                .betweenIfPresent(HisNursingAssessmentDO::getAssessmentTime, reqVO.getAssessmentTime())
                .orderByDesc(HisNursingAssessmentDO::getAssessmentTime));
    }

    /**
     * 按住院ID查询护理评估列表
     */
    default List<HisNursingAssessmentDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisNursingAssessmentDO>()
                .eq(HisNursingAssessmentDO::getAdmissionId, admissionId)
                .orderByDesc(HisNursingAssessmentDO::getAssessmentTime));
    }

    /**
     * 按患者ID查询护理评估列表
     */
    default List<HisNursingAssessmentDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisNursingAssessmentDO>()
                .eq(HisNursingAssessmentDO::getPatientId, patientId)
                .orderByDesc(HisNursingAssessmentDO::getAssessmentTime));
    }

    /**
     * 按评估类型查询
     */
    default List<HisNursingAssessmentDO> selectListByType(Long admissionId, Integer assessmentType) {
        return selectList(new LambdaQueryWrapperX<HisNursingAssessmentDO>()
                .eq(HisNursingAssessmentDO::getAdmissionId, admissionId)
                .eq(HisNursingAssessmentDO::getAssessmentType, assessmentType)
                .orderByDesc(HisNursingAssessmentDO::getAssessmentTime));
    }

    /**
     * 获取最新的评估记录
     */
    default HisNursingAssessmentDO selectLatest(Long admissionId, Integer assessmentType) {
        return selectOne(new LambdaQueryWrapperX<HisNursingAssessmentDO>()
                .eq(HisNursingAssessmentDO::getAdmissionId, admissionId)
                .eq(HisNursingAssessmentDO::getAssessmentType, assessmentType)
                .orderByDesc(HisNursingAssessmentDO::getAssessmentTime)
                .last("LIMIT 1"));
    }

    /**
     * 查询高风险患者
     */
    default List<HisNursingAssessmentDO> selectHighRiskList(String riskLevel) {
        return selectList(new LambdaQueryWrapperX<HisNursingAssessmentDO>()
                .eq(HisNursingAssessmentDO::getRiskLevel, riskLevel)
                .orderByDesc(HisNursingAssessmentDO::getAssessmentTime));
    }

    /**
     * 查询需要重新评估的记录
     */
    default List<HisNursingAssessmentDO> selectNeedReassessmentList() {
        return selectList(new LambdaQueryWrapperX<HisNursingAssessmentDO>()
                .isNotNull(HisNursingAssessmentDO::getNextAssessmentTime)
                .le(HisNursingAssessmentDO::getNextAssessmentTime, java.time.LocalDateTime.now())
                .orderByAsc(HisNursingAssessmentDO::getNextAssessmentTime));
    }
}