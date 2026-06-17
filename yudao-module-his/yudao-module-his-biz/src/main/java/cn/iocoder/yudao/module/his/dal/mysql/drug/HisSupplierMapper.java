package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisSupplierPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisSupplierDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 供应商 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisSupplierMapper extends BaseMapperX<HisSupplierDO> {

    /**
     * 分页查询
     */
    default PageResult<HisSupplierDO> selectPage(HisSupplierPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisSupplierDO>()
                .likeIfPresent(HisSupplierDO::getSupplierCode, reqVO.getSupplierCode())
                .likeIfPresent(HisSupplierDO::getSupplierName, reqVO.getSupplierName())
                .likeIfPresent(HisSupplierDO::getContactName, reqVO.getContactName())
                .eqIfPresent(HisSupplierDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisSupplierDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisSupplierDO::getId));
    }

    /**
     * 根据供应商编码查询
     */
    default HisSupplierDO selectBySupplierCode(String supplierCode) {
        return selectOne(HisSupplierDO::getSupplierCode, supplierCode);
    }

    /**
     * 根据状态查询列表
     */
    default List<HisSupplierDO> selectListByStatus(Integer status) {
        return selectList(HisSupplierDO::getStatus, status);
    }

    /**
     * 查询正常供应商列表
     */
    default List<HisSupplierDO> selectNormalList() {
        return selectListByStatus(1);
    }

}