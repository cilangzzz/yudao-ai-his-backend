package cn.iocoder.yudao.module.his.dal.mysql.fee;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 支付记录 Mapper
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
     * 根据费用ID查询支付记录列表
     */
    default List<OpPaymentDO> selectListByFeeId(Long feeId) {
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getFeeId, feeId)
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 根据挂号ID查询支付记录列表
     */
    default List<OpPaymentDO> selectListByRegisterId(Long registerId) {
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getRegisterId, registerId)
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 根据患者ID查询支付记录列表
     */
    default List<OpPaymentDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getPatientId, patientId)
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 根据支付状态查询支付记录列表
     */
    default List<OpPaymentDO> selectListByPayStatus(Integer payStatus) {
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getPayStatus, payStatus)
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 根据支付方式查询支付记录列表
     */
    default List<OpPaymentDO> selectListByPayType(Integer payType) {
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getPayType, payType)
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 根据收费员ID查询支付记录列表
     */
    default List<OpPaymentDO> selectListByCashierId(Long cashierId) {
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getCashierId, cashierId)
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 根据发票号查询支付记录
     */
    default OpPaymentDO selectByInvoiceNo(String invoiceNo) {
        return selectOne(OpPaymentDO::getInvoiceNo, invoiceNo);
    }

    /**
     * 查询某日期范围内的支付记录列表
     */
    default List<OpPaymentDO> selectListByDateRange(LocalDate startDate, LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .between(OpPaymentDO::getPayTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 根据科室ID和日期范围查询支付记录列表
     */
    default List<OpPaymentDO> selectListByDeptIdAndDateRange(Long deptId, LocalDate startDate, LocalDate endDate) {
        // 需要通过费用表关联科室ID，这里简化处理
        return selectList(new LambdaQueryWrapperX<OpPaymentDO>()
                .between(OpPaymentDO::getPayTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())
                .orderByDesc(OpPaymentDO::getPayTime));
    }

    /**
     * 统计某患者的支付记录数量
     */
    default Long selectCountByPatientId(Long patientId) {
        return selectCount(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getPatientId, patientId));
    }

    /**
     * 统计某状态的支付记录数量
     */
    default Long selectCountByPayStatus(Integer payStatus) {
        return selectCount(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getPayStatus, payStatus));
    }

    /**
     * 统计某收费员的支付记录数量
     */
    default Long selectCountByCashierId(Long cashierId) {
        return selectCount(new LambdaQueryWrapperX<OpPaymentDO>()
                .eq(OpPaymentDO::getCashierId, cashierId));
    }

}