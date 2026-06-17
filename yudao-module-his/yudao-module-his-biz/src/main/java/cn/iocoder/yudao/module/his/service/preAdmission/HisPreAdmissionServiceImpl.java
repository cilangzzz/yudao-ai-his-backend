package cn.iocoder.yudao.module.his.service.preAdmission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.HisAdmissionSaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo.HisPreAdmissionPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo.HisPreAdmissionSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import cn.iocoder.yudao.module.his.dal.dataobject.preAdmission.HisPreAdmissionDO;
import cn.iocoder.yudao.module.his.dal.mysql.preAdmission.HisPreAdmissionMapper;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import cn.iocoder.yudao.module.his.service.patient.HisPatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 预住院 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisPreAdmissionServiceImpl implements HisPreAdmissionService {

    @Resource
    private HisPreAdmissionMapper preAdmissionMapper;

    @Resource
    private HisPatientService patientService;

    @Resource
    private HisAdmissionService admissionService;

    @Override
    public Long createPreAdmission(HisPreAdmissionSaveReqVO createReqVO) {
        // 校验患者
        HisPatientDO patient = patientService.getPatient(createReqVO.getPatientId());
        if (patient == null) {
            throw exception(PATIENT_NOT_EXISTS);
        }

        // 创建预住院记录
        HisPreAdmissionDO preAdmission = BeanUtils.toBean(createReqVO, HisPreAdmissionDO.class);
        preAdmission.setPreAdmissionNo(generatePreAdmissionNo());
        preAdmission.setPatientName(patient.getName());
        preAdmission.setPatientPhone(patient.getPhone());
        preAdmission.setIdCardNo(patient.getIdCardNo());
        preAdmission.setStatus(1); // 待入院

        preAdmissionMapper.insert(preAdmission);
        return preAdmission.getId();
    }

    @Override
    public void updatePreAdmission(HisPreAdmissionSaveReqVO updateReqVO) {
        validatePreAdmissionExists(updateReqVO.getId());
        HisPreAdmissionDO updateObj = BeanUtils.toBean(updateReqVO, HisPreAdmissionDO.class);
        preAdmissionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelPreAdmission(Long id, String reason) {
        HisPreAdmissionDO preAdmission = validatePreAdmissionPending(id);

        HisPreAdmissionDO updateObj = new HisPreAdmissionDO();
        updateObj.setId(id);
        updateObj.setStatus(3); // 已取消
        updateObj.setCancelReason(reason);
        updateObj.setCancelTime(LocalDateTime.now());

        preAdmissionMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long convertToAdmission(Long id) {
        // 1. 校验预住院记录
        HisPreAdmissionDO preAdmission = validatePreAdmissionPending(id);

        // 2. 创建正式入院记录
        HisAdmissionSaveReqVO admissionReqVO = new HisAdmissionSaveReqVO();
        admissionReqVO.setPatientId(preAdmission.getPatientId());
        admissionReqVO.setRegisterId(preAdmission.getRegisterId());
        admissionReqVO.setAdmissionDate(LocalDateTime.now());
        admissionReqVO.setAdmissionDept(preAdmission.getDeptId());
        admissionReqVO.setAdmissionDiagnosis(preAdmission.getDiagnosis());
        admissionReqVO.setDiagnosisCode(preAdmission.getDiagnosisCode());
        admissionReqVO.setAttendingDoctor(preAdmission.getDoctorId() != null ? preAdmission.getDoctorId() : 0L);
        admissionReqVO.setAdmissionWay(1); // 门诊入院
        admissionReqVO.setAdmissionCondition(3); // 一般
        admissionReqVO.setDepositAmount(preAdmission.getDepositAmount());

        Long admissionId = admissionService.createAdmission(admissionReqVO);

        // 3. 更新预住院状态
        HisPreAdmissionDO updateObj = new HisPreAdmissionDO();
        updateObj.setId(id);
        updateObj.setStatus(2); // 已入院
        updateObj.setAdmissionTime(LocalDateTime.now());
        updateObj.setAdmissionId(admissionId);

        preAdmissionMapper.updateById(updateObj);

        return admissionId;
    }

    @Override
    public void deletePreAdmission(Long id) {
        HisPreAdmissionDO preAdmission = validatePreAdmissionExists(id);
        if (preAdmission.isAdmitted()) {
            throw exception(ADMISSION_ALREADY_DISCHARGED);
        }
        preAdmissionMapper.deleteById(id);
    }

    @Override
    public HisPreAdmissionDO getPreAdmission(Long id) {
        return preAdmissionMapper.selectById(id);
    }

    @Override
    public PageResult<HisPreAdmissionDO> getPreAdmissionPage(HisPreAdmissionPageReqVO pageReqVO) {
        return preAdmissionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisPreAdmissionDO> getPendingList() {
        return preAdmissionMapper.selectPendingList();
    }

    @Override
    public List<HisPreAdmissionDO> getPendingListByDept(Long deptId) {
        return preAdmissionMapper.selectPendingListByDept(deptId);
    }

    @Override
    public HisPreAdmissionDO validatePreAdmissionExists(Long id) {
        if (id == null) {
            return null;
        }
        HisPreAdmissionDO preAdmission = preAdmissionMapper.selectById(id);
        if (preAdmission == null) {
            throw exception(ADMISSION_NOT_EXISTS);
        }
        return preAdmission;
    }

    @Override
    public HisPreAdmissionDO validatePreAdmissionPending(Long id) {
        HisPreAdmissionDO preAdmission = validatePreAdmissionExists(id);
        if (!preAdmission.isPending()) {
            throw exception(ADMISSION_ALREADY_DISCHARGED);
        }
        return preAdmission;
    }

    /**
     * 生成预住院编号
     */
    private String generatePreAdmissionNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("P%s%04d", dateStr, seq);
    }
}