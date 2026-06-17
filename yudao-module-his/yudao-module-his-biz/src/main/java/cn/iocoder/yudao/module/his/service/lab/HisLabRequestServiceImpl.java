package cn.iocoder.yudao.module.his.service.lab;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisLabRequestItemSaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisLabRequestPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisLabRequestSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestDO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.lab.HisLabRequestItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.lab.HisLabRequestMapper;
import cn.iocoder.yudao.module.his.enums.LabRequestStatusEnum;
import cn.iocoder.yudao.module.his.enums.SpecimenStatusEnum;
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
 * 检验申请 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisLabRequestServiceImpl implements HisLabRequestService {

    @Resource
    private HisLabRequestMapper labRequestMapper;

    @Resource
    private HisLabRequestItemMapper labRequestItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createLabRequest(HisLabRequestSaveReqVO createReqVO) {
        // 1. 插入申请主表
        HisLabRequestDO labRequest = BeanUtils.toBean(createReqVO, HisLabRequestDO.class);
        labRequest.setRequestNo(generateRequestNo());
        labRequest.setRequestStatus(LabRequestStatusEnum.REQUESTED.getCode());
        labRequest.setSpecimenStatus(SpecimenStatusEnum.PENDING.getCode());
        labRequest.setReportStatus(0);
        labRequest.setPayStatus(0);
        labRequest.setCriticalFlag(0);
        labRequest.setRequestTime(LocalDateTime.now());
        labRequest.setTotalAmount(BigDecimal.ZERO);
        labRequest.setInsuranceAmount(BigDecimal.ZERO);
        labRequest.setSelfAmount(BigDecimal.ZERO);
        labRequestMapper.insert(labRequest);

        // 2. 插入明细表并计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        int sortOrder = 0;
        for (HisLabRequestItemSaveReqVO itemVO : createReqVO.getItems()) {
            HisLabRequestItemDO item = BeanUtils.toBean(itemVO, HisLabRequestItemDO.class);
            item.setRequestId(labRequest.getId());
            item.setItemStatus(1); // 待检验
            if (item.getQuantity() == null) {
                item.setQuantity(BigDecimal.ONE);
            }
            if (item.getUnitPrice() != null && item.getQuantity() != null) {
                item.setAmount(item.getUnitPrice().multiply(item.getQuantity()));
                totalAmount = totalAmount.add(item.getAmount());
            }
            if (item.getSortOrder() == null) {
                item.setSortOrder(sortOrder++);
            }
            labRequestItemMapper.insert(item);
        }

        // 3. 更新总金额和项目数
        labRequest.setTotalAmount(totalAmount);
        labRequest.setItemCount(createReqVO.getItems().size());
        labRequestMapper.updateById(labRequest);

        return labRequest.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLabRequest(HisLabRequestSaveReqVO updateReqVO) {
        // 1. 校验存在
        HisLabRequestDO existRequest = validateLabRequestExists(updateReqVO.getId());

        // 2. 校验状态(只有已申请状态可以修改)
        if (!LabRequestStatusEnum.REQUESTED.getCode().equals(existRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_ALREADY_COLLECTED);
        }

        // 3. 更新主表
        HisLabRequestDO updateObj = BeanUtils.toBean(updateReqVO, HisLabRequestDO.class);
        labRequestMapper.updateById(updateObj);

        // 4. 删除旧明细
        labRequestItemMapper.deleteByRequestId(updateReqVO.getId());

        // 5. 插入新明细
        BigDecimal totalAmount = BigDecimal.ZERO;
        int sortOrder = 0;
        for (HisLabRequestItemSaveReqVO itemVO : updateReqVO.getItems()) {
            HisLabRequestItemDO item = BeanUtils.toBean(itemVO, HisLabRequestItemDO.class);
            item.setRequestId(updateReqVO.getId());
            item.setItemStatus(1);
            if (item.getQuantity() == null) {
                item.setQuantity(BigDecimal.ONE);
            }
            if (item.getUnitPrice() != null && item.getQuantity() != null) {
                item.setAmount(item.getUnitPrice().multiply(item.getQuantity()));
                totalAmount = totalAmount.add(item.getAmount());
            }
            if (item.getSortOrder() == null) {
                item.setSortOrder(sortOrder++);
            }
            labRequestItemMapper.insert(item);
        }

        // 6. 更新总金额和项目数
        updateObj.setTotalAmount(totalAmount);
        updateObj.setItemCount(updateReqVO.getItems().size());
        labRequestMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLabRequest(Long id) {
        // 1. 校验存在
        HisLabRequestDO labRequest = validateLabRequestExists(id);

        // 2. 校验状态(只有已申请或已取消状态可以删除)
        if (!LabRequestStatusEnum.REQUESTED.getCode().equals(labRequest.getRequestStatus()) &&
            !LabRequestStatusEnum.CANCELLED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_ALREADY_COMPLETED);
        }

        // 3. 删除明细
        labRequestItemMapper.deleteByRequestId(id);

        // 4. 删除主表
        labRequestMapper.deleteById(id);
    }

    @Override
    public HisLabRequestDO getLabRequest(Long id) {
        return labRequestMapper.selectById(id);
    }

    @Override
    public PageResult<HisLabRequestDO> getLabRequestPage(HisLabRequestPageReqVO pageReqVO) {
        return labRequestMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisLabRequestDO> getLabRequestListByPatientId(Long patientId) {
        return labRequestMapper.selectListByPatientId(patientId);
    }

    @Override
    public List<HisLabRequestDO> getLabRequestListBySourceId(Long sourceId) {
        return labRequestMapper.selectListBySourceId(sourceId);
    }

    @Override
    public List<HisLabRequestItemDO> getLabRequestItemList(Long requestId) {
        return labRequestItemMapper.selectListByRequestId(requestId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collectSpecimen(Long id, String specimenBarcode, Long collectorId, String collectorName) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.REQUESTED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_ALREADY_COLLECTED);
        }

        labRequest.setRequestStatus(LabRequestStatusEnum.COLLECTED.getCode());
        labRequest.setSpecimenStatus(SpecimenStatusEnum.COLLECTED.getCode());
        labRequest.setSpecimenBarcode(specimenBarcode);
        labRequest.setCollectTime(LocalDateTime.now());
        labRequest.setCollectorId(collectorId);
        labRequest.setCollectorName(collectorName);
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveSpecimen(Long id, Long receiverId, String receiverName) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.COLLECTED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_STATUS_ERROR);
        }

        labRequest.setRequestStatus(LabRequestStatusEnum.RECEIVED.getCode());
        labRequest.setSpecimenStatus(SpecimenStatusEnum.RECEIVED.getCode());
        labRequest.setReceiveTime(LocalDateTime.now());
        labRequest.setReceiverId(receiverId);
        labRequest.setReceiverName(receiverName);
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectSpecimen(Long id, String rejectReason) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.COLLECTED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_STATUS_ERROR);
        }

        labRequest.setSpecimenStatus(SpecimenStatusEnum.REJECTED.getCode());
        labRequest.setRejectReason(rejectReason);
        labRequest.setRejectTime(LocalDateTime.now());
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startLabRequest(Long id, Long technicianId, String technicianName) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.RECEIVED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_STATUS_ERROR);
        }

        labRequest.setRequestStatus(LabRequestStatusEnum.IN_PROGRESS.getCode());
        labRequest.setSpecimenStatus(SpecimenStatusEnum.TESTING.getCode());
        labRequest.setStartTime(LocalDateTime.now());
        labRequest.setTechnicianId(technicianId);
        labRequest.setTechnicianName(technicianName);
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeLabRequest(Long id) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.IN_PROGRESS.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_STATUS_ERROR);
        }

        labRequest.setRequestStatus(LabRequestStatusEnum.COMPLETED.getCode());
        labRequest.setSpecimenStatus(SpecimenStatusEnum.COMPLETED.getCode());
        labRequest.setEndTime(LocalDateTime.now());
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateReport(Long id, Long reportDoctorId, String reportDoctorName) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.COMPLETED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_STATUS_ERROR);
        }

        String reportNo = generateReportNo();
        labRequest.setRequestStatus(LabRequestStatusEnum.REPORTED.getCode());
        labRequest.setReportNo(reportNo);
        labRequest.setReportStatus(2); // 最终报告
        labRequest.setReportDoctorId(reportDoctorId);
        labRequest.setReportDoctorName(reportDoctorName);
        labRequest.setReportTime(LocalDateTime.now());
        labRequestMapper.updateById(labRequest);

        return reportNo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditReport(Long id, Long auditDoctorId, String auditDoctorName) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.REPORTED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REPORT_NOT_AUDITED);
        }

        labRequest.setRequestStatus(LabRequestStatusEnum.AUDITED.getCode());
        labRequest.setReportStatus(3); // 已审核
        labRequest.setAuditDoctorId(auditDoctorId);
        labRequest.setAuditDoctorName(auditDoctorName);
        labRequest.setAuditTime(LocalDateTime.now());
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishReport(Long id) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (!LabRequestStatusEnum.AUDITED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REPORT_NOT_AUDITED);
        }

        labRequest.setReportStatus(4); // 已发布
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportCriticalValue(Long id, String criticalValueContent) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);

        labRequest.setCriticalFlag(1);
        labRequest.setCriticalValueContent(criticalValueContent);
        labRequest.setCriticalReportTime(LocalDateTime.now());
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmCriticalValue(Long id, Long confirmUserId, String confirmUserName) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        if (labRequest.getCriticalFlag() == null || labRequest.getCriticalFlag() != 1) {
            throw exception(CRITICAL_VALUE_NOT_EXISTS);
        }
        if (labRequest.getCriticalConfirmTime() != null) {
            throw exception(CRITICAL_VALUE_ALREADY_CONFIRMED);
        }

        labRequest.setCriticalConfirmUserId(confirmUserId);
        labRequest.setCriticalConfirmUserName(confirmUserName);
        labRequest.setCriticalConfirmTime(LocalDateTime.now());
        labRequestMapper.updateById(labRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelLabRequest(Long id, String cancelReason) {
        HisLabRequestDO labRequest = validateLabRequestExists(id);
        // 只有已申请和已采集状态可以取消
        if (!LabRequestStatusEnum.REQUESTED.getCode().equals(labRequest.getRequestStatus()) &&
            !LabRequestStatusEnum.COLLECTED.getCode().equals(labRequest.getRequestStatus())) {
            throw exception(LAB_REQUEST_ALREADY_COMPLETED);
        }

        labRequest.setRequestStatus(LabRequestStatusEnum.CANCELLED.getCode());
        labRequest.setCancelReason(cancelReason);
        labRequest.setCancelTime(LocalDateTime.now());
        labRequestMapper.updateById(labRequest);
    }

    @Override
    public HisLabRequestDO validateLabRequestExists(Long id) {
        if (id == null) {
            return null;
        }
        HisLabRequestDO labRequest = labRequestMapper.selectById(id);
        if (labRequest == null) {
            throw exception(LAB_REQUEST_NOT_EXISTS);
        }
        return labRequest;
    }

    @Override
    public List<HisLabRequestDO> getPendingCollectList() {
        return labRequestMapper.selectPendingCollectList();
    }

    @Override
    public List<HisLabRequestDO> getInProgressList() {
        return labRequestMapper.selectInProgressList();
    }

    @Override
    public List<HisLabRequestDO> getCriticalValueUnconfirmedList() {
        return labRequestMapper.selectCriticalValueList();
    }

    @Override
    public HisLabRequestDO getLabRequestBySpecimenBarcode(String specimenBarcode) {
        return labRequestMapper.selectBySpecimenBarcode(specimenBarcode);
    }

    /**
     * 生成申请单号
     * 格式: LB + yyyyMMdd + 6位流水
     */
    private String generateRequestNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 1000000;
        return String.format("LB%s%06d", dateStr, seq);
    }

    /**
     * 生成报告编号
     * 格式: LR + yyyyMMdd + 6位流水
     */
    private String generateReportNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 1000000;
        return String.format("LR%s%06d", dateStr, seq);
    }

}