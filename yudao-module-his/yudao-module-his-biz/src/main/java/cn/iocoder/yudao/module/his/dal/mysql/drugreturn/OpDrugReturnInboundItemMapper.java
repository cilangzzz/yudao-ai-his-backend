package cn.iocoder.yudao.module.his.dal.mysql.drugreturn;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnInboundItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 退药入库明细 Mapper
 */
@Mapper
public interface OpDrugReturnInboundItemMapper extends BaseMapperX<OpDrugReturnInboundItemDO> {

    /**
     * 根据入库ID查询明细
     */
    default List<OpDrugReturnInboundItemDO> selectListByInboundId(Long inboundId) {
        return selectList(new LambdaQueryWrapperX<OpDrugReturnInboundItemDO>()
                .eq(OpDrugReturnInboundItemDO::getInboundId, inboundId));
    }

    /**
     * 根据入库单号查询明细
     */
    default List<OpDrugReturnInboundItemDO> selectListByInboundNo(String inboundNo) {
        return selectList(OpDrugReturnInboundItemDO::getInboundNo, inboundNo);
    }

    /**
     * 根据药品ID查询入库明细
     */
    default List<OpDrugReturnInboundItemDO> selectListByDrugId(Long drugId) {
        return selectList(OpDrugReturnInboundItemDO::getDrugId, drugId);
    }

    /**
     * 批量插入明细
     */
    default void insertBatch(List<OpDrugReturnInboundItemDO> items) {
        if (items != null && !items.isEmpty()) {
            items.forEach(this::insert);
        }
    }

}
