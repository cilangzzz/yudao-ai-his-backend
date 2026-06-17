package cn.iocoder.yudao.module.his.dal.mysql.ward;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.ward.vo.HisWardPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.ward.HisWardDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 病区 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisWardMapper extends BaseMapperX<HisWardDO> {

    default PageResult<HisWardDO> selectPage(HisWardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisWardDO>()
                .likeIfPresent(HisWardDO::getWardCode, reqVO.getWardCode())
                .likeIfPresent(HisWardDO::getWardName, reqVO.getWardName())
                .eqIfPresent(HisWardDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisWardDO::getStatus, reqVO.getStatus())
                .orderByDesc(HisWardDO::getId));
    }

    default List<HisWardDO> selectListByDeptId(Long deptId) {
        return selectList(HisWardDO::getDeptId, deptId);
    }

    default List<HisWardDO> selectListByStatus(Integer status) {
        return selectList(HisWardDO::getStatus, status);
    }

    default HisWardDO selectByWardCode(String wardCode) {
        return selectOne(HisWardDO::getWardCode, wardCode);
    }

    default List<HisWardDO> selectAll() {
        return selectList(new LambdaQueryWrapperX<HisWardDO>()
                .eq(HisWardDO::getStatus, 1)
                .orderByAsc(HisWardDO::getWardCode));
    }
}
