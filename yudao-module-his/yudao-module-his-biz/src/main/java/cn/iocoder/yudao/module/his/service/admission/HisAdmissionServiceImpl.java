package cn.iocoder.yudao.module.his.service.admission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.DischargeApplyReqVO;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.HisAdmissionPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.HisAdmissionSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.bed.HisBedDO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import cn.iocoder.yudao.module.his.dal.mysql.admission.HisAdmissionMapper;
import cn.iocoder.yudao.module.his.service.bed.HisBedService;
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
 * 入院记录 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisAdmissionServiceImpl implements HisAdmissionService {

    @Resource
    private HisAdmissionMapper admissionMapper;

    @Resource
    private HisPatientService patientService;

    @Resource
    private HisBedService bedService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAdmission(HisAdmissionSaveReqVO createReqVO) {
        // 1. 校验患者
        HisPatientDO patient = patientService.getPatient(createReqVO.getPatientId());
        if (patient == null) {
            throw exception(PATIENT_NOT_EXISTS);
        }

        // 2. 校验患者是否已有在院记录
        HisAdmissionDO existingAdmission = admissionMapper.selectInpatientByPatientId(patient.getId());
        if (existingAdmission != null) {
            throw exception(ADMISSION_ALREADY_DISCHARGED); // 患者已在院
        }

        // 3. 如果指定了床位，校验并占用床位
        if (createReqVO.getBedId() != null) {
            HisBedDO bed = bedService.validateBedAvailable(createReqVO.getBedId());
        }

        // 4. 创建入院记录
        HisAdmissionDO admission = BeanUtils.toBean(createReqVO, HisAdmissionDO.class);
        admission.setAdmissionNo(generateAdmissionNo());
        admission.setPatientName(patient.getName());
        admission.setPatientPhone(patient.getPhone());
        admission.setIdCardNo(patient.getIdCardNo());
        admission.setAdmissionStatus(1); // 在院
        admission.setDepositAmount(createReqVO.getDepositAmount() != null ? createReqVO.getDepositAmount() : BigDecimal.ZERO);
        admission.setTotalFee(BigDecimal.ZERO);

        admissionMapper.insert(admission);

        // 5. 占用床位
        if (createReqVO.getBedId() != null) {
            bedService.occupyBed(createReqVO.getBedId(), patient.getId(), patient.getName(), admission.getId());
        }

        return admission.getId();
    }

    @Override
    public void updateAdmission(HisAdmissionSaveReqVO updateReqVO) {
        // 校验存在
        validateAdmissionExists(updateReqVO.getId());
        // 更新
        HisAdmissionDO updateObj = BeanUtils.toBean(updateReqVO, HisAdmissionDO.class);
        admissionMapper.updateById(updateObj);
    }

    @Override
    public void deleteAdmission(Long id) {
        // 校验存在
        HisAdmissionDO admission = validateAdmissionExists(id);
        // 校验状态
        if (admission.isInpatient()) {
            throw exception(ADMISSION_ALREADY_DISCHARGED);
        }
        // 删除
        admissionMapper.deleteById(id);
    }

    @Override
    public HisAdmissionDO getAdmission(Long id) {
        return admissionMapper.selectById(id);
    }

    @Override
    public HisAdmissionDO getAdmissionByNo(String admissionNo) {
        return admissionMapper.selectByAdmissionNo(admissionNo);
    }

    @Override
    public PageResult<HisAdmissionDO> getAdmissionPage(HisAdmissionPageReqVO pageReqVO) {
        return admissionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisAdmissionDO> getAdmissionListByPatientId(Long patientId) {
        return admissionMapper.selectByPatientId(patientId);
    }

    @Override
    public List<HisAdmissionDO> getInHospitalList() {
        return admissionMapper.selectList(admission -> admission
                .eq(HisAdmissionDO::getAdmissionStatus, 1));
    }

    @Override
    public List<HisAdmissionDO> getInHospitalListByDept(Long deptId) {
        return admissionMapper.selectInpatientsByDept(deptId);
    }

    @Override
    public List<HisAdmissionDO> getInHospitalListByWard(Long wardId) {
        return admissionMapper.selectInpatientsByWard(wardId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyDischarge(DischargeApplyReqVO reqVO) {
        // 1. 校验入院记录
        HisAdmissionDO admission = validateAdmissionInHospital(reqVO.getAdmissionId());

        // 2. 更新出院信息
        HisAdmissionDO updateObj = new HisAdmissionDO();
        updateObj.setId(reqVO.getAdmissionId());
        updateObj.setDischargeWay(reqVO.getDischargeWay());
        updateObj.setDischargeDiagnosis(reqVO.getDischargeDiagnosis());
        updateObj.setDischargeCode(reqVO.getDischargeCode());
        updateObj.setDischargeDate(reqVO.getDischargeDate() != null ? reqVO.getDischargeDate() : LocalDateTime.now());
        updateObj.setDischargeDept(admission.getAdmissionDept());

        admissionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmDischarge(Long id) {
        // 1. 校验入院记录
        HisAdmissionDO admission = validateAdmissionInHospital(id);

        // 2. 更新状态为已出院
        HisAdmissionDO updateObj = new HisAdmissionDO();
        updateObj.setId(id);
        updateObj.setAdmissionStatus(2); // 已出院
        if (admission.getDischargeDate() == null) {
            updateObj.setDischargeDate(LocalDateTime.now());
        }

        admissionMapper.updateById(updateObj);

        // 3. 释放床位
        if (admission.getBedId() != null) {
            bedService.releaseBed(admission.getBedId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferDept(Long id, Long newDeptId, Long newWardId, Long newBedId) {
        // 1. 校验入院记录
        HisAdmissionDO admission = validateAdmissionInHospital(id);

        // 2. 释放原床位
        if (admission.getBedId() != null) {
            bedService.releaseBed(admission.getBedId());
        }

        // 3. 占用新床位
        if (newBedId != null) {
            bedService.occupyBed(newBedId, admission.getPatientId(), admission.getPatientName(), id);
        }

        // 4. 更新入院记录
        HisAdmissionDO updateObj = new HisAdmissionDO();
        updateObj.setId(id);
        updateObj.setAdmissionDept(newDeptId);
        updateObj.setWardId(newWardId);
        updateObj.setBedId(newBedId);

        admissionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBed(Long id, Long newBedId) {
        // 1. 校验入院记录
        HisAdmissionDO admission = validateAdmissionInHospital(id);

        // 2. 释放原床位
        if (admission.getBedId() != null) {
            bedService.releaseBed(admission.getBedId());
        }

        // 3. 占用新床位
        if (newBedId != null) {
            bedService.occupyBed(newBedId, admission.getPatientId(), admission.getPatientName(), id);
        }

        // 4. 更新床位信息
        HisBedDO newBed = bedService.getBed(newBedId);
        HisAdmissionDO updateObj = new HisAdmissionDO();
        updateObj.setId(id);
        updateObj.setBedId(newBedId);
        if (newBed != null) {
            updateObj.setBedNo(newBed.getBedNo());
            updateObj.setWardId(newBed.getWardId());
            updateObj.setWardName(newBed.getWardName());
        }

        admissionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long emergencyAdmission(Long patientId, Long deptId) {
        // 创建紧急入院记录
        HisAdmissionSaveReqVO createReqVO = new HisAdmissionSaveReqVO();
        createReqVO.setPatientId(patientId);
        createReqVO.setAdmissionDate(LocalDateTime.now());
        createReqVO.setAdmissionDept(deptId);
        createReqVO.setAttendingDoctor(0L); // 待分配
        createReqVO.setAdmissionWay(2); // 急诊
        createReqVO.setAdmissionCondition(2); // 急

        return createAdmission(createReqVO);
    }

    @Override
    public void addDeposit(Long id, BigDecimal amount) {
        // 校验入院记录
        HisAdmissionDO admission = validateAdmissionExists(id);

        // 更新预交金
        BigDecimal newDeposit = (admission.getDepositAmount() != null ? admission.getDepositAmount() : BigDecimal.ZERO)
                .add(amount);

        HisAdmissionDO updateObj = new HisAdmissionDO();
        updateObj.setId(id);
        updateObj.setDepositAmount(newDeposit);

        admissionMapper.updateById(updateObj);
    }

    @Override
    public void updateFee(Long id, BigDecimal fee) {
        // 校验入院记录
        validateAdmissionExists(id);

        // 更新费用
        HisAdmissionDO updateObj = new HisAdmissionDO();
        updateObj.setId(id);
        updateObj.setTotalFee(fee);

        admissionMapper.updateById(updateObj);
    }

    @Override
    public HisAdmissionDO validateAdmissionExists(Long id) {
        if (id == null) {
            return null;
        }
        HisAdmissionDO admission = admissionMapper.selectById(id);
        if (admission == null) {
            throw exception(ADMISSION_NOT_EXISTS);
        }
        return admission;
    }

    @Override
    public HisAdmissionDO validateAdmissionInHospital(Long id) {
        HisAdmissionDO admission = validateAdmissionExists(id);
        if (!admission.isInpatient()) {
            throw exception(ADMISSION_ALREADY_DISCHARGED);
        }
        return admission;
    }

    @Override
    public int countTodayAdmission() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return admissionMapper.selectByAdmissionDateBetween(startOfDay, endOfDay).size();
    }

    @Override
    public int countTodayDischarge() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return admissionMapper.selectByDischargeDateBetween(startOfDay, endOfDay).size();
    }

    @Override
    public int countInHospital() {
        Long count = admissionMapper.selectCount(admission -> admission
                .eq(HisAdmissionDO::getAdmissionStatus, 1));
        return count.intValue();
    }

    /**
     * 生成住院号
     * 格式: A + yyyyMMdd + 4位流水号
     */
    private String generateAdmissionNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("A%s%04d", dateStr, seq);
    }
}