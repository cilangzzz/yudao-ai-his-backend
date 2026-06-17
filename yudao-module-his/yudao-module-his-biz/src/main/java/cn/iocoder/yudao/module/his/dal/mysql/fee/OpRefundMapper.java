package cn.iocoder.yudao.module.his.dal.mysql.fee;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpRefundDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 退费记录 Mapper
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
     * 根据原支付ID查询退费记录列表
     */
    default List<OpRefundDO> selectListByPaymentId(Long paymentId) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getPaymentId, paymentId)
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 根据费用ID查询退费记录列表
     */
    default List<OpRefundDO> selectListByFeeId(Long feeId) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getFeeId, feeId)
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 根据挂号ID查询退费记录列表
     */
    default List<OpRefundDO> selectListByRegisterId(Long registerId) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getRegisterId, registerId)
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 根据患者ID查询退费记录列表
     */
    default List<OpRefundDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getPatientId, patientId)
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 根据退费状态查询退费记录列表
     */
    default List<OpRefundDO> selectListByRefundStatus(Integer refundStatus) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getRefundStatus, refundStatus)
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 根据申请人ID查询退费记录列表
     */
    default List<OpRefundDO> selectListByApplyBy(Long applyBy) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getApplyBy, applyBy)
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 根据审核人ID查询退费记录列表
     */
    default List<OpRefundDO> selectListByAuditBy(Long auditBy) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getAuditBy, auditBy)
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 查询待审核的退费记录列表
     */
    default List<OpRefundDO> selectPendingRefunds() {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getRefundStatus, 1) // 待审核
                .orderByAsc(OpRefundDO::getApplyTime));
    }

    /**
     * 查询某日期范围内的退费记录列表
     */
    default List<OpRefundDO> selectListByDateRange(LocalDate startDate, LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<OpRefundDO>()
                .between(OpRefundDO::getApplyTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())
                .orderByDesc(OpRefundDO::getApplyTime));
    }

    /**
     * 统计某患者的退费记录数量
     */
    default Long selectCountByPatientId(Long patientId) {
        return selectCount(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getPatientId, patientId));
    }

    /**
     * 统计某状态的退费记录数量
     */
    default Long selectCountByRefundStatus(Integer refundStatus) {
        return selectCount(new LambdaQueryWrapperX<OpRefundDO>()
                .eq(OpRefundDO::getRefundStatus, refundStatus));
    }

}