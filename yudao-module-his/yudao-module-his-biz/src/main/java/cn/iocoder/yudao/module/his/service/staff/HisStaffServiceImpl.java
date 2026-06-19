package cn.iocoder.yudao.module.his.service.staff;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.staff.vo.HisStaffSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.staff.HisStaffDO;
import cn.iocoder.yudao.module.his.dal.mysql.staff.HisStaffMapper;
import cn.iocoder.yudao.module.his.service.department.HisDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.*;

/**
 * 医护人员 Service 实现类
 */
@Service
@Validated
public class HisStaffServiceImpl implements HisStaffService {

    @Resource
    private HisStaffMapper staffMapper;

    @Resource
    private HisDepartmentService departmentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createStaff(HisStaffSaveReqVO createReqVO) {
        // 1. 校验人员编码唯一性
        HisStaffDO existStaff = staffMapper.selectByStaffCode(createReqVO.getStaffCode());
        if (existStaff != null) {
            throw exception(STAFF_CODE_DUPLICATE);
        }

        // 2. 校验科室是否存在
        if (createReqVO.getDeptId() != null) {
            departmentService.validateDepartmentExists(createReqVO.getDeptId());
        }

        // 3. 插入人员
        HisStaffDO staff = BeanUtils.toBean(createReqVO, HisStaffDO.class);
        // 设置默认状态
        if (staff.getStatus() == null) {
            staff.setStatus(1);
        }
        // 根据类型设置标识
        setStaffFlags(staff, createReqVO.getType());
        // 设置出生日期
        if (createReqVO.getBirthDate() != null && !createReqVO.getBirthDate().isEmpty()) {
            staff.setBirthDate(LocalDate.parse(createReqVO.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        staffMapper.insert(staff);

        return staff.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStaff(HisStaffSaveReqVO updateReqVO) {
        // 1. 校验存在
        validateStaffExists(updateReqVO.getId());

        // 2. 校验人员编码唯一性
        HisStaffDO existStaff = staffMapper.selectByStaffCode(updateReqVO.getStaffCode());
        if (existStaff != null && !existStaff.getId().equals(updateReqVO.getId())) {
            throw exception(STAFF_CODE_DUPLICATE);
        }

        // 3. 校验科室是否存在
        if (updateReqVO.getDeptId() != null) {
            departmentService.validateDepartmentExists(updateReqVO.getDeptId());
        }

        // 4. 更新人员
        HisStaffDO updateObj = BeanUtils.toBean(updateReqVO, HisStaffDO.class);
        // 根据类型设置标识
        setStaffFlags(updateObj, updateReqVO.getType());
        // 设置出生日期
        if (updateReqVO.getBirthDate() != null && !updateReqVO.getBirthDate().isEmpty()) {
            updateObj.setBirthDate(LocalDate.parse(updateReqVO.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        staffMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStaff(Long id) {
        // 1. 校验存在
        validateStaffExists(id);

        // 2. 删除
        staffMapper.deleteById(id);
    }

    @Override
    public HisStaffDO getStaff(Long id) {
        return staffMapper.selectById(id);
    }

    @Override
    public PageResult<HisStaffDO> getStaffPage(HisStaffPageReqVO pageReqVO) {
        return staffMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisStaffDO> getStaffList(Integer type, Long deptId, Integer status) {
        return staffMapper.selectList(type, deptId, status);
    }

    @Override
    public HisStaffDO getStaffByStaffCode(String staffCode) {
        return staffMapper.selectByStaffCode(staffCode);
    }

    @Override
    public HisStaffDO validateStaffExists(Long id) {
        if (id == null) {
            return null;
        }
        HisStaffDO staff = staffMapper.selectById(id);
        if (staff == null) {
            throw exception(STAFF_NOT_EXISTS);
        }
        return staff;
    }

    @Override
    public List<HisStaffDO> getDoctorListByDeptId(Long deptId) {
        return staffMapper.selectDoctorListByDeptId(deptId);
    }

    @Override
    public List<HisStaffDO> getNurseListByDeptId(Long deptId) {
        return staffMapper.selectNurseListByDeptId(deptId);
    }

    /**
     * 根据人员类型设置标识
     */
    private void setStaffFlags(HisStaffDO staff, Integer type) {
        // 先清空所有标识
        staff.setDoctorFlag(0);
        staff.setNurseFlag(0);
        staff.setPharmacistFlag(0);
        staff.setLabTechFlag(0);
        staff.setRadTechFlag(0);

        if (type == null) {
            return;
        }

        switch (type) {
            case 1: // 医生
                staff.setDoctorFlag(1);
                break;
            case 2: // 护士
                staff.setNurseFlag(1);
                break;
            case 3: // 技师
                staff.setLabTechFlag(1);
                staff.setRadTechFlag(1);
                break;
            case 4: // 药剂师
                staff.setPharmacistFlag(1);
                break;
            default:
                break;
        }
    }

}