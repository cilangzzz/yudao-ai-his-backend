package cn.iocoder.yudao.module.his.dal.mysql.fee;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 门诊费用明细 Mapper
 */
@Mapper
public interface OpFeeItemMapper extends BaseMapperX<OpFeeItemDO> {

    /**
     * 根据费用ID查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByFeeId(Long feeId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getFeeId, feeId)
                .orderByAsc(OpFeeItemDO::getSortOrder));
    }

    /**
     * 根据挂号ID查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByRegisterId(Long registerId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getRegisterId, registerId)
                .orderByAsc(OpFeeItemDO::getSortOrder));
    }

    /**
     * 根据患者ID查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getPatientId, patientId)
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 根据收费项目ID查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByChargeItemId(Long chargeItemId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getChargeItemId, chargeItemId)
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 根据来源类型和来源ID查询费用明细列表
     */
    default List<OpFeeItemDO> selectListBySource(Integer sourceType, Long sourceId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getSourceType, sourceType)
                .eq(OpFeeItemDO::getSourceId, sourceId)
                .orderByAsc(OpFeeItemDO::getSortOrder));
    }

    /**
     * 根据来源类型、来源ID和来源明细ID查询费用明细
     */
    default OpFeeItemDO selectBySourceItem(Integer sourceType, Long sourceId, Long sourceItemId) {
        return selectOne(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getSourceType, sourceType)
                .eq(OpFeeItemDO::getSourceId, sourceId)
                .eq(OpFeeItemDO::getSourceItemId, sourceItemId));
    }

    /**
     * 根据费用状态查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByStatus(Integer feeItemStatus) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getFeeItemStatus, feeItemStatus)
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 查询患者的未收费费用明细
     */
    default List<OpFeeItemDO> selectUnpaidByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getPatientId, patientId)
                .eq(OpFeeItemDO::getFeeItemStatus, 0) // 未收费
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 查询患者的已收费费用明细
     */
    default List<OpFeeItemDO> selectPaidByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getPatientId, patientId)
                .eq(OpFeeItemDO::getFeeItemStatus, 1) // 已收费
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 根据执行科室ID查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByExecutionDeptId(Long executionDeptId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getExecutionDeptId, executionDeptId)
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 根据开单医生ID查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByDoctorId(Long doctorId) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getDoctorId, doctorId)
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 根据项目类别查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByItemCategory(Integer itemCategory) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getItemCategory, itemCategory)
                .orderByDesc(OpFeeItemDO::getCreateTime));
    }

    /**
     * 根据费用ID和状态查询费用明细列表
     */
    default List<OpFeeItemDO> selectListByFeeIdAndStatus(Long feeId, Integer feeItemStatus) {
        return selectList(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getFeeId, feeId)
                .eq(OpFeeItemDO::getFeeItemStatus, feeItemStatus)
                .orderByAsc(OpFeeItemDO::getSortOrder));
    }

    /**
     * 根据费用ID删除费用明细
     */
    default int deleteByFeeId(Long feeId) {
        return delete(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getFeeId, feeId));
    }

    /**
     * 根据挂号ID删除费用明细
     */
    default int deleteByRegisterId(Long registerId) {
        return delete(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getRegisterId, registerId));
    }

    /**
     * 统计某费用的明细数量
     */
    default Long selectCountByFeeId(Long feeId) {
        return selectCount(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getFeeId, feeId));
    }

    /**
     * 统计某患者的费用明细数量
     */
    default Long selectCountByPatientId(Long patientId) {
        return selectCount(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getPatientId, patientId));
    }

    /**
     * 统计某状态的费用明细数量
     */
    default Long selectCountByStatus(Integer feeItemStatus) {
        return selectCount(new LambdaQueryWrapperX<OpFeeItemDO>()
                .eq(OpFeeItemDO::getFeeItemStatus, feeItemStatus));
    }

}
