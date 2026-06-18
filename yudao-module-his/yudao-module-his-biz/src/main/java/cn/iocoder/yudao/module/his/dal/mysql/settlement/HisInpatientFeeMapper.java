package cn.iocoder.yudao.module.his.dal.mysql.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientFeePageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientFeeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 住院费用明细 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisInpatientFeeMapper extends BaseMapperX<HisInpatientFeeDO> {

    /**
     * 根据入院记录ID查询费用列表
     */
    default List<HisInpatientFeeDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisInpatientFeeDO>()
                .eq(HisInpatientFeeDO::getAdmissionId, admissionId)
                .orderByAsc(HisInpatientFeeDO::getFeeDate));
    }

    /**
     * 根据入院记录ID和费用状态查询费用列表
     */
    default List<HisInpatientFeeDO> selectListByAdmissionIdAndStatus(Long admissionId, Integer feeStatus) {
        return selectList(new LambdaQueryWrapperX<HisInpatientFeeDO>()
                .eq(HisInpatientFeeDO::getAdmissionId, admissionId)
                .eq(HisInpatientFeeDO::getFeeStatus, feeStatus)
                .orderByAsc(HisInpatientFeeDO::getFeeDate));
    }

    /**
     * 根据患者ID查询费用列表
     */
    default List<HisInpatientFeeDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisInpatientFeeDO>()
                .eq(HisInpatientFeeDO::getPatientId, patientId)
                .orderByDesc(HisInpatientFeeDO::getCreateTime));
    }

    /**
     * 根据结算单ID查询费用列表
     */
    default List<HisInpatientFeeDO> selectListBySettlementId(Long settlementId) {
        return selectList(new LambdaQueryWrapperX<HisInpatientFeeDO>()
                .eq(HisInpatientFeeDO::getSettlementId, settlementId)
                .orderByAsc(HisInpatientFeeDO::getFeeDate));
    }

    /**
     * 根据费用单号查询
     */
    default HisInpatientFeeDO selectByFeeNo(String feeNo) {
        return selectOne(new LambdaQueryWrapperX<HisInpatientFeeDO>()
                .eq(HisInpatientFeeDO::getFeeNo, feeNo));
    }

    /**
     * 统计入院记录的费用总额
     */
    @Select("SELECT IFNULL(SUM(total_amount), 0) FROM his_inpatient_fee " +
            "WHERE tenant_id = #{tenantId} AND admission_id = #{admissionId} AND deleted = 0")
    BigDecimal sumTotalAmountByAdmission(@Param("tenantId") Long tenantId, @Param("admissionId") Long admissionId);

    /**
     * 统计入院记录的未结算费用总额
     */
    @Select("SELECT IFNULL(SUM(total_amount), 0) FROM his_inpatient_fee " +
            "WHERE tenant_id = #{tenantId} AND admission_id = #{admissionId} AND fee_status = 0 AND deleted = 0")
    BigDecimal sumUnsettledAmountByAdmission(@Param("tenantId") Long tenantId, @Param("admissionId") Long admissionId);

    /**
     * 统计入院记录按项目类型分组费用
     */
    @Select("SELECT IFNULL(SUM(total_amount), 0) FROM his_inpatient_fee " +
            "WHERE tenant_id = #{tenantId} AND admission_id = #{admissionId} AND item_type = #{itemType} AND deleted = 0")
    BigDecimal sumAmountByAdmissionAndItemType(@Param("tenantId") Long tenantId,
                                               @Param("admissionId") Long admissionId,
                                               @Param("itemType") Integer itemType);

    /**
     * 统计入院记录的应付金额总额
     */
    @Select("SELECT IFNULL(SUM(pay_amount), 0) FROM his_inpatient_fee " +
            "WHERE tenant_id = #{tenantId} AND admission_id = #{admissionId} AND fee_status = 0 AND deleted = 0")
    BigDecimal sumPayAmountByAdmission(@Param("tenantId") Long tenantId, @Param("admissionId") Long admissionId);

    /**
     * 查询入院记录在指定日期范围内的费用
     */
    default List<HisInpatientFeeDO> selectListByAdmissionIdAndDateRange(Long admissionId,
                                                                         LocalDate startDate,
                                                                         LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<HisInpatientFeeDO>()
                .eq(HisInpatientFeeDO::getAdmissionId, admissionId)
                .ge(startDate != null, HisInpatientFeeDO::getFeeDate, startDate)
                .le(endDate != null, HisInpatientFeeDO::getFeeDate, endDate)
                .orderByAsc(HisInpatientFeeDO::getFeeDate));
    }

    /**
     * 批量更新费用状态
     */
    @Select("<script>" +
            "UPDATE his_inpatient_fee SET fee_status = #{feeStatus}, settlement_id = #{settlementId}, " +
            "update_time = NOW() WHERE id IN " +
            "<foreach collection='feeIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateFeeStatus(@Param("feeIds") List<Long> feeIds,
                             @Param("feeStatus") Integer feeStatus,
                             @Param("settlementId") Long settlementId);

    /**
     * 分页查询费用明细
     */
    default PageResult<HisInpatientFeeDO> selectPage(HisInpatientFeePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisInpatientFeeDO>()
                .eqIfPresent(HisInpatientFeeDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisInpatientFeeDO::getPatientId, reqVO.getPatientId())
                .likeIfPresent(HisInpatientFeeDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisInpatientFeeDO::getInpatientNo, reqVO.getInpatientNo())
                .eqIfPresent(HisInpatientFeeDO::getFeeNo, reqVO.getFeeNo())
                .eqIfPresent(HisInpatientFeeDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(HisInpatientFeeDO::getItemName, reqVO.getItemName())
                .eqIfPresent(HisInpatientFeeDO::getItemType, reqVO.getItemType())
                .eqIfPresent(HisInpatientFeeDO::getFeeStatus, reqVO.getFeeStatus())
                .eqIfPresent(HisInpatientFeeDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(HisInpatientFeeDO::getFeeDate, reqVO.getFeeDateStart(), reqVO.getFeeDateEnd())
                .orderByDesc(HisInpatientFeeDO::getCreateTime));
    }

    /**
     * 根据入院记录ID更新费用状态
     */
    @Update("UPDATE his_inpatient_fee SET fee_status = #{feeStatus}, settlement_id = #{settlementId}, " +
            "update_time = NOW() WHERE admission_id = #{admissionId} AND deleted = 0")
    int updateStatusByAdmissionId(@Param("admissionId") Long admissionId,
                                   @Param("feeStatus") Integer feeStatus,
                                   @Param("settlementId") Long settlementId);

}