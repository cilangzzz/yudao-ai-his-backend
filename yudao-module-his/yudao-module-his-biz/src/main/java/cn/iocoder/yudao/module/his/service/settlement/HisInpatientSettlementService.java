package cn.iocoder.yudao.module.his.service.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientSettlementPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientSettlementSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientSettlementDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 住院结算 Service 接口
 *
 * @author yudao
 */
public interface HisInpatientSettlementService {

    /**
     * 创建住院结算单
     *
     * @param createReqVO 创建信息
     * @return 结算单ID
     */
    Long createSettlement(@Valid HisInpatientSettlementSaveReqVO createReqVO);

    /**
     * 更新住院结算单
     *
     * @param updateReqVO 更新信息
     */
    void updateSettlement(@Valid HisInpatientSettlementSaveReqVO updateReqVO);

    /**
     * 删除住院结算单
     *
     * @param id 结算单ID
     */
    void deleteSettlement(Long id);

    /**
     * 获取住院结算单
     *
     * @param id 结算单ID
     * @return 结算单
     */
    HisInpatientSettlementDO getSettlement(Long id);

    /**
     * 获取住院结算单分页
     *
     * @param pageReqVO 分页请求
     * @return 结算单分页
     */
    PageResult<HisInpatientSettlementDO> getSettlementPage(HisInpatientSettlementPageReqVO pageReqVO);

    /**
     * 根据入院记录ID查询结算单
     *
     * @param admissionId 入院记录ID
     * @return 结算单
     */
    HisInpatientSettlementDO getSettlementByAdmissionId(Long admissionId);

    /**
     * 根据入院记录ID查询所有结算单
     *
     * @param admissionId 入院记录ID
     * @return 结算单列表
     */
    List<HisInpatientSettlementDO> getSettlementListByAdmissionId(Long admissionId);

    /**
     * 结算确认
     *
     * @param id 结算单ID
     * @param paymentType 支付方式
     * @param operatorId 操作员ID
     * @param operatorName 操作员姓名
     */
    void confirmSettlement(Long id, Integer paymentType, Long operatorId, String operatorName);

    /**
     * 退费处理
     *
     * @param id 结算单ID
     * @param refundAmount 退费金额
     * @param remark 备注
     */
    void refundSettlement(Long id, java.math.BigDecimal refundAmount, String remark);

    /**
     * 作废处理
     *
     * @param id 结算单ID
     * @param remark 备注
     */
    void cancelSettlement(Long id, String remark);

    /**
     * 生成结算单号
     *
     * @return 结算单号
     */
    String generateSettlementNo();

    /**
     * 计算费用汇总
     *
     * @param admissionId 入院记录ID
     * @return 费用汇总结果
     */
    HisInpatientSettlementDO calculateFeeSummary(Long admissionId);

}
