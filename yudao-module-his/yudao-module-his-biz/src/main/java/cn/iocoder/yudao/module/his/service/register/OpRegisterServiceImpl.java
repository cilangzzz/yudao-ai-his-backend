package cn.iocoder.yudao.module.his.service.register;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import cn.iocoder.yudao.module.his.dal.dataobject.register.OpRegisterDO;
import cn.iocoder.yudao.module.his.dal.dataobject.schedule.OpScheduleDO;
import cn.iocoder.yudao.module.his.dal.mysql.patient.HisPatientMapper;
import cn.iocoder.yudao.module.his.dal.mysql.register.OpRegisterMapper;
import cn.iocoder.yudao.module.his.dal.mysql.schedule.OpScheduleMapper;
import cn.iocoder.yudao.module.his.service.patient.HisPatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 挂号 Service 实现类
 */
@Service
@Validated
public class OpRegisterServiceImpl implements OpRegisterService {

    @Resource
    private OpRegisterMapper registerMapper;

    @Resource
    private OpScheduleMapper scheduleMapper;

    @Resource
    private HisPatientMapper patientMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRegister(OpRegisterSaveReqVO createReqVO) {
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

        // 4. 创建挂号记录
        OpRegisterDO register = new OpRegisterDO();
        register.setRegisterNo(generateRegisterNo());
        register.setAppointmentId(createReqVO.getAppointmentId());
        register.setPatientId(patient.getId());
        register.setPatientName(patient.getName());
        register.setPatientPhone(patient.getPhone());
        register.setIdCardNo(patient.getIdCardNo());
        register.setScheduleId(schedule.getId());
        register.setDoctorId(schedule.getDoctorId());
        register.setDoctorName(schedule.getDoctorName());
        register.setDeptId(schedule.getDeptId());
        register.setDeptName(schedule.getDeptName());
        register.setRegisterDate(schedule.getScheduleDate());
        register.setTimePeriod(schedule.getTimePeriod());
        register.setRegisterType(schedule.getRegisterType());
        register.setSourceType(createReqVO.getSourceType() != null ? createReqVO.getSourceType() : 1);
        register.setRegisterFee(schedule.getRegisterFee());
        register.setConsultationFee(schedule.getConsultationFee());
        register.setTotalFee(calculateTotalFee(schedule));
        register.setPayStatus(0); // 未支付
        register.setRegisterStatus(1); // 候诊

        // 分配排队号
        Integer maxQueueNo = registerMapper.selectMaxQueueNo(schedule.getId(), schedule.getScheduleDate());
        register.setQueueNo(maxQueueNo + 1);

        registerMapper.insert(register);

        // 5. 更新排班已用号源
        schedule.setUsedQuota(schedule.getUsedQuota() + 1);
        if (schedule.getUsedQuota() >= schedule.getTotalQuota()) {
            schedule.setStatus(3); // 已满
        }
        scheduleMapper.updateById(schedule);

        return register.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundRegister(Long id, String reason) {
        // 1. 校验挂号存在
        OpRegisterDO register = validateRegisterExists(id);

        // 2. 校验是否可以退号
        if (!register.canRefund()) {
            throw exception(REGISTER_ALREADY_COMPLETED);
        }

        // 3. 更新挂号状态
        OpRegisterDO updateObj = new OpRegisterDO();
        updateObj.setId(id);
        updateObj.setRegisterStatus(4); // 已退号
        updateObj.setRefundAmount(register.getTotalFee());
        updateObj.setRefundTime(LocalDateTime.now());
        updateObj.setRemark(reason);
        registerMapper.updateById(updateObj);

        // 4. 更新排班已用号源
        OpScheduleDO schedule = scheduleMapper.selectById(register.getScheduleId());
        if (schedule != null && schedule.getUsedQuota() > 0) {
            schedule.setUsedQuota(schedule.getUsedQuota() - 1);
            if (schedule.getStatus() == 3) { // 如果是已满状态，恢复为正常
                schedule.setStatus(1);
            }
            scheduleMapper.updateById(schedule);
        }
    }

    @Override
    public OpRegisterDO getRegister(Long id) {
        return registerMapper.selectById(id);
    }

    @Override
    public OpRegisterDO getRegisterByRegisterNo(String registerNo) {
        return registerMapper.selectByRegisterNo(registerNo);
    }

    @Override
    public PageResult<OpRegisterDO> getRegisterPage(OpRegisterPageReqVO pageReqVO) {
        return registerMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startVisit(Long id) {
        // 1. 校验挂号存在
        OpRegisterDO register = validateRegisterExists(id);

        // 2. 校验状态
        if (register.getRegisterStatus() != 1) {
            throw exception(REGISTER_ALREADY_COMPLETED);
        }

        // 3. 更新为就诊中
        OpRegisterDO updateObj = new OpRegisterDO();
        updateObj.setId(id);
        updateObj.setRegisterStatus(2); // 就诊中
        updateObj.setVisitTime(LocalDateTime.now());
        registerMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void endVisit(Long id) {
        // 1. 校验挂号存在
        OpRegisterDO register = validateRegisterExists(id);

        // 2. 校验状态
        if (register.getRegisterStatus() != 2) {
            throw exception(REGISTER_ALREADY_COMPLETED);
        }

        // 3. 更新为已完成
        OpRegisterDO updateObj = new OpRegisterDO();
        updateObj.setId(id);
        updateObj.setRegisterStatus(3); // 已完成
        updateObj.setEndTime(LocalDateTime.now());
        registerMapper.updateById(updateObj);
    }

    @Override
    public List<OpRegisterDO> getWaitingList(Long doctorId, LocalDate date) {
        return registerMapper.selectWaitingByDoctor(doctorId, date != null ? date : LocalDate.now());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OpRegisterDO callNext(Long doctorId, LocalDate date) {
        // 1. 获取候诊列表
        List<OpRegisterDO> waitingList = getWaitingList(doctorId, date);
        if (waitingList.isEmpty()) {
            return null;
        }

        // 2. 获取第一个候诊患者
        OpRegisterDO next = waitingList.get(0);

        // 3. 更新为就诊中
        startVisit(next.getId());

        return next;
    }

    @Override
    public OpRegisterDO validateRegisterExists(Long id) {
        if (id == null) {
            return null;
        }
        OpRegisterDO register = registerMapper.selectById(id);
        if (register == null) {
            throw exception(REGISTER_NOT_EXISTS);
        }
        return register;
    }

    /**
     * 生成挂号编号
     * 格式: R + yyyyMMdd + 4位流水号
     */
    private String generateRegisterNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("R%s%04d", dateStr, seq);
    }

    /**
     * 计算总费用
     */
    private BigDecimal calculateTotalFee(OpScheduleDO schedule) {
        BigDecimal total = BigDecimal.ZERO;
        if (schedule.getRegisterFee() != null) {
            total = total.add(schedule.getRegisterFee());
        }
        if (schedule.getConsultationFee() != null) {
            total = total.add(schedule.getConsultationFee());
        }
        return total;
    }

}