package cn.iocoder.yudao.module.his.service.fee;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.fee.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeDO;
import cn.iocoder.yudao.module.his.dal.dataobject.fee.OpFeeItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.fee.OpFeeMapper;
import cn.iocoder.yudao.module.his.dal.mysql.fee.OpFeeItemMapper;
import cn.iocoder.yudao.module.his.enums.FeeStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 门诊费用 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class OpFeeServiceImpl implements OpFeeService {

    @Resource
    private OpFeeMapper feeMapper;

    @Resource
    private OpFeeItemMapper feeItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFee(OpFeeSaveReqVO createReqVO) {
        // 1. 校验挂号是否已有费用
        OpFeeDO existFee = feeMapper.selectByRegisterId(createReqVO.getRegisterId());
        if (existFee != null) {
            throw exception(OP_FEE_NOT_EXISTS); // 这里应该用专门的错误码
        }

        // 2. 插入费用
        OpFeeDO fee = BeanUtils.toBean(createReqVO, OpFeeDO.class);
        // 生成费用单号
        fee.setFeeNo(generateFeeNo());
        // 设置默认状态
        if (fee.getFeeStatus() == null) {
            fee.setFeeStatus(FeeStatusEnum.NOT_PAID.getStatus());
        }
        // 初始化金额
        if (fee.getTotalAmount() == null) {
            fee.setTotalAmount(BigDecimal.ZERO);
        }
        if (fee.getDiscountAmount() == null) {
            fee.setDiscountAmount(BigDecimal.ZERO);
        }
        if (fee.getPayAmount() == null) {
            fee.setPayAmount(BigDecimal.ZERO);
        }
        if (fee.getInsuranceAmount() == null) {
            fee.setInsuranceAmount(BigDecimal.ZERO);
        }
        if (fee.getSelfAmount() == null) {
            fee.setSelfAmount(BigDecimal.ZERO);
        }
        feeMapper.insert(fee);

        return fee.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFee(OpFeeSaveReqVO updateReqVO) {
        // 1. 校验存在
        validateFeeExists(updateReqVO.getId());

        // 2. 校验状态（已收费不能修改）
        OpFeeDO fee = feeMapper.selectById(updateReqVO.getId());
        if (FeeStatusEnum.PAID.getStatus().equals(fee.getFeeStatus())) {
            throw exception(OP_FEE_ALREADY_PAID);
        }

        // 3. 更新费用
        OpFeeDO updateObj = BeanUtils.toBean(updateReqVO, OpFeeDO.class);
        feeMapper.updateById(updateObj);

        // 4. 重新计算金额
        calculateFeeAmount(updateReqVO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFee(Long id) {
        // 1. 校验存在
        OpFeeDO fee = validateFeeExists(id);

        // 2. 校验状态（已收费不能删除）
        if (FeeStatusEnum.PAID.getStatus().equals(fee.getFeeStatus())) {
            throw exception(OP_FEE_ALREADY_PAID);
        }

        // 3. 删除费用明细
        feeItemMapper.deleteByFeeId(id);

        // 4. 删除费用
        feeMapper.deleteById(id);
    }

    @Override
    public OpFeeDO getFee(Long id) {
        return feeMapper.selectById(id);
    }

    @Override
    public PageResult<OpFeeDO> getFeePage(OpFeePageReqVO pageReqVO) {
        return feeMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<OpFeeDO>()
                .likeIfPresent(OpFeeDO::getFeeNo, pageReqVO.getFeeNo())
                .eqIfPresent(OpFeeDO::getRegisterId, pageReqVO.getRegisterId())
                .eqIfPresent(OpFeeDO::getPatientId, pageReqVO.getPatientId())
                .likeIfPresent(OpFeeDO::getPatientName, pageReqVO.getPatientName())
                .eqIfPresent(OpFeeDO::getDeptId, pageReqVO.getDeptId())
                .eqIfPresent(OpFeeDO::getFeeStatus, pageReqVO.getFeeStatus())
                .betweenIfPresent(OpFeeDO::getCreateTime, pageReqVO.getCreateTime())
                .orderByDesc(OpFeeDO::getId));
    }

    @Override
    public List<OpFeeDO> getFeeList(List<Long> ids) {
        return feeMapper.selectBatchIds(ids);
    }

    @Override
    public OpFeeDO getFeeByRegisterId(Long registerId) {
        return feeMapper.selectByRegisterId(registerId);
    }

    @Override
    public OpFeeDO getFeeByFeeNo(String feeNo) {
        return feeMapper.selectByFeeNo(feeNo);
    }

    @Override
    public List<OpFeeDO> getUnpaidFeeListByPatientId(Long patientId) {
        return feeMapper.selectUnpaidByPatientId(patientId);
    }

    @Override
    public List<OpFeeItemDO> getFeeItemListByFeeId(Long feeId) {
        return feeItemMapper.selectListByFeeId(feeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFeeItem(OpFeeItemSaveReqVO createReqVO) {
        // 1. 校验费用存在
        validateFeeExists(createReqVO.getFeeId());

        // 2. 校验费用状态（已收费不能添加明细）
        OpFeeDO fee = feeMapper.selectById(createReqVO.getFeeId());
        if (FeeStatusEnum.PAID.getStatus().equals(fee.getFeeStatus())) {
            throw exception(OP_FEE_ALREADY_PAID);
        }

        // 3. 计算金额
        OpFeeItemDO feeItem = BeanUtils.toBean(createReqVO, OpFeeItemDO.class);
        calculateFeeItemAmount(feeItem);

        // 4. 设置默认状态
        if (feeItem.getFeeItemStatus() == null) {
            feeItem.setFeeItemStatus(0); // 未收费
        }
        if (feeItem.getDiscountAmount() == null) {
            feeItem.setDiscountAmount(BigDecimal.ZERO);
        }

        // 5. 插入费用明细
        feeItemMapper.insert(feeItem);

        // 6. 更新费用总金额
        calculateFeeAmount(createReqVO.getFeeId());

        return feeItem.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFeeItem(OpFeeItemSaveReqVO updateReqVO) {
        // 1. 校验存在
        OpFeeItemDO feeItem = feeItemMapper.selectById(updateReqVO.getId());
        if (feeItem == null) {
            throw exception(OP_FEE_NOT_EXISTS);
        }

        // 2. 校验费用状态
        OpFeeDO fee = feeMapper.selectById(feeItem.getFeeId());
        if (FeeStatusEnum.PAID.getStatus().equals(fee.getFeeStatus())) {
            throw exception(OP_FEE_ALREADY_PAID);
        }

        // 3. 更新费用明细
        OpFeeItemDO updateObj = BeanUtils.toBean(updateReqVO, OpFeeItemDO.class);
        calculateFeeItemAmount(updateObj);
        feeItemMapper.updateById(updateObj);

        // 4. 更新费用总金额
        calculateFeeAmount(feeItem.getFeeId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFeeItem(Long id) {
        // 1. 校验存在
        OpFeeItemDO feeItem = feeItemMapper.selectById(id);
        if (feeItem == null) {
            throw exception(OP_FEE_NOT_EXISTS);
        }

        // 2. 校验费用状态
        OpFeeDO fee = feeMapper.selectById(feeItem.getFeeId());
        if (FeeStatusEnum.PAID.getStatus().equals(fee.getFeeStatus())) {
            throw exception(OP_FEE_ALREADY_PAID);
        }

        // 3. 删除费用明细
        feeItemMapper.deleteById(id);

        // 4. 更新费用总金额
        calculateFeeAmount(feeItem.getFeeId());
    }

    @Override
    public OpFeeItemDO getFeeItem(Long id) {
        return feeItemMapper.selectById(id);
    }

    @Override
    public PageResult<OpFeeItemDO> getFeeItemPage(OpFeeItemPageReqVO pageReqVO) {
        return feeItemMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<OpFeeItemDO>()
                .eqIfPresent(OpFeeItemDO::getFeeId, pageReqVO.getFeeId())
                .eqIfPresent(OpFeeItemDO::getRegisterId, pageReqVO.getRegisterId())
                .eqIfPresent(OpFeeItemDO::getPatientId, pageReqVO.getPatientId())
                .eqIfPresent(OpFeeItemDO::getChargeItemId, pageReqVO.getChargeItemId())
                .likeIfPresent(OpFeeItemDO::getItemCode, pageReqVO.getItemCode())
                .likeIfPresent(OpFeeItemDO::getItemName, pageReqVO.getItemName())
                .eqIfPresent(OpFeeItemDO::getItemCategory, pageReqVO.getItemCategory())
                .eqIfPresent(OpFeeItemDO::getSourceType, pageReqVO.getSourceType())
                .eqIfPresent(OpFeeItemDO::getFeeItemStatus, pageReqVO.getFeeItemStatus())
                .betweenIfPresent(OpFeeItemDO::getCreateTime, pageReqVO.getCreateTime())
                .orderByAsc(OpFeeItemDO::getSortOrder)
                .orderByDesc(OpFeeItemDO::getId));
    }

    @Override
    public List<OpFeeItemDO> getFeeItemList(List<Long> ids) {
        return feeItemMapper.selectBatchIds(ids);
    }

    @Override
    public List<OpFeeItemDO> getFeeItemListBySource(Integer sourceType, Long sourceId) {
        return feeItemMapper.selectListBySource(sourceType, sourceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calculateFeeAmount(Long feeId) {
        // 1. 查询费用明细
        List<OpFeeItemDO> feeItems = feeItemMapper.selectListByFeeId(feeId);

        // 2. 计算总金额
        BigDecimal totalAmount = feeItems.stream()
                .map(OpFeeItemDO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = feeItems.stream()
                .map(item -> item.getDiscountAmount() != null ? item.getDiscountAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal payAmount = totalAmount.subtract(discountAmount);

        // 3. 更新费用
        OpFeeDO updateFee = new OpFeeDO();
        updateFee.setId(feeId);
        updateFee.setTotalAmount(totalAmount);
        updateFee.setDiscountAmount(discountAmount);
        updateFee.setPayAmount(payAmount);
        feeMapper.updateById(updateFee);
    }

    @Override
    public OpFeeDO validateFeeExists(Long id) {
        if (id == null) {
            return null;
        }
        OpFeeDO fee = feeMapper.selectById(id);
        if (fee == null) {
            throw exception(OP_FEE_NOT_EXISTS);
        }
        return fee;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFeeStatus(Long feeId, Integer status) {
        if (feeId == null) {
            return;
        }
        OpFeeDO updateFee = new OpFeeDO();
        updateFee.setId(feeId);
        updateFee.setFeeStatus(status);
        feeMapper.updateById(updateFee);
    }

    /**
     * 生成费用单号
     * 格式: F + yyyyMMddHHmmss + 4位流水号
     */
    private String generateFeeNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("F%s%04d", dateStr, seq);
    }

    /**
     * 计算费用明细金额
     */
    private void calculateFeeItemAmount(OpFeeItemDO feeItem) {
        // 金额 = 单价 * 数量
        BigDecimal amount = feeItem.getUnitPrice().multiply(feeItem.getQuantity());
        feeItem.setAmount(amount);

        // 实收金额 = 金额 - 优惠金额
        BigDecimal discountAmount = feeItem.getDiscountAmount() != null ? feeItem.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal payAmount = amount.subtract(discountAmount);
        feeItem.setPayAmount(payAmount);
    }

}