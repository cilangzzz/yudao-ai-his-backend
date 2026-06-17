package cn.iocoder.yudao.module.his.dal.mysql.drugreturn;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo.OpDrugReturnPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 退药申请 Mapper
 */
@Mapper
public interface OpDrugReturnMapper extends BaseMapperX<OpDrugReturnDO> {

    /**
     * 分页查询
     */
    default PageResult<OpDrugReturnDO> selectPage(OpDrugReturnPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OpDrugReturnDO>()
                .likeIfPresent(OpDrugReturnDO::getReturnNo, reqVO.getReturnNo())
                .eqIfPresent(OpDrugReturnDO::getReturnType, reqVO.getReturnType())
                .likeIfPresent(OpDrugReturnDO::getPrescriptionNo, reqVO.getPrescriptionNo())
                .likeIfPresent(OpDrugReturnDO::getDispenseNo, reqVO.getDispenseNo())
                .eqIfPresent(OpDrugReturnDO::getPatientId, reqVO.getPatientId())
                .likeIfPresent(OpDrugReturnDO::getPatientName, reqVO.getPatientName())
                .likeIfPresent(OpDrugReturnDO::getPatientPhone, reqVO.getPatientPhone())
                .likeIfPresent(OpDrugReturnDO::getIdCardNo, reqVO.getIdCardNo())
                .eqIfPresent(OpDrugReturnDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(OpDrugReturnDO::getPharmacyId, reqVO.getPharmacyId())
                .eqIfPresent(OpDrugReturnDO::getReturnStatus, reqVO.getReturnStatus())
                .eqIfPresent(OpDrugReturnDO::getReturnReasonType, reqVO.getReturnReasonType())
                .betweenIfPresent(OpDrugReturnDO::getApplyTime, reqVO.getApplyTime())
                .betweenIfPresent(OpDrugReturnDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OpDrugReturnDO::getId));
    }

    /**
     * 根据退药单号查询
     */
    default OpDrugReturnDO selectByReturnNo(String returnNo) {
        return selectOne(OpDrugReturnDO::getReturnNo, returnNo);
    }

    /**
     * 根据处方ID查询退药记录
     */
    default List<OpDrugReturnDO> selectListByPrescriptionId(Long prescriptionId) {
        return selectList(OpDrugReturnDO::getPrescriptionId, prescriptionId);
    }

    /**
     * 根据发药ID查询退药记录
     */
    default List<OpDrugReturnDO> selectListByDispenseId(Long dispenseId) {
        return selectList(OpDrugReturnDO::getDispenseId, dispenseId);
    }

    /**
     * 根据患者ID查询退药记录
     */
    default List<OpDrugReturnDO> selectListByPatientId(Long patientId) {
        return selectList(OpDrugReturnDO::getPatientId, patientId);
    }

    /**
     * 根据状态查询退药记录列表
     */
    default List<OpDrugReturnDO> selectListByStatus(Integer status) {
        return selectList(OpDrugReturnDO::getReturnStatus, status);
    }

    /**
     * 根据科室ID和状态查询
     */
    default List<OpDrugReturnDO> selectListByDeptIdAndStatus(Long deptId, Integer status) {
        return selectList(new LambdaQueryWrapperX<OpDrugReturnDO>()
                .eq(OpDrugReturnDO::getDeptId, deptId)
                .eqIfPresent(OpDrugReturnDO::getReturnStatus, status));
    }

    /**
     * 根据药房ID和状态查询
     */
    default List<OpDrugReturnDO> selectListByPharmacyIdAndStatus(Long pharmacyId, Integer status) {
        return selectList(new LambdaQueryWrapperX<OpDrugReturnDO>()
                .eq(OpDrugReturnDO::getPharmacyId, pharmacyId)
                .eqIfPresent(OpDrugReturnDO::getReturnStatus, status));
    }

    /**
     * 统计指定状态的退药数量
     */
    default Long countByStatus(Integer status) {
        return selectCount(OpDrugReturnDO::getReturnStatus, status);
    }

    /**
     * 根据挂号ID查询
     */
    default List<OpDrugReturnDO> selectListByRegisterId(Long registerId) {
        return selectList(OpDrugReturnDO::getRegisterId, registerId);
    }

    /**
     * 根据住院ID查询
     */
    default List<OpDrugReturnDO> selectListByAdmissionId(Long admissionId) {
        return selectList(OpDrugReturnDO::getAdmissionId, admissionId);
    }

}
