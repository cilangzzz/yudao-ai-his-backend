package cn.iocoder.yudao.module.his.dal.mysql.orderTemplate;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 医嘱模板明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisOrderTemplateItemMapper extends BaseMapperX<HisOrderTemplateItemDO> {

    /**
     * 根据模板ID查询明细列表
     */
    default List<HisOrderTemplateItemDO> selectListByTemplateId(Long templateId) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateItemDO>()
                .eq(HisOrderTemplateItemDO::getTemplateId, templateId)
                .orderByAsc(HisOrderTemplateItemDO::getSort));
    }

    /**
     * 根据模板ID删除明细
     */
    default void deleteByTemplateId(Long templateId) {
        delete(new LambdaQueryWrapperX<HisOrderTemplateItemDO>()
                .eq(HisOrderTemplateItemDO::getTemplateId, templateId));
    }

    /**
     * 根据模板ID统计明细数量
     */
    default Long selectCountByTemplateId(Long templateId) {
        return selectCount(new LambdaQueryWrapperX<HisOrderTemplateItemDO>()
                .eq(HisOrderTemplateItemDO::getTemplateId, templateId));
    }

    /**
     * 根据项目编码查询模板明细
     */
    default List<HisOrderTemplateItemDO> selectListByItemCode(String itemCode) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateItemDO>()
                .eq(HisOrderTemplateItemDO::getItemCode, itemCode));
    }

}