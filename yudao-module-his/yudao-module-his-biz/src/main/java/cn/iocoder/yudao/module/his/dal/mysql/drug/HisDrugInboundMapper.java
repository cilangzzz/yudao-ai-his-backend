package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInboundPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 入库记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugInboundMapper extends BaseMapperX<HisDrugInboundDO> {

    /**
     * 分页查询
     */
    default PageResult<HisDrugInboundDO> selectPage(HisDrugInboundPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDrugInboundDO>()
                .likeIfPresent(HisDrugInboundDO::getInboundNo, reqVO.getInboundNo())
                .eqIfPresent(HisDrugInboundDO::getInboundType, reqVO.getInboundType())
                .eqIfPresent(HisDrugInboundDO::getSupplierId, reqVO.getSupplierId())
                .likeIfPresent(HisDrugInboundDO::getSupplierName, reqVO.getSupplierName())
                .eqIfPresent(HisDrugInboundDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(HisDrugInboundDO::getAuditStatus, reqVO.getAuditStatus())
                .betweenIfPresent(HisDrugInboundDO::getInboundTime, reqVO.getInboundTime())
                .betweenIfPresent(HisDrugInboundDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisDrugInboundDO::getId));
    }

    /**
     * 根据入库单号查询
     */
    default HisDrugInboundDO selectByInboundNo(String inboundNo) {
        return selectOne(HisDrugInboundDO::getInboundNo, inboundNo);
    }

    /**
     * 根据供应商ID查询列表
     */
    default List<HisDrugInboundDO> selectListBySupplierId(Long supplierId) {
        return selectList(HisDrugInboundDO::getSupplierId, supplierId);
    }

    /**
     * 根据仓库ID查询列表
     */
    default List<HisDrugInboundDO> selectListByWarehouseId(Long warehouseId) {
        return selectList(HisDrugInboundDO::getWarehouseId, warehouseId);
    }

    /**
     * 根据审核状态查询列表
     */
    default List<HisDrugInboundDO> selectListByAuditStatus(Integer auditStatus) {
        return selectList(HisDrugInboundDO::getAuditStatus, auditStatus);
    }

    /**
     * 查询待审核列表
     */
    default List<HisDrugInboundDO> selectPendingAuditList() {
        return selectListByAuditStatus(1);
    }

}