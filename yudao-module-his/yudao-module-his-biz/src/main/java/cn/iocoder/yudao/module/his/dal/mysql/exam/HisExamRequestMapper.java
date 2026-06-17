package cn.iocoder.yudao.module.his.dal.mysql.exam;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamRequestPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 检查申请 Mapper
 */
@Mapper
public interface HisExamRequestMapper extends BaseMapperX<HisExamRequestDO> {

    /**
     * 分页查询检查申请
     */
    default PageResult<HisExamRequestDO> selectPage(HisExamRequestPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisExamRequestDO>()
                .likeIfPresent(HisExamRequestDO::getRequestNo, reqVO.getRequestNo())
                .likeIfPresent(HisExamRequestDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisExamRequestDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisExamRequestDO::getEncounterType, reqVO.getEncounterType())
                .eqIfPresent(HisExamRequestDO::getEncounterId, reqVO.getEncounterId())
                .eqIfPresent(HisExamRequestDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisExamRequestDO::getDoctorId, reqVO.getDoctorId())
                .eqIfPresent(HisExamRequestDO::getExamType, reqVO.getExamType())
                .likeIfPresent(HisExamRequestDO::getExamCategory, reqVO.getExamCategory())
                .eqIfPresent(HisExamRequestDO::getRequestStatus, reqVO.getRequestStatus())
                .eqIfPresent(HisExamRequestDO::getUrgency, reqVO.getUrgency())
                .eqIfPresent(HisExamRequestDO::getPayStatus, reqVO.getPayStatus())
                .betweenIfPresent(HisExamRequestDO::getAppointmentTime, reqVO.getAppointmentTime())
                .betweenIfPresent(HisExamRequestDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisExamRequestDO::getId));
    }

    /**
     * 根据申请单号查询
     */
    default HisExamRequestDO selectByRequestNo(String requestNo) {
        return selectOne(HisExamRequestDO::getRequestNo, requestNo);
    }

    /**
     * 根据患者ID查询申请列表
     */
    default List<HisExamRequestDO> selectListByPatientId(Long patientId) {
        return selectList(HisExamRequestDO::getPatientId, patientId);
    }

    /**
     * 根据就诊ID查询申请列表
     */
    default List<HisExamRequestDO> selectListByEncounterId(Long encounterId) {
        return selectList(HisExamRequestDO::getEncounterId, encounterId);
    }

    /**
     * 根据状态查询申请列表
     */
    default List<HisExamRequestDO> selectListByStatus(Integer status) {
        return selectList(HisExamRequestDO::getRequestStatus, status);
    }

    /**
     * 根据执行科室查询待检查列表
     */
    default List<HisExamRequestDO> selectListByExecutionDept(Long executionDeptId, Integer status) {
        return selectList(new LambdaQueryWrapperX<HisExamRequestDO>()
                .eqIfPresent(HisExamRequestDO::getExecutionDeptId, executionDeptId)
                .eqIfPresent(HisExamRequestDO::getRequestStatus, status)
                .orderByAsc(HisExamRequestDO::getAppointmentTime));
    }

}
