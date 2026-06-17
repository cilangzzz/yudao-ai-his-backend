package cn.iocoder.yudao.module.his.dal.mysql.appointment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.appointment.OpAppointmentDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 预约挂号 Mapper
 */
@Mapper
public interface OpAppointmentMapper extends BaseMapperX<OpAppointmentDO> {

    /**
     * 分页查询预约
     */
    default PageResult<OpAppointmentDO> selectPage(cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpAppointmentDO>()
                .eqIfPresent(OpAppointmentDO::getAppointmentNo, reqVO.getAppointmentNo())
                .eqIfPresent(OpAppointmentDO::getPatientId, reqVO.getPatientId())
                .likeIfPresent(OpAppointmentDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(OpAppointmentDO::getScheduleId, reqVO.getScheduleId())
                .eqIfPresent(OpAppointmentDO::getDoctorId, reqVO.getDoctorId())
                .likeIfPresent(OpAppointmentDO::getDoctorName, reqVO.getDoctorName())
                .eqIfPresent(OpAppointmentDO::getDeptId, reqVO.getDeptId())
                .likeIfPresent(OpAppointmentDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(OpAppointmentDO::getAppointmentDate, reqVO.getAppointmentDate())
                .eqIfPresent(OpAppointmentDO::getTimePeriod, reqVO.getTimePeriod())
                .eqIfPresent(OpAppointmentDO::getRegisterType, reqVO.getRegisterType())
                .eqIfPresent(OpAppointmentDO::getSourceType, reqVO.getSourceType())
                .eqIfPresent(OpAppointmentDO::getAppointmentStatus, reqVO.getAppointmentStatus())
                .orderByDesc(OpAppointmentDO::getId));
    }

    /**
     * 根据预约编号查询
     */
    default OpAppointmentDO selectByAppointmentNo(String appointmentNo) {
        return selectOne(OpAppointmentDO::getAppointmentNo, appointmentNo);
    }

    /**
     * 根据患者ID查询预约列表
     */
    default List<OpAppointmentDO> selectListByPatientId(Long patientId) {
        return selectList(OpAppointmentDO::getPatientId, patientId);
    }

    /**
     * 根据排班ID和预约日期查询预约列表
     */
    default List<OpAppointmentDO> selectListByScheduleIdAndDate(Long scheduleId, LocalDate appointmentDate) {
        return selectList(new LambdaQueryWrapperX<OpAppointmentDO>()
                .eq(OpAppointmentDO::getScheduleId, scheduleId)
                .eq(OpAppointmentDO::getAppointmentDate, appointmentDate));
    }

    /**
     * 根据医生ID和日期查询预约列表
     */
    default List<OpAppointmentDO> selectListByDoctorIdAndDate(Long doctorId, LocalDate appointmentDate) {
        return selectList(new LambdaQueryWrapperX<OpAppointmentDO>()
                .eq(OpAppointmentDO::getDoctorId, doctorId)
                .eq(OpAppointmentDO::getAppointmentDate, appointmentDate));
    }

    /**
     * 根据科室ID和日期查询预约列表
     */
    default List<OpAppointmentDO> selectListByDeptIdAndDate(Long deptId, LocalDate appointmentDate) {
        return selectList(new LambdaQueryWrapperX<OpAppointmentDO>()
                .eq(OpAppointmentDO::getDeptId, deptId)
                .eq(OpAppointmentDO::getAppointmentDate, appointmentDate));
    }

    /**
     * 查询某排班某日期某时段的最大排队序号
     */
    default Integer selectMaxQueueNo(Long scheduleId, LocalDate appointmentDate, Integer timePeriod) {
        OpAppointmentDO appointment = selectOne(new LambdaQueryWrapperX<OpAppointmentDO>()
                .eq(OpAppointmentDO::getScheduleId, scheduleId)
                .eq(OpAppointmentDO::getAppointmentDate, appointmentDate)
                .eq(OpAppointmentDO::getTimePeriod, timePeriod)
                .orderByDesc(OpAppointmentDO::getQueueNo)
                .last("LIMIT 1"));
        return appointment != null ? appointment.getQueueNo() : null;
    }

    /**
     * 统计某排班某日期的预约数量
     */
    default Long selectCountByScheduleIdAndDate(Long scheduleId, LocalDate appointmentDate) {
        return selectCount(new LambdaQueryWrapperX<OpAppointmentDO>()
                .eq(OpAppointmentDO::getScheduleId, scheduleId)
                .eq(OpAppointmentDO::getAppointmentDate, appointmentDate));
    }

}
