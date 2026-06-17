package cn.iocoder.yudao.module.his.dal.mysql.bed;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.bed.vo.HisBedPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.bed.HisBedDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 床位 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisBedMapper extends BaseMapperX<HisBedDO> {

    default PageResult<HisBedDO> selectPage(HisBedPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisBedDO>()
                .likeIfPresent(HisBedDO::getBedNo, reqVO.getBedNo())
                .eqIfPresent(HisBedDO::getWardId, reqVO.getWardId())
                .eqIfPresent(HisBedDO::getRoomNo, reqVO.getRoomNo())
                .eqIfPresent(HisBedDO::getBedStatus, reqVO.getBedStatus())
                .eqIfPresent(HisBedDO::getBedType, reqVO.getBedType())
                .eqIfPresent(HisBedDO::getBedClass, reqVO.getBedClass())
                .orderByAsc(HisBedDO::getSortOrder)
                .orderByDesc(HisBedDO::getId));
    }

    default List<HisBedDO> selectListByWardId(Long wardId) {
        return selectList(new LambdaQueryWrapperX<HisBedDO>()
                .eq(HisBedDO::getWardId, wardId)
                .orderByAsc(HisBedDO::getSortOrder));
    }

    default List<HisBedDO> selectListByStatus(Integer status) {
        return selectList(HisBedDO::getBedStatus, status);
    }

    default List<HisBedDO> selectEmptyBedsByWardId(Long wardId) {
        return selectList(new LambdaQueryWrapperX<HisBedDO>()
                .eq(HisBedDO::getWardId, wardId)
                .eq(HisBedDO::getBedStatus, 1) // 空床
                .orderByAsc(HisBedDO::getSortOrder));
    }

    default HisBedDO selectByBedNo(String bedNo) {
        return selectOne(HisBedDO::getBedNo, bedNo);
    }

    default HisBedDO selectByWardIdAndBedNo(Long wardId, String bedNo) {
        return selectOne(new LambdaQueryWrapperX<HisBedDO>()
                .eq(HisBedDO::getWardId, wardId)
                .eq(HisBedDO::getBedNo, bedNo));
    }

    default List<HisBedDO> selectEmptyBeds() {
        return selectList(new LambdaQueryWrapperX<HisBedDO>()
                .eq(HisBedDO::getBedStatus, 1) // 空床
                .orderByAsc(HisBedDO::getWardId)
                .orderByAsc(HisBedDO::getSortOrder));
    }

    default int countByWardId(Long wardId) {
        return selectCount(HisBedDO::getWardId, wardId).intValue();
    }

    default int countUsedByWardId(Long wardId) {
        return selectCount(new LambdaQueryWrapperX<HisBedDO>()
                .eq(HisBedDO::getWardId, wardId)
                .eq(HisBedDO::getBedStatus, 2)) // 占用
                .intValue();
    }

    /**
     * 占用床位
     */
    @Update("UPDATE his_bed SET bed_status = 2, current_patient_id = #{patientId}, " +
            "current_patient_name = #{patientName}, admission_id = #{admissionId}, " +
            "update_time = NOW(), updater = #{updater} WHERE id = #{id} AND bed_status = 1")
    int occupyBed(@Param("id") Long id, @Param("patientId") Long patientId,
                  @Param("patientName") String patientName, @Param("admissionId") Long admissionId,
                  @Param("updater") String updater);

    /**
     * 释放床位
     */
    @Update("UPDATE his_bed SET bed_status = 4, current_patient_id = NULL, " +
            "current_patient_name = NULL, admission_id = NULL, " +
            "update_time = NOW(), updater = #{updater} WHERE id = #{id} AND bed_status = 2")
    int releaseBed(@Param("id") Long id, @Param("updater") String updater);

    /**
     * 清洁完成
     */
    @Update("UPDATE his_bed SET bed_status = 1, update_time = NOW(), updater = #{updater} " +
            "WHERE id = #{id} AND bed_status = 4")
    int cleanComplete(@Param("id") Long id, @Param("updater") String updater);
}
