package cn.iocoder.yudao.module.his.service.lab;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisLabRequestPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisLabRequestSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestDO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 检验申请 Service 接口
 *
 * @author yudao
 */
public interface HisLabRequestService {

    /**
     * 创建检验申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLabRequest(@Valid HisLabRequestSaveReqVO createReqVO);

    /**
     * 更新检验申请
     *
     * @param updateReqVO 更新信息
     */
    void updateLabRequest(@Valid HisLabRequestSaveReqVO updateReqVO);

    /**
     * 删除检验申请
     *
     * @param id 编号
     */
    void deleteLabRequest(Long id);

    /**
     * 获得检验申请
     *
     * @param id 编号
     * @return 检验申请
     */
    HisLabRequestDO getLabRequest(Long id);

    /**
     * 获得检验申请分页
     *
     * @param pageReqVO 分页查询
     * @return 检验申请分页
     */
    PageResult<HisLabRequestDO> getLabRequestPage(HisLabRequestPageReqVO pageReqVO);

    /**
     * 根据患者ID获取检验申请列表
     *
     * @param patientId 患者ID
     * @return 检验申请列表
     */
    List<HisLabRequestDO> getLabRequestListByPatientId(Long patientId);

    /**
     * 根据就诊ID获取检验申请列表
     *
     * @param sourceId 就诊ID
     * @return 检验申请列表
     */
    List<HisLabRequestDO> getLabRequestListBySourceId(Long sourceId);

    /**
     * 获取检验申请明细列表
     *
     * @param requestId 申请ID
     * @return 明细列表
     */
    List<HisLabRequestItemDO> getLabRequestItemList(Long requestId);

    // ========== 标本采集 ==========

    /**
     * 采集标本
     *
     * @param id 申请ID
     * @param specimenBarcode 标本条码
     * @param collectorId 采集人ID
     * @param collectorName 采集人姓名
     */
    void collectSpecimen(Long id, String specimenBarcode, Long collectorId, String collectorName);

    /**
     * 接收标本
     *
     * @param id 申请ID
     * @param receiverId 接收人ID
     * @param receiverName 接收人姓名
     */
    void receiveSpecimen(Long id, Long receiverId, String receiverName);

    /**
     * 拒收标本
     *
     * @param id 申请ID
     * @param rejectReason 拒收原因
     */
    void rejectSpecimen(Long id, String rejectReason);

    // ========== 检验执行 ==========

    /**
     * 开始检验
     *
     * @param id 申请ID
     * @param technicianId 技师ID
     * @param technicianName 技师姓名
     */
    void startLabRequest(Long id, Long technicianId, String technicianName);

    /**
     * 完成检验
     *
     * @param id 申请ID
     */
    void completeLabRequest(Long id);

    // ========== 报告管理 ==========

    /**
     * 生成报告
     *
     * @param id 申请ID
     * @param reportDoctorId 报告医生ID
     * @param reportDoctorName 报告医生姓名
     * @return 报告编号
     */
    String generateReport(Long id, Long reportDoctorId, String reportDoctorName);

    /**
     * 审核报告
     *
     * @param id 申请ID
     * @param auditDoctorId 审核医生ID
     * @param auditDoctorName 审核医生姓名
     */
    void auditReport(Long id, Long auditDoctorId, String auditDoctorName);

    /**
     * 发布报告
     *
     * @param id 申请ID
     */
    void publishReport(Long id);

    // ========== 危急值管理 ==========

    /**
     * 报告危急值
     *
     * @param id 申请ID
     * @param criticalValueContent 危急值内容
     */
    void reportCriticalValue(Long id, String criticalValueContent);

    /**
     * 确认危急值
     *
     * @param id 申请ID
     * @param confirmUserId 确认人ID
     * @param confirmUserName 认人姓名
     */
    void confirmCriticalValue(Long id, Long confirmUserId, String confirmUserName);

    // ========== 取消申请 ==========

    /**
     * 取消检验申请
     *
     * @param id 申请ID
     * @param cancelReason 取消原因
     */
    void cancelLabRequest(Long id, String cancelReason);

    /**
     * 校验检验申请是否存在
     *
     * @param id 编号
     * @return 检验申请
     */
    HisLabRequestDO validateLabRequestExists(Long id);

    /**
     * 获取待采集的申请列表
     *
     * @return 待采集申请列表
     */
    List<HisLabRequestDO> getPendingCollectList();

    /**
     * 获取检验中的申请列表
     *
     * @return 检验中申请列表
     */
    List<HisLabRequestDO> getInProgressList();

    /**
     * 获取危急值未确认的申请列表
     *
     * @return 危急值未确认申请列表
     */
    List<HisLabRequestDO> getCriticalValueUnconfirmedList();

    /**
     * 根据标本条码查询申请
     *
     * @param specimenBarcode 标本条码
     * @return 检验申请
     */
    HisLabRequestDO getLabRequestBySpecimenBarcode(String specimenBarcode);

}