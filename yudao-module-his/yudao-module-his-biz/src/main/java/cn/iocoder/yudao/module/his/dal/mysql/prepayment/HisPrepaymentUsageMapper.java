package cn.iocoder.yudao.module.his.dal.mysql.prepayment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentUsageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预交金使用记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisPrepaymentUsageMapper extends BaseMapperX<HisPrepaymentUsageDO> {

    default HisPrepaymentUsageDO selectByUsageNo(String usageNo) {
        return selectOne(HisPrepaymentUsageDO::getUsageNo, usageNo);
    }

    default List<HisPrepaymentUsageDO> selectListByPrepaymentId(Long prepaymentId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentUsageDO>()
                .eq(HisPrepaymentUsageDO::getPrepaymentId, prepaymentId)
                .orderByDesc(HisPrepaymentUsageDO::getUseTime));
    }

    default List<HisPrepaymentUsageDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentUsageDO>()
                .eq(HisPrepaymentUsageDO::getAdmissionId, admissionId)
                .orderByDesc(HisPrepaymentUsageDO::getUseTime));
    }

    default List<HisPrepaymentUsageDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentUsageDO>()
                .eq(HisPrepaymentUsageDO::getPatientId, patientId)
                .orderByDesc(HisPrepaymentUsageDO::getUseTime));
    }

}