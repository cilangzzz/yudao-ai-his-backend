package cn.iocoder.yudao.module.his.service.ward;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.ward.vo.HisWardPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.ward.vo.HisWardSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.bed.HisBedDO;
import cn.iocoder.yudao.module.his.dal.dataobject.ward.HisWardDO;
import cn.iocoder.yudao.module.his.dal.mysql.bed.HisBedMapper;
import cn.iocoder.yudao.module.his.dal.mysql.ward.HisWardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 病区 Service 实现类
 *
 * @author yudao
 */
@Service
@Validated
public class HisWardServiceImpl implements HisWardService {

    @Resource
    private HisWardMapper wardMapper;

    @Resource
    private HisBedMapper bedMapper;

    @Override
    public Long createWard(HisWardSaveReqVO createReqVO) {
        // 校验编码唯一
        validateWardCodeUnique(null, createReqVO.getWardCode());
        // 插入
        HisWardDO ward = BeanUtils.toBean(createReqVO, HisWardDO.class);
        ward.setBedCount(0);
        ward.setUsedBedCount(0);
        wardMapper.insert(ward);
        return ward.getId();
    }

    @Override
    public void updateWard(HisWardSaveReqVO updateReqVO) {
        // 校验存在
        validateWardExists(updateReqVO.getId());
        // 校验编码唯一
        validateWardCodeUnique(updateReqVO.getId(), updateReqVO.getWardCode());
        // 更新
        HisWardDO updateObj = BeanUtils.toBean(updateReqVO, HisWardDO.class);
        wardMapper.updateById(updateObj);
    }

    @Override
    public void deleteWard(Long id) {
        // 校验存在
        HisWardDO ward = validateWardExists(id);
        // 校验是否有床位
        List<HisBedDO> beds = bedMapper.selectListByWardId(id);
        if (!beds.isEmpty()) {
            throw exception(WARD_NOT_EXISTS); // TODO: 添加具体错误码
        }
        // 删除
        wardMapper.deleteById(id);
    }

    @Override
    public HisWardDO getWard(Long id) {
        return wardMapper.selectById(id);
    }

    @Override
    public PageResult<HisWardDO> getWardPage(HisWardPageReqVO pageReqVO) {
        return wardMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisWardDO> getWardList() {
        return wardMapper.selectAll();
    }

    @Override
    public List<HisWardDO> getWardListByDeptId(Long deptId) {
        return wardMapper.selectListByDeptId(deptId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWardBedStat(Long wardId) {
        HisWardDO ward = wardMapper.selectById(wardId);
        if (ward == null) {
            return;
        }
        // 统计床位数量
        List<HisBedDO> beds = bedMapper.selectListByWardId(wardId);
        int totalCount = beds.size();
        int usedCount = (int) beds.stream().filter(b -> b.getBedStatus() == 2).count();

        // 更新统计
        HisWardDO updateObj = new HisWardDO();
        updateObj.setId(wardId);
        updateObj.setBedCount(totalCount);
        updateObj.setUsedBedCount(usedCount);
        wardMapper.updateById(updateObj);
    }

    @Override
    public HisWardDO validateWardExists(Long id) {
        if (id == null) {
            return null;
        }
        HisWardDO ward = wardMapper.selectById(id);
        if (ward == null) {
            throw exception(WARD_NOT_EXISTS);
        }
        return ward;
    }

    /**
     * 校验病区编码唯一
     */
    private void validateWardCodeUnique(Long id, String wardCode) {
        HisWardDO ward = wardMapper.selectByWardCode(wardCode);
        if (ward == null) {
            return;
        }
        // 如果 id 为空，说明不用比较
        if (id == null) {
            throw exception(WARD_NOT_EXISTS); // TODO: 添加编码重复错误码
        }
        if (!ward.getId().equals(id)) {
            throw exception(WARD_NOT_EXISTS); // TODO: 添加编码重复错误码
        }
    }
}
