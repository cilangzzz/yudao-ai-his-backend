package cn.iocoder.yudao.module.his.dal.mysql.medication;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.medication.vo.HisMedicationAdminPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.medication.HisMedicationAdministrationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 给药记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisMedicationAdministrationMapper extends BaseMapperX<HisMedicationAdministrationDO> {

    /**
     * 分页查询给药记录
     */
    default PageResult<HisMedicationAdministrationDO> selectPage(HisMedicationAdminPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eqIfPresent(HisMedicationAdministrationDO::getPatientId, reqVO.getPatientId())
                .likeIfPresent(HisMedicationAdministrationDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisMedicationAdministrationDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisMedicationAdministrationDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(HisMedicationAdministrationDO::getDrugId, reqVO.getDrugId())
                .likeIfPresent(HisMedicationAdministrationDO::getDrugName, reqVO.getDrugName())
                .eqIfPresent(HisMedicationAdministrationDO::getStatus, reqVO.getStatus())
                .eqIfPresent(HisMedicationAdministrationDO::getNurseId, reqVO.getNurseId())
                .eqIfPresent(HisMedicationAdministrationDO::getCheckResult, reqVO.getCheckResult())
                .betweenIfPresent(HisMedicationAdministrationDO::getScheduledTime, reqVO.getScheduledTimeStart(), reqVO.getScheduledTimeEnd())
                .betweenIfPresent(HisMedicationAdministrationDO::getActualTime, reqVO.getActualTimeStart(), reqVO.getActualTimeEnd())
                .orderByDesc(HisMedicationAdministrationDO::getId));
    }

    /**
     * 根据住院ID查询给药记录列表
     */
    default List<HisMedicationAdministrationDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eq(HisMedicationAdministrationDO::getAdmissionId, admissionId)
                .orderByAsc(HisMedicationAdministrationDO::getScheduledTime));
    }

    /**
     * 根据医嘱ID查询给药记录列表
     */
    default List<HisMedicationAdministrationDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eq(HisMedicationAdministrationDO::getOrderId, orderId)
                .orderByAsc(HisMedicationAdministrationDO::getScheduledTime));
    }

    /**
     * 查询待执行的给药记录列表
     */
    default List<HisMedicationAdministrationDO> selectPendingList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eqIfPresent(HisMedicationAdministrationDO::getAdmissionId, admissionId)
                .eq(HisMedicationAdministrationDO::getStatus, 1) // 待执行
                .orderByAsc(HisMedicationAdministrationDO::getScheduledTime));
    }

    /**
     * 查询指定时间段内的待执行给药记录
     */
    default List<HisMedicationAdministrationDO> selectScheduledList(@Param("admissionId") Long admissionId,
                                                                    @Param("startTime") LocalDateTime startTime,
                                                                    @Param("endTime") LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eqIfPresent(HisMedicationAdministrationDO::getAdmissionId, admissionId)
                .ge(HisMedicationAdministrationDO::getScheduledTime, startTime)
                .lt(HisMedicationAdministrationDO::getScheduledTime, endTime)
                .eq(HisMedicationAdministrationDO::getStatus, 1) // 待执行
                .orderByAsc(HisMedicationAdministrationDO::getScheduledTime));
    }

    /**
     * 根据患者ID查询给药记录列表
     */
    default List<HisMedicationAdministrationDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eq(HisMedicationAdministrationDO::getPatientId, patientId)
                .orderByDesc(HisMedicationAdministrationDO::getScheduledTime));
    }

    /**
     * 查询今日给药记录
     */
    default List<HisMedicationAdministrationDO> selectTodayList(Long admissionId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eqIfPresent(HisMedicationAdministrationDO::getAdmissionId, admissionId)
                .ge(HisMedicationAdministrationDO::getScheduledTime, startOfDay)
                .lt(HisMedicationAdministrationDO::getScheduledTime, endOfDay)
                .orderByAsc(HisMedicationAdministrationDO::getScheduledTime));
    }

    /**
     * 统计未执行的给药记录数量
     */
    default Long countNotExecuted(Long admissionId) {
        return selectCount(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eq(HisMedicationAdministrationDO::getAdmissionId, admissionId)
                .eq(HisMedicationAdministrationDO::getStatus, 3)); // 未执行
    }

    /**
     * 统计延迟执行的给药记录数量
     */
    default Long countDelayed(Long admissionId) {
        return selectCount(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eq(HisMedicationAdministrationDO::getAdmissionId, admissionId)
                .eq(HisMedicationAdministrationDO::getStatus, 4)); // 延迟执行
    }

    /**
     * 统计患者拒绝的给药记录数量
     */
    default Long countRefused(Long admissionId) {
        return selectCount(new LambdaQueryWrapperX<HisMedicationAdministrationDO>()
                .eq(HisMedicationAdministrationDO::getAdmissionId, admissionId)
                .eq(HisMedicationAdministrationDO::getStatus, 5)); // 患者拒绝
    }

}
