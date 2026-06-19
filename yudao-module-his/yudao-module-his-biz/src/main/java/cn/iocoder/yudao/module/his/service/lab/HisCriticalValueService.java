package cn.iocoder.yudao.module.his.service.lab;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisCriticalValuePageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisCriticalValueDO;

import java.util.List;

/**
 * 危急值管理 Service 接口
 */
public interface HisCriticalValueService {

    /**
     * 获得危急值分页
     *
     * @param pageReqVO 分页查询
     * @return 危急值分页
     */
    PageResult<HisCriticalValueDO> getCriticalValuePage(HisCriticalValuePageReqVO pageReqVO);

    /**
     * 获得危急值
     *
     * @param id 编号
     * @return 危急值
     */
    HisCriticalValueDO getCriticalValue(Long id);

    /**
     * 获得待通知的危急值列表（状态=检出）
     *
     * @return 危急值列表
     */
    List<HisCriticalValueDO> getDetectedList();

    /**
     * 获得未确认的危急值列表（状态=已通知）
     *
     * @return 危急值列表
     */
    List<HisCriticalValueDO> getUnconfirmedList();

    /**
     * 通知危急值
     *
     * @param id 危急值ID
     */
    void notifyCriticalValue(Long id);

    /**
     * 确认危急值
     *
     * @param id 危急值ID
     */
    void confirmCriticalValue(Long id);

    /**
     * 处理危急值
     *
     * @param id 危急值ID
     * @param processResult 处理结果
     */
    void processCriticalValue(Long id, String processResult);

    /**
     * 校验危急值是否存在
     *
     * @param id 编号
     * @return 危急值
     */
    HisCriticalValueDO validateCriticalValueExists(Long id);

}