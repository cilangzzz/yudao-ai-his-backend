package cn.iocoder.yudao.module.his.service.lab;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisCriticalValuePageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisCriticalValueDO;
import cn.iocoder.yudao.module.his.dal.mysql.lab.HisCriticalValueMapper;
import cn.iocoder.yudao.module.his.enums.CriticalValueStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.CRITICAL_VALUE_ALREADY_CONFIRMED;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.CRITICAL_VALUE_NOT_CONFIRMED;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.CRITICAL_VALUE_NOT_EXISTS;

/**
 * 危急值管理 Service 实现类
 */
@Service
@Validated
@Slf4j
public class HisCriticalValueServiceImpl implements HisCriticalValueService {

    @Resource
    private HisCriticalValueMapper criticalValueMapper;

    @Override
    public PageResult<HisCriticalValueDO> getCriticalValuePage(HisCriticalValuePageReqVO pageReqVO) {
        return criticalValueMapper.selectPage(pageReqVO);
    }

    @Override
    public HisCriticalValueDO getCriticalValue(Long id) {
        return criticalValueMapper.selectById(id);
    }

    @Override
    public List<HisCriticalValueDO> getDetectedList() {
        return criticalValueMapper.selectByStatus(CriticalValueStatusEnum.DETECTED.getValue());
    }

    @Override
    public List<HisCriticalValueDO> getUnconfirmedList() {
        return criticalValueMapper.selectByStatus(CriticalValueStatusEnum.NOTIFIED.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notifyCriticalValue(Long id) {
        // 1. 校验存在且状态正确
        HisCriticalValueDO criticalValue = validateCriticalValueExists(id);
        if (!CriticalValueStatusEnum.DETECTED.getValue().equals(criticalValue.getStatus())) {
            throw exception(CRITICAL_VALUE_ALREADY_CONFIRMED);
        }

        // 2. 更新状态为已通知
        HisCriticalValueDO updateObj = new HisCriticalValueDO();
        updateObj.setId(id);
        updateObj.setStatus(CriticalValueStatusEnum.NOTIFIED.getValue());
        updateObj.setNotifyTime(LocalDateTime.now());
        // TODO: 获取当前登录用户作为通知人
        criticalValueMapper.updateById(updateObj);

        log.info("[notifyCriticalValue] 危急值通知成功，id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmCriticalValue(Long id) {
        // 1. 校验存在且状态正确
        HisCriticalValueDO criticalValue = validateCriticalValueExists(id);
        if (!CriticalValueStatusEnum.NOTIFIED.getValue().equals(criticalValue.getStatus())
                && !CriticalValueStatusEnum.TIMEOUT_ESCALATED.getValue().equals(criticalValue.getStatus())) {
            throw exception(CRITICAL_VALUE_ALREADY_CONFIRMED);
        }

        // 2. 更新状态为已确认
        HisCriticalValueDO updateObj = new HisCriticalValueDO();
        updateObj.setId(id);
        updateObj.setStatus(CriticalValueStatusEnum.CONFIRMED.getValue());
        updateObj.setConfirmTime(LocalDateTime.now());
        // TODO: 获取当前登录用户作为确认人
        criticalValueMapper.updateById(updateObj);

        log.info("[confirmCriticalValue] 危急值确认成功，id={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processCriticalValue(Long id, String processResult) {
        // 1. 校验存在且状态正确
        HisCriticalValueDO criticalValue = validateCriticalValueExists(id);
        if (!CriticalValueStatusEnum.CONFIRMED.getValue().equals(criticalValue.getStatus())) {
            throw exception(CRITICAL_VALUE_NOT_CONFIRMED);
        }

        // 2. 更新状态为已处理
        HisCriticalValueDO updateObj = new HisCriticalValueDO();
        updateObj.setId(id);
        updateObj.setStatus(CriticalValueStatusEnum.PROCESSED.getValue());
        updateObj.setProcessTime(LocalDateTime.now());
        updateObj.setProcessResult(processResult);
        // TODO: 获取当前登录用户作为处理人
        criticalValueMapper.updateById(updateObj);

        log.info("[processCriticalValue] 危急值处理成功，id={}", id);
    }

    @Override
    public HisCriticalValueDO validateCriticalValueExists(Long id) {
        if (id == null) {
            return null;
        }
        HisCriticalValueDO criticalValue = criticalValueMapper.selectById(id);
        if (criticalValue == null) {
            throw exception(CRITICAL_VALUE_NOT_EXISTS);
        }
        return criticalValue;
    }

}