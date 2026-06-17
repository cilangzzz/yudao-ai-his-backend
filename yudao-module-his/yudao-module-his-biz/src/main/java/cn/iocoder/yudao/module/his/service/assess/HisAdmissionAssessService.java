package cn.iocoder.yudao.module.his.service.assess;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.assess.vo.HisAdmissionAssessPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.assess.vo.HisAdmissionAssessSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.assess.HisAdmissionAssessDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 入院评估 Service 接口
 *
 * @author yudao
 */
public interface HisAdmissionAssessService {

    /**
     * 创建入院评估
     */
    Long createAssess(@Valid HisAdmissionAssessSaveReqVO createReqVO);

    /**
     * 更新入院评估
     */
    void updateAssess(@Valid HisAdmissionAssessSaveReqVO updateReqVO);

    /**
     * 删除入院评估
     */
    void deleteAssess(Long id);

    /**
     * 获得入院评估
     */
    HisAdmissionAssessDO getAssess(Long id);

    /**
     * 获得入院评估分页
     */
    PageResult<HisAdmissionAssessDO> getAssessPage(HisAdmissionAssessPageReqVO pageReqVO);

    /**
     * 获得住院记录的所有评估
     */
    List<HisAdmissionAssessDO> getAssessListByAdmission(Long admissionId);

    /**
     * 获得患者的所有评估
     */
    List<HisAdmissionAssessDO> getAssessListByPatient(Long patientId);

    /**
     * 获得住院记录的最新评估（按类型）
     */
    HisAdmissionAssessDO getLatestAssess(Long admissionId, Integer assessType);

    /**
     * 校验入院评估是否存在
     */
    HisAdmissionAssessDO validateAssessExists(Long id);
}