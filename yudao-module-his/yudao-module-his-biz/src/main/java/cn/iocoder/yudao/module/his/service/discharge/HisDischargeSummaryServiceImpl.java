package cn.iocoder.yudao.module.his.service.discharge;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeSummaryPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeSummaryReviewReqVO;
import cn.iocoder.yudao.module.his.controller.admin.discharge.vo.HisDischargeSummarySaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeSummaryDO;
import cn.iocoder.yudao.module.his.dal.mysql.discharge.HisDischargeSummaryMapper;
import cn.iocoder.yudao.module.his.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 出院小结 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisDischargeSummaryServiceImpl implements HisDischargeSummaryService {

    @Resource
    private HisDischargeSummaryMapper dischargeSummaryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDischargeSummary(HisDischargeSummarySaveReqVO createReqVO) {
        // 校验是否已存在出院小结
        HisDischargeSummaryDO existingSummary = dischargeSummaryMapper.selectByDischargeId(createReqVO.getDischargeId());
        if (existingSummary != null) {
            throw exception(ErrorCodeConstants.DISCHARGE_SUMMARY_ALREADY_EXISTS);
        }

        // 插入
        HisDischargeSummaryDO dischargeSummary = BeanUtils.toBean(createReqVO, HisDischargeSummaryDO.class);
        // 设置默认状态为待审核
        dischargeSummary.setSummaryStatus(1);
        // 设置书写时间
        if (dischargeSummary.getAuthorTime() == null) {
            dischargeSummary.setAuthorTime(LocalDateTime.now());
        }
        dischargeSummaryMapper.insert(dischargeSummary);
        return dischargeSummary.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDischargeSummary(HisDischargeSummarySaveReqVO updateReqVO) {
        // 校验存在
        HisDischargeSummaryDO existSummary = validateDischargeSummaryCanModify(updateReqVO.getId());

        // 更新
        HisDischargeSummaryDO updateObj = BeanUtils.toBean(updateReqVO, HisDischargeSummaryDO.class);
        // 更新书写时间
        updateObj.setAuthorTime(LocalDateTime.now());
        dischargeSummaryMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDischargeSummary(Long id) {
        // 校验存在
        HisDischargeSummaryDO existSummary = validateDischargeSummaryCanModify(id);

        // 删除
        dischargeSummaryMapper.deleteById(id);
    }

    @Override
    public HisDischargeSummaryDO getDischargeSummary(Long id) {
        return dischargeSummaryMapper.selectById(id);
    }

    @Override
    public HisDischargeSummaryDO getDischargeSummaryByDischargeId(Long dischargeId) {
        return dischargeSummaryMapper.selectByDischargeId(dischargeId);
    }

    @Override
    public HisDischargeSummaryDO getDischargeSummaryByAdmissionId(Long admissionId) {
        return dischargeSummaryMapper.selectByAdmissionId(admissionId);
    }

    @Override
    public PageResult<HisDischargeSummaryDO> getDischargeSummaryPage(HisDischargeSummaryPageReqVO pageReqVO) {
        return dischargeSummaryMapper.selectPage(pageReqVO, new LambdaQueryWrapperX<HisDischargeSummaryDO>()
                .eqIfPresent(HisDischargeSummaryDO::getDischargeId, pageReqVO.getDischargeId())
                .eqIfPresent(HisDischargeSummaryDO::getAdmissionId, pageReqVO.getAdmissionId())
                .eqIfPresent(HisDischargeSummaryDO::getPatientId, pageReqVO.getPatientId())
                .likeIfPresent(HisDischargeSummaryDO::getPatientName, pageReqVO.getPatientName())
                .likeIfPresent(HisDischargeSummaryDO::getDischargeDiagnosis, pageReqVO.getDischargeDiagnosis())
                .eqIfPresent(HisDischargeSummaryDO::getAuthorDoctor, pageReqVO.getAuthorDoctor())
                .eqIfPresent(HisDischargeSummaryDO::getReviewDoctor, pageReqVO.getReviewDoctor())
                .eqIfPresent(HisDischargeSummaryDO::getSummaryStatus, pageReqVO.getSummaryStatus())
                .betweenIfPresent(HisDischargeSummaryDO::getAuthorTime, pageReqVO.getAuthorTimeBegin(), pageReqVO.getAuthorTimeEnd())
                .betweenIfPresent(HisDischargeSummaryDO::getReviewTime, pageReqVO.getReviewTimeBegin(), pageReqVO.getReviewTimeEnd())
                .betweenIfPresent(HisDischargeSummaryDO::getCreateTime, pageReqVO.getCreateTimeBegin(), pageReqVO.getCreateTimeEnd())
                .orderByDesc(HisDischargeSummaryDO::getId));
    }

    @Override
    public List<HisDischargeSummaryDO> getDischargeSummaryListByPatientId(Long patientId) {
        return dischargeSummaryMapper.selectListByPatientId(patientId);
    }

    @Override
    public List<HisDischargeSummaryDO> getPendingReviewList() {
        return dischargeSummaryMapper.selectListByStatus(1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewDischargeSummary(HisDischargeSummaryReviewReqVO reqVO) {
        // 校验可以审核
        HisDischargeSummaryDO existSummary = validateDischargeSummaryCanReview(reqVO.getId());

        // 审核通过
        HisDischargeSummaryDO updateObj = new HisDischargeSummaryDO();
        updateObj.setId(reqVO.getId());
        updateObj.setReviewDoctor(reqVO.getReviewDoctor());
        updateObj.setReviewDoctorName(reqVO.getReviewDoctorName());
        updateObj.setReviewTime(LocalDateTime.now());
        updateObj.setSummaryStatus(2); // 已审核
        dischargeSummaryMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawReview(Long id) {
        // 校验存在且已审核
        HisDischargeSummaryDO existSummary = validateDischargeSummaryExists(id);
        if (!existSummary.isReviewed()) {
            throw exception(ErrorCodeConstants.DISCHARGE_SUMMARY_NOT_REVIEWED);
        }

        // 撤回审核
        HisDischargeSummaryDO updateObj = new HisDischargeSummaryDO();
        updateObj.setId(id);
        updateObj.setReviewDoctor(null);
        updateObj.setReviewDoctorName(null);
        updateObj.setReviewTime(null);
        updateObj.setSummaryStatus(1); // 待审核
        dischargeSummaryMapper.updateById(updateObj);
    }

    @Override
    public HisDischargeSummaryDO validateDischargeSummaryExists(Long id) {
        HisDischargeSummaryDO dischargeSummary = dischargeSummaryMapper.selectById(id);
        if (dischargeSummary == null) {
            throw exception(ErrorCodeConstants.DISCHARGE_SUMMARY_NOT_EXISTS);
        }
        return dischargeSummary;
    }

    @Override
    public HisDischargeSummaryDO validateDischargeSummaryCanModify(Long id) {
        HisDischargeSummaryDO dischargeSummary = validateDischargeSummaryExists(id);
        if (!dischargeSummary.canModify()) {
            throw exception(ErrorCodeConstants.DISCHARGE_SUMMARY_CANNOT_MODIFY);
        }
        return dischargeSummary;
    }

    @Override
    public HisDischargeSummaryDO validateDischargeSummaryCanReview(Long id) {
        HisDischargeSummaryDO dischargeSummary = validateDischargeSummaryExists(id);
        if (!dischargeSummary.canReview()) {
            throw exception(ErrorCodeConstants.DISCHARGE_SUMMARY_CANNOT_REVIEW);
        }
        return dischargeSummary;
    }

    @Override
    public int countPendingReview() {
        Long count = dischargeSummaryMapper.selectCount(HisDischargeSummaryDO::getSummaryStatus, 1);
        return count != null ? count.intValue() : 0;
    }
}