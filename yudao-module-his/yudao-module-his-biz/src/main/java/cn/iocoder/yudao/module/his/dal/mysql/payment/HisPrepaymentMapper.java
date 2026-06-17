package cn.iocoder.yudao.module.his.dal.mysql.payment;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.HisPrepaymentDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 预交金记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisPrepaymentMapper extends BaseMapperX<HisPrepaymentDO> {

    /**
     * 根据预交金单号查询
     */
    default HisPrepaymentDO selectByPrepayNo(String prepayNo) {
        return selectOne(HisPrepaymentDO::getPrepayNo, prepayNo);
    }

    /**
     * 根据住院ID查询预交金列表
     */
    default List<HisPrepaymentDO> selectListByAdmissionId(Long admissionId) {
        return selectList(HisPrepaymentDO::getAdmissionId, admissionId);
    }

    /**
     * 根据患者ID查询预交金列表
     */
    default List<HisPrepaymentDO> selectListByPatientId(Long patientId) {
        return selectList(HisPrepaymentDO::getPatientId, patientId);
    }

    /**
     * 查询住院预交金总额
     */
    default BigDecimal selectTotalAmountByAdmissionId(Long admissionId) {
        List<HisPrepaymentDO> list = selectList(new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eq(HisPrepaymentDO::getAdmissionId, admissionId)
                .eq(HisPrepaymentDO::getPayStatus, 1)); // 正常状态
        return list.stream()
                .map(HisPrepaymentDO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}