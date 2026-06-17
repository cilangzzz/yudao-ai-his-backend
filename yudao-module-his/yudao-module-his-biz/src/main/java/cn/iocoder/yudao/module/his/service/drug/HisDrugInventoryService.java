package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInventoryPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInventorySaveReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInventoryItemSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 盘点管理 Service 接口
 *
 * @author 芋道源码
 */
public interface HisDrugInventoryService {

    /**
     * 创建盘点记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventory(@Valid HisDrugInventorySaveReqVO createReqVO);

    /**
     * 删除盘点记录
     *
     * @param id 编号
     */
    void deleteInventory(Long id);

    /**
     * 完成盘点
     *
     * @param id 编号
     */
    void completeInventory(Long id);

    /**
     * 盘点单个药品
     *
     * @param itemId 盘点明细ID
     * @param actualQuantity 实盘数量
     * @param operatorId 操作员ID
     * @param operatorName 操作员姓名
     * @param remark 备注
     */
    void inventoryItem(Long itemId, java.math.BigDecimal actualQuantity,
                       Long operatorId, String operatorName, String remark);

    /**
     * 获得盘点记录
     *
     * @param id 编号
     * @return 盘点记录
     */
    HisDrugInventoryDO getInventory(Long id);

    /**
     * 根据盘点单号查询
     *
     * @param inventoryNo 盘点单号
     * @return 盘点记录
     */
    HisDrugInventoryDO getInventoryByNo(String inventoryNo);

    /**
     * 获得盘点记录分页
     *
     * @param pageReqVO 分页查询
     * @return 盘点记录分页
     */
    PageResult<HisDrugInventoryDO> getInventoryPage(HisDrugInventoryPageReqVO pageReqVO);

    /**
     * 获得盘点明细列表
     *
     * @param inventoryId 盘点ID
     * @return 盘点明细列表
     */
    List<HisDrugInventoryItemDO> getInventoryItems(Long inventoryId);

    /**
     * 获得进行中的盘点列表
     *
     * @return 盘点列表
     */
    List<HisDrugInventoryDO> getInProgressList();

    /**
     * 校验盘点记录存在
     *
     * @param id 编号
     * @return 盘点记录
     */
    HisDrugInventoryDO validateInventoryExists(Long id);

    /**
     * 校验盘点明细存在
     *
     * @param itemId 盘点明细ID
     * @return 盘点明细
     */
    HisDrugInventoryItemDO validateInventoryItemExists(Long itemId);

}