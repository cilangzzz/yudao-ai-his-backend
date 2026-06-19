package cn.iocoder.yudao.module.his.dal.mysql.department;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.department.HisDepartmentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 科室 Mapper
 */
@Mapper
public interface HisDepartmentMapper extends BaseMapperX<HisDepartmentDO> {

    /**
     * 分页查询科室
     */
    default PageResult<HisDepartmentDO> selectPage(HisDepartmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDepartmentDO>()
                .likeIfPresent(HisDepartmentDO::getDeptName, reqVO.getName())
                .eqIfPresent(HisDepartmentDO::getDeptCode, reqVO.getDeptCode())
                .eqIfPresent(HisDepartmentDO::getDeptType, reqVO.getType())
                .eqIfPresent(HisDepartmentDO::getStatus, reqVO.getStatus())
                .orderByAsc(HisDepartmentDO::getSortOrder)
                .orderByDesc(HisDepartmentDO::getId));
    }

    /**
     * 根据科室编码查询
     */
    default HisDepartmentDO selectByDeptCode(String deptCode) {
        return selectOne(HisDepartmentDO::getDeptCode, deptCode);
    }

    /**
     * 查询科室列表
     */
    default List<HisDepartmentDO> selectList(Integer deptType, Integer status) {
        return selectList(new LambdaQueryWrapperX<HisDepartmentDO>()
                .eqIfPresent(HisDepartmentDO::getDeptType, deptType)
                .eqIfPresent(HisDepartmentDO::getStatus, status)
                .orderByAsc(HisDepartmentDO::getSortOrder));
    }

    /**
     * 根据父ID查询子科室
     */
    default List<HisDepartmentDO> selectByParentId(Long parentId) {
        return selectList(HisDepartmentDO::getParentId, parentId);
    }

}