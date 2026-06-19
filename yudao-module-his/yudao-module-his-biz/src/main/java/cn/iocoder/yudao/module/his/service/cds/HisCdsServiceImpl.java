package cn.iocoder.yudao.module.his.service.cds;

import cn.iocoder.yudao.module.his.controller.admin.cds.vo.CdsCheckReqVO;
import cn.iocoder.yudao.module.his.controller.admin.cds.vo.CdsCheckRespVO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugDO;
import cn.iocoder.yudao.module.his.dal.dataobject.drug.HisDrugInteractionDO;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugInteractionMapper;
import cn.iocoder.yudao.module.his.dal.mysql.drug.HisDrugMapper;
import cn.iocoder.yudao.module.his.service.patient.HisPatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CDS 合理用药校验 Service 实现类
 */
@Service
@Validated
@Slf4j
public class HisCdsServiceImpl implements HisCdsService {

    @Resource
    private HisDrugInteractionMapper drugInteractionMapper;

    @Resource
    private HisDrugMapper drugMapper;

    @Resource
    private HisPatientService patientService;

    @Override
    public CdsCheckRespVO checkPrescription(CdsCheckReqVO reqVO) {
        CdsCheckRespVO result = new CdsCheckRespVO();

        // 1. 药物相互作用校验
        CdsCheckRespVO interactionResult = checkInteraction(reqVO);
        result.setInteractionResults(interactionResult.getInteractionResults());

        // 2. 过敏校验
        if (reqVO.getPatientId() != null) {
            CdsCheckRespVO allergyResult = checkAllergy(reqVO.getPatientId(),
                    reqVO.getDrugItems().toArray(new CdsCheckReqVO.DrugItem[0]));
            result.setAllergyResults(allergyResult.getAllergyResults());
        }

        // 3. 剂量校验
        CdsCheckRespVO dosageResult = checkDosage(reqVO);
        result.setDosageResults(dosageResult.getDosageResults());

        // 4. 配伍禁忌校验
        CdsCheckRespVO contraindicationResult = checkContraindication(reqVO);
        result.setContraindicationResults(contraindicationResult.getContraindicationResults());

        // 5. 计算总体风险等级
        int maxRiskLevel = calculateMaxRiskLevel(result);
        result.setRiskLevel(maxRiskLevel);
        result.setRiskLevelName(getRiskLevelName(maxRiskLevel));
        result.setPassed(maxRiskLevel < 3); // 禁止级别不通过

        return result;
    }

