package cn.iocoder.yudao.module.his.service.order;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.order.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.order.HisOrderDO;
import cn.iocoder.yudao.module.his.dal.mysql.order.HisOrderMapper;
import cn.iocoder.yudao.module.his.enums.OrderStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 医嘱 Service 实现类
 */
@Service
@Validated
public class HisOrderServiceImpl implements HisOrderService {

    @Resource
    private HisOrderMapper orderMapper;

    @Override
    public PageResult<HisOrderDO> getOrderPage(HisOrderPageReqVO pageReqVO) {
        return orderMapper.selectPage(pageReqVO);
    }

    @Override
    public HisOrderDO getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<HisOrderDO> getOrderListByAdmission(Long admissionId) {
        return orderMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public List<HisOrderDO> getPendingAuditOrderList(Long admissionId) {
        return orderMapper.selectPendingAuditList(admissionId);
    }

    @Override
    public List<HisOrderDO> getExecutingOrderList(Long admissionId) {
        return orderMapper.selectExecutingList(admissionId);
    }

    @Override
    public List<HisOrderDO> getLongTermOrderList(Long admissionId) {
        return orderMapper.selectLongTermOrders(admissionId);
    }

    @Override
    public List<HisOrderDO> getDrugOrderList(Long admissionId) {
        return orderMapper.selectDrugOrders(admissionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditOrder(HisOrderAuditReqVO reqVO) {
        // 1. 校验医嘱存在
        HisOrderDO order = validateOrderExists(reqVO.getOrderId());

        // 2. 校验是否可以审核
        if (!order.canAudit()) {
            throw exception(ORDER_ALREADY_COMPLETED);
        }

        // 3. 更新医嘱状态
        HisOrderDO updateObj = new HisOrderDO();
        updateObj.setId(reqVO.getOrderId());
        updateObj.setOrderStatus(OrderStatusEnum.AUDITED.getStatus());
        updateObj.setAuditNurse(reqVO.getAuditNurseId());
        updateObj.setAuditNurseName(reqVO.getAuditNurseName());
        updateObj.setAuditTime(LocalDateTime.now());

        orderMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeOrder(HisOrderExecuteReqVO reqVO) {
        // 1. 校验医嘱存在
        HisOrderDO order = validateOrderExists(reqVO.getOrderId());

        // 2. 校验是否可以执行
        if (!order.canExecute()) {
            throw exception(ORDER_ALREADY_COMPLETED);
        }

        // 3. 更新医嘱状态
        HisOrderDO updateObj = new HisOrderDO();
        updateObj.setId(reqVO.getOrderId());
        updateObj.setOrderStatus(OrderStatusEnum.EXECUTING.getStatus());
        updateObj.setExecuteNurse(reqVO.getExecuteNurseId());
        updateObj.setExecuteNurseName(reqVO.getExecuteNurseName());
        updateObj.setExecuteTime(reqVO.getExecuteTime() != null ? reqVO.getExecuteTime() : LocalDateTime.now());

        orderMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopOrder(HisOrderStopReqVO reqVO) {
        // 1. 校验医嘱存在
        HisOrderDO order = validateOrderExists(reqVO.getOrderId());

        // 2. 校验是否可以停止
        if (!order.canStop()) {
            throw exception(ORDER_ALREADY_STOPPED);
        }

        // 3. 更新医嘱状态
        HisOrderDO updateObj = new HisOrderDO();
        updateObj.setId(reqVO.getOrderId());
        updateObj.setOrderStatus(OrderStatusEnum.STOPPED.getStatus());
        updateObj.setStopDoctor(reqVO.getStopDoctorId());
        updateObj.setStopDoctorName(reqVO.getStopDoctorName());
        updateObj.setStopTime(reqVO.getStopTime() != null ? reqVO.getStopTime() : LocalDateTime.now());
        updateObj.setStopReason(reqVO.getStopReason());

        orderMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId, Long cancelDoctorId, String cancelReason) {
        // 1. 校验医嘱存在
        HisOrderDO order = validateOrderExists(orderId);

        // 2. 校验是否可以作废
        if (!order.canCancel()) {
            throw exception(ORDER_CANNOT_MODIFY);
        }

        // 3. 更新医嘱状态
        HisOrderDO updateObj = new HisOrderDO();
        updateObj.setId(orderId);
        updateObj.setOrderStatus(OrderStatusEnum.CANCELLED.getStatus());
        // 记录作废信息到停止医生字段（复用）
        updateObj.setStopDoctor(cancelDoctorId);
        updateObj.setStopReason(cancelReason);
        updateObj.setStopTime(LocalDateTime.now());

        orderMapper.updateById(updateObj);
    }

    @Override
    public void updateOrderExecuteStatus(Long orderId) {
        HisOrderDO order = validateOrderExists(orderId);

        // 如果医嘱是审核状态，更新为执行中
        if (order.getOrderStatus() == OrderStatusEnum.AUDITED.getStatus()) {
            HisOrderDO updateObj = new HisOrderDO();
            updateObj.setId(orderId);
            updateObj.setOrderStatus(OrderStatusEnum.EXECUTING.getStatus());
            updateObj.setExecuteTime(LocalDateTime.now());

            orderMapper.updateById(updateObj);
        }
    }

    @Override
    public HisOrderDO validateOrderExists(Long id) {
        if (id == null) {
            return null;
        }
        HisOrderDO order = orderMapper.selectById(id);
        if (order == null) {
            throw exception(ORDER_NOT_EXISTS);
        }
        return order;
    }
}