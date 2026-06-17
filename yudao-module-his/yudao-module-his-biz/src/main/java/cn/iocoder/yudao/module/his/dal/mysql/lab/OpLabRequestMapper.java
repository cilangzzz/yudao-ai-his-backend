package cn.iocoder.yudao.module.his.dal.mysql.lab;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.OpLabRequestDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 门诊检验申请 Mapper
 *
 * @author yudao
 */
@Mapper
public interface OpLabRequestMapper extends BaseMapperX<OpLabRequestDO> {

    /**
     * 分页查询门诊检验申请
     *
     * @param requestNo 申请单号
     * @param patientId 患者ID
     * @param patientName 患者姓名（模糊）
     * @param requestType 申请类型
     * @param registerId 挂号ID
     * @param admissionId 住院ID
     * @param deptId 申请科室ID
     * @param doctorId 申请医生ID
     * @param labType 检验类型
     * @param specimenType 标本类型
     * @param requestStatus 申请状态
     * @param feeStatus 收费状态
     * @param urgency 紧急标志
     * @param hasCriticalValue 危急值标志
     * @param requestDate 申请日期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 分页结果
     */
    default PageResult<OpLabRequestDO> selectPage(String requestNo, Long patientId, String patientName,
                                                   Integer requestType, Long registerId, Long admissionId,
                                                   Long deptId, Long doctorId, Integer labType, Integer specimenType,
                                                   Integer requestStatus, Integer feeStatus, Integer urgency,
                                                   Integer hasCriticalValue, LocalDate requestDate,
                                                   LocalDateTime startTime, LocalDateTime endTime) {
        return selectPage(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eqIfPresent(OpLabRequestDO::getRequestNo, requestNo)
                .eqIfPresent(OpLabRequestDO::getPatientId, patientId)
                .likeIfPresent(OpLabRequestDO::getPatientName, patientName)
                .eqIfPresent(OpLabRequestDO::getRequestType, requestType)
                .eqIfPresent(OpLabRequestDO::getRegisterId, registerId)
                .eqIfPresent(OpLabRequestDO::getAdmissionId, admissionId)
                .eqIfPresent(OpLabRequestDO::getDeptId, deptId)
                .eqIfPresent(OpLabRequestDO::getDoctorId, doctorId)
                .eqIfPresent(OpLabRequestDO::getLabType, labType)
                .eqIfPresent(OpLabRequestDO::getSpecimenType, specimenType)
                .eqIfPresent(OpLabRequestDO::getRequestStatus, requestStatus)
                .eqIfPresent(OpLabRequestDO::getFeeStatus, feeStatus)
                .eqIfPresent(OpLabRequestDO::getUrgency, urgency)
                .eqIfPresent(OpLabRequestDO::getHasCriticalValue, hasCriticalValue)
                .betweenIfPresent(OpLabRequestDO::getCreateTime, startTime, endTime)
                .orderByDesc(OpLabRequestDO::getId));
    }

    /**
     * 根据申请单号查询
     *
     * @param requestNo 申请单号
     * @return 门诊检验申请
     */
    default OpLabRequestDO selectByRequestNo(String requestNo) {
        return selectOne(OpLabRequestDO::getRequestNo, requestNo);
    }

    /**
     * 根据患者ID查询申请列表
     *
     * @param patientId 患者ID
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getPatientId, patientId)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据挂号ID查询申请列表（门诊）
     *
     * @param registerId 挂号ID
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByRegisterId(Long registerId) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRegisterId, registerId)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据住院ID查询申请列表（住院）
     *
     * @param admissionId 住院ID
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByAdmissionId(Long admissionId) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getAdmissionId, admissionId)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据申请类型和关联ID查询申请列表
     *
     * @param requestType 申请类型
     * @param sourceId 关联ID（挂号ID或住院ID）
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListBySourceType(Integer requestType, Long sourceId) {
        LambdaQueryWrapperX<OpLabRequestDO> wrapper = new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRequestType, requestType);
        if (requestType != null && requestType == 1) {
            wrapper.eq(OpLabRequestDO::getRegisterId, sourceId);
        } else if (requestType != null && requestType == 2) {
            wrapper.eq(OpLabRequestDO::getAdmissionId, sourceId);
        }
        return selectList(wrapper.orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据申请科室ID查询申请列表
     *
     * @param deptId 科室ID
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByDeptId(Long deptId) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getDeptId, deptId)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据申请医生ID查询申请列表
     *
     * @param doctorId 医生ID
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByDoctorId(Long doctorId) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getDoctorId, doctorId)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据申请状态查询申请列表
     *
     * @param requestStatus 申请状态
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByStatus(Integer requestStatus) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRequestStatus, requestStatus)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据收费状态查询申请列表
     *
     * @param feeStatus 收费状态
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByFeeStatus(Integer feeStatus) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getFeeStatus, feeStatus)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据标本条码查询
     *
     * @param specimenBarcode 标本条码
     * @return 门诊检验申请
     */
    default OpLabRequestDO selectBySpecimenBarcode(String specimenBarcode) {
        return selectOne(OpLabRequestDO::getSpecimenBarcode, specimenBarcode);
    }

    /**
     * 根据标本编号查询
     *
     * @param specimenNo 标本编号
     * @return 门诊检验申请
     */
    default OpLabRequestDO selectBySpecimenNo(String specimenNo) {
        return selectOne(OpLabRequestDO::getSpecimenNo, specimenNo);
    }

