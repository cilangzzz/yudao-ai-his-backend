package cn.iocoder.yudao.module.his.dal.mysql.payment;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpRefundDO;
import cn.iocoder.yudao.module.his.enums.RefundStatusEnum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 门诊退费记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface OpRefundMapper extends BaseMapperX<OpRefundDO> {

    /**
     * 根据退费单号查询
     */
    default OpRefundDO selectByRefundNo(String refundNo) {
        return selectOne(OpRefundDO::getRefundNo, refundNo);
    }

    /**
     * 根据支付ID查询退费记录
     */
    default List<OpRefundDO> selectListByPaymentId(Long paymentId) {
        return selectList(OpRefundDO::getPaymentId, paymentId);
    }

    /**
     * 根据患者ID查询退费记录
     */
    default List<OpRefundDO> selectListByPatientId(Long patientId) {
        return selectList(OpRefundDO::getPatientId, patientId);
    }

    /**
     * 查询待审核的退费记录
     */
    default List<OpRefundDO> selectPendingList() {
        return selectList(OpRefundDO::getRefundStatus, RefundStatusEnum.PENDING.getStatus());
    }

}