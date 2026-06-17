package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugStockPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugStockDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 药品库存 Service 接口
 *
 * @author 芋道源码
 */
public interface HisDrugStockService {

    /**
     * 增加库存
     *
     * @param drugId 药品ID
     * @param batchNo 批号
     * @param quantity 数量
     * @param retailPrice 零售价
     * @param purchasePrice 采购价
     * @param expiryDate 有效期
     * @param warehouseId 仓库ID
     * @return 库存ID
     */
    Long increaseStock(Long drugId, String batchNo, BigDecimal quantity,
                       BigDecimal retailPrice, BigDecimal purchasePrice,
                       LocalDate expiryDate, Long warehouseId);

    /**
     * 减少库存（按先进先出原则）
     *
     * @param drugId 药品ID
     * @param quantity 数量
     * @param warehouseId 仓库ID（可选）
     * @return 扣减结果列表
     */
    List<StockDeductionResult> decreaseStock(Long drugId, BigDecimal quantity, Long warehouseId);

    /**
     * 更新库存状态（自动检测近效期和过期）
     */
    void updateStockStatus();

    /**
     * 获得药品库存
     *
     * @param id 编号
     * @return 药品库存
     */
    HisDrugStockDO getDrugStock(Long id);

    /**
     * 获得药品库存分页
     *
     * @param pageReqVO 分页查询
     * @return 药品库存分页
     */
    PageResult<HisDrugStockDO> getDrugStockPage(HisDrugStockPageReqVO pageReqVO);

    /**
     * 根据药品ID查询库存列表
     *
     * @param drugId 药品ID
     * @return 库存列表
     */
    List<HisDrugStockDO> getDrugStockListByDrugId(Long drugId);

    /**
     * 查询药品总库存数量
     *
     * @param drugId 药品ID
     * @return 总库存数量
     */
    BigDecimal getTotalQuantityByDrugId(Long drugId);

    /**
     * 获得近效期库存列表
     *
     * @param warningDays 预警天数
     * @return 库存列表
     */
    List<HisDrugStockDO> getNearExpiryStockList(Integer warningDays);

    /**
     * 获得过期库存列表
     *
     * @return 库存列表
     */
    List<HisDrugStockDO> getExpiredStockList();

    /**
     * 获得低库存列表
     *
     * @param threshold 阈值
     * @return 库存列表
     */
    List<HisDrugStockDO> getLowStockList(BigDecimal threshold);

    /**
     * 校验库存是否充足
     *
     * @param drugId 药品ID
     * @param quantity 需要数量
     * @param warehouseId 仓库ID（可选）
     * @return 是否充足
     */
    boolean validateStockSufficient(Long drugId, BigDecimal quantity, Long warehouseId);

    /**
     * 校验库存存在
     *
     * @param id 编号
     * @return 药品库存
     */
    HisDrugStockDO validateDrugStockExists(Long id);

    /**
     * 库存扣减结果
     */
    class StockDeductionResult {
        private Long stockId;
        private String batchNo;
        private BigDecimal quantity;
        private BigDecimal unitPrice;

        public StockDeductionResult() {}

        public StockDeductionResult(Long stockId, String batchNo, BigDecimal quantity, BigDecimal unitPrice) {
            this.stockId = stockId;
            this.batchNo = batchNo;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public Long getStockId() {
            return stockId;
        }

        public void setStockId(Long stockId) {
            this.stockId = stockId;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }
    }

}