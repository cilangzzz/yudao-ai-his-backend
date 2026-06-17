package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugStockPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugStockDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugStockMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 药品库存 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisDrugStockServiceImpl implements HisDrugStockService {

    @Resource
    private HisDrugStockMapper drugStockMapper;

    @Resource
    private HisDrugMapper drugMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long increaseStock(Long drugId, String batchNo, BigDecimal quantity,
                              BigDecimal retailPrice, BigDecimal purchasePrice,
                              LocalDate expiryDate, Long warehouseId) {
        // 1. 校验药品存在
        HisDrugDO drug = drugMapper.selectById(drugId);
        if (drug == null) {
            throw exception(DRUG_NOT_EXISTS);
        }

        // 2. 查找是否已存在相同批号的库存
        HisDrugStockDO existStock = drugStockMapper.selectByDrugIdAndBatchNo(drugId, batchNo);

        if (existStock != null) {
            // 2.1 已存在，累加库存
            existStock.setQuantity(existStock.getQuantity().add(quantity));
            existStock.setRetailPrice(retailPrice);
            existStock.setPurchasePrice(purchasePrice);
            drugStockMapper.updateById(existStock);
            return existStock.getId();
        } else {
            // 2.2 不存在，创建新库存记录
            HisDrugStockDO stock = HisDrugStockDO.builder()
                    .drugId(drugId)
                    .drugCode(drug.getDrugCode())
                    .drugName(drug.getDrugName())
                    .batchNo(batchNo)
                    .expiryDate(expiryDate)
                    .quantity(quantity)
                    .unit(drug.getUnit())
                    .retailPrice(retailPrice)
                    .purchasePrice(purchasePrice)
                    .warehouseId(warehouseId)
                    .status(1) // 正常
                    .build();
            drugStockMapper.insert(stock);
            return stock.getId();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<StockDeductionResult> decreaseStock(Long drugId, BigDecimal quantity, Long warehouseId) {
        // 1. 查询可用库存（按有效期升序，先进先出）
        List<HisDrugStockDO> stockList = drugStockMapper.selectListByDrugId(drugId);

        // 2. 过滤仓库
        if (warehouseId != null) {
            stockList.removeIf(s -> !warehouseId.equals(s.getWarehouseId()));
        }

        // 3. 过滤过期库存
        stockList.removeIf(HisDrugStockDO::isExpired);

        // 4. 校验库存是否充足
        BigDecimal totalAvailable = stockList.stream()
                .map(HisDrugStockDO::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalAvailable.compareTo(quantity) < 0) {
            throw exception(DRUG_STOCK_INSUFFICIENT);
        }

        // 5. 按先进先出原则扣减
        List<StockDeductionResult> results = new ArrayList<>();
        BigDecimal remaining = quantity;

        for (HisDrugStockDO stock : stockList) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal deductQty = stock.getQuantity().min(remaining);
            stock.setQuantity(stock.getQuantity().subtract(deductQty));
            drugStockMapper.updateById(stock);

            results.add(new StockDeductionResult(
                    stock.getId(),
                    stock.getBatchNo(),
                    deductQty,
                    stock.getRetailPrice()
            ));

            remaining = remaining.subtract(deductQty);
        }

        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStockStatus() {
        LocalDate now = LocalDate.now();
        LocalDate nearExpiryDate = now.plusMonths(3); // 近效期预警：3个月

        // 1. 更新过期库存
        List<HisDrugStockDO> expiredList = drugStockMapper.selectExpiredList();
        for (HisDrugStockDO stock : expiredList) {
            stock.setStatus(3); // 过期
            drugStockMapper.updateById(stock);
        }

        // 2. 更新近效期库存
        List<HisDrugStockDO> nearExpiryList = drugStockMapper.selectNearExpiryList(nearExpiryDate);
        for (HisDrugStockDO stock : nearExpiryList) {
            stock.setStatus(2); // 近效期
            drugStockMapper.updateById(stock);
        }
    }

    @Override
    public HisDrugStockDO getDrugStock(Long id) {
        return drugStockMapper.selectById(id);
    }

    @Override
    public PageResult<HisDrugStockDO> getDrugStockPage(HisDrugStockPageReqVO pageReqVO) {
        return drugStockMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDrugStockDO> getDrugStockListByDrugId(Long drugId) {
        return drugStockMapper.selectListByDrugId(drugId);
    }

    @Override
    public BigDecimal getTotalQuantityByDrugId(Long drugId) {
        return drugStockMapper.selectTotalQuantityByDrugId(drugId);
    }

    @Override
    public List<HisDrugStockDO> getNearExpiryStockList(Integer warningDays) {
        LocalDate warningDate = LocalDate.now().plusDays(warningDays != null ? warningDays : 90);
        return drugStockMapper.selectNearExpiryList(warningDate);
    }

    @Override
    public List<HisDrugStockDO> getExpiredStockList() {
        return drugStockMapper.selectExpiredList();
    }

    @Override
    public List<HisDrugStockDO> getLowStockList(BigDecimal threshold) {
        return drugStockMapper.selectLowStockList(threshold);
    }

    @Override
    public boolean validateStockSufficient(Long drugId, BigDecimal quantity, Long warehouseId) {
        BigDecimal totalStock = getTotalQuantityByDrugId(drugId);
        return totalStock.compareTo(quantity) >= 0;
    }

    @Override
    public HisDrugStockDO validateDrugStockExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDrugStockDO stock = drugStockMapper.selectById(id);
        if (stock == null) {
            throw exception(DRUG_STOCK_NOT_EXISTS);
        }
        return stock;
    }

}