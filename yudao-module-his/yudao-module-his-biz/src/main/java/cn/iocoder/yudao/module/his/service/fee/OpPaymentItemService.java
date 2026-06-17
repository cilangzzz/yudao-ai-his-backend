package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 支付明细 Service 接口
 *
 * @author yudao
 */
public interface OpPaymentItemService {

    /**
     * 创建支付明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPaymentItem(@Valid OpPaymentItemSaveReqVO createReqVO);

    /**
     * 批量创建支付明细
     *
     * @param createReqVOs 创建信息列表
     */
    void createPaymentItems(@Valid List<OpPaymentItemSaveReqVO> createReqVOs);

    /**
     * 更新支付明细
     *
     * @param updateReqVO 更新信息
     */
    void updatePaymentItem(@Valid OpPaymentItemSaveReqVO updateReqVO);

    /**
     * 删除支付明细
     *
     * @param id 编号
     */
    void deletePaymentItem(Long id);

    /**
     * 获得支付明细
     *
     * @param id 编号
     * @return 支付明细
     */
    OpPaymentItemDO getPaymentItem(Long id);

    /**
     * 获得支付明细分页
     *
     * @param pageReqVO 分页查询
     * @return 支付明细分页
     */
    PageResult<OpPaymentItemDO> getPaymentItemPage(OpPaymentItemPageReqVO pageReqVO);

    /**
     * 根据支付记录ID获取支付明细列表
     *
     * @param paymentId 支付记录ID
     * @return 支付明细列表
     */
    List<OpPaymentItemDO> getPaymentItemsByPaymentId(Long paymentId);

    /**
     * 根据费用ID获取支付明细列表
     *
     * @param feeId 费用ID
     * @return 支付明细列表
     */
    List<OpPaymentItemDO> getPaymentItemsByFeeId(Long feeId);

    /**
     * 根据费用明细ID获取支付明细列表
     *
     * @param feeItemId 费用明细ID
     * @return 支付明细列表
     */
    List<OpPaymentItemDO> getPaymentItemsByFeeItemId(Long feeItemId);

    /**
     * 根据挂号ID获取支付明细列表
     *
     * @param registerId 挂号ID
     * @return 支付明细列表
     */
    List<OpPaymentItemDO> getPaymentItemsByRegisterId(Long registerId);

}
