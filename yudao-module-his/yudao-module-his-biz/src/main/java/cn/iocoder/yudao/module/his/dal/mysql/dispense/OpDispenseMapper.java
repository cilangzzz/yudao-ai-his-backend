package cn.iocoder.yudao.module.his.dal.mysql.dispense;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispensePageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 发药记录主表 Mapper
 */
@Mapper
public interface OpDispenseMapper extends BaseMapperX<OpDispenseDO> {

    /**
     * 分页查询发药记录
     */
    default PageResult<OpDispenseDO> selectPage(OpDispensePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpDispenseDO>()
                .eqIfPresent(OpDispenseDO::getDispenseNo, reqVO.getDispenseNo())
                .eqIfPresent(OpDispenseDO::getPrescriptionId, reqVO.getPrescriptionId())
                .eqIfPresent(OpDispenseDO::getRegisterId, reqVO.getRegisterId())
                .eqIfPresent(OpDispenseDO::getPatientId, reqVO.getPatientId())
                .likeIfPresent(OpDispenseDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(OpDispenseDO::getPharmacyId, reqVO.getPharmacyId())
                .eqIfPresent(OpDispenseDO::getDispenseStatus, reqVO.getDispenseStatus())
                .betweenIfPresent(OpDispenseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OpDispenseDO::getId));
    }

    /**
     * 根据发药单号查询
     */
    default OpDispenseDO selectByDispenseNo(String dispenseNo) {
        return selectOne(OpDispenseDO::getDispenseNo, dispenseNo);
    }

    /**
     * 根据处方ID查询
     */
    default OpDispenseDO selectByPrescriptionId(Long prescriptionId) {
        return selectOne(OpDispenseDO::getPrescriptionId, prescriptionId);
    }

    /**
     * 根据挂号ID查询发药记录列表
     */
    default List<OpDispenseDO> selectListByRegisterId(Long registerId) {
        return selectList(OpDispenseDO::getRegisterId, registerId);
    }

    /**
     * 根据患者ID查询发药记录列表
     */
    default List<OpDispenseDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getPatientId, patientId)
                .orderByDesc(OpDispenseDO::getCreateTime));
    }

    /**
     * 根据药房ID查询发药记录列表
     */
    default List<OpDispenseDO> selectListByPharmacyId(Long pharmacyId) {
        return selectList(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getPharmacyId, pharmacyId)
                .orderByDesc(OpDispenseDO::getCreateTime));
    }

    /**
     * 根据状态查询发药记录列表
     */
    default List<OpDispenseDO> selectListByStatus(Integer dispenseStatus) {
        return selectList(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getDispenseStatus, dispenseStatus)
                .orderByAsc(OpDispenseDO::getCreateTime));
    }

    /**
     * 查询待调配的发药记录列表
     */
    default List<OpDispenseDO> selectPendingDispenseList(Long pharmacyId) {
        return selectList(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getDispenseStatus, 1) // 待调配状态
                .eqIfPresent(OpDispenseDO::getPharmacyId, pharmacyId)
                .orderByAsc(OpDispenseDO::getCreateTime));
    }

    /**
     * 查询待发药的记录列表（已调配）
     */
    default List<OpDispenseDO> selectPendingSendList(Long pharmacyId) {
        return selectList(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getDispenseStatus, 2) // 已调配状态
                .eqIfPresent(OpDispenseDO::getPharmacyId, pharmacyId)
                .orderByAsc(OpDispenseDO::getDispenseTime));
    }

    /**
     * 根据药房ID和日期查询发药记录列表
     */
    default List<OpDispenseDO> selectListByPharmacyIdAndDate(Long pharmacyId, LocalDate date) {
        return selectList(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getPharmacyId, pharmacyId)
                .between(OpDispenseDO::getCreateTime, date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .orderByDesc(OpDispenseDO::getCreateTime));
    }

    /**
     * 统计某药房某状态的数量
     */
    default Long selectCountByPharmacyIdAndStatus(Long pharmacyId, Integer dispenseStatus) {
        return selectCount(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getPharmacyId, pharmacyId)
                .eq(OpDispenseDO::getDispenseStatus, dispenseStatus));
    }

    /**
     * 统计某患者的发药记录数量
     */
    default Long selectCountByPatientId(Long patientId) {
        return selectCount(new LambdaQueryWrapperX<OpDispenseDO>()
                .eq(OpDispenseDO::getPatientId, patientId)
                .ne(OpDispenseDO::getDispenseStatus, 4)); // 排除已退药
    }

}
