package cn.iocoder.yudao.module.his.service.bed;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.bed.vo.HisBedPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.bed.vo.HisBedSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.bed.HisBedDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 床位 Service 接口
 *
 * @author yudao
 */
public interface HisBedService {

    /**
     * 创建床位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBed(@Valid HisBedSaveReqVO createReqVO);

    /**
     * 更新床位
     *
     * @param updateReqVO 更新信息
     */
    void updateBed(@Valid HisBedSaveReqVO updateReqVO);

    /**
     * 删除床位
     *
     * @param id 编号
     */
    void deleteBed(Long id);

    /**
     * 获得床位
     *
     * @param id 编号
     * @return 床位
     */
    HisBedDO getBed(Long id);

    /**
     * 获得床位分页
     *
     * @param pageReqVO 分页查询
     * @return 床位分页
     */
    PageResult<HisBedDO> getBedPage(HisBedPageReqVO pageReqVO);

    /**
     * 获得病区下的床位列表
     *
     * @param wardId 病区ID
     * @return 床位列表
     */
    List<HisBedDO> getBedListByWardId(Long wardId);

    /**
     * 获得病区下的空床列表
     *
     * @param wardId 病区ID
     * @return 空床列表
     */
    List<HisBedDO> getEmptyBedListByWardId(Long wardId);

    /**
     * 获得所有空床列表
     *
     * @return 空床列表
     */
    List<HisBedDO> getEmptyBedList();

    /**
     * 占用床位
     *
     * @param id 床位ID
     * @param patientId 患者ID
     * @param patientName 患者姓名
     * @param admissionId 住院ID
     */
    void occupyBed(Long id, Long patientId, String patientName, Long admissionId);

    /**
     * 释放床位
     *
     * @param id 床位ID
     */
    void releaseBed(Long id);

    /**
     * 清洁完成
     *
     * @param id 床位ID
     */
    void cleanComplete(Long id);

    /**
     * 更新床位状态
     *
     * @param id 床位ID
     * @param status 状态
     */
    void updateBedStatus(Long id, Integer status);

    /**
     * 校验床位是否存在
     *
     * @param id 编号
     * @return 床位
     */
    HisBedDO validateBedExists(Long id);

    /**
     * 校验床位是否可用
     *
     * @param id 编号
     * @return 床位
     */
    HisBedDO validateBedAvailable(Long id);
}
