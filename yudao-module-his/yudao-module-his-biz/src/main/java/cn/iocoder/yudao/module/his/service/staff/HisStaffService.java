package cn.iocoder.yudao.module.his.service.staff;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.staff.HisStaffDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 医护人员 Service 接口
 */
public interface HisStaffService {

    /**
     * 创建医护人员
     *
     * @param createReqVO 创建信息
     * @return 人员编号
     */
    Long createStaff(@Valid HisStaffSaveReqVO createReqVO);

    /**
     * 更新医护人员
     *
     * @param updateReqVO 更新信息
     */
    void updateStaff(@Valid HisStaffSaveReqVO updateReqVO);

    /**
     * 删除医护人员
     *
     * @param id 编号
     */
    void deleteStaff(Long id);

    /**
     * 获取医护人员
     *
     * @param id 编号
     * @return 医护人员
     */
    HisStaffDO getStaff(Long id);

    /**
     * 获取医护人员分页
     *
     * @param pageReqVO 分页查询
     * @return 医护人员分页
     */
    PageResult<HisStaffDO> getStaffPage(HisStaffPageReqVO pageReqVO);

    /**
     * 获取医护人员列表
     *
     * @param type 人员类型
     * @param deptId 科室ID
     * @param status 状态
     * @return 医护人员列表
     */
    List<HisStaffDO> getStaffList(Integer type, Long deptId, Integer status);

    /**
     * 根据人员编码获取医护人员
     *
     * @param staffCode 人员编码
     * @return 医护人员
     */
    HisStaffDO getStaffByStaffCode(String staffCode);

    /**
     * 校验医护人员是否存在
     *
     * @param id 编号
     * @return 医护人员
     */
    HisStaffDO validateStaffExists(Long id);

    /**
     * 根据科室ID获取医生列表
     *
     * @param deptId 科室ID
     * @return 医生列表
     */
    List<HisStaffDO> getDoctorListByDeptId(Long deptId);

    /**
     * 根据科室ID获取护士列表
     *
     * @param deptId 科室ID
     * @return 护士列表
     */
    List<HisStaffDO> getNurseListByDeptId(Long deptId);

}