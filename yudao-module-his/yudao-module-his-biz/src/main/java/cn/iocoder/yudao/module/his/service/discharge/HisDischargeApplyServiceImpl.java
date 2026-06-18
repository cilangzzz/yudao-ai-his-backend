package cn.iocoder.yudao.module.his.service.discharge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplyApproveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplyPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplyRejectReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeApplySaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeApplyDO;
import cn.iocoder.yudao.module.his.dal.mysql.discharge.HisDischargeApplyMapper;
import cn.iocoder.yudao.module.his.service.admission.HisAdmissionService;
import cn.iocoder.yudao.module.his.service.bed.HisBedService;
import cn.iocoder.yudao.module.his.service.order.HisOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 出院申请 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisDischargeApplyServiceImpl implements HisDischargeApplyService {

    @Resource
    private HisDischargeApplyMapper dischargeApplyMapper;

    @Resource
    private HisAdmissionService admissionService;

    @Resource
    private HisBedService bedService;

    @Resource
    private HisOrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDischargeApply(HisDischargeApplySaveReqVO createReqVO) {
        // 1. 校验入院记录
        HisAdmissionDO admission = admissionService.validateAdmissionInHospital(createReqVO.getAdmissionId());

        // 2. 校验是否已有出院申请
        HisDischargeApplyDO existingApply = dischargeApplyMapper.selectValidByAdmissionId(createReqVO.getAdmissionId());
        if (existingApply != null) {
            throw exception(DISCHARGE_APPLY_ALREADY_EXISTS);
        }

        // 3. 创建出院申请
        HisDischargeApplyDO dischargeApply = BeanUtils.toBean(createReqVO, HisDischargeApplyDO.class);
        dischargeApply.setApplyNo(generateApplyNo());
        dischargeApply.setAdmissionNo(admission.getAdmissionNo());
        dischargeApply.setPatientId(admission.getPatientId());
        dischargeApply.setPatientName(admission.getPatientName());
        dischargeApply.setDeptId(admission.getAdmissionDept());
        dischargeApply.setDeptName(admission.getAdmissionDeptName());
        dischargeApply.setWardId(admission.getWardId());
        dischargeApply.setWardName(admission.getWardName());
        dischargeApply.setBedId(admission.getBedId());
        dischargeApply.setBedNo(admission.getBedNo());
        dischargeApply.setAttendingDoctorId(admission.getAttendingDoctor());
        dischargeApply.setAttendingDoctorName(admission.getAttendingDoctorName());
        dischargeApply.setAdmissionDate(admission.getAdmissionDate());
        dischargeApply.setAdmissionDiagnosis(admission.getAdmissionDiagnosis());
        dischargeApply.setAdmissionDiagnosisCode(admission.getDiagnosisCode());
        dischargeApply.setApplyTime(LocalDateTime.now());
        dischargeApply.setStatus(1); // 待审批
        dischargeApply.setOrderStopped(0); // 未停止医嘱
        dischargeApply.setFeeSettled(0); // 未结算费用

        // 设置默认出院日期
        if (createReqVO.getDischargeDate() == null) {
            dischargeApply.setDischargeDate(LocalDateTime.now());
        }

        dischargeApplyMapper.insert(dischargeApply);

        return dischargeApply.getId();
    }

    @Override
    public void updateDischargeApply(HisDischargeApplySaveReqVO updateReqVO) {
        // 校验存在
        HisDischargeApplyDO dischargeApply = validateDischargeApplyExists(updateReqVO.getId());
        // 校验状态
        if (!dischargeApply.isPending()) {
            throw exception(DISCHARGE_APPLY_STATUS_ERROR);
        }
        // 更新
        HisDischargeApplyDO updateObj = BeanUtils.toBean(updateReqVO, HisDischargeApplyDO.class);
        dischargeApplyMapper.updateById(updateObj);
    }

    @Override
    public void deleteDischargeApply(Long id) {
        // 校验存在
        HisDischargeApplyDO dischargeApply = validateDischargeApplyExists(id);
        // 校验状态
        if (!dischargeApply.isPending() && !dischargeApply.isRejected()) {
            throw exception(DISCHARGE_APPLY_STATUS_ERROR);
        }
        // 删除
        dischargeApplyMapper.deleteById(id);
    }

    @Override
    public HisDischargeApplyDO getDischargeApply(Long id) {
        return dischargeApplyMapper.selectById(id);
    }

    @Override
    public HisDischargeApplyDO getDischargeApplyByNo(String applyNo) {
        return dischargeApplyMapper.selectByApplyNo(applyNo);
    }

    @Override
    public HisDischargeApplyDO getDischargeApplyByAdmissionId(Long admissionId) {
        return dischargeApplyMapper.selectByAdmissionId(admissionId);
    }

    @Override
    public PageResult<HisDischargeApplyDO> getDischargeApplyPage(HisDischargeApplyPageReqVO pageReqVO) {
        return dischargeApplyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDischargeApplyDO> getDischargeApplyListByPatientId(Long patientId) {
        return dischargeApplyMapper.selectByPatientId(patientId);
    }

    @Override
    public List<HisDischargeApplyDO> getPendingDischargeApplyListByDept(Long deptId) {
        return dischargeApplyMapper.selectPendingByDept(deptId);
    }

    @Override
    public List<HisDischargeApplyDO> getPendingDischargeApplyList() {
        return dischargeApplyMapper.selectPendingList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveDischargeApply(HisDischargeApplyApproveReqVO reqVO) {
        // 1. 校验出院申请
        HisDischargeApplyDO dischargeApply = validateDischargeApplyCanApprove(reqVO.getId());

        // 2. 更新审批信息
        HisDischargeApplyDO updateObj = new HisDischargeApplyDO();
        updateObj.setId(reqVO.getId());
        updateObj.setStatus(2); // 已审批
        updateObj.setAuditDoctorId(reqVO.getAuditDoctorId());
        updateObj.setAuditDoctorName(reqVO.getAuditDoctorName());
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setAuditRemark(reqVO.getAuditRemark());

        dischargeApplyMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectDischargeApply(HisDischargeApplyRejectReqVO reqVO) {
        // 1. 校验出院申请
        HisDischargeApplyDO dischargeApply = validateDischargeApplyCanApprove(reqVO.getId());

        // 2. 更新驳回信息
        HisDischargeApplyDO updateObj = new HisDischargeApplyDO();
        updateObj.setId(reqVO.getId());
        updateObj.setStatus(3); // 已驳回
        updateObj.setAuditDoctorId(reqVO.getAuditDoctorId());
        updateObj.setAuditDoctorName(reqVO.getAuditDoctorName());
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setRejectReason(reqVO.getRejectReason());

        dischargeApplyMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelDischargeApply(Long id, String cancelReason) {
        // 1. 校验出院申请
        HisDischargeApplyDO dischargeApply = validateDischargeApplyExists(id);
        if (!dischargeApply.canCancel()) {
            throw exception(DISCHARGE_APPLY_STATUS_ERROR);
        }

        // 2. 更新取消信息
        HisDischargeApplyDO updateObj = new HisDischargeApplyDO();
        updateObj.setId(id);
        updateObj.setStatus(5); // 已取消
        updateObj.setCancelReason(cancelReason);
        updateObj.setCancelTime(LocalDateTime.now());

        dischargeApplyMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopOrders(Long id) {
        // 1. 校验出院申请
        HisDischargeApplyDO dischargeApply = validateDischargeApplyExists(id);
        if (!dischargeApply.isApproved()) {
            throw exception(DISCHARGE_APPLY_NOT_APPROVED);
        }

        // 2. 停止该入院记录的所有长期医嘱
        orderService.stopAllOrdersByAdmission(dischargeApply.getAdmissionId());

        // 3. 更新医嘱停止状态
        HisDischargeApplyDO updateObj = new HisDischargeApplyDO();
        updateObj.setId(id);
        updateObj.setOrderStopped(1);
        updateObj.setOrderStopTime(LocalDateTime.now());

        dischargeApplyMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmDischarge(Long id) {
        // 1. 校验出院申请
        HisDischargeApplyDO dischargeApply = validateDischargeApplyCanDischarge(id);

        // 2. 校验费用是否已结算
        if (dischargeApply.getFeeSettled() != 1) {
            throw exception(DISCHARGE_FEE_NOT_SETTLED);
        }

        // 3. 更新出院申请状态
        HisDischargeApplyDO updateObj = new HisDischargeApplyDO();
        updateObj.setId(id);
        updateObj.setStatus(4); // 已出院
        updateObj.setDischargeDate(LocalDateTime.now());

        dischargeApplyMapper.updateById(updateObj);

        // 4. 更新入院记录状态
        admissionService.confirmDischarge(dischargeApply.getAdmissionId());

        // 5. 释放床位
        if (dischargeApply.getBedId() != null) {
            bedService.releaseBed(dischargeApply.getBedId());
        }
    }

    @Override
    public HisDischargeApplyDO validateDischargeApplyExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDischargeApplyDO dischargeApply = dischargeApplyMapper.selectById(id);
        if (dischargeApply == null) {
            throw exception(DISCHARGE_APPLY_NOT_EXISTS);
        }
        return dischargeApply;
    }

    @Override
    public HisDischargeApplyDO validateDischargeApplyCanApprove(Long id) {
        HisDischargeApplyDO dischargeApply = validateDischargeApplyExists(id);
        if (!dischargeApply.canApprove()) {
            throw exception(DISCHARGE_APPLY_STATUS_ERROR);
        }
        return dischargeApply;
    }

    @Override
    public HisDischargeApplyDO validateDischargeApplyCanDischarge(Long id) {
        HisDischargeApplyDO dischargeApply = validateDischargeApplyExists(id);
        if (!dischargeApply.canDischarge()) {
            throw exception(DISCHARGE_APPLY_NOT_APPROVED);
        }
        return dischargeApply;
    }

    @Override
    public int countTodayApply() {
        return dischargeApplyMapper.countTodayApply();
    }

    @Override
    public int countPending() {
        return dischargeApplyMapper.countPending();
    }

    /**
     * 生成申请单号
     * 格式: D + yyyyMMdd + 4位流水号
     */
    private String generateApplyNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("D%s%04d", dateStr, seq);
    }

}