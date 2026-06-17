package cn.iocoder.yudao.module.his.dal.mysql.drug;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.drug.vo.HisDrugPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 药品目录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisDrugMapper extends BaseMapperX<HisDrugDO> {

    /**
     * 分页查询
     */
    default PageResult<HisDrugDO> selectPage(HisDrugPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisDrugDO>()
                .likeIfPresent(HisDrugDO::getDrugCode, reqVO.getDrugCode())
                .likeIfPresent(HisDrugDO::getDrugName, reqVO.getDrugName())
                .likeIfPresent(HisDrugDO::getDrugTradeName, reqVO.getDrugTradeName())
                .eqIfPresent(HisDrugDO::getDrugType, reqVO.getDrugType())
                .eqIfPresent(HisDrugDO::getDosageForm, reqVO.getDosageForm())
                .likeIfPresent(HisDrugDO::getManufacturer, reqVO.getManufacturer())
                .eqIfPresent(HisDrugDO::getOtcFlag, reqVO.getOtcFlag())
                .eqIfPresent(HisDrugDO::getNarcoticFlag, reqVO.getNarcoticFlag())
                .eqIfPresent(HisDrugDO::getPsychotropicFlag, reqVO.getPsychotropicFlag())
                .eqIfPresent(HisDrugDO::getAntibioticFlag, reqVO.getAntibioticFlag())
                .eqIfPresent(HisDrugDO::getInsuranceCategory, reqVO.getInsuranceCategory())
                .eqIfPresent(HisDrugDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisDrugDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisDrugDO::getId));
    }

    /**
     * 根据药品编码查询
     */
    default HisDrugDO selectByDrugCode(String drugCode) {
        return selectOne(HisDrugDO::getDrugCode, drugCode);
    }

    /**
     * 根据药品名称模糊查询
     */
    default List<HisDrugDO> selectListByName(String drugName) {
        return selectList(new LambdaQueryWrapperX<HisDrugDO>()
                .likeIfPresent(HisDrugDO::getDrugName, drugName)
                .eq(HisDrugDO::getStatus, 1));
    }

    /**
     * 根据药品类型查询
     */
    default List<HisDrugDO> selectListByDrugType(Integer drugType) {
        return selectList(new LambdaQueryWrapperX<HisDrugDO>()
                .eq(HisDrugDO::getDrugType, drugType)
                .eq(HisDrugDO::getStatus, 1));
    }

    /**
     * 查询在用药品列表
     */
    default List<HisDrugDO> selectListByStatus(Integer status) {
        return selectList(HisDrugDO::getStatus, status);
    }

    /**
     * 查询特殊药品列表（麻醉/精神/毒性）
     */
    default List<HisDrugDO> selectSpecialDrugList() {
        return selectList(new LambdaQueryWrapperX<HisDrugDO>()
                .and(wrapper -> wrapper
                        .eq(HisDrugDO::getNarcoticFlag, 1)
                        .or()
                        .eq(HisDrugDO::getPsychotropicFlag, 1)
                        .or()
                        .eq(HisDrugDO::getToxicFlag, 1))
                .eq(HisDrugDO::getStatus, 1));
    }

    /**
     * 查询抗菌药物列表
     */
    default List<HisDrugDO> selectAntibioticList() {
        return selectList(new LambdaQueryWrapperX<HisDrugDO>()
                .eq(HisDrugDO::getAntibioticFlag, 1)
                .eq(HisDrugDO::getStatus, 1));
    }

}
