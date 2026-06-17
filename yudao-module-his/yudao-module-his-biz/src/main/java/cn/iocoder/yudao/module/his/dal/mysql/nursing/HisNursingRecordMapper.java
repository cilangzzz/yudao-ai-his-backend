package cn.iocoder.yudao.module.his.dal.mysql.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 护理记录 Mapper
 */
@Mapper
public interface HisNursingRecordMapper extends BaseMapperX<HisNursingRecordDO> {

    /**
     * 分页查询护理记录
     */
    default PageResult<HisNursingRecordDO> selectPage(
            cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisNursingRecordDO>()
                .eqIfPresent(HisNursingRecordDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisNursingRecordDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisNursingRecordDO::getNurseId, reqVO.getNurseId())
                .eqIfPresent(HisNursingRecordDO::getRecordType, reqVO.getRecordType())
                .betweenIfPresent(HisNursingRecordDO::getRecordTime, reqVO.getRecordTime())
                .orderByDesc(HisNursingRecordDO::getRecordTime));
    }

    /**
     * 按住院ID查询护理记录列表
     */
    default List<HisNursingRecordDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisNursingRecordDO>()
                .eq(HisNursingRecordDO::getAdmissionId, admissionId)
                .orderByDesc(HisNursingRecordDO::getRecordTime));
    }

    /**
     * 按患者ID查询护理记录列表
     */
    default List<HisNursingRecordDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisNursingRecordDO>()
                .eq(HisNursingRecordDO::getPatientId, patientId)
                .orderByDesc(HisNursingRecordDO::getRecordTime));
    }

    /**
     * 按记录类型查询
     */
    default List<HisNursingRecordDO> selectListByType(Long admissionId, Integer recordType) {
        return selectList(new LambdaQueryWrapperX<HisNursingRecordDO>()
                .eq(HisNursingRecordDO::getAdmissionId, admissionId)
                .eq(HisNursingRecordDO::getRecordType, recordType)
                .orderByDesc(HisNursingRecordDO::getRecordTime));
    }

    /**
     * 查询未签名的护理记录
     */
    default List<HisNursingRecordDO> selectUnsignedList(Long nurseId) {
        return selectList(new LambdaQueryWrapperX<HisNursingRecordDO>()
                .eq(HisNursingRecordDO::getNurseId, nurseId)
                .eq(HisNursingRecordDO::getSignatureStatus, 0)
                .orderByAsc(HisNursingRecordDO::getRecordTime));
    }

    /**
     * 查询待审核的护理记录
     */
    default List<HisNursingRecordDO> selectUnauditedList(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisNursingRecordDO>()
                .eq(HisNursingRecordDO::getAdmissionId, admissionId)
                .eq(HisNursingRecordDO::getSignatureStatus, 1)
                .eq(HisNursingRecordDO::getAuditStatus, 0)
                .orderByAsc(HisNursingRecordDO::getRecordTime));
    }
}