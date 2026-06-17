package cn.iocoder.yudao.module.his.service.prepayment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentRefundPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentRefundSaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.prepayment.vo.HisPrepaymentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentRefundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.prepayment.HisPrepaymentUsageDO;
import cn.iocoder.yudao.module.his.dal.mysql.prepayment.HisPrepaymentMapper;
import cn.iocoder.yudao.module.his.dal.mysql.prepayment.HisPrepaymentRefundMapper;
import cn.iocoder.yudao.module.his.dal.mysql.prepayment.HisPrepaymentUsageMapper;
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
 * 住院预交金 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisPrepaymentServiceImpl implements HisPrepaymentService {

    @Resource
    private HisPrepaymentMapper prepaymentMapper;

    @Resource
    private HisPrepaymentUsageMapper usageMapper;

    @Resource
    private HisPrepaymentRefundMapper refundMapper;

    // ========== 预交金管理 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPrepayment(HisPrepaymentSaveReqVO createReqVO) {
        // 1. 创建预交金记录
        HisPrepaymentDO prepayment = BeanUtils.toBean(createReqVO, HisPrepaymentDO.class);
        // 生成预交金编号
        prepayment.setPrepaymentNo(generatePrepaymentNo());
        // 设置默认状态
        prepayment.setStatus(1); // 已缴纳
        prepayment.setBalanceAmount(createReqVO.getAmount());
        prepayment.setUsedAmount(BigDecimal.ZERO);
        prepayment.setRefundAmount(BigDecimal.ZERO);
        // 设置支付时间
        if (prepayment.getPayTime() == null) {
            prepayment.setPayTime(LocalDateTime.now());
        }

        prepaymentMapper.insert(prepayment);
        return prepayment.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePrepayment(HisPrepaymentSaveReqVO updateReqVO) {
        // 1. 校验存在
        validatePrepaymentExists(updateReqVO.getId());

        // 2. 校验状态（只有已缴纳状态可以修改）
        HisPrepaymentDO prepayment = prepaymentMapper.selectById(updateReqVO.getId());
        if (!prepayment.isPaid()) {
            throw exception(PREPAYMENT_STATUS_ERROR);
        }

        // 3. 更新预交金
        HisPrepaymentDO updateObj = BeanUtils.toBean(updateReqVO, HisPrepaymentDO.class);
        prepaymentMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePrepayment(Long id) {
        // 1. 校验存在
        HisPrepaymentDO prepayment = validatePrepaymentExists(id);

        // 2. 校验状态（只有已缴纳状态且未使用可以删除）
        if (!prepayment.isPaid()) {
            throw exception(PREPAYMENT_STATUS_ERROR);
        }
        if (prepayment.getUsedAmount() != null && prepayment.getUsedAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw exception(PREPAYMENT_CAN_NOT_DELETE);
        }

        // 3. 删除
        prepaymentMapper.deleteById(id);
    }

    @Override
    public HisPrepaymentDO getPrepayment(Long id) {
        return prepaymentMapper.selectById(id);
    }

    @Override
    public PageResult<HisPrepaymentDO> getPrepaymentPage(HisPrepaymentPageReqVO pageReqVO) {
        return prepaymentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisPrepaymentDO> getPrepaymentListByAdmission(Long admissionId) {
        return prepaymentMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public List<HisPrepaymentDO> getPrepaymentListByPatient(Long patientId) {
        return prepaymentMapper.selectListByPatientId(patientId);
    }

    @Override
    public BigDecimal sumAmountByAdmission(Long admissionId) {
        return prepaymentMapper.sumAmountByAdmissionId(admissionId);
    }

    @Override
    public BigDecimal sumAvailableBalanceByAdmission(Long admissionId) {
        return prepaymentMapper.sumAvailableBalanceByAdmissionId(admissionId);
    }

    @Override
    public BigDecimal sumUsedAmountByAdmission(Long admissionId) {
        return prepaymentMapper.sumUsedAmountByAdmissionId(admissionId);
    }

    @Override
    public BigDecimal sumRefundAmountByAdmission(Long admissionId) {
        return prepaymentMapper.sumRefundAmountByAdmissionId(admissionId);
    }

    @Override
    public HisPrepaymentDO validatePrepaymentExists(Long id) {
        if (id == null) {
            return null;
        }
        HisPrepaymentDO prepayment = prepaymentMapper.selectById(id);
        if (prepayment == null) {
            throw exception(PREPAYMENT_NOT_EXISTS);
        }
        return prepayment;
    }

    @Override
    public HisPrepaymentDO validatePrepaymentCanRefund(Long id) {
        HisPrepaymentDO prepayment = validatePrepaymentExists(id);
        // 只有已缴纳或已使用状态可以退还
        if (prepayment.getStatus() != 1 && prepayment.getStatus() != 2) {
            throw exception(PREPAYMENT_STATUS_ERROR);
        }
        // 检查是否有可用余额
        if (prepayment.getAvailableBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw exception(PREPAYMENT_AMOUNT_INVALID);
        }
        return prepayment;
    }

    // ========== 预交金使用 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordUsage(Long prepaymentId, Long admissionId, Long patientId,
                             BigDecimal useAmount, Integer useType,
                             Long feeItemId, String feeItemName,
                             Long operatorId, String operatorName, String remark) {
        // 1. 校验预交金
        HisPrepaymentDO prepayment = validatePrepaymentExists(prepaymentId);
        if (!prepayment.isPaid() && !prepayment.getStatus() == 2) {
            throw exception(PREPAYMENT_STATUS_ERROR);
        }

        // 2. 校验可用余额
        BigDecimal availableBalance = prepayment.getAvailableBalance();
        if (useAmount.compareTo(availableBalance) > 0) {
            throw exception(PREPAYMENT_INSUFFICIENT_BALANCE);
        }

        // 3. 创建使用记录
        HisPrepaymentUsageDO usage = HisPrepaymentUsageDO.builder()
                .usageNo(generateUsageNo())
                .prepaymentId(prepaymentId)
                .prepaymentNo(prepayment.getPrepaymentNo())
                .admissionId(admissionId)
                .patientId(patientId)
                .useAmount(useAmount)
                .useType(useType)
                .feeItemId(feeItemId)
                .feeItemName(feeItemName)
                .operatorId(operatorId)
                .operatorName(operatorName)
                .useTime(LocalDateTime.now())
                .remark(remark)
                .build();
        usageMapper.insert(usage);

        // 4. 更新预交金状态
        BigDecimal newUsedAmount = prepayment.getUsedAmount().add(useAmount);
        BigDecimal newBalance = prepayment.getAmount().subtract(newUsedAmount).subtract(prepayment.getRefundAmount());

        HisPrepaymentDO updateObj = new HisPrepaymentDO();
        updateObj.setId(prepaymentId);
        updateObj.setUsedAmount(newUsedAmount);
        updateObj.setBalanceAmount(newBalance);

        // 如果余额为0，标记为已使用状态
        if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
            updateObj.setStatus(2);
        }

        prepaymentMapper.updateById(updateObj);
    }

    @Override
    public List<HisPrepaymentUsageDO> getUsageListByPrepayment(Long prepaymentId) {
        return usageMapper.selectListByPrepaymentId(prepaymentId);
    }

    @Override
    public List<HisPrepaymentUsageDO> getUsageListByAdmission(Long admissionId) {
        return usageMapper.selectListByAdmissionId(admissionId);
    }

    // ========== 预交金退还 ==========

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRefundApply(HisPrepaymentRefundSaveReqVO createReqVO) {
        // 1. 校验预交金可退还
        HisPrepaymentDO prepayment = validatePrepaymentCanRefund(createReqVO.getPrepaymentId());

        // 2. 校验退还金额不超过可用余额
        BigDecimal availableBalance = prepayment.getAvailableBalance();
        if (createReqVO.getRefundAmount().compareTo(availableBalance) > 0) {
            throw exception(PREPAYMENT_REFUND_EXCEED_BALANCE);
        }

        // 3. 校验是否有申请中的退款
        HisPrepaymentRefundDO existingRefund = refundMapper.selectApplyingByPrepaymentId(createReqVO.getPrepaymentId());
        if (existingRefund != null) {
            throw exception(PREPAYMENT_REFUND_ALREADY_APPLYING);
        }

        // 4. 创建退还申请
        HisPrepaymentRefundDO refund = BeanUtils.toBean(createReqVO, HisPrepaymentRefundDO.class);
        refund.setRefundNo(generateRefundNo());
        refund.setPrepaymentNo(prepayment.getPrepaymentNo());
        refund.setStatus(1); // 申请中
        refund.setApplyTime(LocalDateTime.now());

        refundMapper.insert(refund);
        return refund.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveRefund(Long id, Boolean approved, String approveRemark,
                               Long approveUserId, String approveUserName) {
        // 1. 校验退还记录
        HisPrepaymentRefundDO refund = validateRefundExists(id);

        // 2. 校验状态（只有申请中可以审批）
        if (!refund.isApplying()) {
            throw exception(PREPAYMENT_REFUND_STATUS_ERROR);
        }

        // 3. 更新审批状态
        HisPrepaymentRefundDO updateObj = new HisPrepaymentRefundDO();
        updateObj.setId(id);
        updateObj.setApproveTime(LocalDateTime.now());
        updateObj.setApproveUserId(approveUserId);
        updateObj.setApproveUserName(approveUserName);
        updateObj.setApproveRemark(approveRemark);

        if (approved) {
            updateObj.setStatus(2); // 已审批
        } else {
            updateObj.setStatus(4); // 已拒绝
        }

        refundMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeRefund(Long id, String refundNoExternal,
                               Long operatorId, String operatorName) {
        // 1. 校验退还记录
        HisPrepaymentRefundDO refund = validateRefundExists(id);

        // 2. 校验状态（只有已审批可以执行退还）
        if (!refund.isApproved()) {
            throw exception(PREPAYMENT_REFUND_STATUS_ERROR);
        }

        // 3. 校验预交金
        HisPrepaymentDO prepayment = prepaymentMapper.selectById(refund.getPrepaymentId());
        if (prepayment == null) {
            throw exception(PREPAYMENT_NOT_EXISTS);
        }

        // 4. 更新退还记录
        HisPrepaymentRefundDO updateRefund = new HisPrepaymentRefundDO();
        updateRefund.setId(id);
        updateRefund.setRefundNoExternal(refundNoExternal);
        updateRefund.setRefundTime(LocalDateTime.now());
        updateRefund.setStatus(3); // 已退还
        refundMapper.updateById(updateRefund);

        // 5. 更新预交金状态
        BigDecimal newRefundAmount = prepayment.getRefundAmount().add(refund.getRefundAmount());
        BigDecimal newBalance = prepayment.getAvailableBalance().subtract(refund.getRefundAmount());

        HisPrepaymentDO updatePrepayment = new HisPrepaymentDO();
        updatePrepayment.setId(refund.getPrepaymentId());
        updatePrepayment.setRefundAmount(newRefundAmount);
        updatePrepayment.setBalanceAmount(newBalance);

        // 如果余额为0，标记为已退还状态
        if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
            updatePrepayment.setStatus(3);
        }

        prepaymentMapper.updateById(updatePrepayment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRefund(Long id) {
        // 1. 校验退还记录
        HisPrepaymentRefundDO refund = validateRefundExists(id);

        // 2. 校验状态（只有申请中可以取消）
        if (!refund.isApplying()) {
            throw exception(PREPAYMENT_REFUND_STATUS_ERROR);
        }

        // 3. 删除退还申请
        refundMapper.deleteById(id);
    }

    @Override
    public HisPrepaymentRefundDO getRefund(Long id) {
        return refundMapper.selectById(id);
    }

    @Override
    public PageResult<HisPrepaymentRefundDO> getRefundPage(HisPrepaymentRefundPageReqVO pageReqVO) {
        return refundMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisPrepaymentRefundDO> getRefundListByPrepayment(Long prepaymentId) {
        return refundMapper.selectListByPrepaymentId(prepaymentId);
    }

    @Override
    public List<HisPrepaymentRefundDO> getRefundListByAdmission(Long admissionId) {
        return refundMapper.selectListByAdmissionId(admissionId);
    }

    // ========== 校验方法 ==========

    private HisPrepaymentRefundDO validateRefundExists(Long id) {
        if (id == null) {
            return null;
        }
        HisPrepaymentRefundDO refund = refundMapper.selectById(id);
        if (refund == null) {
            throw exception(PREPAYMENT_REFUND_NOT_EXISTS);
        }
        return refund;
    }

    // ========== 生成编号方法 ==========

    private String generatePrepaymentNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long seq = System.currentTimeMillis() % 1000;
        return String.format("PP%s%03d", dateStr, seq);
    }

    private String generateUsageNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long seq = System.currentTimeMillis() % 1000;
        return String.format("PU%s%03d", dateStr, seq);
    }

    private String generateRefundNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long seq = System.currentTimeMillis() % 1000;
        return String.format("RF%s%03d", dateStr, seq);
    }

}