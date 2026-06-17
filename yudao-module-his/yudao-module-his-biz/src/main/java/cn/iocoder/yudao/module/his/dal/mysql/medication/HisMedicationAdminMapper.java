package cn.iocoder.yudao.module.his.dal.mysql.medication;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.medication.HisMedicationAdminDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 给药记录 Mapper
 *
 * 用于eMAR闭环给药管理
 */
@Mapper
public interface HisMedicationAdminMapper extends BaseMapperX<HisMedicationAdminDO> {

    /**
     * 分页查询给药记录
     */
    default PageResult<HisMedicationAdminDO> selectPage(
            cn.iocoder.yudao.module.his.controller.admin.medication.vo.HisMedicationAdminPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisMedicationAdminDO>()
                .eqIfPresent(HisMedicationAdminDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisMedicationAdminDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisMedicationAdminDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(HisMedicationAdminDO::getNurseId, reqVO.getNurseId())
                .eqIfPresent(HisMedicationAdminDO::getStatus, reqVO.getStatus())
                .eqIfPresent(HisMedicationAdminDO::getDrugId, reqVO.getDrugId())
                .betweenIfPresent(HisMedicationAdminDO::getScheduledTime, reqVO.getScheduledTime())
                .orderByDesc(HisMedicationAdminDO::getId));
    }

    /**
     * 按住院ID查询给药记录列表
     */
    default List<HisMedicationAdminDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdminDO>()
                .eq(HisMedicationAdminDO::getAdmissionId, admissionId)
                .orderByAsc(HisMedicationAdminDO::getScheduledTime));
    }

    /**
     * 按医嘱ID查询给药记录列表
     */
    default List<HisMedicationAdminDO> selectListByOrderId(Long orderId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdminDO>()
                .eq(HisMedicationAdminDO::getOrderId, orderId)
                .orderByAsc(HisMedicationAdminDO::getScheduledTime));
    }

    /**
     * 按患者ID查询给药记录列表
     */
    default List<HisMedicationAdminDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdminDO>()
                .eq(HisMedicationAdminDO::getPatientId, patientId)
                .orderByDesc(HisMedicationAdminDO::getScheduledTime));
    }

    /**
     * 查询待执行的给药记录
     */
    default List<HisMedicationAdminDO> selectPendingList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdminDO>()
                .eq(HisMedicationAdminDO::getAdmissionId, admissionId)
                .eq(HisMedicationAdminDO::getStatus, 1) // 待执行
                .orderByAsc(HisMedicationAdminDO::getScheduledTime));
    }

    /**
     * 查询指定时间范围内的给药记录
     */
    default List<HisMedicationAdminDO> selectListByTimeRange(
            Long admissionId, LocalDateTime startTime, LocalDateTime endTime) {
        return selectList(new LambdaQueryWrapperX<HisMedicationAdminDO>()
                .eq(HisMedicationAdminDO::getAdmissionId, admissionId)
                .between(HisMedicationAdminDO::getScheduledTime, startTime, endTime)
                .orderByAsc(HisMedicationAdminDO::getScheduledTime));
    }

    /**
     * 查询护士执行统计
     */
    List<HisMedicationAdminDO> selectByNurseAndTime(
            @Param("nurseId") Long nurseId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}