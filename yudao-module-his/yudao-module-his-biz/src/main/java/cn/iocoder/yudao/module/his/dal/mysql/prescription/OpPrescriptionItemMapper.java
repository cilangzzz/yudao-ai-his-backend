package cn.iocoder.yudao.module.his.dal.mysql.prescription;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 处方明细 Mapper
 */
@Mapper
public interface OpPrescriptionItemMapper extends BaseMapperX<OpPrescriptionItemDO> {

    /**
     * 根据处方ID查询明细列表
     *
     * @param prescriptionId 处方ID
     * @return 明细列表
     */
    default List<OpPrescriptionItemDO> selectListByPrescriptionId(Long prescriptionId) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionItemDO>()
                .eq(OpPrescriptionItemDO::getPrescriptionId, prescriptionId)
                .orderByAsc(OpPrescriptionItemDO::getSortOrder));
    }

    /**
     * 根据处方ID列表批量查询明细
     *
     * @param prescriptionIds 处方ID列表
     * @return 明细列表
     */
    default List<OpPrescriptionItemDO> selectListByPrescriptionIds(List<Long> prescriptionIds) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionItemDO>()
                .in(OpPrescriptionItemDO::getPrescriptionId, prescriptionIds)
                .orderByAsc(OpPrescriptionItemDO::getSortOrder));
    }

    /**
     * 根据药品ID查询明细列表
     *
     * @param drugId 药品ID
     * @return 明细列表
     */
    default List<OpPrescriptionItemDO> selectListByDrugId(Long drugId) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionItemDO>()
                .eq(OpPrescriptionItemDO::getDrugId, drugId)
                .orderByDesc(OpPrescriptionItemDO::getCreateTime));
    }

    /**
     * 查询需要皮试的明细列表
     *
     * @param prescriptionId 处方ID
     * @return 需要皮试的明细列表
     */
    default List<OpPrescriptionItemDO> selectSkinTestList(Long prescriptionId) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionItemDO>()
                .eq(OpPrescriptionItemDO::getPrescriptionId, prescriptionId)
                .ge(OpPrescriptionItemDO::getSkinTest, 1) // 需要皮试或已做皮试
                .orderByAsc(OpPrescriptionItemDO::getSortOrder));
    }

    /**
     * 查询待皮试的明细列表
     *
     * @param prescriptionId 处方ID
     * @return 待皮试的明细列表
     */
    default List<OpPrescriptionItemDO> selectPendingSkinTestList(Long prescriptionId) {
        return selectList(new LambdaQueryWrapperX<OpPrescriptionItemDO>()
                .eq(OpPrescriptionItemDO::getPrescriptionId, prescriptionId)
                .eq(OpPrescriptionItemDO::getSkinTest, 1) // 需要皮试但未做
                .orderByAsc(OpPrescriptionItemDO::getSortOrder));
    }

    /**
     * 根据处方ID删除明细
     *
     * @param prescriptionId 处方ID
     */
    default void deleteByPrescriptionId(Long prescriptionId) {
        delete(new LambdaQueryWrapperX<OpPrescriptionItemDO>()
                .eq(OpPrescriptionItemDO::getPrescriptionId, prescriptionId));
    }

    /**
     * 根据处方ID统计明细数量
     *
     * @param prescriptionId 处方ID
     * @return 明细数量
     */
    default Long selectCountByPrescriptionId(Long prescriptionId) {
        return selectCount(new LambdaQueryWrapperX<OpPrescriptionItemDO>()
                .eq(OpPrescriptionItemDO::getPrescriptionId, prescriptionId));
    }

    /**
     * 计算处方总金额
     *
     * @param prescriptionId 处方ID
     * @return 总金额
     */
    default java.math.BigDecimal selectTotalAmountByPrescriptionId(Long prescriptionId) {
        List<OpPrescriptionItemDO> items = selectListByPrescriptionId(prescriptionId);
        return items.stream()
                .map(item -> item.getAmount() != null ? item.getAmount() : java.math.BigDecimal.ZERO)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

}
