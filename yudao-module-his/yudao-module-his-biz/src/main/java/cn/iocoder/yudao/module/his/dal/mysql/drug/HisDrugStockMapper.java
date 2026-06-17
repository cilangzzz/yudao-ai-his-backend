package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugStockPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugStockDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 药品库存 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugStockMapper extends BaseMapperX<HisDrugStockDO> {

    /**
     * 分页查询
     */
    default PageResult<HisDrugStockDO> selectPage(HisDrugStockPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDrugStockDO>()
                .eqIfPresent(HisDrugStockDO::getDrugId, reqVO.getDrugId())
                .likeIfPresent(HisDrugStockDO::getDrugCode, reqVO.getDrugCode())
                .likeIfPresent(HisDrugStockDO::getDrugName, reqVO.getDrugName())
                .eqIfPresent(HisDrugStockDO::getBatchNo, reqVO.getBatchNo())
                .eqIfPresent(HisDrugStockDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(HisDrugStockDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisDrugStockDO::getExpiryDate, reqVO.getExpiryDateStart(), reqVO.getExpiryDateEnd())
                .betweenIfPresent(HisDrugStockDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(HisDrugStockDO::getExpiryDate)
                .orderByDesc(HisDrugStockDO::getId));
    }

    /**
     * 根据药品ID查询库存列表
     */
    default List<HisDrugStockDO> selectListByDrugId(Long drugId) {
        return selectList(new LambdaQueryWrapperX<HisDrugStockDO>()
                .eq(HisDrugStockDO::getDrugId, drugId)
                .ne(HisDrugStockDO::getStatus, 3) // 排除过期库存
                .orderByAsc(HisDrugStockDO::getExpiryDate));
    }

    /**
     * 根据药品ID和批号查询库存
     */
    default HisDrugStockDO selectByDrugIdAndBatchNo(Long drugId, String batchNo) {
        return selectOne(new LambdaQueryWrapperX<HisDrugStockDO>()
                .eq(HisDrugStockDO::getDrugId, drugId)
                .eq(HisDrugStockDO::getBatchNo, batchNo));
    }

    /**
     * 根据仓库ID查询库存列表
     */
    default List<HisDrugStockDO> selectListByWarehouseId(Long warehouseId) {
        return selectList(HisDrugStockDO::getWarehouseId, warehouseId);
    }

    /**
     * 查询近效期库存列表（有效期在指定日期之前）
     */
    default List<HisDrugStockDO> selectNearExpiryList(LocalDate warningDate) {
        return selectList(new LambdaQueryWrapperX<HisDrugStockDO>()
                .lt(HisDrugStockDO::getExpiryDate, warningDate)
                .gt(HisDrugStockDO::getExpiryDate, LocalDate.now())
                .eq(HisDrugStockDO::getStatus, 1)
                .orderByAsc(HisDrugStockDO::getExpiryDate));
    }

    /**
     * 查询过期库存列表
     */
    default List<HisDrugStockDO> selectExpiredList() {
        return selectList(new LambdaQueryWrapperX<HisDrugStockDO>()
                .lt(HisDrugStockDO::getExpiryDate, LocalDate.now())
                .orderByAsc(HisDrugStockDO::getExpiryDate));
    }

    /**
     * 查询低库存列表（库存数量低于指定阈值）
     */
    default List<HisDrugStockDO> selectLowStockList(BigDecimal threshold) {
        return selectList(new LambdaQueryWrapperX<HisDrugStockDO>()
                .lt(HisDrugStockDO::getQuantity, threshold)
                .ne(HisDrugStockDO::getStatus, 3)
                .orderByDesc(HisDrugStockDO::getQuantity));
    }

    /**
     * 根据状态查询库存列表
     */
    default List<HisDrugStockDO> selectListByStatus(Integer status) {
        return selectList(HisDrugStockDO::getStatus, status);
    }

    /**
     * 查询药品总库存数量
     */
    default BigDecimal selectTotalQuantityByDrugId(Long drugId) {
        List<HisDrugStockDO> list = selectList(new LambdaQueryWrapperX<HisDrugStockDO>()
                .eq(HisDrugStockDO::getDrugId, drugId)
                .ne(HisDrugStockDO::getStatus, 3));
        return list.stream()
                .map(HisDrugStockDO::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 批量插入库存
     */
    default void insertBatch(List<HisDrugStockDO> list) {
        list.forEach(this::insert);
    }

}