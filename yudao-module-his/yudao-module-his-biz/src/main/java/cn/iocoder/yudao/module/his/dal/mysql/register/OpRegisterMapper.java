package cn.iocoder.yudao.module.his.dal.mysql.register;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.register.OpRegisterDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 挂号 Mapper
 */
@Mapper
public interface OpRegisterMapper extends BaseMapperX<OpRegisterDO> {

    /**
     * 分页查询挂号
     */
    default PageResult<OpRegisterDO> selectPage(OpRegisterPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpRegisterDO>()
                .eqIfPresent(OpRegisterDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(OpRegisterDO::getDoctorId, reqVO.getDoctorId())
                .eqIfPresent(OpRegisterDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(OpRegisterDO::getRegisterDate, reqVO.getRegisterDate())
                .eqIfPresent(OpRegisterDO::getRegisterStatus, reqVO.getRegisterStatus())
                .orderByDesc(OpRegisterDO::getId));
    }

    /**
     * 查询患者的挂号记录
     */
    default List<OpRegisterDO> selectByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpRegisterDO>()
                .eq(OpRegisterDO::getPatientId, patientId)
                .orderByDesc(OpRegisterDO::getRegisterDate));
    }

    /**
     * 查询医生某天的挂号列表
     */
    default List<OpRegisterDO> selectByDoctorAndDate(Long doctorId, LocalDate registerDate) {
        return selectList(new LambdaQueryWrapperX<OpRegisterDO>()
                .eq(OpRegisterDO::getDoctorId, doctorId)
                .eq(OpRegisterDO::getRegisterDate, registerDate)
                .orderByAsc(OpRegisterDO::getQueueNo));
    }

    /**
     * 查询医生的候诊列表
     */
    default List<OpRegisterDO> selectWaitingByDoctor(Long doctorId, LocalDate registerDate) {
        return selectList(new LambdaQueryWrapperX<OpRegisterDO>()
                .eq(OpRegisterDO::getDoctorId, doctorId)
                .eq(OpRegisterDO::getRegisterDate, registerDate)
                .eq(OpRegisterDO::getRegisterStatus, 1) // 候诊状态
                .orderByAsc(OpRegisterDO::getQueueNo));
    }

    /**
     * 获取某排班当天最大排队号
     */
    default Integer selectMaxQueueNo(Long scheduleId, LocalDate registerDate) {
        OpRegisterDO register = selectOne(new LambdaQueryWrapperX<OpRegisterDO>()
                .eq(OpRegisterDO::getScheduleId, scheduleId)
                .eq(OpRegisterDO::getRegisterDate, registerDate)
                .orderByDesc(OpRegisterDO::getQueueNo)
                .last("LIMIT 1"));
        return register != null ? register.getQueueNo() : 0;
    }

    /**
     * 根据挂号编号查询
     */
    default OpRegisterDO selectByRegisterNo(String registerNo) {
        return selectOne(OpRegisterDO::getRegisterNo, registerNo);
    }

}