package cn.iocoder.yudao.module.his.dal.mysql.orderTemplate;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo.HisOrderTemplatePageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateDO;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 医嘱模板 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface HisOrderTemplateMapper extends BaseMapperX<HisOrderTemplateDO> {

    /**
     * 分页查询
     */
    default PageResult<HisOrderTemplateDO> selectPage(HisOrderTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisOrderTemplateDO>()
                .likeIfPresent(HisOrderTemplateDO::getTemplateCode, reqVO.getTemplateCode())
                .likeIfPresent(HisOrderTemplateDO::getTemplateName, reqVO.getTemplateName())
                .eqIfPresent(HisOrderTemplateDO::getTemplateCategory, reqVO.getTemplateCategory())
                .eqIfPresent(HisOrderTemplateDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(HisOrderTemplateDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisOrderTemplateDO::getDoctorId, reqVO.getDoctorId())
                .likeIfPresent(HisOrderTemplateDO::getDiagnosisName, reqVO.getDiagnosisName())
                .eqIfPresent(HisOrderTemplateDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisOrderTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(HisOrderTemplateDO::getSort)
                .orderByDesc(HisOrderTemplateDO::getId));
    }

    /**
     * 根据模板编码查询
     */
    default HisOrderTemplateDO selectByTemplateCode(String templateCode) {
        return selectOne(HisOrderTemplateDO::getTemplateCode, templateCode);
    }

    /**
     * 根据模板分类查询
     */
    default List<HisOrderTemplateDO> selectListByTemplateCategory(Integer templateCategory) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateDO>()
                .eq(HisOrderTemplateDO::getTemplateCategory, templateCategory)
                .eq(HisOrderTemplateDO::getStatus, 1)
                .orderByAsc(HisOrderTemplateDO::getSort));
    }

    /**
     * 根据科室ID查询
     */
    default List<HisOrderTemplateDO> selectListByDeptId(Long deptId) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateDO>()
                .eq(HisOrderTemplateDO::getDeptId, deptId)
                .eq(HisOrderTemplateDO::getStatus, 1)
                .orderByAsc(HisOrderTemplateDO::getSort));
    }

    /**
     * 根据医生ID查询
     */
    default List<HisOrderTemplateDO> selectListByDoctorId(Long doctorId) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateDO>()
                .eq(HisOrderTemplateDO::getDoctorId, doctorId)
                .eq(HisOrderTemplateDO::getStatus, 1)
                .orderByAsc(HisOrderTemplateDO::getSort));
    }

    /**
     * 根据医嘱类型查询
     */
    default List<HisOrderTemplateDO> selectListByOrderType(Integer orderType) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateDO>()
                .eq(HisOrderTemplateDO::getOrderType, orderType)
                .eq(HisOrderTemplateDO::getStatus, 1)
                .orderByAsc(HisOrderTemplateDO::getSort));
    }

    /**
     * 根据疾病编码查询
     */
    default List<HisOrderTemplateDO> selectListByDiagnosisCode(String diagnosisCode) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateDO>()
                .eq(HisOrderTemplateDO::getDiagnosisCode, diagnosisCode)
                .eq(HisOrderTemplateDO::getStatus, 1)
                .orderByAsc(HisOrderTemplateDO::getSort));
    }

    /**
     * 根据名称模糊查询
     */
    default List<HisOrderTemplateDO> selectListByName(String templateName) {
        return selectList(new LambdaQueryWrapperX<HisOrderTemplateDO>()
                .likeIfPresent(HisOrderTemplateDO::getTemplateName, templateName)
                .eq(HisOrderTemplateDO::getStatus, 1)
                .orderByAsc(HisOrderTemplateDO::getSort));
    }

    /**
     * 增加使用次数
     */
    default void incrementUseCount(Long id) {
        HisOrderTemplateDO template = selectById(id);
        if (template != null) {
            template.setUseCount(template.getUseCount() == null ? 1 : template.getUseCount() + 1);
            updateById(template);
        }
    }

}