    @Override
    public CdsCheckRespVO checkInteraction(CdsCheckReqVO reqVO) {
        CdsCheckRespVO result = new CdsCheckRespVO();
        List<CdsCheckRespVO.InteractionResult> interactionResults = new ArrayList<>();

        List<CdsCheckReqVO.DrugItem> drugItems = reqVO.getDrugItems();
        if (drugItems == null || drugItems.size() < 2) {
            result.setInteractionResults(interactionResults);
            return result;
        }

        // 获取所有药品ID
        List<Long> drugIds = drugItems.stream()
                .map(CdsCheckReqVO.DrugItem::getDrugId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 查询所有相关的药物相互作用
        List<HisDrugInteractionDO> interactions = drugInteractionMapper.selectByDrugIds(drugIds);

        // 构建药品ID到药品名称的映射
        Map<Long, String> drugNameMap = new HashMap<>();
        for (CdsCheckReqVO.DrugItem item : drugItems) {
            if (item.getDrugId() != null) {
                drugNameMap.put(item.getDrugId(), item.getDrugName());
            }
        }

        // 检查每对药品组合
        for (int i = 0; i < drugItems.size(); i++) {
            for (int j = i + 1; j < drugItems.size(); j++) {
                Long drugIdA = drugItems.get(i).getDrugId();
                Long drugIdB = drugItems.get(j).getDrugId();

                if (drugIdA == null || drugIdB == null) {
                    continue;
                }

                // 查找这对药品的相互作用
                HisDrugInteractionDO interaction = findInteraction(interactions, drugIdA, drugIdB);
                if (interaction != null) {
                    CdsCheckRespVO.InteractionResult ir = new CdsCheckRespVO.InteractionResult();
                    ir.setDrugIdA(interaction.getDrugIdA());
                    ir.setDrugNameA(interaction.getDrugNameA());
                    ir.setDrugIdB(interaction.getDrugIdB());
                    ir.setDrugNameB(interaction.getDrugNameB());
                    ir.setInteractionType(interaction.getInteractionType());
                    ir.setSeverity(interaction.getSeverity());
                    ir.setRiskLevel(getRiskLevelBySeverity(interaction.getSeverity()));
                    ir.setDescription(interaction.getDescription());
                    ir.setClinicalEffect(interaction.getClinicalEffect());
                    ir.setSuggestion(interaction.getSuggestion());
                    interactionResults.add(ir);
                }
            }
        }

        result.setInteractionResults(interactionResults);
        return result;
    }

    @Override
    public CdsCheckRespVO checkAllergy(Long patientId, CdsCheckReqVO.DrugItem[] drugItems) {
        CdsCheckRespVO result = new CdsCheckRespVO();
        List<CdsCheckRespVO.AllergyResult> allergyResults = new ArrayList<>();

        // TODO: 实现过敏校验逻辑
        // 1. 查询患者过敏史
        // 2. 检查药品是否在过敏源列表中
        // 3. 生成过敏警告

        result.setAllergyResults(allergyResults);
        return result;
    }

    @Override
    public CdsCheckRespVO checkDosage(CdsCheckReqVO reqVO) {
        CdsCheckRespVO result = new CdsCheckRespVO();
        List<CdsCheckRespVO.DosageResult> dosageResults = new ArrayList<>();

        for (CdsCheckReqVO.DrugItem item : reqVO.getDrugItems()) {
            if (item.getDrugId() == null || item.getDosage() == null) {
                continue;
            }

            HisDrugDO drug = drugMapper.selectById(item.getDrugId());
            if (drug == null) {
                continue;
            }

            // TODO: 实现剂量校验逻辑
            // 1. 获取药品的常用剂量范围
            // 2. 检查单次剂量是否超限
            // 3. 计算日剂量并检查是否超限
        }

        result.setDosageResults(dosageResults);
        return result;
    }

    @Override
    public CdsCheckRespVO checkContraindication(CdsCheckReqVO reqVO) {
        CdsCheckRespVO result = new CdsCheckRespVO();
        List<CdsCheckRespVO.ContraindicationResult> contraindicationResults = new ArrayList<>();

        // TODO: 实现配伍禁忌校验逻辑
        // 1. 检查药品间的理化配伍禁忌
        // 2. 检查特殊人群禁忌（孕妇、儿童、肝肾功能不全等）

        result.setContraindicationResults(contraindicationResults);
        return result;
    }

    // ==================== 辅助方法 ====================

    /**
     * 在相互作用列表中查找指定药品对的相互作用
     */
    private HisDrugInteractionDO findInteraction(List<HisDrugInteractionDO> interactions,
                                                  Long drugIdA, Long drugIdB) {
        for (HisDrugInteractionDO interaction : interactions) {
            if ((interaction.getDrugIdA().equals(drugIdA) && interaction.getDrugIdB().equals(drugIdB))
                    || (interaction.getDrugIdA().equals(drugIdB) && interaction.getDrugIdB().equals(drugIdA))) {
                return interaction;
            }
        }
        return null;
    }

    /**
     * 根据严重程度获取风险等级
     */
    private Integer getRiskLevelBySeverity(String severity) {
        if (severity == null) {
            return 1;
        }
        switch (severity) {
            case "禁忌":
                return 3;
            case "重度":
                return 3;
            case "中度":
                return 2;
            case "轻度":
            default:
                return 1;
        }
    }

    /**
     * 计算总体风险等级
     */
    private int calculateMaxRiskLevel(CdsCheckRespVO result) {
        int maxLevel = 0;

        if (result.getInteractionResults() != null) {
            for (CdsCheckRespVO.InteractionResult ir : result.getInteractionResults()) {
                if (ir.getRiskLevel() != null && ir.getRiskLevel() > maxLevel) {
                    maxLevel = ir.getRiskLevel();
                }
            }
        }

        if (result.getAllergyResults() != null) {
            for (CdsCheckRespVO.AllergyResult ar : result.getAllergyResults()) {
                if (ar.getRiskLevel() != null && ar.getRiskLevel() > maxLevel) {
                    maxLevel = ar.getRiskLevel();
                }
            }
        }

        if (result.getDosageResults() != null) {
            for (CdsCheckRespVO.DosageResult dr : result.getDosageResults()) {
                if (dr.getRiskLevel() != null && dr.getRiskLevel() > maxLevel) {
                    maxLevel = dr.getRiskLevel();
                }
            }
        }

        if (result.getContraindicationResults() != null) {
            for (CdsCheckRespVO.ContraindicationResult cr : result.getContraindicationResults()) {
                if (cr.getRiskLevel() != null && cr.getRiskLevel() > maxLevel) {
                    maxLevel = cr.getRiskLevel();
                }
            }
        }

        return maxLevel;
    }

    /**
     * 获取风险等级名称
     */
    private String getRiskLevelName(Integer riskLevel) {
        if (riskLevel == null) {
            return "无风险";
        }
        switch (riskLevel) {
            case 3:
                return "禁止";
            case 2:
                return "警告";
            case 1:
                return "提示";
            default:
                return "无风险";
        }
    }

}