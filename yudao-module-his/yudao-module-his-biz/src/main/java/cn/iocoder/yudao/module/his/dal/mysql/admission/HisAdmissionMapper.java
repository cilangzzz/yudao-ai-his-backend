package cn.iocoder.yudao.module.his.dal.mysql.admission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 入院记录 Mapper
 */
@Mapper
public interface HisAdmissionMapper extends BaseMapperX<HisAdmissionDO> {

    /**
     * 分页查询入院记录
     */
    default PageResult<HisAdmissionDO> selectPage(cn.iocoder.yudao.module.his.controller.admin.admission.vo.HisAdmissionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisAdmissionDO>()
                .eqIfPresent(HisAdmissionDO::getPatientId, reqVO.getPatientId())
                .likeIfPresent(HisAdmissionDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisAdmissionDO::getAdmissionDept, reqVO.getAdmissionDept())
                .eqIfPresent(HisAdmissionDO::getWardId, reqVO.getWardId())
                .eqIfPresent(HisAdmissionDO::getBedId, reqVO.getBedId())
                .eqIfPresent(HisAdmissionDO::getAdmissionStatus, reqVO.getAdmissionStatus())
                .betweenIfPresent(HisAdmissionDO::getAdmissionDate, reqVO.getAdmissionDate())
                .orderByDesc(HisAdmissionDO::getId));
    }

    /**
     * 根据住院号查询
     */
    default HisAdmissionDO selectByAdmissionNo(String admissionNo) {
        return selectOne(HisAdmissionDO::getAdmissionNo, admissionNo);
    }

    /**
     * 根据患者ID查询入院记录
     */
    default List<HisAdmissionDO> selectByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getPatientId, patientId)
                .orderByDesc(HisAdmissionDO::getAdmissionDate));
    }

    /**
     * 查询患者的在院记录
     */
    default HisAdmissionDO selectInpatientByPatientId(Long patientId) {
        return selectOne(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getPatientId, patientId)
                .eq(HisAdmissionDO::getAdmissionStatus, 1) // 在院状态
                .last("LIMIT 1"));
    }

    /**
     * 查询科室的在院患者列表
     */
    default List<HisAdmissionDO> selectInpatientsByDept(Long deptId) {
        return selectList(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getAdmissionDept, deptId)
                .eq(HisAdmissionDO::getAdmissionStatus, 1)
                .orderByAsc(HisAdmissionDO::getBedNo));
    }

    /**
     * 查询病区的在院患者列表
     */
    default List<HisAdmissionDO> selectInpatientsByWard(Long wardId) {
        return selectList(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getWardId, wardId)
                .eq(HisAdmissionDO::getAdmissionStatus, 1)
                .orderByAsc(HisAdmissionDO::getBedNo));
    }

    /**
     * 查询床位的当前住院记录
     */
    default HisAdmissionDO selectCurrentByBedId(Long bedId) {
        return selectOne(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getBedId, bedId)
                .eq(HisAdmissionDO::getAdmissionStatus, 1)
                .last("LIMIT 1"));
    }

    /**
     * 统计科室在院患者数量
     */
    default Long countInpatientsByDept(Long deptId) {
        return selectCount(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getAdmissionDept, deptId)
                .eq(HisAdmissionDO::getAdmissionStatus, 1));
    }

    /**
     * 统计病区在院患者数量
     */
    default Long countInpatientsByWard(Long wardId) {
        return selectCount(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getWardId, wardId)
                .eq(HisAdmissionDO::getAdmissionStatus, 1));
    }

    /**
     * 查询指定时间段的入院记录
     */
    default List<HisAdmissionDO> selectByAdmissionDateBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisAdmissionDO>()
                .ge(HisAdmissionDO::getAdmissionDate, startTime)
                .lt(HisAdmissionDO::getAdmissionDate, endTime)
                .orderByDesc(HisAdmissionDO::getAdmissionDate));
    }

    /**
     * 查询指定时间段的出院记录
     */
    default List<HisAdmissionDO> selectByDischargeDateBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisAdmissionDO>()
                .ge(HisAdmissionDO::getDischargeDate, startTime)
                .lt(HisAdmissionDO::getDischargeDate, endTime)
                .eq(HisAdmissionDO::getAdmissionStatus, 2) // 已出院
                .orderByDesc(HisAdmissionDO::getDischargeDate));
    }

    /**
     * 根据门诊挂号ID查询入院记录
     */
    default HisAdmissionDO selectByRegisterId(Long registerId) {
        return selectOne(new LambdaQueryWrapperX<HisAdmissionDO>()
                .eq(HisAdmissionDO::getRegisterId, registerId)
                .last("LIMIT 1"));
    }

}