    /**
     * 查询紧急申请列表
     *
     * @return 紧急申请列表
     */
    default List<OpLabRequestDO> selectUrgentList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .in(OpLabRequestDO::getUrgency, 1, 2) // 急、特急
                .ne(OpLabRequestDO::getRequestStatus, 11) // 排除已取消
                .orderByDesc(OpLabRequestDO::getUrgency)
                .orderByAsc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 查询有危急值的申请列表
     *
     * @return 有危急值的申请列表
     */
    default List<OpLabRequestDO> selectCriticalValueList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getHasCriticalValue, 1)
                .isNull(OpLabRequestDO::getCriticalConfirmTime) // 未确认的危急值
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 查询待采集的申请列表
     *
     * @return 待采集申请列表
     */
    default List<OpLabRequestDO> selectPendingCollectList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRequestStatus, 3) // 待采集
                .orderByDesc(OpLabRequestDO::getUrgency) // 紧急优先
                .orderByAsc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 查询待接收的申请列表（已采集但未接收）
     *
     * @return 待接收申请列表
     */
    default List<OpLabRequestDO> selectPendingReceiveList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRequestStatus, 5) // 已运送
                .isNull(OpLabRequestDO::getReceiveTime) // 未接收
                .orderByDesc(OpLabRequestDO::getUrgency)
                .orderByAsc(OpLabRequestDO::getTransportTime));
    }

    /**
     * 查询检验中的申请列表
     *
     * @return 检验中申请列表
     */
    default List<OpLabRequestDO> selectInProgressList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRequestStatus, 7) // 检验中
                .orderByDesc(OpLabRequestDO::getUrgency)
                .orderByAsc(OpLabRequestDO::getLabStartTime));
    }

    /**
     * 查询已完成待审核的申请列表
     *
     * @return 待审核申请列表
     */
    default List<OpLabRequestDO> selectPendingAuditList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRequestStatus, 8) // 已完成
                .orderByDesc(OpLabRequestDO::getUrgency)
                .orderByAsc(OpLabRequestDO::getLabEndTime));
    }

    /**
     * 查询已审核待发布的申请列表
     *
     * @return 待发布申请列表
     */
    default List<OpLabRequestDO> selectPendingPublishList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getRequestStatus, 9) // 已审核
                .orderByDesc(OpLabRequestDO::getUrgency)
                .orderByAsc(OpLabRequestDO::getAuditTime));
    }

    /**
     * 查询某日期范围内的申请列表
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startTime = LocalDateTime.of(startDate, LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(endDate, LocalDateTime.MAX.toLocalTime());
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .between(OpLabRequestDO::getCreateTime, startTime, endTime)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 统计某科室某日期的申请数量
     *
     * @param deptId 科室ID
     * @param requestDate 申请日期
     * @return 申请数量
     */
    default Long selectCountByDeptAndDate(Long deptId, LocalDate requestDate) {
        LocalDateTime startTime = LocalDateTime.of(requestDate, LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(requestDate, LocalDateTime.MAX.toLocalTime());
        return selectCount(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getDeptId, deptId)
                .between(OpLabRequestDO::getCreateTime, startTime, endTime));
    }

    /**
     * 统计某医生某日期的申请数量
     *
     * @param doctorId 医生ID
     * @param requestDate 申请日期
     * @return 申请数量
     */
    default Long selectCountByDoctorAndDate(Long doctorId, LocalDate requestDate) {
        LocalDateTime startTime = LocalDateTime.of(requestDate, LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(requestDate, LocalDateTime.MAX.toLocalTime());
        return selectCount(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getDoctorId, doctorId)
                .between(OpLabRequestDO::getCreateTime, startTime, endTime));
    }

    /**
     * 统计某状态的申请数量
     *
     * @param requestStatus 申请状态
     * @return 申请数量
     */
    default Long selectCountByStatus(Integer requestStatus) {
        return selectCount(OpLabRequestDO::getRequestStatus, requestStatus);
    }

    /**
     * 统计某患者某日期的申请数量
     *
     * @param patientId 患者ID
     * @param requestDate 申请日期
     * @return 申请数量
     */
    default Long selectCountByPatientAndDate(Long patientId, LocalDate requestDate) {
        LocalDateTime startTime = LocalDateTime.of(requestDate, LocalDateTime.MIN.toLocalTime());
        LocalDateTime endTime = LocalDateTime.of(requestDate, LocalDateTime.MAX.toLocalTime());
        return selectCount(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getPatientId, patientId)
                .between(OpLabRequestDO::getCreateTime, startTime, endTime));
    }

    /**
     * 根据检验类型查询申请列表
     *
     * @param labType 检验类型
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListByLabType(Integer labType) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getLabType, labType)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 根据标本类型查询申请列表
     *
     * @param specimenType 标本类型
     * @return 申请列表
     */
    default List<OpLabRequestDO> selectListBySpecimenType(Integer specimenType) {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getSpecimenType, specimenType)
                .orderByDesc(OpLabRequestDO::getCreateTime));
    }

    /**
     * 查询待收费的申请列表
     *
     * @return 待收费申请列表
     */
    default List<OpLabRequestDO> selectPendingFeeList() {
        return selectList(new LambdaQueryWrapperX<OpLabRequestDO>()
                .eq(OpLabRequestDO::getFeeStatus, 0) // 未收费
                .eq(OpLabRequestDO::getRequestStatus, 1) // 已开立
                .orderByDesc(OpLabRequestDO::getUrgency)
                .orderByAsc(OpLabRequestDO::getCreateTime));
    }

}