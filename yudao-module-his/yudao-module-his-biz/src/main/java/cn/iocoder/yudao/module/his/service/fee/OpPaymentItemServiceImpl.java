package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.OpPaymentItemSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpPaymentItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.fee.OpPaymentItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 支付明细 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class OpPaymentItemServiceImpl implements OpPaymentItemService {

    @Resource
    private OpPaymentItemMapper paymentItemMapper;

    @Override
    public Long createPaymentItem(OpPaymentItemSaveReqVO createReqVO) {
        OpPaymentItemDO paymentItem = BeanUtils.toBean(createReqVO, OpPaymentItemDO.class);
        paymentItemMapper.insert(paymentItem);
        return paymentItem.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPaymentItems(List<OpPaymentItemSaveReqVO> createReqVOs) {
        List<OpPaymentItemDO> paymentItems = BeanUtils.toBean(createReqVOs, OpPaymentItemDO.class);
        paymentItemMapper.insertBatch(paymentItems);
    }

    @Override
    public void updatePaymentItem(OpPaymentItemSaveReqVO updateReqVO) {
        // 校验存在
        validatePaymentItemExists(updateReqVO.getId());
        // 更新
        OpPaymentItemDO updateObj = BeanUtils.toBean(updateReqVO, OpPaymentItemDO.class);
        paymentItemMapper.updateById(updateObj);
    }

    @Override
    public void deletePaymentItem(Long id) {
        // 校验存在
        validatePaymentItemExists(id);
        // 删除
        paymentItemMapper.deleteById(id);
    }

    private void validatePaymentItemExists(Long id) {
        if (paymentItemMapper.selectById(id) == null) {
            throw exception(PAYMENT_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public OpPaymentItemDO getPaymentItem(Long id) {
        return paymentItemMapper.selectById(id);
    }

    @Override
    public PageResult<OpPaymentItemDO> getPaymentItemPage(OpPaymentItemPageReqVO pageReqVO) {
        return paymentItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OpPaymentItemDO> getPaymentItemsByPaymentId(Long paymentId) {
        return paymentItemMapper.selectListByPaymentId(paymentId);
    }

    @Override
    public List<OpPaymentItemDO> getPaymentItemsByFeeId(Long feeId) {
        return paymentItemMapper.selectListByFeeId(feeId);
    }

    @Override
    public List<OpPaymentItemDO> getPaymentItemsByFeeItemId(Long feeItemId) {
        return paymentItemMapper.selectListByFeeItemId(feeItemId);
    }

    @Override
    public List<OpPaymentItemDO> getPaymentItemsByRegisterId(Long registerId) {
        return paymentItemMapper.selectListByRegisterId(registerId);
    }

}
