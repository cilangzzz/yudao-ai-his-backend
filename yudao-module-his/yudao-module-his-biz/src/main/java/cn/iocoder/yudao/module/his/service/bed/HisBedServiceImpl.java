package cn.iocoder.yudao.module.his.service.bed;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.bed.vo.HisBedPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.bed.vo.HisBedSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.bed.HisBedDO;
import cn.iocoder.yudao.module.his.dal.dataobject.ward.HisWardDO;
import cn.iocoder.yudao.module.his.dal.mysql.bed.HisBedMapper;
import cn.iocoder.yudao.module.his.service.ward.HisWardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 床位 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisBedServiceImpl implements HisBedService {

    @Resource
    private HisBedMapper bedMapper;

    @Resource
    private HisWardService wardService;

    @Override
    public Long createBed(HisBedSaveReqVO createReqVO) {
        // 校验病区存在
        HisWardDO ward = wardService.validateWardExists(createReqVO.getWardId());
        // 校验床号唯一
        validateBedNoUnique(createReqVO.getWardId(), null, createReqVO.getBedNo());
        // 插入
        HisBedDO bed = BeanUtils.toBean(createReqVO, HisBedDO.class);
        bed.setWardName(ward.getWardName());
        if (bed.getBedStatus() == null) {
            bed.setBedStatus(1); // 默认空床
        }
        bedMapper.insert(bed);
        // 更新病区床位统计
        wardService.updateWardBedStat(createReqVO.getWardId());
        return bed.getId();
    }

    @Override
    public void updateBed(HisBedSaveReqVO updateReqVO) {
        // 校验存在
        HisBedDO bed = validateBedExists(updateReqVO.getId());
        // 校验床号唯一
        validateBedNoUnique(updateReqVO.getWardId(), updateReqVO.getId(), updateReqVO.getBedNo());
        // 更新
        HisBedDO updateObj = BeanUtils.toBean(updateReqVO, HisBedDO.class);
        // 如果病区变更，更新病区名称
        if (!bed.getWardId().equals(updateReqVO.getWardId())) {
            HisWardDO ward = wardService.validateWardExists(updateReqVO.getWardId());
            updateObj.setWardName(ward.getWardName());
        }
        bedMapper.updateById(updateObj);
        // 更新病区床位统计
        if (!bed.getWardId().equals(updateReqVO.getWardId())) {
            wardService.updateWardBedStat(bed.getWardId());
            wardService.updateWardBedStat(updateReqVO.getWardId());
        }
    }

    @Override
    public void deleteBed(Long id) {
        // 校验存在
        HisBedDO bed = validateBedExists(id);
        // 校验是否被占用
        if (bed.isOccupied()) {
            throw exception(BED_ALREADY_OCCUPIED);
        }
        // 删除
        bedMapper.deleteById(id);
        // 更新病区床位统计
        wardService.updateWardBedStat(bed.getWardId());
    }

    @Override
    public HisBedDO getBed(Long id) {
        return bedMapper.selectById(id);
    }

    @Override
    public PageResult<HisBedDO> getBedPage(HisBedPageReqVO pageReqVO) {
        return bedMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisBedDO> getBedListByWardId(Long wardId) {
        return bedMapper.selectListByWardId(wardId);
    }

    @Override
    public List<HisBedDO> getEmptyBedListByWardId(Long wardId) {
        return bedMapper.selectEmptyBedsByWardId(wardId);
    }

    @Override
    public List<HisBedDO> getEmptyBedList() {
        return bedMapper.selectEmptyBeds();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void occupyBed(Long id, Long patientId, String patientName, Long admissionId) {
        // 校验床位存在且可用
        HisBedDO bed = validateBedAvailable(id);
        // 占用床位
        int rows = bedMapper.occupyBed(id, patientId, patientName, admissionId, "system");
        if (rows == 0) {
            throw exception(BED_ALREADY_OCCUPIED);
        }
        // 更新病区床位统计
        wardService.updateWardBedStat(bed.getWardId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void releaseBed(Long id) {
        // 校验床位存在
        HisBedDO bed = validateBedExists(id);
        // 释放床位
        int rows = bedMapper.releaseBed(id, "system");
        if (rows == 0) {
            throw exception(BED_NOT_EXISTS);
        }
        // 更新病区床位统计
        wardService.updateWardBedStat(bed.getWardId());
    }

    @Override
    public void cleanComplete(Long id) {
        // 校验床位存在
        validateBedExists(id);
        // 清洁完成
        int rows = bedMapper.cleanComplete(id, "system");
        if (rows == 0) {
            throw exception(BED_NOT_EXISTS);
        }
    }

    @Override
    public void updateBedStatus(Long id, Integer status) {
        // 校验床位存在
        validateBedExists(id);
        // 更新状态
        HisBedDO updateObj = new HisBedDO();
        updateObj.setId(id);
        updateObj.setBedStatus(status);
        bedMapper.updateById(updateObj);
    }

    @Override
    public HisBedDO validateBedExists(Long id) {
        if (id == null) {
            return null;
        }
        HisBedDO bed = bedMapper.selectById(id);
        if (bed == null) {
            throw exception(BED_NOT_EXISTS);
        }
        return bed;
    }

    @Override
    public HisBedDO validateBedAvailable(Long id) {
        HisBedDO bed = validateBedExists(id);
        if (!bed.isAvailable()) {
            throw exception(BED_ALREADY_OCCUPIED);
        }
        return bed;
    }

    /**
     * 校验床号在病区内唯一
     */
    private void validateBedNoUnique(Long wardId, Long id, String bedNo) {
        HisBedDO bed = bedMapper.selectByWardIdAndBedNo(wardId, bedNo);
        if (bed == null) {
            return;
        }
        if (id == null) {
            throw exception(BED_NOT_EXISTS); // TODO: 添加具体错误码
        }
        if (!bed.getId().equals(id)) {
            throw exception(BED_NOT_EXISTS); // TODO: 添加具体错误码
        }
    }
}
