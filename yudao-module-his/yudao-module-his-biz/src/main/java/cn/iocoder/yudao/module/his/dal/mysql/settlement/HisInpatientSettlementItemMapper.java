package cn.iocoder.yudao.module.his.dal.mysql.settlement;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientSettlementItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 住院结算明细 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisInpatientSettlementItemMapper extends BaseMapperX<HisInpatientSettlementItemDO> {

    /**
     * 根据结算单ID查询明细列表
     */
    default List<HisInpatientSettlementItemDO> selectListBySettlementId(Long settlementId) {
        return selectList(new LambdaQueryWrapperX<HisInpatientSettlementItemDO>()
                .eq(HisInpatientSettlementItemDO::getSettlementId, settlementId)
                .orderByAsc(HisInpatientSettlementItemDO::getFeeDate));
    }

    /**
     * 根据费用明细ID查询
     */
    default HisInpatientSettlementItemDO selectByFeeId(Long feeId) {
        return selectOne(new LambdaQueryWrapperX<HisInpatientSettlementItemDO>()
                .eq(HisInpatientSettlementItemDO::getFeeId, feeId));
    }

    /**
     * 根据结算单ID删除明细
     */
    default void deleteBySettlementId(Long settlementId) {
        delete(new LambdaQueryWrapperX<HisInpatientSettlementItemDO>()
                .eq(HisInpatientSettlementItemDO::getSettlementId, settlementId));
    }

    /**
     * 根据入院记录ID查询明细列表
     */
    default List<HisInpatientSettlementItemDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisInpatientSettlementItemDO>()
                .eq(HisInpatientSettlementItemDO::getAdmissionId, admissionId)
                .orderByAsc(HisInpatientSettlementItemDO::getFeeDate));
    }

}