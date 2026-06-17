package cn.iocoder.yudao.module.his.dal.mysql.prescription;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 处方主表 Mapper
 */
@Mapper
public interface OpPrescriptionMapper extends BaseMapperX<OpPrescriptionDO> {

    /**
     * 分页查询处方
     */
    default PageResult<OpPrescriptionDO> selectPage(cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eqIfPresent(OpPrescriptionDO::getPrescriptionNo, reqVO.getPrescriptionNo())
                .eqIfPresent(OpPrescriptionDO::getPatientId, reqVO.getPatientId())
                .likeIfPresent(OpPrescriptionDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(OpPrescriptionDO::getRegisterId, reqVO.getRegisterId())
                .eqIfPresent(OpPrescriptionDO::getPrescriptionType, reqVO.getPrescriptionType())
                .eqIfPresent(OpPrescriptionDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(OpPrescriptionDO::getDoctorId, reqVO.getDoctorId())
                .eqIfPresent(OpPrescriptionDO::getPrescriptionStatus, reqVO.getPrescriptionStatus())
                .betweenIfPresent(OpPrescriptionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OpPrescriptionDO::getId));
    }

    /**
     * 根据处方编号查询
     */
    default OpPrescriptionDO selectByPrescriptionNo(String prescriptionNo) {
        return selectOne(OpPrescriptionDO::getPrescriptionNo, prescriptionNo);
    }

    /**
     * 根据挂号ID查询处方列表
     */
    default List<OpPrescriptionDO> selectListByRegisterId(Long registerId) {
        return selectList(OpPrescriptionDO::getRegisterId, registerId);
    }

    /**
     * 根据患者ID查询处方列表
     */
    default List<OpPrescriptionDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getPatientId, patientId)
                .orderByDesc(OpPrescriptionDO::getCreateTime));
    }

    /**
     * 根据医生ID查询处方列表
     */
    default List<OpPrescriptionDO> selectListByDoctorId(Long doctorId) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getDoctorId, doctorId)
                .orderByDesc(OpPrescriptionDO::getCreateTime));
    }

    /**
     * 根据科室ID查询处方列表
     */
    default List<OpPrescriptionDO> selectListByDeptId(Long deptId) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getDeptId, deptId)
                .orderByDesc(OpPrescriptionDO::getCreateTime));
    }

    /**
     * 根据状态查询处方列表
     */
    default List<OpPrescriptionDO> selectListByStatus(Integer prescriptionStatus) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getPrescriptionStatus, prescriptionStatus)
                .orderByDesc(OpPrescriptionDO::getCreateTime));
    }

    /**
     * 查询待审核的处方列表
     */
    default List<OpPrescriptionDO> selectPendingAuditList() {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getPrescriptionStatus, 1) // 开立状态
                .orderByAsc(OpPrescriptionDO::getCreateTime));
    }

    /**
     * 查询待调配的处方列表（审核通过）
     */
    default List<OpPrescriptionDO> selectPendingDispenseList() {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getPrescriptionStatus, 2) // 审核通过状态
                .orderByAsc(OpPrescriptionDO::getAuditTime));
    }

    /**
     * 查询待发药的处方列表（已调配）
     */
    default List<OpPrescriptionDO> selectPendingSendList() {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getPrescriptionStatus, 4) // 已调配状态
                .orderByAsc(OpPrescriptionDO::getDispenseTime));
    }

    /**
     * 根据医生ID和日期查询处方列表
     */
    default List<OpPrescriptionDO> selectListByDoctorIdAndDate(Long doctorId, LocalDate date) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getDoctorId, doctorId)
                .between(OpPrescriptionDO::getCreateTime, date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .orderByDesc(OpPrescriptionDO::getCreateTime));
    }

    /**
     * 统计某患者的有效处方数量
     */
    default Long selectCountByPatientId(Long patientId) {
        return selectCount(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getPatientId, patientId)
                .ne(OpPrescriptionDO::getPrescriptionStatus, 6)); // 排除已退药
    }

    /**
     * 根据处方类型统计数量
     */
    default Long selectCountByType(Integer prescriptionType) {
        return selectCount(new LambdaQueryWrapperX<OpPrescriptionDO>()
                .eq(OpPrescriptionDO::getPrescriptionType, prescriptionType));
    }

}
