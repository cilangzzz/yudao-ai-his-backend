package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInboundPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugInboundSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInboundItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 入库管理 Service 接口
 *
 * @author 芋道源码
 */
public interface HisDrugInboundService {

    /**
     * 创建入库记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInbound(@Valid HisDrugInboundSaveReqVO createReqVO);

    /**
     * 更新入库记录
     *
     * @param updateReqVO 更新信息
     */
    void updateInbound(@Valid HisDrugInboundSaveReqVO updateReqVO);

    /**
     * 删除入库记录
     *
     * @param id 编号
     */
    void deleteInbound(Long id);

    /**
     * 审核入库记录
     *
     * @param id 编号
     * @param auditBy 审核人ID
     * @param auditByName 审核人姓名
     * @param passed 是否通过
     * @param remark 审核备注
     */
    void auditInbound(Long id, Long auditBy, String auditByName, boolean passed, String remark);

    /**
     * 获得入库记录
     *
     * @param id 编号
     * @return 入库记录
     */
    HisDrugInboundDO getInbound(Long id);

    /**
     * 根据入库单号查询
     *
     * @param inboundNo 入库单号
     * @return 入库记录
     */
    HisDrugInboundDO getInboundByNo(String inboundNo);

    /**
     * 获得入库记录分页
     *
     * @param pageReqVO 分页查询
     * @return 入库记录分页
     */
    PageResult<HisDrugInboundDO> getInboundPage(HisDrugInboundPageReqVO pageReqVO);

    /**
     * 获得入库明细列表
     *
     * @param inboundId 入库ID
     * @return 入库明细列表
     */
    List<HisDrugInboundItemDO> getInboundItems(Long inboundId);

    /**
     * 获得待审核入库列表
     *
     * @return 入库列表
     */
    List<HisDrugInboundDO> getPendingAuditList();

    /**
     * 校验入库记录存在
     *
     * @param id 编号
     * @return 入库记录
     */
    HisDrugInboundDO validateInboundExists(Long id);

    /**
     * 校验入库记录可以审核
     *
     * @param id 编号
     * @return 入库记录
     */
    HisDrugInboundDO validateInboundCanAudit(Long id);

    /**
     * 校验入库记录可以修改
     *
     * @param id 编号
     * @return 入库记录
     */
    HisDrugInboundDO validateInboundCanUpdate(Long id);

}