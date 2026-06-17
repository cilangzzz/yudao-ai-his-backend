package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugOutboundPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugOutboundSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugOutboundItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugOutboundMapper;
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
 * 出库管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisDrugOutboundServiceImpl implements HisDrugOutboundService {

    @Resource
    private HisDrugOutboundMapper outboundMapper;

    @Resource
    private HisDrugOutboundItemMapper outboundItemMapper;

    @Resource
    private HisDrugMapper drugMapper;

    @Resource
    private HisDrugStockService drugStockService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOutbound(HisDrugOutboundSaveReqVO createReqVO) {
        // 1. 生成出库单号
        String outboundNo = generateOutboundNo();

        // 2. 校验库存是否充足
        for (HisDrugOutboundSaveReqVO.OutboundItemVO itemVO : createReqVO.getItems()) {
            boolean sufficient = drugStockService.validateStockSufficient(
                    itemVO.getDrugId(), itemVO.getQuantity(), createReqVO.getWarehouseId());
            if (!sufficient) {
                throw exception(DRUG_STOCK_INSUFFICIENT);
            }
        }

        // 3. 创建出库主表
        HisDrugOutboundDO outbound = BeanUtils.toBean(createReqVO, HisDrugOutboundDO.class);
        outbound.setOutboundNo(outboundNo);
        outbound.setAuditStatus(1); // 待审核

        // 4. 计算总金额
        BigDecimal totalAmount = calculateTotalAmount(createReqVO.getItems());
        outbound.setTotalAmount(totalAmount);

        // 5. 插入出库记录
        outboundMapper.insert(outbound);

        // 6. 插入出库明细
        if (createReqVO.getItems() != null && !createReqVO.getItems().isEmpty()) {
            List<HisDrugOutboundItemDO> items = new ArrayList<>();
            for (HisDrugOutboundSaveReqVO.OutboundItemVO itemVO : createReqVO.getItems()) {
                HisDrugOutboundItemDO item = BeanUtils.toBean(itemVO, HisDrugOutboundItemDO.class);
                item.setOutboundId(outbound.getId());

                // 查询药品信息
                HisDrugDO drug = drugMapper.selectById(itemVO.getDrugId());
                if (drug != null) {
                    item.setDrugCode(drug.getDrugCode());
                    item.setDrugName(drug.getDrugName());
                    item.setDrugSpec(drug.getSpec());
                }

                // 计算金额
                if (item.getQuantity() != null && item.getRetailPrice() != null) {
                    item.setAmount(item.getQuantity().multiply(item.getRetailPrice()));
                }

                items.add(item);
            }
            outboundItemMapper.insertBatch(items);
        }

        return outbound.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOutbound(HisDrugOutboundSaveReqVO updateReqVO) {
        // 1. 校验存在且可修改
        validateOutboundCanUpdate(updateReqVO.getId());

        // 2. 校验库存是否充足
        for (HisDrugOutboundSaveReqVO.OutboundItemVO itemVO : updateReqVO.getItems()) {
            boolean sufficient = drugStockService.validateStockSufficient(
                    itemVO.getDrugId(), itemVO.getQuantity(), updateReqVO.getWarehouseId());
            if (!sufficient) {
                throw exception(DRUG_STOCK_INSUFFICIENT);
            }
        }

        // 3. 更新出库主表
        HisDrugOutboundDO updateObj = BeanUtils.toBean(updateReqVO, HisDrugOutboundDO.class);
        BigDecimal totalAmount = calculateTotalAmount(updateReqVO.getItems());
        updateObj.setTotalAmount(totalAmount);
        outboundMapper.updateById(updateObj);

        // 4. 删除原有明细
        outboundItemMapper.deleteByOutboundId(updateReqVO.getId());

        // 5. 插入新的明细
        if (updateReqVO.getItems() != null && !updateReqVO.getItems().isEmpty()) {
            List<HisDrugOutboundItemDO> items = new ArrayList<>();
            for (HisDrugOutboundSaveReqVO.OutboundItemVO itemVO : updateReqVO.getItems()) {
                HisDrugOutboundItemDO item = BeanUtils.toBean(itemVO, HisDrugOutboundItemDO.class);
                item.setId(null);
                item.setOutboundId(updateReqVO.getId());

                HisDrugDO drug = drugMapper.selectById(itemVO.getDrugId());
                if (drug != null) {
                    item.setDrugCode(drug.getDrugCode());
                    item.setDrugName(drug.getDrugName());
                    item.setDrugSpec(drug.getSpec());
                }

                if (item.getQuantity() != null && item.getRetailPrice() != null) {
                    item.setAmount(item.getQuantity().multiply(item.getRetailPrice()));
                }

                items.add(item);
            }
            outboundItemMapper.insertBatch(items);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOutbound(Long id) {
        // 1. 校验存在且可修改
        validateOutboundCanUpdate(id);

        // 2. 删除明细
        outboundItemMapper.deleteByOutboundId(id);

        // 3. 删除主表
        outboundMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditOutbound(Long id, Long auditBy, String auditByName, boolean passed, String remark) {
        // 1. 校验可以审核
        HisDrugOutboundDO outbound = validateOutboundCanAudit(id);

        // 2. 更新审核状态
        HisDrugOutboundDO updateObj = new HisDrugOutboundDO();
        updateObj.setId(id);
        updateObj.setAuditStatus(passed ? 2 : 3); // 已审核/已拒绝
        updateObj.setAuditBy(auditBy);
        updateObj.setAuditTime(LocalDateTime.now());
        outboundMapper.updateById(updateObj);

        // 3. 如果审核通过，扣减库存
        if (passed) {
            List<HisDrugOutboundItemDO> items = outboundItemMapper.selectListByOutboundId(id);
            for (HisDrugOutboundItemDO item : items) {
                drugStockService.decreaseStock(
                        item.getDrugId(),
                        item.getQuantity(),
                        outbound.getWarehouseId()
                );
            }
        }
    }

    @Override
    public HisDrugOutboundDO getOutbound(Long id) {
        return outboundMapper.selectById(id);
    }

    @Override
    public HisDrugOutboundDO getOutboundByNo(String outboundNo) {
        return outboundMapper.selectByOutboundNo(outboundNo);
    }

    @Override
    public PageResult<HisDrugOutboundDO> getOutboundPage(HisDrugOutboundPageReqVO pageReqVO) {
        return outboundMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDrugOutboundItemDO> getOutboundItems(Long outboundId) {
        return outboundItemMapper.selectListByOutboundId(outboundId);
    }

    @Override
    public List<HisDrugOutboundDO> getPendingAuditList() {
        return outboundMapper.selectPendingAuditList();
    }

    @Override
    public HisDrugOutboundDO validateOutboundExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDrugOutboundDO outbound = outboundMapper.selectById(id);
        if (outbound == null) {
            throw exception(DRUG_OUTBOUND_NOT_EXISTS);
        }
        return outbound;
    }

    @Override
    public HisDrugOutboundDO validateOutboundCanAudit(Long id) {
        HisDrugOutboundDO outbound = validateOutboundExists(id);
        if (!outbound.canAudit()) {
            throw exception(DRUG_OUTBOUND_ALREADY_AUDITED);
        }
        return outbound;
    }

    @Override
    public HisDrugOutboundDO validateOutboundCanUpdate(Long id) {
        HisDrugOutboundDO outbound = validateOutboundExists(id);
        if (!outbound.isPendingAudit()) {
            throw exception(DRUG_OUTBOUND_ALREADY_AUDITED);
        }
        return outbound;
    }

    /**
     * 生成出库单号
     * 格式: CK + yyyyMMdd + 4位流水号
     */
    private String generateOutboundNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("CK%s%04d", dateStr, seq);
    }

    /**
     * 计算总金额
     */
    private BigDecimal calculateTotalAmount(List<HisDrugOutboundSaveReqVO.OutboundItemVO> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> {
                    if (item.getQuantity() != null && item.getRetailPrice() != null) {
                        return item.getQuantity().multiply(item.getRetailPrice());
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}