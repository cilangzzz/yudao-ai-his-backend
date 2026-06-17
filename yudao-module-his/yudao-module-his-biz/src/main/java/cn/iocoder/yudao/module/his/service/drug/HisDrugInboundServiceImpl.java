package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInboundPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInboundSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundItemDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugInboundItemMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugInboundMapper;
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
 * 入库管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisDrugInboundServiceImpl implements HisDrugInboundService {

    @Resource
    private HisDrugInboundMapper inboundMapper;

    @Resource
    private HisDrugInboundItemMapper inboundItemMapper;

    @Resource
    private HisDrugMapper drugMapper;

    @Resource
    private HisDrugStockService drugStockService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInbound(HisDrugInboundSaveReqVO createReqVO) {
        // 1. 生成入库单号
        String inboundNo = generateInboundNo();

        // 2. 创建入库主表
        HisDrugInboundDO inbound = BeanUtils.toBean(createReqVO, HisDrugInboundDO.class);
        inbound.setInboundNo(inboundNo);
        inbound.setAuditStatus(1); // 待审核

        // 3. 计算总金额
        BigDecimal totalAmount = calculateTotalAmount(createReqVO.getItems());
        inbound.setTotalAmount(totalAmount);

        // 4. 插入入库记录
        inboundMapper.insert(inbound);

        // 5. 插入入库明细
        if (createReqVO.getItems() != null && !createReqVO.getItems().isEmpty()) {
            List<HisDrugInboundItemDO> items = new ArrayList<>();
            for (HisDrugInboundSaveReqVO.InboundItemVO itemVO : createReqVO.getItems()) {
                HisDrugInboundItemDO item = BeanUtils.toBean(itemVO, HisDrugInboundItemDO.class);
                item.setInboundId(inbound.getId());

                // 查询药品信息
                HisDrugDO drug = drugMapper.selectById(itemVO.getDrugId());
                if (drug != null) {
                    item.setDrugCode(drug.getDrugCode());
                    item.setDrugName(drug.getDrugName());
                    item.setDrugSpec(drug.getSpec());
                }

                // 计算金额
                if (item.getQuantity() != null && item.getPurchasePrice() != null) {
                    item.setAmount(item.getQuantity().multiply(item.getPurchasePrice()));
                }

                items.add(item);
            }
            inboundItemMapper.insertBatch(items);
        }

        return inbound.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInbound(HisDrugInboundSaveReqVO updateReqVO) {
        // 1. 校验存在且可修改
        validateInboundCanUpdate(updateReqVO.getId());

        // 2. 更新入库主表
        HisDrugInboundDO updateObj = BeanUtils.toBean(updateReqVO, HisDrugInboundDO.class);
        BigDecimal totalAmount = calculateTotalAmount(updateReqVO.getItems());
        updateObj.setTotalAmount(totalAmount);
        inboundMapper.updateById(updateObj);

        // 3. 删除原有明细
        inboundItemMapper.deleteByInboundId(updateReqVO.getId());

        // 4. 插入新的明细
        if (updateReqVO.getItems() != null && !updateReqVO.getItems().isEmpty()) {
            List<HisDrugInboundItemDO> items = new ArrayList<>();
            for (HisDrugInboundSaveReqVO.InboundItemVO itemVO : updateReqVO.getItems()) {
                HisDrugInboundItemDO item = BeanUtils.toBean(itemVO, HisDrugInboundItemDO.class);
                item.setId(null);
                item.setInboundId(updateReqVO.getId());

                HisDrugDO drug = drugMapper.selectById(itemVO.getDrugId());
                if (drug != null) {
                    item.setDrugCode(drug.getDrugCode());
                    item.setDrugName(drug.getDrugName());
                    item.setDrugSpec(drug.getSpec());
                }

                if (item.getQuantity() != null && item.getPurchasePrice() != null) {
                    item.setAmount(item.getQuantity().multiply(item.getPurchasePrice()));
                }

                items.add(item);
            }
            inboundItemMapper.insertBatch(items);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInbound(Long id) {
        // 1. 校验存在且可修改
        validateInboundCanUpdate(id);

        // 2. 删除明细
        inboundItemMapper.deleteByInboundId(id);

        // 3. 删除主表
        inboundMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditInbound(Long id, Long auditBy, String auditByName, boolean passed, String remark) {
        // 1. 校验可以审核
        HisDrugInboundDO inbound = validateInboundCanAudit(id);

        // 2. 更新审核状态
        HisDrugInboundDO updateObj = new HisDrugInboundDO();
        updateObj.setId(id);
        updateObj.setAuditStatus(passed ? 2 : 3); // 已审核/已拒绝
        updateObj.setAuditBy(auditBy);
        updateObj.setAuditTime(LocalDateTime.now());
        inboundMapper.updateById(updateObj);

        // 3. 如果审核通过，增加库存
        if (passed) {
            List<HisDrugInboundItemDO> items = inboundItemMapper.selectListByInboundId(id);
            for (HisDrugInboundItemDO item : items) {
                drugStockService.increaseStock(
                        item.getDrugId(),
                        item.getBatchNo(),
                        item.getQuantity(),
                        item.getRetailPrice(),
                        item.getPurchasePrice(),
                        item.getExpiryDate(),
                        inbound.getWarehouseId()
                );
            }
        }
    }

    @Override
    public HisDrugInboundDO getInbound(Long id) {
        return inboundMapper.selectById(id);
    }

    @Override
    public HisDrugInboundDO getInboundByNo(String inboundNo) {
        return inboundMapper.selectByInboundNo(inboundNo);
    }

    @Override
    public PageResult<HisDrugInboundDO> getInboundPage(HisDrugInboundPageReqVO pageReqVO) {
        return inboundMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDrugInboundItemDO> getInboundItems(Long inboundId) {
        return inboundItemMapper.selectListByInboundId(inboundId);
    }

    @Override
    public List<HisDrugInboundDO> getPendingAuditList() {
        return inboundMapper.selectPendingAuditList();
    }

    @Override
    public HisDrugInboundDO validateInboundExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDrugInboundDO inbound = inboundMapper.selectById(id);
        if (inbound == null) {
            throw exception(DRUG_INBOUND_NOT_EXISTS);
        }
        return inbound;
    }

    @Override
    public HisDrugInboundDO validateInboundCanAudit(Long id) {
        HisDrugInboundDO inbound = validateInboundExists(id);
        if (!inbound.canAudit()) {
            throw exception(DRUG_INBOUND_ALREADY_AUDITED);
        }
        return inbound;
    }

    @Override
    public HisDrugInboundDO validateInboundCanUpdate(Long id) {
        HisDrugInboundDO inbound = validateInboundExists(id);
        if (!inbound.isPendingAudit()) {
            throw exception(DRUG_INBOUND_ALREADY_AUDITED);
        }
        return inbound;
    }

    /**
     * 生成入库单号
     * 格式: RK + yyyyMMdd + 4位流水号
     */
    private String generateInboundNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = System.currentTimeMillis() % 10000;
        return String.format("RK%s%04d", dateStr, seq);
    }

    /**
     * 计算总金额
     */
    private BigDecimal calculateTotalAmount(List<HisDrugInboundSaveReqVO.InboundItemVO> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> {
                    if (item.getQuantity() != null && item.getPurchasePrice() != null) {
                        return item.getQuantity().multiply(item.getPurchasePrice());
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}