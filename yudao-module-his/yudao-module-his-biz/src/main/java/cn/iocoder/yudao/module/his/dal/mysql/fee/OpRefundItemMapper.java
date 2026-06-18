package cn.iocoder.yudao.module.his.dal.mysql.fee;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpRefundItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 退费明细 Mapper
 *
 * @author yudao
 */
@Mapper
public interface OpRefundItemMapper extends BaseMapperX<OpRefundItemDO> {

    /**
     * 根据退费记录ID查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByRefundId(Long refundId) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getRefundId, refundId)
                .orderByAsc(OpRefundItemDO::getId));
    }

    /**
     * 根据退费单号查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByRefundNo(String refundNo) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getRefundNo, refundNo)
                .orderByAsc(OpRefundItemDO::getId));
    }

    /**
     * 根据原支付ID查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByPaymentId(Long paymentId) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getPaymentId, paymentId)
                .orderByDesc(OpRefundItemDO::getCreateTime));
    }

    /**
     * 根据原支付明细ID查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByPaymentItemId(Long paymentItemId) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getPaymentItemId, paymentItemId)
                .orderByDesc(OpRefundItemDO::getCreateTime));
    }

    /**
     * 根据费用明细ID查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByFeeItemId(Long feeItemId) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getFeeItemId, feeItemId)
                .orderByDesc(OpRefundItemDO::getCreateTime));
    }

    /**
     * 根据挂号ID查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByRegisterId(Long registerId) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getRegisterId, registerId)
                .orderByDesc(OpRefundItemDO::getCreateTime));
    }

    /**
     * 根据患者ID查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getPatientId, patientId)
                .orderByDesc(OpRefundItemDO::getCreateTime));
    }

    /**
     * 根据退费状态查询退费明细列表
     */
    default List<OpRefundItemDO> selectListByRefundStatus(Integer refundStatus) {
        return selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getRefundStatus, refundStatus)
                .orderByDesc(OpRefundItemDO::getCreateTime));
    }

    /**
     * 统计某个支付明细已退费的数量
     */
    default BigDecimal selectRefundedQuantityByPaymentItemId(Long paymentItemId) {
        List<OpRefundItemDO> items = selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getPaymentItemId, paymentItemId)
                .in(OpRefundItemDO::getRefundStatus, 2, 4)); // 已通过或已完成
        return items.stream()
                .map(OpRefundItemDO::getRefundQuantity)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    /**
     * 统计某个支付明细已退费的金额
     */
    default java.math.BigDecimal selectRefundedAmountByPaymentItemId(Long paymentItemId) {
        List<OpRefundItemDO> items = selectList(new LambdaQueryWrapperX<OpRefundItemDO>()
                .eq(OpRefundItemDO::getPaymentItemId, paymentItemId)
                .in(OpRefundItemDO::getRefundStatus, 2, 4)); // 已通过或已完成
        return items.stream()
                .map(OpRefundItemDO::getRefundAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

}