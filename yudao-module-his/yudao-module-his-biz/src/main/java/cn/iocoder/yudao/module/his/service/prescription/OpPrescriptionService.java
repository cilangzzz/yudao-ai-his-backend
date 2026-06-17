package cn.iocoder.yudao.module.his.service.prescription;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prescription.vo.OpPrescriptionSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prescription.OpPrescriptionItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 门诊处方 Service 接口
 *
 * 提供处方的开立、审核、调配、发药、退药等全流程管理功能
 */
public interface OpPrescriptionService {

    // ==================== 处方基本操作 ====================

    /**
     * 开立处方
     *
     * @param createReqVO 处方信息
     * @return 处方ID
     */
    Long createPrescription(@Valid OpPrescriptionSaveReqVO createReqVO);

    /**
     * 更新处方
     *
     * 仅在开立状态下可修改
     *
     * @param updateReqVO 处方信息
     */
    void updatePrescription(@Valid OpPrescriptionSaveReqVO updateReqVO);

    /**
     * 删除处方
     *
     * 仅在开立状态下可删除
     *
     * @param id 处方ID
     */
    void deletePrescription(Long id);

    /**
     * 获取处方
     *
     * @param id 处方ID
     * @return 处方记录
     */
    OpPrescriptionDO getPrescription(Long id);

    /**
     * 根据处方编号获取处方
     *
     * @param prescriptionNo 处方编号
     * @return 处方记录
     */
    OpPrescriptionDO getPrescriptionByNo(String prescriptionNo);

    /**
     * 获取处方分页
     *
     * @param pageReqVO 分页查询条件
     * @return 处方分页结果
     */
    PageResult<OpPrescriptionDO> getPrescriptionPage(OpPrescriptionPageReqVO pageReqVO);

    /**
     * 获取处方的明细列表
     *
     * @param prescriptionId 处方ID
     * @return 处方明细列表
     */
    List<OpPrescriptionItemDO> getPrescriptionItems(Long prescriptionId);

    /**
     * 根据挂号ID获取处方列表
     *
     * @param registerId 挂号ID
     * @return 处方列表
     */
    List<OpPrescriptionDO> getPrescriptionsByRegisterId(Long registerId);

    /**
     * 根据患者ID获取处方列表
     *
     * @param patientId 患者ID
     * @return 处方列表
     */
    List<OpPrescriptionDO> getPrescriptionsByPatientId(Long patientId);

    // ==================== 处方审核 ====================

    /**
     * 审核处方（通过）
     *
     * @param id 处方ID
     * @param pharmacistId 药师ID
     * @param pharmacistName 药师姓名
     * @param remark 审核意见
     */
    void auditPass(Long id, Long pharmacistId, String pharmacistName, String remark);

    /**
     * 审核处方（退回）
     *
     * @param id 处方ID
     * @param pharmacistId 药师ID
     * @param pharmacistName 药师姓名
     * @param reason 退回原因
     */
    void auditReject(Long id, Long pharmacistId, String pharmacistName, String reason);

    // ==================== 调配发药 ====================

    /**
     * 调配处方
     *
     * @param id 处方ID
     * @param pharmacistId 药师ID
     * @param pharmacistName 药师姓名
     */
    void dispense(Long id, Long pharmacistId, String pharmacistName);

    /**
     * 发药
     *
     * @param id 处方ID
     * @param pharmacistId 药师ID
     * @param pharmacistName 药师姓名
     */
    void send(Long id, Long pharmacistId, String pharmacistName);

    /**
     * 批量发药
     *
     * @param prescriptionIds 处方ID列表
     * @param pharmacistId 药师ID
     * @param pharmacistName 药师姓名
     */
    void batchSend(List<Long> prescriptionIds, Long pharmacistId, String pharmacistName);

    // ==================== 退药 ====================

    /**
     * 退药
     *
     * @param id 处方ID
     * @param reason 退药原因
     */
    void returnDrug(Long id, String reason);

    /**
     * 退药（带操作人）
     *
     * @param id 处方ID
     * @param pharmacistId 药师ID
     * @param pharmacistName 药师姓名
     * @param reason 退药原因
     */
    void returnDrug(Long id, Long pharmacistId, String pharmacistName, String reason);

    // ==================== 皮试管理 ====================

    /**
     * 记录皮试结果
     *
     * @param itemId 处方明细ID
     * @param skinTestResult 皮试结果
     * @param nurseId 护士ID
     */
    void recordSkinTest(Long itemId, String skinTestResult, Long nurseId);

    /**
     * 获取待皮试的处方明细列表
     *
     * @param prescriptionId 处方ID
     * @return 待皮试的明细列表
     */
    List<OpPrescriptionItemDO> getPendingSkinTestItems(Long prescriptionId);

    // ==================== 查询统计 ====================

    /**
     * 获取待审核处方列表
     *
     * @param deptId 科室ID（可选）
     * @return 待审核处方列表
     */
    List<OpPrescriptionDO> getPendingAuditList(Long deptId);

    /**
     * 获取待调配处方列表
     *
     * @param deptId 科室ID（可选）
     * @return 待调配处方列表
     */
    List<OpPrescriptionDO> getPendingDispenseList(Long deptId);

    /**
     * 获取待发药处方列表
     *
     * @param deptId 科室ID（可选）
     * @return 待发药处方列表
     */
    List<OpPrescriptionDO> getPendingSendList(Long deptId);

    /**
     * 统计处方数量（按状态）
     *
     * @param deptId 科室ID
     * @param status 处方状态
     * @return 数量
     */
    Long countByStatus(Long deptId, Integer status);

    // ==================== 校验方法 ====================

    /**
     * 校验处方是否存在
     *
     * @param id 处方ID
     * @return 处方记录
     */
    OpPrescriptionDO validatePrescriptionExists(Long id);

    /**
     * 校验处方是否可以修改
     *
     * @param id 处方ID
     * @return 处方记录
     */
    OpPrescriptionDO validatePrescriptionCanUpdate(Long id);

    /**
     * 校验处方是否可以审核
     *
     * @param id 处方ID
     * @return 处方记录
     */
    OpPrescriptionDO validatePrescriptionCanAudit(Long id);

    /**
     * 校验处方是否可以调配
     *
     * @param id 处方ID
     * @return 处方记录
     */
    OpPrescriptionDO validatePrescriptionCanDispense(Long id);

    /**
     * 校验处方是否可以发药
     *
     * @param id 处方ID
     * @return 处方记录
     */
    OpPrescriptionDO validatePrescriptionCanSend(Long id);

    /**
     * 校验处方是否可以退药
     *
     * @param id 处方ID
     * @return 处方记录
     */
    OpPrescriptionDO validatePrescriptionCanReturn(Long id);

}
