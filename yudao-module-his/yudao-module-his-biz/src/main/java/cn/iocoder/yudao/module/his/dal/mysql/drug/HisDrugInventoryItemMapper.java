package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 盘点明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugInventoryItemMapper extends BaseMapperX<HisDrugInventoryItemDO> {

    /**
     * 根据盘点ID查询明细列表
     */
    default List<HisDrugInventoryItemDO> selectListByInventoryId(Long inventoryId) {
        return selectList(HisDrugInventoryItemDO::getInventoryId, inventoryId);
    }

    /**
     * 根据盘点ID删除明细
     */
    default void deleteByInventoryId(Long inventoryId) {
        delete(HisDrugInventoryItemDO::getInventoryId, inventoryId);
    }

    /**
     * 根据盘点ID和状态查询明细列表
     */
    default List<HisDrugInventoryItemDO> selectListByInventoryIdAndStatus(Long inventoryId, Integer status) {
        return selectList(new LambdaQueryWrapperX<HisDrugInventoryItemDO>()
                .eq(HisDrugInventoryItemDO::getInventoryId, inventoryId)
                .eq(HisDrugInventoryItemDO::getInventoryItemStatus, status));
    }

    /**
     * 查询待盘点明细列表
     */
    default List<HisDrugInventoryItemDO> selectPendingList(Long inventoryId) {
        return selectListByInventoryIdAndStatus(inventoryId, 1);
    }

    /**
     * 查询已盘点明细列表
     */
    default List<HisDrugInventoryItemDO> selectCompletedList(Long inventoryId) {
        return selectListByInventoryIdAndStatus(inventoryId, 2);
    }

    /**
     * 批量插入
     */
    default void insertBatch(List<HisDrugInventoryItemDO> list) {
        list.forEach(this::insert);
    }

}