package cn.iocoder.yudao.module.his.dal.mysql.vital;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.vital.HisVitalSignDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 生命体征 Mapper
 */
@Mapper
public interface HisVitalSignMapper extends BaseMapperX<HisVitalSignDO> {

    /**
     * 分页查询生命体征
     */
    default PageResult<HisVitalSignDO> selectPage(
            cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisVitalSignDO>()
                .eqIfPresent(HisVitalSignDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisVitalSignDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisVitalSignDO::getAbnormalFlag, reqVO.getAbnormalFlag())
                .betweenIfPresent(HisVitalSignDO::getMeasureTime, reqVO.getMeasureTime())
                .orderByDesc(HisVitalSignDO::getMeasureTime));
    }

    /**
     * 按住院ID查询生命体征列表
     */
    default List<HisVitalSignDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisVitalSignDO>()
                .eq(HisVitalSignDO::getAdmissionId, admissionId)
                .orderByAsc(HisVitalSignDO::getMeasureTime));
    }

    /**
     * 按患者ID查询生命体征列表
     */
    default List<HisVitalSignDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisVitalSignDO>()
                .eq(HisVitalSignDO::getPatientId, patientId)
                .orderByDesc(HisVitalSignDO::getMeasureTime));
    }

    /**
     * 查询指定时间范围内的生命体征
     */
    default List<HisVitalSignDO> selectListByTimeRange(
            Long admissionId, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisVitalSignDO>()
                .eq(HisVitalSignDO::getAdmissionId, admissionId)
                .between(HisVitalSignDO::getMeasureTime, startTime, endTime)
                .orderByAsc(HisVitalSignDO::getMeasureTime));
    }

    /**
     * 查询异常生命体征
     */
    default List<HisVitalSignDO> selectAbnormalList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisVitalSignDO>()
                .eq(HisVitalSignDO::getAdmissionId, admissionId)
                .eq(HisVitalSignDO::getAbnormalFlag, 1)
                .orderByDesc(HisVitalSignDO::getMeasureTime));
    }

    /**
     * 获取最近一次生命体征
     */
    default HisVitalSignDO selectLatest(Long admissionId) {
        return selectOne(new LambdaQueryWrapperX<HisVitalSignDO>()
                .eq(HisVitalSignDO::getAdmissionId, admissionId)
                .orderByDesc(HisVitalSignDO::getMeasureTime)
                .last("LIMIT 1"));
    }
}