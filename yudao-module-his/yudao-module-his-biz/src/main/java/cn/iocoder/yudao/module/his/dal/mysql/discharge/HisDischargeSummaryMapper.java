package cn.iocoder.yudao.module.his.dal.mysql.discharge;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.discharge.HisDischargeSummaryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出院小结 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisDischargeSummaryMapper extends BaseMapperX<HisDischargeSummaryDO> {

    /**
     * 根据出院ID查询出院小结
     *
     * @param dischargeId 出院ID
     * @return 出院小结
     */
    default HisDischargeSummaryDO selectByDischargeId(Long dischargeId) {
        return selectOne(HisDischargeSummaryDO::getDischargeId, dischargeId);
    }

    /**
     * 根据住院ID查询出院小结
     *
     * @param admissionId 住院ID
     * @return 出院小结
     */
    default HisDischargeSummaryDO selectByAdmissionId(Long admissionId) {
        return selectOne(HisDischargeSummaryDO::getAdmissionId, admissionId);
    }

    /**
     * 根据患者ID查询出院小结列表
     *
     * @param patientId 患者ID
     * @return 出院小结列表
     */
    default List<HisDischargeSummaryDO> selectListByPatientId(Long patientId) {
        return selectList(HisDischargeSummaryDO::getPatientId, patientId);
    }

    /**
     * 根据状态查询出院小结列表
     *
     * @param summaryStatus 状态
     * @return 出院小结列表
     */
    default List<HisDischargeSummaryDO> selectListByStatus(Integer summaryStatus) {
        return selectList(HisDischargeSummaryDO::getSummaryStatus, summaryStatus);
    }

    /**
     * 根据书写医生查询出院小结列表
     *
     * @param authorDoctor 书写医生ID
     * @return 出院小结列表
     */
    default List<HisDischargeSummaryDO> selectListByAuthorDoctor(Long authorDoctor) {
        return selectList(HisDischargeSummaryDO::getAuthorDoctor, authorDoctor);
    }

    /**
     * 根据审核医生查询出院小结列表
     *
     * @param reviewDoctor 审核医生ID
     * @return 出院小结列表
     */
    default List<HisDischargeSummaryDO> selectListByReviewDoctor(Long reviewDoctor) {
        return selectList(new LambdaQueryWrapperX<HisDischargeSummaryDO>()
                .eq(HisDischargeSummaryDO::getReviewDoctor, reviewDoctor));
    }
}