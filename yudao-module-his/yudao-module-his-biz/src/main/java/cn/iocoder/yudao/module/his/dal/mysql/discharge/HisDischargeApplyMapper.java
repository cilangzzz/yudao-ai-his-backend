package cn.iocoder.yudao.module.his.dal.mysql.discharge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplyPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeApplyDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 出院申请 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisDischargeApplyMapper extends BaseMapperX<HisDischargeApplyDO> {

    /**
     * 根据申请单号查询
     */
    default HisDischargeApplyDO selectByApplyNo(String applyNo) {
        return selectOne(HisDischargeApplyDO::getApplyNo, applyNo);
    }

    /**
     * 根据入院记录ID查询
     */
    default HisDischargeApplyDO selectByAdmissionId(Long admissionId) {
        return selectOne(new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .eq(HisDischargeApplyDO::getAdmissionId, admissionId)
                .orderByDesc(HisDischargeApplyDO::getId)
                .last("LIMIT 1"));
    }

    /**
     * 根据入院记录ID查询有效的申请
     */
    default HisDischargeApplyDO selectValidByAdmissionId(Long admissionId) {
        return selectOne(new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .eq(HisDischargeApplyDO::getAdmissionId, admissionId)
                .in(HisDischargeApplyDO::getStatus, 1, 2) // 待审批或已审批
                .orderByDesc(HisDischargeApplyDO::getId)
                .last("LIMIT 1"));
    }

    /**
     * 分页查询
     */
    default PageResult<HisDischargeApplyDO> selectPage(HisDischargeApplyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .eqIfPresent(HisDischargeApplyDO::getApplyNo, reqVO.getApplyNo())
                .eqIfPresent(HisDischargeApplyDO::getAdmissionNo, reqVO.getAdmissionNo())
                .likeIfPresent(HisDischargeApplyDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisDischargeApplyDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisDischargeApplyDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisDischargeApplyDO::getWardId, reqVO.getWardId())
                .eqIfPresent(HisDischargeApplyDO::getStatus, reqVO.getStatus())
                .eqIfPresent(HisDischargeApplyDO::getDischargeWay, reqVO.getDischargeWay())
                .betweenIfPresent(HisDischargeApplyDO::getDischargeDate, reqVO.getDischargeDate())
                .betweenIfPresent(HisDischargeApplyDO::getApplyTime, reqVO.getApplyTime())
                .orderByDesc(HisDischargeApplyDO::getId));
    }

    /**
     * 根据患者ID查询列表
     */
    default List<HisDischargeApplyDO> selectByPatientId(Long patientId) {
        return selectList(HisDischargeApplyDO::getPatientId, patientId);
    }

    /**
     * 根据科室ID查询待审批列表
     */
    default List<HisDischargeApplyDO> selectPendingByDept(Long deptId) {
        return selectList(new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .eq(HisDischargeApplyDO::getDeptId, deptId)
                .eq(HisDischargeApplyDO::getStatus, 1)
                .orderByAsc(HisDischargeApplyDO::getApplyTime));
    }

    /**
     * 查询待审批列表
     */
    default List<HisDischargeApplyDO> selectPendingList() {
        return selectList(new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .eq(HisDischargeApplyDO::getStatus, 1)
                .orderByAsc(HisDischargeApplyDO::getApplyTime));
    }

    /**
     * 根据出院日期范围查询
     */
    default List<HisDischargeApplyDO> selectByDischargeDateBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .between(HisDischargeApplyDO::getDischargeDate, startTime, endTime));
    }

    /**
     * 统计今日出院申请数量
     */
    default int countTodayApply() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return selectCount(new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .between(HisDischargeApplyDO::getApplyTime, startOfDay, endOfDay)).intValue();
    }

    /**
     * 统计待审批数量
     */
    default int countPending() {
        return selectCount(new LambdaQueryWrapperX<HisDischargeApplyDO>()
                .eq(HisDischargeApplyDO::getStatus, 1)).intValue();
    }

}
