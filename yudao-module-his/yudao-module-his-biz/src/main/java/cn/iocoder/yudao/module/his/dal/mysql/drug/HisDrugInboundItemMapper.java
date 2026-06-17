package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 入库明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugInboundItemMapper extends BaseMapperX<HisDrugInboundItemDO> {

    /**
     * 根据入库ID查询明细列表
     */
    default List<HisDrugInboundItemDO> selectListByInboundId(Long inboundId) {
        return selectList(HisDrugInboundItemDO::getInboundId, inboundId);
    }

    /**
     * 根据入库ID删除明细
     */
    default void deleteByInboundId(Long inboundId) {
        delete(HisDrugInboundItemDO::getInboundId, inboundId);
    }

    /**
     * 根据药品ID查询入库明细列表
     */
    default List<HisDrugInboundItemDO> selectListByDrugId(Long drugId) {
        return selectList(HisDrugInboundItemDO::getDrugId, drugId);
    }

    /**
     * 批量插入
     */
    default void insertBatch(List<HisDrugInboundItemDO> list) {
        list.forEach(this::insert);
    }

}