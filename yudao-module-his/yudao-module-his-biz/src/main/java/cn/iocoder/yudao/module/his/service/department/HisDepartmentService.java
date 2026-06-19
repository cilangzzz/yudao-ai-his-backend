package cn.iocoder.yudao.module.his.service.department;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.department.HisDepartmentDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 科室 Service 接口
 */
public interface HisDepartmentService {

    /**
     * 创建科室
     *
     * @param createReqVO 创建信息
     * @return 科室编号
     */
    Long createDepartment(@Valid HisDepartmentSaveReqVO createReqVO);

    /**
     * 更新科室
     *
     * @param updateReqVO 更新信息
     */
    void updateDepartment(@Valid HisDepartmentSaveReqVO updateReqVO);

    /**
     * 删除科室
     *
     * @param id 编号
     */
    void deleteDepartment(Long id);

    /**
     * 获取科室
     *
     * @param id 编号
     * @return 科室
     */
    HisDepartmentDO getDepartment(Long id);

    /**
     * 获取科室分页
     *
     * @param pageReqVO 分页查询
     * @return 科室分页
     */
    PageResult<HisDepartmentDO> getDepartmentPage(HisDepartmentPageReqVO pageReqVO);

    /**
     * 获取科室列表
     *
     * @param deptType 科室类型
     * @param status 状态
     * @return 科室列表
     */
    List<HisDepartmentDO> getDepartmentList(Integer deptType, Integer status);

    /**
     * 根据科室编码获取科室
     *
     * @param deptCode 科室编码
     * @return 科室
     */
    HisDepartmentDO getDepartmentByDeptCode(String deptCode);

    /**
     * 校验科室是否存在
     *
     * @param id 编号
     * @return 科室
     */
    HisDepartmentDO validateDepartmentExists(Long id);

}