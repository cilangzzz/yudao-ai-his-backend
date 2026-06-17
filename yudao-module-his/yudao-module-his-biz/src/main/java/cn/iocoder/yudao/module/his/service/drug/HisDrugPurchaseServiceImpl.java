package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPurchasePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPurchaseSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugPurchaseItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugPurchaseMapper;
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
 * 采购计划管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisDrugPurchaseServiceImpl implements HisDrugPurchaseService {

    @Resource
    private HisDrugPurchaseMapper purchaseMapper;

    @Resource
    private HisDrugPurchaseItemMapper purchaseItemMapper;

    @Resource
    private HisDrugMapper drugMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPurchase(HisDrugPurchaseSaveReqVO createReqVO) {
        // 1. 生成采购单号
        String purchaseNo = generatePurchaseNo();

        // 2. 创建采购主表
        HisDrugPurchaseDO purchase = BeanUtils.toBean(createReqVO, HisDrugPurchaseDO.class);
        purchase.setPurchaseNo(purchaseNo);
        purchase.setPurchaseStatus(1); // 草稿

        // 3. 计算总金额
        BigDecimal totalAmount = calculateTotalAmount(createReqVO.getItems());
        purchase.setTotalAmount(totalAmount);

        // 4. 插入采购记录
        purchaseMapper.insert(purchase);

        // 5. 插入采购明细
        if (createReqVO.getItems() != null && !createReqVO.getItems().isEmpty()) {
            List<HisDrugPurchaseItemDO> items = new ArrayList<>();
            for (HisDrugPurchaseSaveReqVO.PurchaseItemVO itemVO : createReqVO.getItems()) {
                HisDrugPurchaseItemDO item = BeanUtils.toBean(itemVO, HisDrugPurchaseItemDO.class);
                item.setPurchaseId(purchase.getId());

                // 查询药品信息
                HisDrugDO drug = drugMapper.selectById(itemVO.getDrugId());
                if (drug != null) {
                    item.setDrugCode(drug.getDrugCode());
                    item.setDrugName(drug.getDrugName());
                    item.setDrugSpec(drug.getSpec());
                    item.setUnit(drug.getUnit());
                }

                // 计算预算金额
                if (item.getQuantity() != null && item.getReferencePrice() != null) {
                    item.setBudgetAmount(item.getQuantity().multiply(item.getReferencePrice()));
                }

                items.add(item);
            }
            purchaseItemMapper.insertBatch(items);
        }

        return purchase.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePurchase(HisDrugPurchaseSaveReqVO updateReqVO) {
        // 1. 校验存在且可修改
        validatePurchaseCanUpdate(updateReqVO.getId());

        // 2. 更新采购主表
        HisDrugPurchaseDO updateObj = BeanUtils.toBean(updateReqVO, HisDrugPurchaseDO.class);
        BigDecimal totalAmount = calculateTotalAmount(updateReqVO.getItems());
        updateObj.setTotalAmount(totalAmount);
        purchaseMapper.updateById(updateObj);

        // 3. 删除原有明细
        purchaseItemMapper.deleteByPurchaseId(updateReqVO.getId());

        // 4. 插入新的明细
        if (updateReqVO.getItems() != null && !updateReqVO.getItems().isEmpty()) {
            List<HisDrugPurchaseItemDO> items = new ArrayList<>();
            for (HisDrugPurchaseSaveReqVO.PurchaseItemVO itemVO : updateReqVO.getItems()) {
                HisDrugPurchaseItemDO item = BeanUtils.toBean(itemVO, HisDrugPurchaseItemDO.class);
                item.setId(null);
                item.setPurchaseId(updateReqVO.getId());

                HisDrugDO drug = drugMapper.selectById(itemVO.getDrugId());
                if (drug != null) {
                    item.setDrugCode(drug.getDrugCode());
                    item.setDrugName(drug.getDrugName());
                    item.setDrugSpec(drug.getSpec());
                    item.setUnit(drug.getUnit());
                }

                if (item.getQuantity() != null && item.getReferencePrice() != null) {
                    item.setBudgetAmount(item.getQuantity().multiply(item.getReferencePrice()));
                }

                items.add(item);
            }
            purchaseItemMapper.insertBatch(items);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePurchase(Long id) {
        // 1. 校验存在且可修改
        validatePurchaseCanUpdate(id);

        // 2. 删除明细
        purchaseItemMapper.deleteByPurchaseId(id);

        // 3. 删除主表
        purchaseMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitPurchase(Long id, Long applyBy) {
        // 1. 校验存在且可提交
        HisDrugPurchaseDO purchase = validatePurchaseExists(id);
        if (!purchase.canSubmit()) {
            throw exception(DRUG_PURCHASE_ALREADY_SUBMITTED);
        }

        // 2. 更新状态
        HisDrugPurchaseDO updateObj = new HisDrugPurchaseDO();
        updateObj.setId(id);
        updateObj.setPurchaseStatus(2); // 已提交
        updateObj.setApplyBy(applyBy);
        updateObj.setApplyTime(LocalDateTime.now());
        purchaseMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approvePurchase(Long id, Long auditBy, boolean approved) {
        // 1. 校验存在且可审批
        HisDrugPurchaseDO purchase = validatePurchaseExists(id);
        if (!purchase.canApprove()) {
            throw exception(DRUG_PURCHASE_ALREADY_APPROVED);
        }

        // 2. 更新状态
        HisDrugPurchaseDO updateObj = new HisDrugPurchaseDO();
        updateObj.setId(id);
        updateObj.setPurchaseStatus(approved ? 3 : 1); // 已审批/退回草稿
        updateObj.setAuditBy(auditBy);
        updateObj.setAuditTime(LocalDateTime.now());
        purchaseMapper.updateById(updateObj);
    }

    @Override
    public HisDrugPurchaseDO getPurchase(Long id) {
        return purchaseMapper.selectById(id);
    }

    @Override
    public HisDrugPurchaseDO getPurchaseByNo(String purchaseNo) {
        return purchaseMapper.selectByPurchaseNo(purchaseNo);
    }

    @Override
    public PageResult<HisDrugPurchaseDO> getPurchasePage(HisDrugPurchasePageReqVO pageReqVO) {
        return purchaseMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDrugPurchaseItemDO> getPurchaseItems(Long purchaseId) {
        return purchaseItemMapper.selectListByPurchaseId(purchaseId);
    }

    @Override
    public List<HisDrugPurchaseDO> getPendingApprovalList() {
        return purchaseMapper.selectPendingApprovalList();
    }

    @Override
    public HisDrugPurchaseDO validatePurchaseExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDrugPurchaseDO purchase = purchaseMapper.selectById(id);
        if (purchase == null) {
            throw exception(DRUG_PURCHASE_NOT_EXISTS);
        }
        return purchase;
    }

    @Override
    public HisDrugPurchaseDO validatePurchaseCanUpdate(Long id) {
        HisDrugPurchaseDO purchase = validatePurchaseExists(id);
        if (!purchase.canUpdate()) {
            throw exception(DRUG_PURCHASE_ALREADY_SUBMITTED);
        }
        return purchase;
    }

    /**
     * 生成采购单号
     * 格式: CG + yyyyMMdd + 4位流水号
     */
    private String generatePurchaseNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("CG%s%04d", dateStr, seq);
    }

    /**
     * 计算总金额
     */
    private BigDecimal calculateTotalAmount(List<HisDrugPurchaseSaveReqVO.PurchaseItemVO> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> {
                    if (item.getQuantity() != null && item.getReferencePrice() != null) {
                        return item.getQuantity().multiply(item.getReferencePrice());
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}