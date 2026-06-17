package cn.iocoder.yudao.module.his.dal.mysql.schedule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpSchedulePageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.schedule.OpScheduleDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 排班 Mapper
 */
@Mapper
public interface OpScheduleMapper extends BaseMapperX<OpScheduleDO> {

    /**
     * 分页查询排班
     */
    default PageResult<OpScheduleDO> selectPage(OpSchedulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpScheduleDO>()
                .eqIfPresent(OpScheduleDO::getDoctorId, reqVO.getDoctorId())
                .eqIfPresent(OpScheduleDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(OpScheduleDO::getScheduleDate, reqVO.getScheduleDate())
                .eqIfPresent(OpScheduleDO::getStatus, reqVO.getStatus())
                .orderByDesc(OpScheduleDO::getScheduleDate)
                .orderByAsc(OpScheduleDO::getTimePeriod));
    }

    /**
     * 查询医生某天某时段的排班
     */
    default OpScheduleDO selectByDoctorAndDateAndPeriod(Long doctorId, LocalDate scheduleDate, Integer timePeriod) {
        return selectOne(new LambdaQueryWrapperX<OpScheduleDO>()
                .eq(OpScheduleDO::getDoctorId, doctorId)
                .eq(OpScheduleDO::getScheduleDate, scheduleDate)
                .eq(OpScheduleDO::getTimePeriod, timePeriod));
    }

    /**
     * 查询某日某科室可用排班（状态正常且有剩余号源）
     */
    default List<OpScheduleDO> selectAvailable(LocalDate scheduleDate, Long deptId) {
        return selectList(new LambdaQueryWrapperX<OpScheduleDO>()
                .eq(OpScheduleDO::getScheduleDate, scheduleDate)
                .eqIfPresent(OpScheduleDO::getDeptId, deptId)
                .eq(OpScheduleDO::getStatus, 1) // 正常状态
                .apply("total_quota > used_quota") // 有剩余号源
                .orderByAsc(OpScheduleDO::getTimePeriod));
    }

    /**
     * 查询某日可用排班列表
     */
    default List<OpScheduleDO> selectByDate(LocalDate scheduleDate) {
        return selectList(new LambdaQueryWrapperX<OpScheduleDO>()
                .eq(OpScheduleDO::getScheduleDate, scheduleDate)
                .orderByAsc(OpScheduleDO::getDeptId)
                .orderByAsc(OpScheduleDO::getTimePeriod));
    }

}