package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInventoryPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 盘点记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugInventoryMapper extends BaseMapperX<HisDrugInventoryDO> {

    /**
     * 分页查询
     */
    default PageResult<HisDrugInventoryDO> selectPage(HisDrugInventoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDrugInventoryDO>()
                .likeIfPresent(HisDrugInventoryDO::getInventoryNo, reqVO.getInventoryNo())
                .eqIfPresent(HisDrugInventoryDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(HisDrugInventoryDO::getInventoryDate, reqVO.getInventoryDate())
                .eqIfPresent(HisDrugInventoryDO::getInventoryStatus, reqVO.getInventoryStatus())
                .betweenIfPresent(HisDrugInventoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisDrugInventoryDO::getId));
    }

    /**
     * 根据盘点单号查询
     */
    default HisDrugInventoryDO selectByInventoryNo(String inventoryNo) {
        return selectOne(HisDrugInventoryDO::getInventoryNo, inventoryNo);
    }

    /**
     * 根据仓库ID查询列表
     */
    default List<HisDrugInventoryDO> selectListByWarehouseId(Long warehouseId) {
        return selectList(HisDrugInventoryDO::getWarehouseId, warehouseId);
    }

    /**
     * 根据状态查询列表
     */
    default List<HisDrugInventoryDO> selectListByStatus(Integer status) {
        return selectList(HisDrugInventoryDO::getInventoryStatus, status);
    }

    /**
     * 查询进行中的盘点列表
     */
    default List<HisDrugInventoryDO> selectInProgressList() {
        return selectListByStatus(1);
    }

}