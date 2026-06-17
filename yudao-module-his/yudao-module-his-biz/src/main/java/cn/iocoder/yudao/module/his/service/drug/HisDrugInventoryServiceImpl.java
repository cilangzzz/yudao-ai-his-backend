package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInventoryPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInventorySaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInventoryItemDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugStockDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugInventoryItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugInventoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 盘点管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisDrugInventoryServiceImpl implements HisDrugInventoryService {

    @Resource
    private HisDrugInventoryMapper inventoryMapper;

    @Resource
    private HisDrugInventoryItemMapper inventoryItemMapper;

    @Resource
    private HisDrugStockService drugStockService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInventory(HisDrugInventorySaveReqVO createReqVO) {
        // 1. 生成盘点单号
        String inventoryNo = generateInventoryNo();

        // 2. 创建盘点主表
        HisDrugInventoryDO inventory = BeanUtils.toBean(createReqVO, HisDrugInventoryDO.class);
        inventory.setInventoryNo(inventoryNo);
        inventory.setInventoryStatus(1); // 进行中
        inventory.setTotalItems(0);
        inventory.setProfitItems(0);
        inventory.setLossItems(0);
        inventory.setProfitAmount(BigDecimal.ZERO);
        inventory.setLossAmount(BigDecimal.ZERO);

        // 3. 插入盘点记录
        inventoryMapper.insert(inventory);

        // 4. 生成盘点明细（从现有库存生成）
        List<HisDrugStockDO> stockList = drugStockService.getDrugStockListByDrugId(null);
        // 这里简化处理，实际应该根据仓库过滤

        if (!stockList.isEmpty()) {
            List<HisDrugInventoryItemDO> items = new ArrayList<>();
            for (HisDrugStockDO stock : stockList) {
                HisDrugInventoryItemDO item = HisDrugInventoryItemDO.builder()
                        .inventoryId(inventory.getId())
                        .drugId(stock.getDrugId())
                        .drugCode(stock.getDrugCode())
                        .drugName(stock.getDrugName())
                        .batchNo(stock.getBatchNo())
                        .bookQuantity(stock.getQuantity())
                        .actualQuantity(BigDecimal.ZERO)
                        .differenceQuantity(BigDecimal.ZERO)
                        .unit(stock.getUnit())
                        .retailPrice(stock.getRetailPrice())
                        .differenceAmount(BigDecimal.ZERO)
                        .inventoryItemStatus(1) // 待盘点
                        .build();
                items.add(item);
            }
            inventoryItemMapper.insertBatch(items);

            // 更新盘点品种数
            inventory.setTotalItems(items.size());
            inventoryMapper.updateById(inventory);
        }

        return inventory.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInventory(Long id) {
        // 1. 校验存在且进行中
        HisDrugInventoryDO inventory = validateInventoryExists(id);
        if (!inventory.isInProgress()) {
            throw exception(DRUG_INVENTORY_ALREADY_COMPLETED);
        }

        // 2. 删除明细
        inventoryItemMapper.deleteByInventoryId(id);

        // 3. 删除主表
        inventoryMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeInventory(Long id) {
        // 1. 校验存在且进行中
        HisDrugInventoryDO inventory = validateInventoryExists(id);
        if (!inventory.canComplete()) {
            throw exception(DRUG_INVENTORY_ALREADY_COMPLETED);
        }

        // 2. 检查是否所有明细都已盘点
        List<HisDrugInventoryItemDO> pendingItems = inventoryItemMapper.selectPendingList(id);
        if (!pendingItems.isEmpty()) {
            throw exception(DRUG_INVENTORY_ITEM_NOT_EXISTS);
        }

        // 3. 统计盘点结果
        List<HisDrugInventoryItemDO> items = inventoryItemMapper.selectListByInventoryId(id);
        int profitItems = 0;
        int lossItems = 0;
        BigDecimal profitAmount = BigDecimal.ZERO;
        BigDecimal lossAmount = BigDecimal.ZERO;

        for (HisDrugInventoryItemDO item : items) {
            if (item.isProfit()) {
                profitItems++;
                profitAmount = profitAmount.add(item.getDifferenceAmount().abs());
            } else if (item.isLoss()) {
                lossItems++;
                lossAmount = lossAmount.add(item.getDifferenceAmount().abs());
            }
        }

        // 4. 更新盘点状态
        HisDrugInventoryDO updateObj = new HisDrugInventoryDO();
        updateObj.setId(id);
        updateObj.setInventoryStatus(2); // 已完成
        updateObj.setProfitItems(profitItems);
        updateObj.setLossItems(lossItems);
        updateObj.setProfitAmount(profitAmount);
        updateObj.setLossAmount(lossAmount);
        updateObj.setCompleteTime(LocalDateTime.now());
        inventoryMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void inventoryItem(Long itemId, BigDecimal actualQuantity,
                              Long operatorId, String operatorName, String remark) {
        // 1. 校验明细存在且待盘点
        HisDrugInventoryItemDO item = validateInventoryItemExists(itemId);
        if (!item.isPending()) {
            throw exception(DRUG_INVENTORY_ALREADY_COMPLETED);
        }

        // 2. 计算差异
        BigDecimal difference = actualQuantity.subtract(item.getBookQuantity());
        BigDecimal differenceAmount = difference.multiply(item.getRetailPrice());

        // 3. 确定盘点结果
        int result = 1; // 正常
        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            result = 2; // 盘盈
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            result = 3; // 盘亏
        }

        // 4. 更新盘点明细
        HisDrugInventoryItemDO updateObj = new HisDrugInventoryItemDO();
        updateObj.setId(itemId);
        updateObj.setActualQuantity(actualQuantity);
        updateObj.setDifferenceQuantity(difference);
        updateObj.setDifferenceAmount(differenceAmount);
        updateObj.setInventoryResult(result);
        updateObj.setInventoryItemStatus(2); // 已盘点
        updateObj.setOperatorId(operatorId);
        updateObj.setOperatorName(operatorName);
        updateObj.setRemark(remark);
        inventoryItemMapper.updateById(updateObj);
    }

    @Override
    public HisDrugInventoryDO getInventory(Long id) {
        return inventoryMapper.selectById(id);
    }

    @Override
    public HisDrugInventoryDO getInventoryByNo(String inventoryNo) {
        return inventoryMapper.selectByInventoryNo(inventoryNo);
    }

    @Override
    public PageResult<HisDrugInventoryDO> getInventoryPage(HisDrugInventoryPageReqVO pageReqVO) {
        return inventoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDrugInventoryItemDO> getInventoryItems(Long inventoryId) {
        return inventoryItemMapper.selectListByInventoryId(inventoryId);
    }

    @Override
    public List<HisDrugInventoryDO> getInProgressList() {
        return inventoryMapper.selectInProgressList();
    }

    @Override
    public HisDrugInventoryDO validateInventoryExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDrugInventoryDO inventory = inventoryMapper.selectById(id);
        if (inventory == null) {
            throw exception(DRUG_INVENTORY_NOT_EXISTS);
        }
        return inventory;
    }

    @Override
    public HisDrugInventoryItemDO validateInventoryItemExists(Long itemId) {
        if (itemId == null) {
            return null;
        }
        HisDrugInventoryItemDO item = inventoryItemMapper.selectById(itemId);
        if (item == null) {
            throw exception(DRUG_INVENTORY_ITEM_NOT_EXISTS);
        }
        return item;
    }

    /**
     * 生成盘点单号
     * 格式: PD + yyyyMMdd + 4位流水号
     */
    private String generateInventoryNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("PD%s%04d", dateStr, seq);
    }

}