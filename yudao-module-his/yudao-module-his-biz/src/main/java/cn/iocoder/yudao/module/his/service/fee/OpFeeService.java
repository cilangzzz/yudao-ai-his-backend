package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 门诊费用 Service 接口
 *
 * @author yudao
 */
public interface OpFeeService {

    /**
     * 创建门诊费用
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFee(@Valid OpFeeSaveReqVO createReqVO);

    /**
     * 更新门诊费用
     *
     * @param updateReqVO 更新信息
     */
    void updateFee(@Valid OpFeeSaveReqVO updateReqVO);

    /**
     * 删除门诊费用
     *
     * @param id 编号
     */
    void deleteFee(Long id);

    /**
     * 获得门诊费用
     *
     * @param id 编号
     * @return 门诊费用
     */
    OpFeeDO getFee(Long id);

    /**
     * 获得门诊费用分页
     *
     * @param pageReqVO 分页查询
     * @return 门诊费用分页
     */
    PageResult<OpFeeDO> getFeePage(OpFeePageReqVO pageReqVO);

    /**
     * 获得门诊费用列表
     *
     * @param ids 编号列表
     * @return 门诊费用列表
     */
    List<OpFeeDO> getFeeList(List<Long> ids);

    /**
     * 根据挂号ID获得门诊费用
     *
     * @param registerId 挂号ID
     * @return 门诊费用
     */
    OpFeeDO getFeeByRegisterId(Long registerId);

    /**
     * 根据费用单号获得门诊费用
     *
     * @param feeNo 费用单号
     * @return 门诊费用
     */
    OpFeeDO getFeeByFeeNo(String feeNo);

    /**
     * 获得患者的未收费费用列表
     *
     * @param patientId 患者ID
     * @return 未收费费用列表
     */
    List<OpFeeDO> getUnpaidFeeListByPatientId(Long patientId);

    /**
     * 根据费用ID获得费用明细列表
     *
     * @param feeId 费用ID
     * @return 费用明细列表
     */
    List<OpFeeItemDO> getFeeItemListByFeeId(Long feeId);

    /**
     * 创建费用明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFeeItem(@Valid OpFeeItemSaveReqVO createReqVO);

    /**
     * 更新费用明细
     *
     * @param updateReqVO 更新信息
     */
    void updateFeeItem(@Valid OpFeeItemSaveReqVO updateReqVO);

    /**
     * 删除费用明细
     *
     * @param id 编号
     */
    void deleteFeeItem(Long id);

    /**
     * 获得费用明细
     *
     * @param id 编号
     * @return 费用明细
     */
    OpFeeItemDO getFeeItem(Long id);

    /**
     * 获得费用明细分页
     *
     * @param pageReqVO 分页查询
     * @return 费用明细分页
     */
    PageResult<OpFeeItemDO> getFeeItemPage(OpFeeItemPageReqVO pageReqVO);

    /**
     * 获得费用明细列表
     *
     * @param ids 编号列表
     * @return 费用明细列表
     */
    List<OpFeeItemDO> getFeeItemList(List<Long> ids);

    /**
     * 根据来源类型和来源ID获得费用明细列表
     *
     * @param sourceType 来源类型
     * @param sourceId 来源ID
     * @return 费用明细列表
     */
    List<OpFeeItemDO> getFeeItemListBySource(Integer sourceType, Long sourceId);

    /**
     * 计算费用金额
     *
     * @param feeId 费用ID
     */
    void calculateFeeAmount(Long feeId);

    /**
     * 校验费用是否存在
     *
     * @param id 编号
     * @return 费用
     */
    OpFeeDO validateFeeExists(Long id);

}