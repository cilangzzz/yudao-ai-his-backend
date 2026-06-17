package cn.iocoder.yudao.module.his.dal.mysql.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingHandoverDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 护理交接班 Mapper
 */
@Mapper
public interface HisNursingHandoverMapper extends BaseMapperX<HisNursingHandoverDO> {

    /**
     * 分页查询护理交接班
     */
    default PageResult<HisNursingHandoverDO> selectPage(HisNursingHandoverPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eqIfPresent(HisNursingHandoverDO::getWardId, reqVO.getWardId())
                .eqIfPresent(HisNursingHandoverDO::getHandoverNurseId, reqVO.getHandoverNurseId())
                .eqIfPresent(HisNursingHandoverDO::getTakeoverNurseId, reqVO.getTakeoverNurseId())
                .eqIfPresent(HisNursingHandoverDO::getShiftType, reqVO.getShiftType())
                .eqIfPresent(HisNursingHandoverDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisNursingHandoverDO::getHandoverTime, reqVO.getHandoverTime())
                .orderByDesc(HisNursingHandoverDO::getHandoverTime));
    }

    /**
     * 按病区ID查询交接班列表
     */
    default List<HisNursingHandoverDO> selectListByWardId(Long wardId) {
        return selectList(new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eq(HisNursingHandoverDO::getWardId, wardId)
                .orderByDesc(HisNursingHandoverDO::getHandoverTime));
    }

    /**
     * 按病区和时间范围查询
     */
    default List<HisNursingHandoverDO> selectListByWardAndTime(Long wardId, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eq(HisNursingHandoverDO::getWardId, wardId)
                .between(HisNursingHandoverDO::getHandoverTime, startTime, endTime)
                .orderByDesc(HisNursingHandoverDO::getHandoverTime));
    }

    /**
     * 按交班护士查询
     */
    default List<HisNursingHandoverDO> selectListByHandoverNurse(Long nurseId) {
        return selectList(new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eq(HisNursingHandoverDO::getHandoverNurseId, nurseId)
                .orderByDesc(HisNursingHandoverDO::getHandoverTime));
    }

    /**
     * 按接班护士查询
     */
    default List<HisNursingHandoverDO> selectListByTakeoverNurse(Long nurseId) {
        return selectList(new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eq(HisNursingHandoverDO::getTakeoverNurseId, nurseId)
                .orderByDesc(HisNursingHandoverDO::getHandoverTime));
    }

    /**
     * 查询待接班的记录
     */
    default List<HisNursingHandoverDO> selectPendingTakeoverList(Long wardId) {
        return selectList(new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eqIfPresent(HisNursingHandoverDO::getWardId, wardId)
                .eq(HisNursingHandoverDO::getStatus, 0)
                .orderByAsc(HisNursingHandoverDO::getHandoverTime));
    }

    /**
     * 查询最新的交接班记录
     */
    default HisNursingHandoverDO selectLatestByWard(Long wardId) {
        return selectOne(new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eq(HisNursingHandoverDO::getWardId, wardId)
                .eq(HisNursingHandoverDO::getStatus, 1)
                .orderByDesc(HisNursingHandoverDO::getHandoverTime)
                .last("LIMIT 1"));
    }

    /**
     * 按班次查询
     */
    default List<HisNursingHandoverDO> selectListByShift(Long wardId, Integer shiftType, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisNursingHandoverDO>()
                .eq(HisNursingHandoverDO::getWardId, wardId)
                .eq(HisNursingHandoverDO::getShiftType, shiftType)
                .between(HisNursingHandoverDO::getHandoverTime, startTime, endTime)
                .orderByDesc(HisNursingHandoverDO::getHandoverTime));
    }
}