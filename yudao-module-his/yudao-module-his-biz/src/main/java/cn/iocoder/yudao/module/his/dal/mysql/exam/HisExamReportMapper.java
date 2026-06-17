package cn.iocoder.yudao.module.his.dal.mysql.exam;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.exam.vo.HisExamReportPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamReportDO;
import cn.iocoder.yudao.module.his.dal.dataobject.exam.HisExamReportItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 检查报告 Mapper
 */
@Mapper
public interface HisExamReportMapper extends BaseMapperX<HisExamReportDO> {

    /**
     * 分页查询检查报告
     */
    default PageResult<HisExamReportDO> selectPage(HisExamReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisExamReportDO>()
                .likeIfPresent(HisExamReportDO::getReportNo, reqVO.getReportNo())
                .likeIfPresent(HisExamReportDO::getPatientName, reqVO.getPatientName())
                .eqIfPresent(HisExamReportDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisExamReportDO::getRequestId, reqVO.getRequestId())
                .eqIfPresent(HisExamReportDO::getExamType, reqVO.getExamType())
                .likeIfPresent(HisExamReportDO::getExamCategory, reqVO.getExamCategory())
                .eqIfPresent(HisExamReportDO::getReportStatus, reqVO.getReportStatus())
                .betweenIfPresent(HisExamReportDO::getReportTime, reqVO.getReportTime())
                .betweenIfPresent(HisExamReportDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(HisExamReportDO::getId));
    }

    /**
     * 根据报告编号查询
     */
    default HisExamReportDO selectByReportNo(String reportNo) {
        return selectOne(HisExamReportDO::getReportNo, reportNo);
    }

    /**
     * 根据申请ID查询报告
     */
    default HisExamReportDO selectByRequestId(Long requestId) {
        return selectOne(HisExamReportDO::getRequestId, requestId);
    }

    /**
     * 根据患者ID查询报告列表
     */
    default List<HisExamReportDO> selectListByPatientId(Long patientId) {
        return selectList(new LambdaQueryWrapperX<HisExamReportDO>()
                .eq(HisExamReportDO::getPatientId, patientId)
                .orderByDesc(HisExamReportDO::getReportTime));
    }

}

/**
 * 检查报告明细 Mapper
 */
@Mapper
public interface HisExamReportItemMapper extends BaseMapperX<HisExamReportItemDO> {

    /**
     * 根据报告ID查询明细列表
     */
    default List<HisExamReportItemDO> selectListByReportId(Long reportId) {
        return selectList(new LambdaQueryWrapperX<HisExamReportItemDO>()
                .eq(HisExamReportItemDO::getReportId, reportId)
                .orderByAsc(HisExamReportItemDO::getSortOrder));
    }

    /**
     * 根据报告ID删除明细
     */
    default void deleteByReportId(Long reportId) {
        delete(new LambdaQueryWrapperX<HisExamReportItemDO>()
                .eq(HisExamReportItemDO::getReportId, reportId));
    }

}