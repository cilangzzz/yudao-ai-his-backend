package cn.iocoder.yudao.module.his.service.admission;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.DischargeApplyReqVO;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.HisAdmissionPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.admission.vo.HisAdmissionSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.admission.HisAdmissionDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 入院记录 Service 接口
 *
 * @author yudao
 */
public interface HisAdmissionService {

    /**
     * 创建入院记录（入院登记）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAdmission(@Valid HisAdmissionSaveReqVO createReqVO);

    /**
     * 更新入院记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAdmission(@Valid HisAdmissionSaveReqVO updateReqVO);

    /**
     * 删除入院记录
     *
     * @param id 编号
     */
    void deleteAdmission(Long id);

    /**
     * 获得入院记录
     *
     * @param id 编号
     * @return 入院记录
     */
    HisAdmissionDO getAdmission(Long id);

    /**
     * 根据住院号获得入院记录
     *
     * @param admissionNo 住院号
     * @return 入院记录
     */
    HisAdmissionDO getAdmissionByNo(String admissionNo);

    /**
     * 获得入院记录分页
     *
     * @param pageReqVO 分页查询
     * @return 入院记录分页
     */
    PageResult<HisAdmissionDO> getAdmissionPage(HisAdmissionPageReqVO pageReqVO);

    /**
     * 获得患者的历史入院记录
     *
     * @param patientId 患者ID
     * @return 入院记录列表
     */
    List<HisAdmissionDO> getAdmissionListByPatientId(Long patientId);

    /**
     * 获得在院患者列表
     *
     * @return 在院患者列表
     */
    List<HisAdmissionDO> getInHospitalList();

    /**
     * 获得科室在院患者列表
     *
     * @param deptId 科室ID
     * @return 在院患者列表
     */
    List<HisAdmissionDO> getInHospitalListByDept(Long deptId);

    /**
     * 获得病区在院患者列表
     *
     * @param wardId 病区ID
     * @return 在院患者列表
     */
    List<HisAdmissionDO> getInHospitalListByWard(Long wardId);

    /**
     * 申请出院
     *
     * @param reqVO 出院申请信息
     */
    void applyDischarge(@Valid DischargeApplyReqVO reqVO);

    /**
     * 确认出院
     *
     * @param id 入院记录ID
     */
    void confirmDischarge(Long id);

    /**
     * 转科
     *
     * @param id 入院记录ID
     * @param newDeptId 新科室ID
     * @param newWardId 新病区ID
     * @param newBedId 新床位ID
     */
    void transferDept(Long id, Long newDeptId, Long newWardId, Long newBedId);

    /**
     * 更新床位
     *
     * @param id 入院记录ID
     * @param newBedId 新床位ID
     */
    void updateBed(Long id, Long newBedId);

    /**
     * 紧急入院
     *
     * @param patientId 患者ID
     * @param deptId 科室ID
     * @return 入院记录ID
     */
    Long emergencyAdmission(Long patientId, Long deptId);

    /**
     * 增加预交金
     *
     * @param id 入院记录ID
     * @param amount 金额
     */
    void addDeposit(Long id, BigDecimal amount);

    /**
     * 更新费用
     *
     * @param id 入院记录ID
     * @param fee 金额
     */
    void updateFee(Long id, BigDecimal fee);

    /**
     * 校验入院记录是否存在
     *
     * @param id 编号
     * @return 入院记录
     */
    HisAdmissionDO validateAdmissionExists(Long id);

    /**
     * 校验入院记录是否在院
     *
     * @param id 编号
     * @return 入院记录
     */
    HisAdmissionDO validateAdmissionInHospital(Long id);

    /**
     * 获取今日入院数量
     *
     * @return 数量
     */
    int countTodayAdmission();

    /**
     * 获取今日出院数量
     *
     * @return 数量
     */
    int countTodayDischarge();

    /**
     * 获取当前在院患者数量
     *
     * @return 数量
     */
    int countInHospital();
}