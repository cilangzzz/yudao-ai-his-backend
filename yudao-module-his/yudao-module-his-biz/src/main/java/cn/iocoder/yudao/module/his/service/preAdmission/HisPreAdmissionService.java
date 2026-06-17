package cn.iocoder.yudao.module.his.service.preAdmission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo.HisPreAdmissionPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.preAdmission.vo.HisPreAdmissionSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.preAdmission.HisPreAdmissionDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 预住院 Service 接口
 *
 * @author yudao
 */
public interface HisPreAdmissionService {

    /**
     * 创建预住院记录
     */
    Long createPreAdmission(@Valid HisPreAdmissionSaveReqVO createReqVO);

    /**
     * 更新预住院记录
     */
    void updatePreAdmission(@Valid HisPreAdmissionSaveReqVO updateReqVO);

    /**
     * 取消预住院
     */
    void cancelPreAdmission(Long id, String reason);

    /**
     * 转为正式入院
     */
    Long convertToAdmission(Long id);

    /**
     * 删除预住院记录
     */
    void deletePreAdmission(Long id);

    /**
     * 获得预住院记录
     */
    HisPreAdmissionDO getPreAdmission(Long id);

    /**
     * 获得预住院分页
     */
    PageResult<HisPreAdmissionDO> getPreAdmissionPage(HisPreAdmissionPageReqVO pageReqVO);

    /**
     * 获得待入院列表
     */
    List<HisPreAdmissionDO> getPendingList();

    /**
     * 获得科室待入院列表
     */
    List<HisPreAdmissionDO> getPendingListByDept(Long deptId);

    /**
     * 校验预住院记录是否存在
     */
    HisPreAdmissionDO validatePreAdmissionExists(Long id);

    /**
     * 校验预住院记录是否待入院
     */
    HisPreAdmissionDO validatePreAdmissionPending(Long id);
}