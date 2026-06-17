package cn.iocoder.yudao.module.his.dal.mysql.preAdmission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo.HisPreAdmissionPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.preAdmission.HisPreAdmissionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预住院记录 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisPreAdmissionMapper extends BaseMapperX<HisPreAdmissionDO> {

    default PageResult<HisPreAdmissionDO> selectPage(HisPreAdmissionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisPreAdmissionDO>()
                .likeIfPresent(HisPreAdmissionDO::getPreAdmissionNo, reqVO.getPreAdmissionNo())
                .likeIfPresent(HisPreAdmissionDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisPreAdmissionDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisPreAdmissionDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisPreAdmissionDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisPreAdmissionDO::getScheduledDate, reqVO.getScheduledDateStart(), reqVO.getScheduledDateEnd())
                .orderByDesc(HisPreAdmissionDO::getId));
    }

    default HisPreAdmissionDO selectByPreAdmissionNo(String preAdmissionNo) {
        return selectOne(HisPreAdmissionDO::getPreAdmissionNo, preAdmissionNo);
    }

    default List<HisPreAdmissionDO> selectListByPatientId(Long patientId) {
        return selectList(HisPreAdmissionDO::getPatientId, patientId);
    }

    default List<HisPreAdmissionDO> selectPendingList() {
        return selectList(HisPreAdmissionDO::getStatus, 1);
    }

    default List<HisPreAdmissionDO> selectPendingListByDept(Long deptId) {
        return selectList(new LambdaQueryWrapperX<HisPreAdmissionDO>()
                .eq(HisPreAdmissionDO::getDeptId, deptId)
                .eq(HisPreAdmissionDO::getStatus, 1)
                .orderByAsc(HisPreAdmissionDO::getScheduledDate));
    }

    default HisPreAdmissionDO selectByRegisterId(Long registerId) {
        return selectOne(HisPreAdmissionDO::getRegisterId, registerId);
    }

    default int countPendingByDept(Long deptId) {
        return selectCount(new LambdaQueryWrapperX<HisPreAdmissionDO>()
                .eq(HisPreAdmissionDO::getDeptId, deptId)
                .eq(HisPreAdmissionDO::getStatus, 1))
                .intValue();
    }
}