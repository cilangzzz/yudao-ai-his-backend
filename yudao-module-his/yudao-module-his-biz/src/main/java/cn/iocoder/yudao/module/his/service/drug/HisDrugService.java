package cn.iocoder.yudao.module.his.service.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 药品目录 Service 接口
 *
 * @author 芋道源码
 */
public interface HisDrugService {

    /**
     * 创建药品目录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDrug(@Valid HisDrugSaveReqVO createReqVO);

    /**
     * 更新药品目录
     *
     * @param updateReqVO 更新信息
     */
    void updateDrug(@Valid HisDrugSaveReqVO updateReqVO);

    /**
     * 删除药品目录
     *
     * @param id 编号
     */
    void deleteDrug(Long id);

    /**
     * 获得药品目录
     *
     * @param id 编号
     * @return 药品目录
     */
    HisDrugDO getDrug(Long id);

    /**
     * 根据药品编码获得药品
     *
     * @param drugCode 药品编码
     * @return 药品目录
     */
    HisDrugDO getDrugByCode(String drugCode);

    /**
     * 获得药品目录分页
     *
     * @param pageReqVO 分页查询
     * @return 药品目录分页
     */
    PageResult<HisDrugDO> getDrugPage(HisDrugPageReqVO pageReqVO);

    /**
     * 获得药品目录列表
     *
     * @param ids 编号列表
     * @return 药品目录列表
     */
    List<HisDrugDO> getDrugList(List<Long> ids);

    /**
     * 根据药品名称模糊查询列表
     *
     * @param drugName 药品名称
     * @return 药品列表
     */
    List<HisDrugDO> getDrugListByName(String drugName);

    /**
     * 根据药品类型查询列表
     *
     * @param drugType 药品类型
     * @return 药品列表
     */
    List<HisDrugDO> getDrugListByType(Integer drugType);

    /**
     * 获得特殊药品列表（麻醉/精神/毒性）
     *
     * @return 药品列表
     */
    List<HisDrugDO> getSpecialDrugList();

    /**
     * 获得抗菌药物列表
     *
     * @return 药品列表
     */
    List<HisDrugDO> getAntibioticList();

    /**
     * 校验药品是否存在
     *
     * @param id 编号
     * @return 药品目录
     */
    HisDrugDO validateDrugExists(Long id);

    /**
     * 校验药品编码唯一性
     *
     * @param id 编号
     * @param drugCode 药品编码
     */
    void validateDrugCodeUnique(Long id, String drugCode);

}