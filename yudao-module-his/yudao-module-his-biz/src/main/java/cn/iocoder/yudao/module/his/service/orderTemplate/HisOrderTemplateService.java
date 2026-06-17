package cn.iocoder.yudao.module.his.service.orderTemplate;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo.HisOrderTemplatePageReqVO;
import cn.iocoder.yudao.module.his.controller.admin.orderTemplate.vo.HisOrderTemplateSaveReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateDO;
import cn.iocoder.yudao.module.his.dal.dataobject.orderTemplate.HisOrderTemplateItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 医嘱模板 Service 接口
 *
 * @author 芋道源码
 */
public interface HisOrderTemplateService {

    /**
     * 创建医嘱模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrderTemplate(@Valid HisOrderTemplateSaveReqVO createReqVO);

    /**
     * 更新医嘱模板
     *
     * @param updateReqVO 更新信息
     */
    void updateOrderTemplate(@Valid HisOrderTemplateSaveReqVO updateReqVO);

    /**
     * 删除医嘱模板
     *
     * @param id 编号
     */
    void deleteOrderTemplate(Long id);

    /**
     * 获得医嘱模板
     *
     * @param id 编号
     * @return 医嘱模板
     */
    HisOrderTemplateDO getOrderTemplate(Long id);

    /**
     * 获得医嘱模板分页
     *
     * @param pageReqVO 分页查询
     * @return 医嘱模板分页
     */
    PageResult<HisOrderTemplateDO> getOrderTemplatePage(HisOrderTemplatePageReqVO pageReqVO);

    /**
     * 获得医嘱模板明细列表
     *
     * @param templateId 模板ID
     * @return 明细列表
     */
    List<HisOrderTemplateItemDO> getOrderTemplateItemList(Long templateId);

    /**
     * 根据模板分类查询列表
     *
     * @param templateCategory 模板分类
     * @return 模板列表
     */
    List<HisOrderTemplateDO> getOrderTemplateListByCategory(Integer templateCategory);

    /**
     * 根据科室ID查询模板列表
     *
     * @param deptId 科室ID
     * @return 模板列表
     */
    List<HisOrderTemplateDO> getOrderTemplateListByDeptId(Long deptId);

    /**
     * 根据医生ID查询模板列表
     *
     * @param doctorId 医生ID
     * @return 模板列表
     */
    List<HisOrderTemplateDO> getOrderTemplateListByDoctorId(Long doctorId);

    /**
     * 根据医嘱类型查询模板列表
     *
     * @param orderType 医嘱类型
     * @return 模板列表
     */
    List<HisOrderTemplateDO> getOrderTemplateListByOrderType(Integer orderType);

    /**
     * 根据疾病编码查询模板列表
     *
     * @param diagnosisCode 疾病编码
     * @return 模板列表
     */
    List<HisOrderTemplateDO> getOrderTemplateListByDiagnosisCode(String diagnosisCode);

    /**
     * 根据名称模糊查询模板列表
     *
     * @param templateName 模板名称
     * @return 模板列表
     */
    List<HisOrderTemplateDO> getOrderTemplateListByName(String templateName);

    /**
     * 复制医嘱模板
     *
     * @param id 模板ID
     * @return 新模板ID
     */
    Long copyOrderTemplate(Long id);

    /**
     * 启用/禁用医嘱模板
     *
     * @param id 模板ID
     * @param status 状态
     */
    void updateOrderTemplateStatus(Long id, Integer status);

    /**
     * 记录模板使用
     *
     * @param id 模板ID
     */
    void recordTemplateUsage(Long id);

}
