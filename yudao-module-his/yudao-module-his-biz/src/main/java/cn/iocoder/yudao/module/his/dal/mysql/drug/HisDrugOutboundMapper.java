package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugOutboundPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出库记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugOutboundMapper extends BaseMapperX<HisDrugOutboundDO> {

    /**
     * 分页查询
     */
    default PageResult<HisDrugOutboundDO> selectPage(HisDrugOutboundPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDrugOutboundDO>()
                .likeIfPresent(HisDrugOutboundDO::getOutboundNo, reqVO.getOutboundNo())
                .eqIfPresent(HisDrugOutboundDO::getOutboundType, reqVO.getOutboundType())
                .eqIfPresent(HisDrugOutboundDO::getTargetId, reqVO.getTargetId())
                .likeIfPresent(HisDrugOutboundDO::getTargetName, reqVO.getTargetName())
                .eqIfPresent(HisDrugOutboundDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(HisDrugOutboundDO::getAuditStatus, reqVO.getAuditStatus())
                .betweenIfPresent(HisDrugOutboundDO::getOutboundTime, reqVO.getOutboundTime())
                .betweenIfPresent(HisDrugOutboundDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisDrugOutboundDO::getId));
    }

    /**
     * 根据出库单号查询
     */
    default HisDrugOutboundDO selectByOutboundNo(String outboundNo) {
        return selectOne(HisDrugOutboundDO::getOutboundNo, outboundNo);
    }

    /**
     * 根据目标ID查询列表
     */
    default List<HisDrugOutboundDO> selectListByTargetId(Long targetId) {
        return selectList(HisDrugOutboundDO::getTargetId, targetId);
    }

    /**
     * 根据仓库ID查询列表
     */
    default List<HisDrugOutboundDO> selectListByWarehouseId(Long warehouseId) {
        return selectList(HisDrugOutboundDO::getWarehouseId, warehouseId);
    }

    /**
     * 根据审核状态查询列表
     */
    default List<HisDrugOutboundDO> selectListByAuditStatus(Integer auditStatus) {
        return selectList(HisDrugOutboundDO::getAuditStatus, auditStatus);
    }

    /**
     * 查询待审核列表
     */
    default List<HisDrugOutboundDO> selectPendingAuditList() {
        return selectListByAuditStatus(1);
    }

}