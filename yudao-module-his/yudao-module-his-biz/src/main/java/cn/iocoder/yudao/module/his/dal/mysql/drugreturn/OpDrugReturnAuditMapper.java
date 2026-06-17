package cn.iocoder.yudao.module.his.dal.mysql.drugreturn;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnAuditDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 退药审核记录 Mapper
 */
@Mapper
public interface OpDrugReturnAuditMapper extends BaseMapperX<OpDrugReturnAuditDO> {

    /**
     * 根据退药ID查询审核记录
     */
    default List<OpDrugReturnAuditDO> selectListByReturnId(Long returnId) {
        return selectList(new LambdaQueryWrapperX<OpDrugReturnAuditDO>()
                .eq(OpDrugReturnAuditDO::getReturnId, returnId)
                .orderByDesc(OpDrugReturnAuditDO::getAuditTime));
    }

    /**
     * 根据退药ID和审核类型查询
     */
    default OpDrugReturnAuditDO selectByReturnIdAndType(Long returnId, Integer auditType) {
        return selectOne(new LambdaQueryWrapperX<OpDrugReturnAuditDO>()
                .eq(OpDrugReturnAuditDO::getReturnId, returnId)
                .eq(OpDrugReturnAuditDO::getAuditType, auditType)
                .orderByDesc(OpDrugReturnAuditDO::getAuditTime)
                .last("LIMIT 1"));
    }

    /**
     * 根据审核人查询审核记录
     */
    default List<OpDrugReturnAuditDO> selectListByAuditBy(Long auditBy) {
        return selectList(OpDrugReturnAuditDO::getAuditBy, auditBy);
    }

    /**
     * 统计退药审核记录数
     */
    default Long countByReturnId(Long returnId) {
        return selectCount(OpDrugReturnAuditDO::getReturnId, returnId);
    }

}
