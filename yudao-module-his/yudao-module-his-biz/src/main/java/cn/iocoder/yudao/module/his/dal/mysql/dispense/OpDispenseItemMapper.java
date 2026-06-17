package cn.iocoder.yudao.module.his.dal.mysql.dispense;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 发药明细 Mapper
 */
@Mapper
public interface OpDispenseItemMapper extends BaseMapperX<OpDispenseItemDO> {

    /**
     * 根据发药ID查询明细列表
     */
    default List<OpDispenseItemDO> selectListByDispenseId(Long dispenseId) {
        return selectList(new LambdaQueryWrapperX<OpDispenseItemDO>()
                .eq(OpDispenseItemDO::getDispenseId, dispenseId)
                .orderByAsc(OpDispenseItemDO::getId));
    }

    /**
     * 根据处方明细ID查询
     */
    default OpDispenseItemDO selectByPrescriptionItemId(Long prescriptionItemId) {
        return selectOne(OpDispenseItemDO::getPrescriptionItemId, prescriptionItemId);
    }

    /**
     * 根据药品ID查询发药明细列表
     */
    default List<OpDispenseItemDO> selectListByDrugId(Long drugId) {
        return selectList(new LambdaQueryWrapperX<OpDispenseItemDO>()
                .eq(OpDispenseItemDO::getDrugId, drugId)
                .orderByDesc(OpDispenseItemDO::getCreateTime));
    }

    /**
     * 根据批号查询发药明细列表
     */
    default List<OpDispenseItemDO> selectListByBatchNo(String batchNo) {
        return selectList(new LambdaQueryWrapperX<OpDispenseItemDO>()
                .eq(OpDispenseItemDO::getBatchNo, batchNo)
                .orderByDesc(OpDispenseItemDO::getCreateTime));
    }

    /**
     * 根据发药ID删除明细
     */
    default void deleteByDispenseId(Long dispenseId) {
        delete(new LambdaQueryWrapperX<OpDispenseItemDO>()
                .eq(OpDispenseItemDO::getDispenseId, dispenseId));
    }

    /**
     * 批量插入明细
     */
    default void insertBatch(List<OpDispenseItemDO> items) {
        if (items != null && !items.isEmpty()) {
            for (OpDispenseItemDO item : items) {
                insert(item);
            }
        }
    }

}
