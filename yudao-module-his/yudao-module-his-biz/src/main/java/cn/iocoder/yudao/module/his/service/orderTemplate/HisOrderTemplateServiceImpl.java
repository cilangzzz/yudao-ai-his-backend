package cn.iocoder.yudao.module.his.service.orderTemplate;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo.HisOrderTemplateItemSaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo.HisOrderTemplatePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo.HisOrderTemplateSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateDO;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.orderTemplate.HisOrderTemplateItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.orderTemplate.HisOrderTemplateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 医嘱模板 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisOrderTemplateServiceImpl implements HisOrderTemplateService {

    @Resource
    private HisOrderTemplateMapper orderTemplateMapper;

    @Resource
    private HisOrderTemplateItemMapper orderTemplateItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrderTemplate(HisOrderTemplateSaveReqVO createReqVO) {
        // 1. 校验模板编码唯一性
        validateTemplateCodeUnique(null, createReqVO.getTemplateCode());

        // 2. 校验明细数据
        validateTemplateItems(createReqVO.getItems());

        // 3. 插入模板主表
        HisOrderTemplateDO template = BeanUtils.toBean(createReqVO, HisOrderTemplateDO.class);
        template.setUseCount(0); // 初始化使用次数为0
        orderTemplateMapper.insert(template);

        // 4. 插入模板明细
        if (createReqVO.getItems() != null && !createReqVO.getItems().isEmpty()) {
            createTemplateItems(template.getId(), createReqVO.getItems());
        }

        // 5. 返回
        return template.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderTemplate(HisOrderTemplateSaveReqVO updateReqVO) {
        // 1. 校验存在
        validateOrderTemplateExists(updateReqVO.getId());

        // 2. 校验模板编码唯一性
        validateTemplateCodeUnique(updateReqVO.getId(), updateReqVO.getTemplateCode());

        // 3. 校验明细数据
        validateTemplateItems(updateReqVO.getItems());

        // 4. 更新模板主表
        HisOrderTemplateDO updateObj = BeanUtils.toBean(updateReqVO, HisOrderTemplateDO.class);
        orderTemplateMapper.updateById(updateObj);

        // 5. 先删除旧明细，再插入新明细
        orderTemplateItemMapper.deleteByTemplateId(updateReqVO.getId());
        if (updateReqVO.getItems() != null && !updateReqVO.getItems().isEmpty()) {
            createTemplateItems(updateReqVO.getId(), updateReqVO.getItems());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderTemplate(Long id) {
        // 1. 校验存在
        validateOrderTemplateExists(id);

        // 2. 删除模板明细
        orderTemplateItemMapper.deleteByTemplateId(id);

        // 3. 删除模板主表
        orderTemplateMapper.deleteById(id);
    }

    @Override
    public HisOrderTemplateDO getOrderTemplate(Long id) {
        return orderTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<HisOrderTemplateDO> getOrderTemplatePage(HisOrderTemplatePageReqVO pageReqVO) {
        return orderTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisOrderTemplateItemDO> getOrderTemplateItemList(Long templateId) {
        return orderTemplateItemMapper.selectListByTemplateId(templateId);
    }

    @Override
    public List<HisOrderTemplateDO> getOrderTemplateListByCategory(Integer templateCategory) {
        return orderTemplateMapper.selectListByTemplateCategory(templateCategory);
    }

    @Override
    public List<HisOrderTemplateDO> getOrderTemplateListByDeptId(Long deptId) {
        return orderTemplateMapper.selectListByDeptId(deptId);
    }

    @Override
    public List<HisOrderTemplateDO> getOrderTemplateListByDoctorId(Long doctorId) {
        return orderTemplateMapper.selectListByDoctorId(doctorId);
    }

    @Override
    public List<HisOrderTemplateDO> getOrderTemplateListByOrderType(Integer orderType) {
        return orderTemplateMapper.selectListByOrderType(orderType);
    }

    @Override
    public List<HisOrderTemplateDO> getOrderTemplateListByDiagnosisCode(String diagnosisCode) {
        return orderTemplateMapper.selectListByDiagnosisCode(diagnosisCode);
    }

    @Override
    public List<HisOrderTemplateDO> getOrderTemplateListByName(String templateName) {
        return orderTemplateMapper.selectListByName(templateName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long copyOrderTemplate(Long id) {
        // 1. 校验存在
        HisOrderTemplateDO sourceTemplate = validateOrderTemplateExists(id);

        // 2. 获取源模板明细
        List<HisOrderTemplateItemDO> sourceItems = orderTemplateItemMapper.selectListByTemplateId(id);

        // 3. 创建新模板
        HisOrderTemplateDO newTemplate = BeanUtils.toBean(sourceTemplate, HisOrderTemplateDO.class);
        newTemplate.setId(null);
        newTemplate.setTemplateCode(sourceTemplate.getTemplateCode() + "_copy");
        newTemplate.setTemplateName(sourceTemplate.getTemplateName() + "(副本)");
        newTemplate.setUseCount(0);
        orderTemplateMapper.insert(newTemplate);

        // 4. 创建新模板明细
        if (sourceItems != null && !sourceItems.isEmpty()) {
            for (HisOrderTemplateItemDO sourceItem : sourceItems) {
                HisOrderTemplateItemDO newItem = BeanUtils.toBean(sourceItem, HisOrderTemplateItemDO.class);
                newItem.setId(null);
                newItem.setTemplateId(newTemplate.getId());
                orderTemplateItemMapper.insert(newItem);
            }
        }

        // 5. 返回
        return newTemplate.getId();
    }

    @Override
    public void updateOrderTemplateStatus(Long id, Integer status) {
        // 1. 校验存在
        validateOrderTemplateExists(id);

        // 2. 更新状态
        HisOrderTemplateDO updateObj = new HisOrderTemplateDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        orderTemplateMapper.updateById(updateObj);
    }

    @Override
    public void recordTemplateUsage(Long id) {
        // 校验存在并增加使用次数
        validateOrderTemplateExists(id);
        orderTemplateMapper.incrementUseCount(id);
    }

    // ==================== 校验方法 ====================

    /**
     * 校验医嘱模板是否存在
     */
    private HisOrderTemplateDO validateOrderTemplateExists(Long id) {
        if (id == null) {
            return null;
        }
        HisOrderTemplateDO template = orderTemplateMapper.selectById(id);
        if (template == null) {
            throw exception(ORDER_TEMPLATE_NOT_EXISTS);
        }
        return template;
    }

    /**
     * 校验模板编码唯一性
     */
    private void validateTemplateCodeUnique(Long id, String templateCode) {
        if (StrUtil.isBlank(templateCode)) {
            return;
        }
        HisOrderTemplateDO template = orderTemplateMapper.selectByTemplateCode(templateCode);
        if (template == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的模板
        if (id == null) {
            throw exception(ORDER_TEMPLATE_CODE_DUPLICATE);
        }
        if (!template.getId().equals(id)) {
            throw exception(ORDER_TEMPLATE_CODE_DUPLICATE);
        }
    }

    /**
     * 校验模板明细数据
     */
    private void validateTemplateItems(List<HisOrderTemplateItemSaveReqVO> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        // 校验每个明细的项目编码和名称是否为空
        for (HisOrderTemplateItemSaveReqVO item : items) {
            if (StrUtil.isBlank(item.getItemCode()) && StrUtil.isBlank(item.getOrderContent())) {
                throw exception(ORDER_TEMPLATE_ITEM_EMPTY);
            }
        }
    }

    /**
     * 创建模板明细
     */
    private void createTemplateItems(Long templateId, List<HisOrderTemplateItemSaveReqVO> items) {
        for (int i = 0; i < items.size(); i++) {
            HisOrderTemplateItemSaveReqVO itemVO = items.get(i);
            HisOrderTemplateItemDO item = BeanUtils.toBean(itemVO, HisOrderTemplateItemDO.class);
            item.setTemplateId(templateId);
            item.setSort(i + 1); // 设置排序号
            orderTemplateItemMapper.insert(item);
        }
    }

}