package cn.iocoder.yudao.module.his.service.exam;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamRequestItemSaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamRequestPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamRequestSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestDO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamRequestItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.exam.HisExamRequestItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.exam.HisExamRequestMapper;
import cn.iocoder.yudao.module.his.enums.ExamRequestStatusEnum;
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
 * 检查申请 Service 实现类
 */
@Service
@Validated
public class HisExamRequestServiceImpl implements HisExamRequestService {

    @Resource
    private HisExamRequestMapper examRequestMapper;

    @Resource
    private HisExamRequestItemMapper examRequestItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createExamRequest(HisExamRequestSaveReqVO createReqVO) {
        // 1. 插入申请主表
        HisExamRequestDO examRequest = BeanUtils.toBean(createReqVO, HisExamRequestDO.class);
        examRequest.setRequestNo(generateRequestNo());
        examRequest.setRequestStatus(ExamRequestStatusEnum.REQUESTED.getCode());
        examRequest.setReportStatus(0);
        examRequest.setPayStatus(0);
        examRequest.setTotalAmount(BigDecimal.ZERO);
        examRequest.setInsuranceAmount(BigDecimal.ZERO);
        examRequest.setSelfAmount(BigDecimal.ZERO);
        examRequestMapper.insert(examRequest);

        // 2. 插入明细表并计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        int sortOrder = 0;
        for (HisExamRequestItemSaveReqVO itemVO : createReqVO.getItems()) {
            HisExamRequestItemDO item = BeanUtils.toBean(itemVO, HisExamRequestItemDO.class);
            item.setRequestId(examRequest.getId());
            item.setItemStatus(1); // 待检查
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
            examRequestItemMapper.insert(item);
        }

        // 3. 更新总金额
        examRequest.setTotalAmount(totalAmount);
        examRequestMapper.updateById(examRequest);

        return examRequest.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExamRequest(HisExamRequestSaveReqVO updateReqVO) {
        // 1. 校验存在
        HisExamRequestDO existRequest = validateExamRequestExists(updateReqVO.getId());

        // 2. 校验状态(只有已申请状态可以修改)
        if (!ExamRequestStatusEnum.REQUESTED.getCode().equals(existRequest.getRequestStatus())) {
            throw exception(EXAM_REQUEST_STATUS_ERROR);
        }

        // 3. 更新主表
        HisExamRequestDO updateObj = BeanUtils.toBean(updateReqVO, HisExamRequestDO.class);
        examRequestMapper.updateById(updateObj);

        // 4. 删除旧明细
        examRequestItemMapper.deleteByRequestId(updateReqVO.getId());

        // 5. 插入新明细
        BigDecimal totalAmount = BigDecimal.ZERO;
        int sortOrder = 0;
        for (HisExamRequestItemSaveReqVO itemVO : updateReqVO.getItems()) {
            HisExamRequestItemDO item = BeanUtils.toBean(itemVO, HisExamRequestItemDO.class);
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
            examRequestItemMapper.insert(item);
        }

        // 6. 更新总金额
        updateObj.setTotalAmount(totalAmount);
        examRequestMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExamRequest(Long id) {
        // 1. 校验存在
        HisExamRequestDO examRequest = validateExamRequestExists(id);

        // 2. 校验状态(只有已申请或已取消状态可以删除)
        if (!ExamRequestStatusEnum.REQUESTED.getCode().equals(examRequest.getRequestStatus()) &&
            !ExamRequestStatusEnum.CANCELLED.getCode().equals(examRequest.getRequestStatus())) {
            throw exception(EXAM_REQUEST_CANNOT_DELETE);
        }

        // 3. 删除明细
        examRequestItemMapper.deleteByRequestId(id);

        // 4. 删除主表
        examRequestMapper.deleteById(id);
    }

    @Override
    public HisExamRequestDO getExamRequest(Long id) {
        return examRequestMapper.selectById(id);
    }

    @Override
    public HisExamRequestDO getExamRequestWithItems(Long id) {
        HisExamRequestDO examRequest = examRequestMapper.selectById(id);
        if (examRequest != null) {
            List<HisExamRequestItemDO> items = examRequestItemMapper.selectListByRequestId(id);
            // 可以在DO中添加items字段，或者返回DTO
        }
        return examRequest;
    }

    @Override
    public PageResult<HisExamRequestDO> getExamRequestPage(HisExamRequestPageReqVO pageReqVO) {
        return examRequestMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisExamRequestDO> getExamRequestListByPatientId(Long patientId) {
        return examRequestMapper.selectListByPatientId(patientId);
    }

    @Override
    public List<HisExamRequestDO> getExamRequestListByEncounterId(Long encounterId) {
        return examRequestMapper.selectListByEncounterId(encounterId);
    }

    @Override
    public List<HisExamRequestItemDO> getExamRequestItemList(Long requestId) {
        return examRequestItemMapper.selectListByRequestId(requestId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appointExamRequest(Long id, LocalDateTime appointmentTime) {
        HisExamRequestDO examRequest = validateExamRequestExists(id);
        if (!ExamRequestStatusEnum.REQUESTED.getCode().equals(examRequest.getRequestStatus())) {
            throw exception(EXAM_REQUEST_STATUS_ERROR);
        }
        examRequest.setRequestStatus(ExamRequestStatusEnum.APPOINTED.getCode());
        examRequest.setAppointmentTime(appointmentTime);
        examRequestMapper.updateById(examRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkInExamRequest(Long id) {
        HisExamRequestDO examRequest = validateExamRequestExists(id);
        if (!ExamRequestStatusEnum.APPOINTED.getCode().equals(examRequest.getRequestStatus())) {
            throw exception(EXAM_REQUEST_STATUS_ERROR);
        }
        examRequest.setRequestStatus(ExamRequestStatusEnum.CHECKED_IN.getCode());
        examRequest.setCheckInTime(LocalDateTime.now());
        examRequestMapper.updateById(examRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startExamRequest(Long id) {
        HisExamRequestDO examRequest = validateExamRequestExists(id);
        if (!ExamRequestStatusEnum.CHECKED_IN.getCode().equals(examRequest.getRequestStatus())) {
            throw exception(EXAM_REQUEST_STATUS_ERROR);
        }
        examRequest.setRequestStatus(ExamRequestStatusEnum.IN_PROGRESS.getCode());
        examRequest.setStartTime(LocalDateTime.now());
        examRequestMapper.updateById(examRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeExamRequest(Long id) {
        HisExamRequestDO examRequest = validateExamRequestExists(id);
        if (!ExamRequestStatusEnum.IN_PROGRESS.getCode().equals(examRequest.getRequestStatus())) {
            throw exception(EXAM_REQUEST_STATUS_ERROR);
        }
        examRequest.setRequestStatus(ExamRequestStatusEnum.COMPLETED.getCode());
        examRequest.setEndTime(LocalDateTime.now());
        examRequestMapper.updateById(examRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelExamRequest(Long id, String cancelReason) {
        HisExamRequestDO examRequest = validateExamRequestExists(id);
        // 只有已申请和已预约状态可以取消
        if (!ExamRequestStatusEnum.REQUESTED.getCode().equals(examRequest.getRequestStatus()) &&
            !ExamRequestStatusEnum.APPOINTED.getCode().equals(examRequest.getRequestStatus())) {
            throw exception(EXAM_REQUEST_CANNOT_CANCEL);
        }
        examRequest.setRequestStatus(ExamRequestStatusEnum.CANCELLED.getCode());
        examRequest.setCancelReason(cancelReason);
        examRequest.setCancelTime(LocalDateTime.now());
        examRequestMapper.updateById(examRequest);
    }

    @Override
    public HisExamRequestDO validateExamRequestExists(Long id) {
        if (id == null) {
            return null;
        }
        HisExamRequestDO examRequest = examRequestMapper.selectById(id);
        if (examRequest == null) {
            throw exception(EXAM_REQUEST_NOT_EXISTS);
        }
        return examRequest;
    }

    /**
     * 生成申请单号
     * 格式: EX + yyyyMMdd + 6位流水
     */
    private String generateRequestNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 1000000;
        return String.format("EX%s%06d", dateStr, seq);
    }

}
