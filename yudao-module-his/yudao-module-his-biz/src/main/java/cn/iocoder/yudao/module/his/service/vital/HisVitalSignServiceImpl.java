package cn.iocoder.yudao.module.his.service.vital;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.vital.HisVitalSignDO;
import cn.iocoder.yudao.module.his.dal.mysql.vital.HisVitalSignMapper;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.VITAL_SIGN_NOT_EXISTS;

/**
 * 生命体征 Service 实现类
 */
@Service
@Validated
public class HisVitalSignServiceImpl implements HisVitalSignService {

    @Resource
    private HisVitalSignMapper vitalSignMapper;

    @Resource
    private HisAdmissionService admissionService;

    @Override
    public Long createVitalSign(HisVitalSignSaveReqVO createReqVO) {
        // 校验住院记录
        HisAdmissionDO admission = admissionService.validateAdmissionExists(createReqVO.getAdmissionId());

        // 创建生命体征记录
        HisVitalSignDO vitalSign = BeanUtils.toBean(createReqVO, HisVitalSignDO.class);
        vitalSign.setPatientId(admission.getPatientId());
        vitalSign.setPatientName(admission.getPatientName());

        // 计算BMI
        if (vitalSign.getWeight() != null && vitalSign.getHeight() != null) {
            vitalSign.setBmi(vitalSign.calculateBmi());
        }

        // 检测异常
        detectAbnormal(vitalSign);

        vitalSignMapper.insert(vitalSign);
        return vitalSign.getId();
    }

    @Override
    public void updateVitalSign(HisVitalSignSaveReqVO updateReqVO) {
        validateVitalSignExists(updateReqVO.getId());

        HisVitalSignDO updateObj = BeanUtils.toBean(updateReqVO, HisVitalSignDO.class);

        // 计算BMI
        if (updateObj.getWeight() != null && updateObj.getHeight() != null) {
            updateObj.setBmi(updateObj.calculateBmi());
        }

        // 检测异常
        detectAbnormal(updateObj);

        vitalSignMapper.updateById(updateObj);
    }

    @Override
    public void deleteVitalSign(Long id) {
        validateVitalSignExists(id);
        vitalSignMapper.deleteById(id);
    }

    @Override
    public HisVitalSignDO getVitalSign(Long id) {
        return vitalSignMapper.selectById(id);
    }

    @Override
    public PageResult<HisVitalSignDO> getVitalSignPage(HisVitalSignPageReqVO pageReqVO) {
        return vitalSignMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisVitalSignDO> getVitalSignListByAdmission(Long admissionId) {
        return vitalSignMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public List<HisVitalSignDO> getVitalSignListByPatient(Long patientId) {
        return vitalSignMapper.selectListByPatientId(patientId);
    }

    @Override
    public HisVitalSignDO getLatestVitalSign(Long admissionId) {
        return vitalSignMapper.selectLatest(admissionId);
    }

    @Override
    public List<HisVitalSignDO> getAbnormalVitalSignList(Long admissionId) {
        return vitalSignMapper.selectAbnormalList(admissionId);
    }

    @Override
    public HisVitalSignDO validateVitalSignExists(Long id) {
        if (id == null) {
            return null;
        }
        HisVitalSignDO vitalSign = vitalSignMapper.selectById(id);
        if (vitalSign == null) {
            throw exception(VITAL_SIGN_NOT_EXISTS);
        }
        return vitalSign;
    }

    /**
     * 检测异常生命体征
     */
    private void detectAbnormal(HisVitalSignDO vitalSign) {
        List<String> abnormalItems = new ArrayList<>();

        if (vitalSign.isTemperatureAbnormal()) {
            abnormalItems.add("体温");
        }
        if (vitalSign.isBloodPressureAbnormal()) {
            abnormalItems.add("血压");
        }
        if (vitalSign.isPulseAbnormal()) {
            abnormalItems.add("脉搏");
        }
        if (vitalSign.isRespirationAbnormal()) {
            abnormalItems.add("呼吸");
        }
        if (vitalSign.isOxygenSaturationAbnormal()) {
            abnormalItems.add("血氧");
        }

        if (!abnormalItems.isEmpty()) {
            vitalSign.setAbnormalFlag(1);
            vitalSign.setAbnormalItems(String.join("、", abnormalItems));
        } else {
            vitalSign.setAbnormalFlag(0);
            vitalSign.setAbnormalItems(null);
        }
    }
}