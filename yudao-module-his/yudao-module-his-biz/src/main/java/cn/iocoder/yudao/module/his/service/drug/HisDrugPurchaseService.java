package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPurchasePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPurchaseSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 采购计划管理 Service 接口
 *
 * @author 芋道源码
 */
public interface HisDrugPurchaseService {

    /**
     * 创建采购计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPurchase(@Valid HisDrugPurchaseSaveReqVO createReqVO);

    /**
     * 更新采购计划
     *
     * @param updateReqVO 更新信息
     */
    void updatePurchase(@Valid HisDrugPurchaseSaveReqVO updateReqVO);

    /**
     * 删除采购计划
     *
     * @param id 编号
     */
    void deletePurchase(Long id);

    /**
     * 提交采购计划
     *
     * @param id 编号
     * @param applyBy 申请人ID
     */
    void submitPurchase(Long id, Long applyBy);

    /**
     * 审批采购计划
     *
     * @param id 编号
     * @param auditBy 审批人ID
     * @param approved 是否通过
     */
    void approvePurchase(Long id, Long auditBy, boolean approved);

    /**
     * 获得采购计划
     *
     * @param id 编号
     * @return 采购计划
     */
    HisDrugPurchaseDO getPurchase(Long id);

    /**
     * 根据采购单号查询
     *
     * @param purchaseNo 采购单号
     * @return 采购计划
     */
    HisDrugPurchaseDO getPurchaseByNo(String purchaseNo);

    /**
     * 获得采购计划分页
     *
     * @param pageReqVO 分页查询
     * @return 采购计划分页
     */
    PageResult<HisDrugPurchaseDO> getPurchasePage(HisDrugPurchasePageReqVO pageReqVO);

    /**
     * 获得采购明细列表
     *
     * @param purchaseId 采购ID
     * @return 采购明细列表
     */
    List<HisDrugPurchaseItemDO> getPurchaseItems(Long purchaseId);

    /**
     * 获得待审批采购列表
     *
     * @return 采购列表
     */
    List<HisDrugPurchaseDO> getPendingApprovalList();

    /**
     * 校验采购计划存在
     *
     * @param id 编号
     * @return 采购计划
     */
    HisDrugPurchaseDO validatePurchaseExists(Long id);

    /**
     * 校验采购计划可以修改
     *
     * @param id 编号
     * @return 采购计划
     */
    HisDrugPurchaseDO validatePurchaseCanUpdate(Long id);

}