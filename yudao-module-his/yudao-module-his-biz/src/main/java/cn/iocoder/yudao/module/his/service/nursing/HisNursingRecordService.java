package cn.iocoder.yudao.module.his.service.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingAssessmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingAssessmentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingRecordDO;

import java.util.List;

/**
 * 护理记录 Service 接口
 */
public interface HisNursingRecordService {

    /**
     * 创建护理记录
     */
    Long createNursingRecord(HisNursingRecordSaveReqVO createReqVO);

    /**
     * 更新护理记录
     */
    void updateNursingRecord(HisNursingRecordSaveReqVO updateReqVO);

    /**
     * 删除护理记录
     */
    void deleteNursingRecord(Long id);

    /**
     * 获取护理记录
     */
    HisNursingRecordDO getNursingRecord(Long id);

    /**
     * 分页查询护理记录
     */
    PageResult<HisNursingRecordDO> getNursingRecordPage(HisNursingRecordPageReqVO pageReqVO);

    /**
     * 按住院ID查询护理记录列表
     */
    List<HisNursingRecordDO> getNursingRecordListByAdmission(Long admissionId);

    /**
     * 签名护理记录
     */
    void signNursingRecord(Long id);

    /**
     * 审核护理记录
     */
    void auditNursingRecord(Long id, Long auditNurseId);

    /**
     * 校验护理记录是否存在
     */
    HisNursingRecordDO validateNursingRecordExists(Long id);

    // ==================== 护理评估相关 ====================

    /**
     * 创建护理评估
     */
    Long createNursingAssessment(HisNursingAssessmentSaveReqVO createReqVO);

    /**
     * 更新护理评估
     */
    void updateNursingAssessment(HisNursingAssessmentSaveReqVO updateReqVO);

    /**
     * 删除护理评估
     */
    void deleteNursingAssessment(Long id);

    /**
     * 获取护理评估
     */
    HisNursingAssessmentDO getNursingAssessment(Long id);

    /**
     * 分页查询护理评估
     */
    PageResult<HisNursingAssessmentDO> getNursingAssessmentPage(HisNursingAssessmentPageReqVO pageReqVO);

    /**
     * 按住院ID查询护理评估列表
     */
    List<HisNursingAssessmentDO> getNursingAssessmentListByAdmission(Long admissionId);

    /**
     * 获取最新的护理评估
     */
    HisNursingAssessmentDO getLatestNursingAssessment(Long admissionId, Integer assessmentType);

    /**
     * 校验护理评估是否存在
     */
    HisNursingAssessmentDO validateNursingAssessmentExists(Long id);
}