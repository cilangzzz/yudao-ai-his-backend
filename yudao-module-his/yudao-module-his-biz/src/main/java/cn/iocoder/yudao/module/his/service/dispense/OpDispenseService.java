package cn.iocoder.yudao.module.his.service.dispense;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispensePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.dispense.vo.OpDispenseSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseDO;
import cn.iocoder.yudao.module.his.dal.dataobject.dispense.OpDispenseItemDO;

import java.util.List;

/**
 * 门诊发药管理 Service 接口
 *
 * 提供发药单的创建、调配、发药、退药等全流程管理功能
 */
public interface OpDispenseService {

    // ==================== 发药单基本操作 ====================

    /**
     * 创建发药单（处方审核通过后自动创建）
     *
     * @param createReqVO 创建信息
     * @return 发药单ID
     */
    Long createDispense(OpDispenseSaveReqVO createReqVO);

    /**
     * 根据处方创建发药单
     *
     * @param prescriptionId 处方ID
     * @param pharmacyId     药房ID
     * @param pharmacyName   药房名称
     * @return 发药单ID
     */
    Long createDispenseByPrescription(Long prescriptionId, Long pharmacyId, String pharmacyName);

    /**
     * 更新发药单
     *
     * @param updateReqVO 更新信息
     */
    void updateDispense(OpDispenseSaveReqVO updateReqVO);

    /**
     * 删除发药单
     *
     * @param id 发药单ID
     */
    void deleteDispense(Long id);

    /**
     * 获取发药单
     *
     * @param id 发药单ID
     * @return 发药单
     */
    OpDispenseDO getDispense(Long id);

    /**
     * 根据发药单号获取发药单
     *
     * @param dispenseNo 发药单号
     * @return 发药单
     */
    OpDispenseDO getDispenseByNo(String dispenseNo);

    /**
     * 根据处方ID获取发药单
     *
     * @param prescriptionId 处方ID
     * @return 发药单
     */
    OpDispenseDO getDispenseByPrescriptionId(Long prescriptionId);

    /**
     * 分页查询发药单
     *
     * @param pageReqVO 分页查询条件
     * @return 分页结果
     */
    PageResult<OpDispenseDO> getDispensePage(OpDispensePageReqVO pageReqVO);

    /**
     * 获取发药明细列表
     *
     * @param dispenseId 发药单ID
     * @return 发药明细列表
     */
    List<OpDispenseItemDO> getDispenseItems(Long dispenseId);

    /**
     * 根据挂号ID获取发药记录列表
     *
     * @param registerId 挂号ID
     * @return 发药记录列表
     */
    List<OpDispenseDO> getDispensesByRegisterId(Long registerId);

    /**
     * 根据患者ID获取发药记录列表
     *
     * @param patientId 患者ID
     * @return 发药记录列表
     */
    List<OpDispenseDO> getDispensesByPatientId(Long patientId);

    // ==================== 调配发药流程 ====================

    /**
     * 调配发药单
     *
     * @param id             发药单ID
     * @param pharmacistId   药师ID
     * @param pharmacistName 药师姓名
     */
    void dispense(Long id, Long pharmacistId, String pharmacistName);

    /**
     * 发药
     *
     * @param id             发药单ID
     * @param pharmacistId   药师ID
     * @param pharmacistName 药师姓名
     */
    void send(Long id, Long pharmacistId, String pharmacistName);

    /**
     * 批量发药
     *
     * @param dispenseIds    发药单ID列表
     * @param pharmacistId   药师ID
     * @param pharmacistName 药师姓名
     */
    void batchSend(List<Long> dispenseIds, Long pharmacistId, String pharmacistName);

    /**
     * 退药
     *
     * @param id             发药单ID
     * @param pharmacistId   药师ID（可选）
     * @param pharmacistName 药师姓名（可选）
     * @param reason         退药原因
     */
    void returnDrug(Long id, Long pharmacistId, String pharmacistName, String reason);

    /**
     * 退药（简化版）
     *
     * @param id     发药单ID
     * @param reason 退药原因
     */
    void returnDrug(Long id, String reason);

    // ==================== 查询统计 ====================

    /**
     * 获取待调配的发药记录列表
     *
     * @param pharmacyId 药房ID（可选）
     * @return 待调配的发药记录列表
     */
    List<OpDispenseDO> getPendingDispenseList(Long pharmacyId);

    /**
     * 获取待发药的记录列表
     *
     * @param pharmacyId 药房ID（可选）
     * @return 待发药的记录列表
     */
    List<OpDispenseDO> getPendingSendList(Long pharmacyId);

    /**
     * 统计某药房某状态的数量
     *
     * @param pharmacyId    药房ID
     * @param dispenseStatus 发药状态
     * @return 数量
     */
    Long countByPharmacyIdAndStatus(Long pharmacyId, Integer dispenseStatus);

    // ==================== 校验方法 ====================

    /**
     * 校验发药单是否存在
     *
     * @param id 发药单ID
     * @return 发药单
     */
    OpDispenseDO validateDispenseExists(Long id);

    /**
     * 校验发药单是否可以调配
     *
     * @param id 发药单ID
     * @return 发药单
     */
    OpDispenseDO validateDispenseCanDispense(Long id);

    /**
     * 校验发药单是否可以发药
     *
     * @param id 发药单ID
     * @return 发药单
     */
    OpDispenseDO validateDispenseCanSend(Long id);

    /**
     * 校验发药单是否可以退药
     *
     * @param id 发药单ID
     * @return 发药单
     */
    OpDispenseDO validateDispenseCanReturn(Long id);

}
