package cn.iocoder.yudao.module.his.service.chargeitem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.chargeitem.HisChargeItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.chargeitem.HisChargeItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 收费项目 Service 实现类
 */
@Service
@Validated
public class HisChargeItemServiceImpl implements HisChargeItemService {

    @Resource
    private HisChargeItemMapper chargeItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createChargeItem(HisChargeItemSaveReqVO createReqVO) {
        // 1. 校验项目编码唯一性
        HisChargeItemDO existItem = chargeItemMapper.selectByItemCode(createReqVO.getItemCode());
        if (existItem != null) {
            throw exception(CHARGE_ITEM_CODE_DUPLICATE);
        }

        // 2. 插入项目
        HisChargeItemDO chargeItem = BeanUtils.toBean(createReqVO, HisChargeItemDO.class);
        chargeItem.setItemName(createReqVO.getName());
        chargeItem.setItemCategory(createReqVO.getType());
        chargeItem.setUnitPrice(createReqVO.getPrice());
        chargeItem.setInsuranceCategory(createReqVO.getInsuranceType());
        chargeItem.setExecutionDept(createReqVO.getDeptId());
        // 设置默认状态
        if (chargeItem.getStatus() == null) {
            chargeItem.setStatus(1);
        }
        chargeItemMapper.insert(chargeItem);

        return chargeItem.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateChargeItem(HisChargeItemSaveReqVO updateReqVO) {
        // 1. 校验存在
        validateChargeItemExists(updateReqVO.getId());

        // 2. 校验项目编码唯一性
        HisChargeItemDO existItem = chargeItemMapper.selectByItemCode(updateReqVO.getItemCode());
        if (existItem != null && !existItem.getId().equals(updateReqVO.getId())) {
            throw exception(CHARGE_ITEM_CODE_DUPLICATE);
        }

        // 3. 更新项目
        HisChargeItemDO updateObj = BeanUtils.toBean(updateReqVO, HisChargeItemDO.class);
        updateObj.setItemName(updateReqVO.getName());
        updateObj.setItemCategory(updateReqVO.getType());
        updateObj.setUnitPrice(updateReqVO.getPrice());
        updateObj.setInsuranceCategory(updateReqVO.getInsuranceType());
        updateObj.setExecutionDept(updateReqVO.getDeptId());
        chargeItemMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChargeItem(Long id) {
        // 1. 校验存在
        validateChargeItemExists(id);

        // 2. 删除
        chargeItemMapper.deleteById(id);
    }

    @Override
    public HisChargeItemDO getChargeItem(Long id) {
        return chargeItemMapper.selectById(id);
    }

    @Override
    public PageResult<HisChargeItemDO> getChargeItemPage(HisChargeItemPageReqVO pageReqVO) {
        return chargeItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisChargeItemDO> getChargeItemList(Integer itemCategory, Integer status) {
        return chargeItemMapper.selectList(itemCategory, status);
    }

    @Override
    public HisChargeItemDO getChargeItemByItemCode(String itemCode) {
        return chargeItemMapper.selectByItemCode(itemCode);
    }

    @Override
    public HisChargeItemDO validateChargeItemExists(Long id) {
        if (id == null) {
            return null;
        }
        HisChargeItemDO chargeItem = chargeItemMapper.selectById(id);
        if (chargeItem == null) {
            throw exception(CHARGE_ITEM_NOT_EXISTS);
        }
        return chargeItem;
    }

}