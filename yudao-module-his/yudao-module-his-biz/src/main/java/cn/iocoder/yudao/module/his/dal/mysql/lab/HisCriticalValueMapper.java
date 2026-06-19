package cn.iocoder.yudao.module.his.dal.mysql.lab;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.dal.dataobject.lab.HisCriticalValueDO;
import cn.iocoder.yudao.module.his.controller.admin.lab.vo.HisCriticalValuePageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 危急值记录 Mapper
 */
@Mapper
public interface HisCriticalValueMapper extends BaseMapperX<HisCriticalValueDO> {

    /**
     * 分页查询危急值
     */
    default PageResult<HisCriticalValueDO> selectPage(HisCriticalValuePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisCriticalValueDO>()
                .eqIfPresent(HisCriticalValueDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(HisCriticalValueDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(HisCriticalValueDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(HisCriticalValueDO::getDetectedTime, reqVO.getDetectedTime())
                .orderByDesc(HisCriticalValueDO::getDetectedTime));
    }

    /**
     * 查询指定状态的危急值列表
     */
    default List<HisCriticalValueDO> selectByStatus(Integer status) {
        return selectList(new LambdaQueryWrapper<HisCriticalValueDO>()
                .eq(HisCriticalValueDO::getStatus, status)
                .orderByAsc(HisCriticalValueDO::getDetectedTime));
    }

    /**
     * 查询超时未确认的危急值
     */
    default List<HisCriticalValueDO> selectTimeoutUnconfirmed(Integer timeoutMinutes) {
        return selectList(new LambdaQueryWrapper<HisCriticalValueDO>()
                .eq(HisCriticalValueDO::getStatus, 2) // 已通知状态
                .apply("TIMESTAMPDIFF(MINUTE, notify_time, NOW()) > {0}", timeoutMinutes)
                .orderByAsc(HisCriticalValueDO::getNotifyTime));
    }

}