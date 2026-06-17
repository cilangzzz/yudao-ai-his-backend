package cn.iocoder.yudao.module.his.service.ward;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.ward.vo.HisWardPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.ward.vo.HisWardSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.ward.HisWardDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 病区 Service 接口
 *
 * @author yudao
 */
public interface HisWardService {

    /**
     * 创建病区
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWard(@Valid HisWardSaveReqVO createReqVO);

    /**
     * 更新病区
     *
     * @param updateReqVO 更新信息
     */
    void updateWard(@Valid HisWardSaveReqVO updateReqVO);

    /**
     * 删除病区
     *
     * @param id 编号
     */
    void deleteWard(Long id);

    /**
     * 获得病区
     *
     * @param id 编号
     * @return 病区
     */
    HisWardDO getWard(Long id);

    /**
     * 获得病区分页
     *
     * @param pageReqVO 分页查询
     * @return 病区分页
     */
    PageResult<HisWardDO> getWardPage(HisWardPageReqVO pageReqVO);

    /**
     * 获得病区列表
     *
     * @return 病区列表
     */
    List<HisWardDO> getWardList();

    /**
     * 获得科室下的病区列表
     *
     * @param deptId 科室ID
     * @return 病区列表
     */
    List<HisWardDO> getWardListByDeptId(Long deptId);

    /**
     * 更新病区床位统计
     *
     * @param wardId 病区ID
     */
    void updateWardBedStat(Long wardId);

    /**
     * 校验病区是否存在
     *
     * @param id 编号
     * @return 病区
     */
    HisWardDO validateWardExists(Long id);
}
