package cn.iocoder.yudao.module.his.service.exam;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamRequestPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamRequestSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestDO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 检查申请 Service 接口
 */
public interface HisExamRequestService {

    /**
     * 创建检查申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createExamRequest(@Valid HisExamRequestSaveReqVO createReqVO);

    /**
     * 更新检查申请
     *
     * @param updateReqVO 更新信息
     */
    void updateExamRequest(@Valid HisExamRequestSaveReqVO updateReqVO);

    /**
     * 删除检查申请
     *
     * @param id 编号
     */
    void deleteExamRequest(Long id);

    /**
     * 获得检查申请
     *
     * @param id 编号
     * @return 检查申请
     */
    HisExamRequestDO getExamRequest(Long id);

    /**
     * 获得检查申请(含明细)
     *
     * @param id 编号
     * @return 检查申请(含明细)
     */
    HisExamRequestDO getExamRequestWithItems(Long id);

    /**
     * 获得检查申请分页
     *
     * @param pageReqVO 分页查询
     * @return 检查申请分页
     */
    PageResult<HisExamRequestDO> getExamRequestPage(HisExamRequestPageReqVO pageReqVO);

    /**
     * 根据患者ID获取检查申请列表
     *
     * @param patientId 患者ID
     * @return 检查申请列表
     */
    List<HisExamRequestDO> getExamRequestListByPatientId(Long patientId);

    /**
     * 根据就诊ID获取检查申请列表
     *
     * @param encounterId 就诊ID
     * @return 检查申请列表
     */
    List<HisExamRequestDO> getExamRequestListByEncounterId(Long encounterId);

    /**
     * 获取检查申请明细列表
     *
     * @param requestId 申请ID
     * @return 明细列表
     */
    List<HisExamRequestItemDO> getExamRequestItemList(Long requestId);

    /**
     * 预约检查
     *
     * @param id 申请ID
     * @param appointmentTime 预约时间
     */
    void appointExamRequest(Long id, java.time.LocalDateTime appointmentTime);

    /**
     * 签到
     *
     * @param id 申请ID
     */
    void checkInExamRequest(Long id);

    /**
     * 开始检查
     *
     * @param id 申请ID
     */
    void startExamRequest(Long id);

    /**
     * 完成检查
     *
     * @param id 申请ID
     */
    void completeExamRequest(Long id);

    /**
     * 取消检查申请
     *
     * @param id 申请ID
     * @param cancelReason 取消原因
     */
    void cancelExamRequest(Long id, String cancelReason);

    /**
     * 校验检查申请是否存在
     *
     * @param id 编号
     * @return 检查申请
     */
    HisExamRequestDO validateExamRequestExists(Long id);

}
