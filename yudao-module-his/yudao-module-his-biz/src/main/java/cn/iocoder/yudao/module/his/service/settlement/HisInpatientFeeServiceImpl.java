package cn.iocoder.yudao.module.his.service.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientFeePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientFeeSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientFeeDO;
import cn.iocoder.yudao.module.his.dal.mysql.settlement.HisInpatientFeeMapper;
import cn.iocoder.yudao.module.his.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 住院费用明细 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
@Slf4j
public class HisInpatientFeeServiceImpl implements HisInpatientFeeService {

    @Resource
    private HisInpatientFeeMapper feeMapper;

    /**
     * 费用单号序列，每天重置
     */
    private final AtomicInteger feeSeq = new AtomicInteger(1);
    private String lastFeeDate = "";

    @Override
    public Long createFee(HisInpatientFeeSaveReqVO createReqVO) {
        // 生成费用单号
        String feeNo = generateFeeNo();

        // 转换 DO
        HisInpatientFeeDO fee = BeanUtils.toBean(createReqVO, HisInpatientFeeDO.class);
        fee.setFeeNo(feeNo);
        fee.setFeeStatus(0); // 未结算

        // 计算金额
        if (fee.getQuantity() != null && fee.getUnitPrice() != null) {
            fee.setTotalAmount(fee.getQuantity().multiply(fee.getUnitPrice()));
            fee.calculatePayAmount();
        }

        // 保存
        feeMapper.insert(fee);
        return fee.getId();
    }

    @Override
    public List<Long> batchCreateFee(List<HisInpatientFeeSaveReqVO> createReqVOList) {
        List<Long> ids = new ArrayList<>();
        for (HisInpatientFeeSaveReqVO createReqVO : createReqVOList) {
            ids.add(createFee(createReqVO));
        }
        return ids;
    }

    @Override
    public void updateFee(HisInpatientFeeSaveReqVO updateReqVO) {
        // 校验存在
        validateFeeExists(updateReqVO.getId());

        // 转换并更新
        HisInpatientFeeDO updateObj = BeanUtils.toBean(updateReqVO, HisInpatientFeeDO.class);

        // 计算金额
        if (updateObj.getQuantity() != null && updateObj.getUnitPrice() != null) {
            updateObj.setTotalAmount(updateObj.getQuantity().multiply(updateObj.getUnitPrice()));
            updateObj.calculatePayAmount();
        }

        feeMapper.updateById(updateObj);
    }

    @Override
    public void deleteFee(Long id) {
        // 校验存在
        HisInpatientFeeDO fee = validateFeeExists(id);

        // 校验状态 - 只有未结算可以删除
        if (!fee.isUnsettled()) {
            throw exception(ErrorCodeConstants.FEE_ALREADY_SETTLED);
        }

        // 删除
        feeMapper.deleteById(id);
    }

    @Override
    public HisInpatientFeeDO getFee(Long id) {
        return feeMapper.selectById(id);
    }

    @Override
    public PageResult<HisInpatientFeeDO> getFeePage(HisInpatientFeePageReqVO pageReqVO) {
        return feeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisInpatientFeeDO> getFeeListByAdmissionId(Long admissionId) {
        return feeMapper.selectListByAdmissionId(admissionId);
    }

    @Override
    public HisInpatientFeeDO getFeeSummaryByAdmissionId(Long admissionId) {
        List<HisInpatientFeeDO> feeList = feeMapper.selectListByAdmissionId(admissionId);
        if (feeList == null || feeList.isEmpty()) {
            return null;
        }

        // 汇总
        HisInpatientFeeDO summary = new HisInpatientFeeDO();
        summary.setTotalAmount(BigDecimal.ZERO);

        for (HisInpatientFeeDO fee : feeList) {
            if (fee.getTotalAmount() != null) {
                summary.setTotalAmount(summary.getTotalAmount().add(fee.getTotalAmount()));
            }
        }

        return summary;
    }

    @Override
    public synchronized String generateFeeNo() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 如果日期变化，重置序列
        if (!today.equals(lastFeeDate)) {
            lastFeeDate = today;
            feeSeq.set(1);
        }

        // 生成费用单号: FEE + YYYYMMDD + XXXX
        int seq = feeSeq.getAndIncrement();
        return String.format("FEE%s%04d", today, seq);
    }

    /**
     * 校验费用明细存在
     */
    private HisInpatientFeeDO validateFeeExists(Long id) {
        HisInpatientFeeDO fee = feeMapper.selectById(id);
        if (fee == null) {
            throw exception(ErrorCodeConstants.FEE_NOT_EXISTS);
        }
        return fee;
    }

}