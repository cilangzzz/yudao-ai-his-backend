package cn.iocoder.yudao.module.his.service.vital;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.vital.vo.HisVitalSignSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.vital.HisVitalSignDO;

import java.util.List;

/**
 * 生命体征 Service 接口
 */
public interface HisVitalSignService {

    /**
     * 创建生命体征记录
     */
    Long createVitalSign(HisVitalSignSaveReqVO createReqVO);

    /**
     * 更新生命体征记录
     */
    void updateVitalSign(HisVitalSignSaveReqVO updateReqVO);

    /**
     * 删除生命体征记录
     */
    void deleteVitalSign(Long id);

    /**
     * 获取生命体征记录
     */
    HisVitalSignDO getVitalSign(Long id);

    /**
     * 分页查询生命体征
     */
    PageResult<HisVitalSignDO> getVitalSignPage(HisVitalSignPageReqVO pageReqVO);

    /**
     * 按住院ID查询生命体征列表
     */
    List<HisVitalSignDO> getVitalSignListByAdmission(Long admissionId);

    /**
     * 按患者ID查询生命体征列表
     */
    List<HisVitalSignDO> getVitalSignListByPatient(Long patientId);

    /**
     * 获取最近一次生命体征
     */
    HisVitalSignDO getLatestVitalSign(Long admissionId);

    /**
     * 查询异常生命体征
     */
    List<HisVitalSignDO> getAbnormalVitalSignList(Long admissionId);

    /**
     * 校验生命体征是否存在
     */
    HisVitalSignDO validateVitalSignExists(Long id);
}