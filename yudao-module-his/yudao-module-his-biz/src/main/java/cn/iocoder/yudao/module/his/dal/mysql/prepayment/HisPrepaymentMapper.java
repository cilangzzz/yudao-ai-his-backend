package cn.iocoder.yudao.module.his.dal.mysql.prepayment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 住院预交金 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisPrepaymentMapper extends BaseMapperX<HisPrepaymentDO> {

    default PageResult<HisPrepaymentDO> selectPage(HisPrepaymentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisPrepaymentDO>()
                .likeIfPresent(HisPrepaymentDO::getPrepaymentNo, reqVO.getPrepaymentNo())
                .likeIfPresent(HisPrepaymentDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisPrepaymentDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisPrepaymentDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisPrepaymentDO::getInpatientNo, reqVO.getInpatientNo())
                .eqIfPresent(HisPrepaymentDO::getStatus, reqVO.getStatus())
                .eqIfPresent(HisPrepaymentDO::getPaymentType, reqVO.getPaymentType())
                .betweenIfPresent(HisPrepaymentDO::getPayTime, reqVO.getPayTimeStart(), reqVO.getPayTimeEnd())
                .betweenIfPresent(HisPrepaymentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisPrepaymentDO::getId));
    }

    default HisPrepaymentDO selectByPrepaymentNo(String prepaymentNo) {
        return selectOne(HisPrepaymentDO::getPrepaymentNo, prepaymentNo);
    }

    default List<HisPrepaymentDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eq(HisPrepaymentDO::getAdmissionId, admissionId)
                .orderByDesc(HisPrepaymentDO::getPayTime));
    }

    default List<HisPrepaymentDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eq(HisPrepaymentDO::getPatientId, patientId)
                .orderByDesc(HisPrepaymentDO::getPayTime));
    }

    /**
     * 获取入院记录的有效预交金列表（已缴纳状态）
     */
    default List<HisPrepaymentDO> selectValidListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eq(HisPrepaymentDO::getAdmissionId, admissionId)
                .in(HisPrepaymentDO::getStatus, 1, 2) // 已缴纳或已使用
                .orderByDesc(HisPrepaymentDO::getPayTime));
    }

    /**
     * 统计入院记录的预交金总额
     */
    default BigDecimal sumAmountByAdmissionId(Long admissionId) {
        List<HisPrepaymentDO> list = selectList(new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eq(HisPrepaymentDO::getAdmissionId, admissionId)
                .in(HisPrepaymentDO::getStatus, 1, 2, 4)); // 已缴纳、已使用、已结算
        return list.stream()
                .map(p -> p.getAmount() != null ? p.getAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 统计入院记录的已使用金额
     */
    default BigDecimal sumUsedAmountByAdmissionId(Long admissionId) {
        List<HisPrepaymentDO> list = selectList(new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eq(HisPrepaymentDO::getAdmissionId, admissionId));
        return list.stream()
                .map(p -> p.getUsedAmount() != null ? p.getUsedAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 统计入院记录的已退还金额
     */
    default BigDecimal sumRefundAmountByAdmissionId(Long admissionId) {
        List<HisPrepaymentDO> list = selectList(new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eq(HisPrepaymentDO::getAdmissionId, admissionId));
        return list.stream()
                .map(p -> p.getRefundAmount() != null ? p.getRefundAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 统计入院记录的可用余额
     */
    default BigDecimal sumAvailableBalanceByAdmissionId(Long admissionId) {
        List<HisPrepaymentDO> list = selectValidListByAdmissionId(admissionId);
        return list.stream()
                .map(HisPrepaymentDO::getAvailableBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}