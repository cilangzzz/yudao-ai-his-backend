package cn.iocoder.yudao.module.his.service.appointment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.appointment.OpAppointmentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import cn.iocoder.yudao.module.his.dal.dataobject.schedule.OpScheduleDO;
import cn.iocoder.yudao.module.his.dal.mysql.appointment.OpAppointmentMapper;
import cn.iocoder.yudao.module.his.dal.mysql.patient.HisPatientMapper;
import cn.iocoder.yudao.module.his.dal.mysql.schedule.OpScheduleMapper;
import cn.iocoder.yudao.module.his.service.register.OpRegisterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 预约挂号 Service 实现类
 */
@Service
@Validated
public class OpAppointmentServiceImpl implements OpAppointmentService {

    @Resource
    private OpAppointmentMapper appointmentMapper;

    @Resource
    private OpScheduleMapper scheduleMapper;

    @Resource
    private HisPatientMapper patientMapper;

    @Resource
    private OpRegisterService registerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAppointment(OpAppointmentSaveReqVO createReqVO) {
        // 1. 校验患者
        HisPatientDO patient = patientMapper.selectById(createReqVO.getPatientId());
        if (patient == null) {
            throw exception(PATIENT_NOT_EXISTS);
        }

        // 2. 校验排班
        OpScheduleDO schedule = scheduleMapper.selectById(createReqVO.getScheduleId());
        if (schedule == null) {
            throw exception(REGISTER_SCHEDULE_NOT_EXISTS);
        }

        // 3. 校验排班状态和号源
        if (schedule.getStatus() != 1) {
            throw exception(REGISTER_SCHEDULE_NOT_EXISTS); // 排班已停诊或已满
        }
        if (!schedule.hasRemainingQuota()) {
            throw exception(REGISTER_SCHEDULE_NO_SLOT);
        }

        // 4. 校验预约日期是否在排班日期
        if (!createReqVO.getAppointmentDate().equals(schedule.getScheduleDate())) {
            throw exception(APPOINTMENT_TIME_INVALID);
        }

        // 5. 校验时段是否匹配
        if (!createReqVO.getTimePeriod().equals(schedule.getTimePeriod())) {
            throw exception(APPOINTMENT_TIME_INVALID);
        }

        // 6. 创建预约记录
        OpAppointmentDO appointment = new OpAppointmentDO();
        appointment.setAppointmentNo(generateAppointmentNo());
        appointment.setPatientId(patient.getId());
        appointment.setPatientName(patient.getName());
        appointment.setScheduleId(schedule.getId());
        appointment.setDoctorId(schedule.getDoctorId());
        appointment.setDoctorName(schedule.getDoctorName());
        appointment.setDeptId(schedule.getDeptId());
        appointment.setDeptName(schedule.getDeptName());
        appointment.setAppointmentDate(createReqVO.getAppointmentDate());
        appointment.setTimePeriod(createReqVO.getTimePeriod());
        appointment.setRegisterType(createReqVO.getRegisterType() != null ? createReqVO.getRegisterType() : schedule.getRegisterType());
        appointment.setSourceType(createReqVO.getSourceType() != null ? createReqVO.getSourceType() : 1);
        appointment.setAppointmentStatus(1); // 已预约
        appointment.setRemark(createReqVO.getRemark());

        // 7. 分配排队号
        Integer maxQueueNo = appointmentMapper.selectMaxQueueNo(
                schedule.getId(), createReqVO.getAppointmentDate(), createReqVO.getTimePeriod());
        appointment.setQueueNo(maxQueueNo != null ? maxQueueNo + 1 : 1);

        appointmentMapper.insert(appointment);

        // 8. 更新排班已用号源
        schedule.setUsedQuota(schedule.getUsedQuota() + 1);
        if (schedule.getUsedQuota() >= schedule.getTotalQuota()) {
            schedule.setStatus(3); // 已满
        }
        scheduleMapper.updateById(schedule);

        return appointment.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAppointment(Long id, String reason) {
        // 1. 校验预约存在
        OpAppointmentDO appointment = validateAppointmentExists(id);

        // 2. 校验是否可以取消
        if (!appointment.canCancel()) {
            throw exception(APPOINTMENT_ALREADY_CANCELLED);
        }

        // 3. 更新预约状态
        OpAppointmentDO updateObj = new OpAppointmentDO();
        updateObj.setId(id);
        updateObj.setAppointmentStatus(4); // 已取消
        updateObj.setCancelReason(reason);
        updateObj.setCancelTime(LocalDateTime.now());
        appointmentMapper.updateById(updateObj);

        // 4. 更新排班已用号源（减少）
        OpScheduleDO schedule = scheduleMapper.selectById(appointment.getScheduleId());
        if (schedule != null && schedule.getUsedQuota() != null && schedule.getUsedQuota() > 0) {
            schedule.setUsedQuota(schedule.getUsedQuota() - 1);
            if (schedule.getStatus() == 3) { // 如果是已满状态，恢复为正常
                schedule.setStatus(1);
            }
            scheduleMapper.updateById(schedule);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long takeAppointment(Long id) {
        // 1. 校验预约存在
        OpAppointmentDO appointment = validateAppointmentExists(id);

        // 2. 校验是否可以取号
        if (!appointment.canTake()) {
            throw exception(APPOINTMENT_ALREADY_CANCELLED);
        }

        // 3. 校验预约日期是否有效（当天或之前）
        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            throw exception(APPOINTMENT_EXPIRED);
        }

        // 4. 更新预约状态为已取号
        OpAppointmentDO updateObj = new OpAppointmentDO();
        updateObj.setId(id);
        updateObj.setAppointmentStatus(2); // 已取号
        appointmentMapper.updateById(updateObj);

        // 5. 创建挂号记录
        // 构建 OpRegisterSaveReqVO 并调用 registerService.createRegister()
        cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterSaveReqVO registerReqVO =
                new cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterSaveReqVO();
        registerReqVO.setPatientId(appointment.getPatientId());
        registerReqVO.setScheduleId(appointment.getScheduleId());
        registerReqVO.setAppointmentId(id);
        registerReqVO.setSourceType(appointment.getSourceType());

        return registerService.createRegister(registerReqVO);
    }

    @Override
    public OpAppointmentDO getAppointment(Long id) {
        return appointmentMapper.selectById(id);
    }

    @Override
    public OpAppointmentDO getAppointmentByAppointmentNo(String appointmentNo) {
        return appointmentMapper.selectByAppointmentNo(appointmentNo);
    }

    @Override
    public PageResult<OpAppointmentDO> getAppointmentPage(OpAppointmentPageReqVO pageReqVO) {
        return appointmentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OpAppointmentDO> getAppointmentListByPatientId(Long patientId) {
        return appointmentMapper.selectListByPatientId(patientId);
    }

    @Override
    public List<OpAppointmentDO> getAppointmentListByDoctorIdAndDate(Long doctorId, LocalDate date) {
        return appointmentMapper.selectListByDoctorIdAndDate(doctorId, date);
    }

    @Override
    public List<OpAppointmentDO> getAppointmentListByDeptIdAndDate(Long deptId, LocalDate date) {
        return appointmentMapper.selectListByDeptIdAndDate(deptId, date);
    }

    @Override
    public Long getAppointmentCountByScheduleIdAndDate(Long scheduleId, LocalDate date) {
        return appointmentMapper.selectCountByScheduleIdAndDate(scheduleId, date);
    }

    @Override
    public OpAppointmentDO validateAppointmentExists(Long id) {
        if (id == null) {
            return null;
        }
        OpAppointmentDO appointment = appointmentMapper.selectById(id);
        if (appointment == null) {
            throw exception(APPOINTMENT_NOT_EXISTS);
        }
        return appointment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processExpiredAppointments() {
        // 查询所有已预约且日期已过的预约
        List<OpAppointmentDO> expiredList = appointmentMapper.selectList(
                new cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX<OpAppointmentDO>()
                        .eq(OpAppointmentDO::getAppointmentStatus, 1) // 已预约
                        .lt(OpAppointmentDO::getAppointmentDate, LocalDate.now())); // 预约日期小于当天

        // 批量更新为已过期
        for (OpAppointmentDO appointment : expiredList) {
            OpAppointmentDO updateObj = new OpAppointmentDO();
            updateObj.setId(appointment.getId());
            updateObj.setAppointmentStatus(5); // 已过期
            appointmentMapper.updateById(updateObj);

            // 更新排班已用号源（减少）
            OpScheduleDO schedule = scheduleMapper.selectById(appointment.getScheduleId());
            if (schedule != null && schedule.getUsedQuota() != null && schedule.getUsedQuota() > 0) {
                schedule.setUsedQuota(schedule.getUsedQuota() - 1);
                if (schedule.getStatus() == 3) {
                    schedule.setStatus(1);
                }
                scheduleMapper.updateById(schedule);
            }
        }
    }

    /**
     * 生成预约编号
     * 格式: A + yyyyMMdd + 4位流水号
     */
    private String generateAppointmentNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("A%s%04d", dateStr, seq);
    }

}
