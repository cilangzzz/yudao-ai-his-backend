package cn.iocoder.yudao.module.his.service.patient;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import javax.validation.Valid;

/**
 * 患者 Service 接口
 */
public interface HisPatientService {

    /**
     * 创建患者
     *
     * @param createReqVO 创建信息
     * @return 患者编号
     */
    Long createPatient(@Valid HisPatientSaveReqVO createReqVO);

    /**
     * 更新患者
     *
     * @param updateReqVO 更新信息
     */
    void updatePatient(@Valid HisPatientSaveReqVO updateReqVO);

    /**
     * 删除患者
     *
     * @param id 编号
     */
    void deletePatient(Long id);

    /**
     * 获取患者
     *
     * @param id 编号
     * @return 患者
     */
    HisPatientDO getPatient(Long id);

    /**
     * 获取患者分页
     *
     * @param pageReqVO 分页查询
     * @return 患者分页
     */
    PageResult<HisPatientDO> getPatientPage(HisPatientPageReqVO pageReqVO);

    /**
     * 根据身份证号获取患者
     *
     * @param idCardNo 身份证号
     * @return 患者
     */
    HisPatientDO getPatientByIdCardNo(String idCardNo);

    /**
     * 根据患者编号获取患者
     *
     * @param patientNo 患者编号
     * @return 患者
     */
    HisPatientDO getPatientByPatientNo(String patientNo);

    /**
     * 校验患者是否存在
     *
     * @param id 编号
     * @return 患者
     */
    HisPatientDO validatePatientExists(Long id);

}