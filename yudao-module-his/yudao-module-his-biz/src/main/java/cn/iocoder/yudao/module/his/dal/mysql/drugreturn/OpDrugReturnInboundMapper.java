package cn.iocoder.yudao.module.his.dal.mysql.drugreturn;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnInboundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnInboundItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 退药入库记录 Mapper
 */
@Mapper
public interface OpDrugReturnInboundMapper extends BaseMapperX<OpDrugReturnInboundDO> {

    /**
     * 根据入库单号查询
     */
    default OpDrugReturnInboundDO selectByInboundNo(String inboundNo) {
        return selectOne(OpDrugReturnInboundDO::getInboundNo, inboundNo);
    }

    /**
     * 根据退药ID查询入库记录
     */
    default OpDrugReturnInboundDO selectByReturnId(Long returnId) {
        return selectOne(OpDrugReturnInboundDO::getReturnId, returnId);
    }

    /**
     * 根据仓库ID查询入库记录
     */
    default List<OpDrugReturnInboundDO> selectListByWarehouseId(Long warehouseId) {
        return selectList(OpDrugReturnInboundDO::getWarehouseId, warehouseId);
    }

    /**
     * 根据状态查询入库记录
     */
    default List<OpDrugReturnInboundDO> selectListByStatus(Integer status) {
        return selectList(OpDrugReturnInboundDO::getAuditStatus, status);
    }

}