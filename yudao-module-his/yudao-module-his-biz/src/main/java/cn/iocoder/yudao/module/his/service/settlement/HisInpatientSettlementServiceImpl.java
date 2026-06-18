package cn.iocoder.yudao.module.his.service.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientSettlementPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientSettlementSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientFeeDO;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientSettlementDO;
import cn.iocoder.yudao.module.his.dal.mysql.settlement.HisInpatientFeeMapper;
import cn.iocoder.yudao.module.his.dal.mysql.settlement.HisInpatientSettlementMapper;
import cn.iocoder.yudao.module.his.enums.ErrorCodeConstants;
import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 住院结算 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
@Slf4j
public class HisInpatientSettlementServiceImpl implements HisInpatientSettlementService {

    @Resource
    private HisInpatientSettlementMapper settlementMapper;

    @Resource
    private HisInpatientFeeMapper feeMapper;

    /**
     * 结算单号序列，每天重置
     */
    private final AtomicInteger settlementSeq = new AtomicInteger(1);
    private String lastSettlementDate = "";

    @Override
    public Long createSettlement(HisInpatientSettlementSaveReqVO createReqVO) {
        // 生成结算单号
        String settlementNo = generateSettlementNo();

        // 转换 DO
        HisInpatientSettlementDO settlement = BeanUtils.toBean(createReqVO, HisInpatientSettlementDO.class);
        settlement.setSettlementNo(settlementNo);
        settlement.setSettlementStatus(0); // 未结算

        // 计算费用汇总
        if (createReqVO.getAdmissionId() != null) {
            HisInpatientSettlementDO summary = calculateFeeSummary(createReqVO.getAdmissionId());
            if (summary != null) {
                settlement.setTotalAmount(summary.getTotalAmount());
                settlement.setWesternMedicineFee(summary.getWesternMedicineFee());
                settlement.setChineseMedicineFee(summary.getChineseMedicineFee());
                settlement.setExaminationFee(summary.getExaminationFee());
                settlement.setLaboratoryFee(summary.getLaboratoryFee());
                settlement.setTreatmentFee(summary.getTreatmentFee());
                settlement.setNursingFee(summary.getNursingFee());
                settlement.setSurgeryFee(summary.getSurgeryFee());
                settlement.setBedFee(summary.getBedFee());
                settlement.setMaterialFee(summary.getMaterialFee());
                settlement.setOtherFee(summary.getOtherFee());
                settlement.calculatePayableAmount();
            }
        }

        // 保存
        settlementMapper.insert(settlement);
        return settlement.getId();
    }

    @Override
    public void updateSettlement(HisInpatientSettlementSaveReqVO updateReqVO) {
        // 校验存在
        validateSettlementExists(updateReqVO.getId());

        // 更新
        HisInpatientSettlementDO updateObj = BeanUtils.toBean(updateReqVO, HisInpatientSettlementDO.class);
        settlementMapper.updateById(updateObj);
    }

    @Override
    public void deleteSettlement(Long id) {
        // 校验存在
        HisInpatientSettlementDO settlement = validateSettlementExists(id);

        // 校验状态 - 只有未结算可以删除
        if (!settlement.isUnsettled()) {
            throw exception(ErrorCodeConstants.SETTLEMENT_STATUS_ERROR);
        }

        // 删除
        settlementMapper.deleteById(id);
    }

    @Override
    public HisInpatientSettlementDO getSettlement(Long id) {
        return settlementMapper.selectById(id);
    }

    @Override
    public PageResult<HisInpatientSettlementDO> getSettlementPage(HisInpatientSettlementPageReqVO pageReqVO) {
        return settlementMapper.selectPage(pageReqVO);
    }

    @Override
    public HisInpatientSettlementDO getSettlementByAdmissionId(Long admissionId) {
        return settlementMapper.selectByAdmissionId(admissionId);
    }

