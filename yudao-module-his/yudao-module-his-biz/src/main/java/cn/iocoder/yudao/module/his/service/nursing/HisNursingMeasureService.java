package cn.iocoder.yudao.module.his.service.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingMeasureEvaluationReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingMeasurePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingMeasureSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingMeasureDO;

import java.util.List;

/**
 * 护理措施 Service 接口
 */
public interface HisNursingMeasureService {

    /**
     * 创建护理措施
     */
    Long createNursingMeasure(HisNursingMeasureSaveReqVO createReqVO);

    /**
     * 更新护理措施
     */
    void updateNursingMeasure(HisNursingMeasureSaveReqVO updateReqVO);

    /**
     * 删除护理措施
     */
    void deleteNursingMeasure(Long id);

    /**
     * 获取护理措施
     */
    HisNursingMeasureDO getNursingMeasure(Long id);

    /**
     * 分页查询护理措施
     */
    PageResult<HisNursingMeasureDO> getNursingMeasurePage(HisNursingMeasurePageReqVO pageReqVO);

    /**
     * 按住院ID查询护理措施列表
     */
    List<HisNursingMeasureDO> getNursingMeasureListByAdmission(Long admissionId);

    /**
     * 按评估ID查询护理措施列表
     */
    List<HisNursingMeasureDO> getNursingMeasureListByAssessment(Long assessmentId);

    /**
     * 开始执行护理措施
     */
    void startNursingMeasure(Long id);

    /**
     * 完成护理措施
     */
    void completeNursingMeasure(Long id);

    /**
     * 停止护理措施
     */
    void stopNursingMeasure(Long id, String reason);

    /**
     * 效果评价
     */
    void evaluateNursingMeasure(HisNursingMeasureEvaluationReqVO evaluationReqVO);

    /**
     * 校验护理措施是否存在
     */
    HisNursingMeasureDO validateNursingMeasureExists(Long id);
}