package cn.iocoder.yudao.module.his.service.payment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.payment.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.payment.HisPrepaymentDO;
import cn.iocoder.yudao.module.his.dal.mysql.payment.HisPrepaymentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 预交金 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisPrepaymentServiceImpl implements HisPrepaymentService {

    @Resource
    private HisPrepaymentMapper prepaymentMapper;

    @Override
    public Long createPrepayment(HisPrepaymentCreateReqVO createReqVO) {
        // 生成预交金单号
        String prepayNo = generatePrepayNo();

        // 创建预交金记录
        HisPrepaymentDO prepayment = BeanUtils.toBean(createReqVO, HisPrepaymentDO.class);
        prepayment.setPrepayNo(prepayNo);
        prepayment.setPayStatus(1); // 正常
        prepayment.setPayTime(LocalDateTime.now());
        prepaymentMapper.insert(prepayment);

        return prepayment.getId();
    }

    @Override
    public void refundPrepayment(Long id, BigDecimal refundAmount, Long refundBy) {
        // 校验预交金记录
        HisPrepaymentDO prepayment = prepaymentMapper.selectById(id);
        if (prepayment == null) {
            throw exception(PREPAYMENT_NOT_EXISTS);
        }

        // 校验退款金额
        if (refundAmount.compareTo(prepayment.getAmount()) > 0) {
            throw exception(REFUND_AMOUNT_EXCEED);
        }

        // 更新退款信息
        HisPrepaymentDO updateObj = new HisPrepaymentDO();
        updateObj.setId(id);
        updateObj.setPayStatus(2); // 已退
        updateObj.setRefundAmount(refundAmount);
        updateObj.setRefundTime(LocalDateTime.now());
        updateObj.setRefundBy(refundBy);
        prepaymentMapper.updateById(updateObj);
    }

    @Override
    public void deletePrepayment(Long id) {
        // 校验存在
        validatePrepaymentExists(id);
        // 删除
        prepaymentMapper.deleteById(id);
    }

    private void validatePrepaymentExists(Long id) {
        if (prepaymentMapper.selectById(id) == null) {
            throw exception(PREPAYMENT_NOT_EXISTS);
        }
    }

    @Override
    public HisPrepaymentDO getPrepayment(Long id) {
        return prepaymentMapper.selectById(id);
    }

    @Override
    public HisPrepaymentDO getPrepaymentByPrepayNo(String prepayNo) {
        return prepaymentMapper.selectByPrepayNo(prepayNo);
    }

    @Override
    public PageResult<HisPrepaymentDO> getPrepaymentPage(HisPrepaymentPageReqVO pageReqVO) {
        return prepaymentMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<HisPrepaymentDO>()
                .eqIfPresent(HisPrepaymentDO::getPrepayNo, pageReqVO.getPrepayNo())
                .eqIfPresent(HisPrepaymentDO::getAdmissionId, pageReqVO.getAdmissionId())
                .eqIfPresent(HisPrepaymentDO::getPatientId, pageReqVO.getPatientId())
                .likeIfPresent(HisPrepaymentDO::getPatientName, pageReqVO.getPatientName())
                .eqIfPresent(HisPrepaymentDO::getPayType, pageReqVO.getPayType())
                .eqIfPresent(HisPrepaymentDO::getPayStatus, pageReqVO.getPayStatus())
                .betweenIfPresent(HisPrepaymentDO::getPayTime, pageReqVO.getPayTime())
                .orderByDesc(HisPrepaymentDO::getId));
    }

    @Override
    public List<HisPrepaymentDO> getPrepaymentListByAdmissionId(Long admissionId) {
        return prepaymentMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public List<HisPrepaymentDO> getPrepaymentListByPatientId(Long patientId) {
        return prepaymentMapper.selectListByPatientId(patientId);
    }

    @Override
    public BigDecimal getTotalPrepaymentByAdmissionId(Long admissionId) {
        return prepaymentMapper.selectTotalAmountByAdmissionId(admissionId);
    }

    /**
     * 生成预交金单号
     */
    private String generatePrepayNo() {
        return "PRE" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

}