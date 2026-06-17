package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出库明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugOutboundItemMapper extends BaseMapperX<HisDrugOutboundItemDO> {

    /**
     * 根据出库ID查询明细列表
     */
    default List<HisDrugOutboundItemDO> selectListByOutboundId(Long outboundId) {
        return selectList(HisDrugOutboundItemDO::getOutboundId, outboundId);
    }

    /**
     * 根据出库ID删除明细
     */
    default void deleteByOutboundId(Long outboundId) {
        delete(HisDrugOutboundItemDO::getOutboundId, outboundId);
    }

    /**
     * 根据药品ID查询出库明细列表
     */
    default List<HisDrugOutboundItemDO> selectListByDrugId(Long drugId) {
        return selectList(HisDrugOutboundItemDO::getDrugId, drugId);
    }

    /**
     * 批量插入
     */
    default void insertBatch(List<HisDrugOutboundItemDO> list) {
        list.forEach(this::insert);
    }

}