package cn.iocoder.yudao.module.his.service.discharge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeSummaryPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeSummaryReviewReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeSummarySaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeSummaryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 出院小结 Service 接口
 *
 * @author yudao
 */
public interface HisDischargeSummaryService {

    /**
     * 创建出院小结
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDischargeSummary(@Valid HisDischargeSummarySaveReqVO createReqVO);

    /**
     * 更新出院小结
     *
     * @param updateReqVO 更新信息
     */
    void updateDischargeSummary(@Valid HisDischargeSummarySaveReqVO updateReqVO);

    /**
     * 删除出院小结
     *
     * @param id 编号
     */
    void deleteDischargeSummary(Long id);

    /**
     * 获得出院小结
     *
     * @param id 编号
     * @return 出院小结
     */
    HisDischargeSummaryDO getDischargeSummary(Long id);

    /**
     * 根据出院ID获得出院小结
     *
     * @param dischargeId 出院ID
     * @return 出院小结
     */
    HisDischargeSummaryDO getDischargeSummaryByDischargeId(Long dischargeId);

    /**
     * 根据住院ID获得出院小结
     *
     * @param admissionId 住院ID
     * @return 出院小结
     */
    HisDischargeSummaryDO getDischargeSummaryByAdmissionId(Long admissionId);

    /**
     * 获得出院小结分页
     *
     * @param pageReqVO 分页查询
     * @return 出院小结分页
     */
    PageResult<HisDischargeSummaryDO> getDischargeSummaryPage(HisDischargeSummaryPageReqVO pageReqVO);

    /**
     * 获得患者的出院小结列表
     *
     * @param patientId 患者ID
     * @return 出院小结列表
     */
    List<HisDischargeSummaryDO> getDischargeSummaryListByPatientId(Long patientId);

    /**
     * 获得待审核的出院小结列表
     *
     * @return 出院小结列表
     */
    List<HisDischargeSummaryDO> getPendingReviewList();

    /**
     * 审核出院小结
     *
     * @param reqVO 审核信息
     */
    void reviewDischargeSummary(@Valid HisDischargeSummaryReviewReqVO reqVO);

    /**
     * 撤回审核
     *
     * @param id 出院小结ID
     */
    void withdrawReview(Long id);

    /**
     * 校验出院小结是否存在
     *
     * @param id 编号
     * @return 出院小结
     */
    HisDischargeSummaryDO validateDischargeSummaryExists(Long id);

    /**
     * 校验出院小结是否可以修改
     *
     * @param id 编号
     * @return 出院小结
     */
    HisDischargeSummaryDO validateDischargeSummaryCanModify(Long id);

    /**
     * 校验出院小结是否可以审核
     *
     * @param id 编号
     * @return 出院小结
     */
    HisDischargeSummaryDO validateDischargeSummaryCanReview(Long id);

    /**
     * 统计待审核数量
     *
     * @return 数量
     */
    int countPendingReview();
}