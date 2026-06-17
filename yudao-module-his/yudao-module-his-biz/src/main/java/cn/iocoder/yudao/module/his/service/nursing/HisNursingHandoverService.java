package cn.iocoder.yudao.module.his.service.nursing;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.nursing.vo.HisNursingHandoverSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.nursing.HisNursingHandoverDO;

import java.util.List;

/**
 * 护理交接班 Service 接口
 */
public interface HisNursingHandoverService {

    /**
     * 创建护理交接班记录（发起交接）
     *
     * @param createReqVO 创建信息
     * @return 交接班ID
     */
    Long createNursingHandover(HisNursingHandoverSaveReqVO createReqVO);

    /**
     * 更新护理交接班记录
     *
     * @param updateReqVO 更新信息
     */
    void updateNursingHandover(HisNursingHandoverSaveReqVO updateReqVO);

    /**
     * 删除护理交接班记录
     *
     * @param id 交接班ID
     */
    void deleteNursingHandover(Long id);

    /**
     * 获取护理交接班记录
     *
     * @param id 交接班ID
     * @return 交接班记录
     */
    HisNursingHandoverDO getNursingHandover(Long id);

    /**
     * 分页查询护理交接班
     *
     * @param pageReqVO 分页查询条件
     * @return 分页结果
     */
    PageResult<HisNursingHandoverDO> getNursingHandoverPage(HisNursingHandoverPageReqVO pageReqVO);

    /**
     * 按病区ID查询交接班列表
     *
     * @param wardId 病区ID
     * @return 交接班列表
     */
    List<HisNursingHandoverDO> getNursingHandoverListByWard(Long wardId);

    /**
     * 查询待接班的记录
     *
     * @param wardId 病区ID（可选）
     * @return 待接班列表
     */
    List<HisNursingHandoverDO> getPendingTakeoverList(Long wardId);

    /**
     * 交班签名
     *
     * @param id 交接班ID
     */
    void signHandover(Long id);

    /**
     * 接班（接班签名）
     *
     * @param id 交接班ID
     * @param takeoverNurseId 接班护士ID
     */
    void takeover(Long id, Long takeoverNurseId);

    /**
     * 作废交接班记录
     *
     * @param id 交接班ID
     * @param reason 作废原因
     */
    void cancelNursingHandover(Long id, String reason);

    /**
     * 校验交接班记录是否存在
     *
     * @param id 交接班ID
     * @return 交接班记录
     */
    HisNursingHandoverDO validateNursingHandoverExists(Long id);

    /**
     * 获取最新的交接班记录
     *
     * @param wardId 病区ID
     * @return 最新交接班记录
     */
    HisNursingHandoverDO getLatestNursingHandover(Long wardId);
}