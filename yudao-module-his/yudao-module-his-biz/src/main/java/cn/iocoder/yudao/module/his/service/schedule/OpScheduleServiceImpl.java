package cn.iocoder.yudao.module.his.service.schedule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpSchedulePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpScheduleSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.schedule.OpScheduleDO;
import cn.iocoder.yudao.module.his.dal.mysql.schedule.OpScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.REGISTER_SCHEDULE_NOT_EXISTS;

/**
 * 医生排班 Service 实现类
 */
@Service
@Validated
public class OpScheduleServiceImpl implements OpScheduleService {

    @Resource
    private OpScheduleMapper scheduleMapper;

    @Override
    public Long createSchedule(OpScheduleSaveReqVO createReqVO) {
        // 1. 校验医生该日期时段是否已有排班
        OpScheduleDO existSchedule = scheduleMapper.selectByDoctorAndDateAndPeriod(
                createReqVO.getDoctorId(), createReqVO.getScheduleDate(), createReqVO.getTimePeriod());
        if (existSchedule != null) {
            throw exception(REGISTER_SCHEDULE_NOT_EXISTS); // TODO: 需要添加重复排班错误码
        }

        // 2. 插入排班记录
        OpScheduleDO schedule = BeanUtils.toBean(createReqVO, OpScheduleDO.class);
        if (schedule.getUsedQuota() == null) {
            schedule.setUsedQuota(0);
        }
        if (schedule.getStatus() == null) {
            schedule.setStatus(1); // 默认正常状态
        }
        scheduleMapper.insert(schedule);

        return schedule.getId();
    }

    @Override
    public void updateSchedule(OpScheduleSaveReqVO updateReqVO) {
        // 1. 校验排班存在
        validateScheduleExists(updateReqVO.getId());

        // 2. 更新排班
        OpScheduleDO updateObj = BeanUtils.toBean(updateReqVO, OpScheduleDO.class);
        scheduleMapper.updateById(updateObj);
    }

    @Override
    public void deleteSchedule(Long id) {
        // 1. 校验排班存在
        OpScheduleDO schedule = validateScheduleExists(id);

        // 2. 校验是否可以删除（已有挂号记录的不能删除）
        if (schedule.getUsedQuota() != null && schedule.getUsedQuota() > 0) {
            throw exception(REGISTER_SCHEDULE_NOT_EXISTS); // TODO: 需要添加有挂号不能删除错误码
        }

        // 3. 删除排班
        scheduleMapper.deleteById(id);
    }

    @Override
    public OpScheduleDO getSchedule(Long id) {
        return scheduleMapper.selectById(id);
    }

    @Override
    public PageResult<OpScheduleDO> getSchedulePage(OpSchedulePageReqVO pageReqVO) {
        return scheduleMapper.selectPage(pageReqVO);
    }

    @Override
    public OpScheduleDO getScheduleByDoctorAndDateAndPeriod(Long doctorId, LocalDate scheduleDate, Integer timePeriod) {
        return scheduleMapper.selectByDoctorAndDateAndPeriod(doctorId, scheduleDate, timePeriod);
    }

    @Override
    public List<OpScheduleDO> getAvailableSchedule(LocalDate scheduleDate, Long deptId) {
        return scheduleMapper.selectAvailable(scheduleDate, deptId);
    }

    @Override
    public List<OpScheduleDO> getScheduleByDate(LocalDate scheduleDate) {
        return scheduleMapper.selectByDate(scheduleDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean incrementUsedQuota(Long id) {
        // 1. 获取排班
        OpScheduleDO schedule = scheduleMapper.selectById(id);
        if (schedule == null) {
            return false;
        }

        // 2. 检查是否有剩余号源
        if (!schedule.hasRemainingQuota()) {
            return false;
        }

        // 3. 增加已用号源
        schedule.setUsedQuota(schedule.getUsedQuota() + 1);

        // 4. 如果号源已满，更新状态
        if (schedule.getUsedQuota() >= schedule.getTotalQuota()) {
            schedule.setStatus(3); // 已满
        }

        scheduleMapper.updateById(schedule);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decrementUsedQuota(Long id) {
        // 1. 获取排班
        OpScheduleDO schedule = scheduleMapper.selectById(id);
        if (schedule == null || schedule.getUsedQuota() == null || schedule.getUsedQuota() <= 0) {
            return false;
        }

        // 2. 减少已用号源
        schedule.setUsedQuota(schedule.getUsedQuota() - 1);

        // 3. 如果原来是已满状态，恢复为正常
        if (schedule.getStatus() == 3) {
            schedule.setStatus(1); // 正常
        }

        scheduleMapper.updateById(schedule);
        return true;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        // 1. 校验排班存在
        OpScheduleDO schedule = validateScheduleExists(id);

        // 2. 更新状态
        OpScheduleDO updateObj = new OpScheduleDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        scheduleMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCreateSchedule(List<OpScheduleSaveReqVO> createReqVOs) {
        for (OpScheduleSaveReqVO createReqVO : createReqVOs) {
            createSchedule(createReqVO);
        }
    }

    @Override
    public OpScheduleDO validateScheduleExists(Long id) {
        if (id == null) {
            return null;
        }
        OpScheduleDO schedule = scheduleMapper.selectById(id);
        if (schedule == null) {
            throw exception(REGISTER_SCHEDULE_NOT_EXISTS);
        }
        return schedule;
    }

}