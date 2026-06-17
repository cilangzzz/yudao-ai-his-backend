package cn.iocoder.yudao.module.his.dal.mysql.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 门诊费用 Mapper
 */
@Mapper
public interface OpFeeMapper extends BaseMapperX<OpFeeDO> {

    /**
     * 根据费用单号查询
     */
    default OpFeeDO selectByFeeNo(String feeNo) {
        return selectOne(OpFeeDO::getFeeNo, feeNo);
    }

    /**
     * 根据挂号ID查询费用
     */
    default OpFeeDO selectByRegisterId(Long registerId) {
        return selectOne(OpFeeDO::getRegisterId, registerId);
    }

    /**
     * 根据患者ID查询费用列表
     */
    default List<OpFeeDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getPatientId, patientId)
                .orderByDesc(OpFeeDO::getCreateTime));
    }

    /**
     * 根据科室ID查询费用列表
     */
    default List<OpFeeDO> selectListByDeptId(Long deptId) {
        return selectList(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getDeptId, deptId)
                .orderByDesc(OpFeeDO::getCreateTime));
    }

    /**
     * 根据费用状态查询费用列表
     */
    default List<OpFeeDO> selectListByStatus(Integer feeStatus) {
        return selectList(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getFeeStatus, feeStatus)
                .orderByDesc(OpFeeDO::getCreateTime));
    }

    /**
     * 查询患者的未收费费用
     */
    default List<OpFeeDO> selectUnpaidByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getPatientId, patientId)
                .eq(OpFeeDO::getFeeStatus, 0) // 未收费
                .orderByDesc(OpFeeDO::getCreateTime));
    }

    /**
     * 查询患者的已收费费用
     */
    default List<OpFeeDO> selectPaidByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getPatientId, patientId)
                .eq(OpFeeDO::getFeeStatus, 1) // 已收费
                .orderByDesc(OpFeeDO::getCreateTime));
    }

    /**
     * 查询某日期范围内的费用列表
     */
    default List<OpFeeDO> selectListByDateRange(LocalDate startDate, LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<OpFeeDO>()
                .between(OpFeeDO::getCreateTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())
                .orderByDesc(OpFeeDO::getCreateTime));
    }

    /**
     * 根据科室ID和日期范围查询费用列表
     */
    default List<OpFeeDO> selectListByDeptIdAndDateRange(Long deptId, LocalDate startDate, LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getDeptId, deptId)
                .between(OpFeeDO::getCreateTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())
                .orderByDesc(OpFeeDO::getCreateTime));
    }

    /**
     * 统计某患者的费用总金额
     */
    default Long selectCountByPatientId(Long patientId) {
        return selectCount(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getPatientId, patientId));
    }

    /**
     * 统计某状态的费用数量
     */
    default Long selectCountByStatus(Integer feeStatus) {
        return selectCount(new LambdaQueryWrapperX<OpFeeDO>()
                .eq(OpFeeDO::getFeeStatus, feeStatus));
    }

}
