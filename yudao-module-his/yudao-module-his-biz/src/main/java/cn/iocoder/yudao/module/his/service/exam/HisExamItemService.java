package cn.iocoder.yudao.module.his.service.exam;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamItemPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 检查项目字典 Service 接口
 */
public interface HisExamItemService {

    /**
     * 创建检查项目
     *
     * @param itemCode 项目编码
     * @param itemName 项目名称
     * @param examType 检查类型
     * @param standardPrice 标准价格
     * @return 编号
     */
    Long createExamItem(@Valid HisExamItemDO examItem);

    /**
     * 更新检查项目
     *
     * @param examItem 更新信息
     */
    void updateExamItem(@Valid HisExamItemDO examItem);

    /**
     * 删除检查项目
     *
     * @param id 编号
     */
    void deleteExamItem(Long id);

    /**
     * 获得检查项目
     *
     * @param id 编号
     * @return 检查项目
     */
    HisExamItemDO getExamItem(Long id);

    /**
     * 获得检查项目分页
     *
     * @param pageReqVO 分页查询
     * @return 检查项目分页
     */
    PageResult<HisExamItemDO> getExamItemPage(HisExamItemPageReqVO pageReqVO);

    /**
     * 根据检查类型获取项目列表
     *
     * @param examType 检查类型
     * @return 项目列表
     */
    List<HisExamItemDO> getExamItemListByType(Integer examType);

    /**
     * 根据项目类别获取项目列表
     *
     * @param itemCategory 项目类别
     * @return 项目列表
     */
    List<HisExamItemDO> getExamItemListByCategory(String itemCategory);

    /**
     * 获取所有在用项目
     *
     * @return 项目列表
     */
    List<HisExamItemDO> getActiveExamItemList();

}
