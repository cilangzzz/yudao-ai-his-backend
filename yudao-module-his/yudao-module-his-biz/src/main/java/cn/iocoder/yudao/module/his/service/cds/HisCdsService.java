package cn.iocoder.yudao.module.his.service.cds;

import cn.iocoder.yudao.module.his.controller.admin.cds.vo.CdsCheckReqVO;
import cn.iocoder.yudao.module.his.controller.admin.cds.vo.CdsCheckRespVO;

/**
 * CDS 合理用药校验 Service 接口
 */
public interface HisCdsService {

    /**
     * 处方综合校验
     * 包括：药物相互作用、过敏、剂量、配伍禁忌等
     *
     * @param reqVO 校验请求
     * @return 校验结果
     */
    CdsCheckRespVO checkPrescription(CdsCheckReqVO reqVO);

    /**
     * 药物相互作用校验
     *
     * @param reqVO 校验请求
     * @return 相互作用校验结果
     */
    CdsCheckRespVO checkInteraction(CdsCheckReqVO reqVO);

    /**
     * 过敏校验
     *
     * @param patientId 患者ID
     * @param drugItems 药品列表
     * @return 过敏校验结果
     */
    CdsCheckRespVO checkAllergy(Long patientId, CdsCheckReqVO.DrugItem[] drugItems);

    /**
     * 剂量校验
     *
     * @param reqVO 校验请求
     * @return 剂量校验结果
     */
    CdsCheckRespVO checkDosage(CdsCheckReqVO reqVO);

    /**
     * 配伍禁忌校验
     *
     * @param reqVO 校验请求
     * @return 配伍禁忌校验结果
     */
    CdsCheckRespVO checkContraindication(CdsCheckReqVO reqVO);

}