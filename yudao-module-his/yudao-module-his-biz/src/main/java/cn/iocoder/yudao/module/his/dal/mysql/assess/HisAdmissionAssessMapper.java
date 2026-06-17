package cn.iocoder.yudao.module.his.dal.mysql.assess;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.assess.vo.HisAdmissionAssessPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.assess.HisAdmissionAssessDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 入院评估 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisAdmissionAssessMapper extends BaseMapperX<HisAdmissionAssessDO> {

    default PageResult<HisAdmissionAssessDO> selectPage(HisAdmissionAssessPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisAdmissionAssessDO>()
                .eqIfPresent(HisAdmissionAssessDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisAdmissionAssessDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisAdmissionAssessDO::getAssessType, reqVO.getAssessType())
                .betweenIfPresent(HisAdmissionAssessDO::getAssessTime, reqVO.getAssessTimeStart(), reqVO.getAssessTimeEnd())
                .orderByDesc(HisAdmissionAssessDO::getId));
    }

    default List<HisAdmissionAssessDO> selectListByAdmissionId(Long admissionId) {
        return selectList(HisAdmissionAssessDO::getAdmissionId, admissionId);
    }

    default List<HisAdmissionAssessDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisAdmissionAssessDO>()
                .eq(HisAdmissionAssessDO::getPatientId, patientId)
                .orderByDesc(HisAdmissionAssessDO::getAssessTime));
    }

    default HisAdmissionAssessDO selectLatestByAdmissionAndType(Long admissionId, Integer assessType) {
        return selectOne(new LambdaQueryWrapperX<HisAdmissionAssessDO>()
                .eq(HisAdmissionAssessDO::getAdmissionId, admissionId)
                .eq(HisAdmissionAssessDO::getAssessType, assessType)
                .orderByDesc(HisAdmissionAssessDO::getAssessTime)
                .last("LIMIT 1"));
    }
}