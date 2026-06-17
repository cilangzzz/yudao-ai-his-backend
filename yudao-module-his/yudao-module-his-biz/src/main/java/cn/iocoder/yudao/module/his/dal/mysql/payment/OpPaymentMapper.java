package cn.iocoder.yudao.module.his.dal.mysql.payment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.OpPaymentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 门诊支付记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface OpPaymentMapper extends BaseMapperX<OpPaymentDO> {

    /**
     * 根据支付单号查询
     */
    default OpPaymentDO selectByPaymentNo(String paymentNo) {
        return selectOne(OpPaymentDO::getPaymentNo, paymentNo);
    }

    /**
     * 根据费用ID查询
     */
    default List<OpPaymentDO> selectListByFeeId(Long feeId) {
        return selectList(OpPaymentDO::getFeeId, feeId);
    }

    /**
     * 根据挂号ID查询
     */
    default List<OpPaymentDO> selectListByRegisterId(Long registerId) {
        return selectList(OpPaymentDO::getRegisterId, registerId);
    }

    /**
     * 根据患者ID查询
     */
    default List<OpPaymentDO> selectListByPatientId(Long patientId) {
        return selectList(OpPaymentDO::getPatientId, patientId);
    }

    /**
     * 根据发票号查询
     */
    default OpPaymentDO selectByInvoiceNo(String invoiceNo) {
        return selectOne(OpPaymentDO::getInvoiceNo, invoiceNo);
    }

}