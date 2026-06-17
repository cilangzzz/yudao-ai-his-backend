package cn.iocoder.yudao.module.his.dal.mysql.patient;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.his.controller.admin.patient.vo.HisPatientPageReqVO;
import cn.iocoder.yudao.module.his.dal.dataobject.patient.HisPatientDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 患者 Mapper
 */
@Mapper
public interface HisPatientMapper extends BaseMapperX<HisPatientDO> {

    /**
     * 分页查询患者
     */
    default PageResult<HisPatientDO> selectPage(HisPatientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<HisPatientDO>()
                .likeIfPresent(HisPatientDO::getName, reqVO.getName())
                .eqIfPresent(HisPatientDO::getIdCardNo, reqVO.getIdCardNo())
                .likeIfPresent(HisPatientDO::getPhone, reqVO.getPhone())
                .eqIfPresent(HisPatientDO::getStatus, reqVO.getStatus())
                .orderByDesc(HisPatientDO::getId));
    }

    /**
     * 根据身份证号查询
     */
    default HisPatientDO selectByIdCardNo(String idCardNo) {
        return selectOne(HisPatientDO::getIdCardNo, idCardNo);
    }

    /**
     * 根据患者编号查询
     */
    default HisPatientDO selectByPatientNo(String patientNo) {
        return selectOne(HisPatientDO::getPatientNo, patientNo);
    }

}
