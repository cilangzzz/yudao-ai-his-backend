package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 采购明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugPurchaseItemMapper extends BaseMapperX<HisDrugPurchaseItemDO> {

    /**
     * 根据采购ID查询明细列表
     */
    default List<HisDrugPurchaseItemDO> selectListByPurchaseId(Long purchaseId) {
        return selectList(HisDrugPurchaseItemDO::getPurchaseId, purchaseId);
    }

    /**
     * 根据采购ID删除明细
     */
    default void deleteByPurchaseId(Long purchaseId) {
        delete(HisDrugPurchaseItemDO::getPurchaseId, purchaseId);
    }

    /**
     * 批量插入
     */
    default void insertBatch(List<HisDrugPurchaseItemDO> list) {
        list.forEach(this::insert);
    }

}