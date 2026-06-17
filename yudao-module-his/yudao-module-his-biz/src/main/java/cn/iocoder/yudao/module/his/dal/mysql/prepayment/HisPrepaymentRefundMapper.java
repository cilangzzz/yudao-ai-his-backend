package cn.iocoder.yudao.module.his.dal.mysql.prepayment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentRefundPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentRefundDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预交金退还记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisPrepaymentRefundMapper extends BaseMapperX<HisPrepaymentRefundDO> {

    default PageResult<HisPrepaymentRefundDO> selectPage(HisPrepaymentRefundPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisPrepaymentRefundDO>()
                .likeIfPresent(HisPrepaymentRefundDO::getRefundNo, reqVO.getRefundNo())
                .likeIfPresent(HisPrepaymentRefundDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisPrepaymentRefundDO::getPrepaymentId, reqVO.getPrepaymentId())
                .eqIfPresent(HisPrepaymentRefundDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisPrepaymentRefundDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisPrepaymentRefundDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisPrepaymentRefundDO::getApplyTime, reqVO.getApplyTime())
                .orderByDesc(HisPrepaymentRefundDO::getId));
    }

    default HisPrepaymentRefundDO selectByRefundNo(String refundNo) {
        return selectOne(HisPrepaymentRefundDO::getRefundNo, refundNo);
    }

    default List<HisPrepaymentRefundDO> selectListByPrepaymentId(Long prepaymentId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentRefundDO>()
                .eq(HisPrepaymentRefundDO::getPrepaymentId, prepaymentId)
                .orderByDesc(HisPrepaymentRefundDO::getApplyTime));
    }

    default List<HisPrepaymentRefundDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentRefundDO>()
                .eq(HisPrepaymentRefundDO::getAdmissionId, admissionId)
                .orderByDesc(HisPrepaymentRefundDO::getApplyTime));
    }

    default List<HisPrepaymentRefundDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisPrepaymentRefundDO>()
                .eq(HisPrepaymentRefundDO::getPatientId, patientId)
                .orderByDesc(HisPrepaymentRefundDO::getApplyTime));
    }

    /**
     * 获取预交金的申请中退款记录
     */
    default HisPrepaymentRefundDO selectApplyingByPrepaymentId(Long prepaymentId) {
        return selectOne(new LambdaQueryWrapperX<HisPrepaymentRefundDO>()
                .eq(HisPrepaymentRefundDO::getPrepaymentId, prepaymentId)
                .eq(HisPrepaymentRefundDO::getStatus, 1)); // 申请中
    }

}