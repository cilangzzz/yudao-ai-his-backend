package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisSupplierPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisSupplierSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisSupplierDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisSupplierMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.his.enums.ErrorCodeConstants.SUPPLIER_NOT_EXISTS;

/**
 * 供应商管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class HisSupplierServiceImpl implements HisSupplierService {

    @Resource
    private HisSupplierMapper supplierMapper;

    @Override
    public Long createSupplier(HisSupplierSaveReqVO createReqVO) {
        // 插入
        HisSupplierDO supplier = BeanUtils.toBean(createReqVO, HisSupplierDO.class);
        supplierMapper.insert(supplier);
        return supplier.getId();
    }

    @Override
    public void updateSupplier(HisSupplierSaveReqVO updateReqVO) {
        // 校验存在
        validateSupplierExists(updateReqVO.getId());
        // 更新
        HisSupplierDO updateObj = BeanUtils.toBean(updateReqVO, HisSupplierDO.class);
        supplierMapper.updateById(updateObj);
    }

    @Override
    public void deleteSupplier(Long id) {
        // 校验存在
        validateSupplierExists(id);
        // 删除
        supplierMapper.deleteById(id);
    }

    @Override
    public HisSupplierDO getSupplier(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public PageResult<HisSupplierDO> getSupplierPage(HisSupplierPageReqVO pageReqVO) {
        return supplierMapper.selectPage(pageReqVO);
    }

    @Override
    public List<HisSupplierDO> getSupplierList(List<Long> ids) {
        return supplierMapper.selectBatchIds(ids);
    }

    @Override
    public List<HisSupplierDO> getNormalSupplierList() {
        return supplierMapper.selectNormalList();
    }

    @Override
    public HisSupplierDO validateSupplierExists(Long id) {
        if (id == null) {
            return null;
        }
        HisSupplierDO supplier = supplierMapper.selectById(id);
        if (supplier == null) {
            throw exception(SUPPLIER_NOT_EXISTS);
        }
        return supplier;
    }

}