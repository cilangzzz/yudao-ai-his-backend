package cn.iocoder.yudao.module.his.dal.mysql.drugreturn;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 退药明细 Mapper
 */
@Mapper
public interface OpDrugReturnItemMapper extends BaseMapperX<OpDrugReturnItemDO> {

    /**
     * 根据退药ID查询明细列表
     */
    default List<OpDrugReturnItemDO> selectListByReturnId(Long returnId) {
        return selectList(new LambdaQueryWrapperX<OpDrugReturnItemDO>()
                .eq(OpDrugReturnItemDO::getReturnId, returnId)
                .orderByAsc(OpDrugReturnItemDO::getSortOrder));
    }

    /**
     * 根据退药单号查询明细列表
     */
    default List<OpDrugReturnItemDO> selectListByReturnNo(String returnNo) {
        return selectList(new LambdaQueryWrapperX<OpDrugReturnItemDO>()
                .eq(OpDrugReturnItemDO::getReturnNo, returnNo)
                .orderByAsc(OpDrugReturnItemDO::getSortOrder));
    }

    /**
     * 根据药品ID查询退药明细
     */
    default List<OpDrugReturnItemDO> selectListByDrugId(Long drugId) {
        return selectList(OpDrugReturnItemDO::getDrugId, drugId);
    }

    /**
     * 根据退药ID和状态查询
     */
    default List<OpDrugReturnItemDO> selectListByReturnIdAndStatus(Long returnId, Integer status) {
        return selectList(new LambdaQueryWrapperX<OpDrugReturnItemDO>()
                .eq(OpDrugReturnItemDO::getReturnId, returnId)
                .eq(OpDrugReturnItemDO::getItemStatus, status)
                .orderByAsc(OpDrugReturnItemDO::getSortOrder));
    }

    /**
     * 根据批号查询退药明细
     */
    default List<OpDrugReturnItemDO> selectListByBatchNo(String batchNo) {
        return selectList(OpDrugReturnItemDO::getBatchNo, batchNo);
    }

    /**
     * 根据原处方明细ID查询
     */
    default List<OpDrugReturnItemDO> selectListByPrescriptionItemId(Long prescriptionItemId) {
        return selectList(OpDrugReturnItemDO::getPrescriptionItemId, prescriptionItemId);
    }

    /**
     * 根据原发药明细ID查询
     */
    default List<OpDrugReturnItemDO> selectListByDispenseItemId(Long dispenseItemId) {
        return selectList(OpDrugReturnItemDO::getDispenseItemId, dispenseItemId);
    }

    /**
     * 批量插入明细
     */
    default void insertBatch(List<OpDrugReturnItemDO> items) {
        if (items != null && !items.isEmpty()) {
            items.forEach(this::insert);
        }
    }

    /**
     * 根据退药ID删除明细
     */
    default int deleteByReturnId(Long returnId) {
        return delete(OpDrugReturnItemDO::getReturnId, returnId);
    }

    /**
     * 统计退药明细数量
     */
    default Long countByReturnId(Long returnId) {
        return selectCount(OpDrugReturnItemDO::getReturnId, returnId);
    }

}
