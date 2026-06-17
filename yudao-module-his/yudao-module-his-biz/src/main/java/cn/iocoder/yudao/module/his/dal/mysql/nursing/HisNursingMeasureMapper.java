package cn.iocoder.yudao.module.his.dal.mysql.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingMeasureDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 护理措施 Mapper
 */
@Mapper
public interface HisNursingMeasureMapper extends BaseMapperX<HisNursingMeasureDO> {

    /**
     * 分页查询护理措施
     */
    default PageResult<HisNursingMeasureDO> selectPage(
            cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingMeasurePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisNursingMeasureDO>()
                .eqIfPresent(HisNursingMeasureDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisNursingMeasureDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisNursingMeasureDO::getAssessmentId, reqVO.getAssessmentId())
                .eqIfPresent(HisNursingMeasureDO::getMeasureType, reqVO.getMeasureType())
                .eqIfPresent(HisNursingMeasureDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisNursingMeasureDO::getStartTime, reqVO.getStartTime())
                .orderByDesc(HisNursingMeasureDO::getCreateTime));
    }

    /**
     * 按住院ID查询护理措施列表
     */
    default List<HisNursingMeasureDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisNursingMeasureDO>()
                .eq(HisNursingMeasureDO::getAdmissionId, admissionId)
                .orderByDesc(HisNursingMeasureDO::getCreateTime));
    }

    /**
     * 按评估ID查询护理措施列表
     */
    default List<HisNursingMeasureDO> selectListByAssessmentId(Long assessmentId) {
        return selectList(new LambdaQueryWrapperX<HisNursingMeasureDO>()
                .eq(HisNursingMeasureDO::getAssessmentId, assessmentId)
                .orderByDesc(HisNursingMeasureDO::getCreateTime));
    }

    /**
     * 按措施类型查询
     */
    default List<HisNursingMeasureDO> selectListByType(Long admissionId, Integer measureType) {
        return selectList(new LambdaQueryWrapperX<HisNursingMeasureDO>()
                .eq(HisNursingMeasureDO::getAdmissionId, admissionId)
                .eq(HisNursingMeasureDO::getMeasureType, measureType)
                .orderByDesc(HisNursingMeasureDO::getCreateTime));
    }

    /**
     * 查询执行中的护理措施
     */
    default List<HisNursingMeasureDO> selectExecutingList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisNursingMeasureDO>()
                .eq(HisNursingMeasureDO::getAdmissionId, admissionId)
                .eq(HisNursingMeasureDO::getStatus, 1)
                .orderByAsc(HisNursingMeasureDO::getStartTime));
    }

    /**
     * 查询待执行的护理措施
     */
    default List<HisNursingMeasureDO> selectPendingList(Long nurseId) {
        return selectList(new LambdaQueryWrapperX<HisNursingMeasureDO>()
                .eq(HisNursingMeasureDO::getNurseId, nurseId)
                .eq(HisNursingMeasureDO::getStatus, 0)
                .orderByAsc(HisNursingMeasureDO::getStartTime));
    }

    /**
     * 查询需要效果评价的措施
     */
    default List<HisNursingMeasureDO> selectNeedEvaluationList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisNursingMeasureDO>()
                .eq(HisNursingMeasureDO::getAdmissionId, admissionId)
                .eq(HisNursingMeasureDO::getStatus, 2)
                .isNull(HisNursingMeasureDO::getEffectEvaluation)
                .orderByDesc(HisNursingMeasureDO::getEndTime));
    }
}