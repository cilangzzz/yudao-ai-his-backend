package cn.iocoder.yudao.module.his.dal.mysql.lab;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisLabRequestPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisLabRequestDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 检验申请 Mapper
 *
 * @author yudao
 */
@Mapper
public interface HisLabRequestMapper extends BaseMapperX<HisLabRequestDO> {

    /**
     * 分页查询检验申请
     */
    default PageResult<HisLabRequestDO> selectPage(HisLabRequestPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisLabRequestDO>()
                .likeIfPresent(HisLabRequestDO::getRequestNo, reqVO.getRequestNo())
                .likeIfPresent(HisLabRequestDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisLabRequestDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisLabRequestDO::getSourceType, reqVO.getSourceType())
                .eqIfPresent(HisLabRequestDO::getSourceId, reqVO.getSourceId())
                .eqIfPresent(HisLabRequestDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisLabRequestDO::getDoctorId, reqVO.getDoctorId())
                .eqIfPresent(HisLabRequestDO::getLabType, reqVO.getLabType())
                .likeIfPresent(HisLabRequestDO::getLabCategory, reqVO.getLabCategory())
                .eqIfPresent(HisLabRequestDO::getSpecimenType, reqVO.getSpecimenType())
                .eqIfPresent(HisLabRequestDO::getRequestStatus, reqVO.getRequestStatus())
                .eqIfPresent(HisLabRequestDO::getSpecimenStatus, reqVO.getSpecimenStatus())
                .eqIfPresent(HisLabRequestDO::getUrgency, reqVO.getUrgency())
                .eqIfPresent(HisLabRequestDO::getCriticalFlag, reqVO.getCriticalFlag())
                .eqIfPresent(HisLabRequestDO::getPayStatus, reqVO.getPayStatus())
                .betweenIfPresent(HisLabRequestDO::getRequestTime, reqVO.getRequestTime())
                .betweenIfPresent(HisLabRequestDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisLabRequestDO::getId));
    }

    /**
     * 根据申请单号查询
     */
    default HisLabRequestDO selectByRequestNo(String requestNo) {
        return selectOne(HisLabRequestDO::getRequestNo, requestNo);
    }

    /**
     * 根据患者ID查询申请列表
     */
    default List<HisLabRequestDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getPatientId, patientId)
                .orderByDesc(HisLabRequestDO::getRequestTime));
    }

    /**
     * 根据关联ID查询申请列表（门诊挂号或住院）
     */
    default List<HisLabRequestDO> selectListBySourceId(Long sourceId) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getSourceId, sourceId)
                .orderByDesc(HisLabRequestDO::getRequestTime));
    }

    /**
     * 根据申请状态查询申请列表
     */
    default List<HisLabRequestDO> selectListByStatus(Integer requestStatus) {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getRequestStatus, requestStatus)
                .orderByDesc(HisLabRequestDO::getRequestTime));
    }

    /**
     * 根据标本条码查询
     */
    default HisLabRequestDO selectBySpecimenBarcode(String specimenBarcode) {
        return selectOne(HisLabRequestDO::getSpecimenBarcode, specimenBarcode);
    }

    /**
     * 查询紧急申请列表
     */
    default List<HisLabRequestDO> selectUrgentList() {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .in(HisLabRequestDO::getUrgency, 1, 2) // 急诊或加急
                .notIn(HisLabRequestDO::getRequestStatus, 8, 9) // 排除已取消和已退费
                .orderByDesc(HisLabRequestDO::getUrgency)
                .orderByDesc(HisLabRequestDO::getRequestTime));
    }

    /**
     * 查询有危急值的申请列表
     */
    default List<HisLabRequestDO> selectCriticalValueList() {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getCriticalFlag, 1)
                .isNull(HisLabRequestDO::getCriticalConfirmTime) // 未确认的危急值
                .orderByDesc(HisLabRequestDO::getRequestTime));
    }

    /**
     * 查询待采集的申请列表
     */
    default List<HisLabRequestDO> selectPendingCollectList() {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getRequestStatus, 1) // 已申请待采集
                .orderByAsc(HisLabRequestDO::getUrgency) // 紧急优先
                .orderByAsc(HisLabRequestDO::getRequestTime));
    }

    /**
     * 查询待接收的申请列表（已采集但未接收）
     */
    default List<HisLabRequestDO> selectPendingReceiveList() {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getSpecimenStatus, 2) // 已采集
                .isNull(HisLabRequestDO::getReceiveTime) // 未接收
                .orderByAsc(HisLabRequestDO::getUrgency)
                .orderByAsc(HisLabRequestDO::getCollectTime));
    }

    /**
     * 查询检验中的申请列表
     */
    default List<HisLabRequestDO> selectInProgressList() {
        return selectList(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getRequestStatus, 4) // 检验中
                .orderByAsc(HisLabRequestDO::getUrgency)
                .orderByAsc(HisLabRequestDO::getStartTime));
    }

    /**
     * 统计某科室某日期的申请数量
     */
    default Long selectCountByDeptAndDate(Long deptId, LocalDate requestDate) {
        LocalDateTime startTime = LocalDateTime.of(requestDate, LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(requestDate, LocalDateTime.MAX.toLocalTime());
        return selectCount(new LambdaQueryWrapperX<HisLabRequestDO>()
                .eq(HisLabRequestDO::getDeptId, deptId)
                .between(HisLabRequestDO::getRequestTime, startTime, endTime));
    }

    /**
     * 统计某状态的申请数量
     */
    default Long selectCountByStatus(Integer requestStatus) {
        return selectCount(HisLabRequestDO::getRequestStatus, requestStatus);
    }

}