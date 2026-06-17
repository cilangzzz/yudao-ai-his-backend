package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 药品目录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisDrugServiceImpl implements HisDrugService {

    @Resource
    private HisDrugMapper drugMapper;

    @Override
    public Long createDrug(HisDrugSaveReqVO createReqVO) {
        // 1. 校验药品编码唯一性
        validateDrugCodeUnique(null, createReqVO.getDrugCode());

        // 2. 插入
        HisDrugDO drug = BeanUtils.toBean(createReqVO, HisDrugDO.class);
        drugMapper.insert(drug);

        // 3. 返回
        return drug.getId();
    }

    @Override
    public void updateDrug(HisDrugSaveReqVO updateReqVO) {
        // 1. 校验存在
        validateDrugExists(updateReqVO.getId());

        // 2. 校验药品编码唯一性
        validateDrugCodeUnique(updateReqVO.getId(), updateReqVO.getDrugCode());

        // 3. 更新
        HisDrugDO updateObj = BeanUtils.toBean(updateReqVO, HisDrugDO.class);
        drugMapper.updateById(updateObj);
    }

    @Override
    public void deleteDrug(Long id) {
        // 1. 校验存在
        validateDrugExists(id);

        // 2. 删除
        drugMapper.deleteById(id);
    }

    @Override
    public HisDrugDO getDrug(Long id) {
        return drugMapper.selectById(id);
    }

    @Override
    public HisDrugDO getDrugByCode(String drugCode) {
        return drugMapper.selectByDrugCode(drugCode);
    }

    @Override
    public PageResult<HisDrugDO> getDrugPage(HisDrugPageReqVO pageReqVO) {
        return drugMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDrugDO> getDrugList(List<Long> ids) {
        return drugMapper.selectBatchIds(ids);
    }

    @Override
    public List<HisDrugDO> getDrugListByName(String drugName) {
        return drugMapper.selectListByName(drugName);
    }

    @Override
    public List<HisDrugDO> getDrugListByType(Integer drugType) {
        return drugMapper.selectListByDrugType(drugType);
    }

    @Override
    public List<HisDrugDO> getSpecialDrugList() {
        return drugMapper.selectSpecialDrugList();
    }

    @Override
    public List<HisDrugDO> getAntibioticList() {
        return drugMapper.selectAntibioticList();
    }

    @Override
    public HisDrugDO validateDrugExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDrugDO drug = drugMapper.selectById(id);
        if (drug == null) {
            throw exception(DRUG_NOT_EXISTS);
        }
        return drug;
    }

    @Override
    public void validateDrugCodeUnique(Long id, String drugCode) {
        if (drugCode == null) {
            return;
        }
        HisDrugDO drug = drugMapper.selectByDrugCode(drugCode);
        if (drug == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的药品
        if (id == null) {
            throw exception(DRUG_CODE_DUPLICATE);
        }
        if (!drug.getId().equals(id)) {
            throw exception(DRUG_CODE_DUPLICATE);
        }
    }

}