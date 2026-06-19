package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInteractionDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 药物相互作用 Mapper
 */
@Mapper
public interface HisDrugInteractionMapper extends BaseMapperX<HisDrugInteractionDO> {

    /**
     * 查询与指定药品相关的所有相互作用
     *
     * @param drugId 药品ID
     * @return 相互作用列表
     */
    default List<HisDrugInteractionDO> selectByDrugId(Long drugId) {
        return selectList(new LambdaQueryWrapper<HisDrugInteractionDO>()
                .eq(HisDrugInteractionDO::getStatus, 1)
                .and(wrapper -> wrapper
                        .eq(HisDrugInteractionDO::getDrugIdA, drugId)
                        .or()
                        .eq(HisDrugInteractionDO::getDrugIdB, drugId)));
    }

    /**
     * 查询两个药品之间的相互作用
     *
     * @param drugIdA 药品A ID
     * @param drugIdB 药品B ID
     * @return 相互作用记录
     */
    default HisDrugInteractionDO selectByDrugPair(Long drugIdA, Long drugIdB) {
        return selectOne(new LambdaQueryWrapper<HisDrugInteractionDO>()
                .eq(HisDrugInteractionDO::getStatus, 1)
                .and(wrapper -> wrapper
                        .eq(HisDrugInteractionDO::getDrugIdA, drugIdA)
                        .eq(HisDrugInteractionDO::getDrugIdB, drugIdB)
                        .or()
                        .eq(HisDrugInteractionDO::getDrugIdA, drugIdB)
                        .eq(HisDrugInteractionDO::getDrugIdB, drugIdA)));
    }

    /**
     * 批量查询药品组合的相互作用
     *
     * @param drugIds 药品ID列表
     * @return 相互作用列表
     */
    default List<HisDrugInteractionDO> selectByDrugIds(List<Long> drugIds) {
        if (drugIds == null || drugIds.isEmpty()) {
            return List.of();
        }
        return selectList(new LambdaQueryWrapper<HisDrugInteractionDO>()
                .eq(HisDrugInteractionDO::getStatus, 1)
                .in(HisDrugInteractionDO::getDrugIdA, drugIds)
                .in(HisDrugInteractionDO::getDrugIdB, drugIds));
    }

}