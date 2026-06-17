package cn.iocoder.yudao.module.his.dal.mysql.lab;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 检验申请明细 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisLabRequestItemMapper extends BaseMapperX<HisLabRequestItemDO> {

    /**
     * 根据申请ID查询明细列表
     */
    default List<HisLabRequestItemDO> selectListByRequestId(Long requestId) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestItemDO>()
                .eq(HisLabRequestItemDO::getRequestId, requestId)
                .orderByAsc(HisLabRequestItemDO::getSortOrder));
    }

    /**
     * 根据申请ID删除明细
     */
    default void deleteByRequestId(Long requestId) {
        delete(new LambdaQueryWrapperX<HisLabRequestItemDO>()
                .eq(HisLabRequestItemDO::getRequestId, requestId));
    }

    /**
     * 根据申请ID列表查询明细
     */
    default List<HisLabRequestItemDO> selectListByRequestIds(List<Long> requestIds) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestItemDO>()
                .in(HisLabRequestItemDO::getRequestId, requestIds)
                .orderByAsc(HisLabRequestItemDO::getSortOrder));
    }

    /**
     * 根据项目编码查询
     */
    default HisLabRequestItemDO selectByItemCode(Long requestId, String itemCode) {
        return selectOne(new LambdaQueryWrapperX<HisLabRequestItemDO>()
                .eq(HisLabRequestItemDO::getRequestId, requestId)
                .eq(HisLabRequestItemDO::getItemCode, itemCode));
    }

    /**
     * 根据项目状态查询明细列表
     */
    default List<HisLabRequestItemDO> selectListByStatus(Long requestId, Integer itemStatus) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestItemDO>()
                .eq(HisLabRequestItemDO::getRequestId, requestId)
                .eq(HisLabRequestItemDO::getItemStatus, itemStatus)
                .orderByAsc(HisLabRequestItemDO::getSortOrder));
    }

    /**
     * 查询有异常结果的明细列表
     */
    default List<HisLabRequestItemDO> selectAbnormalList(Long requestId) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestItemDO>()
                .eq(HisLabRequestItemDO::getRequestId, requestId)
                .ne(HisLabRequestItemDO::getAbnormalFlag, 0) // 异常
                .orderByAsc(HisLabRequestItemDO::getSortOrder));
    }

    /**
     * 查询有危急值的明细列表
     */
    default List<HisLabRequestItemDO> selectCriticalList(Long requestId) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestItemDO>()
                .eq(HisLabRequestItemDO::getRequestId, requestId)
                .eq(HisLabRequestItemDO::getCriticalFlag, 1) // 危急值
                .orderByAsc(HisLabRequestItemDO::getSortOrder));
    }

    /**
     * 统计申请的项目数量
     */
    default Long selectCountByRequestId(Long requestId) {
        return selectCount(HisLabRequestItemDO::getRequestId, requestId);
    }

}