    @Override
    public List<HisInpatientSettlementDO> getSettlementListByAdmissionId(Long admissionId) {
        return settlementMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmSettlement(Long id, Integer paymentType, Long operatorId, String operatorName) {
        // 校验存在
        HisInpatientSettlementDO settlement = validateSettlementExists(id);

        // 校验状态 - 只有未结算可以确认
        if (!settlement.isUnsettled()) {
            throw exception(ErrorCodeConstants.SETTLEMENT_STATUS_ERROR);
        }

        // 更新结算信息
        HisInpatientSettlementDO updateObj = new HisInpatientSettlementDO();
        updateObj.setId(id);
        updateObj.setSettlementStatus(1); // 已结算
        updateObj.setPaymentType(paymentType);
        updateObj.setPaymentTime(LocalDateTime.now());
        updateObj.setOperatorId(operatorId);
        updateObj.setOperatorName(operatorName);
        updateObj.setSettlementTime(LocalDateTime.now());

        // 生成发票号
        updateObj.setInvoiceNo(generateInvoiceNo());

        settlementMapper.updateById(updateObj);

        // 更新费用明细状态
        feeMapper.updateStatusByAdmissionId(settlement.getAdmissionId(), 1, id);

        log.info("住院结算确认成功，结算单ID: {}, 结算单号: {}", id, settlement.getSettlementNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundSettlement(Long id, BigDecimal refundAmount, String remark) {
        // 校验存在
        HisInpatientSettlementDO settlement = validateSettlementExists(id);

        // 校验状态 - 只有已结算可以退费
        if (!settlement.isSettled()) {
            throw exception(ErrorCodeConstants.SETTLEMENT_STATUS_ERROR);
        }

        // 更新退费信息
        HisInpatientSettlementDO updateObj = new HisInpatientSettlementDO();
        updateObj.setId(id);
        updateObj.setSettlementStatus(2); // 已退费
        updateObj.setRefundAmount(refundAmount);
        updateObj.setRemark(remark);

        settlementMapper.updateById(updateObj);

        log.info("住院结算退费成功，结算单ID: {}, 退费金额: {}", id, refundAmount);
    }

    @Override
    public void cancelSettlement(Long id, String remark) {
        // 校验存在
        HisInpatientSettlementDO settlement = validateSettlementExists(id);

        // 校验状态 - 只有未结算可以作废
        if (!settlement.isUnsettled()) {
            throw exception(ErrorCodeConstants.SETTLEMENT_STATUS_ERROR);
        }

        // 更新作废状态
        HisInpatientSettlementDO updateObj = new HisInpatientSettlementDO();
        updateObj.setId(id);
        updateObj.setSettlementStatus(3); // 已作废
        updateObj.setRemark(remark);

        settlementMapper.updateById(updateObj);
    }

    @Override
    public synchronized String generateSettlementNo() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 如果日期变化，重置序列
        if (!today.equals(lastSettlementDate)) {
            lastSettlementDate = today;
            settlementSeq.set(1);
        }

        // 生成结算单号: JS + YYYYMMDD + XXXX
        int seq = settlementSeq.getAndIncrement();
        return String.format("JS%s%04d", today, seq);
    }

    @Override
    public HisInpatientSettlementDO calculateFeeSummary(Long admissionId) {
        // 查询费用明细
        List<HisInpatientFeeDO> feeList = feeMapper.selectListByAdmissionId(admissionId);
        if (feeList == null || feeList.isEmpty()) {
            return null;
        }

        // 汇总各项费用
        HisInpatientSettlementDO summary = new HisInpatientSettlementDO();
        summary.setTotalAmount(BigDecimal.ZERO);
        summary.setWesternMedicineFee(BigDecimal.ZERO);
        summary.setChineseMedicineFee(BigDecimal.ZERO);
        summary.setExaminationFee(BigDecimal.ZERO);
        summary.setLaboratoryFee(BigDecimal.ZERO);
        summary.setTreatmentFee(BigDecimal.ZERO);
        summary.setNursingFee(BigDecimal.ZERO);
        summary.setSurgeryFee(BigDecimal.ZERO);
        summary.setBedFee(BigDecimal.ZERO);
        summary.setMaterialFee(BigDecimal.ZERO);
        summary.setOtherFee(BigDecimal.ZERO);

        for (HisInpatientFeeDO fee : feeList) {
            if (fee.getTotalAmount() == null) continue;

            summary.setTotalAmount(summary.getTotalAmount().add(fee.getTotalAmount()));

            // 按费用类型汇总
            if (fee.getItemType() != null) {
                switch (fee.getItemType()) {
                    case 1: // 药品
                        summary.setWesternMedicineFee(summary.getWesternMedicineFee().add(fee.getTotalAmount()));
                        break;
                    case 2: // 检查
                        summary.setExaminationFee(summary.getExaminationFee().add(fee.getTotalAmount()));
                        break;
                    case 3: // 检验
                        summary.setLaboratoryFee(summary.getLaboratoryFee().add(fee.getTotalAmount()));
                        break;
                    case 4: // 诊疗
                        summary.setTreatmentFee(summary.getTreatmentFee().add(fee.getTotalAmount()));
                        break;
                    case 5: // 护理
                        summary.setNursingFee(summary.getNursingFee().add(fee.getTotalAmount()));
                        break;
                    case 6: // 手术
                        summary.setSurgeryFee(summary.getSurgeryFee().add(fee.getTotalAmount()));
                        break;
                    case 7: // 床位
                        summary.setBedFee(summary.getBedFee().add(fee.getTotalAmount()));
                        break;
                    default: // 其他
                        summary.setOtherFee(summary.getOtherFee().add(fee.getTotalAmount()));
                        break;
                }
            }
        }

        summary.calculatePayableAmount();
        return summary;
    }

    /**
     * 生成发票号
     */
    private String generateInvoiceNo() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long timestamp = System.currentTimeMillis() % 100000;
        return String.format("FP%s%05d", today, timestamp);
    }

    /**
     * 校验结算单存在
     */
    private HisInpatientSettlementDO validateSettlementExists(Long id) {
        HisInpatientSettlementDO settlement = settlementMapper.selectById(id);
        if (settlement == null) {
            throw exception(ErrorCodeConstants.SETTLEMENT_NOT_EXISTS);
        }
        return settlement;
    }

}
