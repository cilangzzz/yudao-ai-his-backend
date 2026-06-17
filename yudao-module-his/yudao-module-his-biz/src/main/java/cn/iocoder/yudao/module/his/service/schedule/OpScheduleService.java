package cn.iocoder.yudao.module.his.service.schedule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpSchedulePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.schedule.vo.OpScheduleSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.schedule.OpScheduleDO;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 医生排班 Service 接口
 */
public interface OpScheduleService {

    /**
     * 创建排班
     *
     * @param createReqVO 创建信息
     * @return 排班编号
     */
    Long createSchedule(@Valid OpScheduleSaveReqVO createReqVO);

    /**
     * 更新排班
     *
     * @param updateReqVO 更新信息
     */
    void updateSchedule(@Valid OpScheduleSaveReqVO updateReqVO);

    /**
     * 删除排班
     *
     * @param id 编号
     */
    void deleteSchedule(Long id);

    /**
     * 获取排班
     *
     * @param id 编号
     * @return 排班
     */
    OpScheduleDO getSchedule(Long id);

    /**
     * 获取排班分页
     *
     * @param pageReqVO 分页查询
     * @return 排班分页
     */
    PageResult<OpScheduleDO> getSchedulePage(OpSchedulePageReqVO pageReqVO);

    /**
     * 查询医生某天某时段的排班
     *
     * @param doctorId 医生ID
     * @param scheduleDate 排班日期
     * @param timePeriod 时段
     * @return 排班记录
     */
    OpScheduleDO getScheduleByDoctorAndDateAndPeriod(Long doctorId, LocalDate scheduleDate, Integer timePeriod);

    /**
     * 查询某日某科室可用排班（状态正常且有剩余号源）
     *
     * @param scheduleDate 排班日期
     * @param deptId 科室ID（可选）
     * @return 可用排班列表
     */
    List<OpScheduleDO> getAvailableSchedule(LocalDate scheduleDate, Long deptId);

    /**
     * 查询某日排班列表
     *
     * @param scheduleDate 排班日期
     * @return 排班列表
     */
    List<OpScheduleDO> getScheduleByDate(LocalDate scheduleDate);

    /**
     * 增加已用号源数（挂号时调用）
     *
     * @param id 排班ID
     * @return 是否成功
     */
    boolean incrementUsedQuota(Long id);

    /**
     * 减少已用号源数（退号时调用）
     *
     * @param id 排班ID
     * @return 是否成功
     */
    boolean decrementUsedQuota(Long id);

    /**
     * 更新排班状态
     *
     * @param id 排班ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 批量创建排班（排班批量导入）
     *
     * @param createReqVOs 创建信息列表
     */
    void batchCreateSchedule(@Valid List<OpScheduleSaveReqVO> createReqVOs);

    /**
     * 校验排班是否存在
     *
     * @param id 编号
     * @return 排班
     */
    OpScheduleDO validateScheduleExists(Long id);

}
