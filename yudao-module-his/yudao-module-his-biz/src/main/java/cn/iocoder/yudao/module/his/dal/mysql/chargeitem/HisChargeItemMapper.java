package cn.iocoder.yudao.module.his.dal.mysql.chargeitem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.chargeitem.vo.HisChargeItemPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.chargeitem.HisChargeItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收费项目 Mapper
 */
@Mapper
public interface HisChargeItemMapper extends BaseMapperX<HisChargeItemDO> {

    /**
     * 分页查询收费项目
     */
    default PageResult<HisChargeItemDO> selectPage(HisChargeItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisChargeItemDO>()
                .likeIfPresent(HisChargeItemDO::getItemName, reqVO.getName())
                .eqIfPresent(HisChargeItemDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(HisChargeItemDO::getItemCategory, reqVO.getType())
                .eqIfPresent(HisChargeItemDO::getInsuranceCategory, reqVO.getInsuranceType())
                .eqIfPresent(HisChargeItemDO::getExecutionDept, reqVO.getDeptId())
                .eqIfPresent(HisChargeItemDO::getStatus, reqVO.getStatus())
                .orderByDesc(HisChargeItemDO::getId));
    }

    /**
     * 根据项目编码查询
     */
    default HisChargeItemDO selectByItemCode(String itemCode) {
        return selectOne(HisChargeItemDO::getItemCode, itemCode);
    }

    /**
     * 查询收费项目列表
     */
    default List<HisChargeItemDO> selectList(Integer itemCategory, Integer status) {
        return selectList(new LambdaQueryWrapperX<HisChargeItemDO>()
                .eqIfPresent(HisChargeItemDO::getItemCategory, itemCategory)
                .eqIfPresent(HisChargeItemDO::getStatus, status)
                .orderByAsc(HisChargeItemDO::getItemCode));
    }

}