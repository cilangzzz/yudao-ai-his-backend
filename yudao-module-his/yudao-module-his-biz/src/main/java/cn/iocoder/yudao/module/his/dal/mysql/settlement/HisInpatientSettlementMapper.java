package cn.iocoder.yudao.module.his.dal.mysql.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientSettlementPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientSettlementDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 住院结算主表 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisInpatientSettlementMapper extends BaseMapperX<HisInpatientSettlementDO> {

    /**
     * 根据入院记录ID查询结算单
     */
    default HisInpatientSettlementDO selectByAdmissionId(Long admissionId) {
        return selectOne(new LambdaQueryWrapperX<HisInpatientSettlementDO>()
                .eq(HisInpatientSettlementDO::getAdmissionId, admissionId)
                .orderByDesc(HisInpatientSettlementDO::getCreateTime)
                .last("LIMIT 1"));
    }

    /**
     * 根据入院记录ID查询所有结算单
     */
    default List<HisInpatientSettlementDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<HisInpatientSettlementDO>()
                .eq(HisInpatientSettlementDO::getAdmissionId, admissionId)
                .orderByDesc(HisInpatientSettlementDO::getCreateTime));
    }

    /**
     * 根据结算单号查询
     */
    default HisInpatientSettlementDO selectBySettlementNo(String settlementNo) {
        return selectOne(new LambdaQueryWrapperX<HisInpatientSettlementDO>()
                .eq(HisInpatientSettlementDO::getSettlementNo, settlementNo));
    }

    /**
     * 根据发票号码查询
     */
    default HisInpatientSettlementDO selectByInvoiceNo(String invoiceNo) {
        return selectOne(new LambdaQueryWrapperX<HisInpatientSettlementDO>()
                .eq(HisInpatientSettlementDO::getInvoiceNo, invoiceNo));
    }

    /**
     * 根据患者ID查询结算单列表
     */
    default List<HisInpatientSettlementDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisInpatientSettlementDO>()
                .eq(HisInpatientSettlementDO::getPatientId, patientId)
                .orderByDesc(HisInpatientSettlementDO::getCreateTime));
    }

    /**
     * 根据结算状态查询结算单列表
     */
    default List<HisInpatientSettlementDO> selectListByStatus(Integer settlementStatus) {
        return selectList(new LambdaQueryWrapperX<HisInpatientSettlementDO>()
                .eq(HisInpatientSettlementDO::getSettlementStatus, settlementStatus)
                .orderByDesc(HisInpatientSettlementDO::getCreateTime));
    }

    /**
     * 分页查询结算单
     */
    default PageResult<HisInpatientSettlementDO> selectPage(HisInpatientSettlementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisInpatientSettlementDO>()
                .likeIfPresent(HisInpatientSettlementDO::getSettlementNo, reqVO.getSettlementNo())
                .likeIfPresent(HisInpatientSettlementDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisInpatientSettlementDO::getInpatientNo, reqVO.getInpatientNo())
                .eqIfPresent(HisInpatientSettlementDO::getAdmissionId, reqVO.getAdmissionId())
                .eqIfPresent(HisInpatientSettlementDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisInpatientSettlementDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisInpatientSettlementDO::getSettlementStatus, reqVO.getSettlementStatus())
                .eqIfPresent(HisInpatientSettlementDO::getSettlementType, reqVO.getSettlementType())
                .betweenIfPresent(HisInpatientSettlementDO::getSettlementTime, reqVO.getSettlementTimeStart(), reqVO.getSettlementTimeEnd())
                .betweenIfPresent(HisInpatientSettlementDO::getAdmissionDate, reqVO.getAdmissionDateStart(), reqVO.getAdmissionDateEnd())
                .betweenIfPresent(HisInpatientSettlementDO::getDischargeDate, reqVO.getDischargeDateStart(), reqVO.getDischargeDateEnd())
                .orderByDesc(HisInpatientSettlementDO::getCreateTime));
    }

}