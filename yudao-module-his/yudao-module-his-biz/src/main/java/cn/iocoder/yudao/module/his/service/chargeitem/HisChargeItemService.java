package cn.iocoder.yudao.module.his.service.chargeitem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.chargeitem.HisChargeItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 收费项目 Service 接口
 */
public interface HisChargeItemService {

    /**
     * 创建收费项目
     *
     * @param createReqVO 创建信息
     * @return 项目编号
     */
    Long createChargeItem(@Valid HisChargeItemSaveReqVO createReqVO);

    /**
     * 更新收费项目
     *
     * @param updateReqVO 更新信息
     */
    void updateChargeItem(@Valid HisChargeItemSaveReqVO updateReqVO);

    /**
     * 删除收费项目
     *
     * @param id 编号
     */
    void deleteChargeItem(Long id);

    /**
     * 获取收费项目
     *
     * @param id 编号
     * @return 收费项目
     */
    HisChargeItemDO getChargeItem(Long id);

    /**
     * 获取收费项目分页
     *
     * @param pageReqVO 分页查询
     * @return 收费项目分页
     */
    PageResult<HisChargeItemDO> getChargeItemPage(HisChargeItemPageReqVO pageReqVO);

    /**
     * 获取收费项目列表
     *
     * @param itemCategory 项目类别
     * @param status 状态
     * @return 收费项目列表
     */
    List<HisChargeItemDO> getChargeItemList(Integer itemCategory, Integer status);

    /**
     * 根据项目编码获取收费项目
     *
     * @param itemCode 项目编码
     * @return 收费项目
     */
    HisChargeItemDO getChargeItemByItemCode(String itemCode);

    /**
     * 校验收费项目是否存在
     *
     * @param id 编号
     * @return 收费项目
     */
    HisChargeItemDO validateChargeItemExists(Long id);

}