package cn.iocoder.yudao.module.his.service.exam;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamItemPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.exam.HisExamItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 检查项目字典 Service 实现类
 */
@Service
@Validated
public class HisExamItemServiceImpl implements HisExamItemService {

    @Resource
    private HisExamItemMapper examItemMapper;

    @Override
    public Long createExamItem(HisExamItemDO examItem) {
        // 校验编码唯一性
        if (examItemMapper.selectByItemCode(examItem.getItemCode()) != null) {
            throw exception(EXAM_ITEM_CODE_DUPLICATE);
        }
        examItemMapper.insert(examItem);
        return examItem.getId();
    }

    @Override
    public void updateExamItem(HisExamItemDO examItem) {
        // 校验存在
        validateExamItemExists(examItem.getId());
        // 校验编码唯一性
        HisExamItemDO existItem = examItemMapper.selectByItemCode(examItem.getItemCode());
        if (existItem != null && !existItem.getId().equals(examItem.getId())) {
            throw exception(EXAM_ITEM_CODE_DUPLICATE);
        }
        examItemMapper.updateById(examItem);
    }

    @Override
    public void deleteExamItem(Long id) {
        validateExamItemExists(id);
        examItemMapper.deleteById(id);
    }

    @Override
    public HisExamItemDO getExamItem(Long id) {
        return examItemMapper.selectById(id);
    }

    @Override
    public PageResult<HisExamItemDO> getExamItemPage(HisExamItemPageReqVO pageReqVO) {
        return examItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisExamItemDO> getExamItemListByType(Integer examType) {
        return examItemMapper.selectListByExamType(examType);
    }

    @Override
    public List<HisExamItemDO> getExamItemListByCategory(String itemCategory) {
        return examItemMapper.selectListByCategory(itemCategory);
    }

    @Override
    public List<HisExamItemDO> getActiveExamItemList() {
        return examItemMapper.selectListByStatus(1);
    }

    private void validateExamItemExists(Long id) {
        if (examItemMapper.selectById(id) == null) {
            throw exception(EXAM_ITEM_NOT_EXISTS);
        }
    }

}
