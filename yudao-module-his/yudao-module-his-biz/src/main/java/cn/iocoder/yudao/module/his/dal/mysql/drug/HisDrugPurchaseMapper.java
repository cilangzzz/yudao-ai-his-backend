package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPurchasePageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugPurchaseDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 采购计划 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugPurchaseMapper extends BaseMapperX<HisDrugPurchaseDO> {

    /**
     * 分页查询
     */
    default PageResult<HisDrugPurchaseDO> selectPage(HisDrugPurchasePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDrugPurchaseDO>()
                .likeIfPresent(HisDrugPurchaseDO::getPurchaseNo, reqVO.getPurchaseNo())
                .eqIfPresent(HisDrugPurchaseDO::getSupplierId, reqVO.getSupplierId())
                .likeIfPresent(HisDrugPurchaseDO::getSupplierName, reqVO.getSupplierName())
                .eqIfPresent(HisDrugPurchaseDO::getPurchaseType, reqVO.getPurchaseType())
                .eqIfPresent(HisDrugPurchaseDO::getPurchaseStatus, reqVO.getPurchaseStatus())
                .betweenIfPresent(HisDrugPurchaseDO::getApplyTime, reqVO.getApplyTime())
                .betweenIfPresent(HisDrugPurchaseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisDrugPurchaseDO::getId));
    }

    /**
     * 根据采购单号查询
     */
    default HisDrugPurchaseDO selectByPurchaseNo(String purchaseNo) {
        return selectOne(HisDrugPurchaseDO::getPurchaseNo, purchaseNo);
    }

    /**
     * 根据供应商ID查询列表
     */
    default List<HisDrugPurchaseDO> selectListBySupplierId(Long supplierId) {
        return selectList(HisDrugPurchaseDO::getSupplierId, supplierId);
    }

    /**
     * 根据状态查询列表
     */
    default List<HisDrugPurchaseDO> selectListByStatus(Integer status) {
        return selectList(HisDrugPurchaseDO::getPurchaseStatus, status);
    }

    /**
     * 查询待审批列表
     */
    default List<HisDrugPurchaseDO> selectPendingApprovalList() {
        return selectListByStatus(2);
    }

}