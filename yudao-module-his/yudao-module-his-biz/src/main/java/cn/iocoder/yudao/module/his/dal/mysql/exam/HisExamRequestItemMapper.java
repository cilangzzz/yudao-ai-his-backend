package cn.iocoder.yudao.module.his.dal.mysql.exam;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 检查申请明细 Mapper
 */
@Mapper
public interface HisExamRequestItemMapper extends BaseMapperX<HisExamRequestItemDO> {

    /**
     * 根据申请ID查询明细列表
     */
    default List<HisExamRequestItemDO> selectListByRequestId(Long requestId) {
        return selectList(new LambdaQueryWrapperX<HisExamRequestItemDO>()
                .eq(HisExamRequestItemDO::getRequestId, requestId)
                .orderByAsc(HisExamRequestItemDO::getSortOrder));
    }

    /**
     * 根据申请ID删除明细
     */
    default void deleteByRequestId(Long requestId) {
        delete(new LambdaQueryWrapperX<HisExamRequestItemDO>()
                .eq(HisExamRequestItemDO::getRequestId, requestId));
    }

    /**
     * 根据申请ID列表查询明细
     */
    default List<HisExamRequestItemDO> selectListByRequestIds(List<Long> requestIds) {
        return selectList(new LambdaQueryWrapperX<HisExamRequestItemDO>()
                .in(HisExamRequestItemDO::getRequestId, requestIds)
                .orderByAsc(HisExamRequestItemDO::getSortOrder));
    }

}
