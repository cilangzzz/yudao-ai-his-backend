package cn.iocoder.yudao.module.his.service.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingHandoverDO;
import cn.iocoder.yudao.module.his.dal.mysql.nursing.HisNursingHandoverMapper;
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
 * 护理交接班 Service 实现类
 */
@Service
@Validated
public class HisNursingHandoverServiceImpl implements HisNursingHandoverService {

    @Resource
    private HisNursingHandoverMapper nursingHandoverMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createNursingHandover(HisNursingHandoverSaveReqVO createReqVO) {
        // 生成交接班编号
        String handoverNo = generateHandoverNo();

        // 创建交接班记录
        HisNursingHandoverDO handover = BeanUtils.toBean(createReqVO, HisNursingHandoverDO.class);
        handover.setHandoverNo(handoverNo);
        handover.setHandoverTime(LocalDateTime.now());
        handover.setStatus(0); // 待接班
        handover.setHandoverSignatureStatus(0); // 未签名
        handover.setTakeoverSignatureStatus(0); // 未签名

        nursingHandoverMapper.insert(handover);
        return handover.getId();
    }

    @Override
    public void updateNursingHandover(HisNursingHandoverSaveReqVO updateReqVO) {
        HisNursingHandoverDO handover = validateNursingHandoverExists(updateReqVO.getId());

        // 只有待接班状态可以修改
        if (handover.getStatus() != 0) {
            throw exception(NURSING_HANDOVER_STATUS_ERROR, "只有待接班状态的记录可以修改");
        }

        HisNursingHandoverDO updateObj = BeanUtils.toBean(updateReqVO, HisNursingHandoverDO.class);
        nursingHandoverMapper.updateById(updateObj);
    }

    @Override
    public void deleteNursingHandover(Long id) {
        HisNursingHandoverDO handover = validateNursingHandoverExists(id);

        // 只有待接班状态可以删除
        if (handover.getStatus() != 0) {
            throw exception(NURSING_HANDOVER_STATUS_ERROR, "只有待接班状态的记录可以删除");
        }

        nursingHandoverMapper.deleteById(id);
    }

    @Override
    public HisNursingHandoverDO getNursingHandover(Long id) {
        return nursingHandoverMapper.selectById(id);
    }

    @Override
    public PageResult<HisNursingHandoverDO> getNursingHandoverPage(HisNursingHandoverPageReqVO pageReqVO) {
        return nursingHandoverMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisNursingHandoverDO> getNursingHandoverListByWard(Long wardId) {
        return nursingHandoverMapper.selectListByWardId(wardId);
    }

    @Override
    public List<HisNursingHandoverDO> getPendingTakeoverList(Long wardId) {
        return nursingHandoverMapper.selectPendingTakeoverList(wardId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signHandover(Long id) {
        HisNursingHandoverDO handover = validateNursingHandoverExists(id);

        // 只有待接班状态可以签名
        if (handover.getStatus() != 0) {
            throw exception(NURSING_HANDOVER_STATUS_ERROR, "只有待接班状态的记录可以签名");
        }

        // 检查是否已签名
        if (handover.isHandoverSigned()) {
            throw exception(NURSING_HANDOVER_ALREADY_SIGNED, "已签名，不能重复签名");
        }

        HisNursingHandoverDO updateObj = new HisNursingHandoverDO();
        updateObj.setId(id);
        updateObj.setHandoverSignatureStatus(1);
        updateObj.setHandoverSignatureTime(LocalDateTime.now());

        nursingHandoverMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void takeover(Long id, Long takeoverNurseId) {
        HisNursingHandoverDO handover = validateNursingHandoverExists(id);

        // 检查是否可以接班
        if (!handover.canTakeover()) {
            throw exception(NURSING_HANDOVER_CANNOT_TAKEOVER, "交班人未签名或已接班，无法进行接班");
        }

        HisNursingHandoverDO updateObj = new HisNursingHandoverDO();
        updateObj.setId(id);
        updateObj.setTakeoverNurseId(takeoverNurseId);
        updateObj.setTakeoverTime(LocalDateTime.now());
        updateObj.setTakeoverSignatureStatus(1);
        updateObj.setTakeoverSignatureTime(LocalDateTime.now());
        updateObj.setStatus(1); // 已接班

        nursingHandoverMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelNursingHandover(Long id, String reason) {
        HisNursingHandoverDO handover = validateNursingHandoverExists(id);

        // 检查是否可以作废
        if (!handover.canCancel()) {
            throw exception(NURSING_HANDOVER_CANNOT_CANCEL, "已接班，无法作废");
        }

        HisNursingHandoverDO updateObj = new HisNursingHandoverDO();
        updateObj.setId(id);
        updateObj.setStatus(2); // 已作废
        updateObj.setRemark(reason);

        nursingHandoverMapper.updateById(updateObj);
    }

    @Override
    public HisNursingHandoverDO validateNursingHandoverExists(Long id) {
        if (id == null) {
            return null;
        }
        HisNursingHandoverDO handover = nursingHandoverMapper.selectById(id);
        if (handover == null) {
            throw exception(NURSING_HANDOVER_NOT_EXISTS);
        }
        return handover;
    }

    @Override
    public HisNursingHandoverDO getLatestNursingHandover(Long wardId) {
        return nursingHandoverMapper.selectLatestByWard(wardId);
    }

    /**
     * 生成交接班编号
     * 格式: HO + 年月日 + 4位序号，如 HO202606170001
     */
    private String generateHandoverNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String prefix = "HO" + dateStr;

        // 查询当天最大编号
        HisNursingHandoverDO lastHandover = nursingHandoverMapper.selectOne(
                new cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX<HisNursingHandoverDO>()
                        .likeRight(HisNursingHandoverDO::getHandoverNo, prefix)
                        .orderByDesc(HisNursingHandoverDO::getId)
                        .last("LIMIT 1")
        );

        if (lastHandover == null) {
            return prefix + "0001";
        }

        String lastNo = lastHandover.getHandoverNo();
        int seq = Integer.parseInt(lastNo.substring(lastNo.length() - 4)) + 1;
        return prefix + String.format("%04d", seq);
    }
}