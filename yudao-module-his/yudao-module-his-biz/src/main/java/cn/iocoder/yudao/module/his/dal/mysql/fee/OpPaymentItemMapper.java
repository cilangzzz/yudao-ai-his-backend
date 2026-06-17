package cn.iocoder.yudao.module.his.dal.mysql.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 支付明细 Mapper
 *
 * @author yudao
 */
@Mapper
public interface OpPaymentItemMapper extends BaseMapperX<OpPaymentItemDO> {

    default PageResult<OpPaymentItemDO> selectPage(OpPaymentItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpPaymentItemDO>()
                .eqIfPresent(OpPaymentItemDO::getPaymentId, reqVO.getPaymentId())
                .eqIfPresent(OpPaymentItemDO::getFeeId, reqVO.getFeeId())
                .eqIfPresent(OpPaymentItemDO::getRegisterId, reqVO.getRegisterId())
                .eqIfPresent(OpPaymentItemDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(OpPaymentItemDO::getItemCategory, reqVO.getItemCategory())
                .eqIfPresent(OpPaymentItemDO::getPayType, reqVO.getPayType())
                .eqIfPresent(OpPaymentItemDO::getPayStatus, reqVO.getPayStatus())
                .likeIfPresent(OpPaymentItemDO::getPatientName, reqVO.getPatientName())
                .likeIfPresent(OpPaymentItemDO::getItemName, reqVO.getItemName())
                .betweenIfPresent(OpPaymentItemDO::getPayTime, reqVO.getPayTime())
                .orderByDesc(OpPaymentItemDO::getId));
    }

    default List<OpPaymentItemDO> selectListByPaymentId(Long paymentId) {
        return selectList(OpPaymentItemDO::getPaymentId, paymentId);
    }

    default List<OpPaymentItemDO> selectListByFeeId(Long feeId) {
        return selectList(OpPaymentItemDO::getFeeId, feeId);
    }

    default List<OpPaymentItemDO> selectListByFeeItemId(Long feeItemId) {
        return selectList(OpPaymentItemDO::getFeeItemId, feeItemId);
    }

    default List<OpPaymentItemDO> selectListByRegisterId(Long registerId) {
        return selectList(OpPaymentItemDO::getRegisterId, registerId);
    }

    default List<OpPaymentItemDO> selectListByPatientId(Long patientId) {
        return selectList(OpPaymentItemDO::getPatientId, patientId);
    }

}