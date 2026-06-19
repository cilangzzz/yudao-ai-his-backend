package cn.iocoder.yudao.module.his.service.department;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.department.vo.HisDepartmentSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.department.HisDepartmentDO;
import cn.iocoder.yudao.module.his.dal.mysql.department.HisDepartmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 科室 Service 实现类
 */
@Service
@Validated
public class HisDepartmentServiceImpl implements HisDepartmentService {

    @Resource
    private HisDepartmentMapper departmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDepartment(HisDepartmentSaveReqVO createReqVO) {
        // 1. 校验科室编码唯一性
        HisDepartmentDO existDept = departmentMapper.selectByDeptCode(createReqVO.getDeptCode());
        if (existDept != null) {
            throw exception(DEPARTMENT_CODE_DUPLICATE);
        }

        // 2. 插入科室
        HisDepartmentDO department = BeanUtils.toBean(createReqVO, HisDepartmentDO.class);
        // 设置默认状态
        if (department.getStatus() == null) {
            department.setStatus(1);
        }
        // 设置默认父ID
        if (department.getParentId() == null) {
            department.setParentId(0L);
        }
        departmentMapper.insert(department);

        return department.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDepartment(HisDepartmentSaveReqVO updateReqVO) {
        // 1. 校验存在
        validateDepartmentExists(updateReqVO.getId());

        // 2. 校验科室编码唯一性
        HisDepartmentDO existDept = departmentMapper.selectByDeptCode(updateReqVO.getDeptCode());
        if (existDept != null && !existDept.getId().equals(updateReqVO.getId())) {
            throw exception(DEPARTMENT_CODE_DUPLICATE);
        }

        // 3. 更新科室
        HisDepartmentDO updateObj = BeanUtils.toBean(updateReqVO, HisDepartmentDO.class);
        departmentMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartment(Long id) {
        // 1. 校验存在
        validateDepartmentExists(id);

        // 2. 校验是否有子科室
        List<HisDepartmentDO> children = departmentMapper.selectByParentId(id);
        if (!children.isEmpty()) {
            throw exception(DEPARTMENT_HAS_CHILDREN);
        }

        // 3. 删除
        departmentMapper.deleteById(id);
    }

    @Override
    public HisDepartmentDO getDepartment(Long id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public PageResult<HisDepartmentDO> getDepartmentPage(HisDepartmentPageReqVO pageReqVO) {
        return departmentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisDepartmentDO> getDepartmentList(Integer deptType, Integer status) {
        return departmentMapper.selectList(deptType, status);
    }

    @Override
    public HisDepartmentDO getDepartmentByDeptCode(String deptCode) {
        return departmentMapper.selectByDeptCode(deptCode);
    }

    @Override
    public HisDepartmentDO validateDepartmentExists(Long id) {
        if (id == null) {
            return null;
        }
        HisDepartmentDO department = departmentMapper.selectById(id);
        if (department == null) {
            throw exception(DEPARTMENT_NOT_EXISTS);
        }
        return department;
    }

}