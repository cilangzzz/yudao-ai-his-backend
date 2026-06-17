package cn.iocoder.yudao.module.his.dal.mysql.order;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.order.HisOrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 医嘱 Mapper
 */
@Mapper
public interface HisOrderMapper extends BaseMapperX<HisOrderDO> {

    /**
     * 分页查询医嘱
     */
    default PageResult<HisOrderDO> selectPage(
            cn.iocoder.yudao.module.his.controller.admin.order.vo.HisOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisOrderDO>()
                .eqIfPresent(HisOrderDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisOrderDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisOrderDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(HisOrderDO::getOrderCategory, reqVO.getOrderCategory())
                .eqIfPresent(HisOrderDO::getOrderStatus, reqVO.getOrderStatus())
                .betweenIfPresent(HisOrderDO::getStartTime, reqVO.getStartTime())
                .orderByDesc(HisOrderDO::getId));
    }

    /**
     * 按住院ID查询医嘱列表
     */
    default List<HisOrderDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisOrderDO>()
                .eq(HisOrderDO::getAdmissionId, admissionId)
                .orderByAsc(HisOrderDO::getStartTime));
    }

    /**
     * 按患者ID查询医嘱列表
     */
    default List<HisOrderDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisOrderDO>()
                .eq(HisOrderDO::getPatientId, patientId)
                .orderByDesc(HisOrderDO::getStartTime));
    }

    /**
     * 查询待审核的医嘱
     */
    default List<HisOrderDO> selectPendingAuditList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisOrderDO>()
                .eq(HisOrderDO::getAdmissionId, admissionId)
                .eq(HisOrderDO::getOrderStatus, 1) // 开立状态
                .orderByAsc(HisOrderDO::getStartTime));
    }

    /**
     * 查询执行中的医嘱
     */
    default List<HisOrderDO> selectExecutingList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisOrderDO>()
                .eq(HisOrderDO::getAdmissionId, admissionId)
                .eq(HisOrderDO::getOrderStatus, 3) // 执行中
                .orderByAsc(HisOrderDO::getStartTime));
    }

    /**
     * 查询长期医嘱
     */
    default List<HisOrderDO> selectLongTermOrders(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisOrderDO>()
                .eq(HisOrderDO::getAdmissionId, admissionId)
                .eq(HisOrderDO::getOrderCategory, 1) // 长期医嘱
                .in(HisOrderDO::getOrderStatus, 2, 3) // 审核或执行中
                .orderByAsc(HisOrderDO::getStartTime));
    }

    /**
     * 查询药品医嘱
     */
    default List<HisOrderDO> selectDrugOrders(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisOrderDO>()
                .eq(HisOrderDO::getAdmissionId, admissionId)
                .eq(HisOrderDO::getOrderType, 1) // 药品医嘱
                .in(HisOrderDO::getOrderStatus, 2, 3) // 审核或执行中
                .orderByAsc(HisOrderDO::getStartTime));
    }
}