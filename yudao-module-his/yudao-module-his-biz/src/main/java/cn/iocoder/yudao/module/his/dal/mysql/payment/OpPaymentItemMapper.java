package cn.iocoder.yudao.module.his.dal.mysql.payment;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 支付明细 Mapper
 *
 * @author yudao
 */
@Mapper
public interface OpPaymentItemMapper extends BaseMapperX<OpPaymentItemDO> {

    /**
     * 根据支付ID查询明细列表
     */
    default List<OpPaymentItemDO> selectListByPaymentId(Long paymentId) {
        return selectList(OpPaymentItemDO::getPaymentId, paymentId);
    }

    /**
     * 根据费用ID查询明细列表
     */
    default List<OpPaymentItemDO> selectListByFeeId(Long feeId) {
        return selectList(OpPaymentItemDO::getFeeId, feeId);
    }

    /**
     * 根据费用明细ID查询
     */
    default OpPaymentItemDO selectByFeeItemId(Long feeItemId) {
        return selectOne(OpPaymentItemDO::getFeeItemId, feeItemId);
    }

    /**
     * 根据挂号ID查询明细列表
     */
    default List<OpPaymentItemDO> selectListByRegisterId(Long registerId) {
        return selectList(OpPaymentItemDO::getRegisterId, registerId);
    }

    /**
     * 根据患者ID查询明细列表
     */
    default List<OpPaymentItemDO> selectListByPatientId(Long patientId) {
        return selectList(OpPaymentItemDO::getPatientId, patientId);
    }

}