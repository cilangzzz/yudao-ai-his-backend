package cn.iocoder.yudao.module.his.service.drugreturn;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drugreturn.vo.*;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drugreturn.OpDrugReturnItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 退药申请 Service 接口
 */
public interface OpDrugReturnService {

    /**
     * 创建退药申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDrugReturn(@Valid OpDrugReturnSaveReqVO createReqVO);

    /**
     * 更新退药申请
     *
     * @param updateReqVO 更新信息
     */
    void updateDrugReturn(@Valid OpDrugReturnSaveReqVO updateReqVO);

    /**
     * 删除退药申请
     *
     * @param id 编号
     */
    void deleteDrugReturn(Long id);

    /**
     * 获得退药申请
     *
     * @param id 编号
     * @return 退药申请
     */
    OpDrugReturnDO getDrugReturn(Long id);

    /**
     * 获得退药申请分页
     *
     * @param pageReqVO 分页查询
     * @return 退药申请分页
     */
    PageResult<OpDrugReturnDO> getDrugReturnPage(OpDrugReturnPageReqVO pageReqVO);

    /**
     * 获得退药申请列表
     *
     * @param ids 编号列表
     * @return 退药申请列表
     */
    List<OpDrugReturnDO> getDrugReturnList(List<Long> ids);

    /**
     * 获得退药明细列表
     *
     * @param returnId 退药ID
     * @return 退药明细列表
     */
    List<OpDrugReturnItemDO> getDrugReturnItemList(Long returnId);

    /**
     * 审核退药申请
     *
     * @param auditReqVO 审核信息
     */
    void auditDrugReturn(@Valid OpDrugReturnAuditReqVO auditReqVO);

    /**
     * 取消退药申请
     *
     * @param id 退药ID
     * @param cancelReason 取消原因
     */
    void cancelDrugReturn(Long id, String cancelReason);

    /**
     * 退药入库
     *
     * @param inboundReqVO 入库信息
     * @return 入库单号
     */
    String inboundDrugReturn(@Valid OpDrugReturnInboundReqVO inboundReqVO);

    /**
     * 退药退款
     *
     * @param id 退药ID
     */
    void refundDrugReturn(Long id);

    /**
     * 根据处方ID查询退药记录
     *
     * @param prescriptionId 处方ID
     * @return 退药记录列表
     */
    List<OpDrugReturnDO> getDrugReturnListByPrescriptionId(Long prescriptionId);

    /**
     * 根据发药ID查询退药记录
     *
     * @param dispenseId 发药ID
     * @return 退药记录列表
     */
    List<OpDrugReturnDO> getDrugReturnListByDispenseId(Long dispenseId);

    /**
     * 根据患者ID查询退药记录
     *
     * @param patientId 患者ID
     * @return 退药记录列表
     */
    List<OpDrugReturnDO> getDrugReturnListByPatientId(Long patientId);

    /**
     * 获取待审核退药数量
     *
     * @return 数量
     */
    Long getPendingCount();

    /**
     * 根据药房获取待处理退药列表
     *
     * @param pharmacyId 药房ID
     * @return 退药列表
     */
    List<OpDrugReturnDO> getPendingListByPharmacy(Long pharmacyId);

}