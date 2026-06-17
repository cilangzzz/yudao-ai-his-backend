package cn.iocoder.yudao.module.his.service.register;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.register.vo.OpRegisterSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.register.OpRegisterDO;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 挂号 Service 接口
 */
public interface OpRegisterService {

    /**
     * 现场挂号
     *
     * @param createReqVO 挂号信息
     * @return 挂号ID
     */
    Long createRegister(@Valid OpRegisterSaveReqVO createReqVO);

    /**
     * 退号
     *
     * @param id 挂号ID
     * @param reason 退号原因
     */
    void refundRegister(Long id, String reason);

    /**
     * 获取挂号
     *
     * @param id 挂号ID
     * @return 挂号记录
     */
    OpRegisterDO getRegister(Long id);

    /**
     * 根据挂号编号获取挂号
     *
     * @param registerNo 挂号编号
     * @return 挂号记录
     */
    OpRegisterDO getRegisterByRegisterNo(String registerNo);

    /**
     * 获取挂号分页
     *
     * @param pageReqVO 分页查询条件
     * @return 挂号分页结果
     */
    PageResult<OpRegisterDO> getRegisterPage(OpRegisterPageReqVO pageReqVO);

    /**
     * 开始就诊
     *
     * @param id 挂号ID
     */
    void startVisit(Long id);

    /**
     * 结束就诊
     *
     * @param id 挂号ID
     */
    void endVisit(Long id);

    /**
     * 获取医生的候诊列表
     *
     * @param doctorId 医生ID
     * @param date 日期
     * @return 候诊列表
     */
    List<OpRegisterDO> getWaitingList(Long doctorId, LocalDate date);

    /**
     * 叫号（获取下一个候诊患者）
     *
     * @param doctorId 医生ID
     * @param date 日期
     * @return 下一个候诊患者
     */
    OpRegisterDO callNext(Long doctorId, LocalDate date);

    /**
     * 校验挂号是否存在
     *
     * @param id 挂号ID
     * @return 挂号记录
     */
    OpRegisterDO validateRegisterExists(Long id);

}