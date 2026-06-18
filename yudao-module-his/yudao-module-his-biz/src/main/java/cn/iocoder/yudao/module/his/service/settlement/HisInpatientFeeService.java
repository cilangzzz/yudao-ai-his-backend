package cn.iocoder.yudao.module.his.service.settlement;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientFeePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.settlement.vo.HisInpatientFeeSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.settlement.HisInpatientFeeDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 住院费用明细 Service 接口
 *
 * @author yudao
 */
public interface HisInpatientFeeService {

    /**
     * 创建住院费用明细
     *
     * @param createReqVO 创建信息
     * @return 费用明细ID
     */
    Long createFee(@Valid HisInpatientFeeSaveReqVO createReqVO);

    /**
     * 批量创建住院费用明细
     *
     * @param createReqVOList 创建信息列表
     * @return 费用明细ID列表
     */
    List<Long> batchCreateFee(@Valid List<HisInpatientFeeSaveReqVO> createReqVOList);

    /**
     * 更新住院费用明细
     *
     * @param updateReqVO 更新信息
     */
    void updateFee(@Valid HisInpatientFeeSaveReqVO updateReqVO);

    /**
     * 删除住院费用明细
     *
     * @param id 费用明细ID
     */
    void deleteFee(Long id);

    /**
     * 获取住院费用明细
     *
     * @param id 费用明细ID
     * @return 费用明细
     */
    HisInpatientFeeDO getFee(Long id);

    /**
     * 获取住院费用明细分页
     *
     * @param pageReqVO 分页请求
     * @return 费用明细分页
     */
    PageResult<HisInpatientFeeDO> getFeePage(HisInpatientFeePageReqVO pageReqVO);

    /**
     * 根据入院记录ID查询费用明细列表
     *
     * @param admissionId 入院记录ID
     * @return 费用明细列表
     */
    List<HisInpatientFeeDO> getFeeListByAdmissionId(Long admissionId);

    /**
     * 获取费用汇总（按类型汇总）
     *
     * @param admissionId 入院记录ID
     * @return 费用汇总结果
     */
    HisInpatientFeeDO getFeeSummaryByAdmissionId(Long admissionId);

    /**
     * 生成费用单号
     *
     * @return 费用单号
     */
    String generateFeeNo();

}