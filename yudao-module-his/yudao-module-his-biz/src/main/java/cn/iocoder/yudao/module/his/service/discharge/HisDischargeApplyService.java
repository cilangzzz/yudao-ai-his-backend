package cn.iocoder.yudao.module.his.service.discharge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplyApproveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplyPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplyRejectReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplySaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeApplyDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 出院申请 Service 接口
 *
 * @author yudao
 */
public interface HisDischargeApplyService {

    /**
     * 创建出院申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDischargeApply(@Valid HisDischargeApplySaveReqVO createReqVO);

    /**
     * 更新出院申请
     *
     * @param updateReqVO 更新信息
     */
    void updateDischargeApply(@Valid HisDischargeApplySaveReqVO updateReqVO);

    /**
     * 删除出院申请
     *
     * @param id 编号
     */
    void deleteDischargeApply(Long id);

    /**
     * 获得出院申请
     *
     * @param id 编号
     * @return 出院申请
     */
    HisDischargeApplyDO getDischargeApply(Long id);

    /**
     * 根据申请单号获得出院申请
     *
     * @param applyNo 申请单号
     * @return 出院申请
     */
    HisDischargeApplyDO getDischargeApplyByNo(String applyNo);

    /**
     * 根据入院记录ID获得出院申请
     *
     * @param admissionId 入院记录ID
     * @return 出院申请
     */
    HisDischargeApplyDO getDischargeApplyByAdmissionId(Long admissionId);

    /**
     * 获得出院申请分页
     *
     * @param pageReqVO 分页查询
     * @return 出院申请分页
     */
    PageResult<HisDischargeApplyDO> getDischargeApplyPage(HisDischargeApplyPageReqVO pageReqVO);

    /**
     * 获得患者的出院申请列表
     *
     * @param patientId 患者ID
     * @return 出院申请列表
     */
    List<HisDischargeApplyDO> getDischargeApplyListByPatientId(Long patientId);

    /**
     * 获得科室待审批出院申请列表
     *
     * @param deptId 科室ID
     * @return 出院申请列表
     */
    List<HisDischargeApplyDO> getPendingDischargeApplyListByDept(Long deptId);

    /**
     * 获得待审批出院申请列表
     *
     * @return 出院申请列表
     */
    List<HisDischargeApplyDO> getPendingDischargeApplyList();

    /**
     * 审批出院申请
     *
     * @param reqVO 审批信息
     */
    void approveDischargeApply(@Valid HisDischargeApplyApproveReqVO reqVO);

    /**
     * 驳回出院申请
     *
     * @param reqVO 驳回信息
     */
    void rejectDischargeApply(@Valid HisDischargeApplyRejectReqVO reqVO);

    /**
     * 取消出院申请
     *
     * @param id 编号
     * @param cancelReason 取消原因
     */
    void cancelDischargeApply(Long id, String cancelReason);

    /**
     * 确认出院（执行出院）
     *
     * @param id 编号
     */
    void confirmDischarge(Long id);

    /**
     * 停止医嘱
     *
     * @param id 编号
     */
    void stopOrders(Long id);

    /**
     * 校验出院申请是否存在
     *
     * @param id 编号
     * @return 出院申请
     */
    HisDischargeApplyDO validateDischargeApplyExists(Long id);

    /**
     * 校验出院申请是否可以审批
     *
     * @param id 编号
     * @return 出院申请
     */
    HisDischargeApplyDO validateDischargeApplyCanApprove(Long id);

    /**
     * 校验出院申请是否可以出院
     *
     * @param id 编号
     * @return 出院申请
     */
    HisDischargeApplyDO validateDischargeApplyCanDischarge(Long id);

    /**
     * 获取今日出院申请数量
     *
     * @return 数量
     */
    int countTodayApply();

    /**
     * 获取待审批数量
     *
     * @return 数量
     */
    int countPending();

}