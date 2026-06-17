package cn.iocoder.yudao.module.his.dal.mysql.exam;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamItemPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 检查项目字典 Mapper
 */
@Mapper
public interface HisExamItemMapper extends BaseMapperX<HisExamItemDO> {

    /**
     * 分页查询检查项目
     */
    default PageResult<HisExamItemDO> selectPage(HisExamItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisExamItemDO>()
                .likeIfPresent(HisExamItemDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(HisExamItemDO::getItemName, reqVO.getItemName())
                .eqIfPresent(HisExamItemDO::getExamType, reqVO.getExamType())
                .likeIfPresent(HisExamItemDO::getItemCategory, reqVO.getItemCategory())
                .eqIfPresent(HisExamItemDO::getExecutionDept, reqVO.getExecutionDept())
                .eqIfPresent(HisExamItemDO::getStatus, reqVO.getStatus())
                .orderByAsc(HisExamItemDO::getItemCode));
    }

    /**
     * 根据项目编码查询
     */
    default HisExamItemDO selectByItemCode(String itemCode) {
        return selectOne(HisExamItemDO::getItemCode, itemCode);
    }

    /**
     * 根据检查类型查询项目列表
     */
    default List<HisExamItemDO> selectListByExamType(Integer examType) {
        return selectList(new LambdaQueryWrapperX<HisExamItemDO>()
                .eq(HisExamItemDO::getExamType, examType)
                .eq(HisExamItemDO::getStatus, 1)
                .orderByAsc(HisExamItemDO::getItemCode));
    }

    /**
     * 根据项目类别查询项目列表
     */
    default List<HisExamItemDO> selectListByCategory(String itemCategory) {
        return selectList(new LambdaQueryWrapperX<HisExamItemDO>()
                .eq(HisExamItemDO::getItemCategory, itemCategory)
                .eq(HisExamItemDO::getStatus, 1)
                .orderByAsc(HisExamItemDO::getItemCode));
    }

    /**
     * 查询所有在用项目
     */
    default List<HisExamItemDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<HisExamItemDO>()
                .eq(HisExamItemDO::getStatus, status)
                .orderByAsc(HisExamItemDO::getItemCode));
    }

}
