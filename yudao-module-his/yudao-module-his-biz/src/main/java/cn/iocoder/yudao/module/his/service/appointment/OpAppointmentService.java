package cn.iocoder.yudao.module.his.service.appointment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.appointment.vo.OpAppointmentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.appointment.OpAppointmentDO;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 预约挂号 Service 接口
 */
public interface OpAppointmentService {

    /**
     * 创建预约
     *
     * @param createReqVO 预约信息
     * @return 预约ID
     */
    Long createAppointment(@Valid OpAppointmentSaveReqVO createReqVO);

    /**
     * 取消预约
     *
     * @param id 预约ID
     * @param reason 取消原因
     */
    void cancelAppointment(Long id, String reason);

    /**
     * 取号（将预约转为挂号）
     *
     * @param id 预约ID
     * @return 挂号ID
     */
    Long takeAppointment(Long id);

    /**
     * 获取预约
     *
     * @param id 预约ID
     * @return 预约记录
     */
    OpAppointmentDO getAppointment(Long id);

    /**
     * 根据预约编号获取预约
     *
     * @param appointmentNo 预约编号
     * @return 预约记录
     */
    OpAppointmentDO getAppointmentByAppointmentNo(String appointmentNo);

    /**
     * 获取预约分页
     *
     * @param pageReqVO 分页查询条件
     * @return 预约分页结果
     */
    PageResult<OpAppointmentDO> getAppointmentPage(OpAppointmentPageReqVO pageReqVO);

    /**
     * 获取患者的预约列表
     *
     * @param patientId 患者ID
     * @return 预约列表
     */
    List<OpAppointmentDO> getAppointmentListByPatientId(Long patientId);

    /**
     * 获取医生某日期的预约列表
     *
     * @param doctorId 医生ID
     * @param date 日期
     * @return 预约列表
     */
    List<OpAppointmentDO> getAppointmentListByDoctorIdAndDate(Long doctorId, LocalDate date);

    /**
     * 获取科室某日期的预约列表
     *
     * @param deptId 科室ID
     * @param date 日期
     * @return 预约列表
     */
    List<OpAppointmentDO> getAppointmentListByDeptIdAndDate(Long deptId, LocalDate date);

    /**
     * 统计某排班某日期的预约数量
     *
     * @param scheduleId 排班ID
     * @param date 日期
     * @return 预约数量
     */
    Long getAppointmentCountByScheduleIdAndDate(Long scheduleId, LocalDate date);

    /**
     * 校验预约是否存在
     *
     * @param id 预约ID
     * @return 预约记录
     */
    OpAppointmentDO validateAppointmentExists(Long id);

    /**
     * 处理过期预约
     * 将超过预约日期且未取号的预约标记为过期
     */
    void processExpiredAppointments();

}
