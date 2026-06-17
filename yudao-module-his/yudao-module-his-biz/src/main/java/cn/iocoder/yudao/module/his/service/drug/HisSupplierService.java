package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisSupplierPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisSupplierSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisSupplierDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 供应商管理 Service 接口
 *
 * @author 芋道源码
 */
public interface HisSupplierService {

    /**
     * 创建供应商
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplier(@Valid HisSupplierSaveReqVO createReqVO);

    /**
     * 更新供应商
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplier(@Valid HisSupplierSaveReqVO updateReqVO);

    /**
     * 删除供应商
     *
     * @param id 编号
     */
    void deleteSupplier(Long id);

    /**
     * 获得供应商
     *
     * @param id 编号
     * @return 供应商
     */
    HisSupplierDO getSupplier(Long id);

    /**
     * 获得供应商分页
     *
     * @param pageReqVO 分页查询
     * @return 供应商分页
     */
    PageResult<HisSupplierDO> getSupplierPage(HisSupplierPageReqVO pageReqVO);

    /**
     * 获得供应商列表
     *
     * @param ids 编号列表
     * @return 供应商列表
     */
    List<HisSupplierDO> getSupplierList(List<Long> ids);

    /**
     * 获得正常供应商列表
     *
     * @return 供应商列表
     */
    List<HisSupplierDO> getNormalSupplierList();

    /**
     * 校验供应商存在
     *
     * @param id 编号
     * @return 供应商
     */
    HisSupplierDO validateSupplierExists(Long id);

}