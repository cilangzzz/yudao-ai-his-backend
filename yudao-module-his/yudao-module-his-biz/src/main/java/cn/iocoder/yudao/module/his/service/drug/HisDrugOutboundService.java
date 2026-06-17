package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugOutboundPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugOutboundSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugOutboundItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 出库管理 Service 接口
 *
 * @author 芋道源码
 */
public interface HisDrugOutboundService {

    /**
     * 创建出库记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOutbound(@Valid HisDrugOutboundSaveReqVO createReqVO);

    /**
     * 更新出库记录
     *
     * @param updateReqVO 更新信息
     */
    void updateOutbound(@Valid HisDrugOutboundSaveReqVO updateReqVO);

    /**
     * 删除出库记录
     *
     * @param id 编号
     */
    void deleteOutbound(Long id);

    /**
     * 审核出库记录
     *
     * @param id 编号
     * @param auditBy 审核人ID
     * @param auditByName 审核人姓名
     * @param passed 是否通过
     * @param remark 审核备注
     */
    void auditOutbound(Long id, Long auditBy, String auditByName, boolean passed, String remark);

    /**
     * 获得出库记录
     *
     * @param id 编号
     * @return 出库记录
     */
    HisDrugOutboundDO getOutbound(Long id);

    /**
     * 根据出库单号查询
     *
     * @param outboundNo 出库单号
     * @return 出库记录
     */
    HisDrugOutboundDO getOutboundByNo(String outboundNo);

    /**
     * 获得出库记录分页
     *
     * @param pageReqVO 分页查询
     * @return 出库记录分页
     */
    PageResult<HisDrugOutboundDO> getOutboundPage(HisDrugOutboundPageReqVO pageReqVO);

    /**
     * 获得出库明细列表
     *
     * @param outboundId 出库ID
     * @return 出库明细列表
     */
    List<HisDrugOutboundItemDO> getOutboundItems(Long outboundId);

    /**
     * 获得待审核出库列表
     *
     * @return 出库列表
     */
    List<HisDrugOutboundDO> getPendingAuditList();

    /**
     * 校验出库记录存在
     *
     * @param id 编号
     * @return 出库记录
     */
    HisDrugOutboundDO validateOutboundExists(Long id);

    /**
     * 校验出库记录可以审核
     *
     * @param id 编号
     * @return 出库记录
     */
    HisDrugOutboundDO validateOutboundCanAudit(Long id);

    /**
     * 校验出库记录可以修改
     *
     * @param id 编号
     * @return 出库记录
     */
    HisDrugOutboundDO validateOutboundCanUpdate(Long id);

}