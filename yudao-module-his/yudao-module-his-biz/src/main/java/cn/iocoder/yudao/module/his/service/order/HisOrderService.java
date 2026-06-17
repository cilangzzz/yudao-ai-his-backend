package cn.iocoder.yudao.module.his.service.order;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.order.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.order.HisOrderDO;

import java.util.List;

/**
 * 医嘱 Service 接口
 */
public interface HisOrderService {

    /**
     * 分页查询医嘱
     */
    PageResult<HisOrderDO> getOrderPage(HisOrderPageReqVO pageReqVO);

    /**
     * 获取医嘱
     */
    HisOrderDO getOrder(Long id);

    /**
     * 按住院ID查询医嘱列表
     */
    List<HisOrderDO> getOrderListByAdmission(Long admissionId);

    /**
     * 查询待审核的医嘱
     */
    List<HisOrderDO> getPendingAuditOrderList(Long admissionId);

    /**
     * 查询执行中的医嘱
     */
    List<HisOrderDO> getExecutingOrderList(Long admissionId);

    /**
     * 查询长期医嘱
     */
    List<HisOrderDO> getLongTermOrderList(Long admissionId);

    /**
     * 查询药品医嘱
     */
    List<HisOrderDO> getDrugOrderList(Long admissionId);

    /**
     * 审核医嘱
     */
    void auditOrder(HisOrderAuditReqVO reqVO);

    /**
     * 执行医嘱
     */
    void executeOrder(HisOrderExecuteReqVO reqVO);

    /**
     * 停止医嘱
     */
    void stopOrder(HisOrderStopReqVO reqVO);

    /**
     * 作废医嘱
     */
    void cancelOrder(Long orderId, Long cancelDoctorId, String cancelReason);

    /**
     * 更新医嘱执行状态
     *
     * 当给药记录执行后调用此方法更新医嘱状态
     */
    void updateOrderExecuteStatus(Long orderId);

    /**
     * 校验医嘱是否存在
     */
    HisOrderDO validateOrderExists(Long id);
}