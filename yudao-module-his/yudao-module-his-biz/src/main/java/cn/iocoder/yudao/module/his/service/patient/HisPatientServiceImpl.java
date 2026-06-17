package cn.iocoder.yudao.module.his.service.patient;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import cn.iocoder.yudao.module.his.dal.mysql.patient.HisPatientMapper;
import cn.iocoder.yudao.module.his.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 患者 Service 实现类
 */
@Service
@Validated
public class HisPatientServiceImpl implements HisPatientService {

    @Resource
    private HisPatientMapper patientMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPatient(HisPatientSaveReqVO createReqVO) {
        // 1. 校验身份证号唯一性
        if (createReqVO.getIdCardNo() != null && !createReqVO.getIdCardNo().isEmpty()) {
            HisPatientDO existPatient = patientMapper.selectByIdCardNo(createReqVO.getIdCardNo());
            if (existPatient != null) {
                throw exception(PATIENT_ID_CARD_DUPLICATE);
            }
        }

        // 2. 插入患者
        HisPatientDO patient = BeanUtils.toBean(createReqVO, HisPatientDO.class);
        // 生成患者编号: P + yyyyMMdd + 4位流水
        patient.setPatientNo(generatePatientNo());
        // 设置默认状态
        if (patient.getStatus() == null) {
            patient.setStatus(1);
        }
        // 计算年龄
        if (patient.getBirthDate() != null) {
            calculateAge(patient);
        }
        patientMapper.insert(patient);

        return patient.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePatient(HisPatientSaveReqVO updateReqVO) {
        // 1. 校验存在
        validatePatientExists(updateReqVO.getId());

        // 2. 校验身份证号唯一性
        if (updateReqVO.getIdCardNo() != null && !updateReqVO.getIdCardNo().isEmpty()) {
            HisPatientDO existPatient = patientMapper.selectByIdCardNo(updateReqVO.getIdCardNo());
            if (existPatient != null && !existPatient.getId().equals(updateReqVO.getId())) {
                throw exception(PATIENT_ID_CARD_DUPLICATE);
            }
        }

        // 3. 更新患者
        HisPatientDO updateObj = BeanUtils.toBean(updateReqVO, HisPatientDO.class);
        // 计算年龄
        if (updateObj.getBirthDate() != null) {
            calculateAge(updateObj);
        }
        patientMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePatient(Long id) {
        // 1. 校验存在
        validatePatientExists(id);

        // 2. 删除
        patientMapper.deleteById(id);
    }

    @Override
    public HisPatientDO getPatient(Long id) {
        return patientMapper.selectById(id);
    }

    @Override
    public PageResult<HisPatientDO> getPatientPage(HisPatientPageReqVO pageReqVO) {
        return patientMapper.selectPage(pageReqVO);
    }

    @Override
    public HisPatientDO getPatientByIdCardNo(String idCardNo) {
        return patientMapper.selectByIdCardNo(idCardNo);
    }

    @Override
    public HisPatientDO getPatientByPatientNo(String patientNo) {
        return patientMapper.selectByPatientNo(patientNo);
    }

    @Override
    public HisPatientDO validatePatientExists(Long id) {
        if (id == null) {
            return null;
        }
        HisPatientDO patient = patientMapper.selectById(id);
        if (patient == null) {
            throw exception(PATIENT_NOT_EXISTS);
        }
        return patient;
    }

    /**
     * 生成患者编号
     * 格式: P + yyyyMMdd + 4位流水号
     */
    private String generatePatientNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 这里简化处理，实际应该用分布式ID或数据库序列
        long seq = System.currentTimeMillis() % 10000;
        return String.format("P%s%04d", dateStr, seq);
    }

    /**
     * 计算年龄
     */
    private void calculateAge(HisPatientDO patient) {
        if (patient.getBirthDate() == null) {
            return;
        }
        LocalDate today = LocalDate.now();
        LocalDate birthDate = patient.getBirthDate();
        int age = today.getYear() - birthDate.getYear();
        if (today.getMonthValue() < birthDate.getMonthValue() ||
            (today.getMonthValue() == birthDate.getMonthValue() && today.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }
        patient.setAge(age);
        patient.setAgeUnit(1); // 默认岁
    }

}