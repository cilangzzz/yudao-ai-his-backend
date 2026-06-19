package cn.iocoder.yudao.module.his.dal.mysql.staff;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.staff.HisStaffDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 医护人员 Mapper
 */
@Mapper
public interface HisStaffMapper extends BaseMapperX<HisStaffDO> {

    /**
     * 分页查询医护人员
     */
    default PageResult<HisStaffDO> selectPage(HisStaffPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisStaffDO>()
                .likeIfPresent(HisStaffDO::getName, reqVO.getName())
                .eqIfPresent(HisStaffDO::getStaffCode, reqVO.getStaffCode())
                .eqIfPresent(HisStaffDO::getDoctorFlag, reqVO.getType() != null && reqVO.getType() == 1 ? 1 : null)
                .eqIfPresent(HisStaffDO::getNurseFlag, reqVO.getType() != null && reqVO.getType() == 2 ? 1 : null)
                .eqIfPresent(HisStaffDO::getPharmacistFlag, reqVO.getType() != null && reqVO.getType() == 4 ? 1 : null)
                .eqIfPresent(HisStaffDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisStaffDO::getStatus, reqVO.getStatus())
                .orderByDesc(HisStaffDO::getId));
    }

    /**
     * 根据人员编码查询
     */
    default HisStaffDO selectByStaffCode(String staffCode) {
        return selectOne(HisStaffDO::getStaffCode, staffCode);
    }

    /**
     * 查询医护人员列表
     */
    default List<HisStaffDO> selectList(Integer type, Long deptId, Integer status) {
        LambdaQueryWrapperX<HisStaffDO> wrapper = new LambdaQueryWrapperX<HisStaffDO>()
                .eqIfPresent(HisStaffDO::getStatus, status)
                .eqIfPresent(HisStaffDO::getDeptId, deptId);

        // 根据类型过滤
        if (type != null) {
            switch (type) {
                case 1: // 医生
                    wrapper.eq(HisStaffDO::getDoctorFlag, 1);
                    break;
                case 2: // 护士
                    wrapper.eq(HisStaffDO::getNurseFlag, 1);
                    break;
                case 3: // 技师
                    wrapper.and(w -> w.eq(HisStaffDO::getLabTechFlag, 1)
                            .or().eq(HisStaffDO::getRadTechFlag, 1));
                    break;
                case 4: // 药剂师
                    wrapper.eq(HisStaffDO::getPharmacistFlag, 1);
                    break;
                default:
                    break;
            }
        }

        return selectList(wrapper.orderByAsc(HisStaffDO::getName));
    }

    /**
     * 根据科室ID查询医生列表
     */
    default List<HisStaffDO> selectDoctorListByDeptId(Long deptId) {
        return selectList(new LambdaQueryWrapperX<HisStaffDO>()
                .eq(HisStaffDO::getDeptId, deptId)
                .eq(HisStaffDO::getDoctorFlag, 1)
                .eq(HisStaffDO::getStatus, 1)
                .orderByAsc(HisStaffDO::getName));
    }

    /**
     * 根据科室ID查询护士列表
     */
    default List<HisStaffDO> selectNurseListByDeptId(Long deptId) {
        return selectList(new LambdaQueryWrapperX<HisStaffDO>()
                .eq(HisStaffDO::getDeptId, deptId)
                .eq(HisStaffDO::getNurseFlag, 1)
                .eq(HisStaffDO::getStatus, 1)
                .orderByAsc(HisStaffDO::getName));
    }

}